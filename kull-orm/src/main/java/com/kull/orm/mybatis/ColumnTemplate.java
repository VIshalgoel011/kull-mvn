package com.kull.orm.mybatis;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.Bidi;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



import org.apache.ibatis.type.JdbcType;

import com.kull.StringHelper;
import com.kull.orm.Column;






public class ColumnTemplate {
	

	
	




	
	public static final int PREC_FLOAT=126;
	public static final int PREC_INTEGER=38;
	
	private String dbColName;
	private String dbColType;
	private String javaName;
	//private Class javaType;
	//private JdbcType jdbcType;
	private Column column;
	private int precision;
	

	
	public ColumnTemplate(String dbColName,String dbColType,int perc){
		this.dbColName=dbColName;
		this.dbColType=dbColType;
		this.precision=perc;
		this.javaName=getJavaColName(dbColName);
		//this.javaType=toJavaType(dbColType);
		//this.jdbcType=toJdbcType(dbColType);
		try {
			//JdbcType enumJdbcType=this.getJdbcType(Column.valueOf(dbColType), perc);
			//Class clsJavaType=this.getJavaClass(enumJdbcType);
			//this.jdbcType=enumJdbcType;
			//this.javaType=clsJavaType;
			this.column=Column.valueOf(dbColType.toUpperCase());
			if(column==Column.NUMBER){
				if(perc<=PREC_INTEGER){
					this.column=Column.INTEGER;
				}else if(perc<=PREC_FLOAT){
					this.column=Column.FLOAT;
				}
				else {
					this.column=Column.DOUBLE;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getDbColName() {
		return dbColName;
	}
	public String getDbColType() {
		return dbColType;
	}

	
	
	public String getJavaName() {
		return javaName;
	}

	

	public Column getColumn() {
		return column;
	}

	public int getPrecision() {
		return precision;
	}

	public static String getJavaColName(String lStrDbColName)
	{
		if(StringHelper.isBlank(lStrDbColName))return "";
		StringBuffer lSbrModelColName=new StringBuffer("");
		String[] lSubNames=lStrDbColName.split("_");
		boolean isFirstSubNameLen1=false;
		for (int i = 0; i < lSubNames.length; i++) {
			String lSubName=lSubNames[i].toLowerCase();
//			if(i==0){
//				isFirstSubNameLen1=lSbrModelColName.length()==1;
//				if(isFirstSubNameLen1){
//					lSbrModelColName.append(lSubName.toUpperCase());
//				}else{
//					lSbrModelColName.append(lSubName);
//				}
//			}
//			else{
			   lSbrModelColName.append(StringHelper.format(lSubName, StringHelper.Format.upcaseFirstChar));
//			}
		}
		return lSbrModelColName.toString();
	}
	
	

	/*
	
	private static Map<ColumnType, JdbcType> mapJdbcEnum;
	private static Map<JdbcType, Class> mapJavaClass;
	
	public static JdbcType getJdbcType(ColumnType columnType,int perc) throws Exception{
		if(columnType==null)throw new Exception();
	 	JdbcType jdbcType=null;
	 	if(mapJdbcEnum==null){
	 		mapJdbcEnum=new HashMap<ColumnType, JdbcType>();
	 		mapJdbcEnum.put(ColumnType.CHAR, JdbcType.CHAR);
	 		mapJdbcEnum.put(ColumnType.VARCHAR, JdbcType.VARCHAR);
	 		mapJdbcEnum.put(ColumnType.VARCHAR2, JdbcType.VARCHAR);
	 		mapJdbcEnum.put(ColumnType.TEXT, JdbcType.VARCHAR);
	 		mapJdbcEnum.put(ColumnType.DOUBLE, JdbcType.DOUBLE);
	 		mapJdbcEnum.put(ColumnType.FLOAT, JdbcType.FLOAT);
	 		mapJdbcEnum.put(ColumnType.INTEGER, JdbcType.INTEGER);
	 		mapJdbcEnum.put(ColumnType.INT, JdbcType.INTEGER);
	 		mapJdbcEnum.put(ColumnType.DATE, JdbcType.DATE);
	 		mapJdbcEnum.put(ColumnType.DATETIME, JdbcType.DATE);
	 		mapJdbcEnum.put(ColumnType.TIME, JdbcType.TIME);
	 		mapJdbcEnum.put(ColumnType.TIMESTAMP, JdbcType.TIMESTAMP);
	 		mapJdbcEnum.put(ColumnType.BLOB, JdbcType.BLOB);
	 		mapJdbcEnum.put(ColumnType.LONGBLOB, JdbcType.BLOB);
	 		mapJdbcEnum.put(ColumnType.LONG, JdbcType.LONGVARCHAR);
	 		mapJdbcEnum.put(ColumnType.CLOB, JdbcType.CLOB);
	 		mapJdbcEnum.put(ColumnType.MEDIUMINT, JdbcType.INTEGER);
	 	}
	 	if(columnType.equals(ColumnType.NUMBER)){
			if(perc<=PREC_INTEGER){
				jdbcType=JdbcType.INTEGER;
			}else if(perc<=PREC_FLOAT){
				jdbcType=JdbcType.FLOAT;
			}
			else {
				jdbcType=JdbcType.DOUBLE;
			}
	 	}else{
		 	jdbcType=mapJdbcEnum.get(columnType);
	 	}
	 	if(jdbcType==null){
		 	  String errMsg=MessageFormat.format("dbType:{0} can't ref to JdbcType",columnType );
		 	  throw new Exception(errMsg);
		} 
	 	return jdbcType;
	}
	
	public static Class getJavaClass(JdbcType jdbcType) throws Exception{
	 	Class javaClass=null;
	 	if(mapJavaClass==null){
	 		mapJavaClass=new HashMap<JdbcType, Class>();
	 		mapJavaClass.put(JdbcType.CHAR,String.class);
	 		mapJavaClass.put(JdbcType.VARCHAR,String.class);
	 		mapJavaClass.put(JdbcType.DOUBLE,Double.class);
	 		mapJavaClass.put(JdbcType.FLOAT,Float.class);
	 		mapJavaClass.put(JdbcType.INTEGER,Integer.class);
	 		mapJavaClass.put(JdbcType.DATE,Date.class);
	 		mapJavaClass.put(JdbcType.TIME,Date.class);
	 		mapJavaClass.put(JdbcType.TIMESTAMP,Timestamp.class);
	 		mapJavaClass.put(JdbcType.BLOB, Byte.class);
	 		mapJavaClass.put(JdbcType.CLOB, String.class);
	 		mapJavaClass.put(JdbcType.LONGVARCHAR,String.class);
	 	}
	 	javaClass=mapJavaClass.get(jdbcType);
	 	if(javaClass==null){
		 	  String errMsg=MessageFormat.format("jdbcType:{0} can't ref to JavaClass",jdbcType );
		 	  throw new Exception(errMsg);
		} 
	 	return javaClass;
	}
	

	*/
	
	
	public static void main(String[] args){
		//ColumnTemplate ct=new ColumnTemplate("xxxx",DB_TYPE_VARCHAR);
	}
	
}
