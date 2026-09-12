package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionPositionModel;

/**
 * 维修位置&设备型号Service接口
 * 
 * @author DamonZhang
 * @date 2021-04-02
 */
public interface INxpInspectionPositionModelService 
{
    /**
     * 查询维修位置&设备型号
     * 
     * @param positionId 维修位置&设备型号ID
     * @return 维修位置&设备型号
     */
    public NxpInspectionPositionModel selectNxpInspectionPositionModelById(Long positionId);

    /**
     * 查询维修位置&设备型号列表
     * 
     * @param nxpInspectionPositionModel 维修位置&设备型号
     * @return 维修位置&设备型号集合
     */
    public List<NxpInspectionPositionModel> selectNxpInspectionPositionModelList(NxpInspectionPositionModel nxpInspectionPositionModel);
    public List<NxpInspectionPositionModel> selectNxpInspectionPositionModelListByModelId(NxpInspectionPositionModel nxpInspectionPositionModel);

    /**
     * 新增维修位置&设备型号
     * 
     * @param nxpInspectionPositionModel 维修位置&设备型号
     * @return 结果
     */
    public int insertNxpInspectionPositionModel(NxpInspectionPositionModel nxpInspectionPositionModel);

    /**
     * 修改维修位置&设备型号
     * 
     * @param nxpInspectionPositionModel 维修位置&设备型号
     * @return 结果
     */
    public int updateNxpInspectionPositionModel(NxpInspectionPositionModel nxpInspectionPositionModel);

    /**
     * 批量删除维修位置&设备型号
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionPositionModelByIds(String ids);

    /**
     * 删除维修位置&设备型号信息
     * 
     * @param positionId 维修位置&设备型号ID
     * @return 结果
     */
    public int deleteNxpInspectionPositionModelById(Long positionId);
}
