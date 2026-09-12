package com.ruoyi.web.controller.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.system.domain.NxpAppMessage;
import com.ruoyi.system.service.IAppMessageApiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Yancheng Liu
 * @create 2021-02-07 17:49
 */
@RestController
@RequestMapping("/api/app-message")
public class AppMessageApiController extends BaseController {

    @Resource
    private IAppMessageApiService appMessageApiService;

    /**
     * 获取指定用户的消息列表
     * @param nxpAppMessage
     * @return
     */
    @PostMapping("/get-message")
    public BaseVO getMessage(@RequestBody NxpAppMessage nxpAppMessage){
        if (nxpAppMessage.getPageNum() == null){
            nxpAppMessage.setPageNum(1);
        }
        if (nxpAppMessage.getPageSize() == null){
            nxpAppMessage.setPageSize(10);
        }
        PageHelper.startPage(nxpAppMessage.getPageNum(), nxpAppMessage.getPageSize());
        return BaseVO.success(new PageInfo<>(appMessageApiService.getMessage(nxpAppMessage)));
    }

    /**
     * 更新消息状态
     * @param nxpAppMessage
     * @return
     */
    @PostMapping("/update-message")
    public BaseVO updateMessage(@RequestBody NxpAppMessage nxpAppMessage){
        return appMessageApiService.updateMessage(nxpAppMessage);
    }

    /**
     * 删除消息
     * @param nxpAppMessage
     * @return
     */
    @PostMapping("/delete-message")
    public BaseVO deleteMessage(@RequestBody NxpAppMessage nxpAppMessage){
        return appMessageApiService.deleteMessage(nxpAppMessage);
    }

    /**
     * 提交消息
     * @param nxpAppMessage
     * @return
     */
    @PostMapping("/create-message")
    public BaseVO createMessage(@RequestBody NxpAppMessage nxpAppMessage){
        return appMessageApiService.createMessage(nxpAppMessage);
    }

    /**
     * 查看消息
     * @param nxpAppMessage
     * @return
     */
    @PostMapping("/detail-message")
    public BaseVO detailMessage(@RequestBody NxpAppMessage nxpAppMessage){
        return appMessageApiService.detailMessage(nxpAppMessage);
    }

    /**
     * 消息轮询接口
     * @param nxpAppMessage
     * @return
     */
    @PostMapping("/message-polling")
    public BaseVO messagePolling(@RequestBody NxpAppMessage nxpAppMessage){
        return appMessageApiService.messagePolling(nxpAppMessage);
    }

    /**
     * 批量更新消息为已读
     * @param nxpAppMessage
     * @return
     */
    @PostMapping("/msg-all-update")
    public BaseVO allMsgUpdate(@RequestBody NxpAppMessage nxpAppMessage){
        return appMessageApiService.allMsgUpdate(nxpAppMessage);
    }

    /**
     * 批量删除消息
     * @param nxpAppMessage
     * @return
     */
    @PostMapping("/msg-all-delete")
    public BaseVO allMsdDelete(@RequestBody NxpAppMessage nxpAppMessage){
        return appMessageApiService.allMsdDelete(nxpAppMessage);
    }
}
