package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.system.domain.NxpAppMessage;

import java.util.List;

/**
 * @author Yancheng Liu
 * @create 2021-02-07 17:51
 */
public interface IAppMessageApiService {
    /**
     * 获取指定用户的消息列表
     * @param nxpAppMessage
     * @return
     */
    List<NxpAppMessage> getMessage(NxpAppMessage nxpAppMessage);

    /**
     * 更新消息状态
     * @param nxpAppMessage
     * @return
     */
    BaseVO updateMessage(NxpAppMessage nxpAppMessage);

    /**
     * 删除消息
     * @param nxpAppMessage
     * @return
     */
    BaseVO deleteMessage(NxpAppMessage nxpAppMessage);

    /**
     * 提交消息
     * @param nxpAppMessage
     * @return
     */
    BaseVO createMessage(NxpAppMessage nxpAppMessage);

    /**
     * 查看消息
     * @param nxpAppMessage
     * @return
     */
    BaseVO detailMessage(NxpAppMessage nxpAppMessage);

    /**
     * 消息轮询接口
     * @param nxpAppMessage
     * @return
     */
    BaseVO messagePolling(NxpAppMessage nxpAppMessage);

    /**
     * 批量更新消息为已读
     * @param nxpAppMessage
     * @return
     */
    BaseVO allMsgUpdate(NxpAppMessage nxpAppMessage);

    /**
     * 批量删除消息
     * @param nxpAppMessage
     * @return
     */
    BaseVO allMsdDelete(NxpAppMessage nxpAppMessage);
}
