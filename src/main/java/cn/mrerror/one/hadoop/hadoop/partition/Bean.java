package cn.mrerror.yinuoc.tools.hadoop.mapreduce.partition;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 单词包装类
 * 用于partition分发使用
 * 重写word的hash值
 */
public class Bean implements WritableComparable<Bean>{
    //单词
    private String word;

    public String getWord() {
        return word;
    }
    /**
     * 无惨构造方法,用于框架发生生成对象的时候使用
     */
    public Bean(){}

    public void setWord(String word) {
        this.word = word;
    }

    public Bean(String word){
        this.word=word;
    }

    /**
     * 重写hashcode算法
     */
    @Override
    public int hashCode() {
        if(word.isEmpty()){
            return 0;
        }
        return word.substring(0,1).hashCode();
    }

    @Override
    public String toString() {
        return word;
    }
    /**
     * 文字输出流
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(word);
    }
    /**
     * 文字输入流
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        word = in.readUTF();
    }
    /**
     * 文字内容比较器
     */
    @Override
    public int compareTo(Bean o) {
        return word.hashCode()>o.hashCode()?1:-1;
    }
}