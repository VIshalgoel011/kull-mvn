package com.kull.action.ra;

import com.kull.action.CrudAction;
import com.kull.entity.ra.MemberEntity;


public class D_MemberAction extends CrudAction<MemberEntity> {

	@Override
	protected MemberEntity newModel() {
		// TODO Auto-generated method stub
		return new MemberEntity();
	}

}
