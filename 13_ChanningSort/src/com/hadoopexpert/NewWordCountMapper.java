package com.hadoopexpert;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class NewWordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		String s = value.toString();
		
		/*for(String words : s.split(" ")){
			if(words.length() > 0)
			context.write(new Text(words), new IntWritable(1));
		}*/
		
		StringTokenizer tokenizer = new StringTokenizer(s);
		while(tokenizer.hasMoreTokens()){
			String words = tokenizer.nextToken();
			context.write(new Text(words), new IntWritable(1));
		}
	
	}

}
