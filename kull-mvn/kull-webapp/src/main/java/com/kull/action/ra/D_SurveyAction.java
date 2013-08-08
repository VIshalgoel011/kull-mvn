package com.kull.action.ra;

import com.kull.action.CrudAction;
import com.kull.entity.ra.SurveyEntity;

public class D_SurveyAction extends CrudAction<SurveyEntity> {

	@Override
	protected SurveyEntity newModel() {
		// TODO Auto-generated method stub
		return new SurveyEntity();
	}

}
