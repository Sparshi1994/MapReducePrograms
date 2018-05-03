package com.hadoopexpert;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class NewWordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{

	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException{
		int tot = 0;
		while(values.iterator().hasNext()){
			IntWritable i = values.iterator().next();
			int i1 = i.get();
			tot += i1;
		}
		context.write(key, new IntWritable(tot));
	}
}
