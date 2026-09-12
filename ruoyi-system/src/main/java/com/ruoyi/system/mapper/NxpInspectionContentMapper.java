package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionContent;

/**
 * 维修内容Mapper接口
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
public interface NxpInspectionContentMapper 
{
    /**
     * 查询维修内容
     * 
     * @param id 维修内容ID
     * @return 维修内容
     */
    public NxpInspectionContent selectNxpInspectionContentById(Long id);

    /**
     * 查询维修内容列表
     * 
     * @param nxpInspectionContent 维修内容
     * @return 维修内容集合
     */
    public List<NxpInspectionContent> selectNxpInspectionContentList(NxpInspectionContent nxpInspectionContent);

    /**
     * 新增维修内容
     * 
     * @param nxpInspectionContent 维修内容
     * @return 结果
     */
    public int insertNxpInspectionContent(NxpInspectionContent nxpInspectionContent);

    /**
     * 修改维修内容
     * 
     * @param nxpInspectionContent 维修内容
     * @return 结果
     */
    public int updateNxpInspectionContent(NxpInspectionContent nxpInspectionContent);

    /**
     * 删除维修内容
     * 
     * @param id 维修内容ID
     * @return 结果
     */
    public int deleteNxpInspectionContentById(Long id);

    /**
     * 批量删除维修内容
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionContentByIds(String[] ids);
}
