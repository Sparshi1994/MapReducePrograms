package com.hadoopexpert;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hdfs.DistributedFileSystem;

public class TestConnection {

	/**
	 * @author 
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, URISyntaxException {
		
		// Establishing Connection
		Configuration conf = new Configuration();
		FileSystem fileSystem = FileSystem.get(new URI("hdfs://localhost:8020"), conf);
		
		if(fileSystem instanceof DistributedFileSystem){
			System.out.println("HDFS is the underlying filesystem");
		}
		else{
			System.out.println("Other type of FileSystem " +fileSystem.getClass());
		}

	}

}
