package cn.mrerror.yinuoc.tools.hadoop.mapreduce.log;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 平台日志统计
 * 查询每日 单IP 访问量最高的功能
 */
public class LogStatisticMapReducer {

    /**
     * 目录文件过滤器(part*)
     */
    public static class FileFilter implements PathFilter{
        @Override
        public boolean accept(Path path) {
            return path.getName().startsWith("part");
        }
    }

    /**
     * 清洗原始数据
     * 组合成逻辑结构数据
     */
    public static class LogSMapper extends Mapper<LongWritable, Text, Text, Text>{
        /**
         * 每行进行清洗
         */
        @Override
        protected void map(LongWritable key, Text value,Context context)throws IOException, InterruptedException {
            String logItems [] =value.toString().split("\\s+");
            String ip,date,url;
            ip=logItems[0];
            date=logItems[3].split(":")[0].replace("[", "");
            url=logItems[6];
            if(!url.endsWith(".png")&&!url.endsWith(".js")&&!url.endsWith(".css")&&!url.endsWith(".ico")&&!url.endsWith(".jpg")){
                context.write(new Text(date+"\t"+ip), new Text(url));
            }
        }
    }

    /**
     * 组合内容并输出格式化数据
     */
    public static class LogSReducer extends Reducer<Text, Text, Text, Text>{
        @Override
        protected void reduce(Text key, Iterable<Text> values,Context context)throws IOException, InterruptedException {
            for(Text url:values){
                context.write(key, url);
            }
        }
    }

    /**
     * 读取清洗后的格式化数据
     */
    public static class LogStatMapper extends Mapper<LongWritable, Text, Text, Text>{
        @Override
        protected void map(LongWritable key, Text value,Context context)throws IOException, InterruptedException {
            String items[] = value.toString().split("\\t");
            context.write(new Text(items[0]+"\t"+items[1]), new Text(items[2]));
        }
    }
    /**
     * 汇总每个IP URL出现次数最多的地址
     */
    public static class LogStatReducer extends Reducer<Text, Text, Text, Text>{
        /**
         * 提取每日单个Ip出现URL最大次数的地址
         */
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context)throws IOException, InterruptedException {
            //存储每个URL出现的次数
            Map<String,Integer> countMap = new HashMap<String,Integer>();
            //循环存入
            for(Text url:values){
                if(countMap.get(url.toString())!=null){
                    countMap.put(url.toString(),countMap.get(url.toString())+1);
                }else{
                    countMap.put(url.toString(), 1);
                }
            }
            String result = "";
            int count =0;
            //判断最大出现次数的URL地址
            for(String url:countMap.keySet()){
                if(countMap.get(url)>count){
                    count=countMap.get(url);
                    result = url;
                }
            }
            context.write(key, new Text(result));
        }
    }

    /**
     * MapReducer 执行入口
     * @param args 执行所需要参数
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job logSjob = Job.getInstance(conf);
        logSjob.setMapperClass(LogSMapper.class);
        logSjob.setReducerClass(LogSReducer.class);

        logSjob.setMapOutputKeyClass(Text.class);
        logSjob.setMapOutputValueClass(Text.class);

        logSjob.setOutputKeyClass(Text.class);
        logSjob.setOutputValueClass(Text.class);

        logSjob.setInputFormatClass(TextInputFormat.class);
        logSjob.setOutputFormatClass(TextOutputFormat.class);

        logSjob.setJarByClass(LogStatisticMapReducer.class);
        logSjob.setJobName("LogSMapper");

        TextInputFormat.setInputPaths(logSjob, new Path("E:\\hadoop\\mutiljob\\input.txt"));
        TextOutputFormat.setOutputPath(logSjob, new Path("E:\\hadoop\\mutiljob\\out"));

//		    logSjob.waitForCompletion(true);

        Job logStatJob = Job.getInstance(conf);
        logStatJob.setMapperClass(LogStatMapper.class);
        logStatJob.setReducerClass(LogStatReducer.class);

        logStatJob.setMapOutputKeyClass(Text.class);
        logStatJob.setMapOutputValueClass(Text.class);

        logStatJob.setOutputKeyClass(Text.class);
        logStatJob.setOutputValueClass(Text.class);

        logStatJob.setInputFormatClass(TextInputFormat.class);
        logStatJob.setOutputFormatClass(TextOutputFormat.class);

        logStatJob.setJarByClass(LogStatisticMapReducer.class);
        logStatJob.setJobName("LogStatMapper");

        TextInputFormat.setInputPaths(logStatJob, new Path("E:\\hadoop\\mutiljob\\out\\*"));
        TextInputFormat.setInputPathFilter(logStatJob, FileFilter.class);
        TextOutputFormat.setOutputPath(logStatJob, new Path("E:\\hadoop\\mutiljob\\out1"));

//	    logStatJob.waitForCompletion(true);

        ControlledJob cLogSjob = new ControlledJob(logSjob.getConfiguration());
                      cLogSjob.setJob(logSjob);

        ControlledJob cLogStatJob =new ControlledJob(logStatJob.getConfiguration());
                      cLogStatJob.setJob(logStatJob);
                      cLogStatJob.addDependingJob(cLogSjob);

        JobControl  jobControl = new JobControl("logStatGroup");
                    jobControl.addJob(cLogSjob);
                    jobControl.addJob(cLogStatJob);

        new Thread(jobControl).start();

        while(true){
            Thread.sleep(1000);
            if(jobControl.allFinished()){
                jobControl.stop();
                break;
            }else if(jobControl.getFailedJobList().size()>0){
                jobControl.stop();
                break;
            }
        }
    }
}