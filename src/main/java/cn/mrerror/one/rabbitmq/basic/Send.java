package cn.mrerror.yinuoc.tools.mq.rabbitmq.basic;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;


public class Send {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("118.89.249.248");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            while(true) {
                channel.basicPublish("", "hello", null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
                Thread.sleep(0);
            }
        }
    }
}