package cn.mrerror.yinuoc.spark.stream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.spark.*;
import org.apache.spark.streaming.*;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

/**
 * 这个类是接受kafka数据 已经测试成功
 * 在kafka 端启动如下命令后 在 linux 里面 就能 输入数据   直接传入到这里了  可以直接在这个控制台看到输出结果
 * ./kafka-console-producer.sh --broker-list linux1:9092,linux2:9092,linux3:9092 --topic Hellokafka
 */
public class KafkaStreamingTester {

    public static void main(String[] args) {
        //设置匹配模式，以空格分隔
        final Pattern SPACE = Pattern.compile(" ");
        //接收数据的地址和端口
        String zkQuorum = "linux1:2181,linux2:2181,linux4:2181";
        //话题所在的组
        String group = "1";
        //话题名称以“，”分隔
        String topics = "Hellokafka";
        //每个话题的分片数
        int numThreads = 2;        
        SparkConf sparkConf = new SparkConf().setAppName("KafkaWordCount").setMaster("local[2]");
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, new Duration(10000));
//        jssc.checkpoint("checkpoint"); //设置检查点
        //存放话题跟分片的映射关系
        Map<String, Integer> topicmap = new HashMap<>();
        String[] topicsArr = topics.split(",");
        int n = topicsArr.length;
        for(int i=0;i<n;i++){
            topicmap.put(topicsArr[i], numThreads);
        }
        //从Kafka中获取数据转换成RDD
        JavaPairReceiverInputDStream<String, String> lines = KafkaUtils.createStream(jssc, zkQuorum, group, topicmap);

        //打印结果
        lines.print();

//        JavaDStream<String>  rdd1 = lines.map(new Function<Tuple2<String, String>, String>() {
//            @Override
//            public String call(Tuple2<String, String> tuple2) {
//                return tuple2._2();
//            }
//        });
//        JavaDStream<String> words = rdd1.flatMap(new FlatMapFunction<String, String>() {
//            @Override
//            public Iterator<String> call(String s) throws Exception {
//                return Lists.newArrayList(SPACE.split(s)).iterator();
//            }
//        });
//        JavaPairDStream<String, Integer> wordCounts = words.mapToPair(
//                new PairFunction<String, String, Integer>() {
//                    @Override
//                    public Tuple2<String, Integer> call(String s) {
//                        return new Tuple2<String, Integer>(s, 1);
//                    }
//                }).reduceByKey(
//                new Function2<Integer, Integer, Integer>() {
//                    @Override
//                    public Integer call(Integer i1, Integer i2) {
//                        return i1 + i2;
//                    }
//                });
//        wordCounts.print();
        jssc.start();
        try {
            jssc.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}