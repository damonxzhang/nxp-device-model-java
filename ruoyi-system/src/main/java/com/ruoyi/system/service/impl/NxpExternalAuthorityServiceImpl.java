package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpExternalAuthorityMapper;
import com.ruoyi.system.domain.NxpExternalAuthority;
import com.ruoyi.system.service.INxpExternalAuthorityService;
import com.ruoyi.common.core.text.Convert;

/**
 * 外部关联Service业务层处理
 * 
 * @author DamonZhang
 * @date 2021-04-25
 */

@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpExternalAuthorityServiceImpl implements INxpExternalAuthorityService 
{
    @Autowired
    private NxpExternalAuthorityMapper nxpExternalAuthorityMapper;

    /**
     * 查询外部关联
     * 
     * @param id 外部关联ID
     * @return 外部关联
     */
    @Override
    public NxpExternalAuthority selectNxpExternalAuthorityById(Long id)
    {
        return nxpExternalAuthorityMapper.selectNxpExternalAuthorityById(id);
    }

    /**
     * 查询外部关联列表
     * 
     * @param nxpExternalAuthority 外部关联
     * @return 外部关联
     */
    @Override
    public List<NxpExternalAuthority> selectNxpExternalAuthorityList(NxpExternalAuthority nxpExternalAuthority)
    {
        return nxpExternalAuthorityMapper.selectNxpExternalAuthorityList(nxpExternalAuthority);
    }

    /**
     * 新增外部关联
     * 
     * @param nxpExternalAuthority 外部关联
     * @return 结果
     */
    @Override
    public int insertNxpExternalAuthority(NxpExternalAuthority nxpExternalAuthority)
    {
        nxpExternalAuthority.setCreateTime(DateUtils.getNowDate());
        return nxpExternalAuthorityMapper.insertNxpExternalAuthority(nxpExternalAuthority);
    }

    /**
     * 修改外部关联
     * 
     * @param nxpExternalAuthority 外部关联
     * @return 结果
     */
    @Override
    public int updateNxpExternalAuthority(NxpExternalAuthority nxpExternalAuthority)
    {
        nxpExternalAuthority.setUpdateTime(DateUtils.getNowDate());
        return nxpExternalAuthorityMapper.updateNxpExternalAuthority(nxpExternalAuthority);
    }

    /**
     * 删除外部关联对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpExternalAuthorityByIds(String ids)
    {
        return nxpExternalAuthorityMapper.deleteNxpExternalAuthorityByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除外部关联信息
     * 
     * @param id 外部关联ID
     * @return 结果
     */
    @Override
    public int deleteNxpExternalAuthorityById(Long id)
    {
        return nxpExternalAuthorityMapper.deleteNxpExternalAuthorityById(id);
    }
}
