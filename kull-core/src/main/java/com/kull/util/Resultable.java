package com.kull.util;

import java.text.MessageFormat;





public class Resultable  {

	public static int CODE_SUCCESS=0;
	public static int CODE_ALERT=-1;
	public static int CODE_ERROR=-2;
	public static int CODE_EXCEPTION=-3;

    protected Integer code,eff;
    protected String msg,title,icon,action;
	
    public static enum Icon{
    	info,error,question,warning
    }
	
	
	
    public static Resultable create(Exception ex){
    	Resultable resultModel=new Resultable();
    	
    
			resultModel.setAction("alert");
			resultModel.setCode(CODE_EXCEPTION);
	    	resultModel.setMsg(ex.getMessage());
	    	resultModel.setTitle("异常类型:"+ex.getClass().getName());
	    	
		
    	return resultModel;
    }
    
    public Resultable(){	
    
    		this.setEff(0);
			this.setCode(CODE_SUCCESS);
			this.setIcon(Icon.info.name());
	    	this.setTitle("");
	    	this.setMsg("");
	    	this.setAction("show");
		
    	
    }
    
   
    
	
	
	public void setTitle(String pattern,Object...arguments) {
		String title=MessageFormat.format(pattern, arguments);
		 this.setTitle(title);
	}

	
	
	public void setMsg(String pattern,Object...arguments) {
		String msg=MessageFormat.format(pattern, arguments);
		 this.setMsg(msg);
	}

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getEff() {
        return eff;
    }

    public void setEff(Integer eff) {
        this.eff = eff;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

	


	

    public boolean isSuccess(){
    	
			return this.getCode()==CODE_SUCCESS;
		
    }
}
