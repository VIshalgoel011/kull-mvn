/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web.struts;



/**
 *
 * @author lin
 */
public abstract class MonthLSAction extends LSActionSupport{
    
    protected int year,month;

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }
    
    
    
}
