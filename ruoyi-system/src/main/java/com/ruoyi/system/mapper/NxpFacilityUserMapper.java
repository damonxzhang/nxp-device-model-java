package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpFacilityUser;

/**
 * 设备相关人员Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2021-04-09
 */
public interface NxpFacilityUserMapper 
{
    /**
     * 查询设备相关人员
     * 
     * @param id 设备相关人员ID
     * @return 设备相关人员
     */
    public NxpFacilityUser selectNxpFacilityUserById(Long id);
    public List<NxpFacilityUser> selectNxpFacilityUserByFacilityId(Long facilityId);
    public List<NxpFacilityUser> selectNxpFacilityUserGroupByFacilityId(NxpFacilityUser nxpFacilityUser);

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
     * 删除设备相关人员
     * 
     * @param id 设备相关人员ID
     * @return 结果
     */
    public int deleteNxpFacilityUserById(Long id);
    public int deleteNxpFacilityUserByFacilityId(Long facilityId);

    /**
     * 批量删除设备相关人员
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpFacilityUserByIds(String[] ids);
}
