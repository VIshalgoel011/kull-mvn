/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web.struts;

import javax.persistence.EntityManager;

/**
 *
 * @author lin
 */
public abstract class JpaMDActionSupport<M> extends MDActionSupport<M>{

    protected EntityManager entityManager;
            
    public void setEntityManager(EntityManager EntityManager) {
        this.entityManager = EntityManager;
    }      
    
    @Override
    protected M readByPk(String pk) throws Exception {
       return entityManager.find(classM(), pk);
    }

    @Override
    protected void _create(M m) throws Exception {
        entityManager.persist(m);
    }

    @Override
    protected void _update(M m) throws Exception {
       
    }

    @Override
    protected void _delete(M m) throws Exception {
       entityManager.remove(m);
    }
    
}
