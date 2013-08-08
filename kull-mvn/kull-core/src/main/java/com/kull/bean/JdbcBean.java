package com.kull.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.type.JdbcType;



import com.kull.LinqHelper;
import com.kull.StringHelper;
import com.kull.annotation.SimpleOrmTable;
import com.kull.jdbc.*;



public class JdbcBean {

	
	private Connection conn;
	
	public JdbcBean(Connection conn){
		this.conn=conn;
	}
	
	
	
	public final static String DRIVER_MYSQL="com.mysql.jdbc.Driver";
	public final static String DRIVER_ORACLE="oracle.jdbc.driver.OracleDriver";
	public final static String URL_PATTERN_MYSQL="jdbc:mysql://{0}:{1}/{2}";
	public final static String URL_PATTERN_ORACLE_THIN="jdbc:oracle:thin:@{0}:{1}:{2}";
	public final static int DEFAULT_PORT_MYSQL=3306;
	public final static int DEFAULT_PORT_ORACLE=1521;
	
	private static Map<String, String> SQL_CACHE=new HashMap<String, String>();
	
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
		unknow;
		
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
		
	}
	
	
	public enum Column{
		VARCHAR2(JdbcType.VARCHAR,String.class),
		VARCHAR(JdbcType.VARCHAR,String.class),
		CHAR(JdbcType.CHAR,String.class),
		TEXT(JdbcType.LONGVARCHAR,String.class),
		LONGVARCHAR(JdbcType.LONGVARCHAR,String.class),
		INTEGER(JdbcType.INTEGER,Integer.class),
		INT(JdbcType.INTEGER,Integer.class),
		BIGINT(JdbcType.BIGINT,Integer.class),
		NUMBER(JdbcType.NUMERIC,Double.class),
		FLOAT(JdbcType.DECIMAL,Float.class),
		DOUBLE(JdbcType.DOUBLE,Double.class),
		DATE(JdbcType.DATE,Date.class),
		TIME(JdbcType.TIME,Date.class),
		DATETIME(JdbcType.DATE,Date.class),
		TIMESTAMP(JdbcType.TIMESTAMP,Timestamp.class),
		BLOB(JdbcType.BLOB,Byte.class),
		LONGBLOB(JdbcType.BLOB,Byte.class),
		CLOB(JdbcType.CLOB,String.class),
		LONG(JdbcType.LONGVARCHAR,String.class),
		MEDIUMINT(JdbcType.INTEGER,Integer.class),
		TINYINT(JdbcType.TINYINT,Integer.class), DECIMAL(JdbcType.DECIMAL,Double.class);
		JdbcType jdbcType;
		Class javaType;
		int type;
		Column(JdbcType jdbcType,Class javaType){
			this.jdbcType=jdbcType;
			this.javaType=javaType;
			
		}
		

		public JdbcType getJdbcType() {
			return jdbcType;
		}

		public Class getJavaType() {
			return javaType;
		}
		
		
	}
	
	
	public  enum QueryType{
		lt("小于","<","&lt;"),
		lte("小于等于","<=","&lt;="),
		gte("大于等于",">=","&gt;="),
		gt("大于",">","&gt;"),
		like("匹配","like"),
		neq("非等于","!="),
		isin("包含","in"),
		isnotin("非包含","not in"),
		notlike("非匹配","not like"),
		eq("等于","="),
		max("最大值","<=","&lt;="),
		min("最小值",">=","&gt;="),
		isnull("空","is null"),
		isnotnull("非空","is not null")
		;
		
		public String nameCn,symbol,xmlSymbol;
		
		QueryType(String nameCn,String symbol){
			this.nameCn=nameCn;
			this.symbol=symbol;
			this.xmlSymbol=symbol;
		}
		
		QueryType(String nameCn,String symbol,String xmlSymbol){
			this.nameCn=nameCn;
			this.symbol=symbol;
			this.xmlSymbol=xmlSymbol;
		}

		public String getNameCn() {
			return nameCn;
		}

		public String getSymbol() {
			return symbol;
		}

		public String getXmlSymbol() {
			return xmlSymbol;
		}
		
		public String toProName(String proName){
			return MessageFormat.format("{0}_{1}", this.name(),proName);
		}
		
	}
	
	
	final public static JdbcType[]  JDBC_TYPES_STRING=new JdbcType[]{JdbcType.VARCHAR,JdbcType.CHAR};
	final public static JdbcType[]  JDBC_TYPES_DATE=new JdbcType[]{JdbcType.DATE,JdbcType.TIME};
	final public static JdbcType[]  JDBC_TYPES_TIMESTAMP=new JdbcType[]{JdbcType.TIMESTAMP};
	final public static JdbcType[]  JDBC_TYPES_BYTES=new JdbcType[]{JdbcType.BLOB,JdbcType.CLOB};
	
	
	

	
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
		}
		else{
			throw new Exception(MessageFormat.format("don't surport the driver:{0}",connection.getMetaData().getDriverName()));
		}
		return database;
	}
	
	public <T> T load(Class<T> cls,Object pk) throws Exception{
		 T t=cls.newInstance();
		 return load(t, pk);
	}
	
	public <T> T load(T t,Object pk) {
		if(t==null)return t;
		SimpleOrmTable table=null;
		String sql="";
		Field pkField=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try{
		table=t.getClass().getAnnotation(SimpleOrmTable.class);
		 sql=MessageFormat.format("select * from {0} where {1}=?", table.name(),table.pk());
		pkField=t.getClass().getDeclaredField(table.pk());
	     preparedStatement=conn.prepareStatement(sql);
	     /*
		if(pkField.getType().equals(String.class)){
			preparedStatement.setString(1, pk.toString());
		}else if(pkField.getType().equals(Integer.class)){
			preparedStatement.setInt(1, Integer.parseInt(pk.toString()));
		}*/
	    preparedStatement.setObject(1, pk);
	    resultSet=preparedStatement.executeQuery();
		   
		if(resultSet.next()){
	    	t=evalObject(t, resultSet);
	    }
		}catch(Exception ex){
			return null;
		}
		 finally{
			close(null,preparedStatement,resultSet);
		}

	
		return t;
	}
	
	public <T> T load(Class<T> cls,String column,Object pk) throws Exception{
		 T t=cls.newInstance();
		 return load(t,column, pk);
	}
	
	public <T> T load(T t,String column,Object pk) {
		if(t==null)return t;
		SimpleOrmTable table=null;
		String sql="";
		Field pkField=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try{
		table=t.getClass().getAnnotation(SimpleOrmTable.class);
		 sql=MessageFormat.format("select * from {0} where {1}= ?", table.name(),column);
		pkField=t.getClass().getDeclaredField(column);
	     preparedStatement=conn.prepareStatement(sql);
		if(pkField.getType().equals(String.class)){
			preparedStatement.setString(1, pk.toString());
		}else if(pkField.getType().equals(Integer.class)){
			preparedStatement.setInt(1, Integer.parseInt(pk.toString()));
		}
	    resultSet=preparedStatement.executeQuery();
		   
		if(resultSet.next()){
	    	t=evalObject(t, resultSet);
	    }
		}catch(Exception ex){}
		 finally{
			close(null,preparedStatement,resultSet);
	
		}

	
		return t;
	}
	
