package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.NxpMold;
import com.ruoyi.system.domain.NxpProductType;
import com.ruoyi.system.mapper.NxpMoldMapper;
import com.ruoyi.system.mapper.NxpProductTypeMapper;
import com.ruoyi.system.service.INxpMoldService;
import com.ruoyi.system.service.INxpProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpMoldProductMapper;
import com.ruoyi.system.domain.NxpMoldProduct;
import com.ruoyi.system.service.INxpMoldProductService;
import com.ruoyi.common.core.text.Convert;

import javax.annotation.Resource;

/**
 * 模具&产品类型Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2023-06-26
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpMoldProductServiceImpl implements INxpMoldProductService 
{
    @Resource
    private NxpMoldProductMapper nxpMoldProductMapper;
    @Resource
    private NxpMoldMapper nxpMoldMapper;
    @Resource
    private NxpProductTypeMapper nxpProductTypeMapper;

    /**
     * 查询模具&产品类型
     * 
     * @param moldId 模具&产品类型ID
     * @return 模具&产品类型
     */
    @Override
    public NxpMoldProduct selectNxpMoldProductById(Long moldId)
    {
        return nxpMoldProductMapper.selectNxpMoldProductById(moldId);
    }

    /**
     * 查询模具&产品类型列表
     * 
     * @param nxpMoldProduct 模具&产品类型
     * @return 模具&产品类型
     */
    @Override
    public List<NxpMoldProduct> selectNxpMoldProductList(NxpMoldProduct nxpMoldProduct)
    {
        return nxpMoldProductMapper.selectNxpMoldProductList(nxpMoldProduct);
    }

    /**
     * 新增模具&产品类型
     * 
     * @param nxpMoldProduct 模具&产品类型
     * @return 结果
     */
    @Override
    public int insertNxpMoldProduct(NxpMoldProduct nxpMoldProduct)
    {
        NxpMold nxpMold = nxpMoldMapper.selectNxpMoldById(nxpMoldProduct.getMoldId());
        if (nxpMold != null){
            nxpMoldProduct.setMoldName(nxpMold.getMoldCode());
        }
        NxpProductType nxpProductType = nxpProductTypeMapper.selectNxpProductTypeById(nxpMoldProduct.getProductTypeId());
        if (nxpProductType != null){
            nxpMoldProduct.setProductTypeName(nxpProductType.getProductTypeName());
        }
        nxpMoldProduct.setCreateTime(DateUtils.getNowDate());
        return nxpMoldProductMapper.insertNxpMoldProduct(nxpMoldProduct);
    }

    /**
     * 修改模具&产品类型
     * 
     * @param nxpMoldProduct 模具&产品类型
     * @return 结果
     */
    @Override
    public int updateNxpMoldProduct(NxpMoldProduct nxpMoldProduct)
    {
        NxpMold nxpMold = nxpMoldMapper.selectNxpMoldById(nxpMoldProduct.getMoldId());
        if (nxpMold != null){
            nxpMoldProduct.setMoldName(nxpMold.getMoldCode());
        }
        NxpProductType nxpProductType = nxpProductTypeMapper.selectNxpProductTypeById(nxpMoldProduct.getProductTypeId());
        if (nxpProductType != null){
            nxpMoldProduct.setProductTypeName(nxpProductType.getProductTypeName());
        }
        nxpMoldProduct.setUpdateTime(DateUtils.getNowDate());
        return nxpMoldProductMapper.updateNxpMoldProduct(nxpMoldProduct);
    }

    /**
     * 删除模具&产品类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldProductByIds(String ids)
    {
        return nxpMoldProductMapper.deleteNxpMoldProductByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除模具&产品类型信息
     * 
     * @param moldId 模具&产品类型ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldProductById(Long moldId)
    {
        return nxpMoldProductMapper.deleteNxpMoldProductById(moldId);
    }

    /**
     * 根据模具id查询最新的一条更换记录
     * @param moldId
     * @return
     */
    @Override
    public NxpMoldProduct selectByMoldIdLimit1(Long moldId) {
        return nxpMoldProductMapper.selectByMoldIdLimit1(moldId);
    }
}
