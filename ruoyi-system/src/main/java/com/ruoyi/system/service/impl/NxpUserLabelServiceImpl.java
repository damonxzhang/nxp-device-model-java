package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpUserLabelMapper;
import com.ruoyi.system.domain.NxpUserLabel;
import com.ruoyi.system.service.INxpUserLabelService;
import com.ruoyi.common.core.text.Convert;

import javax.annotation.Resource;

/**
 * 用户权限标签Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2023-08-13
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpUserLabelServiceImpl implements INxpUserLabelService 
{
    @Resource
    private NxpUserLabelMapper nxpUserLabelMapper;

    /**
     * 查询用户权限标签
     * 
     * @param id 用户权限标签ID
     * @return 用户权限标签
     */
    @Override
    public NxpUserLabel selectNxpUserLabelById(Long id)
    {
        return nxpUserLabelMapper.selectNxpUserLabelById(id);
    }

    /**
     * 查询用户权限标签列表
     * 
     * @param nxpUserLabel 用户权限标签
     * @return 用户权限标签
     */
    @Override
    public List<NxpUserLabel> selectNxpUserLabelList(NxpUserLabel nxpUserLabel)
    {
        return nxpUserLabelMapper.selectNxpUserLabelList(nxpUserLabel);
    }

    /**
     * 新增用户权限标签
     * 
     * @param nxpUserLabel 用户权限标签
     * @return 结果
     */
    @Override
    public int insertNxpUserLabel(NxpUserLabel nxpUserLabel)
    {

        nxpUserLabel.setCreateTime(DateUtils.getNowDate());
        return nxpUserLabelMapper.insertNxpUserLabel(nxpUserLabel);
    }

    /**
     * 修改用户权限标签
     * 
     * @param nxpUserLabel 用户权限标签
     * @return 结果
     */
    @Override
    public int updateNxpUserLabel(NxpUserLabel nxpUserLabel)
    {
        nxpUserLabel.setUpdateTime(DateUtils.getNowDate());
        return nxpUserLabelMapper.updateNxpUserLabel(nxpUserLabel);
    }

    /**
     * 删除用户权限标签对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpUserLabelByIds(String ids)
    {
        return nxpUserLabelMapper.deleteNxpUserLabelByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户权限标签信息
     * 
     * @param id 用户权限标签ID
     * @return 结果
     */
    @Override
    public int deleteNxpUserLabelById(Long id)
    {
        return nxpUserLabelMapper.deleteNxpUserLabelById(id);
    }

    /**
     * 根据userId查询
     * @param userInfoId
     * @return
     */
    @Override
    public NxpUserLabel selectNxpUserLabelByUserInfoId(Integer userInfoId) {
        return nxpUserLabelMapper.selectNxpUserLabelByUserInfoId(userInfoId);
    }
}
