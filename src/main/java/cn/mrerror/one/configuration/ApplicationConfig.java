package cn.mrerror.one.configuration;

import cn.mrerror.one.cache.SpyMemcachedManager;
//import cn.com.one.utils.DateHandlerMethodArgumentResolver;
import cn.mrerror.one.websocket.SpringWebSocketHandler;
import cn.mrerror.one.websocket.SpringWebSocketHandlerInterceptor;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.DefaultHashAlgorithm;
import net.spy.memcached.FailureMode;
import net.spy.memcached.spring.MemcachedClientFactoryBean;
import net.spy.memcached.transcoders.SerializingTranscoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Configuration
@EnableWebMvc
@EnableWebSocket
/**
 * SPRING WebSocket 配置
 */
public class ApplicationConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {


    /**
     * 全局跨域配置信息
     *
     * @param registry 跨域配置注册对象
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/cros/*").allowedOrigins("*").allowedMethods("*").allowCredentials(true).maxAge(1000 * 60 * 60);
    }

    /**
     * 注册WebSocket连接地址
     *
     * @param registry 注册对象
     */
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //注册路径,添加消息处理对象与握手连接器对象
        registry.addHandler(webSocketHandler(), "/websocket/socketServer").addInterceptors(new SpringWebSocketHandlerInterceptor());
    }

    /**
     * 将WebSocket处理对象交给IOC管理
     *
     * @return 返回IOC websocket 文本处理对象
     */
    @Bean
    public TextWebSocketHandler webSocketHandler() {
        return new SpringWebSocketHandler();
    }

    /**
     * 全局日期转换方法
     * @param argumentResolvers
     */
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        //向集合中添加参数转化对象
//        argumentResolvers.add(new DateHandlerMethodArgumentResolver());
//    }

    /**
     * memcached 配置
     *
     * @return
     */
    @Bean(name = "memcacheClient")
    public MemcachedClientFactoryBean memcachedClientFactoryBeanHandler() {
        SerializingTranscoder serializingTranscoder = new SerializingTranscoder();
        serializingTranscoder.setCompressionThreshold(1024);

        MemcachedClientFactoryBean memcachedClientFactoryBean = new MemcachedClientFactoryBean();
        memcachedClientFactoryBean.setServers("118.89.249.248");
        memcachedClientFactoryBean.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY);
        memcachedClientFactoryBean.setTranscoder(serializingTranscoder);
        memcachedClientFactoryBean.setOpTimeout(1000);
        memcachedClientFactoryBean.setTimeoutExceptionThreshold(1988);
        memcachedClientFactoryBean.setHashAlg(DefaultHashAlgorithm.KETAMA_HASH);
        memcachedClientFactoryBean.setLocatorType(ConnectionFactoryBuilder.Locator.CONSISTENT);
        memcachedClientFactoryBean.setFailureMode(FailureMode.Redistribute);
        memcachedClientFactoryBean.setUseNagleAlgorithm(false);
        return memcachedClientFactoryBean;
    }

    @Bean
    public SpyMemcachedManager getSpyMemcachedManager() {
        return SpyMemcachedManager.getInstance();
    }
}