package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpAppMessage;

/**
 * app消息Service接口
 * 
 * @author YanCheng Liu
 * @date 2021-02-07
 */
public interface INxpAppMessageService 
{
    /**
     * 查询app消息
     * 
     * @param id app消息ID
     * @return app消息
     */
    public NxpAppMessage selectNxpAppMessageById(Long id);

    /**
     * 查询app消息列表
     * 
     * @param nxpAppMessage app消息
     * @return app消息集合
     */
    public List<NxpAppMessage> selectNxpAppMessageList(NxpAppMessage nxpAppMessage);

    /**
     * 新增app消息
     * 
     * @param nxpAppMessage app消息
     * @return 结果
     */
    public int insertNxpAppMessage(NxpAppMessage nxpAppMessage);

    /**
     * 修改app消息
     * 
     * @param nxpAppMessage app消息
     * @return 结果
     */
    public int updateNxpAppMessage(NxpAppMessage nxpAppMessage);

    /**
     * 批量删除app消息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpAppMessageByIds(String ids);

    /**
     * 删除app消息信息
     * 
     * @param id app消息ID
     * @return 结果
     */
    public int deleteNxpAppMessageById(Long id);
}
