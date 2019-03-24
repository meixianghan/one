package cn.mrerror.yinuoc.tools.mq.rabbitmq.subscribepublish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceiveLogs {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("118.89.249.248");
        //获得连接
        Connection connection = factory.newConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //绑定订阅与发布交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //创建随机名队列
        String queueName = channel.queueDeclare().getQueue();
        //将队列绑定至交换机
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        //消息处理回调函数
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        //消费消息
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}

