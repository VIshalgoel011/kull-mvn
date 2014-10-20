/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web.struts;

import com.kull.ObjectHelper;
import com.kull.StringHelper;
import com.kull.util.Resultable;
import com.kull.web.Utils;
import com.opensymphony.xwork2.ModelDriven;
import java.io.IOException;

/**
 *
 * @author lin
 */
public abstract class MDActionSupport<M> extends AwareActionSupport implements ModelDriven<M> {
    
        protected M m;
	
	protected String pk;

    public void setPk(String pk) {
        this.pk = pk;
    }
        
       public  boolean hasPk() {
        return StringHelper.isNotBlank(pk);
    }    
	
	protected abstract M readByPk(String pk) ;
	
	protected abstract M newModel() ;
	
	protected abstract void _create(M m) throws Exception;
        
        protected void onCreate(M m,Resultable re) throws Exception{}
        protected void onCreateError(M m,Resultable re,Exception ex) {}
        protected void onCreateSuccess(M m,Resultable re) throws Exception{}
        
	protected abstract void _update(M m) throws Exception;
        protected void onUpdate(M m,Resultable re) throws Exception{}
        protected void onUpdateError(M m,Resultable re,Exception ex) {}
        protected void onUpdateSuccess(M m,Resultable re) throws Exception{}
	
        protected abstract void _delete(M m) throws Exception;
        protected void onDelete(M m,Resultable re) throws Exception{}
        protected void onDeleteError(M m,Resultable re,Exception ex) {}
        protected void onDeleteSuccess(M m,Resultable re) throws Exception{}
        
        
        
        public void create() throws IOException{
		Resultable result=new Resultable();
		M m=null;
		
		try{
			m=newModel();
			 
			m=Utils.evalParameterModel(this.request,m, "","");
		onCreate(m,result);
			_create(m);
			onCreateSuccess(m,result);
		
		}catch(Exception ex){
			onCreateError(m,result,ex);
		}
		Utils.writeJson(this.response, result);
	}
        
        public void update() throws IOException{
		Resultable result=new Resultable();
		M source=null,post=null;
		try{
			source=readByPk(pk);

                        post=Utils.evalParameterModel(this.request, newModel(), "", "");
		        ObjectHelper.cp( post,source);
		onUpdate(source,result);
			_update(source);
			onUpdateSuccess(m,result);
		
		}catch(Exception ex){
			onUpdateError(m,result,ex);
		}
		Utils.writeJson(this.response, result);
	}
        
        public void delete() throws IOException{
		Resultable result=new Resultable();
		M m=null;
		
		try{
			m=readByPk(pk);
			m=Utils.evalParameterModel(this.request,m, "","");
		        onDelete(m,result);
			_delete(m);
			onDeleteSuccess(m,result);
		
		}catch(Exception ex){
			onDeleteError(m,result,ex);
		}
		Utils.writeJson(this.response, result);
	}
	
	public void save() throws IOException{
		if(hasPk()){
			this.update();
		}else{
			
			this.create();
		}
	}
	
	public void read() throws IOException{
		M m=hasPk()?readByPk(pk):newModel();
		Utils.writeJson(this.response, m);
	}
        
}
