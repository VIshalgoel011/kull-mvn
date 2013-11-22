package com.kull.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.javaclub.jorm.Jorm;
import org.javaclub.jorm.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class PostgreSqlTest {

	public enum Table{
		ra_coding,ra_demo_form,ra_demo_form_field,
		ra_demo_grid,ra_demo_grid_button,ra_demo_grid_column,ra_demo,ra_demo_tree,
		ra_dict,ra_issue,ra_module,ra_module_menu,ra_project,ra_table,ra_table_column,ra_task,ra_user
	}
	
	Session session;
	//@Before
	public void setUp() throws Exception {
	    session=Jorm.getSession();
	}

	//@After
	public void tearDown() throws Exception {
	}

	//@Test
	public void test() {
		List<Object[]> params=new ArrayList<Object[]>();
		for(int i=0;i<1000;i++){
		   params.add(new Object[]{"code_"+i,"name_"+i});
		}
		session.batchInsert("insert into pm_project (code,name) values (?,?)", params);
        session.commit();
	}
	
	//@Test
	public void drop(){
		String sqlPattern="drop TABLE {0}";
	    for(Table table:Table.values()){
	    	String sql=MessageFormat.format(sqlPattern, table.name());
	    	session.executeUpdate(sql);
	    }
	    session.commit();
	}
	
	
	//@Test
	public void create(){
		String sqlPattern="CREATE TABLE {0}(id serial NOT NULL,code character(10),text character varying(200),remark character varying(1000),create_date date,create_user_code character(10),level integer,CONSTRAINT {0}_pkey PRIMARY KEY (id))";
	    for(Table table:Table.values()){
	    	String sql=MessageFormat.format(sqlPattern, table.name());
	    	session.executeUpdate(sql);
	    }
	    session.commit();
	}
	

}
