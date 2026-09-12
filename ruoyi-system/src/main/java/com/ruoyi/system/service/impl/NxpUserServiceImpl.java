package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpUserMapper;
import com.ruoyi.system.domain.NxpUser;
import com.ruoyi.system.service.INxpUserService;
import com.ruoyi.common.core.text.Convert;

/**
 * 用户登录Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2021-03-09
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpUserServiceImpl implements INxpUserService 
{
    @Autowired
    private NxpUserMapper nxpUserMapper;

    /**
     * 查询用户登录
     * 
     * @param id 用户登录ID
     * @return 用户登录
     */
    @Override
    public NxpUser selectNxpUserById(Long id)
    {
        return nxpUserMapper.selectNxpUserById(id);
    }

    /**
     * 查询用户登录列表
     * 
     * @param nxpUser 用户登录
     * @return 用户登录
     */
    @Override
    public List<NxpUser> selectNxpUserList(NxpUser nxpUser)
    {
        return nxpUserMapper.selectNxpUserList(nxpUser);
    }

    /**
     * 新增用户登录
     * 
     * @param nxpUser 用户登录
     * @return 结果
     */
    @Override
    public int insertNxpUser(NxpUser nxpUser)
    {
        nxpUser.setCreateTime(DateUtils.getNowDate());
        return nxpUserMapper.insertNxpUser(nxpUser);
    }

    /**
     * 修改用户登录
     * 
     * @param nxpUser 用户登录
     * @return 结果
     */
    @Override
    public int updateNxpUser(NxpUser nxpUser)
    {
        return nxpUserMapper.updateNxpUser(nxpUser);
    }

    /**
     * 删除用户登录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpUserByIds(String ids)
    {
        return nxpUserMapper.deleteNxpUserByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户登录信息
     * 
     * @param id 用户登录ID
     * @return 结果
     */
    @Override
    public int deleteNxpUserById(Long id)
    {
        return nxpUserMapper.deleteNxpUserById(id);
    }

    @Override
    public NxpUser selectByUserInfoId(Long id) {
        return nxpUserMapper.selectByUserInfoId(id);
    }
}
