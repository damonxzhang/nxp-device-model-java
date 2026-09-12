package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.DTO.NxpCheckGroupDTO;
import com.ruoyi.system.domain.NxpCheckGroup;

/**
 * 检查组管理Service接口
 * 
 * @author YanCheng Liu
 * @date 2022-10-24
 */
public interface INxpCheckGroupService 
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
     * 批量删除检查组管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpCheckGroupByIds(String ids);

    /**
     * 删除检查组管理信息
     * 
     * @param id 检查组管理ID
     * @return 结果
     */
    public int deleteNxpCheckGroupById(Long id);

    /**
     * 根据工序id查询检查组
     * @param processId
     * @return
     */
    AjaxResult getByProcessId(Long processId);

    /**
     * 查询检查组带工序名称
     * @return
     */
    List<NxpCheckGroupDTO> selectListAndProcess();
}
