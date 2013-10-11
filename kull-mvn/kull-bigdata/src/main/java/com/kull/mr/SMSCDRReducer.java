package com.kull.mr;

import java.util.Date;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;





public class SMSCDRReducer extends
Reducer<Text, IntWritable, Text, IntWritable> {

protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws java.io.IOException, InterruptedException {
  int sum = 0;
  for (IntWritable value : values) {
    sum += value.get();
  }
  context.write(key, new IntWritable(sum));
  //Console.println("{0} key :{1} sum:{2}",new Date().toString(), key,sum);
}
}