package com.hadoopexpert;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DeliverDetailsMapper extends Mapper<LongWritable, Text, Text, Text> {

	// Variable to process Delivery report details
	private String contactNumber;
	private String deliverCode;
	private String fileTag = "DR~";
	
	
	//map method to process DelvieryReport Details
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		String[] line = value.toString().split(",");
		contactNumber = line[0].trim();
		deliverCode = line[1].trim();
		
		context.write(new Text(contactNumber.toString()), new Text(fileTag+deliverCode));
	}
}
