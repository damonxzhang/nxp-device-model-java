package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.system.domain.DTO.NxpFacilityDTO;
import com.ruoyi.system.domain.DTO.NxpFacilityListDTO;
import com.ruoyi.system.domain.NxpFacility;
import com.ruoyi.system.domain.NxpInspection;
import com.ruoyi.system.domain.NxpMold;
import com.ruoyi.system.domain.VO.NxpFacilityVO;

/**
 * 设备管理Mapper接口
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
public interface NxpFacilityMapper 
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
     * 删除设备管理
     * 
     * @param id 设备管理ID
     * @return 结果
     */
    public int deleteNxpFacilityById(Long id);

    /**
     * 批量删除设备管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpFacilityByIds(String[] ids);

    /**
     * 查询设备详情
     * @param nxpFacility
     * @return
     */
    NxpFacility selectNxpFacilityByIdOrNumber(NxpFacility nxpFacility);

    /**
     * 待处理的设备列表
     * @param nxpInspection
     * @return
     */
    List<NxpFacilityDTO> pendingInspection(NxpInspection nxpInspection);

    /**
     * 设备列表
     * @param nxpFacility
     * @return
     */
    List<NxpFacilityListDTO> selectFacilityList(NxpFacility nxpFacility);

    /**
     * 根据ids批量查询not in
     * @param nxpFacilityVO
     * @return
     */
    List<NxpFacility> selectNxpFacilityByNotInIds(NxpFacilityVO nxpFacilityVO);

    /**
     * 根据id批量查询
     * @param ids
     * @return
     */
    List<NxpFacility> selectNxpFacilityByIds(String[] ids);

    /**
     * 根据设备编号查询设备
     * @param number
     * @return
     */
    NxpFacility selectNxpFacilityByNumber(String number);

    /**
     * 根据设备编号查询设备以及设备工序
     * @param number
     * @return
     */
    NxpFacility selectNxpFacilityAndProcessByNumber(String number);

    /**
     * 查询pm设备
     * @param nxpFacility
     * @return
     */
    List<NxpFacility> selectNxpFacilityPmList(NxpFacility nxpFacility);

    /**
     * 根据machineModel和productTypeId查询
     * @param nxpMold
     * @return
     */
    List<NxpFacility> selectFacilityByModelAndProductTypeId(NxpMold nxpMold);
}
