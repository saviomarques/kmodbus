package com.modbus.quartz;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 调度器
 * @author kenter
 */
public class ModbusQuertz {

    private SchedulerFactory schedulerFactory;
    private Scheduler scheduler;

    public void startTask() throws SchedulerException {
        this.schedulerFactory = new StdSchedulerFactory();
        this.scheduler = schedulerFactory.getScheduler();
        JobDetail jobDetail =
                new JobDetail("jobDetail", "jobDetailGroup", com.modbus.quartz.ModbusJob.class);
        CronTrigger cronTrigger = new CronTrigger("cronTrigger", "triggerGroup");
        try {
            CronExpression cexp = new CronExpression("0/1 * * * * ?");
            cronTrigger.setCronExpression(cexp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.scheduler.scheduleJob(jobDetail, cronTrigger);
        this.scheduler.start();
    }

    public void stopTask() throws SchedulerException{
        this.scheduler.shutdown();
    }

}
