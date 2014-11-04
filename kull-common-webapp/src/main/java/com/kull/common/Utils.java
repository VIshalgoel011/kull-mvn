/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common;


import com.kull.orm.Database;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author lin
 */
public class Utils extends com.kull.web.Utils{
    
    
    public static String[] modes(HttpServletRequest req){
       String mode=req.getParameter("mode");
       return mode==null?new String[0]:mode.split(",");
    }
    
    public static Connection dbmeta() throws Exception{
    
        return Database.createSqllteConnection(Env.dbpath());
        
    }
    
    
}
