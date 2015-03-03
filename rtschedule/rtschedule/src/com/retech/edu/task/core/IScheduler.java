package com.retech.edu.task.core;

import com.retech.edu.task.core.TaskSchedulerInfo;
import com.retech.edu.task.core.TaskTypes;

import java.util.Date;
import java.util.List;

/**
 * Created by jackwang on 15/3/2.
 */
public interface IScheduler
{
    void Start();
    void Stop();

    Date AddScheduler(String name, String group, TaskTypes taskType, Date startDate, Date endDate, String cronString, String taskParameters);
    void RemoveScheduler(String name, String group);

    void PauseScheduler(String name, String group);
    void ResumeScheduler(String name, String group);
    List<TaskSchedulerInfo> GetCurrentlyExecutingJobs();


    Boolean IsStarted();
}
