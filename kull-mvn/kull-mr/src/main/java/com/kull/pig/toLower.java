package com.kull.pig;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

public class toLower extends toString {

	@Override
	public String exec(Tuple input) throws IOException {
		// TODO Auto-generated method stub
		String val= super.exec(input);
		return val.toUpperCase();
	}

}
