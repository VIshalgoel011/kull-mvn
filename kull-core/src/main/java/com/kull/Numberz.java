package com.kull;

public final class Numberz {

	
	
	 
	public static <N extends Number> N avg(N... numbers){
		Double v=0.00;
		for(int i=0;i<numbers.length;i++){
	    	 v=v+numbers[i].doubleValue();
	    }
		v=v/numbers.length;
		return (N)v; 
	}
}
