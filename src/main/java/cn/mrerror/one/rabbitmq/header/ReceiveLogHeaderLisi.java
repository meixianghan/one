package cn.mrerror.yinuoc.tools.mq.rabbitmq.header;

import com.rabbitmq.client.*;
import java.util.HashMap;
import java.util.Map;

public class ReceiveLogHeaderLisi {
    private static final String EXCHANGE_NAME = "header_test";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("118.89.249.248");
        //创建连接
        Connection connection = factory.newConnection();
        //创建频道
        Channel channel = connection.createChannel();
        //创建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS);
        //队列名字
        String queueInputName = "yinuoc_header_lisi_queue";
        //队列头参数
        Map<String, Object> arguments = new HashMap<>();
                            arguments.put("x-match","all");
                            arguments.put("name","lisi");
                            arguments.put("address","tianjing");
        //创建队列
        String queueName = channel.queueDeclare(queueInputName, true, false, false, null).getQueue();
        //创建队列
        channel.queueBind(queueName, EXCHANGE_NAME, "", arguments);
        //投递回调
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            //获取属性
            System.out.println(delivery.getProperties().toString());
            //获取内容
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        //消费消息
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}

