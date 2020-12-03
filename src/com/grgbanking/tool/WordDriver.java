package com.grgbanking.tool;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordDriver extends Configured implements Tool{

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = new Job(conf);
		job.setJarByClass(WordDriver.class);
		job.setJobName("wordcount");
 
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
 
		job.setMapperClass(WordMapper.class);
		job.setReducerClass(WordReducer.class);
 
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
 
		FileInputFormat.addInputPath(job, new Path("hdfs://hadoop1:9000/runner/input"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://hadoop1:9000/runner/output" + 2));
 
		return(job.waitForCompletion(true)?0:-1);

	}
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new WordDriver(), args);
		System.out.println(exitCode);
		System.exit(exitCode);
	}


}
