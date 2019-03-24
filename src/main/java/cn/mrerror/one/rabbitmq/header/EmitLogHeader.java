package cn.mrerror.yinuoc.tools.mq.rabbitmq.header;

import com.rabbitmq.client.*;

import java.util.HashMap;
import java.util.Map;

public class EmitLogHeader {

    private static final String EXCHANGE_NAME = "header_test";

    public static void main(String[] argv) throws Exception {

        Map<String, Object> headers = new HashMap<String, Object>();
                            headers.put("userid","123");
                            headers.put("name","zhangsan");
                            headers.put("address","beijing");
                            headers.put("paymoney",1238);

        //连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("118.89.249.248");
        //创建连接
         Connection connection = factory.newConnection();
         //创建通道
         Channel channel = connection.createChannel();
         //创建HEADER交换机
                 channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS);
         //创建属性对象
            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
                                         //投递模式(持久化)
                                         builder.deliveryMode(MessageProperties.PERSISTENT_TEXT_PLAIN.getDeliveryMode());
                                         //优先级
                                         builder.priority(MessageProperties.PERSISTENT_TEXT_PLAIN.getPriority());
                                         //设置头部
                                         builder.headers(headers);
                                         //返回属性集合
            //消息内容
            String message = "head msg";
            int i=0;
            //发布消息
            while(true){
                if(i%2==0){
                    headers.put("name","zhangsan");
                    headers.put("address","beijing");
                }else{
                    headers.put("name","lisi");
                    headers.put("address","tianjing");
                }
                AMQP.BasicProperties properties = builder.build();
                channel.basicPublish(EXCHANGE_NAME, "", properties, (++i+message).getBytes("UTF-8"));
                Thread.sleep(100);
            }
    }
}

