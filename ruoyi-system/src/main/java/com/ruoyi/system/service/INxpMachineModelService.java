package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpMachineModel;
import com.ruoyi.system.domain.NxpMold;
import com.ruoyi.system.domain.NxpMoldType;

/**
 * 设备型号Service接口
 * 
 * @author YanCheng Liu
 * @date 2021-02-21
 */
public interface INxpMachineModelService 
{
    /**
     * 查询设备型号
     * 
     * @param id 设备型号ID
     * @return 设备型号
     */
    public NxpMachineModel selectNxpMachineModelById(Long id);

    /**
     * 查询设备型号列表
     * 
     * @param nxpMachineModel 设备型号
     * @return 设备型号集合
     */
    public List<NxpMachineModel> selectNxpMachineModelList(NxpMachineModel nxpMachineModel);
    public List<NxpMachineModel> selectNxpMachineModelListMold(NxpMachineModel nxpMachineModel);
    public List<NxpMachineModel> selectNxpMachineModelListByPositionId(NxpMachineModel nxpMachineModel, Long id);
    public List<NxpMachineModel> selectNxpMachineModelByProcessId(Long processId);

    List<NxpMachineModel> selectNxpMachineModelListNew(NxpMachineModel nxpMachineModel);
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
     * 批量删除设备型号
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMachineModelByIds(String ids);

    /**
     * 删除设备型号信息
     * 
     * @param id 设备型号ID
     * @return 结果
     */
    public int deleteNxpMachineModelById(Long id);


    List<NxpMachineModel> selectNxpMachineModelListByPositionId(NxpMachineModel nxpMachineModel,String model_ids);

    /**
     * 根据部件种类中的MachineModel 查询
     * @param nxpMoldType
     * @return
     */
    List<NxpMachineModel> selectNxpMachineModelListByMoldType(NxpMoldType nxpMoldType);

    List<NxpMachineModel> selectNxpMachineModelListByMold(NxpMold nxpMold);

    List<NxpMachineModel> selectBatchIds(String[] ids);
}
