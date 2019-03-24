package cn.mrerror.yinuoc.tools.hadoop.mapreduce.partition;

import org.apache.hadoop.mapreduce.Partitioner;

//自定义内容分发的方式
public class WordPartitioner<K,V> extends Partitioner<K, V> {

    @Override
    public int getPartition(K key, V value, int numPartitions) {
        String result = ((org.apache.hadoop.io.Text)key).toString();
        if(result.isEmpty()){
            return 0;
        }
        return result.substring(0, 1).hashCode()%numPartitions;
    }
}
