package cn.mrerror.yinuoc.tools.mq.rabbitmq.subscribepublish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("118.89.249.248");
        //创建连接
        Connection connection = factory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //申请订阅与发布交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //基础消息
        String message = "hello yinuoc";
        int i =0;
        //循环发送消息
        while(true) {
            //发布消息
            channel.basicPublish(EXCHANGE_NAME, "", null, (++i+message).getBytes("UTF-8"));
            Thread.sleep(1000);
        }
    }

}
