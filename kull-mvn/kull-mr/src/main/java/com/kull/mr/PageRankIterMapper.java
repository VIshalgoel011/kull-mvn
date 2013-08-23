package com.kull.mr;

// Copyright 2007
// Distributed under GPLv3
//
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;



public class PageRankIterMapper implements Mapper<Text, Text,FloatWritable, Text> {

	
	public void map(Text key, Text value,
			OutputCollector<FloatWritable, Text> output, Reporter arg3)
			throws IOException {
		// TODO Auto-generated method stub
		String data = ((Text)value).toString();
	    int index = data.indexOf(":");
	    if (index == -1) {
	      return;
	    }
	    String toParse = data.substring(0, index).trim();
	    double currScore = Double.parseDouble(toParse);
	    output.collect(new FloatWritable((float) - currScore), key);
	}

	@Override
	public void configure(JobConf arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	



	

}
