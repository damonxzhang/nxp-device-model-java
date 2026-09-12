package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpMoldProduct;

/**
 * 模具&产品类型Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2023-06-26
 */
public interface NxpMoldProductMapper 
{
    /**
     * 查询模具&产品类型
     * 
     * @param moldId 模具&产品类型ID
     * @return 模具&产品类型
     */
    public NxpMoldProduct selectNxpMoldProductById(Long moldId);

    /**
     * 查询模具&产品类型列表
     * 
     * @param nxpMoldProduct 模具&产品类型
     * @return 模具&产品类型集合
     */
    public List<NxpMoldProduct> selectNxpMoldProductList(NxpMoldProduct nxpMoldProduct);

    /**
     * 新增模具&产品类型
     * 
     * @param nxpMoldProduct 模具&产品类型
     * @return 结果
     */
    public int insertNxpMoldProduct(NxpMoldProduct nxpMoldProduct);

    /**
     * 修改模具&产品类型
     * 
     * @param nxpMoldProduct 模具&产品类型
     * @return 结果
     */
    public int updateNxpMoldProduct(NxpMoldProduct nxpMoldProduct);

    /**
     * 删除模具&产品类型
     * 
     * @param moldId 模具&产品类型ID
     * @return 结果
     */
    public int deleteNxpMoldProductById(Long moldId);

    /**
     * 批量删除模具&产品类型
     * 
     * @param moldIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMoldProductByIds(String[] moldIds);

    /**
     * 根据模具id查询最新的一条更换记录
     * @param moldId
     * @return
     */
    NxpMoldProduct selectByMoldIdLimit1(Long moldId);
}
