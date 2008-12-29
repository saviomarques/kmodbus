package com.modbus.quartz;

import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 执行任务
 * @author kenter
 */
public class ModbusJob implements Job {

    public ModbusJob(){

    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println("执行时间：" + new Date());
    }
}
