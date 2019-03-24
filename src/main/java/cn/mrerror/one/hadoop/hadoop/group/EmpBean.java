package cn.mrerror.yinuoc.tools.hadoop.mapreduce.group;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class EmpBean implements WritableComparable<EmpBean> {

    private static final String EMP_BEAN_NULL="";
    //员工编号
    private String eId=EMP_BEAN_NULL;
    //员工岗位
    private String eJob=EMP_BEAN_NULL;
    //员工姓名
    private String eName=EMP_BEAN_NULL;
    //员工部门编号
    private String eDepNum=EMP_BEAN_NULL;
    //员工部门名称
    private String eDepName=EMP_BEAN_NULL;
    //内容标记,用于区分来源
    private String eTag=EMP_BEAN_NULL;

    /**
     * 将字段内容格式化输出
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(eId);
        out.writeUTF(eJob);
        out.writeUTF(eName);
        out.writeUTF(eDepNum);
        out.writeUTF(eDepName);
        out.writeUTF(eTag);
    }
    /**
     * 将字段内容格式化输入
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        eId=in.readUTF();
        eJob=in.readUTF();
        eName=in.readUTF();
        eDepNum=in.readUTF();
        eDepName=in.readUTF();
        eTag=in.readUTF();
    }

    /**
     * 将实体内容输出
     */
    @Override
    public String toString() {
        return new StringBuilder().append(eId).append("\t")
                .append(eJob).append("\t")
                .append(eName).append("\t")
                .append(eDepName).append("\t").toString();
    }

    /**
     * 按照部门编号比较排序
     */
    @Override
    public int compareTo(EmpBean o) {
        return this.eDepNum.hashCode()>o.geteDepNum().hashCode()?1:-1;
    }

    public String geteId() {
        return eId;
    }
    public void seteId(String eId) {
        this.eId = eId;
    }
    public String geteJob() {
        return eJob;
    }
    public void seteJob(String eJob) {
        this.eJob = eJob;
    }
    public String geteName() {
        return eName;
    }
    public void seteName(String eName) {
        this.eName = eName;
    }
    public String geteDepNum() {
        return eDepNum;
    }
    public void seteDepNum(String eDepNum) {
        this.eDepNum = eDepNum;
    }
    public String geteDepName() {
        return eDepName;
    }
    public void seteDepName(String eDepName) {
        this.eDepName = eDepName;
    }
    public String geteTag() {
        return eTag;
    }
    public void seteTag(String eTag) {
        this.eTag = eTag;
    }
}