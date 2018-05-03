package com.hadoopexpert;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class HDFSRead {

	/**
	 * @param args
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws URISyntaxException, IOException {
		
		// Get the instance of the Configuration
		Configuration conf = new Configuration();
		
		//URI of the file to be read
		URI uri = new URI("hdfs://localhost:8020/home/hadoop/Work/Output/WordOutput");
		
		
		// Get the instance of HDFS
		FileSystem hdfs =  FileSystem.get(uri, conf);
		
		// Reference to hold the InputStream
		InputStream inputStream = null;
		try{
			Path path = new Path(uri);
			inputStream = hdfs.open(path);
			
			IOUtils.copyBytes(inputStream, System.out, 4096, false);
			IOUtils.closeStream(inputStream);
			
		}catch(Exception e){ e.printStackTrace(); }
		

	}

}
