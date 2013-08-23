package com.kull.mr;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;

import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.*;

public class PageRankViewerMapper  extends Mapper<IntWritable,Text ,FloatWritable , Text> {





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


}
