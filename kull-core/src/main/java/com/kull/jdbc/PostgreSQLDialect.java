package com.kull.jdbc;

import java.util.List;

import org.apache.ibatis.mapping.ResultMapping;




/**
 * @author badqiu
 */
public class PostgreSQLDialect extends Dialect{
	
	public boolean supportsLimit() {
		return true;
	}

	public boolean supportsLimitOffset(){
		return true;
	}
	
	public String getLimitString(String sql, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder) {
		return new StringBuffer( sql.length()+20 )
		.append(sql)
		.append(offset > 0 ? " limit "+limitPlaceholder+" offset "+offsetPlaceholder : " limit "+limitPlaceholder)
		.toString();
	}

	
	
	@Override
	public String methodStrToDate(String regexp) {
		// TODO Auto-generated method stub
		return null;
	}


}
