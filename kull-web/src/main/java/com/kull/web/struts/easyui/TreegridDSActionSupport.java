package com.kull.web.struts.easyui;



import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.text.MessageFormat;
import java.util.Map;


import com.kull.StringHelper;
import com.kull.bean.JdbcBean;
import com.kull.web.struts.DSActionSupport;

import com.kull.web.Utils;


public abstract class TreegridDSActionSupport extends DSActionSupport {
	public final String _parentIdField="_parentId";

	protected String root;
	
	
	
	public void setRoot(String root) {
		this.root = root;
	}



	@Override
	protected Map<String,Object>  rowEach(Map<String,Object> row)  {
		// TODO Auto-generated method stub
		Object parent=row.get(_parentIdField.toLowerCase());
		if(parent!=null&&!parent.toString().equals(root)){
		    row.put(_parentIdField, parent);
		}
		return row;
	}

	@Override
	public void help() throws Exception{
		super.help();

        StringBuffer html=new StringBuffer("");
        String sql="select * from "+this.viewName()+" where 1=2";
        PreparedStatement ps= connection.prepareStatement(sql);
		ResultSetMetaData rsmd= ps.executeQuery().getMetaData();
        String durl=request.getRequestURI().replace("/help", "/grid");
		
       StringBuffer treegrid=new StringBuffer("");
        
       treegrid.append("<table id=\"table-treegrid\" class=\"easyui-treegrid\" data-options=\"url:'"+durl+"',fit:true,toolbar:'#div-toolbar',idField:'_id',treeField:'_text'\"> \n");
       treegrid.append("<thead><tr> \n");
		for(int i=1;i<=rsmd.getColumnCount();i++){
			String field=rsmd.getColumnLabel(i);
			treegrid.append(MessageFormat.format("\t<th data-options=\"field:''{0}'',width:180,title:''{0}'',editor:''text''\">{0}</th> \n",field));
		}
		treegrid.append("</thead></tr> \n");
		treegrid.append("</table>\n")
		.append("<div id=\"div-toolbar\"></div>")
		;
		html
		.append("<html><body>")
		.append(Utils.fieldset("easyui-treegrid", "<pre>"+StringHelper.htmlWapper(treegrid.toString())+"</pre>"))
		.append("</body></html>");
		this.response.getWriter().write(html.toString());
		JdbcBean.close(connection, ps, null);
	}

	
	
}
