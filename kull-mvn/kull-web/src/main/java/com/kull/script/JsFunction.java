package com.kull.script;

import java.text.MessageFormat;
import java.util.Iterator;


import com.kull.ObjectHelper;
import com.kull.util.IScript;



public class JsFunction implements IScript {

	private StringBuffer parmas=new StringBuffer("");
	private StringBuffer context=new StringBuffer("");
	private String funName="";
	
	public JsFunction(String[] parmas){

		if (parmas!=null){
			for(int i=0;i<parmas.length;i++)
			{
				this.parmas.append(parmas[i]);
				if(i!=parmas.length-1)
				{
					this.parmas.append(",");
				}
			}
		}
	}
	
	public JsFunction(String funName, String[] parmas){

		if (parmas!=null){
			for(int i=0;i<parmas.length;i++)
			{
				this.parmas.append(parmas[i]);
				if(i!=parmas.length-1)
				{
					this.parmas.append(",");
				}
			}
		}
		this.funName=ObjectHelper.parse(funName,"");
	}
	
	public String toScirpt() {
		// TODO Auto-generated method stub
		String lStrRege="function {0}({1})'{' \n  {2}  '}'";
		String lStrReturn=MessageFormat.format(lStrRege,
				          this.funName,  //0
		                  this.parmas.toString(),  //1
		                  this.context.toString()  //2
		);
		return lStrReturn;
	}
	
	public JsFunction appendContext(String pContext)
	{
		this.context.append(pContext).append("\n");
		return this;
	}
	
	public JsFunction appendContext(IScript pContext)
	{
		this.context.append(pContext.toScirpt()).append("\n");
		return this;
	}
	
	public JsFunction appendContext(String pattern,Object...arguments){
		this.context.append(MessageFormat.format(pattern, arguments));
		return this;
	}

	
	
	
	public String getFunName() {
		return funName;
	}
	
	
	public static String iframe(String url)
	{
		return "<iframe scrolling='no' frameborder='0' src='"+url+"' style='width:100%;height:100%;overflow:hidden;'></iframe>";
	}
	
	

	
}
