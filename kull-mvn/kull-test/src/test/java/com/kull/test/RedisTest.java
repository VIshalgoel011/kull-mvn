package com.kull.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisTest {

	private Jedis jedis;
	
	//@Before
	public void setUp() throws Exception {
		jedis=new Jedis("127.0.0.1");
	}

	//@After
	public void tearDown() throws Exception {
	}

	//@Test
	public void test() {
		
		jedis.set("java", this.getClass().getName());
	}

}
