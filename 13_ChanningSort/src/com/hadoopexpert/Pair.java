package com.hadoopexpert;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

public class Pair implements WritableComparable<Pair>{

	private String key;
	private Integer value;

	
	public Pair(){}
	
	public Pair(String key, Integer value){
		this.key = key;
		this.value = value;
	}
	
	@Override
	public String toString(){
		return (new StringBuilder())
		.append('{')
		.append(key)
		.append(',')
		.append(value)
		.append('}').toString();
	}
	
	
	
	
	@Override
	public void readFields(DataInput input) throws IOException {
		key = WritableUtils.readString(input);
		value = input.readInt();		
	}

	@Override
	public void write(DataOutput output) throws IOException {
		WritableUtils.writeString(output, key);
		output.writeInt(value);
	}


	@Override
	public int compareTo(Pair pair) {
		int compareResult = this.key.compareTo(pair.key);
		
		if(compareResult != 0){
			return compareResult;
		}else{
			return this.value.compareTo(pair.value);
		}	
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	
}
