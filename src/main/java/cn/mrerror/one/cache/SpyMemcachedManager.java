package cn.mrerror.one.cache;

import cn.mrerror.one.configuration.Applications;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.SerializingTranscoder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;

/**
 * spy memcached 单例管理对象
 *
 * 这里只有 一个管理对象   以实现memecache通信
 */

public class SpyMemcachedManager {

    //打印本类日志
    private static final Logger logger = Logger.getLogger(SpyMemcachedManager.class);


    //单例 --------------------------------------------------------------------------------------------------
    private MemcachedClient memcacheCient;
    private static SpyMemcachedManager INSTANCE = new SpyMemcachedManager();

    private SpyMemcachedManager() {
        try {

            SerializingTranscoder serializingTranscoder = new SerializingTranscoder();
                                  serializingTranscoder.setCompressionThreshold(1024);//压缩阀值

            ConnectionFactoryBuilder connectionFactoryBuilder = new ConnectionFactoryBuilder();
                                     connectionFactoryBuilder.setOpTimeout(10000);
                                     connectionFactoryBuilder.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY);
                                     connectionFactoryBuilder.setTranscoder(serializingTranscoder);
                                     connectionFactoryBuilder.setTimeoutExceptionThreshold(1988);
                                     connectionFactoryBuilder.setFailureMode(FailureMode.Redistribute);
            memcacheCient = new MemcachedClient(connectionFactoryBuilder.build(), Collections.singletonList(new InetSocketAddress(Applications.MEMCACHED_ADDRESS, Applications.MEMCACHED_PORT)));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }
    }

    public static SpyMemcachedManager getInstance() {
        return INSTANCE;
    }
    //单例 --------------------------------------------------------------------------------------------------











    //   对memcached   操作的命令方法

    public void add(String key, Object value, int milliseconds) {
        memcacheCient.add(key, milliseconds, value);
    }

    public void add(String key, Object value) {
        memcacheCient.add(key, 3600, value);
    }

    public void remove(String key, int milliseconds) {
        memcacheCient.delete(key);
    }

    public void remove(String key) {
        memcacheCient.delete(key);
    }

    public void update(String key, Object value, int milliseconds) {
        memcacheCient.replace(key, milliseconds, value);
    }

    public void update(String key, Object value) {
        memcacheCient.replace(key, 3600, value);
    }

    /**
     * 后缀追加值
     * @param key 键
     * @param value 值
     */
    public void append(String key, Object value) {
        memcacheCient.append(key,value);
    }

    /**
     * 前缀追加值
     * @param key 键
     * @param value 值
     */
    public void preAppend(String key, Object value) {
        memcacheCient.prepend(key,value);
    }

    /**
     * 获取key所对应的值
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        try{
            return memcacheCient.get(key);
        }catch (Exception ex){
            logger.error(ex);
            return null;
        }
    }

    /**
     * 设置需要临时缓存的数据
     * @param key 键盘
     * @param exp 过期时间
     * @param value 值
     */
    public void set(String key, int exp, Object value) {
        memcacheCient.set(key, exp, value);
    }

    /**
     * 更新key的时间
     * @param key 键
     * @param exp 过期时间
     */
    public void touch(String key, int exp) {
        try{
            memcacheCient.touch(key, exp);
        }catch (Exception ex){
            logger.error(ex);
        }
    }

    /**
     * 获取或更新Key的时间
     * @param key 键
     * @param exp 过期时间
     * @return 结果
     */
    public Object getAndTouch(String key, int exp) {
        return memcacheCient.getAndTouch(key, exp);
    }

}
