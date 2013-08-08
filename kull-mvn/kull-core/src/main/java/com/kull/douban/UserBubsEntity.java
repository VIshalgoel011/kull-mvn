package com.kull.douban;

import java.util.ArrayList;
import java.util.List;

public class UserBubsEntity extends BaseEntity{

	protected List<BubEntity> result=new ArrayList<BubEntity>();

	public List<BubEntity> getResult() {
		return result;
	}

	public void setResult(List<BubEntity> result) {
		this.result = result;
	}
	
	
	
}
