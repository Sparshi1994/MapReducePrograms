package com.ibm.hadoop;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class SortingReducer extends Reducer<Pair, NullWritable, Pair, NullWritable>{

	public void reduce(Pair pair, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException{
		context.write(pair, NullWritable.get());
		
	}
	
}