package com.retech.edu;

import com.retech.edu.zk.ScheduleWatcher;
import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * Created by jackwang on 15-2-4.
 */
public class testWatcher implements CuratorWatcher {
    private CuratorFramework zkclient;
    public testWatcher(CuratorFramework curatorClient) {
        this.zkclient = zkclient;
    }
    @Override
    public void process(WatchedEvent watchedEvent) throws Exception {
        //节点数据改变
        if (watchedEvent.getType() != Watcher.Event.EventType.NodeDeleted) {
          System.out.println("NodeDeleted");
        } else if (watchedEvent.getType() == Watcher.Event.EventType.NodeDeleted) { //节点被删除
            System.out.println("NodeDeleted");
        }else{
            ScheduleWatcher scheduleWatcher=new ScheduleWatcher();
            scheduleWatcher.addWatch(zkclient, "/t4", true, new testWatcher(zkclient));
        }

    }
}
