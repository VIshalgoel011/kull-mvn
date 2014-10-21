/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.common.console;

import com.kull.StringHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author lin
 */
public abstract class Console {
    
    protected final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
     protected  void println(String... msgs){
         for(String msg:msgs){
           System.out.println(msg);
         }

     }
     
     protected void print(String... msgs){
         for(String msg:msgs){
           System.out.print(msg);
         }
     }
     
     protected void ln(int times){
         print(StringHelper.ln(times));
     }
     
     protected void tab(int times){
         print(StringHelper.tab(times));
     }
     
     protected String prompt(String alert) throws IOException{
         print(alert);
         return reader.readLine();
     }
     
     protected boolean confirm(String alert) throws IOException{
         println(alert);
         println("please input (y/n):");
         String re= reader.readLine();
         return re.toLowerCase().equals("y");
     }
    
     public abstract void welcome();
     
     public void loop(){
          
     }
}
