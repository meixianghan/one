package cn.mrerror.one.service.imp;

import cn.mrerror.one.dao.LogsDao;
import cn.mrerror.one.entity.User;
import cn.mrerror.one.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("logService")
public class LogServiceImpl implements LogService {

    @Autowired
    private LogsDao logsDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void log(User user) {
        logsDao.insert(String.format("Insert->%s",user.toString()));
    }
}
