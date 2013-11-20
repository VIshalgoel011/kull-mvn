package com.kull.douban;

public class UserEntity  {

	protected String id,uid,title,homepage,icon;
	protected StatsEntity stats;
	
	
	
	
	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getUid() {
		return uid;
	}




	public void setUid(String uid) {
		this.uid = uid;
	}




	public String getTitle() {
		return title;
	}




	public void setTitle(String title) {
		this.title = title;
	}




	public String getHomepage() {
		return homepage;
	}




	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}




	public String getIcon() {
		return icon;
	}




	public void setIcon(String icon) {
		this.icon = icon;
	}




	public StatsEntity getStats() {
		return stats;
	}




	public void setStats(StatsEntity stats) {
		this.stats = stats;
	}




	public class StatsEntity{
		protected int bub,collect,board;

		public int getBub() {
			return bub;
		}

		public void setBub(int bub) {
			this.bub = bub;
		}

		public int getCollect() {
			return collect;
		}

		public void setCollect(int collect) {
			this.collect = collect;
		}

		public int getBoard() {
			return board;
		}

		public void setBoard(int board) {
			this.board = board;
		}
		
		
	}
}
