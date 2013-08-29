
package com.kull.job;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

import com.kull.mr.PageRankViewerMapper;


public class PageRankViewer {

  public static void main(String[] args) {
    JobClient client = new JobClient();
    JobConf conf = new JobConf(PageRankViewer.class);
    conf.setJobName("Page-rank Viewer");

    conf.setInputFormat(org.apache.hadoop.mapred.SequenceFileInputFormat.class);
    conf.setOutputKeyClass(FloatWritable.class);
    conf.setOutputValueClass(Text.class);

    if (args.length < 2) {
      System.out.println("Usage: PageRankIter <input path> <output path>");
      System.exit(0);
    }
    //conf.setInputPath(new Path(args[0]));
    //conf.setOutputPath(new Path(args[1]));
    
    conf.setMapperClass(PageRankViewerMapper.class);
    conf.setReducerClass(org.apache.hadoop.mapred.lib.IdentityReducer.class);

    client.setConf(conf);
    try {
      JobClient.runJob(conf);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
