package cn.mrerror.yinuoc.tools.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;

import java.util.concurrent.CountDownLatch;

public class CreateASync implements Watcher {

    //计数器
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        //连接
        ZooKeeper zookeeper = new ZooKeeper("118.89.249.248:2181", 5000, new CreateASync());
        //打印状态
        System.out.println(zookeeper.getState());
        //等待连接
        connectedSemaphore.await();

        zookeeper.create("/yinuoc-ephemeral-", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,new Callback(), "yinuoc context");
        zookeeper.create("/yinuoc-ephemeral-", "456".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,new Callback(), "yinuoc context");

        zookeeper.create("/yinuoc-ephemeral-", "789".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,new Callback(), "yinuoc context");
        //测试等待函数回调
        Thread.sleep(Integer.MAX_VALUE);
    }

    //连接回调
    public void process(WatchedEvent event) {
        if (KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }
}
//回调类
class Callback implements AsyncCallback.StringCallback {
    /**
     * 回调处理
     * @param rc 回调状态
     * @param path 创建路径
     * @param ctx 上下文对象
     * @param name 文件名
     */
    public void processResult(int rc, String path, Object ctx, String name) {
        if(rc==0)
            System.out.println("路径成功: [" + rc + ", " + path + ", " + ctx + ",文件名: " + name);
        else
            System.out.println("路径失败: [" + rc + ", " + path + ", " + ctx + ",文件名: " + name);
    }
}