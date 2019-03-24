package cn.mrerror.yinuoc.tools.mq.rabbitmq.basic;

import com.rabbitmq.client.*;

import java.io.IOException;

public class RecvDefaultConsumer {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("118.89.249.248");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(envelope.getRoutingKey()+":Received :'"+message+"' done");
            }
        };
        channel.basicConsume(QUEUE_NAME, true,consumer);
    }

}