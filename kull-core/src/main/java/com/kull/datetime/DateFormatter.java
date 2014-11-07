/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.datetime;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author lin
 */
public enum DateFormatter {

    YMD("yyyy-MM-dd"), ymd("yyyy/MM/dd"), dym("dd/MM/yyyy"), YMDHMSS("yyyy-MM-dd HH:mm:ss.S"), YMDHMS("yyyy-MM-dd HH:mm:ss");

    String pattern;
    SimpleDateFormat simpleDateFormat;

    DateFormatter(String patern) {
        this.pattern = patern;
        this.simpleDateFormat = new SimpleDateFormat(patern);
    }

    public String getPattern() {
        return pattern;
    }

    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public String format(Date date) {
        return simpleDateFormat.format(date);
    }

    public Date parse(String pattern) throws ParseException {
        return simpleDateFormat.parse(pattern);
    }

    public static Date parsez(String dateString) throws ParseException {

        Date dateReturn = null;
        for (DateFormatter dateFormat : DateFormatter.values()) {

            if (dateString.trim().length() != dateFormat.pattern.length()) {
                continue;
            }
            dateReturn = dateFormat.parse(dateString);

        }
        return dateReturn;
    }
    
    public static Date parsez(String dateString,String pattern) throws ParseException {
       
       
        DateFormatter dateFormat=DateFormatter.valueOf(pattern);
        return dateFormat.parse(dateString);
        
    }

}
