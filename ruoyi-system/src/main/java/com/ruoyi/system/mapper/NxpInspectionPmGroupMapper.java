package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionPmGroup;
import com.ruoyi.system.domain.NxpInspectionPmGroupItem;

/**
 * 周PM分组Mapper接口
 * 
 * @author ruoyi
 * @date 2021-02-21
 */
public interface NxpInspectionPmGroupMapper 
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
    public List<NxpInspectionPmGroup> selectNxpInspectionPmGroupListById(Long id);

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
     * 删除周PM分组
     * 
     * @param id 周PM分组ID
     * @return 结果
     */
    public int deleteNxpInspectionPmGroupById(Long id);

    /**
     * 批量删除周PM分组
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionPmGroupByIds(String[] ids);

    /**
     * 批量删除周PM检查项
     * 
     * @param customerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionPmGroupItemByGroupIds(String[] ids);
    
    /**
     * 批量新增周PM检查项
     * 
     * @param nxpInspectionPmGroupItemList 周PM检查项列表
     * @return 结果
     */
    public int batchNxpInspectionPmGroupItem(List<NxpInspectionPmGroupItem> nxpInspectionPmGroupItemList);
    

    /**
     * 通过周PM分组ID删除周PM检查项信息
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteNxpInspectionPmGroupItemByGroupId(Long id);
}
