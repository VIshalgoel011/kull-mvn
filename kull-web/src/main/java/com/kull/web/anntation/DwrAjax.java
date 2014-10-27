/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web.anntation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author lin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) 
public @interface DwrAjax {
    
    Method method() default Method.get;
    
    Format format() default Format.json;
    
    String[] params() default {};
    
    
    public enum Method {
     get,post,delete,put
}
    
   public enum Format{
    json,xml,script,content
}
      
      
}
