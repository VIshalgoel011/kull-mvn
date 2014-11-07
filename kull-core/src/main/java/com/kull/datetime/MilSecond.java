/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.datetime;

/**
 *
 * @author lin
 */
public enum MilSecond {

    SECOND(1000),
    MINUTE(SECOND.getMilSecond() * 60),
    HOUR(MINUTE.getMilSecond() * 60),
    DAY(HOUR.getMilSecond() * 24),
    WEEK(DAY.getMilSecond() * 7);

    private long milSecond;

    MilSecond(long milSecond) {
        this.milSecond = milSecond;
    }

    public long getMilSecond() {
        return milSecond;
    }
    
    public long getSecond() {
        return milSecond/1000;
    }

}
