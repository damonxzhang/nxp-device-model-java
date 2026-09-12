package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpProductType;

/**
 * 产品类型Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2023-06-23
 */
public interface NxpProductTypeMapper 
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
     * 删除产品类型
     * 
     * @param id 产品类型ID
     * @return 结果
     */
    public int deleteNxpProductTypeById(Long id);

    /**
     * 批量删除产品类型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpProductTypeByIds(String[] ids);
}
