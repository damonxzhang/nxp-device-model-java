package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionPmGroupItem;

/**
 * 周PM检查项Mapper接口
 * 
 * @author ruoyi
 * @date 2021-02-08
 */
public interface NxpInspectionPmGroupItemMapper 
{
    /**
     * 查询周PM检查项
     * 
     * @param id 周PM检查项ID
     * @return 周PM检查项
     */
    public NxpInspectionPmGroupItem selectNxpInspectionPmGroupItemById(Long id);

    /**
     * 查询周PM检查项列表
     * 
     * @param nxpInspectionPmGroupItem 周PM检查项
     * @return 周PM检查项集合
     */
    public List<NxpInspectionPmGroupItem> selectNxpInspectionPmGroupItemList(NxpInspectionPmGroupItem nxpInspectionPmGroupItem);

    /**
     * 新增周PM检查项
     * 
     * @param nxpInspectionPmGroupItem 周PM检查项
     * @return 结果
     */
    public int insertNxpInspectionPmGroupItem(NxpInspectionPmGroupItem nxpInspectionPmGroupItem);

    /**
     * 修改周PM检查项
     * 
     * @param nxpInspectionPmGroupItem 周PM检查项
     * @return 结果
     */
    public int updateNxpInspectionPmGroupItem(NxpInspectionPmGroupItem nxpInspectionPmGroupItem);

    /**
     * 删除周PM检查项
     * 
     * @param id 周PM检查项ID
     * @return 结果
     */
    public int deleteNxpInspectionPmGroupItemById(Long id);

    /**
     * 批量删除周PM检查项
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionPmGroupItemByIds(String[] ids);
}
