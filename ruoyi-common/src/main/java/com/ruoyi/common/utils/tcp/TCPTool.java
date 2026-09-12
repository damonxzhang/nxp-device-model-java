package com.ruoyi.common.utils.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author snkj
 * @create 2023-07-10 20:15
 */
public class TCPTool {

    private static final int TIMEOUT = 3000; // 连接超时时间（单位：毫秒）
    private static final int MAX_RETRIES = 3; // 最大重试次数

    /**
     * 连接到指定服务器的指定端口，并返回与该服务器的连接套接字
     *
     * @param serverAddress 服务器地址
     * @param port          服务器端口
     * @return 连接套接字
     * @throws IOException 连接异常
     */
    public static Socket connect(String serverAddress, int port) throws IOException {
        int retries = 0;
        Socket socket = null;
        while (socket == null && retries < MAX_RETRIES) { // 当套接字为空且重试次数不超过最大重试次数时执行
            try {
                socket = new Socket(serverAddress, port); // 创建新的套接字
                socket.setSoTimeout(TIMEOUT); // 设置读取超时时间
            } catch (IOException e) {
                retries++;
            }
        }
        if (socket == null) { // 当套接字为空且已达到最大重试次数时抛出异常
            throw new IOException("无法连接到服务器");
        }
        return socket;
    }

    /**
     * 接收来自服务器的消息并返回消息内容
     *
     * @param socket 连接套接字
     * @return 消息内容
     * @throws IOException 接收异常
     */
    public static String receiveMessage(Socket socket) throws IOException {
        byte[] buffer = new byte[1024];
        InputStream inputStream = socket.getInputStream();
        int bytesRead = inputStream.read(buffer); // 从输入流中读取数据
        if (bytesRead == -1) { // 如果返回-1，则表示连接已关闭
            throw new SocketException("连接已关闭");
        }
        return new String(buffer, 0, bytesRead);
    }

    /**
     * 向服务器发送消息
     *
     * @param socket  连接套接字
     * @param message 消息内容
     * @throws IOException 发送异常
     */
    public static void sendMessage(Socket socket, String message) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(message.getBytes()); // 将消息内容写入输出流
        outputStream.flush(); // 刷新输出流
    }

    /**
     * 断开连接并关闭套接字
     *
     * @param socket 连接套接字
     */
    public static void disconnect(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            // 忽略异常
        }
    }

}
