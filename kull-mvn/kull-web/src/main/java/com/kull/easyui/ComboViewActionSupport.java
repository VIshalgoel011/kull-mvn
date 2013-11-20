package com.kull.easyui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



import com.kull.EasyUiUtil;
import com.kull.StringHelper;
import com.kull.bean.JdbcBean;
import com.kull.easyui.EasyUiPlugin.ComboBox;
import com.kull.jdbc.Dialect;
import com.kull.js.JsMap;

import com.kull.struts.DialectViewActionSupport;
import com.kull.struts.SimpleViewActionSupport;

public abstract class ComboViewActionSupport extends SimpleViewActionSupport{

	public final String ordernoField="_orderno",textField="_text",valueField="_code";

	@Override
	protected String viewName() {
		// TODO Auto-generated method stub
		return "combo_"+this.pk;
	}

	

	
	
	
	@Override
	protected String baseOrderby() {
		// TODO Auto-generated method stub
		return MessageFormat.format("{0} asc", this.ordernoField);
	}

	public void rows(){
		Connection connection=createConnection();
		JdbcBean jdbcBean=new JdbcBean(connection);
		
		String sql=createDataSql();
		LinkedList<Map<String,Object>> list=jdbcBean.selectList(sql);
		JdbcBean.close(connection, null, null);
		writeText(toJson(list));
	}
	
	@Override
	public void help() throws Exception{
        if(StringHelper.isBlank(this.pk)){
        	writeText("缺少pk参数");
        	return;
        }
		
		Connection connection=createConnection();
		JdbcBean jdbcBean=new JdbcBean(connection);
        StringBuffer html=new StringBuffer("");
        String sql="select * from "+this.viewName();
        List<Map<String,Object>> list=jdbcBean.selectList(sql);
        String url=request.getRequestURI().substring(request.getContextPath().length());
        
		StringBuffer combobox_url=new StringBuffer(""),combobox_data=new StringBuffer("")
		,combogrid_url=new StringBuffer(""),combogrid_data=new StringBuffer("");
        String cburl=url.replace("/help", "/rows");
        String cgurl=url.replace("/help", "/grid");
        JsMap<String, Object> opts=new JsMap<String, Object>();
        opts.put(EasyUiPlugin.ComboBox.Properties.TEXT_FIELD, this.textField,true);
        opts.put(EasyUiPlugin.ComboBox.Properties.VALUE_FIELD, this.valueField,true);
        opts.put(EasyUiPlugin.ComboBox.Properties.REQUIRED, true);
        opts.put(EasyUiPlugin.ComboBox.Properties.MULTIPLE, false);
        opts.put(EasyUiPlugin.ComboBox.Properties.URL, cburl,true);
       
		combobox_url.append("html:\n\n <input  name=\""+this.pk+"\" class=\""+EasyUiPlugin.ComboBox.CLASS+"\" style=\"\" data-options=\""+StringHelper.trim(opts.toScirpt(),"{","}")+"\" /> \n\n")
		.append("editor:\n\n {type:\"combobox\",options:"+opts.toScirpt()+"}");
		
		opts.remove(EasyUiPlugin.ComboBox.Properties.URL);
		opts.put(EasyUiPlugin.ComboBox.Properties.DATA, toJson(list));
		combobox_data.append("<input  name=\""+this.pk+"\" class=\""+EasyUiPlugin.ComboBox.CLASS+"\" style=\"\" data-options=\""+StringHelper.trim(opts.toScirpt(),"{","}")+"\" /> \n\n")
		.append("editor:\n\n {type:\"combobox\",options:"+opts.toScirpt()+"}");
		
		opts.remove(EasyUiPlugin.ComboBox.Properties.DATA);
		combogrid_url.append("<input  name=\""+this.pk+"\" class=\""+EasyUiPlugin.ComboGrid.CLASS+"\" style=\"\" data-options=\""+StringHelper.trim(opts.toScirpt(),"{","}")+"\" /> \n\n")
		.append("editor:\n\n {type:\"combogrid\",options:"+opts.toScirpt()+"}");
		
		opts.remove(EasyUiPlugin.ComboBox.Properties.URL);
		opts.put(EasyUiPlugin.ComboBox.Properties.DATA, toJson(list));
		combogrid_data.append("<input  name=\""+this.pk+"\" class=\""+EasyUiPlugin.ComboGrid.CLASS+"\" style=\"\" data-options=\""+StringHelper.trim(opts.toScirpt(),"{","}")+"\" /> \n\n")
		.append("editor:\n\n {type:\"combogrid\",options:"+opts.toScirpt()+"}");
		html
		.append("<html><body>")
		.append(fieldset("easyui-combobox  url", "<pre>"+StringHelper.htmlWapper(combobox_url.toString())+"</pre>"))
		.append(fieldset("easyui-combobox  data", "<pre>"+StringHelper.htmlWapper(combobox_data.toString())+"</pre>"))
		.append(fieldset("easyui-combogrid  url", "<pre>"+StringHelper.htmlWapper(combogrid_url.toString())+"</pre>"))
		.append(fieldset("easyui-combogrid  data", "<pre>"+StringHelper.htmlWapper(combogrid_data.toString())+"</pre>"))
		//.append(fieldset("easyui-treegrid", "<pre>"+StringHelper.htmlWapper(treegrid.toString())+"</pre>"))
		.append("</body></html>");
		writeText(html.toString());
		JdbcBean.close(connection, null, null);
	}
}
