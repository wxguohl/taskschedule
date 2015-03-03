package edu.task.scheduler;

import com.retech.edu.task.schedule.HelloJob;
import com.retech.edu.task.schedule.TaskSchedulerManage;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by jackwang on 15/3/2.
 */
public class SchedulerRunner {
    public static void main(String args[]) {
        System.out.println("Starting...");
        TaskSchedulerManage taskScheduler = new TaskSchedulerManage();
        taskScheduler.Start();
    }

//        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//        Scheduler scheduler;
//        try {
//            scheduler = schedulerFactory.getScheduler();
//            //引进作业程序
//            JobDetail jobDetail =
//                    new JobDetail("jobDetail-s1", "jobDetailGroup-s1", HelloJob.class);
//            //new一个触发器
//            CronTrigger trigger = new CronTrigger("simpleTrigger", "triggerGroup-s1");
//              /**/ /*  每1分钟执行一次  */
//            trigger.setCronExpression("0/10 * * * * ?");
//            scheduler.scheduleJob(jobDetail, trigger);
//            //启动调度器
//            scheduler.start();
//        } catch (Exception ex) {
//        }
}
