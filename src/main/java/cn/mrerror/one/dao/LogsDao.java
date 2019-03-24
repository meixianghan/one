package cn.mrerror.one.dao;

import org.springframework.stereotype.Repository;

@Repository()
public interface LogsDao {

    public int insert(String content);

}