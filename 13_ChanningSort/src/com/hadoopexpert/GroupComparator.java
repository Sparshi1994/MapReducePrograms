package com.hadoopexpert;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class GroupComparator extends WritableComparator{

	protected GroupComparator() {
		super(Pair.class, true);		
	}

	public int compare(WritableComparable w1, WritableComparable w2){
		
		Pair v1 = (Pair) w1;
		Pair v2 = (Pair) w2;
		
		  return v1.getKey().compareTo(v2.getKey());
		//return -1 * v1.getKey().compareTo(v2.getKey());
		//return v1.getValue().compareTo(v2.getValue());
	}
}
