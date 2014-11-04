/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common.model;

import com.kull.able.Ormable;
import com.kull.orm.annotation.OrmTable;
import javax.persistence.Id;

/**
 *
 * @author lin
 */
public class KullCommonDBML {
    
    
    
    public class mp_driver implements Ormable<Integer>{
       
           protected Integer id;
           
           protected String type,handler_name,text_pattern,text_param,image_url;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getHandler_name() {
            return handler_name;
        }

        public void setHandler_name(String handler_name) {
            this.handler_name = handler_name;
        }

        public String getText_pattern() {
            return text_pattern;
        }

        public void setText_pattern(String text_pattern) {
            this.text_pattern = text_pattern;
        }

        public String getText_param() {
            return text_param;
        }

        public void setText_param(String text_param) {
            this.text_param = text_param;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        @Override
        public Integer getPk() {
            return getId();
        }

        @Override
        public String table() {
            return this.getClass().getSimpleName();
        }
        
    }
    

    
    @OrmTable(name = "dbmeta_conn",pk ="id")
    public class dbmeta_conn {
       
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
