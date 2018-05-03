package com.hadoopexpert;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import hipi.image.FloatImage;
import hipi.image.ImageHeader;
import hipi.image.ImageHeader.ImageType;
import hipi.image.io.JPEGImageUtil;
import hipi.imagebundle.AbstractImageBundle;
import hipi.imagebundle.HipiImageBundle;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

public class HibCreate {

	
	
	
	// Creation of a hib file
	Configuration conf = new Configuration();
	HipiImageBundle hib = new HipiImageBundle(new Path("/home/hadoop/Work/hipi/images.hib"), conf);
	
	public void hibCreation() throws IOException{
		
		hib.open(AbstractImageBundle.FILE_MODE_WRITE, true);
		
	}
	
	
	// Adding Images to the hib
	public void addImages() throws IOException{
		
		File file = new File("/home/hadoop/Work/Images/12.png");		
		FileInputStream fis = new FileInputStream(file);
	
		hib.addImage(fis, ImageType.PNG_IMAGE);	
		hib.close();
	}
	
	

	
	
	public static void main(String[] args) throws IOException{
		HibCreate hc = new HibCreate();
		hc.hibCreation();
		hc.addImages();	
		
		System.out.println("Done");
	}
	
	
	
	
	
}
