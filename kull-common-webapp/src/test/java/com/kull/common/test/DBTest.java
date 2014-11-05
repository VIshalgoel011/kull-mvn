package com.kull.common.test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.kull.common.Utils;
import com.kull.orm.Database;
import com.kull.orm.dialect.SqliteDialect;
import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author lin
 */
public class DBTest {
    
    Connection conn_ayhhlin,conn_kullcommon;


    
    public DBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        // conn_ayhhlin= Database.createConnection(Driver.class, "hhlin", "111111", "hhlin54133.mysql.rds.aliyuncs.com", "uci");
        conn_kullcommon=Database.createSqllteConnection("i:/kull-common.db3");
        
      
       // jdbcBean=new JdbcBean(Utils.dbmeta());
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void dbmeta() throws SQLException, Exception {
          SqliteDialect dialect=new SqliteDialect();
         String context=dialect.dbmlScript(conn_kullcommon, "com.kull.orm.KullCommonDBML");
           System.err.println(context);
         
     }
     
     
}
