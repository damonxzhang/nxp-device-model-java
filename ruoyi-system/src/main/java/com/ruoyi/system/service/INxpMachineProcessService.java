package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpMachineProcess;

/**
 * 工序Service接口
 * 
 * @author YanCheng Liu
 * @date 2021-02-08
 */
public interface INxpMachineProcessService 
{
    /**
     * 查询工序
     * 
     * @param id 工序ID
     * @return 工序
     */
    public NxpMachineProcess selectNxpMachineProcessById(Long id);

    /**
     * 查询工序列表
     * 
     * @param nxpMachineProcess 工序
     * @return 工序集合
     */
    public List<NxpMachineProcess> selectNxpMachineProcessList(NxpMachineProcess nxpMachineProcess);

    /**
     * 新增工序
     * 
     * @param nxpMachineProcess 工序
     * @return 结果
     */
    public int insertNxpMachineProcess(NxpMachineProcess nxpMachineProcess);

    /**
     * 修改工序
     * 
     * @param nxpMachineProcess 工序
     * @return 结果
     */
    public int updateNxpMachineProcess(NxpMachineProcess nxpMachineProcess);

    /**
     * 批量删除工序
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMachineProcessByIds(String ids);

    /**
     * 删除工序信息
     * 
     * @param id 工序ID
     * @return 结果
     */
    public int deleteNxpMachineProcessById(Long id);
}
