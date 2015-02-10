package com.retech.edu.task.schedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by jackwang on 15-2-10.
 */
public class CronTriggerRunner {

    public static void main(String args[]) {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler;
        try {
            scheduler = schedulerFactory.getScheduler();
            //引进作业程序
            JobDetail jobDetail =
                    new JobDetail("jobDetail-s1", "jobDetailGroup-s1", HelloJob.class);
            //new一个触发器
            CronTrigger trigger = new CronTrigger("simpleTrigger", "triggerGroup-s1");
              /**/ /*  每1分钟执行一次  */
            trigger.setCronExpression("0 33 16 * * ?");
            scheduler.scheduleJob(jobDetail, trigger);
            //启动调度器
            scheduler.start();
        } catch (Exception ex) {

        }
    }
}
