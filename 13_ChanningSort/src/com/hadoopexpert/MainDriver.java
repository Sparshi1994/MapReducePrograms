package com.hadoopexpert;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MainDriver extends Configured implements Tool{

	public static final String output = "/home/hadoop/WOrk/WordOutput";
	@Override
	public int run(String[] args) throws Exception {
	
		
		Configuration conf1 = getConf();
		FileSystem fs = FileSystem.get(conf1);
		
		
		// Job1 Configuration
		Job job1 = new Job(conf1, "Job1");
		job1.setJarByClass(MainDriver.class);
		
		job1.setMapperClass(NewWordCountMapper.class);
		job1.setReducerClass(NewWordCountReducer.class);
		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(IntWritable.class);
		job1.setInputFormatClass(TextInputFormat.class);
		job1.setOutputFormatClass(TextOutputFormat.class);
		
		TextInputFormat.addInputPath(job1, new Path(args[0]));
		TextOutputFormat.setOutputPath(job1, new Path(output));
		
		job1.waitForCompletion(true);
		
		//Job2 Configuration
		 Configuration conf2 = getConf();
		 Job job2 = new Job(conf2, "Job2");
		 job2.setJarByClass(SortCount.class);
		 
		 	job2.setMapperClass(SortingMapper.class);
			job2.setPartitionerClass(SortingPartitioner.class);
			job2.setSortComparatorClass(KeyComparator.class);
			job2.setGroupingComparatorClass(GroupComparator.class);
			job2.setReducerClass(SortingReducer.class);

			job2.setMapOutputKeyClass(Pair.class);
			job2.setMapOutputValueClass(NullWritable.class);
			
			job2.setOutputKeyClass(Pair.class);
			job2.setOutputValueClass(NullWritable.class);
			
			job2.setInputFormatClass(TextInputFormat.class);
			job2.setOutputFormatClass(TextOutputFormat.class);

			TextInputFormat.addInputPath(job2, new Path(output));
			TextOutputFormat.setOutputPath(job2, new Path(args[1]));
		
			return job2.waitForCompletion(true) ? 0 : 1;	
		
	}
	
	public static void main(String[] args) throws Exception {		  
		  if (args.length != 2) {
		   System.err.println("Enter valid number of arguments <Inputdirectory>  <Outputlocation>");
		   System.exit(0);
		  }
		  ToolRunner.run(new Configuration(), new MainDriver(), args);
		 }
	
}
