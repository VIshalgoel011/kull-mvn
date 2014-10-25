package com.kull.api.douban;

import java.util.ArrayList;
import java.util.List;

public class BoardEntity extends BaseEntity {

	protected Board board;
	protected List<SongBubs> song_list=new ArrayList<SongBubs>();
	
	
	public class SongBubs{
		protected List<BubEntity> bubs;
		protected SongEntity song;
		public List<BubEntity> getBubs() {
			return bubs;
		}
		public void setBubs(List<BubEntity> bubs) {
			this.bubs = bubs;
		}
		public SongEntity getSong() {
			return song;
		}
		public void setSong(SongEntity song) {
			this.song = song;
		}
		
	}
	
	public class Board{
		protected String id,thumbnail,title,description;
		protected List<String> song_list;
		protected UserEntity user;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getThumbnail() {
			return thumbnail;
		}
		public void setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public List<String> getSong_list() {
			return song_list;
		}
		public void setSong_list(List<String> song_list) {
			this.song_list = song_list;
		}
		public UserEntity getUser() {
			return user;
		}
		public void setUser(UserEntity user) {
			this.user = user;
		}

	}
	
}
