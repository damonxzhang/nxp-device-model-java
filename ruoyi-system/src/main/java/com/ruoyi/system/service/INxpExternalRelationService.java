package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpExternalRelation;

/**
 * 外部权限Service接口
 * 
 * @author DamonZhang
 * @date 2021-04-25
 */
public interface INxpExternalRelationService 
{
    /**
     * 查询外部权限
     * 
     * @param id 外部权限ID
     * @return 外部权限
     */
    public NxpExternalRelation selectNxpExternalRelationById(Long id);

    /**
     * 查询外部权限列表
     * 
     * @param nxpExternalRelation 外部权限
     * @return 外部权限集合
     */
    public List<NxpExternalRelation> selectNxpExternalRelationList(NxpExternalRelation nxpExternalRelation);

    /**
     * 新增外部权限
     * 
     * @param nxpExternalRelation 外部权限
     * @return 结果
     */
    public int insertNxpExternalRelation(NxpExternalRelation nxpExternalRelation);

    /**
     * 修改外部权限
     * 
     * @param nxpExternalRelation 外部权限
     * @return 结果
     */
    public int updateNxpExternalRelation(NxpExternalRelation nxpExternalRelation);

    /**
     * 批量删除外部权限
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpExternalRelationByIds(String ids);

    /**
     * 删除外部权限信息
     * 
     * @param id 外部权限ID
     * @return 结果
     */
    public int deleteNxpExternalRelationById(Long id);
}
