package com.retech.edu.task.core;

import java.util.List;

/**
 * Created by jackwang on 15/3/2.
 */
public interface ITaskSchedulerManager {
    void Register(TaskSchedulerInfo taskSchedulerInfo);
    void Deregister(int id);
    void Update(TaskSchedulerInfo taskSchedulerInfo);
    TaskSchedulerInfo Get(int id);
    List<TaskSchedulerInfo> GetAll();
    List<TaskSchedulerInfo> GetCurrentlyExecutingJobs();
    boolean IsStarted();
}

