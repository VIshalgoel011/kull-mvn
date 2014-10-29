/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common.test;

import com.kull.common.Env;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
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
public class EnvTest {
    
    public EnvTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void hello() {
         Map map = System.getenv();  
Iterator it = map.entrySet().iterator();  
while(it.hasNext())  
{  
    Entry entry = (Entry)it.next();  
    System.out.print(entry.getKey()+"=");  
    System.out.println(entry.getValue());  
}
        String val= Env.dbpath();
         assertEquals(val, "i:/kull-common.db3");
     }
}
