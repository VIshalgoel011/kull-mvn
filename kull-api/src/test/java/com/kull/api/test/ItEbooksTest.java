/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.api.test;

import com.kull.api.ItEbooks;
import com.kull.api.ItEbooks.Book;
import java.io.IOException;
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
public class ItEbooksTest {
    
    public ItEbooksTest() {
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
    // @Test
    public void book() throws IOException {
         Book book= ItEbooks.book("2279690981");
         assertEquals(book.getTitle(), "PHP & MySQL: The Missing Manual");
    }
    
    @Test
    public void search() throws IOException {
         ItEbooks.SearchResult sre= ItEbooks.search("php mysql",1);
         assertEquals(sre.getBooks().size(), 10);
    }
}
