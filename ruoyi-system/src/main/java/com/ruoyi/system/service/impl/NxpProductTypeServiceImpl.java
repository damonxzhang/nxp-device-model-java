package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.NxpFacilityProductType;
import com.ruoyi.system.domain.NxpMold;
import com.ruoyi.system.mapper.NxpFacilityMapper;
import com.ruoyi.system.mapper.NxpFacilityProductTypeMapper;
import com.ruoyi.system.mapper.NxpMoldMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpProductTypeMapper;
import com.ruoyi.system.domain.NxpProductType;
import com.ruoyi.system.service.INxpProductTypeService;
import com.ruoyi.common.core.text.Convert;

import javax.annotation.Resource;

/**
 * 产品类型Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2023-06-23
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpProductTypeServiceImpl implements INxpProductTypeService 
{
    @Resource
    private NxpProductTypeMapper nxpProductTypeMapper;
    @Resource
    private NxpMoldMapper nxpMoldMapper;
    @Resource
    private NxpFacilityMapper nxpFacilityMapper;
    @Resource
    private NxpFacilityProductTypeMapper nxpFacilityProductTypeMapper;

    /**
     * 查询产品类型
     * 
     * @param id 产品类型ID
     * @return 产品类型
     */
    @Override
    public NxpProductType selectNxpProductTypeById(Long id)
    {
        return nxpProductTypeMapper.selectNxpProductTypeById(id);
    }

    /**
     * 查询产品类型列表
     * 
     * @param nxpProductType 产品类型
     * @return 产品类型
     */
    @Override
    public List<NxpProductType> selectNxpProductTypeList(NxpProductType nxpProductType)
    {
        return nxpProductTypeMapper.selectNxpProductTypeList(nxpProductType);
    }

    /**
     * 新增产品类型
     * 
     * @param nxpProductType 产品类型
     * @return 结果
     */
    @Override
    public int insertNxpProductType(NxpProductType nxpProductType)
    {
        nxpProductType.setCreateTime(DateUtils.getNowDate());
        return nxpProductTypeMapper.insertNxpProductType(nxpProductType);
    }

    /**
     * 修改产品类型
     * 
     * @param nxpProductType 产品类型
     * @return 结果
     */
    @Override
    public int updateNxpProductType(NxpProductType nxpProductType)
    {
        nxpProductType.setUpdateTime(DateUtils.getNowDate());
        return nxpProductTypeMapper.updateNxpProductType(nxpProductType);
    }

    /**
     * 删除产品类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpProductTypeByIds(String ids)
    {
        return nxpProductTypeMapper.deleteNxpProductTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除产品类型信息
     * 
     * @param id 产品类型ID
     * @return 结果
     */
    @Override
    public int deleteNxpProductTypeById(Long id)
    {
        return nxpProductTypeMapper.deleteNxpProductTypeById(id);
    }

    /**
     * 修改模具信息回显用
     * @param moldId
     * @return
     */
    @Override
    public List<NxpProductType> selectNxpProductTypeByMoldId(Long moldId) {

        // 查询所有
        NxpProductType nxpProductType = new NxpProductType();
        nxpProductType.setStatus(1);
        List<NxpProductType> nxpProductTypes = nxpProductTypeMapper.selectNxpProductTypeList(nxpProductType);

        // 根据moId 查询所绑定的模具产品类型
        List<String> productTypeIds = new ArrayList<>();
        NxpMold nxpMold = nxpMoldMapper.selectNxpMoldById(moldId);
        String productTypeId = nxpMold.getProductTypeId();
        if (StringUtils.isNotEmpty(productTypeId)){
            boolean contains = productTypeId.contains(",");
            if (contains){
                String[] split = productTypeId.split(",");
                productTypeIds.addAll(Arrays.asList(split));
            }else {
                productTypeIds.add(productTypeId);
            }
        }

        for (NxpProductType productType : nxpProductTypes) {
            for (String typeId : productTypeIds) {
                if (productType.getId().longValue() == Long.valueOf(typeId))
                {
                    productType.setFlag(true);
                    break;
                }
            }
        }
        return nxpProductTypes;
    }

    /**
     * 根据设备id查询产品类型
     * @param facilityId
     * @return
     */
    @Override
    public List<NxpProductType> selectNxpProductTypeByFacilityId(Long facilityId) {
        NxpProductType nxpProductTypeParam = new NxpProductType();
        nxpProductTypeParam.setStatus(1);
        List<NxpProductType> nxpProductTypesAll = nxpProductTypeMapper.selectNxpProductTypeList(nxpProductTypeParam);

        NxpFacilityProductType nxpFacilityProductType = nxpFacilityProductTypeMapper.selectNxpFacilityProductTypeById(facilityId);
        String productTypeId = nxpFacilityProductType.getProductTypeId();
        List<String> productTypeIds = new ArrayList<>();
        if (productTypeId.contains(",")){
            String[] split = productTypeId.split(",");
            productTypeIds = Arrays.asList(split);
        }else {
            productTypeIds.add(productTypeId);
        }

        List<NxpProductType> selectByFacilityId = new ArrayList<>();
        for (String typeId : productTypeIds) {
            NxpProductType nxpProductType = nxpProductTypeMapper.selectNxpProductTypeById(Long.valueOf(typeId));
            selectByFacilityId.add(nxpProductType);
        }

        for (NxpProductType nxpProductType : nxpProductTypesAll) {
            for (NxpProductType productType : selectByFacilityId) {
                if (nxpProductType.getId().longValue() == productType.getId().longValue())
                {
                    nxpProductType.setFlag(true);
                    break;
                }
            }
        }
        return nxpProductTypesAll;
    }
}
