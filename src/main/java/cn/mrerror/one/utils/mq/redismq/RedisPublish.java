package cn.mrerror.one.utils.mq.redismq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("redisPublish")
public class RedisPublish {

    @Autowired
    private RedisTemplate<String, String> rt;
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

    /**
     * 发送字符串
     * @param topic 主题
     * @param message 信息
     */
    public void sendMessage(String topic, String message) {
        rt.convertAndSend(topic, message);
    }

    /**
     * 发送序列化对象
     * @param topic 主题
     * @param serializable 序列化对象
     * @param type  序列化枚举
     */
    public void sendMessage(String topic, Serializable serializable,SerializableType type) {
        if(type == SerializableType.JSON_SERIALIZABLE){
            rt.convertAndSend(topic, new String(jackson2JsonRedisSerializer.serialize(serializable)));
        }else if(type==SerializableType.JDK_SERIALIZABLE){
            rt.convertAndSend(topic, jdkSerializationRedisSerializer.serialize(serializable));
        }
    }
}
