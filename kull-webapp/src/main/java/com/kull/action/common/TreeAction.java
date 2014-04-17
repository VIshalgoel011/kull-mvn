package com.kull.action.common;

import java.sql.Connection;

import org.javaclub.jorm.Jorm;

import com.google.gson.Gson;
import com.kull.easyui.TreeViewActionSupport;

public class TreeAction extends TreeViewActionSupport {

	private final static Gson GSON=new Gson();
	
	@Override
	protected Connection createConnection() {
		// TODO Auto-generated method stub
		return Jorm.getSession().getConnection(true);
	}

	@Deprecated
	@Override
	protected String title() {
		// TODO Auto-generated method stub
		return null;
	}

	@Deprecated
	@Override
	protected String createPageSql(int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String toJson(Object obj) {
		// TODO Auto-generated method stub
		return GSON.toJson(obj);
	}

	

	
}
