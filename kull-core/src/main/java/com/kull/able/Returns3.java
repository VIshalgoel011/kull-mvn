/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.able;

/**
 *
 * @author lin
 */
public class Returns3 <R0,R1,R2> extends Returns2<R0, R1>{

    public Returns3(R0 r0, R1 r1,R2 r2) {
        super(r0, r1);
        this.r2=r2;
    }

    public R2 getR2() {
        return r2;
    }

    
    
    protected R2 r2;
    
    
    
}
