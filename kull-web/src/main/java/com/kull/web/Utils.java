/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web;


import com.kull.ObjectHelper;
import com.kull.script.JsContext;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


/**
 *
 * @author lin
 */
public  class Utils {
    
    public enum ContentType{
		text("text/plain; charset=UTF-8"),
		html("text/html; charset=UTF-8"),
                json("text/json; charset=UTF-8"),
                javascript("text/javascript; charset=UTF-8"),
		img("image; charset=UTF-8"),
		pdf("application/pdf"),
		doc("application/msword"),
		excel("application/vnd.ms-excel")
		;
		
		String contextType;
		ContentType(String context){
			this.contextType=context;
		}

        public String getContextType() {
            return contextType;
        }
	}
	
	public  enum Method{
    	get,post,put,delete
    }
    
    public static void writeJavascript(HttpServletResponse response,String script) throws IOException{
		
	response.setContentType(ContentType.javascript.getContextType());
	response.getWriter().write(script);
		
	}
    
     public static void writeJson(HttpServletResponse response,Object obj) throws IOException{
		
	response.setContentType(ContentType.json.getContextType());
	response.getWriter().write(toJson(obj).toString());
		
	}
     
       public static JSON toJson(Object obj) throws IOException{
		
	return JSONSerializer.toJSON(obj);
		
	}
    
       

        public static void checkParam(HttpServletRequest request, String... names) throws NullPointerException{
                boolean isNotNull=true;
                Set<String> nullkeys = new HashSet<String>();
                for(String name : names){
                  String val=request.getParameter(name);
                  if(val==null){
                      nullkeys.add(val);
                  }
                }
                if(!nullkeys.isEmpty()){
                    String errinfo="these param is null:";
                    for(String nullkey :nullkeys){
                       errinfo+=nullkey+ ",";
                    }
                    throw new NullPointerException(errinfo);
                }
                
	}
    
        
        public static Integer[] paramInt(HttpServletRequest request,String... names) throws NumberFormatException{
                checkParam(request, names);
                Integer[] res=new Integer[names.length];
                for(int i=0;i<names.length;i++){
                   res[i]=Integer.valueOf(request.getParameter(names[i]));
                }
		return res;
	}
	
	
	
	public static Float[] paramFloat(HttpServletRequest request,String... names) throws NumberFormatException{
                checkParam(request, names);
                Float[] res=new Float[names.length];
                for(int i=0;i<names.length;i++){
                   res[i]=Float.valueOf(request.getParameter(names[i]));
                }
		return res;
	}
	
	
	
	public static Double[] paramDouble(HttpServletRequest request,String... names) throws NumberFormatException{
                checkParam(request, names);
                Double[] res=new Double[names.length];
                for(int i=0;i<names.length;i++){
                   res[i]=Double.valueOf(request.getParameter(names[i]));
                }
		return res;
	}
	
	
	
	
        

        
        
        public static boolean isPost(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		return Method.post.name().equals(request.getMethod().toLowerCase());
	}

	public static boolean isGet(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return Method.get.name().equals(request.getMethod().toLowerCase());
	}
	
	public static boolean alert(HttpServletResponse response,String msg){
		StringBuffer context=new StringBuffer("");
		context.append(JsContext.SCRIPT_START);
		context.append("alert(\""+msg+"\");");
		context.append(JsContext.SCRIPT_END);
		try {
			response.getWriter().write(context.toString());
		} catch (IOException e) {return false;}
		return true;
	}
	
	public static boolean confirm(HttpServletResponse response,String msg,String scriptYes,String scriptNo){
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
        
        public static String webRoot(HttpServletRequest request){
		
                int port=request.getServerPort();
                if(port==80){
                  return request.getScheme()+"://"+request.getServerName()+request.getContextPath();
                }
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	}
        
        
        
        public static <T>  T evalParameterModel(HttpServletRequest request,T t,String prefix,String subfix) {
		return evalParameterModel(request,t, prefix, subfix, true);
	}
	
	public static <T>  T evalParameterModel(HttpServletRequest request,T t,String prefix,String subfix,boolean isRewrite) {
	
			 for(Iterator<String> it=request.getParameterMap().keySet().iterator();it.hasNext(); ){
				 
				 String key=it.next();
				 String name=key.substring(key.indexOf(prefix)+prefix.length(),key.lastIndexOf(subfix));
				 String value=request.getParameter(name);
				 try{
				 if(!isRewrite){
					 continue;
				 }
				 t= (T) ObjectHelper.attr(t,name,value);
				 }catch(Exception ex){}
			 }
	
		return t;
		
	}
}
