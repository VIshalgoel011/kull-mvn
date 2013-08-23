package com.kull.job;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reducer;

import com.kull.mr.PageRankIterMapper;
import com.kull.mr.PageRankIterReducer;


public class PageRankIter {

  public static void main(String[] args) {
    JobClient client = new JobClient();
    JobConf conf = new JobConf(PageRankIter.class);
    conf.setJobName("Page-rank Iter");

    conf.setNumReduceTasks(5);

    conf.setInputFormat(org.apache.hadoop.mapred.SequenceFileInputFormat.class);
    conf.setOutputFormat(org.apache.hadoop.mapred.SequenceFileOutputFormat.class);

    conf.setOutputKeyClass(Text.class);
    conf.setOutputValueClass(Text.class);

    if (args.length < 2) {
      System.out.println("Usage: PageRankIter <input path> <output path>");
      System.exit(0);
    }
    //conf.setInputPath(new Path(args[0]));
    //conf.setOutputPath(new Path(args[1]));

    //conf.setInputPath(new Path("graph2"));
    //conf.setOutputPath(new Path("graph3"));

    conf.setMapperClass(PageRankIterMapper.class);
    conf.setReducerClass(PageRankIterReducer.class);
    conf.setCombinerClass(PageRankIterReducer.class);

    client.setConf(conf);
    try {
      JobClient.runJob(conf);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
