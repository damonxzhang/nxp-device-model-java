package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpMachineModel;

/**
 * 设备型号Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2021-02-21
 */
public interface NxpMachineModelMapper 
{
    /**
     * 查询设备型号
     * 
     * @param id 设备型号ID
     * @return 设备型号
     */
    public NxpMachineModel selectNxpMachineModelById(Long id);

    public NxpMachineModel selectNxpMachineModelByName(String name);

    /**
     * 查询设备型号列表
     * 
     * @param nxpMachineModel 设备型号
     * @return 设备型号集合
     */
    public List<NxpMachineModel> selectNxpMachineModelList(NxpMachineModel nxpMachineModel);
    public List<NxpMachineModel> selectNxpMachineModelByProcessId(Long processId);

    /**
     * 新增设备型号
     * 
     * @param nxpMachineModel 设备型号
     * @return 结果
     */
    public int insertNxpMachineModel(NxpMachineModel nxpMachineModel);

    /**
     * 修改设备型号
     * 
     * @param nxpMachineModel 设备型号
     * @return 结果
     */
    public int updateNxpMachineModel(NxpMachineModel nxpMachineModel);

    /**
     * 删除设备型号
     * 
     * @param id 设备型号ID
     * @return 结果
     */
    public int deleteNxpMachineModelById(Long id);

    /**
     * 批量删除设备型号
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMachineModelByIds(String[] ids);

    List<NxpMachineModel> selectBatchIds(String[] ids);
}
