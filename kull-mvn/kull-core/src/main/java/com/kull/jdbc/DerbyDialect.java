package com.kull.jdbc;

import java.util.List;

import org.apache.ibatis.mapping.ResultMapping;




public class DerbyDialect extends Dialect{

	public boolean supportsLimit() {
		return false;
	}

	public boolean supportsLimitOffset() {
		return false;
	}

	public String getLimitString(String sql, int offset,String offsetPlaceholder, int limit, String limitPlaceholder) {
		throw new UnsupportedOperationException( "paged queries not supported" );
	}

	

	

	@Override
	public String methodStrToDate(String regexp) {
		// TODO Auto-generated method stub
		return null;
	}



}
