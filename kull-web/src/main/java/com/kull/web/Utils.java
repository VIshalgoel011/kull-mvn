/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author lin
 */
public  class Utils {
    
    public static void writeJavascript(HttpServletResponse response,String script) throws IOException{
		
	response.setContentType("text/javascript");
	response.getWriter().write(script);
		
	}

        protected void checkParam(HttpServletRequest request, String... names) throws NullPointerException{
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
}
