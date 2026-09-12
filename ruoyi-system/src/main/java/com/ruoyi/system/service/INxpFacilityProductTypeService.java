package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpFacilityProductType;

/**
 * 设备&产品类型Service接口
 * 
 * @author YanCheng Liu
 * @date 2023-06-27
 */
public interface INxpFacilityProductTypeService 
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
     * 批量删除设备&产品类型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpFacilityProductTypeByIds(String ids);

    /**
     * 删除设备&产品类型信息
     * 
     * @param facilityId 设备&产品类型ID
     * @return 结果
     */
    public int deleteNxpFacilityProductTypeById(Long facilityId);
}
