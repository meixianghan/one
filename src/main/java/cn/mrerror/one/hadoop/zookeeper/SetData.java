package cn.mrerror.yinuoc.tools.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class SetData implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;

    public static void main(String[] args) throws Exception {
        //路径
        String path = "/yinuoc";
        //连接
        zk = new ZooKeeper("118.89.249.248:2181", 5000, new SetData());
        //等下连接成功
        connectedSemaphore.await();
        //创建临时路径
        zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        //获取数据
        zk.getData(path, true, null);
        //设置节点数据,并返回状态
        Stat stat = zk.setData(path, "123".getBytes(), -1);
        //打印状态
        System.out.println("创建事务: " + stat.getCzxid() + ", 修改事务: " + stat.getMzxid() + ", 版本: " + stat.getVersion());
        //设置值
        Stat stat2 = zk.setData(path, "456".getBytes(), stat.getVersion());
        //打印状态
        System.out.println("创建事务: " + stat2.getCzxid() + ", 修改事务: " + stat2.getMzxid() + ",版本: " + stat2.getVersion());
        try {
            //根据版本设置值
            zk.setData(path, "666".getBytes(), stat.getVersion());
        } catch (KeeperException e) {
            System.out.println("修改错误: " + e.code() + "," + e.getMessage());
        }
        //等待测试
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {
        if (KeeperState.SyncConnected == event.getState()) {
            if (EventType.None == event.getType() && null == event.getPath()) {
                //连接成功
                connectedSemaphore.countDown();
            }
        }
    }
}