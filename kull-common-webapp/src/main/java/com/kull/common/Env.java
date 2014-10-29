/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common;

/**
 *
 * @author lin
 */
public class Env {
    
      private static  String _getevn() {
         String mname=Thread.currentThread().getStackTrace()[2].getMethodName();
          return System.getenv("kull-common."+mname);
     }
    
      public final static String dbpath(){
           return _getevn();
      } 
}
