package com.kull.script;

import java.util.ArrayList;
import java.util.Iterator;

import com.kull.able.Scriptable;




public class JsList<E> extends ArrayList<E> implements Scriptable {

	
	

	public String toScirpt() {
		// TODO Auto-generated method stub
		StringBuffer lSbrReturn=new StringBuffer("");
		lSbrReturn.append("[ \n");
        for(Iterator it=this.iterator();it.hasNext();){
			Object tempObj=it.next();
        	if((tempObj instanceof JsMap) ||( tempObj instanceof JsList) )
			{
			  lSbrReturn.append(((Scriptable)tempObj).toScirpt()).append("\n");
			}

        	if(it.hasNext())
        	{
        		lSbrReturn.append(", \n");
        	}
		}
		lSbrReturn.append("] \n");
		return lSbrReturn.toString();
	}

}
