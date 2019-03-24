package cn.mrerror.yinuoc.tools.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

public class Exist implements Watcher {
    //计数器
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;

    public static void main(String[] args) throws Exception {
        //路径
        String path = "/yinuoc";
        //连接
        zk = new ZooKeeper("118.89.249.248:2181", 5000,new Exist());
        //等待连接成功
        connectedSemaphore.await();
        //是否存在
        zk.exists(path, true);
        //创建路径
        zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //给路径设置内容
        zk.setData(path, "456".getBytes(), -1);
        //创建子路径
        zk.create(path + "/c1", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //删除子路径
        zk.delete(path + "/c1", -1);
        //删除路径
        zk.delete(path, -1);
        //测试等待
        Thread.sleep(Integer.MAX_VALUE);
    }
    //回调处理
    public void process(WatchedEvent event) {
        try {
            if (KeeperState.SyncConnected == event.getState()) {
                if (EventType.None == event.getType() && null == event.getPath()) {
                    //连接成功
                    connectedSemaphore.countDown();
                } else if (EventType.NodeCreated == event.getType()) {
                    //节点创建成功
                    System.out.println("创建: " + event.getPath());
                    //观察路径是否存在
                    zk.exists(event.getPath(), true);
                } else if (EventType.NodeDeleted == event.getType()) {
                    //节点被删除
                    System.out.println("删除: " + event.getPath());
                    //观察路径是否存在
                    zk.exists(event.getPath(), true);
                } else if (EventType.NodeDataChanged == event.getType()) {
                    //节点内容被改变
                    System.out.println("改变: " + event.getPath());
                    //观察路径是否存在
                    zk.exists(event.getPath(), true);
                }
            }
        } catch (Exception e) {
        }
    }
}