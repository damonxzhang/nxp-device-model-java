package com.ruoyi.system.service;

import java.io.IOException;
import java.util.Map;

/**
 * @author snkj
 * @create 2023-07-10 20:18
 */
public interface ITcpConnectService {

    /**
     * 发送开锁指令
     *
     * @param serverAddress 服务器地址
     * @param port          服务器端口
     * @param boxNum        箱号
     */
    public Map<String, String> sendOpenInstruct(String serverAddress, Integer port, Integer boxNum) throws IOException;

}
