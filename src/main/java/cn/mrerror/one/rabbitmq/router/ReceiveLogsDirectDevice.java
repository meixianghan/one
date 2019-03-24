package cn.mrerror.yinuoc.tools.mq.rabbitmq.router;

import com.rabbitmq.client.*;

public class ReceiveLogsDirectDevice {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("118.89.249.248");
        //获得连接
        Connection connection = factory.newConnection();
        //获得通道
        Channel channel = connection.createChannel();
        //注册交换机【根据路由KEY转发】
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //创建队列
        String queueName = channel.queueDeclare().getQueue();

        //路由器key列表
        String routerkeys[] = new String[]{"tv","phone","log"};

        //一个队列绑定多个router key
        for (String routerkey : routerkeys) {
            channel.queueBind(queueName, EXCHANGE_NAME, routerkey);
        }
        //消费回调接口
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        //消费消息
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}