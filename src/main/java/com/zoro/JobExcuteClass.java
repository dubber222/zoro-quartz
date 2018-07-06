package com.zoro;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created on 2018/7/6.
 *
 * @author dubber
 */
public class JobExcuteClass implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 通过context 得到map中的job
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        System.out.println(jobDataMap.get("jobName") + " 执行");
    }
}
