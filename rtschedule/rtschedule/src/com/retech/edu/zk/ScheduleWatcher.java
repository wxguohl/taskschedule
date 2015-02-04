package com.retech.edu.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.Watcher;

/**
 * zookeeper监听器
 * @author jackwang
 */
public class ScheduleWatcher {
    /**
     * 增加监听
     *
     * @param node
     * @param isSelf
     *            true 为node本身增加监听 false 为node的子节点增加监听
     * @throws Exception
     */
    public void addWatch(CuratorFramework client,String node, boolean isSelf) throws Exception {
        if (isSelf) {
            client.getData().watched().forPath(node);
        }
        else {
            client.getChildren().watched().forPath(node);
        }
    }


    /**
     * 增加监听
     *
     * @param node
     * @param isSelf
     *            true 为node本身增加监听 false 为node的子节点增加监听
     * @param watcher
     * @throws Exception
     */
    public void addWatch(CuratorFramework client,String node, boolean isSelf, Watcher watcher) throws Exception {
        if (isSelf) {
            client.getData().usingWatcher(watcher).forPath(node);
        }
        else {
            client.getChildren().usingWatcher(watcher).forPath(node);
        }
    }


    /**
     * 增加监听
     *
     * @param node
     * @param isSelf
     *            true 为node本身增加监听 false 为node的子节点增加监听
     * @param watcher
     * @throws Exception
     */
    public void addWatch(CuratorFramework client,String node, boolean isSelf, CuratorWatcher watcher) throws Exception {
        if (isSelf) {
            client.getData().usingWatcher(watcher).forPath(node);
        }
        else {
            client.getChildren().usingWatcher(watcher).forPath(node);
        }
    }

}
