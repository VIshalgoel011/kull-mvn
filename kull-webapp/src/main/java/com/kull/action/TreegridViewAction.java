package com.kull.action;

import java.sql.Connection;

import org.javaclub.jorm.Jorm;

import com.google.gson.Gson;
import com.kull.easyui.TreegridViewActionSupport;

public abstract class TreegridViewAction extends TreegridViewActionSupport {

	private final static Gson GSON=new Gson();
	
	@Override
	protected Connection createConnection() {
		// TODO Auto-generated method stub
		return Jorm.getSession().getConnection(true);
	}

	

	@Deprecated
	@Override
	protected String createPageSql(int start, int limit) {
		// TODO Auto-generated method stub
		return this.createDataSql();
	}

	
	@Override
	protected String toJson(Object obj) {
		// TODO Auto-generated method stub
		return GSON.toJson(obj);
	
	}
}
