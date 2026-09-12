package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.DTO.NxpCheckGroupDTO;
import com.ruoyi.system.domain.NxpCheckGroup;

/**
 * 检查组管理Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2022-10-24
 */
public interface NxpCheckGroupMapper 
{
    /**
     * 查询检查组管理
     * 
     * @param id 检查组管理ID
     * @return 检查组管理
     */
    public NxpCheckGroup selectNxpCheckGroupById(Long id);

    /**
     * 查询检查组管理列表
     * 
     * @param nxpCheckGroup 检查组管理
     * @return 检查组管理集合
     */
    public List<NxpCheckGroup> selectNxpCheckGroupList(NxpCheckGroup nxpCheckGroup);

    /**
     * 新增检查组管理
     * 
     * @param nxpCheckGroup 检查组管理
     * @return 结果
     */
    public int insertNxpCheckGroup(NxpCheckGroup nxpCheckGroup);

    /**
     * 修改检查组管理
     * 
     * @param nxpCheckGroup 检查组管理
     * @return 结果
     */
    public int updateNxpCheckGroup(NxpCheckGroup nxpCheckGroup);

    /**
     * 删除检查组管理
     * 
     * @param id 检查组管理ID
     * @return 结果
     */
    public int deleteNxpCheckGroupById(Long id);

    /**
     * 批量删除检查组管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpCheckGroupByIds(String[] ids);

    /**
     * 根据工序id查询检查组
     * @param processId
     * @return
     */
    List<NxpCheckGroup> getByProcessId(Long processId);

    /**
     * 查询检查组带工序名称
     * @return
     */
    List<NxpCheckGroupDTO> selectListAndProcess();
}
