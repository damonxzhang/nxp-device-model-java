package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.DTO.NxpCheckGroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpCheckGroupMapper;
import com.ruoyi.system.domain.NxpCheckGroup;
import com.ruoyi.system.service.INxpCheckGroupService;
import com.ruoyi.common.core.text.Convert;

/**
 * 检查组管理Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2022-10-24
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpCheckGroupServiceImpl implements INxpCheckGroupService 
{
    @Autowired
    private NxpCheckGroupMapper nxpCheckGroupMapper;

    /**
     * 查询检查组管理
     * 
     * @param id 检查组管理ID
     * @return 检查组管理
     */
    @Override
    public NxpCheckGroup selectNxpCheckGroupById(Long id)
    {
        return nxpCheckGroupMapper.selectNxpCheckGroupById(id);
    }

    /**
     * 查询检查组管理列表
     * 
     * @param nxpCheckGroup 检查组管理
     * @return 检查组管理
     */
    @Override
    public List<NxpCheckGroup> selectNxpCheckGroupList(NxpCheckGroup nxpCheckGroup)
    {
        return nxpCheckGroupMapper.selectNxpCheckGroupList(nxpCheckGroup);
    }

    /**
     * 新增检查组管理
     * 
     * @param nxpCheckGroup 检查组管理
     * @return 结果
     */
    @Override
    public int insertNxpCheckGroup(NxpCheckGroup nxpCheckGroup)
    {
        nxpCheckGroup.setCreateTime(DateUtils.getNowDate());
        return nxpCheckGroupMapper.insertNxpCheckGroup(nxpCheckGroup);
    }

    /**
     * 修改检查组管理
     * 
     * @param nxpCheckGroup 检查组管理
     * @return 结果
     */
    @Override
    public int updateNxpCheckGroup(NxpCheckGroup nxpCheckGroup)
    {
        nxpCheckGroup.setUpdateTime(DateUtils.getNowDate());
        return nxpCheckGroupMapper.updateNxpCheckGroup(nxpCheckGroup);
    }

    /**
     * 删除检查组管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpCheckGroupByIds(String ids)
    {
        return nxpCheckGroupMapper.deleteNxpCheckGroupByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除检查组管理信息
     * 
     * @param id 检查组管理ID
     * @return 结果
     */
    @Override
    public int deleteNxpCheckGroupById(Long id)
    {
        return nxpCheckGroupMapper.deleteNxpCheckGroupById(id);
    }

    /**
     * 根据工序id查询检查组
     * @param processId
     * @return
     */
    @Override
    public AjaxResult getByProcessId(Long processId) {
        return AjaxResult.success(nxpCheckGroupMapper.getByProcessId(processId));
    }

    /**
     * 查询检查组带工序名称
     * @return
     */
    @Override
    public List<NxpCheckGroupDTO> selectListAndProcess() {
        return nxpCheckGroupMapper.selectListAndProcess();
    }
}
