package com;

import java.text.MessageFormat;
import java.util.List;


public class Console {

	public static void println(String pattern,Object... vals){
		System.out.println(MessageFormat.format(pattern, vals));
	}
	
	public static void println(Object pattern){
		
		System.out.println(pattern==null?"null":pattern.toString());
	}
	
	public static void println(List<? extends Object> patterns){
		for (Object pattern : patterns) {
			if(pattern==null)continue;
			println(pattern.toString());
		}
	}
	
	public static void println(String[] patterns){
		for (String pattern : patterns) {
			if(pattern==null)continue;
			System.out.println(pattern);
		}
	}
	
	public static void println(List<String> patterns,Object... vals){
		for (String pattern : patterns) {
			println(pattern, vals);
		}
	}
}
