package cn.mrerror.yinuoc.tools.hadoop.mapreduce.insertdb;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 员工实体 POJO
 */
public class EmpInfo implements WritableComparable<EmpInfo>,DBWritable{
    /**
     * 员工编号
     */
    private String eId="";
    /**
     * 员工职位
     */
    private String eJob="";
    /**
     * 员工姓名
     */
    private String eName="";
    /**
     * 员工部门编号
     */
    private String ePid="";
    /**
     * 员工部门名称
     */
    private String ePName="";
    /**
     * 数据类型标记，用于作判断,跟数据本身结构没有关系
     */
    private String cFlag="";

    /**
     * 将实体员工BEAN记录写入数据库
     */
    @Override
    public void write(PreparedStatement statement) throws SQLException {
        //将员工BEAN一行信息写入数据库
        statement.setString(1, eId);//员工编号
        statement.setString(2, eJob);//员工职位
        statement.setString(3, eName);//员工姓名
        statement.setString(4, ePid);//员工部门编号
        statement.setString(5, ePName);//员工部门名称
    }
    /**
     * 将员工信息从数据库读入实体员工BEAN
     */
    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        eId=resultSet.getString(1);//员工编号
        eJob=resultSet.getString(2);//员工职位
        eName=resultSet.getString(3);//员工姓名
        ePid=resultSet.getString(4);//员工部门编号
        ePName=resultSet.getString(5);//员工部门名称
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(eId);
        out.writeUTF(eJob);
        out.writeUTF(eName);
        out.writeUTF(ePid);
        out.writeUTF(ePName);
        out.writeUTF(cFlag);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        eId=in.readUTF();
        eJob=in.readUTF();
        eName=in.readUTF();
        ePid=in.readUTF();
        ePName=in.readUTF();
        cFlag=in.readUTF();
    }

    @Override
    public int compareTo(EmpInfo o) {
        return eId.hashCode() > o.eId.hashCode()?1:-1;
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
    public String getePid() {
        return ePid;
    }
    public void setePid(String ePid) {
        this.ePid = ePid;
    }
    public String getePName() {
        return ePName;
    }
    public void setePName(String ePName) {
        this.ePName = ePName;
    }
    public String getcFlag() {
        return cFlag;
    }
    public void setcFlag(String cFlag) {
        this.cFlag = cFlag;
    }

    @Override
    public int hashCode() {
        return this.eId.hashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder(this.eId).append("\t")
                .append(this.eJob).append("\t")
                .append(this.eName).append("\t")
                .append(this.ePName).append("\t").toString();
    }

}