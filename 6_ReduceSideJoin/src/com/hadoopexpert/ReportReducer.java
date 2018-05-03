package com.hadoopexpert;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ReportReducer extends Reducer<Text, Text, Text, Text>{

	// Variable to add the Join Process
	private String userName;
	private String deliveryReport;
	private BufferedReader brReader;
	
	enum MYCOUNTER{
		RECORD_COUNT, FILE_EXISTS, FILE_NOT_FOUND,ERROR;
	}
	
	// Map to store Delivery Code and Messages
	private static HashMap<String, String> deliveryCodesMap = new HashMap<String, String>();
	
	@Override
	public void setup(Context context) throws IOException{
		Path[] cacheFileLocal = DistributedCache.getLocalCacheFiles(context.getConfiguration());
		
		for(Path path : cacheFileLocal){
			if(path.getName().toString().trim().equals("DeliveryStatusCodes"));
			context.getCounter(MYCOUNTER.FILE_EXISTS).increment(1);
			loadDeliveryStatusCodes(path, context);
		}		
	}
	
	// This method is used to load the Delivery Codes and Messages into the HashMap
	private void loadDeliveryStatusCodes(Path path, Context context) throws IOException{
		String lineReader = "";
		
		try{
			brReader = new BufferedReader(new FileReader(path.toString()));
			
			//Read each line and load it into HashMap
			while((lineReader = brReader.readLine()) != null){
				String splitArray[] = lineReader.split(",");
				deliveryCodesMap.put(splitArray[0].trim(), splitArray[1].trim());
			}		
			
		}catch(FileNotFoundException fe){
			fe.printStackTrace();
			context.getCounter(MYCOUNTER.FILE_NOT_FOUND).increment(1);
		}catch(IOException io){
			io.printStackTrace();
			context.getCounter(MYCOUNTER.ERROR).increment(1);
		}finally{
			if(brReader != null){
				brReader.close();
			}
		}
		
	}
	
	
	
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException{
		
		for(Text value : values){
			String currValue = value.toString();
			System.out.println("Current Value " + currValue);
			String valueSplitted[] = currValue.split("~");
			System.out.println("Splitted Value " + valueSplitted);
			
			if(valueSplitted[0].equals("UD")){
				userName = valueSplitted[1].trim();				
			}else if(valueSplitted[0].equals("DR")){
				deliveryReport = deliveryCodesMap.get(valueSplitted[1].trim());
			}			
		}
		if(userName != null && deliveryReport != null)
		context.write(new Text(userName), new Text(deliveryReport));
		
		else if(userName == null && deliveryReport != null)
			context.write(new Text("Name Not Found"), new Text(deliveryReport));
		else if(userName != null && deliveryReport == null)
			context.write(new Text(userName), new Text("No Delivery Report"));
		
	}
	
	
	
	
	
}

























