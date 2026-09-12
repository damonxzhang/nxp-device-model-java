package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionServiceDetail;

/**
 * 维修详情备件Mapper接口
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
public interface NxpInspectionServiceDetailMapper 
{
    /**
     * 查询维修详情备件
     * 
     * @param id 维修详情备件ID
     * @return 维修详情备件
     */
    public NxpInspectionServiceDetail selectNxpInspectionServiceDetailById(Long id);

    /**
     * 查询维修详情备件列表
     * 
     * @param nxpInspectionServiceDetail 维修详情备件
     * @return 维修详情备件集合
     */
    public List<NxpInspectionServiceDetail> selectNxpInspectionServiceDetailList(NxpInspectionServiceDetail nxpInspectionServiceDetail);

    /**
     * 新增维修详情备件
     * 
     * @param nxpInspectionServiceDetail 维修详情备件
     * @return 结果
     */
    public int insertNxpInspectionServiceDetail(NxpInspectionServiceDetail nxpInspectionServiceDetail);

    /**
     * 修改维修详情备件
     * 
     * @param nxpInspectionServiceDetail 维修详情备件
     * @return 结果
     */
    public int updateNxpInspectionServiceDetail(NxpInspectionServiceDetail nxpInspectionServiceDetail);

    /**
     * 删除维修详情备件
     * 
     * @param id 维修详情备件ID
     * @return 结果
     */
    public int deleteNxpInspectionServiceDetailById(Long id);

    /**
     * 批量删除维修详情备件
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionServiceDetailByIds(String[] ids);
}
