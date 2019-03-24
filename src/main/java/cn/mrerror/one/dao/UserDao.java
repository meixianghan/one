package cn.mrerror.one.dao;

import cn.mrerror.one.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户登录接口
 */
@Repository()
public interface UserDao {
    //登录验证
    public User login(User user);
    public List<User> select();
    public User selectById(User user);
    public int insert(User user);
    public int update(User user);
    public int delete(User user);

}