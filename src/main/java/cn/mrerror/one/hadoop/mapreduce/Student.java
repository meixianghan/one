package cn.mrerror.yinuoc.tools.hadoop.mapreduce;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
public class Student implements WritableComparable<Student>{

    //姓名
    private String name;
    //拼音名字
    private String pinyinName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyinName() {
        return pinyinName;
    }

    public void setPinyinName(String pinyinName) {
        this.pinyinName = pinyinName;
    }

    public void setField(String []field){
        name = field[0];
        pinyinName = field[1];
    }

    /**
     * write 负责人:Mapper
     * 第一步： 先将Mapper业务数据通过序列化格式输出
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(name);
        out.writeUTF(pinyinName);
    }
    /**
     * read:负责人:Comparable
     * 第二步： 将序列化格式数据进行反序列化生成对象
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        name = in.readUTF();
        pinyinName = in.readUTF();
    }

    @Override
    public int compareTo(Student o) {
        return this.name.hashCode()>o.name.hashCode()?1:-1;
    }

    @Override
    public String toString() {
        return this.name;
    }

}