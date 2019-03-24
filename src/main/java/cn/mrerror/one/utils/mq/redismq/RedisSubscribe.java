package cn.mrerror.one.utils.mq.redismq;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisSubscribe implements MessageListener {

    /**
     * 字符串转字节数组
     */
    private StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    /**
     * JAVA序列化
     */
    private JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
    /**
     * JSON序列化
     */
    private GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();//请使用valueSerializer
        byte[] channel = message.getChannel();

        Object msg = jdkSerializationRedisSerializer.deserialize(body);
        String topic = (String)stringRedisSerializer.deserialize(channel);

        System.out.println("我是sub2,监听"+topic+",我收到消息："+msg);

//        byte[] body = message.getBody();//请使用valueSerializer
//        byte[] channel = message.getChannel();
//        User u = jackson2JsonRedisSerializer.deserialize(body,User.class);
//
//        String topic = (String)stringRedisSerializer.deserialize(channel);
//        System.out.println("我是sub3,监听"+topic+",我收到消息："+u.getId()+"--"+u.getName());

    }
}
