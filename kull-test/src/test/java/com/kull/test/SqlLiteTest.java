package com.kull.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;


import org.javaclub.jorm.Jorm;
import org.javaclub.jorm.Session;
import org.javaclub.jorm.jdbc.sql.SqlParams;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sqlite.*;

import com.kull.bean.JdbcBean;



public class SqlLiteTest {


	SQLiteDataSource sqllitLiteDataSource=new SQLiteDataSource();
    JDBC jdbc;
  
	String url="jdbc:sqlite:K:/ws-sts/kull-mvn/kull-webapp/src/main/resources/kull.db3";
	Connection connection;
	JdbcBean jdbcEntity;
	//@Before
	public void setUp() throws Exception {
		sqllitLiteDataSource.setUrl(url);
		connection= sqllitLiteDataSource.getConnection();
		jdbcEntity=new JdbcBean(connection);
		Session se;
	    

	}

	//@After
	public void tearDown() throws Exception {
	}

	//@Test
	public void test() throws SQLException {
		String sql="create table pm_project( id int,name varchar(200) )";
		jdbcEntity.executeUpdate(sql);
	}

}
