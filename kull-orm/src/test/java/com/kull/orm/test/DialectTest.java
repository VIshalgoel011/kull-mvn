/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.orm.test;

import com.kull.orm.Database;
import com.kull.orm.dialect.MySQLDialect;
import java.sql.Connection;
import com.mysql.jdbc.Driver;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lin
 */
public class DialectTest {
    
    
    Connection conn_ayhhlin;
    
    
    public DialectTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        
       conn_ayhhlin= Database.createConnection(Driver.class, "hhlin", "111111", "hhlin54133.mysql.rds.aliyuncs.com", "uci");
        
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void hello() throws Exception {
           MySQLDialect dialect=new MySQLDialect();
           String context=dialect.createTableDBML(conn_ayhhlin, "com.kull.orm.DefaultDBML");
           System.err.println(context);
     }
}
