package com.retech.edu.task.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * zookeeper的管理
 * @author jackwang
 */
public class ZKManager {
    private CuratorFramework client;

    public ZKManager(String zkAddress) {
        client = CuratorFrameworkFactory.newClient(zkAddress, new ExponentialBackoffRetry(1000, 3));
        client.start();
    }

    /**
     * 销毁资源
     */
    public void destory() {
        if (client != null) {
            client.close();
        }
    }


    /**
     * 获取client
     *
     * @return
     */
    public CuratorFramework getClient() {
        return client;
    }
}
