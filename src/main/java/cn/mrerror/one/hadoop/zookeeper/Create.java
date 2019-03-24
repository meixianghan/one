package cn.mrerror.yinuoc.tools.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

public class Create implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        //连接
        ZooKeeper zookeeper = new ZooKeeper("118.89.249.248:2181", 5000, new Create());
        //打印状态欧
        System.out.println(zookeeper.getState());
        //线程等待
        connectedSemaphore.await();
        //创建临时节点
        String path1 = zookeeper.create("/yinuoc-ephemeral-", "yinuoc".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        //打印节点路径
        System.out.println("路径: " + path1);
        //创建临时顺序目录
        String path2 = zookeeper.create("/yinuoc-ephemeral-", "yinuoc".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        //打印节点目录
        System.out.println("路径: " + path2);
    }

    public void process(WatchedEvent event) {
        //连接成功
        if (KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }
}