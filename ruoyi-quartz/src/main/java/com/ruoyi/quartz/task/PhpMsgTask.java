package com.ruoyi.quartz.task;

import cn.hutool.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 定时调用php端接口
 * @author snkj
 * @create 2023-08-22 11:40
 */
@Component("phpMsgTask")
public class PhpMsgTask {

    private static final Logger log = LoggerFactory.getLogger(PhpMsgTask.class);

    public void send(String url){
        String result = HttpUtil.get(url);
    }

}
