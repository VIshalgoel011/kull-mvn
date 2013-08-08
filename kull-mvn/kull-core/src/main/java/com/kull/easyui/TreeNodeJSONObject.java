package com.kull.easyui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TreeNodeJSONObject extends JSONObject {

	public static enum Tree{
		id,text,_parentId,iconCls,children,attributes,state,checked
	}
	
	public String getId()  {
		try {
			return this.getString(Tree.id.name());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

	public TreeNodeJSONObject setId(String id) throws JSONException {
		this.put(Tree.id.name(), id);
		return this;
	}

	public String getText() {
		try {
			return this.getString(Tree.text.name());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

	public TreeNodeJSONObject setText(String text) throws JSONException {
		this.put(Tree.text.name(), text);
		return this;
	}

	public String get_parentId()  {
		try {
			return this.getString(Tree._parentId.name());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

	public TreeNodeJSONObject set_parentId(String _parentId)  throws JSONException{
		this.put(Tree._parentId.name(), _parentId);
		return this;
	}

	public String getIconCls()  {
		try {
			return this.getString(Tree.iconCls.name());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

	public TreeNodeJSONObject setIconCls(String iconCls)  throws JSONException{
		this.put(Tree.iconCls.name(), iconCls);
		return this;
	}

	public JSONArray getChildren() throws JSONException {
		try {
			return this.getJSONArray(Tree.children.name());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return new JSONArray();
		}
	}

	public TreeNodeJSONObject setChildren(JSONArray children) throws JSONException {
		this.put(Tree.children.name(), children);
		return this;
	}

	public JSONObject getAttributes() {
		try {
			return this.getJSONObject(Tree.attributes.name());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return new JSONObject();
		}
	}

	public TreeNodeJSONObject setAttributes(JSONObject attributes)  throws JSONException{
		this.put(Tree.attributes.name(), attributes);
		return this;
	}

	public String getState()  {
		try {
			return this.getString(Tree.state.name());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}

	public TreeNodeJSONObject setState(String state) throws JSONException {
		this.put(Tree.state.name(), state);
		return this;
	}

	public boolean getChecked()  {
		try {
			return this.getBoolean(Tree.checked.name());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

	public TreeNodeJSONObject setChecked(String checked)  throws JSONException{
		this.put(Tree.checked.name(),checked);
		return this;
	}
	
	public static <T> List<TreeNodeJSONObject> toNodes(List<T> list,ITreeNodeable<T>... nodeables ){
		List<TreeNodeJSONObject> treenodes=new ArrayList<TreeNodeJSONObject>();
		if(nodeables.length>1){
			List<TreeNodeJSONObject> tempTreeNodes=new ArrayList<TreeNodeJSONObject>();
			
		}
	    else if(nodeables.length==1){
			
		}
		return treenodes;
	}
	
	
	public interface ITreeNodeable<T>{
	
		public List<TreeNodeJSONObject> findNodes(List<T> list,TreeNodeJSONObject parent);
	}
}
