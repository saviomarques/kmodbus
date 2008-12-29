/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modbus.main;

import com.modbus.quartz.ModbusJob;
import com.modbus.util.ModbusTool;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Administrator
 */
public class TestJob extends ModbusJob {

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        List<String> ls = new ArrayList();
        try {
            ModbusTool modbus = new ModbusTool("127.0.0.1", "502", new char[]{'0', '0', '0', '0', '0'});
            ls = modbus.runmodbus(0, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("test oh:" + new Date() +"数据是"+ ls.get(0));
    }
}
