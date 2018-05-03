package com.hadoopexpert;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class UserDetailsMapper extends Mapper<LongWritable, Text, Text, Text>{
	
	// Variable to Process User Detials
	private String contactNumber;
	private String userName;
	private String fileTag = "UD~";
	
	//  map method to process User Details
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		
		String[] line = value.toString().split(",");
		contactNumber = line[0].trim();
		userName = line[1].trim();
		
		context.write(new Text(contactNumber.toString()), new Text(fileTag+userName));
	}
}
