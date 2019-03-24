package cn.mrerror.yinuoc.tools.hadoop.mapreduce.dupdata;

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

/**
 *  去除同IP访问记录
 */
public class IPCountTest {

    /**
     * IP访问Mapper端处理
     * -------------------    --inputformat 输入key 输入value outputformat 输出key,输出value
     */
    public static class IPCMapper extends Mapper<LongWritable, Text, Text, Text>{

        /**
         * 将IP+URL作为KEY
         * @param key 文档偏移量
         * @param value 每行内容
         * @param context Mapper阶段上下文
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void map(LongWritable key, Text value,Context context)throws IOException, InterruptedException {
//            需要处理一行中的其他字符
//            String ips = value.toString().replaceAll("\\s+", "");
//            context.write(new Text(ips), new Text(""));
            //不需要可直接输出
            context.write(value, new Text(""));
        }
    }

    /**
     * Reduer端汇总
     * 将ip+url(key)直接输出,value为空
     */
    public static class IPCCombiner extends Reducer<Text, Text, Text,Text>{
        /**
         * 将非重复值汇总输出
         * @param key mapper输入键
         * @param values mapper输入值
         * @param context mapper上下文
         */
        @Override
        protected void reduce(Text key, Iterable<Text> values,Context context) throws IOException, InterruptedException {
            int count=0;
            for(Text value :values){
                count++;
            }
            context.write(key, new Text(""));
        }
    }

    /**
     * Reducer 最总汇总端
     */
    public static class IPCReducer extends Reducer<Text, Text, Text,NullWritable>{
        /**
         * 将最终汇总的结果输出
         * @param key combiner输入键
         * @param values combiner输入值数组
         * @param context reducer上下文对象
         */
        @Override
        protected void reduce(Text key, Iterable<Text> values,Context context) throws IOException, InterruptedException {
            int count=0;
            for(Text value :values){
                count++;
            }
            context.write(key, NullWritable.get());
        }
    }
    //本地磁盘结果输出
    public static final String LOCAL_DIR_LOCATION="E:\\hadoop\\dupdata\\out";
    //本地磁盘原始数据输入地址
    public static final String LOCAL_INPUT_FILE="E:\\hadoop\\dupdata\\data.txt";

    public static void main(String[] args) throws Exception {
        //hadoop配置文件对象
        Configuration conf = new Configuration();

        //Hadoop 作业提交对象
        Job job = Job.getInstance(conf);
        //设置 MAPPER REDUCER 处理类
        job.setMapperClass(IPCMapper.class);
        job.setReducerClass(IPCReducer.class);
        //设置 MAPPER输出 KEY,VALUE类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        //设置REDUCER输出 KEY,VALUE类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        //设置作业的名字
        job.setJobName("IPCCOUNT");
        job.setJarByClass(IPCountTest.class);
        //本地reducer
        job.setCombinerClass(IPCCombiner.class);
        //设置输入输出格式化处理对象
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        //本地化文件处理对象，用于删除已经存在的文件夹
        FileSystem fs = FileSystem.getLocal(conf);
        //判断文件夹是否存在
        if(fs.exists(new Path(LOCAL_DIR_LOCATION))){
            //递归删除文件夹
            fs.delete(new Path(LOCAL_DIR_LOCATION),true);
        }
        //文本输入格式化对象设置 输入文本地址
        TextInputFormat.setInputPaths(job, new Path(LOCAL_INPUT_FILE));
        //文本输出格式化对象设置输出文件目录地址
        TextOutputFormat.setOutputPath(job, new Path(LOCAL_DIR_LOCATION));
        //设置作业等待完成状态
        job.waitForCompletion(true);
    }
}