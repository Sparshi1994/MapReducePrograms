package com.hadoopexpert;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

public class HDFSWrite {

	/**
	 * @param args
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, URISyntaxException {
		
		// Get the instance of the Configuration
		Configuration conf = new Configuration();
		
		// Create an InputStream to read the data
		InputStream inputStream = new BufferedInputStream(new FileInputStream("/home/hadoop/Work/hive_inputs/word.txt"));
		
		//Get the HDFS Instance
		FileSystem hdfs = FileSystem.get(new URI("hdfs://localhost:8020"), conf); 
		
		//Open the outputstream to write the data
		OutputStream outputStream = hdfs.create(new Path("hdfs://localhost:8020/home/hadoop/WOrk/wordjava.txt"), new Progressable(){
			public void progress(){
				System.out.println("..........");
			}
		});
		
		try{
			IOUtils.copyBytes(inputStream, outputStream, 4096, false);
		}finally{
			IOUtils.closeStream(inputStream);
			IOUtils.closeStream(outputStream);
		}
		

	}

}
