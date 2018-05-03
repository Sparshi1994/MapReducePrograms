package com.hadoopexpert;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapJoinMapper extends Mapper<LongWritable, Text, Text, Text>{

	private static HashMap<String, String> departmentMap = new HashMap<String, String>();
	private BufferedReader brReader;
	private String departmentName;
	private Text txtMapOutputKey = new Text();
	private Text txtMapOutputValue = new Text();
	
	enum MYCOUNTER{
		RECORD_COUNT, FILE_EXISTS, FILE_NOT_FOUND, SOME_OTHER_ERROR;		
	}
	
	
	
	protected void setup(Context context) throws IOException{
		Path[] cacheFilesLocal = DistributedCache.getLocalCacheFiles(context.getConfiguration());
		for(Path paths : cacheFilesLocal){
			if(paths.getName().toString().equals("departments.txt")){
				context.getCounter(MYCOUNTER.FILE_EXISTS).increment(1);
				loadDepartmentHashMap(paths, context);
			}
		}
	}
	
	private void loadDepartmentHashMap(Path path, Context context) throws IOException{
		String lineRead = "";
		
		try{
			brReader = new BufferedReader(new FileReader(path.toString()));
			
			//Read each line and split and load into HashMap
			while((lineRead = brReader.readLine()) != null){
				String[] deptFieldArray = lineRead.split("\t");
				departmentMap.put(deptFieldArray[0].trim(), deptFieldArray[1].trim());				
			}
		}catch(FileNotFoundException fe){
			fe.printStackTrace();
			context.getCounter(MYCOUNTER.FILE_NOT_FOUND).increment(1);
		}catch(IOException io){
			io.printStackTrace();
			context.getCounter(MYCOUNTER.SOME_OTHER_ERROR).increment(1);
		}finally{
			if(brReader != null){
				brReader.close();
			}
		}
	}
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		context.getCounter(MYCOUNTER.RECORD_COUNT).increment(1);
		String deptName = "";
		if(value.toString().length() > 0){
			String[] empFieldArray = value.toString().split("\t");
			
			try{
				deptName = departmentMap.get(empFieldArray[6].toString());
			}finally{
				deptName = (deptName.equals(null) || deptName.equals("")) ? "Not-Found" : deptName;
			}
			
			txtMapOutputKey.set(empFieldArray[0].toString());
			txtMapOutputValue.set(empFieldArray[1].toString() + "\t"
					            + empFieldArray[2].toString() + "\t" 
					            + empFieldArray[3].toString() + "\t" 
					            + empFieldArray[4].toString() + "\t" 
					            + empFieldArray[5].toString() + "\t" 
					            + empFieldArray[6].toString() + "\t" + deptName);					            
		}
		
		context.write(txtMapOutputKey, txtMapOutputValue);
		deptName = "";
	}
	
	
	
	
}
