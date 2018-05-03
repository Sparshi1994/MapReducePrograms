package com.hadoopexpert;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MapJoinDriver extends Configured implements Tool{

	/**
	 * @author Syed
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception  {
		
		int exit = ToolRunner.run(new Configuration(), new MapJoinDriver(), args);
		System.exit(exit);		
	}

	@Override
	public int run(String[] args) throws Exception {
		Job job = new Job(getConf());
		
		Configuration conf = job.getConfiguration();
		job.setJobName("MapSideJoin");
		
		DistributedCache.addCacheFile(new URI("/home/hadoop/Work/hive_inputshdfs/departments.txt"), conf);
		
		job.setJarByClass(MapJoinDriver.class);
		job.setMapperClass(MapJoinMapper.class);
		job.setNumReduceTasks(0);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		return job.waitForCompletion(true) ? 0 : 1;	
		

	}

}
