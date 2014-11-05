/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.orm;

import java.sql.Timestamp;
import java.util.Date;
import org.apache.ibatis.type.JdbcType;

/**
 *
 * @author lin
 */
public enum ColumnType{
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
		ColumnType(JdbcType jdbcType,Class javaType){
			this.jdbcType=jdbcType;
			this.javaType=javaType;
			
		}
		

		public JdbcType getJdbcType() {
			return jdbcType;
		}

		public Class getJavaType() {
			return javaType;
		}
		
			final public static JdbcType[]  JDBC_TYPES_STRING=new JdbcType[]{JdbcType.VARCHAR,JdbcType.CHAR};
	final public static JdbcType[]  JDBC_TYPES_DATE=new JdbcType[]{JdbcType.DATE,JdbcType.TIME};
	final public static JdbcType[]  JDBC_TYPES_TIMESTAMP=new JdbcType[]{JdbcType.TIMESTAMP};
	final public static JdbcType[]  JDBC_TYPES_BYTES=new JdbcType[]{JdbcType.BLOB,JdbcType.CLOB};
	}
