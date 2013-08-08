package com.kull.easyui;

import java.sql.ResultSet;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class DatagridResultObject extends JSONObject {

	public DatagridResultObject(){
		this.setTotal(0);
		this.setRows(new JSONArray());
	}
	
	public void setTotal(int total){
		try {
			this.put("total", total);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void setRows(Collection list){
          this.setRows(new JSONArray(list));
	}
	
	
	
	public void setRows(JSONArray jarr){
		try {
			this.put("rows", jarr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setFooter(Collection list){
		this.setFooter(new JSONArray(list));
	}
	
	public void setFooter(JSONArray jarr){
		try {
			this.put("footer", jarr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
