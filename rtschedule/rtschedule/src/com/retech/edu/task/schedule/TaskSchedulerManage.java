package com.retech.edu.task.schedule;

import com.retech.edu.task.core.IScheduler;
import com.retech.edu.task.core.ITaskScheduler;
import com.retech.edu.task.core.TaskSchedulerInfo;
import com.retech.edu.task.core.TaskTypes;
import org.quartz.SchedulerException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by jackwang on 15/3/2.
 */
public class TaskSchedulerManage implements ITaskScheduler {
    private final IScheduler quartzScheduler;

    public TaskSchedulerManage() {
        quartzScheduler = new QuartzScheduler();
    }

    @Override
    public void Start() {
        quartzScheduler.Start();
        AddAllJob();
    }

    @Override
    public void Stop() {
        quartzScheduler.Stop();
    }

    @Override
    public void Pause(int id) {

    }

    @Override
    public void Resume(int id) {

    }

    @Override
    public void Remove(int id) {

    }

    @Override
    public void Register(TaskSchedulerInfo taskSchedulerInfo) {

    }

    @Override
    public void Deregister(int id) {

    }

    @Override
    public void Update(TaskSchedulerInfo taskSchedulerInfo) {

    }

    @Override
    public TaskSchedulerInfo Get(int id) {
        return null;
    }

    @Override
    public List<TaskSchedulerInfo> GetAll() {
        return null;
    }

    @Override
    public List<TaskSchedulerInfo> GetCurrentlyExecutingJobs() {
        return null;
    }

    @Override
    public boolean IsStarted() {
        return false;
    }

    private void AddAllJob(){
       Calendar calendar = new GregorianCalendar(2999, 11, 25,0,0,0);
       Date date = calendar.getTime();
        quartzScheduler.AddScheduler("Hello","test", TaskTypes.CustomTask, new Date(),
                date ,"0/10 * * * * ?","jackwang");
    }
}
