/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.orm;

import com.kull.LinqHelper;
import com.kull.ObjectHelper;
import com.kull.StringHelper;
import com.kull.orm.annotation.OrmTable;
import com.kull.orm.dialect.*;
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

/**
 *
 * @author lin
 */
public class Session {
    private Connection conn;
	
	public Session(Connection conn){
		this.conn=conn;
	}
	
	
	

	
	private static Map<String, String> SQL_CACHE=new HashMap<String, String>();
	
	
	
	
	
	
	
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
	
	

	
	
	

	
	
	
	public <T> T load(Class<T> cls,Object pk) throws Exception{
		 T t=cls.newInstance();
		 return load(t, pk);
	}
	
	public <T> T load(T t,Object pk) throws SQLException, NoSuchFieldException{
		if(t==null|| pk==null) throw new NullPointerException("t or pk can't be null");
		OrmTable table=t.getClass().getAnnotation(OrmTable.class);
		return (T)load(t, table.pk(), pk);
	}
	
	public <T> T load(Class<T> cls,String column,Object pk) throws Exception{
		 T t=cls.newInstance();
		 return load(t,column, pk);
	}
	
	public <T> T load(T t,String column,Object pk) throws  SQLException, NoSuchFieldException {
		if(t==null|| pk==null) throw new NullPointerException("t or pk can't be null");
		OrmTable table=t.getClass().getAnnotation(OrmTable.class);
		
		Field pkField=t.getClass().getDeclaredField(table.pk());
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String sql=MessageFormat.format("select * from {0} where {1}= ?", table.name(),column);
		
	     preparedStatement=conn.prepareStatement(sql);
		if(pkField.getType().equals(String.class)){
			preparedStatement.setString(1, pk.toString());
		}else if(pkField.getType().equals(Integer.class)){
			preparedStatement.setInt(1, Integer.parseInt(pk.toString()));
		}
	    resultSet=preparedStatement.executeQuery();
		   
	    if(resultSet.next()){
	    	t=evalObject(t, resultSet);
	    }else{
                  String errormsg="can't select a model wiht sql= "+sql+ " , pk ="+pk;
                    throw new SQLException(errormsg);
            }
		
          close(null,preparedStatement,resultSet);
	
		

	
		return t;
	}
	
public int insert(Object...objs) throws Exception{
		
		int success=0;
		PreparedStatement preparedStatement=null;
		for(Object obj:objs){
			if(obj==null)continue;
			OrmTable table=null;
			String  sqlPattern="insert into {0} ({1}) values ({2})",sql="",
					sqlCacheKey=obj.getClass().getSimpleName()+":insert",cols="",vals="";
			Field[] fields=null;
		    
	    		table=obj.getClass().getAnnotation(OrmTable.class);
				fields=obj.getClass().getDeclaredFields();
		    	if(SQL_CACHE.containsKey(sqlCacheKey)){
		    		sql=SQL_CACHE.get(sqlCacheKey);
		    	}else{

					
					for(Field field:fields){
                                            if(ObjectHelper.isThis0(field))continue;
                                            String fname=field.getName() ;
						if( LinqHelper.isIn(field.getName(),table.excludeColumns())||
								   (!table.insertPk()&& field.getName().equalsIgnoreCase(table.pk())
                                                          
                                                        )
						)continue;	
						cols+=MessageFormat.format(" `{0}`,",fname );
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
                                        if(ObjectHelper.isThis0(field))continue;
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
			OrmTable table=null;
			String  sqlPattern="delete from {0} where {1}=?",sql="",
					sqlCacheKey=obj.getClass().getSimpleName()+":delete";
			try{
			  table=obj.getClass().getAnnotation(OrmTable.class);
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
			OrmTable table=null;
			String sqlPattern="update {0} set {1} where {2}=? ",key="",sql=""
					,sqlCacheKey=obj.getClass().getSimpleName()+":update";
			Field[] fields=null;
			Field pkField=null;
			try{
				table=obj.getClass().getAnnotation(OrmTable.class);
				fields=obj.getClass().getDeclaredFields();
				pkField=obj.getClass().getDeclaredField(table.pk());
				if(SQL_CACHE.containsKey(sqlCacheKey)){
						sql=SQL_CACHE.get(sqlCacheKey);  
			}else{
	
			int i=0;
			for(Field field:fields){
                                String fname=field.getName();
                                 if(ObjectHelper.isThis0(field))continue;
				if(LinqHelper.isIn(field.getName(),table.excludeColumns())||field.getName().equalsIgnoreCase(table.pk())){pkField=field;continue;}
			
                                key+=MessageFormat.format(" `{0}` =? ,", fname);
				i++;
			}
			key=StringHelper.trim(key, ",");
		    sql=MessageFormat.format(sqlPattern, table.name(),key,table.pk());
		    SQL_CACHE.put(sqlCacheKey,sql);
		    }
		    preparedStatement=conn.prepareStatement(sql);
		    int j=0;
			for(Field field:fields){
                                if(ObjectHelper.isThis0(field))continue;
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
		OrmTable table=cls.getAnnotation(OrmTable.class);
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
	
	
	
	public  int executeUpdate(String sql,Object ...params)throws SQLException{
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
