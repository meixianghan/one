package cn.mrerror.yinuoc.tools.hadoop.mapreduce.partition;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

public class WCBeanTest {

    /**
     * 文字统计Mapper阶段
     * ------------------MAP原始数据阶段-------- MAP处理结果输出阶段--
     * 设置范型 Map<输入key类型,输入value类型,输出key类型,输出value类型>
     *
     */
    public static class WCMapper extends Mapper<LongWritable, Text, Bean, LongWritable>{
        /**
         * 重写MAP方法,处理输入数据<key,value>
         * @param key 输入位置
         * @param value 行数
         * @param context mapper 上下文对
         */
        @Override
        protected void map(LongWritable key, Text value,Context context)throws IOException, InterruptedException {
            //读取行文本，按照空格分割为单词数
            String wordItems[] = value.toString().replaceAll("(\\t|\\s+)", "").split("\\s+");
            //循环每个单词并输出单词出现的次数
            for(String wordItem:wordItems){
                //输出每个单词的统计key,value
                context.write(new Bean(wordItem), new LongWritable(1));
            }
        }
    }
    /**
     * 文字汇Reducer阶段
     * ------------------Reducer原始数据阶段----Reducer处理结果输出阶段--
     * 设置范型 Reducer<输入key类型,输入value类型,输出key类型,输出value类型>
     *
     */
    public static class WCReducer extends Reducer<Bean, LongWritable, Bean, LongWritable>{

        public static LongWritable textValue = new LongWritable();
        /**
         * reducer汇数据阶
         * @param key 单词
         * @param values 同类型单词出现次数数
         * @param context reducer阶段上下文对
         */
        @Override
        protected void reduce(Bean key, Iterable<LongWritable> values,Context context) throws IOException, InterruptedException {
            //统计单词的数
            int wordcount = 0;
            //循环统计单词出现的数
            for(LongWritable value:values){
                //出现次加1
                wordcount+=value.get();
            }
            //优化：重LongWritable对象
            textValue.set(wordcount);
            //通过上下文输出统计结
            context.write(key,textValue);
        }
    }

    /**
     * 本地汇结果存放地
     */
    public static final String LOCAL_DIR_LOCATION="E:\\hadoop\\wcount";

    public static final String LOCAL_INPUT_FILE="E:\\hadoop\\wordcount.txt";

    /**
     * 主函数入
     * @param args 参数数组
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //hadoop配置文件对象
        Configuration conf = new Configuration();

        //Hadoop 作业提交对象
        Job job = Job.getInstance(conf);
        //设置 MAPPER REDUCER 处理
        job.setMapperClass(WCMapper.class);
        job.setReducerClass(WCReducer.class);
        //设置 MAPPER输出 KEY,VALUE类型
        job.setMapOutputKeyClass(Bean.class);
        job.setMapOutputValueClass(LongWritable.class);
        //设置REDUCER输出 KEY,VALUE类型
        job.setOutputKeyClass(Bean.class);
        job.setOutputValueClass(LongWritable.class);
        //设置作业的名
        job.setJobName("WCCOUNT");
        job.setJarByClass(WCBeanTest.class);
        job.setCombinerClass(WCReducer.class);
        job.setPartitionerClass(BeanPartitioner.class);
//        job.setPartitionerClass(HashPartitioner.class);
        //设置reduce作业的数量
        job.setNumReduceTasks(3);
        //设置输入输出格式化处理对
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        //本地化文件处理对象，用于删除已经存在的文件夹
        FileSystem fs = FileSystem.getLocal(conf);
        //判断文件夹是否存
        if(fs.exists(new Path(LOCAL_DIR_LOCATION))){
            //递归删除文件
            fs.delete(new Path(LOCAL_DIR_LOCATION),true);
        }
        //文本输入格式化对象设输入文本地址
        TextInputFormat.setInputPaths(job, new Path(LOCAL_INPUT_FILE));
        //文本输出格式化对象设置输出文件目录地
        TextOutputFormat.setOutputPath(job, new Path(LOCAL_DIR_LOCATION));
        //设置作业等待完成状
        job.waitForCompletion(true);
    }
}