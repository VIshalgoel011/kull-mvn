/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web.struts.easyui;

import com.kull.Stringz;
import com.kull.orm.Session;

import com.kull.script.Html;

import com.kull.script.JsMap;
import com.kull.web.Utils;
import com.kull.web.struts.DSActionSupport;
import com.kull.web.struts.SqlDSActionSupport;
import java.io.IOException;
import java.sql.SQLException;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author lin
 */
public abstract class ComboDSActionSupport extends SqlDSActionSupport{
    
    public final String ordernoField="_orderno",textField="_text",valueField="_code";

    
        protected String pk;

    public void setPk(String pk) {
        this.pk = pk;
    }
        
        
    
	
	protected String viewName() {
		// TODO Auto-generated method stub
		return "combo_"+this.pk;
	}

	

	
	
	
	@Override
	protected String baseOrderby() {
		// TODO Auto-generated method stub
		return MessageFormat.format("{0} asc", this.ordernoField);
	}

	public void rows() throws IOException, SQLException{
		
		Session session=new Session(connection);
		
		String sql=createDataSql();
		List<Map<String,Object>> list=session.query(sql);
		Session.close(connection, null, null);
	        Utils.writeJson(this.response,list);

                
	}
	
	@Override
	public void help() throws Exception{
        if(Stringz.isBlank(this.pk)){
        	this.response.getWriter().write("缺少pk参数");
        	return;
        }
		Session session=new Session(connection);
        StringBuffer html=new StringBuffer("");
        String sql="select * from "+this.viewName();
        List<Map<String,Object>> list=session.query(sql);
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
       
		combobox_url.append("html:\n\n <input  name=\""+this.pk+"\" class=\""+EasyUiPlugin.ComboBox.CLASS+"\" style=\"\" data-options=\""+Stringz.trim(opts.toScirpt(),"{","}")+"\" /> \n\n")
		.append("editor:\n\n {type:\"combobox\",options:"+opts.toScirpt()+"}");
		
		opts.remove(EasyUiPlugin.ComboBox.Properties.URL);
		opts.put(EasyUiPlugin.ComboBox.Properties.DATA, Utils.toJson(list));
		combobox_data.append("<input  name=\""+this.pk+"\" class=\""+EasyUiPlugin.ComboBox.CLASS+"\" style=\"\" data-options=\""+Stringz.trim(opts.toScirpt(),"{","}")+"\" /> \n\n")
		.append("editor:\n\n {type:\"combobox\",options:"+opts.toScirpt()+"}");
		
		opts.remove(EasyUiPlugin.ComboBox.Properties.DATA);
		combogrid_url.append("<input  name=\""+this.pk+"\" class=\""+EasyUiPlugin.ComboGrid.CLASS+"\" style=\"\" data-options=\""+Stringz.trim(opts.toScirpt(),"{","}")+"\" /> \n\n")
		.append("editor:\n\n {type:\"combogrid\",options:"+opts.toScirpt()+"}");
		
		opts.remove(EasyUiPlugin.ComboBox.Properties.URL);
		opts.put(EasyUiPlugin.ComboBox.Properties.DATA, Utils.toJson(list));
		combogrid_data.append("<input  name=\""+this.pk+"\" class=\""+EasyUiPlugin.ComboGrid.CLASS+"\" style=\"\" data-options=\""+Stringz.trim(opts.toScirpt(),"{","}")+"\" /> \n\n")
		.append("editor:\n\n {type:\"combogrid\",options:"+opts.toScirpt()+"}");
		html
		.append("<html><body>")
		.append(Html.fieldset("easyui-combobox  url", "<pre>"+Stringz.htmlWapper(combobox_url.toString())+"</pre>"))
		.append(Html.fieldset("easyui-combobox  data", "<pre>"+Stringz.htmlWapper(combobox_data.toString())+"</pre>"))
		.append(Html.fieldset("easyui-combogrid  url", "<pre>"+Stringz.htmlWapper(combogrid_url.toString())+"</pre>"))
		.append(Html.fieldset("easyui-combogrid  data", "<pre>"+Stringz.htmlWapper(combogrid_data.toString())+"</pre>"))
		//.append(fieldset("easyui-treegrid", "<pre>"+StringHelper.htmlWapper(treegrid.toString())+"</pre>"))
		.append("</body></html>");
		this.response.getWriter().write(html.toString());
		Session.close(connection, null, null);
	}
}
