package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpExternalRelationMapper;
import com.ruoyi.system.domain.NxpExternalRelation;
import com.ruoyi.system.service.INxpExternalRelationService;
import com.ruoyi.common.core.text.Convert;

/**
 * 外部权限Service业务层处理
 * 
 * @author DamonZhang
 * @date 2021-04-25
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpExternalRelationServiceImpl implements INxpExternalRelationService 
{
    @Autowired
    private NxpExternalRelationMapper nxpExternalRelationMapper;

    /**
     * 查询外部权限
     * 
     * @param id 外部权限ID
     * @return 外部权限
     */
    @Override
    public NxpExternalRelation selectNxpExternalRelationById(Long id)
    {
        return nxpExternalRelationMapper.selectNxpExternalRelationById(id);
    }

    /**
     * 查询外部权限列表
     * 
     * @param nxpExternalRelation 外部权限
     * @return 外部权限
     */
    @Override
    public List<NxpExternalRelation> selectNxpExternalRelationList(NxpExternalRelation nxpExternalRelation)
    {
        return nxpExternalRelationMapper.selectNxpExternalRelationList(nxpExternalRelation);
    }

    /**
     * 新增外部权限
     * 
     * @param nxpExternalRelation 外部权限
     * @return 结果
     */
    @Override
    public int insertNxpExternalRelation(NxpExternalRelation nxpExternalRelation)
    {
        nxpExternalRelation.setCreateTime(DateUtils.getNowDate());
        return nxpExternalRelationMapper.insertNxpExternalRelation(nxpExternalRelation);
    }

    /**
     * 修改外部权限
     * 
     * @param nxpExternalRelation 外部权限
     * @return 结果
     */
    @Override
    public int updateNxpExternalRelation(NxpExternalRelation nxpExternalRelation)
    {
        nxpExternalRelation.setUpdateTime(DateUtils.getNowDate());
        return nxpExternalRelationMapper.updateNxpExternalRelation(nxpExternalRelation);
    }

    /**
     * 删除外部权限对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpExternalRelationByIds(String ids)
    {
        return nxpExternalRelationMapper.deleteNxpExternalRelationByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除外部权限信息
     * 
     * @param id 外部权限ID
     * @return 结果
     */
    @Override
    public int deleteNxpExternalRelationById(Long id)
    {
        return nxpExternalRelationMapper.deleteNxpExternalRelationById(id);
    }
}
