package cn.mrerror.yinuoc.tools.hadoop.mapreduce.insertdb;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.net.URISyntaxException;

public class JoinMapReducerReadDB {


    /**
     * 从数据库hadoop 的emp表中读取数据进行统计
     */
    public static class JoinMapper extends Mapper<LongWritable, EmpInfo, Text, EmpInfo>{

        @Override
        protected void map(LongWritable key, EmpInfo value, Context context)throws IOException, InterruptedException {
            //将对象按照部门编号写出
            context.write(new Text(value.getePid()), value);
        }
    }

    /**
     * 直接将数据库完整信息存储至文件系统中
     */
    public static class JoinReducer extends Reducer<Text, EmpInfo, EmpInfo, NullWritable>{
        /**
         * 循环每个部门的员工信息
         */
        @Override
        protected void reduce(Text key, Iterable<EmpInfo> values,Context context)throws IOException, InterruptedException {
            for(EmpInfo ei:values){
                context.write(ei, NullWritable.get());
            }
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, URISyntaxException {
        //创建一个配置文件对象
        Configuration conf = new Configuration();
        //设置数据配置信息
        DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver", "jdbc:mysql://118.89.249.248:3306/ssm?characterEncoding=utf-8", "root","123456");
        //创建一个任务对象
        Job joinJob = Job.getInstance(conf);
        //设置Mapper执行类
        joinJob.setMapperClass(JoinMapper.class);
        //设置Reducer执行类
        joinJob.setReducerClass(JoinReducer.class);
        //设置Mapper输出KEY类型
        joinJob.setMapOutputKeyClass(Text.class);
        //设置Mapper输出Value类型
        joinJob.setMapOutputValueClass(EmpInfo.class);
        //设置ReducerKey输出类型
        joinJob.setOutputKeyClass(EmpInfo.class);
        //设置Reducer VALUE输出类型
        joinJob.setOutputValueClass(NullWritable.class);
        //设置输出数据处理的格式对象为DBInputFormat对象
        joinJob.setInputFormatClass(DBInputFormat.class);
        //设置输出数据处理格式的对象为TextOutputFormat对象
        joinJob.setOutputFormatClass(TextOutputFormat.class);
        //设置任务的名称
        joinJob.setJobName("joinJobDB");
        //设置任务执行入口的类对象
        joinJob.setJarByClass(JoinMapReducerReadDB.class);
        //设置读取的路径为数据库格式化对象
        DBInputFormat.setInput(joinJob, EmpInfo.class, "emp", null, null, new String[]{"eId","eJob","eName","ePid","ePName"});
        //设置内容输出的路径文件系统地址
        TextOutputFormat.setOutputPath(joinJob, new Path("E:\\hadoop\\join\\out"));
        //执行任务
        joinJob.waitForCompletion(true);
    }
}