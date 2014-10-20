package com.kull.action;

import java.io.Serializable;


import org.javaclub.jorm.Jorm;
import org.javaclub.jorm.Session;


import com.kull.ObjectHelper;
import com.kull.web.struts.MDActionSupport;


import com.opensymphony.xwork2.Preparable;

public abstract class CrudAction<M extends Serializable> extends MDActionSupport<M> implements Preparable{

	public Session session;
	
	

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		session=Jorm.getSession(true);
	}

	@Override
	protected M readByPk(String pk) {
		// TODO Auto-generated method stub
		return session.read(ObjectHelper.<M>getClazz(this.getClass(), 0), Integer.valueOf(pk));
	}

    @Override
    protected void _create(M m) throws Exception {
        session.save(m);
    }

    @Override
    protected void _update(M m) throws Exception {
      session.update(m);
    }

    @Override
    protected void _delete(M m) throws Exception {
        session.delete(m);
    }

    public M getModel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	
	
	

	
	
}
