package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionService;

/**
 * 维修详情Mapper接口
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
public interface NxpInspectionServiceMapper 
{
    /**
     * 查询维修详情
     * 
     * @param id 维修详情ID
     * @return 维修详情
     */
    public NxpInspectionService selectNxpInspectionServiceById(Long id);

    public List<NxpInspectionService> selectNxpInspectionServiceByIds(String[] ids);

    /**
     * 查询维修详情列表
     * 
     * @param nxpInspectionService 维修详情
     * @return 维修详情集合
     */
    public List<NxpInspectionService> selectNxpInspectionServiceList(NxpInspectionService nxpInspectionService);
    public List<NxpInspectionService> selectNxpInspectionServiceListNew(NxpInspectionService nxpInspectionService);

    /**
     * 新增维修详情
     * 
     * @param nxpInspectionService 维修详情
     * @return 结果
     */
    public int insertNxpInspectionService(NxpInspectionService nxpInspectionService);

    /**
     * 修改维修详情
     * 
     * @param nxpInspectionService 维修详情
     * @return 结果
     */
    public int updateNxpInspectionService(NxpInspectionService nxpInspectionService);

    /**
     * 删除维修详情
     * 
     * @param id 维修详情ID
     * @return 结果
     */
    public int deleteNxpInspectionServiceById(Long id);

    /**
     * 批量删除维修详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionServiceByIds(String[] ids);

    /**
     * 根据报修id和设备id批量查询
     * @param nxpInspectionService
     * @return
     */
    List<NxpInspectionService> selectNxpInspectionServiceListByIids(NxpInspectionService nxpInspectionService);
}
