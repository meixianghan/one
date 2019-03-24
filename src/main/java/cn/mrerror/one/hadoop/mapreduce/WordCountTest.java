package cn.mrerror.yinuoc.tools.hadoop.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

public class WordCountTest {
	
	public static class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable>{
		@Override
		protected void map(LongWritable key, Text value,Context context)throws IOException, InterruptedException {
			
			String words[] = value.toString().replace("(", "").replace(")", "").split(" ");
			for(String word:words){
				context.write(new Text(word), new LongWritable(1));
			}
		}
	}
	
	public static class WordCountReducer extends Reducer<Text, LongWritable, Text, LongWritable>{
		@Override
		protected void reduce(Text key, Iterable<LongWritable> values,Context context) throws IOException, InterruptedException {
			int count = 0;
			for(LongWritable value:values){
				count++;
			}
			context.write(key, new LongWritable(count));
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		
		Job job = Job.getInstance(conf);
			job.setJarByClass(WordCountTest.class);
			
			job.setMapperClass(WordCountMapper.class);
			job.setReducerClass(WordCountReducer.class);
			
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(LongWritable.class);

			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(LongWritable.class);
			
			job.setInputFormatClass(TextInputFormat.class);
			
			job.setJobName("wordcount");
			
			TextInputFormat.setInputPaths(job, new Path("E:\\hadoop\\wordcount.txt"));
			TextOutputFormat.setOutputPath(job, new Path("E:\\hadoop\\out"));
			
			job.waitForCompletion(true);
	}
}