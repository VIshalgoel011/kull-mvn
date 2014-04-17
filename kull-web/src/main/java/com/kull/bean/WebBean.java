package com.kull.bean;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kull.ObjectHelper;
import com.kull.script.JsContext;





public class WebBean {

	public enum ContentType{
		text("text/plain; charset=UTF-8"),
		html("text/html; charset=UTF-8"),
		img("image; charset=UTF-8"),
		pdf("application/pdf"),
		doc("application/msword"),
		excel("application/vnd.ms-excel")
		;
		
		String context;
		ContentType(String context){
			this.context=context;
		}
	}
	
	public  enum Method{
    	get,post,put
    }
	
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public WebBean(HttpServletRequest request,HttpServletResponse response){
		this.request=request;
		this.response=response;
	}
	
	public  String getWebRootPath(){
		
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	}
	
	public  <T> T getParameter(String key,Class<T> cls) {
		// TODO Auto-generated method stub
		T t=null;
		if(key==null){
			return t;
		}
		String value= request.getParameter(key);
        return ObjectHelper.<T>parse(value, null,cls);
	}
	
	public   <T>  T evalParameterModel(T t,String prefix,String subfix) {
		return evalParameterModel(t, prefix, subfix, true);
	}
	
	public   <T>  T evalParameterModel(T t,String prefix,String subfix,boolean isRewrite) {
	
			 for(Iterator<String> it=request.getParameterMap().keySet().iterator();it.hasNext(); ){
				 
				 String key=it.next();
				 String name=key.substring(key.indexOf(prefix)+prefix.length(),key.lastIndexOf(subfix));
				 String value=getParameter(key,String.class);
				 try{
				 if(!isRewrite){
					 continue;
				 }
				 t= (T) ObjectHelper.attr(t,name,value);
				 }catch(Exception ex){}
			 }
	
		return t;
		
	}
	
	public  boolean isPost() {
		// TODO Auto-generated method stub
		
		return Method.post.name().equals(request.getMethod().toLowerCase());
	}

	public  boolean isGet() {
		// TODO Auto-generated method stub
		return Method.get.name().equals(request.getMethod().toLowerCase());
	}
	
	public boolean alert(String msg){
		StringBuffer context=new StringBuffer("");
		context.append(JsContext.SCRIPT_START);
		context.append("alert(\""+msg+"\");");
		context.append(JsContext.SCRIPT_END);
		try {
			response.getWriter().write(context.toString());
		} catch (IOException e) {return false;}
		return true;
	}
	
	public boolean confirm(String msg,String scriptYes,String scriptNo){
		StringBuffer context=new StringBuffer("");
		context.append(JsContext.SCRIPT_START)
		.append("if(confirm(\""+msg+"\")")
		.append("'{' "+scriptYes+" '}'")
		.append("else '{' "+scriptNo+" '}'")
		.append(JsContext.SCRIPT_END);
		try {
			response.getWriter().write(context.toString());
		} catch (IOException e) {return false;}
		return true;
	}
}
