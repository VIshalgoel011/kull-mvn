/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common;


import com.kull.IOHelper;
import com.kull.bean.JdbcBean;
import java.sql.Connection;


/**
 *
 * @author lin
 */
public class Utils extends com.kull.web.Utils{
    
    public static Connection dbmeta() throws Exception{
        String dbpath=Utils.class.getClassLoader().getResource("kull-common.db3").getFile();
        return JdbcBean.createSqllteConnection(dbpath);
        
    }
    
    
}
