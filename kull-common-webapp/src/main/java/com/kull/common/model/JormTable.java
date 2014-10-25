/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common.model;

import com.kull.orm.annotation.OrmTable;
import javax.persistence.Id;


/**
 *
 * @author lin
 */

public class JormTable {
    
   
   @OrmTable(name = "dbmeta_conn",pk ="id")
    public class dbmeta_conn{
       
       @Id
       protected Integer id;
       
        protected String name,descp,url,user,pwd,driver;
        

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescp() {
            return descp;
        }

        public void setDescp(String descp) {
            this.descp = descp;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getDriver() {
            return driver;
        }

        public void setDriver(String driver) {
            this.driver = driver;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
        
        
        
    }
    
}
