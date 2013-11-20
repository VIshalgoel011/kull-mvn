package com.kull.struts;



import java.io.Serializable;
import java.util.Date;







import com.kull.StringHelper;
import com.kull.bean.WebBean;
import com.kull.util.ResultJSONObject;


import com.opensymphony.xwork2.ModelDriven;


public abstract class CrudActionSupport<M extends Serializable> extends AwareActionSupport implements ModelDriven<M> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private M m;
	
	
	
	protected abstract M readByPk(String pk);
	
	protected abstract M newModel();
	
	protected abstract void _insert(M m) throws Exception;
	protected abstract void _update(M m) throws Exception;
	protected abstract void _delete(M m) throws Exception;
	
	
	 protected boolean onCreate(M m,ResultJSONObject resultJSONObject){return true;}
		protected  boolean onUpdate(M m,M form,ResultJSONObject resultJSONObject){return true;}
		protected  boolean onDelete(M m,ResultJSONObject resultJSONObject){return true;}

	@Override
	public M getModel() {
		// TODO Auto-generated method stub
		return m;
	}


   public boolean hasPk(){
	   return StringHelper.isNotBlank(pk);
   }
	
   protected M refForm(M m){
	   WebBean webBean=new WebBean(request,response);
	   return webBean.evalParameterModel(m, "", "");
   }

	public String edit(){
		
		return Result.jsp.toString();
	};
	
   
    
    protected void onCreateSuccess(M m,ResultJSONObject resultJSONObject){
    	
    	try {
			resultJSONObject.setMsg("{0} 成功创建一条数据",new Date().toString());
			resultJSONObject.setCode(ResultJSONObject.CODE_SUCCESS);
			resultJSONObject.setIcon(ResultJSONObject.Icon.info.name());
	    	resultJSONObject.setTitle("创建成功");
	    	resultJSONObject.setEff(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    protected void onCreateError(M m,ResultJSONObject resultJSONObject,Exception ex){
    	
    	try {
			resultJSONObject.setMsg("{0} {1}",new Date().toString(),ex.getLocalizedMessage());
			resultJSONObject.setCode(ResultJSONObject.CODE_EXCEPTION);
			resultJSONObject.setIcon(ResultJSONObject.Icon.error.name());
	    	resultJSONObject.setTitle("创建异常");
	    	resultJSONObject.setEff(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
	public void create(){
		ResultJSONObject resultJSONObject=new ResultJSONObject();
		WebBean webEntity=new WebBean(request, response);
		
		try{
			M m=newModel();
			 
			m=webEntity.evalParameterModel(m, "","");
		if(onCreate(m,resultJSONObject)){
			_insert(m);
			onCreateSuccess(m,resultJSONObject);
		}
		}catch(Exception ex){
			onCreateError(m,resultJSONObject,ex);
		}
		writeText(toJson(resultJSONObject));
	}
	   
	
	protected void onUpdateSuccess(M m,ResultJSONObject resultJSONObject){
		try {
			resultJSONObject.setMsg("{0} 成功保存一条数据",new Date().toString());
			resultJSONObject.setCode(ResultJSONObject.CODE_SUCCESS);
			resultJSONObject.setIcon(ResultJSONObject.Icon.info.name());
	    	resultJSONObject.setTitle("保存成功");
	    	resultJSONObject.setEff(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	    
	protected void onUpdateError(M m,ResultJSONObject resultJSONObject,Exception ex){
		try {
			resultJSONObject.setMsg("{0} {1}",new Date().toString(),ex.getLocalizedMessage());
			resultJSONObject.setCode(ResultJSONObject.CODE_EXCEPTION);
			resultJSONObject.setIcon(ResultJSONObject.Icon.error.name());
	    	resultJSONObject.setTitle("保存异常");
	    	resultJSONObject.setEff(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void update(){
		ResultJSONObject resultJSONObject=new ResultJSONObject();
		
		try{
			M m1=readByPk(pk),m2=readByPk(pk);
			m2=refForm(m2);
		if(onUpdate(m1,m2,resultJSONObject)){
			_update(m2);
			onUpdateSuccess(m,resultJSONObject);
		}
		}catch(Exception ex){
			onUpdateError(m,resultJSONObject,ex);
		}
		writeText(toJson(resultJSONObject));
	}
	
	
	    
	protected void onDeleteSuccess(M m,ResultJSONObject resultJSONObject){
		try {
			resultJSONObject.setMsg("{0} 成功删除一条数据",new Date().toString());
			resultJSONObject.setCode(ResultJSONObject.CODE_SUCCESS);
			resultJSONObject.setIcon(ResultJSONObject.Icon.info.name());
	    	resultJSONObject.setTitle("删除成功");
	    	resultJSONObject.setEff(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	    
	protected void onDeleteError(M m,ResultJSONObject resultJSONObject,Exception ex){
		try {
			resultJSONObject.setMsg("{0} {1}",new Date().toString(),ex.getLocalizedMessage());
			resultJSONObject.setCode(ResultJSONObject.CODE_EXCEPTION);
			resultJSONObject.setIcon(ResultJSONObject.Icon.error.name());
	    	resultJSONObject.setTitle("删除异常");
	    	resultJSONObject.setEff(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delete(){
		ResultJSONObject resultJSONObject=new ResultJSONObject();
		WebBean webEntity=new WebBean(request, response);
		
		try{
			M m=readByPk(pk);
			m=webEntity.evalParameterModel(m, "","");
		if(onDelete(m,resultJSONObject)){
			_delete(m);
			onDeleteSuccess(m,resultJSONObject);
		}
		}catch(Exception ex){
			onDeleteError(m,resultJSONObject,ex);
		}
		writeText(toJson(resultJSONObject));
	}
	
	public void save(){
		if(hasPk()){
			this.update();
		}else{
			
			this.create();
		}
	}
	
	public void read(){
		M m=hasPk()?readByPk(pk):newModel();
		writeText(toJson(m));
	}
	
	
}
