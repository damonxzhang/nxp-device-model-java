package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpCheckContent;

/**
 * 检查内容Service接口
 * 
 * @author YanCheng Liu
 * @date 2022-09-02
 */
public interface INxpCheckContentService 
{
    /**
     * 查询检查内容
     * 
     * @param id 检查内容ID
     * @return 检查内容
     */
    public NxpCheckContent selectNxpCheckContentById(Long id);

    /**
     * 查询检查内容列表
     * 
     * @param nxpCheckContent 检查内容
     * @return 检查内容集合
     */
    public List<NxpCheckContent> selectNxpCheckContentList(NxpCheckContent nxpCheckContent);

    /**
     * 新增检查内容
     * 
     * @param nxpCheckContent 检查内容
     * @return 结果
     */
    public int insertNxpCheckContent(NxpCheckContent nxpCheckContent);

    /**
     * 修改检查内容
     * 
     * @param nxpCheckContent 检查内容
     * @return 结果
     */
    public int updateNxpCheckContent(NxpCheckContent nxpCheckContent);

    /**
     * 批量删除检查内容
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpCheckContentByIds(String ids);

    /**
     * 删除检查内容信息
     * 
     * @param id 检查内容ID
     * @return 结果
     */
    public int deleteNxpCheckContentById(Long id);

    /**
     * 根据检查组查询检查项
     * @param checkGroupId
     * @return
     */
    List<NxpCheckContent> selectNxpCheckContentByCheckGroupId(Long checkGroupId);
}
