package cn.mrerror.yinuoc.tools.mq.rabbitmq.workqueue;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;


public class WorkerPublishTransaction {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
                          factory.setHost("118.89.249.248");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

            String message ="worker queue";
            int i=0;
            while(true) {
                try {
                    channel.txSelect();
                    channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
                    channel.txCommit();
                }catch(Exception ex){
                    channel.txRollback();
                }
//                Thread.sleep(1000);
            }
        }
    }


}
