/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.modbus.main;

import com.modbus.quartz.ModbusQuertz;
import com.modbus.util.modbusTool;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.SchedulerException;

/**
 *
 * @author Administrator
 */
public class Test {

    public static void main(String[] args) throws InterruptedException, IOException, SchedulerException {
//        modbusTool instance = new modbusTool("127.0.0.1", "502", new char[]{'0', '0', '0', '0', '0'});
//        int i = 0;
//        while (i < 1) {
//            List<String> result = instance.runmodbus(0, 4);
//            for (String s : result) {
//                s += "0";
//                byte sss = Byte.parseByte(s);
//                long a = sss << 8;
//                System.out.println(s + ":" + a +":"+ sss);
//            }
//            Thread.sleep(1000);
//            i++;
//        }
//        instance.clossConnect();
        ModbusQuertz qz = new ModbusQuertz();

        qz.startTask();
        //Thread.sleep(2000);
        //qz.stopTask();
    }
}
