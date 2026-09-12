package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpSmartCabinet;

/**
 * 智能柜管理Service接口
 * 
 * @author YanCheng Liu
 * @date 2023-06-24
 */
public interface INxpSmartCabinetService 
{
    /**
     * 查询智能柜管理
     * 
     * @param id 智能柜管理ID
     * @return 智能柜管理
     */
    public NxpSmartCabinet selectNxpSmartCabinetById(Long id);

    /**
     * 查询智能柜管理列表
     * 
     * @param nxpSmartCabinet 智能柜管理
     * @return 智能柜管理集合
     */
    public List<NxpSmartCabinet> selectNxpSmartCabinetList(NxpSmartCabinet nxpSmartCabinet);

    /**
     * 新增智能柜管理
     * 
     * @param nxpSmartCabinet 智能柜管理
     * @return 结果
     */
    public int insertNxpSmartCabinet(NxpSmartCabinet nxpSmartCabinet);

    /**
     * 修改智能柜管理
     * 
     * @param nxpSmartCabinet 智能柜管理
     * @return 结果
     */
    public int updateNxpSmartCabinet(NxpSmartCabinet nxpSmartCabinet);

    /**
     * 批量删除智能柜管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpSmartCabinetByIds(String ids);

    /**
     * 删除智能柜管理信息
     * 
     * @param id 智能柜管理ID
     * @return 结果
     */
    public int deleteNxpSmartCabinetById(Long id);
}
