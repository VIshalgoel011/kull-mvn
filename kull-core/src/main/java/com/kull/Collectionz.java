/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author lin
 */
public class Collectionz {
    
     public static <T> Set<T> newSet(T... vals) {
        Set<T> tset = new HashSet<T>();
        for (T val : vals) {
            tset.add(val);
        }
        return tset;
    }
    
      
     
     public static <T> List<T> newList(T... ts) {
        List<T> list = new ArrayList<T>();
        for (T t : ts) {
            list.add(t);
        }
        return list;
    }

    
     
}
