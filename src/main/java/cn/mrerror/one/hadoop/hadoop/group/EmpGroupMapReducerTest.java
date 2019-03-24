package cn.mrerror.yinuoc.tools.hadoop.mapreduce.group;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 关联查询
 */
public class EmpGroupMapReducerTest {

    /**
     * 读取员工信息与部门信息
     * 分装如实体BEAN
     */
    public static class EmpGroupMapper extends Mapper<LongWritable, Text, Text, EmpBean>{

        EmpBean eb = new EmpBean();

        @Override
        protected void map(LongWritable key, Text value,Context context)throws IOException, InterruptedException {

            String items [] = value.toString().split("\\s+");

            FileSplit fs = (FileSplit)context.getInputSplit();

            if(fs.getPath().getName().startsWith("dept")){
                eb.seteDepNum(items[0]);
                eb.seteDepName(items[1]);
                eb.seteTag("dept");
            }else{
                eb.seteId(items[0]);
                eb.seteJob(items[1]);
                eb.seteName(items[2]);
                eb.seteDepNum(items[3]);
                eb.seteTag("emp");
            }
            context.write(new Text(eb.geteDepNum()), eb);
        }

    }
    public static class EmpGroupReducer extends Reducer<Text, EmpBean, EmpBean, NullWritable>{
        EmpBean depBean = null;
        @Override
        protected void reduce(Text key, Iterable<EmpBean> value,Context context) throws IOException, InterruptedException {

            ArrayList<EmpBean> al = new ArrayList<EmpBean>();
            for (EmpBean empBean : value) {
                if("dept".equals(empBean.geteTag())){
                    try {
                        //框架对对象容器会重复使用,必须要克隆
                        depBean= (EmpBean)BeanUtils.cloneBean(empBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        if(null!=depBean){
                            empBean.seteDepName(depBean.geteDepName());
                            context.write(empBean, NullWritable.get());
                        }else{
                            al.add((EmpBean)BeanUtils.cloneBean(empBean));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            for (EmpBean empBean : al) {
                empBean.seteDepName(depBean.geteDepName());
                context.write(empBean, NullWritable.get());
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setMapperClass(EmpGroupMapper.class);
        job.setReducerClass(EmpGroupReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(EmpBean.class);

        job.setOutputKeyClass(EmpBean.class);
        job.setOutputValueClass(NullWritable.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        job.setJarByClass(EmpGroupMapReducerTest.class);

        job.setJobName("EmpGroupMapReducerTest");

        FileSystem fs = FileSystem.get(conf);
        if(fs.exists(new Path("E:\\hadoop\\join\\out"))){
            fs.delete(new Path("E:\\hadoop\\join\\out"),true);
        }
        TextInputFormat.setInputPaths(job, new Path("E:\\hadoop\\join\\*"));
        TextOutputFormat.setOutputPath(job, new Path("E:\\hadoop\\join\\out"));

        job.waitForCompletion(true);


    }

}
