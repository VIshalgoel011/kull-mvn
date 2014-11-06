/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.test;

import com.kull.ObjectHelper;
import com.kull.test.InnerClassTest.Inner.Inner0.Inner1.Inner2;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
public class InnerClassTest {
    
    
    
    public class Inner{
    
        public class Inner0{
        
             public class Inner1{
               
                  public class Inner2{}
             }
        }
        
    }
    
    public InnerClassTest() {
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
     public void newInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
         
         Inner2 inner=ObjectHelper.newInstance(Inner2.class);
         InnerClassTest outter=ObjectHelper.newInstance(InnerClassTest.class);
         assertNotNull(inner);
         assertNotNull(outter);
     }
}
