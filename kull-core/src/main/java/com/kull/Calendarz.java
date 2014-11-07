package com.kull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;



public class Calendarz {

	
      private static final  Calendar calendar=Calendar.getInstance();
	
	
	
	
	
	
	
	
	
	public static Set<Date> weekDays(Date pDt)
	{

		if(pDt==null)return null;
		Set<Date> lListReturn=new HashSet<Date>();
		
		calendar.setTime(pDt);
		int weekday=calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.DAY_OF_WEEK, -(weekday-1));
		int t=0;
		while(t++<7)
		{
			calendar.add(Calendar.DATE, 1);
			lListReturn.add(calendar.getTime());
		}
		return lListReturn;
	}
	
	public static Set<Date> weekDays(int year,int weeknum)
	{
		Set<Date> lListReturn=new HashSet<Date>();
		
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.WEEK_OF_YEAR, weeknum);
		calendar.set(Calendar.DAY_OF_WEEK, 0);
		lListReturn=weekDays(calendar.getTime());
		//System.out.println(calendar.getTime().toString());
		return lListReturn;
		
	}
	
	public static String format(java.util.Date date,String dateFormat) {
		 if(date == null)
			 return null;
		 return new SimpleDateFormat(dateFormat).format(date);
	}
	
	
	public static int getWeekOfYear(Date date)
	{
		
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	
	
	
	
	
	
	
	
	public static Date attr(Date date,int field,int value){
		
		calendar.setTime(date);
		calendar.set(field, value);
		//date=calendar.getTime();
		return calendar.getTime();
	}
	

	
	public static int attr(Date date,int field){
		
		calendar.setTime(date);
		return calendar.get(field);
		//return calendar.getTime();
	}
	
	public static boolean isEquals(int field,Date... dates){
	      if(dates.length<=1)return true;
	      boolean isSame=true;

	      for(int i=0,j=1;i<dates.length-1;i++,j++){
	    	 calendar.setTime(dates[i]);
	    	 int attr0=calendar.get(field);
	    	 calendar.setTime(dates[j]);
	    	 int attr1=calendar.get(field);
	    	 if(attr0!=attr1){
	    		 isSame=false;
	    		 break;
	    	 }
	      }
	      return isSame;
	}
	
	public static boolean isSameYear(Date... dates){
	      return isEquals(Calendar.YEAR, dates);
	}
	
	public static boolean isSameMonth(Date... dates){
	      return isSameYear(dates)&&isEquals(Calendar.MONTH, dates);
	}

	public static boolean isSameDay(Date... dates){
	      return isSameYear(dates)&&isEquals(Calendar.DAY_OF_YEAR, dates);
	}
	
	public static boolean isSameHour(Date... dates){
	      return isSameDay(dates)&&isEquals(Calendar.HOUR_OF_DAY, dates);
	}
	
	public static boolean isSameMinute(Date... dates){
	      return isSameHour(dates)&&isEquals(Calendar.MINUTE, dates);
	}
	
	public static boolean isSameMoment(Date... dates){
	      return isSameMinute(dates)&&isEquals(Calendar.SECOND, dates);
	}
	
}
