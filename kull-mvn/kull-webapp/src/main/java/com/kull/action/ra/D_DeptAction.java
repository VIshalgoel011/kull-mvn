package com.kull.action.ra;

import com.kull.action.CrudAction;
import com.kull.entity.ra.DeptEntity;

public class D_DeptAction extends CrudAction<DeptEntity> {

	@Override
	protected DeptEntity newModel() {
		// TODO Auto-generated method stub
		return new DeptEntity();
	}

}
