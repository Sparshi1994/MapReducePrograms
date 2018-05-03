package com.hadoopexpert;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MultiDriver extends Configured implements Tool{
	
	
	
	public int run(String[] args) throws Exception {
		
		 Configuration conf = new Configuration();
		 Job job = new Job(conf, "MultiInputs");
		 
		 job.setJarByClass(MultiDriver.class);
		 
		 MultipleInputs.addInputPath(job,new Path(args[0]),TextInputFormat.class,Multi1Mapper.class);
		 MultipleInputs.addInputPath(job,new Path(args[1]),TextInputFormat.class,Multi2Mapper.class);
		  
		 FileOutputFormat.setOutputPath(job, new Path(args[2]));
		 job.setReducerClass(MultiReducer.class);
		 //job.setNumReduceTasks(0);
		 job.setOutputKeyClass(Text.class);
		 job.setOutputValueClass(Text.class);
		 
		
		 
		 return (job.waitForCompletion(true) ? 0 : 1);

		 }

		 public static void main(String[] args) throws Exception {

		  
		  int ecode = ToolRunner.run(new Configuration(), new MultiDriver(), args);
		  System.exit(ecode);

		  
		 }
}



