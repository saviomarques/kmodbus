package com.modbus.util;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ModbusTool {

    private String ip_adrs;//IP地址
    private String Port;//链接端口
    private char[] bwt;//报文头前五位
    private int unit = 1;//单元标识
    private Socket es;//套字节

    /**
     * 构造函数
     * @param ip_adrs
     * @param Port
     * @param bwt
     * @throws java.net.UnknownHostException
     * @throws java.io.IOException
     */
    public ModbusTool(String ip_adrs, String Port, char[] bwt) throws UnknownHostException, IOException {
        this.ip_adrs = ip_adrs;
        this.Port = Port;
        this.bwt = bwt;
        this.connect();
    }

    /**
     * 链接aps仪器
     * @throws java.net.UnknownHostException
     * @throws java.io.IOException
     */
    public void connect() throws UnknownHostException, IOException{
        this.es = new Socket(this.getIp_adrs(), Integer.parseInt(this.getPort()));
    }

    /**
     * 关闭链接
     * @throws java.io.IOException
     */
    public void close() throws IOException{
        this.es.close();
    }

    public char[] getBwt() {
        return bwt;
    }

    public void setBwt(char[] bwt) {
        this.bwt = bwt;
    }

    public String getPort() {
        return Port;
    }

    public void setPort(String Port) {
        this.Port = Port;
    }

    public String getIp_adrs() {
        return ip_adrs;
    }

    public void setIp_adrs(String ip_adrs) {
        this.ip_adrs = ip_adrs;
    }

    /**
     * 高低位运算
     * @String high 高位
     * @String down 低位
     * @return
     */
    public long HighDown(String high, String down) {
        return (Long.parseLong(high) * 65536 + Long.parseLong(down));
    }

    /**
     *
     * @param reg_no 开始地址
     * @param num_regs 寄存器数量
     * @return
     */
    public List<String> runmodbus(int reg_no, int num_regs) {
        List<String> result = new ArrayList();
        try {
            OutputStream os = es.getOutputStream();
            FilterInputStream is = new BufferedInputStream(es.getInputStream());
            byte obuf[] = new byte[261];
            byte ibuf[] = new byte[261];
            int i;
            // 初始化报文头 0 0 0 0 0 6 ui 3 rr rr nn nn
            for (i = 0; i < 5; i++) {
                obuf[i] = (byte) (Integer.parseInt(String.valueOf(this.getBwt()[i])) & 0xff);
            }
            // 后面的个数
            obuf[5] = 6;
            // 设置单元标示
            obuf[6] = (byte) unit;
            // 通讯类型（3代表可读写）
            obuf[7] = 3;
            // 开始地址
            obuf[8] = (byte) (reg_no >> 8);
            obuf[9] = (byte) (reg_no & 0xff);
            // 要读取寄存器的个数
            obuf[10] = (byte) (num_regs >> 8);
            obuf[11] = (byte) (num_regs & 0xff);
            // 发送
            os.write(obuf, 0, 12);
            // 接收读取
            i = is.read(ibuf, 0, 261);
            if (i < 9) {
                if (i == 0) {
                    result.add("unexpected close of connection at remote end");
                } else {
                    result.add("response was too short - " + i + " chars");
                }
            } else if (0 != (ibuf[7] & 0x80)) {
                result.add("MODBUS exception response - type " + ibuf[8]);
            } else if (i != (9 + 2 * num_regs)) {
                result.add("incorrect response size is " + i + " expected" + (9 + 2 * num_regs));
            } else {
                for (int z = 0; z < num_regs; z++) {
                    long w = (ibuf[9 + z * 2] << 8) + (ibuf[9 + z * 2 + 1] & 0xff);
                    result.add(String.valueOf(w));
                }
            }
            this.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入modbus的salve端
     * @param reg_no 开始地址
     * @param num_regs 寄存器数
     * @param wCount 要写入的字节
     * @param wContent 要写入内容（多个寄存器用","号隔开）
     * @return
     */
    public boolean writemodbus(int reg_no, int num_regs, String wCount, String wContent) {
        try {
            String[] WContent2 = wContent.split(",");
            int WContentCount = WContent2.length;
            OutputStream os = es.getOutputStream();
            byte obuf[] = new byte[261];
            int i;
            // 初始化报文头 0 0 0 0 0 6 ui 3 rr rr nn nn
            for (i = 0; i < 5; i++) {
                obuf[i] = (byte) (Integer.parseInt(String.valueOf(this.getBwt()[i])) & 0xff);
            }
            // 后面的个数
            obuf[5] = (byte) (7 + Integer.parseInt(wCount));
            // 设置单元标示
            obuf[6] = (byte) unit;
            // 通讯类型（16代表可写）
            obuf[7] = 16;
            // 开始地址
            obuf[8] = (byte) (reg_no >> 8);
            obuf[9] = (byte) (reg_no & 0xff);
            // 要读取寄存器的个数
            obuf[10] = (byte) (num_regs >> 8);
            obuf[11] = (byte) (num_regs & 0xff);
            // 要写入的字节数
            obuf[12] = (byte) Integer.parseInt(wCount);
            // 要写入的字节
            int wc;
            int q = 0;
            for (int p = 0; p < WContentCount; p++) {
                wc = Integer.parseInt(WContent2[p]);
                obuf[13 + q] = (byte) (wc >> 8);
                obuf[14 + q] = (byte) (wc & 0xff);
                q += 2;
            }
            // 发送
            os.write(obuf, 0, 13 + q);
            this.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
