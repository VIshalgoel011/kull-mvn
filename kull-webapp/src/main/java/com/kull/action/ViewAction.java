package com.kull.action;

import java.sql.Connection;
import java.util.Set;


import javax.sql.DataSource;

import org.javaclub.jorm.Jorm;

import com.google.gson.Gson;
import com.kull.jdbc.Dialect;
import com.kull.jdbc.PostgreSQLDialect;
import com.kull.struts.DialectViewActionSupport;
import com.kull.struts.SimpleViewActionSupport;




public abstract class ViewAction extends DialectViewActionSupport {

	
	
	private final static Gson GSON=new Gson();
    private final static Dialect DIALECT=new PostgreSQLDialect();
	
	
    
    
	@Override
	protected Dialect getDialect() {
		// TODO Auto-generated method stub
		return DIALECT;
	}


	


	


	@Override
	protected Connection createConnection() {
		// TODO Auto-generated method stub
		return Jorm.getSession().getConnection(true);
	}

	
	

	

	@Override
	protected int postStart() {
		// TODO Auto-generated method stub
		int page=paramInt("page", 1);
		int start=(page-1)*this.postLimit();
		return start;
	}


	@Override
	protected int postLimit() {
		// TODO Auto-generated method stub
		return paramInt("rows", 100);
	}


	@Override
	protected String toJson(Object obj) {
		// TODO Auto-generated method stub
		return GSON.toJson(obj);
		
	}

	
}
