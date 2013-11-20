package com.kull.struts;

import java.io.File;

public abstract class AttachAction extends CrudActionSupport<Attach> {

	private File file;

	@Override
	protected Attach readByPk(String pk) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void upload(){
		
	}
	
	public void download(){
		
	}
	
	
	
}
