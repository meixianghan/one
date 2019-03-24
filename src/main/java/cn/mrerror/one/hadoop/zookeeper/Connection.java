package cn.mrerror.yinuoc.tools.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Connection implements Watcher {
    //计数器
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent event) {
        System.out.println("接收事件 : " + event);
        if (KeeperState.SyncConnected == event.getState()) {
            //连接成功
            connectedSemaphore.countDown();
        }
    }

    public static void main(String[] args) throws IOException {
        //连接
        ZooKeeper zookeeper = new ZooKeeper("118.89.249.248:2181", 5000, new Connection());
        //连接状态
        System.out.println(zookeeper.getState());
        try {
            //等待连接成功
            connectedSemaphore.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Zookeeper 连接成功");
    }
}