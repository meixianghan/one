package cn.mrerror.yinuoc.tools.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class GetDataASync implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;

    public static void main(String[] args) throws Exception {
        //路径
        String path = "/yinuoc";
        //连接
        zk = new ZooKeeper("118.89.249.248:2181", 5000,new GetDataASync());
        //等待连接
        connectedSemaphore.await();
        //创建路径
        zk.create(path, "123456".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        //异步获取内容
        zk.getData(path, true, new IDataCallback(), null);
        //设置内容
        zk.setData(path, "123".getBytes(), -1);
        //测试等待
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {
        if (KeeperState.SyncConnected == event.getState()) {
            if (EventType.None == event.getType() && null == event.getPath()) {
                //连接成功
                connectedSemaphore.countDown();
                //内容变化
            } else if (event.getType() == EventType.NodeDataChanged) {
                try {
                    //再次异步获取内容
                    zk.getData(event.getPath(), true, new IDataCallback(), null);
                } catch (Exception e) {
                }
            }
        }
    }
}
//内容回调
class IDataCallback implements AsyncCallback.DataCallback {
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        System.out.println("状态: " + rc + ", 路径: " + path + ", 内容: " + new String(data));
        System.out.println("创建事务: " + stat.getCzxid() + ", 修改事务: " + stat.getMzxid() + ", 版本: " + stat.getVersion());
    }
}