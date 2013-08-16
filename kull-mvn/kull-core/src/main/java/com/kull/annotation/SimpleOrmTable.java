package com.kull.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SimpleOrmTable {

	String name();

	String pk();

	String[] excludeColumns() default {};

	boolean insertPk() default true;

	String oracleSeqIdRegexp() default "";

}
