package com.kull.pig;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

public class toString extends EvalFunc<String> {

	@Override
	public String exec(Tuple input) throws IOException {
		// TODO Auto-generated method stub
		Object obj =input.get(0);
		return obj==null?"":obj.toString();
	}

}
