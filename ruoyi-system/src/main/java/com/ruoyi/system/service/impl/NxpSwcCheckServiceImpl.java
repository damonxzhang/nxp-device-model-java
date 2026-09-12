package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.NxpMachineProcess;
import com.ruoyi.system.domain.NxpSwcProgramGroup;
import com.ruoyi.system.domain.NxpUserInfo;
import com.ruoyi.system.mapper.NxpMachineProcessMapper;
import com.ruoyi.system.mapper.NxpSwcProgramGroupMapper;
import com.ruoyi.system.mapper.NxpUserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpSwcCheckMapper;
import com.ruoyi.system.domain.NxpSwcCheck;
import com.ruoyi.system.service.INxpSwcCheckService;
import com.ruoyi.common.core.text.Convert;

/**
 * SWC互查问题Service业务层处理
 * 
 * @author DamonZhang
 * @date 2021-03-18
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpSwcCheckServiceImpl implements INxpSwcCheckService 
{
    @Autowired
    private NxpSwcCheckMapper nxpSwcCheckMapper;
    @Autowired
    private NxpUserInfoMapper nxpUserInfoMapper;
    @Autowired
    private NxpMachineProcessMapper nxpMachineProcessMapper;
    @Autowired
    private NxpSwcProgramGroupMapper nxpSwcProgramGroupMapper;

    /**
     * 查询SWC互查问题
     * 
     * @param id SWC互查问题ID
     * @return SWC互查问题
     */
    @Override
    public NxpSwcCheck selectNxpSwcCheckById(Long id)
    {
        return nxpSwcCheckMapper.selectNxpSwcCheckById(id);
    }

    /**
     * 查询SWC互查问题列表
     * 
     * @param nxpSwcCheck SWC互查问题
     * @return SWC互查问题
     */
    @Override
    public List<NxpSwcCheck> selectNxpSwcCheckList(NxpSwcCheck nxpSwcCheck)
    {
        List<NxpSwcCheck> nxpSwcChecks = nxpSwcCheckMapper.selectNxpSwcCheckList(nxpSwcCheck);
        for (NxpSwcCheck swcCheck : nxpSwcChecks) {
            // 被检查人
            Long checkedUserId = swcCheck.getCheckedUserId();
            NxpUserInfo checkUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(checkedUserId);
            if (checkUserInfo != null){
                swcCheck.setCheckedUserName(checkUserInfo.getChinese_name());
            }
            // 查询工序
            Long processId = swcCheck.getProcessId();
            NxpMachineProcess machineProcess = nxpMachineProcessMapper.selectNxpMachineProcessById(processId);
            if (machineProcess != null){
                swcCheck.setProcessName(machineProcess.getName());
            }
            // 检查组
            Long programGroupId = swcCheck.getProgramGroupId();
            NxpSwcProgramGroup nxpSwcProgramGroup = nxpSwcProgramGroupMapper.selectNxpSwcProgramGroupById(programGroupId);
            if (nxpSwcProgramGroup != null){
                swcCheck.setProgramGroupName(nxpSwcProgramGroup.getGroupName());
            }
        }
        return nxpSwcChecks;
    }

    /**
     * 新增SWC互查问题
     * 
     * @param nxpSwcCheck SWC互查问题
     * @return 结果
     */
    @Override
    public int insertNxpSwcCheck(NxpSwcCheck nxpSwcCheck)
    {
        return nxpSwcCheckMapper.insertNxpSwcCheck(nxpSwcCheck);
    }

    /**
     * 修改SWC互查问题
     * 
     * @param nxpSwcCheck SWC互查问题
     * @return 结果
     */
    @Override
    public int updateNxpSwcCheck(NxpSwcCheck nxpSwcCheck)
    {
        return nxpSwcCheckMapper.updateNxpSwcCheck(nxpSwcCheck);
    }

    /**
     * 删除SWC互查问题对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpSwcCheckByIds(String ids)
    {
        return nxpSwcCheckMapper.deleteNxpSwcCheckByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除SWC互查问题信息
     * 
     * @param id SWC互查问题ID
     * @return 结果
     */
    @Override
    public int deleteNxpSwcCheckById(Long id)
    {
        return nxpSwcCheckMapper.deleteNxpSwcCheckById(id);
    }
}
