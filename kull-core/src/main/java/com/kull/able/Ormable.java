package com.kull.able;

public interface Ormable<T> {

	public T getPk();
        
        public String table();

}