public int insert(Object...objs) throws Exception{
		
		int success=0;
		PreparedStatement preparedStatement=null;
		for(Object obj:objs){
			if(obj==null)continue;
			SimpleOrmTable table=null;
			String  sqlPattern="insert into {0} ({1}) values ({2})",sql="",
					sqlCacheKey=obj.getClass().getSimpleName()+":insert",cols="",vals="";
			Field[] fields=null;
		    
	    		table=obj.getClass().getAnnotation(SimpleOrmTable.class);
				fields=obj.getClass().getDeclaredFields();
		    	if(SQL_CACHE.containsKey(sqlCacheKey)){
		    		sql=SQL_CACHE.get(sqlCacheKey);
		    	}else{

					
					for(Field field:fields){
						if( LinqHelper.isIn(field.getName(),table.excludeColumns())||
								   (!table.insertPk()&& field.getName().equalsIgnoreCase(table.pk()) )
						)continue;	
						cols+=MessageFormat.format(" `{0}`,",field.getName() );
						vals+=" ?,";
						
					}
					cols=StringHelper.trim(cols, ",");
					vals=StringHelper.trim(vals, ",");
		    	    sql=MessageFormat.format(sqlPattern, table.name(),cols,vals);
		    	    SQL_CACHE.put(sqlCacheKey, sql);
		    	    System.out.println(sql);
		    	}
		    	preparedStatement=conn.prepareStatement(sql);
		        int j=0;
				for(Field field:fields){
					if( LinqHelper.isIn(field.getName(),table.excludeColumns())||
					   (!table.insertPk()&& field.getName().equalsIgnoreCase(table.pk()) )
					)continue;	
					String getterName="get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
					Method m=obj.getClass().getDeclaredMethod(getterName);
					Object value=m.invoke(obj);
					preparedStatement.setObject(j+1, value);
					j++;
				}
				
				success+=preparedStatement.executeUpdate();
				
		   
		       close(null,preparedStatement,null);
		    
		}
		return success;
	}
	
	public int delete(Object...objs){
		
		int success=0;
		PreparedStatement preparedStatement=null;
		for(Object obj:objs){
			if(obj==null)continue;
			SimpleOrmTable table=null;
			String  sqlPattern="delete from {0} where {1}=?",sql="",
					sqlCacheKey=obj.getClass().getSimpleName()+":delete";
			try{
			  table=obj.getClass().getAnnotation(SimpleOrmTable.class);
			  if(SQL_CACHE.containsKey(sqlCacheKey)){
				sql=SQL_CACHE.get(sqlCacheKey);  
			  }else{
			    sql=MessageFormat.format(sqlPattern, table.name(),table.pk());
			    SQL_CACHE.put(sqlCacheKey,sql); 
			  }
			  System.out.println(sql);
			  String getterName="get"+table.pk().substring(0,1).toUpperCase()+table.pk().substring(1);;
			  Method method=obj.getClass().getDeclaredMethod(getterName);
			  Object value=method.invoke(obj);
			  preparedStatement=conn.prepareStatement(sql);
			  preparedStatement.setObject(1, value);
			  success+=preparedStatement.executeUpdate();
			}catch(Exception ex){
				ex.printStackTrace();
				continue;
			}
		}
		return success;
	}
	
	public int update(Object...objs){
		
		int success=0;
		PreparedStatement preparedStatement=null;
		for(Object obj : objs){
			if(obj==null)continue;
			SimpleOrmTable table=null;
			String sqlPattern="update {0} set {1} where {2}=? ",key="",sql=""
					,sqlCacheKey=obj.getClass().getSimpleName()+":update";
			Field[] fields=null;
			Field pkField=null;
			try{
				table=obj.getClass().getAnnotation(SimpleOrmTable.class);
				fields=obj.getClass().getDeclaredFields();
				pkField=obj.getClass().getDeclaredField(table.pk());
				if(SQL_CACHE.containsKey(sqlCacheKey)){
						sql=SQL_CACHE.get(sqlCacheKey);  
			}else{
	
			int i=0;
			for(Field field:fields){
				if(LinqHelper.isIn(field.getName(),table.excludeColumns())||field.getName().equalsIgnoreCase(table.pk())){pkField=field;continue;}
				key+=MessageFormat.format(" `{0}` =? ,",field.getName() );
				i++;
			}
			key=StringHelper.trim(key, ",");
		    sql=MessageFormat.format(sqlPattern, table.name(),key,table.pk());
		    SQL_CACHE.put(sqlCacheKey,sql);
		    }
		    preparedStatement=conn.prepareStatement(sql);
		    int j=0;
			for(Field field:fields){
				if(LinqHelper.isIn(field.getName(),table.excludeColumns())||field.getName().equalsIgnoreCase(table.pk()))continue;	
				String getterName="get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
				Method m=obj.getClass().getDeclaredMethod(getterName);
				Object value=m.invoke(obj);
				preparedStatement.setObject(j+1, value);
				j++;
			}
			String getterName="get"+pkField.getName().substring(0,1).toUpperCase()+pkField.getName().substring(1);
			Method m=obj.getClass().getDeclaredMethod(getterName);
			Object value=m.invoke(obj);
			preparedStatement.setObject(j+1, value);
			System.out.println(sql);
			success+=preparedStatement.executeUpdate();
			
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				close(null,preparedStatement,null);
			}
			
		}
		return success;
	}
	
	/**
	 * 
	 * @param cls
	 * @param resultSet
	 * @return  将resutlSet映射到泛型集合中
	 * @throws Exception
	 */
	public static  <T> List<T> evalList(Class<T> cls,ResultSet resultSet) throws Exception{
	    List<T> list=new ArrayList<T>();
	    while(resultSet.next()){
	    	list.add(evalObject(cls,resultSet));
	    }
	    return list;
	}
	
	
	/**
	 * 
	 * @param cls
	 * @param resultSet
	 * @return 将resutlSet的第一条记录创建成新对象并返回
	 * @throws Exception
	 */
	public static  <T> T evalObject(Class<T> cls,ResultSet resultSet) throws Exception{
		T t=cls.newInstance();
	    return evalObject(t, resultSet);
	}

	
	public static final Map<Integer,Class> COLTYPE_REF_CLASS=new HashMap<Integer, Class>();
	
	static{
		COLTYPE_REF_CLASS.put(Types.VARCHAR, String.class);
		COLTYPE_REF_CLASS.put(Types.CHAR, String.class);
		COLTYPE_REF_CLASS.put(Types.NVARCHAR, String.class);
		COLTYPE_REF_CLASS.put(Types.LONGNVARCHAR, String.class);
		COLTYPE_REF_CLASS.put(Types.LONGVARCHAR, String.class);
		COLTYPE_REF_CLASS.put(Types.DOUBLE, Double.class);
		COLTYPE_REF_CLASS.put(Types.FLOAT, Float.class);
		COLTYPE_REF_CLASS.put(Types.INTEGER, Integer.class);
		COLTYPE_REF_CLASS.put(Types.SMALLINT, Integer.class);
		COLTYPE_REF_CLASS.put(Types.TINYINT, Integer.class);
		COLTYPE_REF_CLASS.put(Types.BIGINT, Integer.class);
		COLTYPE_REF_CLASS.put(Types.DATE, Date.class);
		COLTYPE_REF_CLASS.put(Types.TIME, Time.class);
		COLTYPE_REF_CLASS.put(Types.TIMESTAMP, Timestamp.class);
		COLTYPE_REF_CLASS.put(Types.NUMERIC, Number.class);
		COLTYPE_REF_CLASS.put(Types.ARRAY, String[].class);
	}
	
	/**
	 * 
	 * @param t
	 * @param resultSet
	 * @return 将resutlSet的第一条记录映射如新对象中并返回
	 * @throws Exception
	 */
	public static  <T> T evalObject(T t,ResultSet resultSet) {
		if(t==null)return t;
		int colCount=0;
		try {
			colCount = resultSet.getMetaData().getColumnCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Method m=null;
	    for(int i=1;i<=colCount;i++){

	        try{
		    	String colName=resultSet.getMetaData().getColumnName(i);
		    	int colType=resultSet.getMetaData().getColumnType(i);
		    	String setMethodName="set"+colName.substring(0,1).toUpperCase()+colName.substring(1);
	    	    Class  cls=COLTYPE_REF_CLASS.get(colType);
	    	    m=t.getClass().getDeclaredMethod(setMethodName,cls);
	    	    m.invoke(t, resultSet.getObject(i));
		    	/*
	    	if(LinqHelper.isIn(colType, Types.VARCHAR,Types.CHAR,Types.NVARCHAR)){
	    		  m=t.getClass().getDeclaredMethod(setMethodName,String.class);
	    		  m.invoke(t,resultSet.getString(colName));
	    	}else  if(colType==Column.INT.name()
	    			){
	    		m=t.getClass().getDeclaredMethod(setMethodName,Integer.class);
	    		 m.invoke(t,resultSet.getInt(colName));
	    	}else if(colType==Column.FLOAT.name()){
	    		m=t.getClass().getDeclaredMethod(setMethodName,Float.class);
	    		 m.invoke(t,resultSet.getFloat(colName));
	    	}else if(colType==Column.DOUBLE.name()||colType==Column.DECIMAL.name()){
	    		m=t.getClass().getDeclaredMethod(setMethodName,Double.class); 
	    		m.invoke(t,resultSet.getDouble(colName));
	    	}else if(colType==Column.DATETIME.name()){
	    		m=t.getClass().getDeclaredMethod(setMethodName,Date.class);
	    		 m.invoke(t,resultSet.getDate(colName));
	    	}*/
	        }catch(Exception ex){
	        	continue;
	        }
	    }
		return t;
	}
	
	
	public <T> List<T> select(Class<T> cls,String sql,Object ...params){
		PreparedStatement ps=null;
		List<T> list=null;
		ResultSet rs=null;
		SimpleOrmTable table=cls.getAnnotation(SimpleOrmTable.class);
		try {
			 if(table!=null){
				 sql=MessageFormat.format(sql, table.name(),table.pk());
			 }
			 ps=this.conn.prepareStatement(sql);
			 for(int i=0;i<params.length;i++){
				 ps.setObject(i+1, params[i]);
			 }
			 rs=ps.executeQuery();
			 list=evalList(cls, rs);
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close(null, ps, rs);
		}
		return list;
	}
	
	public LinkedList<Map<String, Object>> selectList(String sql,Object ...params){
		PreparedStatement ps=null;
		LinkedList<Map<String, Object>> list=new LinkedList<Map<String,Object>>();
		ResultSet rs=null;
		
		try {
			 
			 ps=this.conn.prepareStatement(sql);
			 for(int i=0;i<params.length;i++){
				 ps.setObject(i+1, params[i]);
			 }
			 rs=ps.executeQuery();
			 ResultSetMetaData resultSetMetaData=rs.getMetaData();
			 int colCount=resultSetMetaData.getColumnCount();
			 while(rs.next()){
				 Map<String, Object> map=new HashMap<String, Object>();
				 for(int i=1;i<=colCount;i++){
					String label=resultSetMetaData.getColumnLabel(i);
					map.put(label, rs.getObject(i));
			        
				 }
				 list.add(map);
			 }
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			close(null, ps, rs);
		}
		return list;
	}
	
	
	
	public <T> int executeUpdate(String sql,Object ...params)throws SQLException{
		PreparedStatement ps=null;
		int eff=0;
		
			 ps=this.conn.prepareStatement(sql);
			 for(int i=0;i<params.length;i++){
				 ps.setObject(i+1, params[i]);
			 }
			 eff=ps.executeUpdate();
			 close(null, ps, null);
		return eff;
	}

	
	public <T> int selectInt(String sql,Object ...params) throws SQLException{
		PreparedStatement ps=null;
		ResultSet rs=null;
		int eff=0;
		
		ps=this.conn.prepareStatement(sql);
	    for(int i=0;i<params.length;i++){
				 ps.setObject(i+1, params[i]);
	    }
		rs=ps.executeQuery();
		if(rs.next()){
			eff=rs.getInt(1);
		}
		close(null, ps, rs);
		
		return eff;
	}

	/**
	 * 安全关闭数据库连接
	 * 
	 * @param conn1
	 *            Connection
	 */
	public static void close(Connection conn,Statement statement,ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (conn != null) {
				conn.close();
			}

	
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 
	 * @param table
	 * @return  实体类正文，包含属性定义和getset方法
	 * @throws SQLException
	 */
	public  String tableRefClassConext(String table) throws SQLException{
		String sql=MessageFormat.format("select * from {0}", table);
		StringBuffer sbrPro=new StringBuffer(""),sbrGetSet=new StringBuffer("");
		String proPattern="\t protected {0} {1} ;"
				,setterPattern="\t public void set{0}({1} {3})'{' this.{2}={3}; '}'"
				,getterPattern="\t public {1} get{0}()'{' return this.{2}; '}'";
		PreparedStatement ps=conn.prepareStatement(sql);
		ResultSet resultSet=ps.executeQuery();
		ResultSetMetaData rsmd= resultSet.getMetaData();
		int colCount=resultSet.getMetaData().getColumnCount();
		for(int i=1;i<=colCount;i++){
	    	String colName=resultSet.getMetaData().getColumnName(i);
	    	int colType=resultSet.getMetaData().getColumnType(i);
	    	
	    	String fName=colName.substring(0,1).toUpperCase()+colName.substring(1);
	    	Class proType=COLTYPE_REF_CLASS.get(colType);
	    	
	    	sbrPro.append(MessageFormat.format(proPattern, proType.getSimpleName(),colName)).append("\n");
	    	sbrGetSet.append(MessageFormat.format(getterPattern,fName, proType.getSimpleName(),colName)).append("\n");
	    	sbrGetSet.append(MessageFormat.format(setterPattern,fName, proType.getSimpleName(),colName,colName)).append("\n");

		}
		
		sbrPro.append("\n\n").append(sbrGetSet);
		return sbrPro.toString();
	}
}
