package cn.mrerror.yinuoc.tools.hadoop.mapreduce.emp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class EmpSearchByStreamTest {

    public static class EmpMapper extends Mapper<LongWritable, Text, Text, NullWritable>{

        /**
         * 部门信息映射MAP对象
         */
        Map<String,String> depMap = new HashMap<String,String>();

        /**
         * 获取部门信息存入HashMap对象
         */
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            FileSystem fs = FileSystem.getLocal(context.getConfiguration());
            FSDataInputStream fsis= fs.open(new Path("E:/hadoop/join/dept.txt"));
            BufferedReader br = new BufferedReader(new InputStreamReader(fsis, "utf-8"));
            String depContent = null;
            while((depContent=br.readLine())!=null){
                String depItems [] =depContent.split("\\s+");
                depMap.put(depItems[0], depItems[1]);
            }
            fsis.close();
        }
        /**
         * 匹配员工部分信息，并输出结果
         */
        @Override
        protected void map(LongWritable key, Text value, Context context)throws IOException, InterruptedException {
            String empItems [] = value.toString().split("\\s+");
            context.write(new Text(new StringBuilder(empItems[0]).append("\t").append(empItems[1]).append("\t").append(empItems[2]).append("\t").append(depMap.get(empItems[3])).toString()), NullWritable.get());
        }

        /**
         * 销毁全局变量,为GC提供快速回收
         */
        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            depMap.clear();
            depMap=null;
        }
    }
    /**
     * mapreducer执行程序入口
     * @param args 执行任务需要参数值
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
            job.setMapperClass(EmpMapper.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(NullWritable.class);
            job.setJobName("empJob");
            job.setInputFormatClass(TextInputFormat.class);
            job.setOutputFormatClass(TextOutputFormat.class);
            job.setJarByClass(EmpSearchByStreamTest.class);
        TextInputFormat.setInputPaths(job, new Path("E:/hadoop/join/emp.txt"));
        TextInputFormat.setMinInputSplitSize(job, 1024);
        TextOutputFormat.setOutputPath(job, new Path("E:/hadoop/join/out2"));
        job.waitForCompletion(true);
    }
}