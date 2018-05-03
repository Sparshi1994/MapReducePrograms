package com.hadoopexpert;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapred.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;



public class SortCount extends Configured{

	public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException{
		
		//Configuration conf1 = new Configuration();
		
		//ControlledJob cJob = new ControlledJob(conf1);
		
		//conf1.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", " ");
		//cJob.setJobName("Secondary Sorting Job");
		
		Job job = new Job();
		
		job.setJarByClass(SortCount.class);
		//job.setInputFormatClass(KeyValueTextInputFormat.class);
		
		job.setMapperClass(SortingMapper.class);
		job.setPartitionerClass(SortingPartitioner.class);
		job.setSortComparatorClass(KeyComparator.class);
		job.setGroupingComparatorClass(GroupComparator.class);
		job.setReducerClass(SortingReducer.class);

		job.setMapOutputKeyClass(Pair.class);
		job.setMapOutputValueClass(NullWritable.class);
		
		job.setOutputKeyClass(Pair.class);
		job.setOutputValueClass(NullWritable.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		
		System.exit(job.waitForCompletion(true)? 0 : 1);
	}
}
