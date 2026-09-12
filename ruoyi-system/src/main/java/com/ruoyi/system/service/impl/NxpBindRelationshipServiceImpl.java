package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpBindRelationshipMapper;
import com.ruoyi.system.domain.NxpBindRelationship;
import com.ruoyi.system.service.INxpBindRelationshipService;
import com.ruoyi.common.core.text.Convert;

/**
 * 绑定关系管理Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2022-09-02
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpBindRelationshipServiceImpl implements INxpBindRelationshipService 
{
    @Autowired
    private NxpBindRelationshipMapper nxpBindRelationshipMapper;

    /**
     * 查询绑定关系管理
     * 
     * @param checkContentId 绑定关系管理ID
     * @return 绑定关系管理
     */
    @Override
    public NxpBindRelationship selectNxpBindRelationshipById(Long checkContentId)
    {
        return nxpBindRelationshipMapper.selectNxpBindRelationshipById(checkContentId);
    }

    /**
     * 查询绑定关系管理列表
     * 
     * @param nxpBindRelationship 绑定关系管理
     * @return 绑定关系管理
     */
    @Override
    public List<NxpBindRelationship> selectNxpBindRelationshipList(NxpBindRelationship nxpBindRelationship)
    {
        return nxpBindRelationshipMapper.selectNxpBindRelationshipList(nxpBindRelationship);
    }

    /**
     * 新增绑定关系管理
     * 
     * @param nxpBindRelationship 绑定关系管理
     * @return 结果
     */
    @Override
    public int insertNxpBindRelationship(NxpBindRelationship nxpBindRelationship)
    {
        nxpBindRelationship.setCreateTime(DateUtils.getNowDate());
        return nxpBindRelationshipMapper.insertNxpBindRelationship(nxpBindRelationship);
    }

    /**
     * 修改绑定关系管理
     * 
     * @param nxpBindRelationship 绑定关系管理
     * @return 结果
     */
    @Override
    public int updateNxpBindRelationship(NxpBindRelationship nxpBindRelationship)
    {
        nxpBindRelationship.setUpdateTime(DateUtils.getNowDate());
        return nxpBindRelationshipMapper.updateNxpBindRelationship(nxpBindRelationship);
    }

    /**
     * 删除绑定关系管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpBindRelationshipByIds(String ids)
    {
        return nxpBindRelationshipMapper.deleteNxpBindRelationshipByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除绑定关系管理信息
     * 
     * @param checkContentId 绑定关系管理ID
     * @return 结果
     */
    @Override
    public int deleteNxpBindRelationshipById(Long checkContentId)
    {
        return nxpBindRelationshipMapper.deleteNxpBindRelationshipById(checkContentId);
    }
}
