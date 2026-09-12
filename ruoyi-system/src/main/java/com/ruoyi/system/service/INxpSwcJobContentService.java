package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpSwcJobContent;

/**
 * 作业内容管理Service接口
 * 
 * @author YanCheng Liu
 * @date 2022-09-01
 */
public interface INxpSwcJobContentService 
{
    /**
     * 查询作业内容管理
     * 
     * @param id 作业内容管理ID
     * @return 作业内容管理
     */
    public NxpSwcJobContent selectNxpSwcJobContentById(Long id);

    /**
     * 查询作业内容管理列表
     * 
     * @param nxpSwcJobContent 作业内容管理
     * @return 作业内容管理集合
     */
    public List<NxpSwcJobContent> selectNxpSwcJobContentList(NxpSwcJobContent nxpSwcJobContent);

    /**
     * 新增作业内容管理
     * 
     * @param nxpSwcJobContent 作业内容管理
     * @return 结果
     */
    public int insertNxpSwcJobContent(NxpSwcJobContent nxpSwcJobContent);

    /**
     * 修改作业内容管理
     * 
     * @param nxpSwcJobContent 作业内容管理
     * @return 结果
     */
    public int updateNxpSwcJobContent(NxpSwcJobContent nxpSwcJobContent);

    /**
     * 批量删除作业内容管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpSwcJobContentByIds(String ids);

    /**
     * 删除作业内容管理信息
     * 
     * @param id 作业内容管理ID
     * @return 结果
     */
    public int deleteNxpSwcJobContentById(Long id);
}
