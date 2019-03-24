package cn.mrerror.yinuoc.tools.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GetChildrenSync implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    private static ZooKeeper zk = null;

    public static void main(String[] args) throws Exception {

        String path = "/yinuoc";
        //创建zookeeper对象
        zk = new ZooKeeper("118.89.249.248:2181", 50000, new GetChildrenSync());
        //等待zookeeper连接成功之后再操作
        connectedSemaphore.await();
        //判断路径是否已经存在
        Stat stat = zk.exists(path,false);
        if(null==stat){
            //创建路径
            zk.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        zk.getChildren(path, true);
        //创建子路径
        zk.create(path + "/c1", "192.168.1.1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //创建子路径
        zk.create(path + "/c2", "192.168.5.2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        //创建子路径
        zk.create(path + "/c3", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        //创建子路径
        zk.create(path + "/c4", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        //删除节点
        rmr(path);
        //测试等待
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {
        if (KeeperState.SyncConnected == event.getState())
            //类型未知,路径为null
            if (EventType.None == event.getType()&&null == event.getPath()) {
                connectedSemaphore.countDown();
            //子节点发生变化
            } else if (event.getType()  == EventType.NodeChildrenChanged) {
                try {
                    //获取节点子节点
                    System.out.println("子节点:" + zk.getChildren(event.getPath(), true));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }

    /**
     * 递归删除节点
     * @param path
     * @throws Exception
     */
    public static void rmr(String path) throws Exception {
        //获取子节点
        List<String> children = zk.getChildren(path, false);
        for (String pathCd : children) {
            String newPath = "";
            if (path.equals("/")) {
                newPath = "/" + pathCd;
            } else {
                newPath = path + "/" + pathCd;
            }
            rmr(newPath);
        }
        if (path != null && !path.trim().startsWith("/zookeeper") && !path.trim().equals("/")) {
            zk.delete(path, -1);
            System.out.println("被删除的节点为：" + path);
        }
    }
}