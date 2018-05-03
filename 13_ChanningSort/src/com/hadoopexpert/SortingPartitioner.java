package com.hadoopexpert;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class SortingPartitioner extends Partitioner<Pair, NullWritable> {

	@Override
	public int getPartition(Pair pair, NullWritable null1, int numOfPartitions) {
		
		int hash = pair.getKey().hashCode();
		int partition = hash % numOfPartitions;
		
		return partition;
	}


}
