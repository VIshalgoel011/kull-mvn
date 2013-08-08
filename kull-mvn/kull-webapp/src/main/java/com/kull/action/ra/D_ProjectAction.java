package com.kull.action.ra;

import com.kull.action.CrudAction;
import com.kull.entity.ra.ProjectEntity;



public class D_ProjectAction extends CrudAction<ProjectEntity> {

	

	@Override
	protected ProjectEntity newModel() {
		// TODO Auto-generated method stub
		return new ProjectEntity();
	}

}
