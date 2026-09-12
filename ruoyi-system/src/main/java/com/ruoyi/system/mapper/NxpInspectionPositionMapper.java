package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionPosition;
import com.ruoyi.system.domain.NxpMachineModel;

/**
 * 维修位置Mapper接口
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
public interface NxpInspectionPositionMapper 
{
    /**
     * 查询维修位置
     * 
     * @param id 维修位置ID
     * @return 维修位置
     */
    public NxpInspectionPosition selectNxpInspectionPositionById(Long id);
    public NxpInspectionPosition selectNxpInspectionPositionByPosition(String position);

    /**
     * 查询维修位置列表
     * 
     * @param nxpInspectionPosition 维修位置
     * @return 维修位置集合
     */
    public List<NxpInspectionPosition> selectNxpInspectionPositionList(NxpInspectionPosition nxpInspectionPosition);
    public List<NxpInspectionPosition> selectCheckNxpInspectionPositionList(NxpInspectionPosition nxpInspectionPosition);
    public List<NxpInspectionPosition> selectNxpInspectionPositionByProcessId(Long processId);

    /**
     * 新增维修位置
     * 
     * @param nxpInspectionPosition 维修位置
     * @return 结果
     */
    public int insertNxpInspectionPosition(NxpInspectionPosition nxpInspectionPosition);

    /**
     * 修改维修位置
     * 
     * @param nxpInspectionPosition 维修位置
     * @return 结果
     */
    public int updateNxpInspectionPosition(NxpInspectionPosition nxpInspectionPosition);

    /**
     * 删除维修位置
     * 
     * @param id 维修位置ID
     * @return 结果
     */
    public int deleteNxpInspectionPositionById(Long id);

    /**
     * 批量删除维修位置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionPositionByIds(String[] ids);


    List<NxpInspectionPosition> selectBatchIds(String[] ids);
}
