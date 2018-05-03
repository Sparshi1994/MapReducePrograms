package com.hadoopexpert;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SortingMapper extends Mapper<LongWritable, Text, Pair, NullWritable>{

	String[] tokens = null;
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		
		tokens = value.toString().split("\t");
		
		String word = tokens[0].trim(); 
		int freq = Integer.parseInt(tokens[1].trim());
		
		Pair pair = new Pair(word, freq);
		context.write(pair, NullWritable.get());
	}
}
