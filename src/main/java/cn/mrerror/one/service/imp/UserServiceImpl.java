package cn.mrerror.one.service.imp;

import cn.mrerror.one.dao.LogsDao;
import cn.mrerror.one.dao.UserDao;
import cn.mrerror.one.entity.User;
import cn.mrerror.one.service.LogService;
import cn.mrerror.one.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")

public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao ;
    @Autowired
    private LogsDao logsDao;
    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;

    @Override
    public User doLogin(User user) {
        return userDao.login(user);
    }

    @Override
    public List<User> select() {
        return userDao.select();
    }

    @Override
    public User selectById(User user) {
        return userDao.selectById(user);
    }

//    @Transactional
//    public int insert(User user) {
//        //记录操作行为
//        logsDao.insert(user.toString());
//        int result = userDao.insert(user);
//        int x = 1/0;
//        return result;
//    }

//    @Transactional
//    public int insert(User user) {
//        logService.log(user);
//        int result = userDao.insert(user);
//        int x = 1/0;
//        return result;
//    }

    public int insert(User user) {
        logsDao.insert(String.format("Insert->%s",user.toString()));
        return userService.insertAndlog(user);
    }

    @Transactional
    public int insertAndlog(User user){
        int result = userDao.insert(user);
        int x = 1/0;
        //发送操作数据至负责人
        return result;
    }

    @Override
    public int update(User user) {
        return userDao.update(user);
    }

    @Override
    public int delete(User user) {
        return userDao.delete(user);
    }

}
