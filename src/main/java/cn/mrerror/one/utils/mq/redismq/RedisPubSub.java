package cn.mrerror.one.utils.mq.redismq;

import cn.mrerror.one.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis.xml")
public class RedisPubSub {

    @Autowired
    private RedisPublish pub;

    @Test
    public void test() throws InterruptedException{

        while(true) {

            User u = new User();
            u.setId(1);
            u.setAccount("zhangsan");
            u.setPassword("zhangsan");

            pub.sendMessage("", "我发消息了");
            pub.sendMessage("", u, SerializableType.JDK_SERIALIZABLE);
            pub.sendMessage("", u, SerializableType.JSON_SERIALIZABLE);
            Thread.sleep(1000);//jackson 反向序列化慢
        }
    }
}