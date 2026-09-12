package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionPmGroup;

/**
 * 周PM分组Service接口
 * 
 * @author ruoyi
 * @date 2021-02-21
 */
public interface INxpInspectionPmGroupService 
{
    /**
     * 查询周PM分组
     * 
     * @param id 周PM分组ID
     * @return 周PM分组
     */
    public NxpInspectionPmGroup selectNxpInspectionPmGroupById(Long id);

    /**
     * 查询周PM分组列表
     * 
     * @param nxpInspectionPmGroup 周PM分组
     * @return 周PM分组集合
     */
    public List<NxpInspectionPmGroup> selectNxpInspectionPmGroupList(NxpInspectionPmGroup nxpInspectionPmGroup);

    /**
     * 新增周PM分组
     * 
     * @param nxpInspectionPmGroup 周PM分组
     * @return 结果
     */
    public int insertNxpInspectionPmGroup(NxpInspectionPmGroup nxpInspectionPmGroup);

    /**
     * 修改周PM分组
     * 
     * @param nxpInspectionPmGroup 周PM分组
     * @return 结果
     */
    public int updateNxpInspectionPmGroup(NxpInspectionPmGroup nxpInspectionPmGroup);

    /**
     * 批量删除周PM分组
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionPmGroupByIds(String ids);

    /**
     * 删除周PM分组信息
     * 
     * @param id 周PM分组ID
     * @return 结果
     */
    public int deleteNxpInspectionPmGroupById(Long id);
}
