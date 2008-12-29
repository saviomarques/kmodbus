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
    private String jobDetailName;
    private String jobDetailGroupName;
    private String cronTriggerName;
    private String triggerGroupName;
    private Class runMethod;
    private String runTimeSet;

    public ModbusQuertz(String jobDetailName, String jobDetailGroupName,
            String cronTriggerName, String triggerGroupName ,Class runMethod ,String runTimeSet) {
        this.jobDetailName = jobDetailName;
        this.jobDetailGroupName = jobDetailGroupName;
        this.cronTriggerName = cronTriggerName;
        this.triggerGroupName = triggerGroupName;
        this.runMethod = runMethod;
        this.runTimeSet = runTimeSet;
    }

    public void startTask() throws SchedulerException {
        this.schedulerFactory = new StdSchedulerFactory();
        this.scheduler = schedulerFactory.getScheduler();
        JobDetail jobDetail =
                new JobDetail(this.jobDetailName, this.jobDetailGroupName,
                this.runMethod);
        CronTrigger cronTrigger = new CronTrigger(this.cronTriggerName,
                this.triggerGroupName);
        try {
            CronExpression cexp = new CronExpression(this.runTimeSet);
            cronTrigger.setCronExpression(cexp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.scheduler.scheduleJob(jobDetail, cronTrigger);
        this.scheduler.start();
    }

    public void stopTask() throws SchedulerException {
        this.scheduler.shutdown();
    }

    public String getCronTriggerName() {
        return cronTriggerName;
    }

    public void setCronTriggerName(String cronTriggerName) {
        this.cronTriggerName = cronTriggerName;
    }

    public String getJobDetailGroupName() {
        return jobDetailGroupName;
    }

    public void setJobDetailGroupName(String jobDetailGroupName) {
        this.jobDetailGroupName = jobDetailGroupName;
    }

    public String getJobDetailName() {
        return jobDetailName;
    }

    public void setJobDetailName(String jobDetailName) {
        this.jobDetailName = jobDetailName;
    }

    public Class getRunMethod() {
        return runMethod;
    }

    public void setRunMethod(Class runMethod) {
        this.runMethod = runMethod;
    }

    public String getRunTimeSet() {
        return runTimeSet;
    }

    public void setRunTimeSet(String runTimeSet) {
        this.runTimeSet = runTimeSet;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public SchedulerFactory getSchedulerFactory() {
        return schedulerFactory;
    }

    public void setSchedulerFactory(SchedulerFactory schedulerFactory) {
        this.schedulerFactory = schedulerFactory;
    }

    public String getTriggerGroupName() {
        return triggerGroupName;
    }

    public void setTriggerGroupName(String triggerGroupName) {
        this.triggerGroupName = triggerGroupName;
    }

    
}
