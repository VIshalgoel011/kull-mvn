package com.kull.js;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;

import com.kull.util.IScript;





public class JsContext implements IScript {

	public static final String SCRIPT_START = null;
	public static final String SCRIPT_END = null;
	private ArrayList<String> scripts;
	
	public JsContext(){
		scripts=new ArrayList<String>();
	}
	
	public JsContext(ArrayList<String> scripts){
		if(scripts==null)
		{
			this.scripts=new ArrayList<String>();
		}else
			this.scripts=scripts;
	}
	
	public JsContext appendScript(Object script)
	{
		if(script==null) return this;
		if(script instanceof IScript)
		{
			this.scripts.add(((IScript) script).toScirpt());
		}else
		{
			this.scripts.add(script.toString());
		}
		return this;
	}
	
	public JsContext appendScript(String pattern,Object...arguments){
		this.scripts.add(MessageFormat.format(pattern, arguments));
		return this;
	}
	
	public String toScirpt() {
		// TODO Auto-generated method stub
		StringBuffer lSbrScript=new StringBuffer("");
		for(Iterator<String> it=scripts.iterator();it.hasNext();)
		{
			String tempScript=it.next();
			String script=MessageFormat.format("\n\t{0}\n", tempScript);
		    lSbrScript.append(script);
		}
		return lSbrScript.toString();
	}

}
