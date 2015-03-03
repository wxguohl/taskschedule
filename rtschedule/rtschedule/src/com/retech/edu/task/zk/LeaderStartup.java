package com.retech.edu.task.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.zookeeper.CreateMode;

/**
 * Created by jackwang on 15/3/2.
 */
public class LeaderStartup {
    private static String zkAddress = "centos6-jack.chinacloudapp.cn:2181";
    private static CuratorFramework client;
    private static ZKManager zkManager = null;

    public static void main(String[] args) throws Exception {
        zkManager = new ZKManager(zkAddress);
        client = zkManager.getClient();
        // 创建一个临时节点
        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/rtschedule/cluster/taskjob", new byte[0]);
        Thread.sleep(Long.MAX_VALUE);
    }
}
