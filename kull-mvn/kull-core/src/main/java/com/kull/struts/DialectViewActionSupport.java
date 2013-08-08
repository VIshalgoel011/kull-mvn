package com.kull.struts;

import java.lang.reflect.ParameterizedType;


import com.kull.jdbc.Dialect;

public abstract class DialectViewActionSupport extends SimpleViewActionSupport {

	protected abstract Dialect getDialect();
	
	@Override
	protected String createPageSql(int start, int limit) {
		// TODO Auto-generated method stub
		String sql=createDataSql();
		String psql=getDialect().getLimitString(sql, start, limit);
		return psql;
	}

	
	
}
