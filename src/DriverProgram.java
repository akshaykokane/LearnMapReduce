import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.io.*;

public class DriverProgram extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
		int exitcode = ToolRunner.run(new DriverProgram(), args);
		System.exit(exitcode);

	}

	@Override
	public int run(String[] arg0) throws Exception {
		
		Configuration conf = this.getConf();
		
		Job job = Job.getInstance(conf);
		job.setJobName("viewCount");
		job.setJarByClass(DriverProgram.class);
	
		// set num of reducer to 2
		job.setNumReduceTasks(2);
		
		// set output key and value data type
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		//set mapper and reduce class references
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		
		// set combiner class, to run for each mapper
		// does some job of reducer at mapper level
		// improves parallelism
		job.setCombinerClass(Reduce.class);
		
		//set inputFilepath and output file path
		Path inputFilePath = new Path(arg0[0]);
		Path outputFilePath = new Path(arg0[1]);

		FileInputFormat.addInputPath(job, inputFilePath);
		FileOutputFormat.setOutputPath(job, outputFilePath);
		
		return job.waitForCompletion(true) ? 0 : 1;
	}

}
