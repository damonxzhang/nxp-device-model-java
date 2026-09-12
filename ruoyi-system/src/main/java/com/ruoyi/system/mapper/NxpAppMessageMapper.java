package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpAppMessage;

/**
 * app消息Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2021-02-07
 */
public interface NxpAppMessageMapper 
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
    public int updateNxpAppMessageByInspectionId(Long inspection_id);

    /**
     * 删除app消息
     * 
     * @param id app消息ID
     * @return 结果
     */
    public int deleteNxpAppMessageById(Long id);

    /**
     * 批量删除app消息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpAppMessageByIds(String[] ids);

    /**
     * 批量更新消息为已读
     * @param nxpAppMessage
     * @return
     */
    int updateBatch(NxpAppMessage nxpAppMessage);
}
