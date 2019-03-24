package cn.mrerror.yinuoc.tools.hadoop.mapreduce.sort;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

public class NumberSortTest {

    public static final String HADOOP_MR_SORT_DIR_IN="E:\\hadoop\\sort\\";
    public static final String HADOOP_MR_SORT_DIR_OUT="E:\\hadoop\\sort\\out";

    /**
     *  排序MAPPER阶段
     */
    public static class NSMapper extends Mapper<LongWritable, Text, Text,NullWritable>{
        /**
         * mapper 排序统计
         * @param key:文档字符偏移量
         * @param value:文档一行内容
         */
        @Override
        protected void map(LongWritable key, Text value, Context context)throws IOException, InterruptedException {
            /**
             * 1.对行数字按照空格切割
             * 2.循环输出单个数字,对应的value为空
             */

            for(String numItem:value.toString().split(" ")){
                context.write(new Text(numItem), NullWritable.get());
            }
        }
    }
    /**
     * 排序REDUCER阶段
     */
    public static class NSReducer extends Reducer<Text, NullWritable, Text, NullWritable>{
        @Override
        protected void reduce(Text key, Iterable<NullWritable> values,Context context)throws IOException, InterruptedException {
            context.write(key, NullWritable.get());
        }
    }

    /**
     * 作业提交运行阶段
     * @param args jvm 运行传入参数
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);

        job.setMapperClass(NSMapper.class);
        job.setReducerClass(NSReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        job.setJobName("NSSort");
        job.setJarByClass(NumberSortTest.class);

        FileSystem fs  = FileSystem.get(conf);
        if(fs.exists(new Path(HADOOP_MR_SORT_DIR_OUT))){
            fs.delete(new Path(HADOOP_MR_SORT_DIR_OUT), true);
        }

        TextInputFormat.setInputPaths(job, new Path(new StringBuilder(HADOOP_MR_SORT_DIR_IN).append("sort.txt").toString()));
        TextOutputFormat.setOutputPath(job, new Path(HADOOP_MR_SORT_DIR_OUT));

        job.waitForCompletion(true);
    }
}