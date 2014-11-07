/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.datetime;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author lin
 */
public enum TimeFormatter {
    
    
  
		HMS ("HH:mm:ss"),HM ("HH:mm")
		;
		
		String pattern;
		SimpleDateFormat simpleDateFormat;
		TimeFormatter(String patern){
			this.pattern=patern;
			this.simpleDateFormat=new SimpleDateFormat(patern);
		}
		public String getPattern() {
			return pattern;
		}
		public SimpleDateFormat getSimpleDateFormat() {
			return simpleDateFormat;
		}
		
	    public String format(Time  time){
	    	return simpleDateFormat.format(time);
	    }
	    
	   
    
}
