package cn.mrerror.yinuoc.tools.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GetChildrenASync implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    private static ZooKeeper zk = null;

    public static void main(String[] args) throws Exception {
        String path = "/yinuoc";
        zk = new ZooKeeper("118.89.249.248:2181", 5000, new GetChildrenASync());
        //等zookeeper连接成功之后可执行
        connectedSemaphore.await();
        //创建目录
        zk.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //创建子目录
        zk.create(path + "/y1", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        //获取子节点
        zk.getChildren(path, true, new IChildren2Callback(), null);
        //创建目录
        zk.create(path + "/y2", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {
        if (KeeperState.SyncConnected == event.getState()) {
            if (EventType.None == event.getType() && null == event.getPath()) {
                //计数器减1,服务器已经连接上
                connectedSemaphore.countDown();
            } else if (event.getType() == EventType.NodeChildrenChanged) {
                //子目录已经发生变化
                try {
                    System.out.println("子节点:" + zk.getChildren(event.getPath(), true));
                } catch (Exception e) {
                }
            }
        }
    }
}
//子节点回调
class IChildren2Callback implements AsyncCallback.Children2Callback {

    /**
     * @param rc 响应状态码(response code)
     * @param path 路径
     * @param ctx 上下文对象
     * @param children 子节点列表
     * @param stat 状态对象
     */
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
        System.out.println("子节点回调: [rc: " + rc + ", path: " + path + ", ctx: "+ ctx + ", children: " + children + ", stat: " + stat);
    }
}