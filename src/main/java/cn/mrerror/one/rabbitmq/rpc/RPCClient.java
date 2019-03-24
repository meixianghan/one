package cn.mrerror.yinuoc.tools.mq.rabbitmq.rpc;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class RPCClient implements AutoCloseable {

    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";

    public RPCClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
                          factory.setHost("118.89.249.248");

        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public static void main(String[] argv) {
        try (RPCClient rpcClient = new RPCClient()) {
            //请求32次
            for (int i = 0; i < 33; i++) {
                //请求并返回结果
                String response = rpcClient.call("hello yinuoc"+i);
                //打印结果
                System.out.println(" [.] Got '" + response + "'");
            }
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String call(String message) throws IOException, InterruptedException {
        //消息编号
        final String corrId = UUID.randomUUID().toString();
        //创建队列
        String replyQueueName = channel.queueDeclare().getQueue();
        //消息属性对象
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                            //消息编号
                            .correlationId(corrId)
                            //处理完毕回复队列
                            .replyTo(replyQueueName)
                            //构建属性
                            .build();
        //发送请求值
        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));
        //创建堵塞队列
        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
        //消息返回值
        String consumerTags = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            //判断返回结果是否为我发送的消息
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                //添加元素
                response.offer(new String(delivery.getBody(), "UTF-8"));
            }
        }, consumerTag -> {

        });
        //线程等待获取元素
        String result = response.take();
        //注销消费者回调函数
        channel.basicCancel(consumerTags);
        //返回结果
        return result;
    }

    public void close() throws IOException {
        connection.close();
    }
}

