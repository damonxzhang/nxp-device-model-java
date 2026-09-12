package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpProductType;

/**
 * 产品类型Service接口
 * 
 * @author YanCheng Liu
 * @date 2023-06-23
 */
public interface INxpProductTypeService 
{
    /**
     * 查询产品类型
     * 
     * @param id 产品类型ID
     * @return 产品类型
     */
    public NxpProductType selectNxpProductTypeById(Long id);

    /**
     * 查询产品类型列表
     * 
     * @param nxpProductType 产品类型
     * @return 产品类型集合
     */
    public List<NxpProductType> selectNxpProductTypeList(NxpProductType nxpProductType);

    /**
     * 新增产品类型
     * 
     * @param nxpProductType 产品类型
     * @return 结果
     */
    public int insertNxpProductType(NxpProductType nxpProductType);

    /**
     * 修改产品类型
     * 
     * @param nxpProductType 产品类型
     * @return 结果
     */
    public int updateNxpProductType(NxpProductType nxpProductType);

    /**
     * 批量删除产品类型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpProductTypeByIds(String ids);

    /**
     * 删除产品类型信息
     * 
     * @param id 产品类型ID
     * @return 结果
     */
    public int deleteNxpProductTypeById(Long id);

    /**
     * 修改模具信息回显用
     * @param moldId
     * @return
     */
    List<NxpProductType> selectNxpProductTypeByMoldId(Long moldId);

    /**
     * 根据设备id查询产品类型
     * @param facilityId
     * @return
     */
    List<NxpProductType> selectNxpProductTypeByFacilityId(Long facilityId);
}
