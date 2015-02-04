package com.retech.edu.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * zookeeper监听事件触发
 * @author jackwang
 */
public class NodeEventListener implements CuratorListener {
    @Override
    public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
        System.out.println(event.toString() + ".......................");
        final WatchedEvent watchedEvent = event.getWatchedEvent();
        if (watchedEvent != null) {
            System.out.println(watchedEvent.getState() + "=======================" + watchedEvent.getType());
            if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
                switch (watchedEvent.getType()) {
                    case NodeChildrenChanged:
                        System.out.println();
                        // TODO
                        break;
                    case NodeDataChanged:
                        // TODO
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
