package com.kull.script;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;

import com.kull.able.Scriptable;






public class JsMap<K,V> extends HashMap<K, V> implements Scriptable{


	
	
	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		return this.put(key, value,false);
	}

	public V put(K key, V value,boolean isStr) {
		// TODO Auto-generated method stub
		return super.put(key,(V) (isStr?"\""+value+"\"":value));
	}

	public String toScirpt() {
		// TODO Auto-generated method stub
		StringBuffer lSbrReturn=new StringBuffer("");
		lSbrReturn.append("{");
		for(Iterator<K> itKey=this.keySet().iterator();itKey.hasNext();)
		{
			K tempKey=itKey.next();
			Object tempObj=this.get(tempKey);
			String tempStr="";
			if(tempObj instanceof Scriptable)
			{
				tempStr=((Scriptable)tempObj).toScirpt();
			}else
			{
				tempStr=tempObj.toString();
			}
			lSbrReturn.append(MessageFormat.format("{0}:{1}", 
				         tempKey,   //0
				         tempStr //1
				         )
		      );
			if(itKey.hasNext())
			{
				lSbrReturn.append(",\n");
			}
			
		}
		lSbrReturn.append("}");
		return lSbrReturn.toString();
	}

}
