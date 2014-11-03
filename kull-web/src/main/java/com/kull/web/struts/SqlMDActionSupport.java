/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web.struts;

import com.kull.orm.Session;
import java.sql.Connection;

/**
 *
 * @author lin
 */
public abstract class SqlMDActionSupport<M> extends MDActionSupport<M>{
    
    protected Connection connection;
     protected Session session;

   

    public void setConnection(Connection connection) {
        this.connection = connection;
        this.session=new Session(this.connection);
    }
    
    

    @Override
    protected M readByPk(String pk) throws Exception{
          return   session.load(newModel(), pk); 
        
    }

    @Override
    protected void _create(M m) throws Exception {
       session.insert(m);
    }

    @Override
    protected void _update(M m) throws Exception {
       session.update(m);
    }

    @Override
    protected void _delete(M m) throws Exception {
        session.delete(m);
    }
    
}
