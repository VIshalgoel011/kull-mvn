package com.kull.jdbc;

import java.text.MessageFormat;
import java.util.List;

import org.apache.ibatis.mapping.ResultMapping;

import com.kull.bean.JdbcBean.Database;







/**
 * @author badqiu
 */
public class MySQLDialect extends Dialect{

	public MySQLDialect(){
		this.database=Database.mysql;
		this.dateRegexp=DATE_REGEXP_MYSQL;
	}
	
	public boolean supportsLimitOffset(){
		return true;
	}
	
    public boolean supportsLimit() {   
        return true;   
    }  
    
	public String getLimitString(String sql, int offset,String offsetPlaceholder, int limit, String limitPlaceholder) {
        if (offset > 0) {   
        	return sql + " limit "+offsetPlaceholder+","+limitPlaceholder; 
        } else {   
            return sql + " limit "+limitPlaceholder;
        }  
	}

	



	@Override
	public String methodStrToDate(String regexp) {
		// TODO Auto-generated method stub
		String pattern="str_to_date({0},''{1}'')";
		return MessageFormat.format(pattern,regexp,dateRegexp);
	}   
  
}
