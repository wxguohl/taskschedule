package com.retech.edu.task.schedule;

import com.retech.edu.task.core.IScheduler;
import com.retech.edu.task.core.TaskSchedulerInfo;
import com.retech.edu.task.core.TaskTypes;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jackwang on 15/3/2.
 */
public class QuartzScheduler implements IScheduler {
    private Scheduler scheduler;

    public QuartzScheduler(){
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Start() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Stop() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Date AddScheduler(String name, String group, TaskTypes taskType, Date startDate, Date endDate, String cronString, String taskParameters) {
        JobDetail jobDetail=new JobDetail(name, group, HelloJob.class);
        jobDetail.getJobDataMap().put("taskParameters", taskParameters);
        CronTrigger trigger = null;
        try {
            trigger = new CronTrigger(name, group, cronString);
            trigger.setStartTime(startDate);
            trigger.setEndTime(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            return scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void RemoveScheduler(String name, String group) {
        try {
            scheduler.deleteJob(name, group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void PauseScheduler(String name, String group) {
        try {
            scheduler.pauseJob(name, group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ResumeScheduler(String name, String group) {
        try {
            scheduler.resumeJob(name, group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TaskSchedulerInfo> GetCurrentlyExecutingJobs() {
        List<TaskSchedulerInfo> list = new ArrayList<TaskSchedulerInfo>();
        List curr =null;
        try {
            curr = scheduler.getCurrentlyExecutingJobs();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        for(Object item:curr){
            TaskSchedulerInfo taskSchedulerInfo = new TaskSchedulerInfo();
            JobExecutionContext jobExecutionContext = (JobExecutionContext)item;
            taskSchedulerInfo.setName(jobExecutionContext.getJobDetail().getName());
            taskSchedulerInfo.setTaskgroup(jobExecutionContext.getJobDetail().getGroup());
            taskSchedulerInfo.setStartdate(jobExecutionContext.getFireTime());
            list.add(taskSchedulerInfo);
        }
        return list;
    }

    @Override
    public Boolean IsStarted() {
        boolean isstart=true;
        try {
             isstart= scheduler.isStarted();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return isstart;
    }
}
