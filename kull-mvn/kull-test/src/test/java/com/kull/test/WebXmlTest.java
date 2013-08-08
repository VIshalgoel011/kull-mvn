package com.kull.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.kull.webxml.WeatherWebService;

public class WebXmlTest {

	WeatherWebService weatherWebService;
	
	//@Before
	public void setUp() throws Exception {
		weatherWebService=new WeatherWebService();
	}

	//@After
	public void tearDown() throws Exception {
	}

	//@Test
	public void test() throws Exception {
	   List<String> provs=	weatherWebService.getSupportProvince();
	   
	   for(String prov :provs){
		   System.out.println(prov);
	   }
	}

}
