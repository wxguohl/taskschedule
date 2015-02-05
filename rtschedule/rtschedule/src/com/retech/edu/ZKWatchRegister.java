package com.retech.edu;

import com.retech.edu.zk.ZKTools;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

/**
 * Created by jackwang on 15-2-5.
 */
public class ZKWatchRegister implements CuratorWatcher {
    private CuratorFramework zkclient;
    public ZKWatchRegister(CuratorFramework zkclient){
        zkclient=zkclient;
    }
    @Override
    public void process(WatchedEvent event) throws Exception {
        System.out.println(event.getType());
        if(event.getType() == Watcher.Event.EventType.NodeDataChanged){
            //节点数据改变了，需要记录下来，以便session过期后，能够恢复到先前的数据状态
        }else if(event.getType() == Watcher.Event.EventType.NodeDeleted){
            //节点被删除了，需要创建新的节点
        }else if(event.getType() == Watcher.Event.EventType.NodeCreated){
            //节点被创建时，需要添加监听事件（创建可能是由于session过期后，curator的状态监听部分触发的）
        }
    }
}
