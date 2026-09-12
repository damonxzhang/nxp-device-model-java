package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.system.domain.DTO.InspectionInfoDTO;
import com.ruoyi.system.domain.NxpInspection;
import com.ruoyi.system.domain.NxpInspectionService;

/**
 * 待处理设备Mapper接口
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
public interface NxpInspectionMapper 
{
    /**
     * 查询待处理设备
     * 
     * @param id 待处理设备ID
     * @return 待处理设备
     */
    public NxpInspection selectNxpInspectionById(Long id);

    /**
     * 查询待处理设备列表
     * 
     * @param nxpInspection 待处理设备
     * @return 待处理设备集合
     */
    public List<NxpInspection> selectNxpInspectionList(NxpInspection nxpInspection);
    public List<NxpInspection> selectNxpInspectionListForFacility(NxpInspection nxpInspection);

    public List<NxpInspection> selectNxpInspectionList01(NxpInspection nxpInspection);

    public List<NxpInspection> selectNxpInspectionListNew(NxpInspection nxpInspection);

    /**
     * 新增待处理设备
     * 
     * @param nxpInspection 待处理设备
     * @return 结果
     */
    public int insertNxpInspection(NxpInspection nxpInspection);

    /**
     * 修改待处理设备
     * 
     * @param nxpInspection 待处理设备
     * @return 结果
     */
    public int updateNxpInspection(NxpInspection nxpInspection);

    /**
     * 删除待处理设备
     * 
     * @param id 待处理设备ID
     * @return 结果
     */
    public int deleteNxpInspectionById(Long id);

    /**
     * 批量删除待处理设备
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionByIds(String[] ids);

    /**
     * 查询报修信息
     * @param nxpInspectionService
     * @return
     */
    InspectionInfoDTO inspectionInfo(NxpInspectionService nxpInspectionService);

    /**
     * 根据状态和设备id查询唯一
     * @param nxpInspection
     * @return
     */
    NxpInspection selectByStatusAndDeviceId(NxpInspection nxpInspection);

    /**
     * 根据设备id查询
     * @param facility_id
     * @return
     */
    List<NxpInspection> selectNxpInspectionListByFacilityId(String facility_id);

    /**
     * 根据设备id查询是否存在非完成的报修
     * @param facilityId
     * @return
     */
    int selectNxpInspectionListByFacilityIdCount(Long facilityId);

    /**
     * 查询所有待处理设备（定时任务用）
     * @param nxpInspection
     * @return
     */
    public List<NxpInspection> selectNxpInspectionListByTiming(NxpInspection nxpInspection);
}
