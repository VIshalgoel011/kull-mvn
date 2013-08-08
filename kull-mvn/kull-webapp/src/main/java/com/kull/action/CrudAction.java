package com.kull.action;

import java.io.Serializable;

import org.apache.struts2.interceptor.ParameterAware;
import org.javaclub.jorm.Jorm;
import org.javaclub.jorm.Session;


import com.google.gson.Gson;
import com.kull.ObjectHelper;
import com.kull.struts.CrudActionSupport;

import com.opensymphony.xwork2.Preparable;

public abstract class CrudAction<M extends Serializable> extends CrudActionSupport<M> implements Preparable{

	public Session session;
	
	private final static Gson GSON=new Gson();

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		session=Jorm.getSession(true);
	}

	@Override
	protected M readByPk(String pk) {
		// TODO Auto-generated method stub
		return session.read(ObjectHelper.<M>getClazz(this.getClass(), 0), Integer.valueOf(pk));
	}

	@Override
	protected void _insert(M m) throws Exception {
		// TODO Auto-generated method stub
		session.save(m);
	}



	@Override
	protected void _update(M m) throws Exception {
		// TODO Auto-generated method stub
		session.update(m);
	}



	@Override
	protected void _delete(M m) throws Exception {
		// TODO Auto-generated method stub
		session.delete(m);
	}

	@Override
	protected String toJson(Object obj) {
		// TODO Auto-generated method stub
		return GSON.toJson(obj);
	}

	
	
}
