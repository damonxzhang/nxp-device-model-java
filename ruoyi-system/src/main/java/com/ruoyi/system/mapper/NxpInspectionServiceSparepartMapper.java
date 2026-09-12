package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionServiceSparepart;

/**
 * 维修设备备件Mapper接口
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
public interface NxpInspectionServiceSparepartMapper 
{
    /**
     * 查询维修设备备件
     * 
     * @param id 维修设备备件ID
     * @return 维修设备备件
     */
    public NxpInspectionServiceSparepart selectNxpInspectionServiceSparepartById(Long id);

    /**
     * 查询维修设备备件列表
     * 
     * @param nxpInspectionServiceSparepart 维修设备备件
     * @return 维修设备备件集合
     */
    public List<NxpInspectionServiceSparepart> selectNxpInspectionServiceSparepartList(NxpInspectionServiceSparepart nxpInspectionServiceSparepart);

    /**
     * 新增维修设备备件
     * 
     * @param nxpInspectionServiceSparepart 维修设备备件
     * @return 结果
     */
    public int insertNxpInspectionServiceSparepart(NxpInspectionServiceSparepart nxpInspectionServiceSparepart);

    /**
     * 修改维修设备备件
     * 
     * @param nxpInspectionServiceSparepart 维修设备备件
     * @return 结果
     */
    public int updateNxpInspectionServiceSparepart(NxpInspectionServiceSparepart nxpInspectionServiceSparepart);

    /**
     * 删除维修设备备件
     * 
     * @param id 维修设备备件ID
     * @return 结果
     */
    public int deleteNxpInspectionServiceSparepartById(Long id);

    /**
     * 批量删除维修设备备件
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionServiceSparepartByIds(String[] ids);
}
