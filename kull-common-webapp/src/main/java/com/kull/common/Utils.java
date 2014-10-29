/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common;


import com.kull.orm.Database;
import java.sql.Connection;


/**
 *
 * @author lin
 */
public class Utils extends com.kull.web.Utils{
    
    public static Connection dbmeta() throws Exception{
    
        return Database.createSqllteConnection(Env.dbpath());
        
    }
    
    
}
