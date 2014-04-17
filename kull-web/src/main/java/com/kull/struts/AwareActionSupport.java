package com.kull.struts;

import java.io.IOException;

import java.text.MessageFormat;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.CookiesAware;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.PrincipalAware;
import org.apache.struts2.interceptor.PrincipalProxy;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.ServletConfigAware;




import com.opensymphony.xwork2.ActionSupport;


public abstract class AwareActionSupport extends ActionSupport implements ServletRequestAware,ServletResponseAware,SessionAware,
ApplicationAware,CookiesAware,ParameterAware,PrincipalAware,ServletConfigAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3747211127970837816L;

	public static enum Result{
    	jsp,html,js,css
    }
 	public final String GRID_TOTAL_NAME="totle",GRID_ROWS_NAME="rows"
 		,EXCEPTION_TYPE_NAME="excType"	,EXCEPTION_MSG_NAME="excMsg",REMIND_MSG_NAME="msg"
 		,REMIND_URL_NAME="url",REMIND_TITLE_NAME="title",REMIND_COUNT_NAME="count"
 		;	
 			;

	protected HttpServletResponse response;
	protected HttpServletRequest request;
	protected Map<String, String> cookies;
	protected Map<String, Object> session,application;
	protected Map<String, String[]> parameters;
	protected PrincipalProxy principalProxy;
	protected ServletConfig servletConfig;
	protected String pk;
	public void setPk(String pk) {
		this.pk=pk;
	}
	
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request=arg0;
	}
	@Override
	public void setCookiesMap(Map<String, String> cookies) {
		// TODO Auto-generated method stub
		this.cookies=cookies;
	}
	@Override
	public void setApplication(Map<String, Object> application) {
		// TODO Auto-generated method stub
		this.application=application;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session=session;
	}
	@Override
	public void setParameters(Map<String, String[]> parameters) {
		// TODO Auto-generated method stub
		this.parameters=parameters;
	}
	
	protected void writeText(String text){
		try {
			this.response.getWriter().write(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void writeJavascript(String text){
		try{
			this.response.setContentType("text/javascript");
			this.response.getWriter().write(text);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeBytes(byte[] bs){
		try {
			this.response.getOutputStream().write(bs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void setPrincipalProxy(PrincipalProxy principalProxy) {
		// TODO Auto-generated method stub
		this.principalProxy=principalProxy;
	}
	
	protected Integer paramInt(String name) throws NullPointerException,NumberFormatException{
		Integer i=paramInt(name,null);
		if(i==null)throw new NullPointerException();
		return i;
	}
	
	protected Integer paramInt(String name,Integer defaultVal) throws NumberFormatException{
		if(!hasParam(name))return defaultVal;
		String[] vals=parameters.get(name);
		
		return Integer.parseInt(vals[0]);
	}
	
	protected Float paramFloat(String name) throws NullPointerException,NumberFormatException{
		Float i=paramFloat(name,null);
		if(i==null)throw new NullPointerException();
		return i;
	}
	
	protected Float paramFloat(String name,Float defaultVal) throws NumberFormatException{
		if(!hasParam(name))return defaultVal;
		String[] vals=parameters.get(name);
		
		return Float.parseFloat(vals[0]);
	}
	
	protected Double paramDouble(String name) throws NullPointerException,NumberFormatException{
		Double i=paramDouble(name,null);
		if(i==null)throw new NullPointerException();
		return i;
	}
	
	protected Double paramDouble(String name,Double defaultVal) throws NumberFormatException{
		if(!hasParam(name))return defaultVal;
		String[] vals=parameters.get(name);
		
		return Double.parseDouble(vals[0]);
	}
	
	protected String paramString(String name) throws NullPointerException{
		String i=paramString(name,null);
		if(i==null)throw new NullPointerException();
		return i;
	}
	
	protected String paramString(String name,String defaultVal) {
		if(!hasParam(name))return defaultVal;
		String[] vals=parameters.get(name);
		
		return vals[0];
	}
	
	protected boolean hasParam(String name){
		String[] vals=parameters.get(name);
		return vals!=null&&vals.length>0;
	}
	
	@Override
	public void setServletConfig(ServletConfig servletConfig) {
		// TODO Auto-generated method stub
		this.servletConfig=servletConfig;
	}
	
	public static String fieldset(String legend, String context){
		return MessageFormat.format("<fieldset><legend>{0}</legend>{1}</fieldset>", legend,context);
	}
    
	protected abstract String toJson(Object obj);
}
