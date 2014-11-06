/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.cache;

import com.sun.imageio.plugins.jpeg.JPEG;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.jcs.JCS;
import org.apache.jcs.access.behavior.ICacheAccess;
import org.apache.jcs.access.exception.CacheException;
import org.apache.jcs.engine.behavior.ICompositeCacheAttributes;
import org.apache.jcs.engine.behavior.IElementAttributes;
import org.apache.jcs.engine.control.CompositeCache;

/**
 *
 * @author lin
 */
public class MapCacheAccess<K,V> {

    
    private ICacheAccess cacheAccess;

    public MapCacheAccess(String region) throws CacheException{
       this.cacheAccess= JCS.getInstance(region);
    }
    
    public MapCacheAccess(ICacheAccess cacheAccess) {
        this.cacheAccess = cacheAccess;
    }

    public V get(K k) {
        return (V)cacheAccess.get(k);
    }

    public void putSafe(Object o, Object o1) throws CacheException {
       cacheAccess.putSafe(o, o1);
    }

    public void put(K k, V v) throws CacheException {
        cacheAccess.put(k, v);
    }

    public void put(K k, V v, IElementAttributes iea) throws CacheException {
       cacheAccess.put(k, v,iea);
    }

    public void destroy() throws CacheException {
       cacheAccess.destroy();
    }

    public void remove() throws CacheException {
        cacheAccess.remove();
    }

    public void destroy(Object o) throws CacheException {
        cacheAccess.destroy();
    }

    public void remove(Object o) throws CacheException {
        cacheAccess.remove();
    }

    public void resetElementAttributes(IElementAttributes iea) throws CacheException {
       cacheAccess.resetElementAttributes(iea);
    }

    public void resetElementAttributes(Object o, IElementAttributes iea) throws CacheException {
        cacheAccess.resetElementAttributes(o, iea);
    }

    public IElementAttributes getElementAttributes() throws CacheException {
       return cacheAccess.getElementAttributes();
    }

    public IElementAttributes getElementAttributes(Object o) throws CacheException {
       return cacheAccess.getElementAttributes(o);
    }

    public ICompositeCacheAttributes getCacheAttributes() {
       return cacheAccess.getCacheAttributes();
    }

    public void setCacheAttributes(ICompositeCacheAttributes icca) {
         cacheAccess.setCacheAttributes(icca);
    }

    public int freeMemoryElements(int i) throws CacheException {
        return cacheAccess.freeMemoryElements(i);
    }
    
    public boolean containsKey(K... ks){
         for (K k : ks) {
            if(get(k)==null)return false;
        }
         return true;
    }
    
    public V[] gets(K... ks){
        Object[] objs=new Object[ks.length];
        for(int i=0;i<ks.length;i++)objs[i]=get(ks[i]);
        return (V[])objs;
    }
    
    
    
}
