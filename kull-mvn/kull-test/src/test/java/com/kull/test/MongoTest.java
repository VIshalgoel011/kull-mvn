package com.kull.test;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoTest {

	Mongo mongo;
	DB db;

	//@Before
	public void setUp() throws Exception {
		mongo=new Mongo("127.0.0.1");
		db=mongo.getDB("kull");
	}

	//@After
	public void tearDown() throws Exception {
	}

	//@Test
	public void test() {
		for(String name :mongo.getDatabaseNames()){
			System.out.println(name);
		}
		for(String name :db.getCollectionNames()){
			System.out.println(name);
		}
		DBCollection coll= db.getCollection("yl_hotel");
		DBObject dbquery=new BasicDBObject();
		//dbquery.put("hotel_id", "92503002");
		DBCursor dbCursor =coll.find(dbquery);
		
		while(dbCursor.hasNext()){
			DBObject obj=dbCursor.next();
			//obj.put("newname", "新保存的");
			//coll.update(obj, obj);
			System.out.println(obj.toString());
		}
	
	}

}
