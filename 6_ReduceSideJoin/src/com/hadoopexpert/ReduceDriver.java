package com.hadoopexpert;

import java.net.URI;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ReduceDriver extends Configured implements Tool{

	
	public static void main(String[] args) throws Exception {
		
		int exitCode = ToolRunner.run(new Configuration(), new ReduceDriver(), args);
		
		System.exit(exitCode);

	}

	@Override
	public int run(String[] args) throws Exception {
		
		if(args.length <= 2){
			System.out.println("Please provide two parameters");
			return -1;
		}
		
		Job job = new Job(getConf());
		Configuration conf = job.getConfiguration();
		
		job.setJobName("Reduce Join");
		
		DistributedCache.addCacheFile(new URI("/home/hadoop/Work/hive_inputs/DeliveryStatusCodes.txt"), conf);
		
		job.setJarByClass(ReduceDriver.class);
		job.setReducerClass(ReportReducer.class);
		
		
		MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, UserDetailsMapper.class);
		MultipleInputs.addInputPath(job, new Path(args[1]), TextInputFormat.class, DeliverDetailsMapper.class);
		
		
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileOutputFormat.setOutputPath(job, new Path(args[2]));
		
		boolean success = job.waitForCompletion(true);
		
	/*	if(success)
			return 0;
		else
			return -1;*/
		
		return success ? 0 : 1;
		
	}

}
