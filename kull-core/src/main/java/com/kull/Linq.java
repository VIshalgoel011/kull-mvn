package com.kull;

import com.kull.able.Filterable;
import com.kull.able.Foreachable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;






public  class Linq {

  
    
       public static <E> void foreach(Iterable<E> list,Foreachable<E> foreach){
    	
           int i=0;
           for(Iterator<E> it=list.iterator();it.hasNext();){
            foreach.each(i++, it.next());
        }
    }
	
	
	
	public  static <E> List<E> where(Iterable<E> list,Filterable<E> filter){
		ArrayList<E> dlist=new ArrayList<E>();
                int i=0;
		for(Iterator<E> it=list.iterator();it.hasNext();){
			E e=it.next();
			if(filter.filter(i, e))dlist.add(e);
                        i++;
		}
		return dlist;
	} 
        
	
	public  static <E> E single(Iterable<E> list,Filterable<E> filter) throws NullPointerException{
		List<E> dlist=where(list, filter);
		if(dlist.size()!=1)throw new NullPointerException();
		return dlist.get(0);
	}
        
        public  static <E> E singleOrDefault(Iterable<E> list,Filterable<E> filter){
		List<E> dlist=where(list, filter);
		if(dlist.size()==0)return null;
		return dlist.get(0);
	}
        
        
    
    public static <E> HashSet<E>  union(Iterable<E> ... colls){
    	HashSet<E> dset=new HashSet<E>();
    	for(Iterable<E> coll :colls){
            for(Iterator<E> it=coll.iterator();it.hasNext();){
            	dset.add(it.next());
            }
    	}
    	return dset;
    }
    
    public static <E> HashSet<E> intersect(Iterable<E> ... colls){
    	HashSet<E>dset=new HashSet<E>();
    	for(Iterable<E> coll :colls){
            for(Iterator<E> it=coll.iterator();it.hasNext();){
            	E e=it.next();
            	if(dset.contains(e))continue;
            	if(isIn(e, colls))dset.add(it.next());
            }
    	}
    	return dset;
    }
    
    public static <E> boolean isIn(E e,Iterable<E> ... colls){
    	for(Iterable<E> coll :colls){
            for(Iterator<E> it=coll.iterator();it.hasNext();){
            	if(Clazz.isEquals(e, it.next()))return true;
            }
    	}
    	return false;
    }
    
    public static <E> boolean isNotIn(E e,Iterable<E> ... colls){
    	return !isIn(e, colls);
    }
    
    public static <E> boolean isIn(E e,E... colls){
    	for(E tempE : colls){
            	if(Clazz.isEquals(e, tempE))return true;
        }
    	
    	return false;
    }
    
    public static <E> boolean isNotIn(E e,E... colls){
    	return !isIn(e, colls);
    }
    
   
   
    
    /*
    public static <L extends Collection<E>,E> Collection<E>[] update(IUpdateable<E> iupdater,L ... colls){
    	L[] dcolls=(L[])new Collections[colls.length];
    	for(int i=0;i<colls.length;i++){
    		L coll =colls[i];
            for(Iterator<E> it=coll.iterator();it.hasNext();){
            	E e=it.next();
            	E updateE=iupdater.update(e);
            	coll.add(updateE);
            }
            dcolls[i]=coll;
    	}
    	return dcolls;
    }
    */
    
    
    
    
 

        public static <M> M selectByPK(String pkName,Object pkval,List<M> list)
        {
            M m = null;
            for (int i=0;i<list.size();i++)
            {
                M l = list.get(i);
                try
                {
                    if (Clazz.isEquals(pkval.toString(),Clazz.attr(l, pkName).toString()))
                    {
                        m = l;
                    }
                }catch(Exception ex){}
            }
           
            return m;
        }

        

        public static <M>  int  indexOfByPk(String pkName,Object pkval, List<M> list)
        {
            int index = -1;
            for (int i = 0; i < list.size(); i++)
            {
                M l = list.get(i);
                try
                {
                     if (Clazz.isEquals(pkval.toString(),Clazz.attr(l, pkName).toString()))
                    {
                        index = i;
                        break;
                    }
                }
                catch(Exception ex){}
            }

            return index;
        }
}
