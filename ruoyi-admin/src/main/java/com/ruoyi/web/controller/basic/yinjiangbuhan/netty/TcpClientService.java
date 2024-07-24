package com.ruoyi.web.controller.basic.yinjiangbuhan.netty;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

@Slf4j
public class TcpClientService {

    private static String host = "oa.sntsoft.com";
    private static int port = 8095;

    public static String sendTcpRequest(String message) {
        try {
            //发送到8888端口
            Socket socket = new Socket("58.48.101.155", 9045);
            OutputStream outputStream = socket.getOutputStream();
            //输出流
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream, Charset.forName("GBK")), true);

            printWriter.write(message);
            printWriter.flush();
            InputStream in=socket.getInputStream();
            byte[] data=new byte[1024];
            int len=in.read(data);
            String s = new String(data, 0, len);
            //关闭资源
            printWriter.close();
            outputStream.close();
            socket.close();
            return s;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }//18058518487
}