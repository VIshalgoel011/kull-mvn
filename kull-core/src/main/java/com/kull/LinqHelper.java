package com.kull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kull.util.IMapable;
import com.kull.util.IQueryable;
import com.kull.util.IUpdateable;

public  class LinqHelper {

	
	
	public  static <E> ArrayList<E>  where(Collection<E> list,IQueryable<E>... iquerys){
		
		return where(list, list.size(), iquerys);
	}
	
	public  static <E> ArrayList<E> where(Collection<E> list,int limit,IQueryable<E>... iquerys){
		ArrayList<E> dlist=new ArrayList<E>();
		int size=list.size();
		for(Iterator<E> it=list.iterator();it.hasNext();){
			E e=it.next();
			if(query(e, iquerys))dlist.add(e);
			if(size==limit)break;
		}
		return dlist;
	} 
	
	public  static <E> E single(Collection<E> list,IQueryable<E>... iquerys) throws NullPointerException{
		ArrayList<E> dlist=where(list, 1,iquerys);
		if(dlist.size()==0)throw new NullPointerException();
		return dlist.get(0);
	}
	
    public  static <L extends List<E>,E> E single(Collection<E> list,E defE,IQueryable<E>... iquerys) {
    	ArrayList<E> dlist=where(list, 1,iquerys);
		if(dlist.size()==0)return defE;
		return dlist.get(0);
	}
    
    public static <E> HashSet<E>  union(Collection<E> ... colls){
    	HashSet<E> dset=new HashSet<E>();
    	for(Iterable<E> coll :colls){
            for(Iterator<E> it=coll.iterator();it.hasNext();){
            	dset.add(it.next());
            }
    	}
    	return dset;
    }
    
    public static <E> HashSet<E> intersect(Collection<E> ... colls){
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
    
    public static <E> boolean isIn(E e,Collection<E> ... colls){
    	for(Iterable<E> coll :colls){
            for(Iterator<E> it=coll.iterator();it.hasNext();){
            	if(ObjectHelper.isEquals(e, it.next()))return true;
            }
    	}
    	return false;
    }
    
    public static <E> boolean isNotIn(E e,Collection<E> ... colls){
    	return !isIn(e, colls);
    }
    
    public static <E> boolean isIn(E e,E... colls){
    	for(E tempE : colls){
            	if(ObjectHelper.isEquals(e, tempE))return true;
        }
    	
    	return false;
    }
    
    public static <E> boolean isNotIn(E e,E... colls){
    	return !isIn(e, colls);
    }
    
    public static <E> boolean query(E e,IQueryable<E>... iquerys){
    	for(IQueryable<E> iquery :iquerys){
    		if(!iquery.query(e))return false;
    	}
    	return true;
    }
    
    
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
    
    public static <L extends Collection<E>,E> Collection<E>[] update(IUpdateable<E> iupdater,IQueryable<E> iquery,L ... colls){
    	L[] dcolls=(L[])new Collections[colls.length];
    	for(int i=0;i<colls.length;i++){
    		L coll =colls[i];
            for(Iterator<E> it=coll.iterator();it.hasNext();){
            	E e=it.next();
            	if(iquery.query(e)){
            	  E updateE=iupdater.update(e);
            	  coll.add(updateE);
            	}
            }
            dcolls[i]=coll;
    	}
    	return dcolls;
    }
    
    public static <K,E> HashMap<K,E> toMap(IMapable<E> imap,Collection<E> ... colls){
    	HashMap<K,E> map=new HashMap<K,E>();
    	for(Collection<E> coll :colls){
            for(Iterator<E> it=coll.iterator();it.hasNext();){
            	E e=it.next();
            	K key=imap.key(e);
            	if(map.containsKey(key))continue;
            	map.put(key, e);
            }
    	}
    	return map;
    }
    
 

        public static <M> M selectByPK(String pkName,Object pkval,List<M> list)
        {
            M m = null;
            for (int i=0;i<list.size();i++)
            {
                M l = list.get(i);
                try
                {
                    if (ObjectHelper.isEquals(pkval.toString(),ObjectHelper.attr(l, pkName).toString()))
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
                     if (ObjectHelper.isEquals(pkval.toString(),ObjectHelper.attr(l, pkName).toString()))
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
