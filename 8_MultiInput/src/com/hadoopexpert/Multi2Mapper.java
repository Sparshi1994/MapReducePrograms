package com.hadoopexpert;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Multi2Mapper extends Mapper<LongWritable, Text, Text, Text>{
	
	 public void map(LongWritable key, Text value, Context context)
			  throws IOException, InterruptedException
			  {
			   String[] line=value.toString().split("\t");
			   context.write(new Text(line[0]), new Text(line[1]));
			  }
}
