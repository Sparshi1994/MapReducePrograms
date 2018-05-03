package com.hadoopexpert;

import java.io.IOException;

import hipi.image.FloatImage;
import hipi.image.ImageHeader;
import hipi.imagebundle.AbstractImageBundle;
import hipi.imagebundle.HipiImageBundle;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

public class HibRead {
	
	
	Configuration conf = new Configuration();
	HipiImageBundle hib = new HipiImageBundle(new Path("/home/hadoop/Work/hipi/images.hib"), conf);
	
	
	public void openConnection() throws IOException{
		hib.open(AbstractImageBundle.FILE_MODE_READ, true);
	}
	
	// Process Images
	public void processImage() throws IOException{
		
		while (hib.hasNext()) {
			  ImageHeader imageHeader = hib.next();
			  FloatImage fImage = hib.getCurrentImage();
			  
			  System.out.println("Image Type " + imageHeader.getImageType());
			
			  
			  
			  System.out.println("-------------------------------------");
			  System.out.println("Height " + fImage.getHeight());
			  System.out.println("Width " + fImage.getWidth());
			  System.out.println("Pixel " + fImage.getPixel(0, 0, 0));
			  
			}
			hib.close();
		 
	}
	
	
	
	public static void main(String[] args) throws IOException{
		HibRead hr = new HibRead();
		hr.openConnection();
		hr.processImage();
		System.out.println("Done");
	}
	
	
	
	
}
