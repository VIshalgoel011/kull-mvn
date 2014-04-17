package com.kull.struts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;






import com.kull.ObjectHelper;
import com.kull.StringHelper;
import com.kull.bean.JdbcBean;





public abstract class SimpleViewActionSupport  extends AwareActionSupport {

 	
/**
	 * 
	 */
	private static final long serialVersionUID = 3155087172246475126L;

protected  abstract  Connection createConnection();


	protected abstract String viewName();
	protected abstract String title();
	protected abstract String toJson(Object obj);
	
	protected  int postStart(){
		return paramInt("start", 0);
	};
	protected  int postLimit(){
		return paramInt("limit", 100);
	};
	
	protected String columns(){
		return "*";
	}
	
	protected String remindMsg(int count){
		return MessageFormat.format("{0} : 你有 {1} 个事项未处理",title(),count );
	}
	
	protected void onRemind(Map<String,Object> remind){
		
	}
	
	protected String baseCondition(){
		return "";
	}
	
	protected String postCondition(){
		String condition="";
		for(Iterator<String> it=this.parameters.keySet().iterator();it.hasNext();){
			String name=it.next();
			String value=this.parameters.get(name)[0];
			if(StringHelper.isBlank(value))continue;
			if(name.startsWith("qc_like_")){
				String colName=name.substring("qc_like_".length(),name.length());
				condition+=MessageFormat.format(" and {0} like '%{1}%'", colName,value);
			}else if(name.startsWith("qc_eq_")){
				String colName=name.substring("qc_eq_".length(),name.length());
				condition+=MessageFormat.format(" and {0} = '{1}'", colName,value);
			}else if(name.startsWith("qc_in_")){
				String colName=name.substring("qc_in_".length(),name.length());
				condition+=MessageFormat.format(" and {0} in ({1})", colName,value);
			}else if(name.startsWith("qn_eq_")){
				String colName=name.substring("qn_eq_".length(),name.length());
				condition+=MessageFormat.format(" and {0} = {1}", colName,value);
			}else if(name.startsWith("qn_in_")){
				String colName=name.substring("qn_in_".length(),name.length());
				condition+=MessageFormat.format(" and {0} in ({1})", colName,value);
			}else if(name.startsWith("qn_from_")){
				String colName=name.substring("qn_from_".length(),name.length());
				String toName="qn_to_"+colName;
				if(parameters.containsKey(toName)){
					String toValue=this.parameters.get(toName)[0];
					condition+=MessageFormat.format(" and {0} between {1} and {2}", colName,value,toValue);
				}else{
					condition+=MessageFormat.format(" and {0} > {1}", colName,value);
				}
			}
		}
		return condition;
	}
	
	protected String baseOrderby(){
		return "";
	}
	
	protected String postOrderby(){
		
		String orderby="",sort=request.getParameter("sort"),order=request.getParameter("order");
		if(StringHelper.isNotBlank(sort)&&StringHelper.isNotBlank(order)){
		  orderby+= sort+" "+ order;	
		}
		return orderby;
	}
	
	protected Map<String,Object> rowEach(Map<String,Object> row) {
		return row;
	}

	protected String createDataSql(){
		String sql="",sqlPattern="select {0} from {1} {2} {3}";
		String condition="",baseCondition=this.baseCondition(),postCondition=this.postCondition()
			  ,orderby="",baseOrderby=StringHelper.trim(this.baseOrderby(),","),postOrderby=StringHelper.trim(this.postOrderby(),",");
		if(StringHelper.isNotBlank(baseCondition)&&StringHelper.isNotBlank(postCondition)){
			baseCondition=StringHelper.trim(baseCondition, ",","and","or","and");
			postCondition=StringHelper.trim(postCondition, ",","and","or","and");
			condition=MessageFormat.format(" where {0} and ( {1} ) ", baseCondition,postCondition);
		}else if(StringHelper.isNotBlank(baseCondition)){
			baseCondition=StringHelper.trim(baseCondition, ",","and","or","and");
			condition=" where "+baseCondition;
		}else if(StringHelper.isNotBlank(postCondition)){
			postCondition=StringHelper.trim(postCondition, ",","and","or","and");
			condition=" where "+postCondition;
		}
		
		if(StringHelper.isNotBlank(baseOrderby)&&StringHelper.isNotBlank(postOrderby)){
			orderby=MessageFormat.format(" order by {0},{1} ",StringHelper.trim(baseOrderby,","),StringHelper.trim(postOrderby,","));
		}else if(StringHelper.isNotBlank(baseOrderby)){
			orderby=" order by "+StringHelper.trim(baseOrderby,",");
		}else if(StringHelper.isNotBlank(postOrderby)){
			orderby=" order by "+StringHelper.trim(postOrderby,",");
		}
		sql=MessageFormat.format(sqlPattern, 
		  this.columns(),
		  this.viewName(),
		  condition,
		  orderby
		);
		return sql;
	}
	
	
	
