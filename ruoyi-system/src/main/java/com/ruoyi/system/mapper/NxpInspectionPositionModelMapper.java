package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionPositionModel;

/**
 * 维修位置&设备型号Mapper接口
 * 
 * @author DamonZhang
 * @date 2021-04-02
 */
public interface NxpInspectionPositionModelMapper 
{
    /**
     * 查询维修位置&设备型号
     * 
     * @param positionId 维修位置&设备型号ID
     * @return 维修位置&设备型号
     */
    public NxpInspectionPositionModel selectNxpInspectionPositionModelById(Long positionId);
    public List<NxpInspectionPositionModel> selectNxpInspectionPositionModelBypositionId(Long positionId);
    public List<NxpInspectionPositionModel> selectNxpInspectionPositionModelBymodelId(Long modelId);

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
     * 删除维修位置&设备型号
     * 
     * @param positionId 维修位置&设备型号ID
     * @return 结果
     */
    public int deleteNxpInspectionPositionModelById(Long positionId);
    public int deleteNxpInspectionPositionModelByPositionId(Long positionId);
    public int deleteNxpInspectionPositionModelByModelId(Long modelId);

    /**
     * 批量删除维修位置&设备型号
     * 
     * @param positionIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionPositionModelByIds(String[] positionIds);
}
