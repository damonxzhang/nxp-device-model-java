package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpUserInfo;

/**
 * 用户信息Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2021-02-05
 */
public interface NxpUserInfoMapper 
{
    /**
     * 查询用户信息
     * 
     * @param id 用户信息ID
     * @return 用户信息
     */
    public NxpUserInfo selectNxpUserInfoById(Long id);

    /**
     * 查询用户信息列表
     * 
     * @param nxpUserInfo 用户信息
     * @return 用户信息集合
     */
    public List<NxpUserInfo> selectNxpUserInfoList(NxpUserInfo nxpUserInfo);

    /**
     * 新增用户信息
     * 
     * @param nxpUserInfo 用户信息
     * @return 结果
     */
    public int insertNxpUserInfo(NxpUserInfo nxpUserInfo);

    /**
     * 修改用户信息
     * 
     * @param nxpUserInfo 用户信息
     * @return 结果
     */
    public int updateNxpUserInfo(NxpUserInfo nxpUserInfo);

    /**
     * 删除用户信息
     * 
     * @param id 用户信息ID
     * @return 结果
     */
    public int deleteNxpUserInfoById(Long id);

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpUserInfoByIds(String[] ids);

    /**
     * 批量查询
     * @param ids
     * @return
     */
    List<NxpUserInfo> selectNxpUserInfoByIds(String[] ids);
}
