package com.kull.js;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kull.util.IScript;




public class JsList<E> extends ArrayList<E> implements IScript {

	
	

	public String toScirpt() {
		// TODO Auto-generated method stub
		StringBuffer lSbrReturn=new StringBuffer("");
		lSbrReturn.append("[ \n");
        for(Iterator it=this.iterator();it.hasNext();){
			Object tempObj=it.next();
        	if((tempObj instanceof JsMap) ||( tempObj instanceof JsList) )
			{
			  lSbrReturn.append(((IScript)tempObj).toScirpt()).append("\n");
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
