package com.retech.edu.task.schedule;

import com.retech.edu.task.zk.ZKManager;
import com.retech.edu.task.zk.ZKTools;
import org.apache.curator.framework.CuratorFramework;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * Created by jackwang on 15/3/2.
 */
public class DistributeJob implements Job{
    private static String zkAddress = "centos6-jack.chinacloudapp.cn:2181";
    private static CuratorFramework client;
    private static ZKManager zkManager = null;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        zkManager = new ZKManager(zkAddress);
        client = zkManager.getClient();
        List<String> listcluster= ZKTools.listChildren(client,"");
//        ZKTools.createNode(client,"/rtschedule/currentjob/emailjob","www.mail.com");
        System.out.println("任务分发");
    }
}
