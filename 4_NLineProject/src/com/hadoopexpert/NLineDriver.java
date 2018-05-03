package com.hadoopexpert;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.LazyOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class NLineDriver extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {

		if (args.length != 2) {
			System.out.printf("Two parameters are required for NLineInputFormat- <input dir> <output dir>\n");
			return -1;
		}

		Job job = new Job(getConf());
		job.setJobName("NLineInputFormat example");
		job.setJarByClass(NLineDriver.class);

		job.setInputFormatClass(NLineInputFormat.class);
		NLineInputFormat.addInputPath(job, new Path(args[0]));
		job.getConfiguration().setInt("mapreduce.input.lineinputformat.linespermap", 30);
		LazyOutputFormat.setOutputFormatClass(job, TextOutputFormat.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(NLineMapper.class);
		job.setNumReduceTasks(0);

		boolean success = job.waitForCompletion(true);
		return success ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new Configuration(),
				new NLineDriver(), args);
		System.exit(exitCode);
	}
}