package com.kull.easyui;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.kull.struts.AwareActionSupport;
import com.kull.struts.CrudActionSupport;
import com.kull.util.ResultJSONObject;

public abstract class ETreeActionSupport extends AwareActionSupport  {

	public enum Point{
		append,top,bottom
	}
	
	public class Node{
	   public String id,text;
	   
	}
	
	
	protected abstract Node _createNode(String id,String parentId);
	protected abstract Node _updateNode(String id,String text);
	protected abstract void _destroyNode(String id);
	protected abstract void _dndNode(String id,String targetId,Point point);
	
	public void createNode(){
		String id=this.parameters.get("id")[0];
		String parentId=this.parameters.get("parentId")[0];
		Node node= _createNode(id,parentId);
	    writeJavascript(toJson(node));
	}
	

	public void updateNode(){
		String id=this.parameters.get("id")[0];
		String text=this.parameters.get("text")[0];
		Node node= _updateNode(id,text);
		writeJavascript(toJson(node));
	}
	
	public void destroyNode(){
		String id=this.parameters.get("id")[0];
		_destroyNode(id);
	    writeJavascript("{\"success\":true}");
	}
	
	public void dndNode(){
		String id=this.parameters.get("id")[0];
		String targetId=this.parameters.get("targetId")[0];
		String point=this.parameters.get("point")[0];
		Point p=Point.valueOf(point);
		_dndNode(id,targetId,p );
	    writeJavascript("{\"success\":true}");
	}
	
}
