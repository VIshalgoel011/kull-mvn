package com.kull.api.douban;

import java.text.MessageFormat;

import com.google.gson.Gson;
import com.kull.Netz;


public  class LabsBubblerApi {

	public final String baseUrl="http://api.douban.com/labs/bubbler";
	 
	
	public UserEntity user(String uid)throws Exception{
		UserEntity entity=new UserEntity();
		String url=MessageFormat.format(baseUrl+"/user/{0}", uid);
		String context=Netz.getString(url);
		Gson gson=new Gson();
		entity= gson.fromJson(context, UserEntity.class);
		//entity=
		return entity;
	}
	
	public UserBubsEntity userBubs(String uid)throws Exception{
		UserBubsEntity e=new UserBubsEntity();
		String url=MessageFormat.format(baseUrl+"/user/{0}/bubs", uid);
		String context=Netz.getString(url);
		Gson gson=new Gson();
		e= gson.fromJson(context,UserBubsEntity.class);
		//entity=
		return e;
	}
	
	public WallEntity wall(String uid) throws Exception{
		WallEntity entity=new WallEntity();
		String url=MessageFormat.format(baseUrl+"/wall/{0}", uid);
		String context=Netz.getString(url);
		Gson gson=new Gson();
		entity= gson.fromJson(context, WallEntity.class);
		//entity=
		return entity;
	}
	
	public BoardEntity board(String id)throws Exception{
		BoardEntity e=new BoardEntity();
		String url=MessageFormat.format(baseUrl+"/board/{0}", id);
		String context=Netz.getString(url);
		Gson gson=new Gson();
		e= gson.fromJson(context,BoardEntity.class);
		//entity=
		return e;
	}
}
