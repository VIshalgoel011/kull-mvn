/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common;

import com.kull.IOHelper;
import com.kull.bean.JdbcBean;
import java.sql.Connection;
import java.text.MessageFormat;

/**
 *
 * @author lin
 */
public class Utils {
    
    public static Connection dbmeta() throws Exception{
        String dbpath="I:\\ws-nb\\kull-mvn\\kull-common-webapp\\src\\main\\resources\\kull.db3";
        return JdbcBean.createSqllteConnection(dbpath);
        
    }
    
    
}
