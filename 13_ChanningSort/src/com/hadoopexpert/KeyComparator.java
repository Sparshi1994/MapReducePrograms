package com.hadoopexpert;


import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class KeyComparator extends WritableComparator{

	protected KeyComparator() {
		super(Pair.class, true);		
	}

	@Override
	public int compare(WritableComparable w1, WritableComparable w2){
		
		Pair v1 = (Pair) w1;
		Pair v2 = (Pair) w2;
		
		int result = v1.getKey().compareTo(v1.getKey());
		if( 0 == result){
			result = -1 * v1.getValue().compareTo(v2.getValue()); // For Descending Order
			//result = v1.getValue().compareTo(v2.getValue()); // For Ascending Order
		}
		
		return result;
	}
}
