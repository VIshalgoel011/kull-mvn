package com.kull.action.sa;

import com.kull.action.CrudAction;
import com.kull.entity.sa.DictEntity;

public class D_DictAction extends CrudAction<DictEntity> {

	@Override
	protected DictEntity newModel() {
		// TODO Auto-generated method stub
		return new DictEntity();
	}

}
