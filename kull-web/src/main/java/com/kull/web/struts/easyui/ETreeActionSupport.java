package com.kull.web.struts.easyui;



import com.kull.web.Utils;
import com.kull.web.struts.AwareActionSupport;
import java.io.IOException;


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
	
	public void createNode() throws IOException{
		String id=this.parameters.get("id")[0];
		String parentId=this.parameters.get("parentId")[0];
		Node node= _createNode(id,parentId);
	  
             Utils.writeJson(response, node);
	}
	

	public void updateNode() throws IOException{
		String id=this.parameters.get("id")[0];
		String text=this.parameters.get("text")[0];
		Node node= _updateNode(id,text);
		
                Utils.writeJson(response, node);
	}
	
	public void destroyNode() throws IOException{
		String id=this.parameters.get("id")[0];
		_destroyNode(id);
	    this.response.getWriter().write("{\"success\":true}");
	}
	
	public void dndNode() throws IOException{
		String id=this.parameters.get("id")[0];
		String targetId=this.parameters.get("targetId")[0];
		String point=this.parameters.get("point")[0];
		Point p=Point.valueOf(point);
		_dndNode(id,targetId,p );
	    this.response.getWriter().write("{\"success\":true}");
	}
	
}
