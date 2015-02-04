package com.retech.edu;

import com.retech.edu.zk.NodeEventListener;
import com.retech.edu.zk.ZKManager;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class LockStartup {
    private static CuratorFramework client;

    public static void main(String[] args) throws Exception {
        String zkAddress = "centos6-jack.chinacloudapp.cn:2181";
        ZKManager zkManager = new ZKManager(zkAddress);
        client = zkManager.getClient();
        lock();
        zkManager.destory();
    }

    public static void watch() throws Exception {
        PathChildrenCache cache = new PathChildrenCache(client, "/zk", false);
        cache.start();
        System.out.println("监听开始/zk........");
        PathChildrenCacheListener plis = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
                    throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED: {
                        System.out.println("Node added: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
                        break;
                    }
                    case CHILD_UPDATED: {
                        System.out.println("Node changed: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
                        break;
                    }
                    case CHILD_REMOVED: {
                        System.out.println("Node removed: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
                        break;
                    }
                }
            }
        };
        //注册监听
        cache.getListenable().addListener(plis);
    }

    private static void lock() throws Exception {
        InterProcessSemaphoreMutex processSemaphoreMutex = new InterProcessSemaphoreMutex(client, "/lock");
        printProcess(processSemaphoreMutex);
        try {
            processSemaphoreMutex.acquire();
            printProcess(processSemaphoreMutex);
            run();
        } catch (Exception ex) {
        } finally {
            try {
                processSemaphoreMutex.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (processSemaphoreMutex.isAcquiredInThisProcess()) {
            processSemaphoreMutex.release();
        }
        printProcess(processSemaphoreMutex);
        System.out.println("再次申请锁服务......");
        lock();
    }

    private static void run() throws Exception {
        watch();
//        client.getCuratorListenable().addListener(new NodeEventListener());
    }

    private static void printProcess(final InterProcessSemaphoreMutex processSemaphoreMutex) {
        // 在本进程中锁是否激活（是否正在执行）
        System.out.println("isAcquiredInThisProcess: " + processSemaphoreMutex.isAcquiredInThisProcess());
    }
}
