package cn.mrerror.one.entity;

import java.io.Serializable;

/**
 * 部门实体
 */
public class Department implements Serializable {
    //部门编号
    private int deptNo;
    //部门名
    private String deptName;

    public Department() {
    }

    public Department(int deptNo, String deptName) {
        this.deptNo = deptNo;
        this.deptName = deptName;
    }

    public int getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(int deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}