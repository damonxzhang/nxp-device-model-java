package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpFacilityProductType;

/**
 * 设备&产品类型Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2023-06-27
 */
public interface NxpFacilityProductTypeMapper 
{
    /**
     * 查询设备&产品类型
     * 
     * @param facilityId 设备&产品类型ID
     * @return 设备&产品类型
     */
    public NxpFacilityProductType selectNxpFacilityProductTypeById(Long facilityId);

    /**
     * 查询设备&产品类型列表
     * 
     * @param nxpFacilityProductType 设备&产品类型
     * @return 设备&产品类型集合
     */
    public List<NxpFacilityProductType> selectNxpFacilityProductTypeList(NxpFacilityProductType nxpFacilityProductType);

    /**
     * 新增设备&产品类型
     * 
     * @param nxpFacilityProductType 设备&产品类型
     * @return 结果
     */
    public int insertNxpFacilityProductType(NxpFacilityProductType nxpFacilityProductType);

    /**
     * 修改设备&产品类型
     * 
     * @param nxpFacilityProductType 设备&产品类型
     * @return 结果
     */
    public int updateNxpFacilityProductType(NxpFacilityProductType nxpFacilityProductType);

    /**
     * 删除设备&产品类型
     * 
     * @param facilityId 设备&产品类型ID
     * @return 结果
     */
    public int deleteNxpFacilityProductTypeById(Long facilityId);

    /**
     * 批量删除设备&产品类型
     * 
     * @param facilityIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpFacilityProductTypeByIds(String[] facilityIds);

    /**
     * 根据产品类型id查询设备
     * @param nxpFacilityProductType
     * @return
     */
    public List<NxpFacilityProductType> selectByProductTypeId(NxpFacilityProductType nxpFacilityProductType);
}
