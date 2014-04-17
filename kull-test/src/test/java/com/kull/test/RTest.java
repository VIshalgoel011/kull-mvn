package com.kull.test;

import java.lang.reflect.Field;

import org.junit.Test;

import com.Console;

public class RTest {

	
	@Test
	public void testRef() throws IllegalArgumentException, IllegalAccessException{
		
	  Class[] clss=  R.class.getDeclaredClasses();
	  
	  for(Class cls :clss){
		  Console.println(cls.getSimpleName());
		  Field[] fields=cls.getDeclaredFields();
		  for(Field field :fields){
			  Console.println(field.getName()+"="+field.getInt(cls));
		  }
	  }
	}
}
