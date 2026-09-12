package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.system.domain.NxpFacilityUser;

/**
 * 设备相关人员Service接口
 * 
 * @author YanCheng Liu
 * @date 2021-04-09
 */
public interface INxpFacilityUserService 
{
    /**
     * 查询设备相关人员
     * 
     * @param id 设备相关人员ID
     * @return 设备相关人员
     */
    public NxpFacilityUser selectNxpFacilityUserById(Long id);
    public List<NxpFacilityUser> selectNxpFacilityUserByFacilityId(Long id);

    /**
     * 查询设备相关人员列表
     * 
     * @param nxpFacilityUser 设备相关人员
     * @return 设备相关人员集合
     */
    public List<NxpFacilityUser> selectNxpFacilityUserList(NxpFacilityUser nxpFacilityUser);

    /**
     * 新增设备相关人员
     * 
     * @param nxpFacilityUser 设备相关人员
     * @return 结果
     */
    public int insertNxpFacilityUser(NxpFacilityUser nxpFacilityUser);

    /**
     * 修改设备相关人员
     * 
     * @param nxpFacilityUser 设备相关人员
     * @return 结果
     */
    public int updateNxpFacilityUser(NxpFacilityUser nxpFacilityUser);

    /**
     * 批量删除设备相关人员
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpFacilityUserByIds(String ids);

    /**
     * 删除设备相关人员信息
     * 
     * @param id 设备相关人员ID
     * @return 结果
     */
    public int deleteNxpFacilityUserById(Long id);

    /**
     * 初始化设备相关人员
     * @return
     */
    BaseVO initFacilityUser();
}
