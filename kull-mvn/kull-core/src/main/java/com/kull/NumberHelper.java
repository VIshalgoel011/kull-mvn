package com.kull;

public final class NumberHelper {

	
	public static <N extends Number> N max(N[] numbers){
	     N v=null;
	     for(int i=0,j=1;i<numbers.length-1;i++,j++){
	    	 v=numbers[i].byteValue()>=numbers[j].byteValue()?numbers[i]:numbers[j];
	     }
	     return v;
	}
	
	public static <N extends Number> N min(N[] numbers){
	     N v=null;
	     for(int i=0,j=1;i<numbers.length-1;i++,j++){
	    	 v=numbers[i].byteValue()<=numbers[j].byteValue()?numbers[i]:numbers[j];
	     }
	     return v;
	}
	
	public static <N extends Number> N avg(N[] numbers){
		Double v=0.00;
		for(int i=0;i<numbers.length;i++){
	    	 v=v+numbers[i].doubleValue();
	    }
		v=v/numbers.length;
		return (N)v; 
	}
}
