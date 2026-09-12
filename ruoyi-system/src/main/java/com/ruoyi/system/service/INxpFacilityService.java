package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpFacility;
import com.ruoyi.system.domain.NxpMold;
import com.ruoyi.system.domain.VO.NxpFacilityVO;

/**
 * 设备管理Service接口
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
public interface INxpFacilityService 
{
    /**
     * 查询设备管理
     * 
     * @param id 设备管理ID
     * @return 设备管理
     */
    public NxpFacility selectNxpFacilityById(Long id);

    /**
     * 查询设备管理列表
     * 
     * @param nxpFacility 设备管理
     * @return 设备管理集合
     */
    public List<NxpFacility> selectNxpFacilityList(NxpFacility nxpFacility);

    /**
     * 新增设备管理
     * 
     * @param nxpFacility 设备管理
     * @return 结果
     */
    public int insertNxpFacility(NxpFacility nxpFacility);

    /**
     * 修改设备管理
     * 
     * @param nxpFacility 设备管理
     * @return 结果
     */
    public int updateNxpFacility(NxpFacility nxpFacility);

    /**
     * 批量删除设备管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpFacilityByIds(String ids);

    /**
     * 删除设备管理信息
     * 
     * @param id 设备管理ID
     * @return 结果
     */
    public int deleteNxpFacilityById(Long id);

    /**
     * 根据ids批量查询
     * @param nxpFacilityVO
     * @return
     */
    List<NxpFacility> selectNxpFacilityByNotInIds(NxpFacilityVO nxpFacilityVO);

    /**
     * pm设备查询
     * @param nxpFacility
     * @return
     */
    List<NxpFacility> selectNxpFacilityPmList(NxpFacility nxpFacility);

    /**
     * 根据设备编号查询设备
     * @param number
     * @return
     */
    NxpFacility selectByNumber(String number);

    List<NxpFacility> selectFacilityByModelAndProductTypeId(NxpMold nxpMold);
}
