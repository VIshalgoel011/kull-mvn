/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common.action;


import com.kull.common.Utils;
import com.kull.orm.Session;
import com.kull.web.struts.JpaMDActionSupport;
import com.kull.web.struts.OrmMDActionSupport;
import com.opensymphony.xwork2.Preparable;
import java.sql.Connection;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 *
 * @author lin
 */
public abstract class MDAction<M> extends JpaMDActionSupport<M> implements Preparable{

    protected String namespace,action;
    
    protected static EntityManagerFactory EMF=Persistence.createEntityManagerFactory("kull-common");
    
     public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    @Override
    public void prepare() throws Exception {
        setEntityManager(EMF.createEntityManager());
    }

    
    
    
}
