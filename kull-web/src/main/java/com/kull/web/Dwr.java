/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web;

import com.kull.web.anntation.DwrAjax;
import com.opensymphony.xwork2.Action;
import java.lang.reflect.Method;

/**
 *
 * @author lin
 */
public class Dwr {
    
    
      public static String jqueryFor(String urlbase,Action action){
          
          String script="$";
          Method[] methods= action.getClass().getDeclaredMethods();
          for(Method method : methods){
             DwrAjax dwr= method.getAnnotation(DwrAjax.class);
             if(dwr==null)continue;
             switch(dwr.method()){
                 case get: script+=".get"; break;
                 case post:script+=".post"; break;
                 default: break;
             }
             String url=urlbase+"/"+method.getName();
             script +="(\"+url+\",params,funcSuccess,funcSuccess)";
             
            switch(dwr.format()){
                 case json: script+=".get"; break;
                 case script:script+=".post"; break;
                 //case script:script+=".post"; break;
                 default: break;
             }
          }
          return script;
      }
    
}
