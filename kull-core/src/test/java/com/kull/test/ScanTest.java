/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.test;

import com.kull.Clazz;
import java.io.IOException;
import java.util.List;
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
public class ScanTest {
    
    public ScanTest() {
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
     public void hello() throws IOException, ClassNotFoundException {
         
         List<Class<? extends Object>> thClasses=Clazz.genericClassIn(Clazz.class.getPackage(),Object.class);
         for (Class<? extends Object> thClasse : thClasses) {
             System.out.println(thClasse.getName());
         }
     }
}
