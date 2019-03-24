package cn.mrerror.yinuoc.tools.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;

import java.util.concurrent.CountDownLatch;

public class DeleteASync implements Watcher {
    //计数器
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;

    public static void main(String[] args) throws Exception {
        //路径
        String path = "/yinuoc";
        //zookeeper连接对象
        zk = new ZooKeeper("118.89.249.248:2181", 5000,new DeleteASync());
        //等待连接成功
        connectedSemaphore.await();
        //创建持久目录
        zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //创建持久子目录
        zk.create(path + "/c1", "456".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //删除目录
        zk.delete(path, -1, new IVoidCallback(), null);
        //删除子目录
        zk.delete(path + "/c1", -1, new IVoidCallback(), null);
        //删除目录
        zk.delete(path, -1, new IVoidCallback(), null);
        //测试等待
        Thread.sleep(Integer.MAX_VALUE);
    }
    //回调
    public void process(WatchedEvent event) {
        if (KeeperState.SyncConnected == event.getState()) {
            //连接成功
            if (EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
            }
        }
    }
}

class IVoidCallback implements AsyncCallback.VoidCallback {
    /**
     * 删除状态回调
     * @param rc 状态码
     * @param path 路径
     * @param ctx 上下文参数
     */
    public void processResult(int rc, String path, Object ctx) {
        if(rc==0)
        System.out.println("成功["+rc + ", " + path + ", " + ctx+"]");
        else
        System.out.println("失败["+rc + ", " + path + ", " + ctx+"]");
    }
}