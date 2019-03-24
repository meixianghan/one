package cn.mrerror.yinuoc.tools.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class ExistASync implements Watcher {
    //计数器
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;

    public static void main(String[] args) throws Exception {
        //路径
        String path = "/yinuoc";
        //连接
        zk = new ZooKeeper("118.89.249.248:2181", 5000,new ExistASync());
        //等待连接成功
        connectedSemaphore.await();
        //路径是否存在
        zk.exists(path, true, new IIStatCallback(), null);
        //创建路径
        zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //设置路径内容
        zk.setData(path, "456".getBytes(), -1);
        //创建子路径
        zk.create(path + "/c1", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //删除子路径
        zk.delete(path + "/c1", -1);
        //删除路径
        zk.delete(path, -1);
        //等待测试
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {
        try {
            if (KeeperState.SyncConnected == event.getState()) {
                if (EventType.None == event.getType() && null == event.getPath()) {
                    //连接成功
                    connectedSemaphore.countDown();
                } else if (EventType.NodeCreated == event.getType()) {
                    //创建成功
                    System.out.println("创建：" + event.getPath());
                    zk.exists(event.getPath(), true, new IIStatCallback(), null);
                } else if (EventType.NodeDeleted == event.getType()) {
                    //删除成功
                    System.out.println("删除：" + event.getPath());
                    zk.exists(event.getPath(), true, new IIStatCallback(), null);
                } else if (EventType.NodeDataChanged == event.getType()) {
                    //内容改变
                    System.out.println("改变：" + event.getPath());
                    zk.exists(event.getPath(), true, new IIStatCallback(), null);
                }
            }
        } catch (Exception e) {
        }
    }
}

/**
 * 存在回调
 */
class IIStatCallback implements AsyncCallback.StatCallback {
    /**
     * 路径是否存在
     * @param rc 状态码
     * @param path 路径
     * @param ctx 上下文参数
     * @param stat 状态对象
     */
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        if(rc==0)
            System.out.println("成功:[rc: " + rc + ", path: " + path + ", stat: " + stat+']');
        else
            System.out.println("失败:[rc: " + rc + ", path: " + path + ", stat: " + stat+']');

    }
}