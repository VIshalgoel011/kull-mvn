/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author lin
 */
public class ContextListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
          System.err.println("appkey:"+sce.getServletContext().getInitParameter("appkey"));
    } 

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
