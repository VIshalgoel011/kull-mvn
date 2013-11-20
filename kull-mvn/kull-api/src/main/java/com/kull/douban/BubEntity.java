package com.kull.douban;

public class BubEntity {

	protected String id,content,time;
	protected SongEntity song;
	protected UserEntity user;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public SongEntity getSong() {
		return song;
	}
	public void setSong(SongEntity song) {
		this.song = song;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	
}
