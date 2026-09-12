package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpUserLabel;

/**
 * 用户权限标签Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2023-08-13
 */
public interface NxpUserLabelMapper 
{
    /**
     * 查询用户权限标签
     * 
     * @param id 用户权限标签ID
     * @return 用户权限标签
     */
    public NxpUserLabel selectNxpUserLabelById(Long id);

    /**
     * 查询用户权限标签列表
     * 
     * @param nxpUserLabel 用户权限标签
     * @return 用户权限标签集合
     */
    public List<NxpUserLabel> selectNxpUserLabelList(NxpUserLabel nxpUserLabel);

    /**
     * 新增用户权限标签
     * 
     * @param nxpUserLabel 用户权限标签
     * @return 结果
     */
    public int insertNxpUserLabel(NxpUserLabel nxpUserLabel);

    /**
     * 修改用户权限标签
     * 
     * @param nxpUserLabel 用户权限标签
     * @return 结果
     */
    public int updateNxpUserLabel(NxpUserLabel nxpUserLabel);

    /**
     * 删除用户权限标签
     * 
     * @param id 用户权限标签ID
     * @return 结果
     */
    public int deleteNxpUserLabelById(Long id);

    /**
     * 批量删除用户权限标签
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpUserLabelByIds(String[] ids);

    /**
     * 根据userId查询
     * @param userInfoId
     * @return
     */
    NxpUserLabel selectNxpUserLabelByUserInfoId(Integer userInfoId);
}
