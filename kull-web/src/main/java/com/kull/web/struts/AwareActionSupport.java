/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web.struts;

import com.opensymphony.xwork2.ActionSupport;
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


/**
 *
 * @author lin
 */
public class AwareActionSupport extends ActionSupport implements ServletRequestAware,ServletResponseAware,SessionAware,
ApplicationAware,CookiesAware,ParameterAware,PrincipalAware {
    
    	protected HttpServletResponse response;
	protected HttpServletRequest request;
	protected Map<String, String> cookies;
	protected Map<String, Object> session,application;
	protected Map<String, String[]> parameters;
	protected PrincipalProxy principalProxy;
	
    
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
    
        	@Override
	public void setPrincipalProxy(PrincipalProxy principalProxy) {
		// TODO Auto-generated method stub
		this.principalProxy=principalProxy;
	}

	protected String basePath(){
           return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); 
        }
}
