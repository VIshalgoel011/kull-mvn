package com.kull.orm.able;

public interface Ormable<T> {

	public T getPk();
        
        public String tableName();

}
