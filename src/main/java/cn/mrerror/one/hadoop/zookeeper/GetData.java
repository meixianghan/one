package cn.mrerror.yinuoc.tools.zookeeper;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class GetData implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws Exception {
        //节点
        String path = "/yinuoc";
        //连接
        zk = new ZooKeeper("118.89.249.248:2181", 5000,new GetData());
        //等待连接成功
        connectedSemaphore.await();
        //创建目录
        zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        //打印节点内容
        System.out.println("节点内容" + new String(zk.getData(path, true, stat)));
        //打印状态信息
        System.out.println("创建事务: " + stat.getCzxid() + ", 修改事务: " + stat.getMzxid() + ", 版本: " + stat.getVersion());
        //设置新内容
        zk.setData(path, "123456".getBytes(), -1);
        //等待测试
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {
        if (KeeperState.SyncConnected == event.getState()) {
            if (EventType.None == event.getType() && null == event.getPath()) {
                //连接成功
                connectedSemaphore.countDown();
                //内容修改事假
            } else if (event.getType() == EventType.NodeDataChanged) {
                try {
                    System.out.println("路径" + event.getPath() + " 内容: " + new String(zk.getData(event.getPath(), true, stat)));
                    System.out.println("创建事务: " + stat.getCzxid() + ", 修改事务: " + stat.getMzxid() + ", 版本: " + stat.getVersion());
                } catch (Exception e) {
                }
            }
        }
    }
}