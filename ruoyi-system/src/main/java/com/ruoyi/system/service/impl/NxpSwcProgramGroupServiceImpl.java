package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.NxpMachineProcess;
import com.ruoyi.system.domain.VO.NxpSwcProgramGroupVO;
import com.ruoyi.system.mapper.NxpMachineProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpSwcProgramGroupMapper;
import com.ruoyi.system.domain.NxpSwcProgramGroup;
import com.ruoyi.system.service.INxpSwcProgramGroupService;
import com.ruoyi.common.core.text.Convert;

/**
 * 检查组管理Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2021-03-18
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpSwcProgramGroupServiceImpl implements INxpSwcProgramGroupService 
{
    @Autowired
    private NxpSwcProgramGroupMapper nxpSwcProgramGroupMapper;
    @Autowired
    private NxpMachineProcessMapper nxpMachineProcessMapper;

    /**
     * 查询检查组管理
     * 
     * @param id 检查组管理ID
     * @return 检查组管理
     */
    @Override
    public NxpSwcProgramGroup selectNxpSwcProgramGroupById(Long id)
    {
        return nxpSwcProgramGroupMapper.selectNxpSwcProgramGroupById(id);
    }

    /**
     * 查询检查组管理列表
     * 
     * @param nxpSwcProgramGroup 检查组管理
     * @return 检查组管理
     */
    @Override
    public List<NxpSwcProgramGroup> selectNxpSwcProgramGroupList(NxpSwcProgramGroup nxpSwcProgramGroup)
    {
        List<NxpSwcProgramGroup> nxpSwcProgramGroups = nxpSwcProgramGroupMapper.selectNxpSwcProgramGroupList(nxpSwcProgramGroup);
        for (NxpSwcProgramGroup swcProgramGroup : nxpSwcProgramGroups) {
            Long processId = swcProgramGroup.getProcessId();
            NxpMachineProcess nxpMachineProcess = nxpMachineProcessMapper.selectNxpMachineProcessById(processId);
            if (nxpMachineProcess != null){
                swcProgramGroup.setProcessName(nxpMachineProcess.getName());
            }
        }
        return nxpSwcProgramGroups;
    }

    /**
     * 新增检查组管理
     * 
     * @param nxpSwcProgramGroup 检查组管理
     * @return 结果
     */
    @Override
    public int insertNxpSwcProgramGroup(NxpSwcProgramGroup nxpSwcProgramGroup)
    {
        return nxpSwcProgramGroupMapper.insertNxpSwcProgramGroup(nxpSwcProgramGroup);
    }

    /**
     * 修改检查组管理
     * 
     * @param nxpSwcProgramGroup 检查组管理
     * @return 结果
     */
    @Override
    public int updateNxpSwcProgramGroup(NxpSwcProgramGroup nxpSwcProgramGroup)
    {
        return nxpSwcProgramGroupMapper.updateNxpSwcProgramGroup(nxpSwcProgramGroup);
    }

    /**
     * 删除检查组管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpSwcProgramGroupByIds(String ids)
    {
        return nxpSwcProgramGroupMapper.deleteNxpSwcProgramGroupByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除检查组管理信息
     * 
     * @param id 检查组管理ID
     * @return 结果
     */
    @Override
    public int deleteNxpSwcProgramGroupById(Long id)
    {
        return nxpSwcProgramGroupMapper.deleteNxpSwcProgramGroupById(id);
    }

    /**
     * 根据groupId连表查询其他三张表
     * @param nxpSwcProgramGroup
     * @return
     */
    @Override
    public List<NxpSwcProgramGroupVO> selectThreeMenu(NxpSwcProgramGroup nxpSwcProgramGroup) {
        return nxpSwcProgramGroupMapper.selectThreeMenu(nxpSwcProgramGroup);
    }
}