	protected String createCountSql(){
		String sql="",sqlPattern="select count(*) from {1} {2} ";
		String condition="",baseCondition=this.baseCondition(),postCondition=this.postCondition()
			  ,orderby="";
		if(StringHelper.isNotBlank(baseCondition)&&StringHelper.isNotBlank(postCondition)){
			condition=MessageFormat.format(" where {0} and ( {1} ) ", baseCondition,postCondition);
		}else if(StringHelper.isNotBlank(baseCondition)){
			condition=" where "+baseCondition;
		}else if(StringHelper.isNotBlank(postCondition)){
			condition=" where "+postCondition;
		}
		
		
		sql=MessageFormat.format(sqlPattern, 
		  this.columns(),
		  this.viewName(),
		  condition,
		  orderby
		);
		return sql;
	}
	
	protected abstract String createPageSql(int start,int limit);
	
	
	public void grid()   {
		Connection connection=createConnection();
		JdbcBean jdbcBean=new JdbcBean(connection);
		int start=this.postStart(),limit=this.postLimit();
		String sql=createPageSql(start, limit);
		Map<String,Object> grid=new HashMap<String, Object>();
		try{
	   LinkedList<Map<String,Object>> rows= jdbcBean.selectList(sql),rowsNew=new LinkedList<Map<String,Object>>();
		
		for(int i=0;i<rows.size();i++){
			rowsNew.add(rowEach(rows.get(i)));
		}
		grid.put(GRID_ROWS_NAME, rowsNew);
		if(rows.size()<limit){
			grid.put(GRID_TOTAL_NAME, rows.size());
		}else{
			String csql=createCountSql();
			int count=jdbcBean.selectInt(csql);
			grid.put(GRID_TOTAL_NAME, count);
		}
		}catch (Exception ex){
			grid.put(EXCEPTION_TYPE_NAME, ex.getClass().getName());
			grid.put(EXCEPTION_MSG_NAME, ex.getMessage());
		}
		JdbcBean.close(connection, null, null);
		writeText(toJson(grid));
		
	}
	
	public void remind() throws Exception  {
		Connection connection=createConnection();
		JdbcBean jdbcBean=new JdbcBean(connection);
		Map<String,Object> remind=new HashMap<String, Object>();
	    String csql=createCountSql();
	    try{
	    int count=jdbcBean.selectInt(csql);
	    String msg=remindMsg(count);
	    remind.put("count", count);
		remind.put("title",this.title());
		remind.put(msg,msg);
		}catch (Exception ex){
			remind.put(EXCEPTION_TYPE_NAME, ex.getClass().getName());
			remind.put(EXCEPTION_MSG_NAME, ex.getMessage());
		}
		JdbcBean.close(connection, null, null);
		onRemind(remind);
		writeText(toJson(remind));
		
	}
	
	public void debug() throws Exception{
		int start=this.postStart(),limit=this.postLimit();
		Map<String,Object> debug=new HashMap<String, Object>();

		try {
			String dsql=this.createDataSql();
			String psql=this.createPageSql(start, limit);
			String csql=this.createCountSql();
			debug.put("dataSql", dsql);
			debug.put("pageSql", psql);
			debug.put("countSql", csql);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			debug.put(EXCEPTION_TYPE_NAME, ex.getClass().getName());
			debug.put(EXCEPTION_MSG_NAME, ex.getMessage());
		}
		writeJavascript(debug.toString());
	}
	
	
	
	public void cache_script(){
		String var=this.parameters.get("var")[0];
		StringBuilder sbrPattern=new StringBuilder("");
		Connection connection=this.createConnection();
		JdbcBean jdbcBean=new JdbcBean(connection);
		String dsql="select * from "+this.viewName();
		LinkedList<Map<String,Object>> list=jdbcBean.selectList(dsql),newList=new LinkedList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
				Map<String,Object> map=list.get(i),newMap=new HashMap<String, Object>();
				String pkvalue=map.get(pk).toString();
				newMap.put("_"+pkvalue, map);
				newList.add(newMap);
		}
		sbrPattern.append(var+"['data']="+toJson(newList)+"; \n");
		writeJavascript(sbrPattern.toString());
	}
	

	
	public String index(){
		return Result.jsp.name();
	}
	
	
	
	public void help() throws Exception{
        Connection connection=createConnection();
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
		.append(fieldset("easyui-datagrid html", "<pre>"+StringHelper.htmlWapper(datagrid.toString())+"</pre>"))
		.append(fieldset("easyui-datagrid js", "<pre>"+StringHelper.htmlWapper(datagrid_js.toString())+"</pre>"))
		.append("</body></html>");
		writeText(html.toString());
		JdbcBean.close(connection, ps, null);
	}
	
	
}
