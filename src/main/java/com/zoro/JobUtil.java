package com.zoro;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created on 2018/7/6.
 *
 * @author dubber
 */
public class JobUtil {

    // 声明调度器
    private Scheduler scheduler = null;
    // 创建 Job 组名
    private String jobsGroupName = "jobsGroup";

    // 创建调度器对象
    public Scheduler createScheduler() throws SchedulerException {
        return new StdSchedulerFactory().getScheduler();
    }

    // add 为调度器添加调度器对象
    public void addJob(String jobName) throws SchedulerException {
        //用于给Job实现类的excute方法传递数据
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName",jobName);

        //创建JobDetail对象
        //设定Job执行类，设定Job标识，设定数据map
        JobDetail jobDetail = newJob(JobExcuteClass.class)
                .withIdentity(jobName, jobsGroupName)
                .usingJobData(jobDataMap)
                .build();

        //执行策略，Cron表达式，代表十秒执行一次
        String strategy = "0/10 0/1 0/1 1/1 * ?";
        //创建触发器，应用策略
        CronTrigger trigger = newTrigger().withSchedule(cronSchedule(strategy)).build();

        //创建调度器对象
        scheduler = createScheduler();
        //启动调度器
        scheduler.start();
        //将myJob对象与trigger对象加入调度器
        scheduler.scheduleJob(jobDetail,trigger);
    }


    // remove 删除调度器对象

    public void removeJob(String jobName) throws SchedulerException {
        //Job标识对象
        JobKey key = new JobKey(jobName, jobsGroupName);
        //注意这里一定要再次创建调度器对象，否则涉及持久化时，会抛出空指针异常
        scheduler = createScheduler();
        //从调度器中移除任务
        scheduler.deleteJob(key);
    }

    public static void main(String[] args) {
        try {
            new JobUtil().addJob("zoro-quartz");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
