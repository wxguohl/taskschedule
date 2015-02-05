package com.retech.edu;

import com.retech.edu.zk.ScheduleWatcher;
import com.retech.edu.zk.ZKTools;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.List;

/**
 * Created by jackwang on 15-2-3.
 */
public class CuratorWatch {

    static CuratorFramework zkclient = null;
    static String nameSpace = "zk";

    static {
        String zkhost = "centos6-jack.chinacloudapp.cn:2181";//zk的host
        RetryPolicy rp = new ExponentialBackoffRetry(1000, 3);//重试机制
        Builder builder = CuratorFrameworkFactory.builder().connectString(zkhost)
                .connectionTimeoutMs(5000)
                .sessionTimeoutMs(5000)
                .retryPolicy(rp);
        builder.namespace(nameSpace);
        CuratorFramework zclient = builder.build();
        zkclient = zclient;
        zkclient.start();

    }

    public static void main(String[] args) throws Exception {
        Wa();
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void Wa() throws Exception {
        List<String> childData = ZKTools.listChildren(zkclient, "/t9");
        if (childData.size() != 0) {
            for (String data : childData) {
                System.out.println("Path:" + data);
                System.out.println("任务执行完成");
                return;
            }
        } else {
            System.out.println("开始监控");
            CuratorWatcher watcher = new CuratorWatcher() {
                @Override
                public void process(WatchedEvent event) throws Exception {
                    System.out.println("节点获取");
                    Wa();
                }
            };
            zkclient.getChildren().usingWatcher(watcher).forPath("/t9");
//            zkclient.getChildren().usingWatcher(new Watcher() {
//                @Override
//                public void process(WatchedEvent watchedEvent) {
//                    try {
//                        System.out.println("节点无变化");
//                        Wa();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).inBackground().forPath("/t9");
        }
    }

    public static PathChildrenCache pathChildrenCache(CuratorFramework client, String path, Boolean cacheData) throws Exception {
        final PathChildrenCache cached = new PathChildrenCache(client, path, cacheData);
        cached.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                PathChildrenCacheEvent.Type eventType = event.getType();
                switch (eventType) {
                    case CONNECTION_RECONNECTED:
                        cached.rebuild();
                        break;
                    case CONNECTION_SUSPENDED:
                    case CONNECTION_LOST:
                        System.out.println("Connection error,waiting...");
                        break;
                    default:
                        System.out.println("Data:" + event.getData().toString());
                }
            }
        });
        return cached;
    }


    public static void watch() throws Exception {
        PathChildrenCache cached = pathChildrenCache(zkclient, "/t2", true);
        //事件操作,将会在额外的线程中执行.
        cached.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        List<ChildData> childData = cached.getCurrentData();
        if (childData != null) {
            for (ChildData data : childData) {
                System.out.println("Path:" + data.getPath() + ",data" + new String(data.getData(), "utf-8"));
                System.out.println("任务执行完成");
                return;
            }
        } else {
            watch();
        }
    }


}
