import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class Map extends Mapper<LongWritable, Text, Text, IntWritable> {

	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String[] row = value.toString().split(",");
		context.write(new Text(row[1]), new IntWritable(1));
	}
}
