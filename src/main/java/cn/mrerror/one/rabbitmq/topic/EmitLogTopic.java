package cn.mrerror.yinuoc.tools.mq.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("118.89.249.248");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //创建模糊匹配交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            //路由key
            String routingKey[] = new String[]{"yinuoc.info","yinuoc.log"};
            //消息内容
            String message = "Hello World";

            int i =0;

            while(true){
                for (String routingkey : routingKey) {
                    channel.basicPublish(EXCHANGE_NAME, routingkey, null, (++i+message).getBytes("UTF-8"));
                }
            }
        }
    }
}

