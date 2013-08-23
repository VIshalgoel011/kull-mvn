package com.kull.mr;
import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;

import org.apache.hadoop.mapred.Reporter;

import org.apache.hadoop.io.Text;

public class PageRankIterReducer implements Reducer<FloatWritable, Text, FloatWritable, Text> {

  public void reduce(FloatWritable key, Iterator<Text> values,
                     OutputCollector<FloatWritable, Text> output, Reporter reporter) throws IOException {
    double score = 0;
    String outLinks = "";

    while (values.hasNext()) {
      String curr = values.next().toString();
      int colon = curr.indexOf(":");
      int space = curr.indexOf(" ");
      if ((colon > -1)) {
        String presScore = curr.substring(0, colon);
        try {
          score += Double.parseDouble(presScore);
          outLinks = curr.substring(colon + 1);
          continue;
        } catch (Exception e) {
          ;
        }
      }
      if (space > -1) {
        outLinks = curr;
      } else {
        score += Double.parseDouble(curr);
      }
    }
    String toEmit;
    if (outLinks.length() > 0) {
      toEmit = (new Double(score)).toString() + ":" + outLinks;
    } else {
      toEmit = (new Double(score)).toString();
    }
    output.collect(key, new Text(toEmit));
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
