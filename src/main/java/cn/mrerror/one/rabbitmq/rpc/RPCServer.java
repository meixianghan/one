package cn.mrerror.yinuoc.tools.mq.rabbitmq.rpc;

import com.rabbitmq.client.*;

public class RPCServer {

    private static final String RPC_QUEUE_NAME = "rpc_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
                          factory.setHost("118.89.249.248");
        //自动资源释放
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //创建队列
            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
            //清理队列内容
            channel.queuePurge(RPC_QUEUE_NAME);
            //设置消费频率
            channel.basicQos(1);
            //锁对象
            Object monitor = new Object();

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
                                    //消息编号,从客户端获取的消息编号
                                    .correlationId(delivery.getProperties().getCorrelationId())
                                    .build();

                String response = "";

                try {
                    //客户端消息
                    String message = new String(delivery.getBody(), "UTF-8");
                    System.out.println(" [.] (" + message + ")");
                    response = message+"[done]";
                } catch (RuntimeException e) {
                    System.out.println(" [.] " + e.toString());
                } finally {
                    //发送处理结果
                    //delivery.getProperties().getReplyTo()需要发送结果值的队列名
                    channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                    //手动确认
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    //通知
                    synchronized (monitor) {
                        monitor.notify();
                    }
                }
            };
            //消费数据
            channel.basicConsume(RPC_QUEUE_NAME, false, deliverCallback, (consumerTag -> { }));
            while (true) {
                synchronized (monitor) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}