package com.kull.script;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;

import com.kull.ObjectHelper;
import com.kull.able.Scriptable;





public class JQuery implements Scriptable {

	
	private String object;
	private String method;
	private ArrayList<Object> parmas=new ArrayList<Object>();
	
	public JQuery(String method)
	{
		this.object="$";
		this.method=method;
	}
	
	public JQuery(String object,String method)
	{
		this.object=object;
		this.method=method;
	}
	
	public String toScirpt() {
		// TODO Auto-generated method stub
		String lStrRege="{0}.{1}(\n {2} \n);  // end:$(\"{0}\").{1}";
		String lStrReturn=MessageFormat.format(lStrRege, 
				this.object,   //0
				this.method,  //1
				this.getParmas() //2
				);
		return lStrReturn;
	}
	
	public JQuery appendParma(Object parma)
	{
		return this.appendParma(parma,false);
	}
	
	public JQuery appendParma(Object parma,boolean isStr)
	{
		String tempStr="";
		if(parma instanceof Scriptable)
		{
			tempStr=((Scriptable)parma).toScirpt();
		}else
		{
			tempStr=parma.toString();
		}
		if(isStr)
		{
			tempStr="\""+tempStr+"\"";
		}
		this.parmas.add(tempStr);
		return this;
	}
	
	public JQuery appendParma(String pattern,Object...arguments)
	{
		return this.appendParma(MessageFormat.format(pattern, arguments));
	}
	
	
	private String getParmas()
	{
		StringBuffer lSbrReturn=new StringBuffer("");
		for(Iterator<Object> it=this.parmas.iterator();it.hasNext();)
		{
			Object lStrTemp=it.next();
			lSbrReturn.append(lStrTemp.toString());
			if(it.hasNext())
			{
				lSbrReturn.append(",\n");
			}
		}
		return lSbrReturn.toString();
	}

	
	public final static String DOC_READY_START="$(document).ready(function(){ \n\n";
	public final static String DOC_READY_END="}); //$(document).ready";
	

	
	public final static class Events{
		public final static String CLICK="click";
	}
	
	public static final class Properties{
		public final static String HTML="html";
	}
	
	public static final class Ajax{
		public final static String GET="get";
	}
	
	public static String id(String id){
		return MessageFormat.format("$(\"#{0}\")", ObjectHelper.<String>valueOf(id,""));
	}
	
	public static String cls(String id){
		return MessageFormat.format("$(\".{0}\")", ObjectHelper.<String>valueOf(id,""));
	}
	
	public static String regexp(String regexp){
		return MessageFormat.format("$(\"{0}\")", ObjectHelper.<String>valueOf(regexp,""));
	}
	
	
}
