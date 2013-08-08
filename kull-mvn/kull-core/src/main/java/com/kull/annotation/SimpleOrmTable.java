package com.kull.annotation;

public @interface SimpleOrmTable {

	String name();

	String pk();

	String[] excludeColumns() default {};

	boolean insertPk() default true;

	String oracleSeqIdRegexp() default "";

}
