package com.retech.edu.task.zk;

import com.retech.edu.task.zk.ZKManager;
import com.retech.edu.task.zk.ZKTools;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.zookeeper.WatchedEvent;

import java.util.List;

public class LockStartup {
    private static String zkAddress = "centos6-jack.chinacloudapp.cn:2181";
    private static CuratorFramework client;
    private static ZKManager zkManager = null;

    public static void main(String[] args) throws Exception {
        zkManager = new ZKManager(zkAddress);
        client = zkManager.getClient();
        lock();
        // zkManager.destory();
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void Wa(final InterProcessSemaphoreMutex processSemaphoreMutex) throws Exception {
        List<String> childData = ZKTools.listChildren(client, "/rtschedule/currentjob");
        if (childData.size() != 0) {
            for (String data : childData) {
                System.out.println("Path:" + data);
                System.out.println("任务执行完成");
                ZKTools.deleteNode(client,"/hot/"+data);
                System.out.println("删除节点");
            }
            if (processSemaphoreMutex.isAcquiredInThisProcess()) {
                processSemaphoreMutex.release();
            }
            printProcess(processSemaphoreMutex);
            System.out.println("再次申请锁服务......");
            lock();
        } else {
            System.out.println("开始监控");
            CuratorWatcher watcher = new CuratorWatcher() {
                @Override
                public void process(WatchedEvent event) throws Exception {
                    System.out.println("节点获取");
                    Wa(processSemaphoreMutex);
                }
            };
            client.getChildren().usingWatcher(watcher).forPath("/rtschedule/currentjob");
        }
    }

    private static void lock() throws Exception {
        InterProcessSemaphoreMutex processSemaphoreMutex = new InterProcessSemaphoreMutex(client, "/rtschedule/locks");
        try {
            printProcess(processSemaphoreMutex);
            processSemaphoreMutex.acquire();
            printProcess(processSemaphoreMutex);
            Wa(processSemaphoreMutex);
        } catch (Exception ex) {
            if (processSemaphoreMutex.isAcquiredInThisProcess()) {
                processSemaphoreMutex.release();
            }
            printProcess(processSemaphoreMutex);
//            zkManager.destory();
//            zkManager = new ZKManager(zkAddress);
//            client = zkManager.getClient();
            System.out.println("再次申请锁服务......");
            lock();
        }

    }

    private static void printProcess(final InterProcessSemaphoreMutex processSemaphoreMutex) {
        // 在本进程中锁是否激活（是否正在执行）
        System.out.println("isAcquiredInThisProcess: " + processSemaphoreMutex.isAcquiredInThisProcess());
    }
}
