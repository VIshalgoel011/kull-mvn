/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.orm;

import com.kull.orm.dialect.DB2Dialect;
import com.kull.orm.dialect.Dialect;
import com.kull.orm.dialect.H2Dialect;
import com.kull.orm.dialect.HSQLDialect;
import com.kull.orm.dialect.MssqlDialect;
import com.kull.orm.dialect.MySQLDialect;
import com.kull.orm.dialect.OracleDialect;
import com.kull.orm.dialect.PostgreSQLDialect;
import com.kull.orm.dialect.SqliteDialect;
import com.kull.orm.dialect.SybaseDialect;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.text.MessageFormat;
import org.hibernate.dialect.SQLiteDialect;

/**
 *
 * @author lin
 */
public enum Database{
		oracle(new OracleDialect(),"oracle.jdbc.driver.OracleDriver",
				"jdbc:oracle:thin:@{0}:{1}:{2}"),
		mysql(new MySQLDialect(),"com.mysql.jdbc.Driver",
				"jdbc:mysql://{0}:{1}/{2}"),
		mssql(new MssqlDialect(),""),mongo,
		db2(new DB2Dialect(),""),
		hsql(new HSQLDialect(),"",""),
		h2(new H2Dialect(),"",""),
		sybase(new SybaseDialect(),"",""),
		postgresql(new PostgreSQLDialect(),"","" ),
                sqlite(new SqliteDialect(),"",""),
		unknow;
		
                	public final static String DRIVER_MYSQL="com.mysql.jdbc.Driver";
	public final static String DRIVER_ORACLE="oracle.jdbc.driver.OracleDriver";
	public final static String URL_PATTERN_MYSQL="jdbc:mysql://{0}:{1}/{2}";
	public final static String URL_PATTERN_ORACLE_THIN="jdbc:oracle:thin:@{0}:{1}:{2}";
	public final static int DEFAULT_PORT_MYSQL=3306;
	public final static int DEFAULT_PORT_ORACLE=1521;
                
		private Dialect dialect;
		private String driverName,urlPattern;
		
		Database(){}
		
		Database(Dialect dialect,String driverName){
			this.dialect=dialect;
		}
		
		
		Database(Dialect dialect,String driverName,String urlPattern){
			this.dialect=dialect;
			this.urlPattern=urlPattern;
			
		}

		public Dialect getDialect() throws NullPointerException {
			if(dialect==null)
				throw new NullPointerException(MessageFormat.format("{0} don't have a dialect",this.name()));
			return dialect;
		}
		
		                                                     
		
		public Connection createConnection(String host,int port,String dbName,String user,String password) throws Exception{
			String url=MessageFormat.format(this.urlPattern, host,String.valueOf(port),dbName);
			Class.forName(driverName);
			return DriverManager.getConnection(url, user, password);
		}
		
                
                public static Connection createConnection (Class<? extends Driver> driverCls,String user,String password,String dbName) throws Exception{
	    return createConnection(driverCls, user, password,"127.0.0.1" , dbName);
	}
	
	public static Connection createConnection (Class<? extends Driver> driverCls,String user,String password,String host,String dbName) throws Exception{
	    int port=-1;
		if(DRIVER_MYSQL.equals(driverCls.getName())){
			port=DEFAULT_PORT_MYSQL;
		}else if(DRIVER_ORACLE.equals(driverCls.getName())){
			port=DEFAULT_PORT_ORACLE;
		}
		if(port==-1){
			throw new Exception( MessageFormat.format("don't support this driver:{0}",driverCls.getSimpleName()));
		}
		return createConnection(driverCls, user, password, host,port, dbName);
	}
	
	public static Connection createConnection (Class<? extends Driver> driverCls,String user,String password,String host,int port,String dbName) throws Exception{
		String pattern="";
		if(DRIVER_MYSQL.equals(driverCls.getName())){
			pattern=URL_PATTERN_MYSQL;
		}else if(DRIVER_ORACLE.equals(driverCls.getName())){
			pattern=URL_PATTERN_ORACLE_THIN;
		}
		if(pattern==null){
			throw new Exception( MessageFormat.format("don't support this driver:{0}",driverCls.getSimpleName()));
		}
		Class.forName(driverCls.getName());
		String url=MessageFormat.format(pattern, host,String.valueOf(port),dbName);
		System.out.println("url:"+url);
		Connection connection=null; 
		connection= DriverManager.getConnection(url, user, password);
		return connection;
	}
	
	public static Connection createSqllteConnection(String dbPath) throws Exception{
		Class.forName("org.sqlite.JDBC");
		return DriverManager.getConnection(MessageFormat.format("jdbc:sqlite:{0}",dbPath));
	}
	
	public static Database refDatabase(Connection connection) throws Exception{
		Database database=Database.unknow;
		String driverName=connection.getMetaData().getDriverName().toLowerCase();
		if(driverName.contains(Database.oracle.name())){
			database=Database.oracle;
		}else if(driverName.contains(Database.mysql.name())){
			database=Database.mysql;
		}else if(driverName.contains(Database.postgresql.name())){
			database=Database.postgresql;
		}else if(driverName.contains(Database.sqlite.name())){
			database=Database.sqlite;
		}
		else{
			throw new Exception(MessageFormat.format("don't surport the driver:{0}",connection.getMetaData().getDriverName()));
		}
		return database;
	}
	}