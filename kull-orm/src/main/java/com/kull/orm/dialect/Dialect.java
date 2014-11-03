package com.kull.orm.dialect;

import com.kull.ObjectHelper;
import com.kull.StringHelper;
import com.kull.able.Ormable;
import com.kull.orm.Database;
import com.kull.orm.annotation.OrmTable;
import com.kull.orm.mybatis.MapperFactory;
import com.kull.orm.mybatis.MapperTemplate;

import com.kull.able.Resultable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;











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
    
    
    public abstract Set<String> showTables(Connection conn) throws SQLException;
    
    public abstract Set<String> showViews(Connection conn) throws SQLException; 
    
    public  String createTableDBML(Connection conn,String className) throws SQLException, Exception{
         MapperFactory mapperFactory=new MapperFactory(conn);
        
         String packg=className.substring(0,className.lastIndexOf(".")),
                 sname=className.substring(packg.length()+1,className.length()-1);
         
         StringBuffer context=new StringBuffer("");
         
        Class[] importClss={List.class,Date.class,Timestamp.class,Ormable.class,Resultable.class,Set.class,ObjectHelper.class,OrmTable.class};
    	context
    	.append("package "+packg+";").append(StringHelper.ln(2));
    	for(Class importCls : importClss){
    		context.append("import "+importCls.getName()+";").append(StringHelper.ln())
        	;
    	}        
        
        
        context.append("\n\n public class ").append(sname).append(" {  \n")
                
                ;
         
         for(String table :showTables(conn)){
             MapperTemplate mt=mapperFactory.createMapperTemplate(null, table, "id", new HashSet<String>());
             context.append("\n\n\t\t").append(mt.contextBaseModel(table));
         }
         context.append("\n\n }");
         return context.toString();
    };
    
    
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
