package com.retech.edu;

import com.retech.edu.zk.ZKManager;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * Created by jackwang on 15-2-4.
 */
public class Main {

    private static CuratorFramework client;

    public static void main(String[] args) throws Exception {
        String zkAddress = "centos6-jack.chinacloudapp.cn:2181";
        ZKManager zkManager = new ZKManager(zkAddress);
        client = zkManager.getClient();
        client.checkExists().forPath("/zk");
        // 注册观察者，当节点变动时触发
        client.getData().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("node is changed");
            }
        }).inBackground().forPath("/zk");
        Thread.sleep(Long.MAX_VALUE);
    }
}
