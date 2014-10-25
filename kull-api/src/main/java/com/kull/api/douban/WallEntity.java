package com.kull.api.douban;

import java.util.ArrayList;
import java.util.List;

import com.kull.api.douban.BoardEntity.Board;

public class WallEntity extends BaseEntity {

	protected UserEntity user;
	protected List<Board> board_list=new ArrayList<Board>();
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public List<Board> getBoard_list() {
		return board_list;
	}
	public void setBoard_list(List<Board> board_list) {
		this.board_list = board_list;
	}
	
	
}
