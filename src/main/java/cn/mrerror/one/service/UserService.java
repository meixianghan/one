package cn.mrerror.one.service;

import cn.mrerror.one.entity.User;

import java.util.List;

/**
 * 用户服务类
 */
public interface UserService {
    //登录验证
    public User doLogin(User user);
    public List<User> select();
    public User selectById(User user);
    public int insert(User user);
    public int update(User user);
    public int delete(User user);
    public int insertAndlog(User user);
}