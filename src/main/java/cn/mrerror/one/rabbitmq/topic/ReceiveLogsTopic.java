package cn.mrerror.yinuoc.tools.mq.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceiveLogsTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("118.89.249.248");
        //创建连接
        Connection connection = factory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //创建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        //创建队列
        String queueName = channel.queueDeclare().getQueue();

        //路由器key列表
        String routerkeys[] = new String[]{"yinuoc.#","yinuoc.*","#.log"};

        for (String bindingKey : routerkeys) {
            channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
        }

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        //接收消息
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}

