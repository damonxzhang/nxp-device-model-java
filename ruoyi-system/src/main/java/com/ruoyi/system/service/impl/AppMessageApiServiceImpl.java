package com.ruoyi.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.NxpAppMessage;
import com.ruoyi.system.domain.NxpFacility;
import com.ruoyi.system.domain.NxpMachineProcess;
import com.ruoyi.system.domain.NxpUserInfo;
import com.ruoyi.system.mapper.NxpAppMessageMapper;
import com.ruoyi.system.mapper.NxpFacilityMapper;
import com.ruoyi.system.mapper.NxpMachineProcessMapper;
import com.ruoyi.system.mapper.NxpUserInfoMapper;
import com.ruoyi.system.service.IAppMessageApiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Yancheng Liu
 * @create 2021-02-07 17:51
 */
@Service("IAppMessageApiService")
@DataSource(value = DataSourceType.SLAVE)
public class AppMessageApiServiceImpl implements IAppMessageApiService {

    @Resource
    private NxpAppMessageMapper nxpAppMessageMapper;
    @Resource
    private NxpUserInfoMapper nxpUserInfoMapper;
    @Resource
    private NxpFacilityMapper nxpFacilityMapper;
    @Resource
    private NxpMachineProcessMapper nxpMachineProcessMapper;

    @Override
    public List<NxpAppMessage> getMessage(NxpAppMessage nxpAppMessage) {
        nxpAppMessage.setDel("NO");
        List<NxpAppMessage> nxpAppMessages = nxpAppMessageMapper.selectNxpAppMessageList(nxpAppMessage);
        for (NxpAppMessage appMessage : nxpAppMessages) {
            Long user_id = appMessage.getCreate_user_id();
            NxpUserInfo userInfo = nxpUserInfoMapper.selectNxpUserInfoById(user_id);
            appMessage.setHead_user(userInfo.getChinese_name());
            Long facility_id = appMessage.getFacility_id();
            NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(facility_id);
            if (nxpFacility != null){
                appMessage.setFacility_name(nxpFacility.getName());
                appMessage.setFacility_number(nxpFacility.getNumber());
                //  工序
                String process = nxpFacility.getProcess();
                NxpMachineProcess machineProcess = nxpMachineProcessMapper.selectNxpMachineProcessById(Long.valueOf(process));
                appMessage.setProcess(machineProcess.getName());
            }
            appMessage.setIs_receive(0);
            nxpAppMessageMapper.updateNxpAppMessage(appMessage);
        }
        return nxpAppMessages;
    }

    @Override
    @Transactional
    public BaseVO updateMessage(NxpAppMessage nxpAppMessage) {
        NxpAppMessage message = nxpAppMessageMapper.selectNxpAppMessageById(nxpAppMessage.getId());
        if (message != null){
            message.setStatus(2);
            nxpAppMessageMapper.updateNxpAppMessage(message);
        }
        return BaseVO.success();
    }

    @Override
    @Transactional
    public BaseVO deleteMessage(NxpAppMessage nxpAppMessage) {
        NxpAppMessage message = nxpAppMessageMapper.selectNxpAppMessageById(nxpAppMessage.getId());
        if (message != null){
            message.setDel("YES");
            nxpAppMessageMapper.updateNxpAppMessage(message);
        }
        return BaseVO.success();
    }

    @Override
    public BaseVO createMessage(NxpAppMessage nxpAppMessage) {
        nxpAppMessage.setCreated_at(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        nxpAppMessageMapper.insertNxpAppMessage(nxpAppMessage);
        return BaseVO.error();
    }

    @Override
    public BaseVO detailMessage(NxpAppMessage nxpAppMessage) {
        NxpAppMessage message = nxpAppMessageMapper.selectNxpAppMessageById(nxpAppMessage.getId());
        if (message != null){
            // 设置为已读
            message.setStatus(2);
            nxpAppMessageMapper.updateNxpAppMessage(message);
        }
        return BaseVO.success(message);
    }

    @Override
    public BaseVO messagePolling(NxpAppMessage nxpAppMessage) {
        // 根据userId查询当前用户有没有最新消息
        nxpAppMessage.setIs_receive(1);
        List<NxpAppMessage> nxpAppMessages = nxpAppMessageMapper.selectNxpAppMessageList(nxpAppMessage);
        JSONObject jsonObject = new JSONObject();
        if (nxpAppMessages.size() > 0){
            jsonObject.put("isReceive","true");
            // 推送过后则变更为已接收
            for (NxpAppMessage appMessage : nxpAppMessages) {
                appMessage.setIs_receive(0);
                nxpAppMessageMapper.updateNxpAppMessage(appMessage);
            }
        }else {
            jsonObject.put("isReceive","false");
        }
        return BaseVO.success(jsonObject);
    }

    @Override
    public BaseVO allMsgUpdate(NxpAppMessage nxpAppMessage) {
        // 判断参数是否传输
        if (nxpAppMessage.getUser_id() == null){
            return BaseVO.error("user_id为必传字段");
        }
        // 根据userId查询所有的未读消息
        NxpAppMessage msg = new NxpAppMessage();
        msg.setUser_id(nxpAppMessage.getUser_id());
        msg.setStatus(1);
        msg.setDel("NO");
        List<NxpAppMessage> nxpAppMessages = nxpAppMessageMapper.selectNxpAppMessageList(msg);
        for (NxpAppMessage appMessage : nxpAppMessages) {
            appMessage.setStatus(2);
            nxpAppMessageMapper.updateNxpAppMessage(appMessage);
        }
        return BaseVO.success("全部已读成功");
    }

    @Override
    public BaseVO allMsdDelete(NxpAppMessage nxpAppMessage) {
        // 判断参数是否传输
        if (nxpAppMessage.getUser_id() == null){
            return BaseVO.error("user_id为必传字段");
        }
        // 根据userId查询所有的未读消息
        NxpAppMessage msg = new NxpAppMessage();
        msg.setUser_id(nxpAppMessage.getUser_id());
        msg.setStatus(2);
        msg.setDel("NO");
        List<NxpAppMessage> nxpAppMessages = nxpAppMessageMapper.selectNxpAppMessageList(msg);
        for (NxpAppMessage appMessage : nxpAppMessages) {
            appMessage.setDel("YES");
            nxpAppMessageMapper.updateNxpAppMessage(appMessage);
        }
        return BaseVO.success("全部删除成功");
    }
}
