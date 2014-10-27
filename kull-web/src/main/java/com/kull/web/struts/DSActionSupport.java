/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web.struts;

import com.kull.ObjectHelper;
import com.kull.StringHelper;
import com.kull.orm.Session;

import com.kull.script.Html;

import com.kull.web.Utils;
import com.kull.web.anntation.DwrAjax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import net.sf.json.JSONObject;


/**
 *
 * @author lin
 */
public abstract class DSActionSupport extends AwareActionSupport {

    protected Connection connection;

    protected Integer start=0, limit=Integer.MAX_VALUE ;
    protected String rowsName="rows",totalName="total",errTypeName="errtype",errMsgName="errmsg";

    public void setStart(Integer start) {
        this.start = start;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    protected abstract String viewName();

    protected abstract String title();

    protected abstract String createPageSql(String dataSql,int start, int limit);
    
    protected Map<String,Object> rowEach(Map<String,Object> row) {
		return row;
   }

    protected String columns() {
        return "*";
    }

    protected String baseCondition() {
        return "";
    }

    protected String baseOrderby() {
        return "";
    }

    protected String postOrderby() {

        String orderby = "", sort = request.getParameter("sort"), order = request.getParameter("order");
        if (StringHelper.isNotBlank(sort) && StringHelper.isNotBlank(order)) {
            orderby += sort + " " + order;
        }
        return orderby;
    }

    protected String postCondition() {
        String condition = "";
        for (Iterator<String> it = this.parameters.keySet().iterator(); it.hasNext();) {
            String name = it.next();
            String value = this.parameters.get(name)[0];
            if (StringHelper.isBlank(value)) {
                continue;
            }
            if (name.startsWith("qc_like_")) {
                String colName = name.substring("qc_like_".length(), name.length());
                condition += MessageFormat.format(" and {0} like '%{1}%'", colName, value);
            } else if (name.startsWith("qc_eq_")) {
                String colName = name.substring("qc_eq_".length(), name.length());
                condition += MessageFormat.format(" and {0} = '{1}'", colName, value);
            } else if (name.startsWith("qc_in_")) {
                String colName = name.substring("qc_in_".length(), name.length());
                condition += MessageFormat.format(" and {0} in ({1})", colName, value);
            } else if (name.startsWith("qn_eq_")) {
                String colName = name.substring("qn_eq_".length(), name.length());
                condition += MessageFormat.format(" and {0} = {1}", colName, value);
            } else if (name.startsWith("qn_in_")) {
                String colName = name.substring("qn_in_".length(), name.length());
                condition += MessageFormat.format(" and {0} in ({1})", colName, value);
            } else if (name.startsWith("qn_from_")) {
                String colName = name.substring("qn_from_".length(), name.length());
                String toName = "qn_to_" + colName;
                if (parameters.containsKey(toName)) {
                    String toValue = this.parameters.get(toName)[0];
                    condition += MessageFormat.format(" and {0} between {1} and {2}", colName, value, toValue);
                } else {
                    condition += MessageFormat.format(" and {0} > {1}", colName, value);
                }
            }
        }
        return condition;
    }

    protected final String createDataSql() {
        String sql = "", sqlPattern = "select {0} from {1} {2} ";
        String condition = "", columns = this.columns(), viewName = viewName(), baseCondition = this.baseCondition(), postCondition = this.postCondition();
        if (StringHelper.isNotBlank(baseCondition) && StringHelper.isNotBlank(postCondition)) {
            baseCondition = StringHelper.trim(baseCondition, ",", "and", "or", "and");
            postCondition = StringHelper.trim(postCondition, ",", "and", "or", "and");
            condition = MessageFormat.format(" where {0} and ( {1} ) ", baseCondition, postCondition);
        } else if (StringHelper.isNotBlank(baseCondition)) {
            baseCondition = StringHelper.trim(baseCondition, ",", "and", "or", "and");
            condition = " where " + baseCondition;
        } else if (StringHelper.isNotBlank(postCondition)) {
            postCondition = StringHelper.trim(postCondition, ",", "and", "or", "and");
            condition = " where " + postCondition;
        }
        sql = MessageFormat.format(sqlPattern,
                columns,
                viewName,
                condition
        );
        return sql;

    }

    protected final String createOrderbySql() {
        String sql = createDataSql(), orderby = " ", baseOrderby = StringHelper.trim(this.baseOrderby(), ","), postOrderby = StringHelper.trim(this.postOrderby(), ",");

        if (StringHelper.isNotBlank(baseOrderby) && StringHelper.isNotBlank(postOrderby)) {
            orderby += MessageFormat.format(" order by {0},{1} ", StringHelper.trim(baseOrderby, ","), StringHelper.trim(postOrderby, ","));
        } else if (StringHelper.isNotBlank(baseOrderby)) {
            orderby += " order by " + StringHelper.trim(baseOrderby, ",");
        } else if (StringHelper.isNotBlank(postOrderby)) {
            orderby += " order by " + StringHelper.trim(postOrderby, ",");
        }
        sql += orderby;
        return sql;
    }

    protected final String createCountSql() {
        String sql = "", sqlPattern = "select count(*) from {0} {1} ";
        String condition = "", baseCondition = this.baseCondition(), postCondition = this.postCondition(), viewName = viewName();
        if (StringHelper.isNotBlank(baseCondition) && StringHelper.isNotBlank(postCondition)) {
            condition = MessageFormat.format(" where {0} and ( {1} ) ", baseCondition, postCondition);
        } else if (StringHelper.isNotBlank(baseCondition)) {
            condition = " where " + baseCondition;
        } else if (StringHelper.isNotBlank(postCondition)) {
            condition = " where " + postCondition;
        }

        sql = MessageFormat.format(sqlPattern,
                viewName,
                condition
        );
        return sql;
    }

    @DwrAjax(method = DwrAjax.Method.post)
    public void grid() throws IOException {

        Session session = new Session(connection);

        String sql = createPageSql(this.createOrderbySql(),start, limit);

        JSONObject grid=new JSONObject();
        try {
            LinkedList<Map<String, Object>> rows = session.selectList(sql), rowsNew = new LinkedList<Map<String, Object>>();

            for (int i = 0; i < rows.size(); i++) {
                rowsNew.add(rowEach(rows.get(i)));
            }
            grid.put(rowsName, rowsNew);
            if (rows.size() < limit) {
                grid.put(totalName, rows.size());
            } else {
                String csql = createCountSql();
                int count = session.selectInt(csql);
                grid.put(totalName, count);
            }
        } catch (Exception ex) {
            
                grid.put(errMsgName, ex.getMessage());
                grid.put(errTypeName, ex.getClass().getName());
           
        }
        Session.close(connection, null, null);
        this.response.getWriter().write(grid.toString());
    }
    
    	public void debug() throws IOException {
		
		Map<String,Object> debug=new HashMap<String, Object>();

		try {
			String dsql=this.createDataSql();
                        String osql=this.createOrderbySql();
			String psql=this.createPageSql(dsql,start, limit);
			String csql=this.createCountSql();
			debug.put("dataSql", dsql);
			debug.put("pageSql", psql);
			debug.put("countSql", csql);
                        debug.put("orderbySql", osql);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			debug.put(errTypeName, ex.getClass().getName());
			debug.put(errMsgName, ex.getMessage());
		}
	     Utils.writeJavascript(this.response,debug.toString());
	}

        
        public void help() throws Exception{
        
        StringBuffer html=new StringBuffer("");
        String sql="select * from "+this.viewName()+" where 1=2";
        PreparedStatement ps= connection.prepareStatement(sql);
		ResultSetMetaData rsmd= ps.executeQuery().getMetaData();
        String durl=request.getRequestURI().replace("/help", "/grid");
		StringBuffer datagrid=new StringBuffer(""),datagrid_js=new StringBuffer("");
		 int[] int_cts={Types.BIGINT,Types.INTEGER,Types.SMALLINT,Types.TINYINT};
	        int[] float_cts={Types.FLOAT,Types.DOUBLE};
		datagrid.append("<table id=\"table-datagrid\" class=\"easyui-datagrid\" data-options=\"url:'"+durl+"',fit:true,toolbar:'#div-toolbar',idField:'_id'\"> \n");
		datagrid.append("<thead data-options=\"frozen:true\"><tr> </thead><thead><tr> \n");
		for(int i=1;i<=rsmd.getColumnCount();i++){
			String field=rsmd.getColumnLabel(i);
			int ct=rsmd.getColumnType(i);
			String editor="type:''validatebox'',options:'{'required:true '}'";
			if(ObjectHelper.isIn(ct, Types.BIGINT,Types.INTEGER,Types.SMALLINT,Types.TINYINT)){
				editor="type:''numberspinner'',options:'{'required:true,precision:0 '}'";
			}else if(ObjectHelper.isIn(ct, Types.FLOAT,Types.DOUBLE)){
				editor="type:''numberbox'',options:'{'required:true,precision:2 '}'";
			}else if(ObjectHelper.isIn(ct, Types.DATE)){
				editor="type:''datebox'',options:'{'required:true '}'";
			}else if(ObjectHelper.isIn(ct, Types.TIMESTAMP)){
				editor="type:''datetimebox'',options:'{'required:true '}'"; 
			}
			datagrid.append(MessageFormat.format("\t<th data-options=\"field:''{0}'',width:180,title:''{0}'',editor:'{' "+editor+" '}'\">"+field+"</th> \n",field));
		}
		datagrid.append("</thead></tr> \n");
		datagrid.append("</table>\n")
		.append("<div id=\"div-toolbar\"></div>")
		;
       
		for(int i=1;i<=rsmd.getColumnCount();i++){
			String field=rsmd.getColumnLabel(i);
			int ct=rsmd.getColumnType(i);
			String editor="type:\"validatebox\",options:'{'required:true '}'";
			if(ObjectHelper.isIn(ct, Types.BIGINT,Types.INTEGER,Types.SMALLINT,Types.TINYINT)){
				editor="type:\"numberspinner\",options:'{'required:true,precision:0 '}'";
			}else if(ObjectHelper.isIn(ct, Types.FLOAT,Types.DOUBLE)){
				editor="type:\"numberbox\",options:'{'required:true,precision:2 '}'";
			}else if(ObjectHelper.isIn(ct, Types.DATE)){
				editor="type:\"datebox\",options:'{'required:true '}'";
			}else if(ObjectHelper.isIn(ct, Types.TIMESTAMP)){
				editor="type:\"datetimebox\",options:'{'required:true '}'";
			}
			datagrid_js.append(MessageFormat.format("var col_{0}='{'field:''{0}'',width:180,title:''{0}'',editor:'{' "+editor+" '}' '}'; \n",field));
		 
		}
		datagrid_js.append("$(\"#table-datagrid_crud\").datagrid({url:\""+durl+"\",fit:true,toolbar:[],idField:\"_id\",")
		.append("frozenColumns:[],columns:[[ \n")
		;
		for(int i=1;i<=rsmd.getColumnCount();i++){
			String field=rsmd.getColumnLabel(i);
			datagrid_js.append("col_"+field);
		    if(i!=rsmd.getColumnCount()){
		    	datagrid_js.append(",");
		    }
		}
		datagrid_js.append(" ]]\n")
		.append("} ) ;  //$(\"#table-datagrid_crud\").datagrid")
		//datagrid_js.append("</table>\n")
		//.append("<div id=\"div-toolbar\"></div>")
		;
		
		html
		.append("<html><body>")
		.append(Html.fieldset("easyui-datagrid html", "<pre>"+StringHelper.htmlWapper(datagrid.toString())+"</pre>"))
		.append(Html.fieldset("easyui-datagrid js", "<pre>"+StringHelper.htmlWapper(datagrid_js.toString())+"</pre>"))
		.append("</body></html>");
                Session.close(connection, ps, null);
                Utils.writeJavascript(this.response, html.toString());
		
	}
}
