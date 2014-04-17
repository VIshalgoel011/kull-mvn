package com.kull.jdbc;

import java.text.MessageFormat;
import java.util.List;

import com.kull.bean.JdbcBean.Database;









public abstract class Dialect {
	public Database database=Database.unknow;
	public String dateRegexp="";
	
	final public static String DATE_REGEXP_ORACLE="YYYY-MM-DD";
	final public static String DATE_REGEXP_MYSQL="%Y-%m-%d";
	
	final public static String METHOD_STR_TO_DATE_ORACLE="to_date";
	final public static String METHOD_STR_TO_DATE_MYSQL="str_to_date";
    public boolean supportsLimit(){
    	return false;
    }

    public boolean supportsLimitOffset() {
    	return supportsLimit();
    }
    

    public String getLimitString(String sql, int offset, int limit) {
    	return getLimitString(sql,offset,Integer.toString(offset),limit,Integer.toString(limit));
    }
    

    public abstract String getLimitString(String sql, int offset,String offsetPlaceholder, int limit,String limitPlaceholder);
    
    
    
    
    public abstract String methodStrToDate(String regexp);
    
    
    public static Dialect createDialect(Database database) throws Exception{
    	switch (database) {
		case oracle:{return new OracleDialect();}
		case mssql:{return new MssqlDialect();}
		case mysql:{return new MySQLDialect();}
		case db2:{return new DB2Dialect();}
		case hsql:{return new HSQLDialect();}
		case postgresql:{return new PostgreSQLDialect();}
		default:
			throw new Exception(MessageFormat.format("can't find the dialect with {0}", database));
		}
    	
    }
}
