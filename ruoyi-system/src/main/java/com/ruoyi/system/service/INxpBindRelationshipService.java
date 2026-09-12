package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpBindRelationship;

/**
 * 绑定关系管理Service接口
 * 
 * @author YanCheng Liu
 * @date 2022-09-02
 */
public interface INxpBindRelationshipService 
{
    /**
     * 查询绑定关系管理
     * 
     * @param checkContentId 绑定关系管理ID
     * @return 绑定关系管理
     */
    public NxpBindRelationship selectNxpBindRelationshipById(Long checkContentId);

    /**
     * 查询绑定关系管理列表
     * 
     * @param nxpBindRelationship 绑定关系管理
     * @return 绑定关系管理集合
     */
    public List<NxpBindRelationship> selectNxpBindRelationshipList(NxpBindRelationship nxpBindRelationship);

    /**
     * 新增绑定关系管理
     * 
     * @param nxpBindRelationship 绑定关系管理
     * @return 结果
     */
    public int insertNxpBindRelationship(NxpBindRelationship nxpBindRelationship);

    /**
     * 修改绑定关系管理
     * 
     * @param nxpBindRelationship 绑定关系管理
     * @return 结果
     */
    public int updateNxpBindRelationship(NxpBindRelationship nxpBindRelationship);

    /**
     * 批量删除绑定关系管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpBindRelationshipByIds(String ids);

    /**
     * 删除绑定关系管理信息
     * 
     * @param checkContentId 绑定关系管理ID
     * @return 结果
     */
    public int deleteNxpBindRelationshipById(Long checkContentId);
}
