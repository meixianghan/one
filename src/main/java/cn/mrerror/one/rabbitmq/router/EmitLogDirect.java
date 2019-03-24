package cn.mrerror.yinuoc.tools.mq.rabbitmq.router;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogDirect {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("118.89.249.248");
        //创建连接
        Connection connection = factory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //创建路由转发交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //定义发送的路由key
        String routerkeys[] = new String[]{"tv","phone","desk","bookshelf","log"};
        //死循环发送
        while(true) {
            for (String routerkey : routerkeys) {
                channel.basicPublish(EXCHANGE_NAME, routerkey, null, routerkey.getBytes("UTF-8"));
            }
        }

    }
}