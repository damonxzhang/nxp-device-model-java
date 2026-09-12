package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.NxpUserInfo;
import com.ruoyi.system.mapper.NxpUserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpMachineProcessMapper;
import com.ruoyi.system.domain.NxpMachineProcess;
import com.ruoyi.system.service.INxpMachineProcessService;
import com.ruoyi.common.core.text.Convert;

/**
 * 工序Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2021-02-08
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpMachineProcessServiceImpl implements INxpMachineProcessService 
{
    @Autowired
    private NxpMachineProcessMapper nxpMachineProcessMapper;
    @Autowired
    private NxpUserInfoMapper nxpUserInfoMapper;

    /**
     * 查询工序
     * 
     * @param id 工序ID
     * @return 工序
     */
    @Override
    public NxpMachineProcess selectNxpMachineProcessById(Long id)
    {
        return nxpMachineProcessMapper.selectNxpMachineProcessById(id);
    }

    /**
     * 查询工序列表
     * 
     * @param nxpMachineProcess 工序
     * @return 工序
     */
    @Override
    public List<NxpMachineProcess> selectNxpMachineProcessList(NxpMachineProcess nxpMachineProcess)
    {
        nxpMachineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessMapper.selectNxpMachineProcessList(nxpMachineProcess);
        for (NxpMachineProcess machineProcess : nxpMachineProcesses) {
            Long owner = machineProcess.getOwner();
            NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(owner);
            if (nxpUserInfo == null)
                continue;
            machineProcess.setOwnerName(nxpUserInfo.getChinese_name());
        }
        return nxpMachineProcesses;
    }

    /**
     * 新增工序
     * 
     * @param nxpMachineProcess 工序
     * @return 结果
     */
    @Override
    public int insertNxpMachineProcess(NxpMachineProcess nxpMachineProcess)
    {
        nxpMachineProcess.setCreate_dt(new Date());
        return nxpMachineProcessMapper.insertNxpMachineProcess(nxpMachineProcess);
    }

    /**
     * 修改工序
     * 
     * @param nxpMachineProcess 工序
     * @return 结果
     */
    @Override
    public int updateNxpMachineProcess(NxpMachineProcess nxpMachineProcess)
    {
        nxpMachineProcess.setUpdate_dt(new Date());
        return nxpMachineProcessMapper.updateNxpMachineProcess(nxpMachineProcess);
    }

    /**
     * 删除工序对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpMachineProcessByIds(String ids)
    {
        return nxpMachineProcessMapper.deleteNxpMachineProcessByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除工序信息
     * 
     * @param id 工序ID
     * @return 结果
     */
    @Override
    public int deleteNxpMachineProcessById(Long id)
    {
        return nxpMachineProcessMapper.deleteNxpMachineProcessById(id);
    }
}
