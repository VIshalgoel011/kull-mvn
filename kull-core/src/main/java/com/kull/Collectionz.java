/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author lin
 */
public class Collectionz {
    
     public static <T> Set<T> asSet(T... vals) {
        Set<T> tset = new HashSet<T>(Arrays.asList(vals));
        return tset;
    }
    
      
     
    

    
     
}
