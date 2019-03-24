package cn.mrerror.yinuoc.tools.hadoop.mapreduce.insertdb;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class JoinMapReducerWriteDB {


    /**
     * 原始数据从HDFS文件中读取
     * dept.txt 部门文件
     * emp.txt 员工文件
     */
    public static class JoinMapper extends Mapper<LongWritable, Text, Text, EmpInfo>{
        //员工实体对象
        EmpInfo ei = new EmpInfo();

        @Override
        protected void map(LongWritable key, Text value, Context context)throws IOException, InterruptedException {
            //读取每一行数据，按照空格分割
            String items[] = value.toString().split("\\s+");
            //获取当前读取文件的分割对象,里面包含了文件信息
            FileSplit fs = (FileSplit)context.getInputSplit();
            //判断当前读取物理文件的名称,是否为部门文件
            if(fs.getPath().getName().startsWith("dept")){
                ei.setcFlag("dept");//设置标记为部门实体
                ei.setePName(items[1]);//设置部门名称信息
                ei.setePid(items[0]);//设置部门编号信息
            }else{
                ei.setcFlag("emp");//设置标记为员工实体
                ei.seteId(items[0]);//设置员工编号信息
                ei.seteJob(items[1]);//设置员工职位信息
                ei.seteName(items[2]);//设置员工名称
                ei.setePid(items[3]);//设置员工编号
            }
            //将对象按照部门编号写出
            context.write(new Text(ei.getePid()), ei);
        }
    }
    /**
     * 将员工信息的部门补齐，然后将对象输出至数据库的emp表
     */
    public static class JoinReducer extends Reducer<Text, EmpInfo, EmpInfo, NullWritable>{
        /**
         * 循环每个部门的员工信息
         */
        @Override
        protected void reduce(Text key, Iterable<EmpInfo> values,Context context)throws IOException, InterruptedException {
            //定义一个部门信息对象
            EmpInfo depInfo = new EmpInfo();
            //临时存储为填入部门名称的员工实体对象
            ArrayList<EmpInfo> als = new ArrayList<EmpInfo>(50);
            //循环找出当前部门信息实体，赋值给depInfo变量
            for(EmpInfo einfo:values){
                if(einfo.getcFlag().equals("dept")){
                    try {
                        BeanUtils.copyProperties(depInfo, einfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        als.add((EmpInfo)BeanUtils.cloneBean(einfo));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            for(EmpInfo ei:als){
                ei.setePName(depInfo.getePName());
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
        //设置输出数据处理的格式对象为TextInputFormat对象
        joinJob.setInputFormatClass(TextInputFormat.class);
        //设置输出数据处理格式的对象为DBOutputFormat对象
        joinJob.setOutputFormatClass(DBOutputFormat.class);
        //设置任务的名称
        joinJob.setJobName("joinJobDB");
        //设置任务执行入口的类对象
        joinJob.setJarByClass(JoinMapReducerWriteDB.class);
        //设置文件读取的路径与格式
        TextInputFormat.setInputPaths(joinJob, new Path("E:\\hadoop\\join\\*"));
        //设置内容输出的路径为数据库，格式为表格式
        DBOutputFormat.setOutput(joinJob, "emp", new String[]{"eId","eJob","eName","ePid","ePName"});
        //执行任务
        joinJob.waitForCompletion(true);
    }
}