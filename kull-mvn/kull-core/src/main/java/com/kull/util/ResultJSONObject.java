package com.kull.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;



public class ResultJSONObject extends JSONObject {

	public static int CODE_SUCCESS=0;
	public static int CODE_ALERT=-1;
	public static int CODE_ERROR=-2;
	public static int CODE_EXCEPTION=-3;

	
    public static enum Icon{
    	info,error,question,warning
    }
	
	
	
    public static ResultJSONObject create(Exception ex){
    	ResultJSONObject resultModel=new ResultJSONObject();
    	
    	try {
			resultModel.setAction("alert");
			resultModel.setCode(CODE_EXCEPTION);
	    	resultModel.setMsg(ex.getMessage());
	    	resultModel.setTitle("异常类型:"+ex.getClass().getName());
	    	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return resultModel;
    }
    
    public ResultJSONObject(){	
    	try {
    		this.setEff(0);
			this.setCode(CODE_SUCCESS);
			this.setIcon(Icon.info.name());
	    	this.setTitle("");
	    	this.setMsg("");
	    	this.setAction("show");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
   
    
	public Integer getCode() throws JSONException {
		return this.getInt("code");
	}

	public ResultJSONObject setCode(Integer code) throws JSONException {
		this.put("code", code);
		return this;
	}

	public String getIcon() throws JSONException {
		return this.getString("icon");
	}

	public ResultJSONObject setIcon(String icon) throws JSONException {
		this.put("icon", icon);
		return this;
	}

	public String getTitle() throws JSONException {
		return this.getString("title");
	}

	public ResultJSONObject setTitle(String title) throws JSONException {
		this.put("title", title);
		return this;
	}
	
	public ResultJSONObject setTitle(String pattern,Object...arguments) throws JSONException{
		String title=MessageFormat.format(pattern, arguments);
		return this.setTitle(title);
	}

	public String getMsg() throws JSONException {
		return this.getString("msg");
	}

	public ResultJSONObject setMsg(String msg) throws JSONException {
		this.put("msg", msg);
		return this;
	}
	
	public ResultJSONObject setMsg(String pattern,Object...arguments) throws JSONException{
		String msg=MessageFormat.format(pattern, arguments);
		return this.setMsg(msg);
	}

	public String getAction() throws JSONException {
		return this.getString("action");
	}

	public ResultJSONObject setAction(String action) throws JSONException {
		this.put("action", action);
		return this;
	}

	public int getEff() throws JSONException {
		return this.getInt("eff");
	}

	public ResultJSONObject setEff(int eff) throws JSONException {
		this.put("eff", eff);
		return this;
	}


	

    public boolean isSuccess(){
    	try {
			return this.getCode()==CODE_SUCCESS;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}
    }
}
