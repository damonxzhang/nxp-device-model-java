package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpAppMessageMapper;
import com.ruoyi.system.domain.NxpAppMessage;
import com.ruoyi.system.service.INxpAppMessageService;
import com.ruoyi.common.core.text.Convert;

/**
 * app消息Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2021-02-07
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpAppMessageServiceImpl implements INxpAppMessageService 
{
    @Autowired
    private NxpAppMessageMapper nxpAppMessageMapper;

    /**
     * 查询app消息
     * 
     * @param id app消息ID
     * @return app消息
     */
    @Override
    public NxpAppMessage selectNxpAppMessageById(Long id)
    {
        return nxpAppMessageMapper.selectNxpAppMessageById(id);
    }

    /**
     * 查询app消息列表
     * 
     * @param nxpAppMessage app消息
     * @return app消息
     */
    @Override
    public List<NxpAppMessage> selectNxpAppMessageList(NxpAppMessage nxpAppMessage)
    {
        return nxpAppMessageMapper.selectNxpAppMessageList(nxpAppMessage);
    }

    /**
     * 新增app消息
     * 
     * @param nxpAppMessage app消息
     * @return 结果
     */
    @Override
    public int insertNxpAppMessage(NxpAppMessage nxpAppMessage)
    {
        return nxpAppMessageMapper.insertNxpAppMessage(nxpAppMessage);
    }

    /**
     * 修改app消息
     * 
     * @param nxpAppMessage app消息
     * @return 结果
     */
    @Override
    public int updateNxpAppMessage(NxpAppMessage nxpAppMessage)
    {
        return nxpAppMessageMapper.updateNxpAppMessage(nxpAppMessage);
    }

    /**
     * 删除app消息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpAppMessageByIds(String ids)
    {
        return nxpAppMessageMapper.deleteNxpAppMessageByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除app消息信息
     * 
     * @param id app消息ID
     * @return 结果
     */
    @Override
    public int deleteNxpAppMessageById(Long id)
    {
        return nxpAppMessageMapper.deleteNxpAppMessageById(id);
    }
}
