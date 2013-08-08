package com.kull.hadoop;

import java.io.IOException;
import java.text.MessageFormat;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class MaxTempReduce extends Reducer<Text, IntWritable, Text, IntWritable>{

	@Override
	 public void reduce(Text key, Iterable<IntWritable> values,
		      Context context)
		      throws IOException, InterruptedException {
		    
		    int maxValue = Integer.MIN_VALUE;
		    for (IntWritable value : values) {
		      maxValue = Math.max(maxValue, value.get());
		    }
		    context.write(key, new IntWritable(maxValue));
		    System.out.println(MessageFormat.format("{0} text:{1} iw:{2}",this.getClass().getName(),key,maxValue ));

		  }
	
}