package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpUser;

/**
 * 用户登录Service接口
 * 
 * @author YanCheng Liu
 * @date 2021-03-09
 */
public interface INxpUserService 
{
    /**
     * 查询用户登录
     * 
     * @param id 用户登录ID
     * @return 用户登录
     */
    public NxpUser selectNxpUserById(Long id);

    /**
     * 查询用户登录列表
     * 
     * @param nxpUser 用户登录
     * @return 用户登录集合
     */
    public List<NxpUser> selectNxpUserList(NxpUser nxpUser);

    /**
     * 新增用户登录
     * 
     * @param nxpUser 用户登录
     * @return 结果
     */
    public int insertNxpUser(NxpUser nxpUser);

    /**
     * 修改用户登录
     * 
     * @param nxpUser 用户登录
     * @return 结果
     */
    public int updateNxpUser(NxpUser nxpUser);

    /**
     * 批量删除用户登录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpUserByIds(String ids);

    /**
     * 删除用户登录信息
     * 
     * @param id 用户登录ID
     * @return 结果
     */
    public int deleteNxpUserById(Long id);

    /**
     * 根据userInfoId查询
     * @param id
     * @return
     */
    NxpUser selectByUserInfoId(Long id);
}
