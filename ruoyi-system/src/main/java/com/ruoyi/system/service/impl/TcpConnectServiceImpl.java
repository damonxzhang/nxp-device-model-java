package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.ConvertUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.tcp.OperationUtils;
import com.ruoyi.common.utils.tcp.TCPTool;
import com.ruoyi.common.utils.tcp.TCPUtil;
import com.ruoyi.system.service.ITcpConnectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

/**
 * 智能柜service
 * @author snkj
 * @create 2023-07-10 20:18
 */
@Service
@Slf4j
public class TcpConnectServiceImpl implements ITcpConnectService {

    /**
     * 发送开锁指令
     *
     * @param serverAddress 服务器地址
     * @param port          服务器端口
     * @param boxNum        箱号
     */
    @Override
    public Map<String, String> sendOpenInstruct(String serverAddress, Integer port, Integer boxNum) throws IOException {
        log.error("开锁指令接收到的参数 --> {},{},{}",serverAddress,port,boxNum);
        // 组装命令
        String instruct = "55" + ConvertUtil.toHex(boxNum).toUpperCase() + "A15F00";
        String yihuo = OperationUtils.yihuo(instruct);
        instruct = instruct + yihuo;
        log.error("开锁指令最终执行的指令为 --> {}",instruct);
        // 发送指令
        Map<String, String> resultMap = TCPUtil.sendTCPRequest(serverAddress, String.valueOf(port), instruct.toUpperCase(), "utf8");
        // 接收指令 55 07 A2 30 70 B0
        log.error("开锁指令执行结果，接收到的返回 --> {}", JSON.toJSONString(resultMap.get("respData")));
        // 断开连接
        return resultMap;
    }

    public static void main(String[] args) {
        /*String str = "55 07 A2 30 70 B0";
        String[] s = str.split(" ");
        if (s.length >= 6){
            System.out.println(s[4]);
        }*/
        Integer boxNum = 30;

        String s = ConvertUtil.toHex(boxNum);
        System.out.println(s.toUpperCase());
    }
}
