package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.NxpFacility;
import com.ruoyi.system.domain.NxpProductType;
import com.ruoyi.system.mapper.NxpFacilityMapper;
import com.ruoyi.system.mapper.NxpProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpFacilityProductTypeMapper;
import com.ruoyi.system.domain.NxpFacilityProductType;
import com.ruoyi.system.service.INxpFacilityProductTypeService;
import com.ruoyi.common.core.text.Convert;

import javax.annotation.Resource;

/**
 * 设备&产品类型Service业务层处理
 *
 * @author YanCheng Liu
 * @date 2023-06-27
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpFacilityProductTypeServiceImpl implements INxpFacilityProductTypeService {
    @Resource
    private NxpFacilityProductTypeMapper nxpFacilityProductTypeMapper;
    @Resource
    private NxpFacilityMapper nxpFacilityMapper;
    @Resource
    private NxpProductTypeMapper nxpProductTypeMapper;

    /**
     * 查询设备&产品类型
     *
     * @param facilityId 设备&产品类型ID
     * @return 设备&产品类型
     */
    @Override
    public NxpFacilityProductType selectNxpFacilityProductTypeById(Long facilityId) {
        return nxpFacilityProductTypeMapper.selectNxpFacilityProductTypeById(facilityId);
    }

    /**
     * 查询设备&产品类型列表
     *
     * @param nxpFacilityProductType 设备&产品类型
     * @return 设备&产品类型
     */
    @Override
    public List<NxpFacilityProductType> selectNxpFacilityProductTypeList(NxpFacilityProductType nxpFacilityProductType) {
        return nxpFacilityProductTypeMapper.selectNxpFacilityProductTypeList(nxpFacilityProductType);
    }

    /**
     * 新增设备&产品类型
     *
     * @param nxpFacilityProductType 设备&产品类型
     * @return 结果
     */
    @Override
    public int insertNxpFacilityProductType(NxpFacilityProductType nxpFacilityProductType) {
        NxpFacilityProductType nxpFacilityProductType1 = nxpFacilityProductTypeMapper.selectNxpFacilityProductTypeById(nxpFacilityProductType.getFacilityId());
        if (nxpFacilityProductType1 == null) {
            NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(nxpFacilityProductType.getFacilityId());
            if (nxpFacility != null) {
                nxpFacilityProductType.setFacilityNumber(nxpFacility.getNumber());
            }
            String productTypeName = "";
            if (nxpFacilityProductType.getProductTypeId().contains(",")) {
                String[] split = nxpFacilityProductType.getProductTypeId().split(",");
                for (String productTypeId : split) {
                    NxpProductType nxpProductType = nxpProductTypeMapper.selectNxpProductTypeById(Long.valueOf(productTypeId));
                    productTypeName = productTypeName + nxpProductType.getProductTypeName() + ",";
                }
            } else {
                NxpProductType nxpProductType = nxpProductTypeMapper.selectNxpProductTypeById(Long.valueOf(nxpFacilityProductType.getProductTypeId()));
                productTypeName = productTypeName + nxpProductType.getProductTypeName() + ",";
            }
            nxpFacilityProductType.setProductTypeName(productTypeName.substring(0, productTypeName.length() - 1));
            nxpFacilityProductType.setCreateTime(DateUtils.getNowDate());
            return nxpFacilityProductTypeMapper.insertNxpFacilityProductType(nxpFacilityProductType);
        } else {
            String productTypeName = "";
            if (nxpFacilityProductType.getProductTypeId().contains(",")) {
                String[] split = nxpFacilityProductType.getProductTypeId().split(",");
                for (String productTypeId : split) {
                    NxpProductType nxpProductType = nxpProductTypeMapper.selectNxpProductTypeById(Long.valueOf(productTypeId));
                    productTypeName = productTypeName + nxpProductType.getProductTypeName() + ",";
                }
            } else {
                NxpProductType nxpProductType = nxpProductTypeMapper.selectNxpProductTypeById(Long.valueOf(nxpFacilityProductType.getProductTypeId()));
                productTypeName = productTypeName + nxpProductType.getProductTypeName() + ",";
            }
            nxpFacilityProductType1.setProductTypeName(productTypeName.substring(0, productTypeName.length() - 1));
            nxpFacilityProductType1.setProductTypeId(nxpFacilityProductType.getProductTypeId());
            nxpFacilityProductType1.setUpdateTime(DateUtils.getNowDate());
            return nxpFacilityProductTypeMapper.updateNxpFacilityProductType(nxpFacilityProductType1);
        }
    }

    /**
     * 修改设备&产品类型
     *
     * @param nxpFacilityProductType 设备&产品类型
     * @return 结果
     */
    @Override
    public int updateNxpFacilityProductType(NxpFacilityProductType nxpFacilityProductType) {
        NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(nxpFacilityProductType.getFacilityId());
        if (nxpFacility != null) {
            nxpFacilityProductType.setFacilityNumber(nxpFacility.getNumber());
        }
        String productTypeName = "";
        if (nxpFacilityProductType.getProductTypeId().contains(",")) {
            String[] split = nxpFacilityProductType.getProductTypeId().split(",");
            for (String productTypeId : split) {
                NxpProductType nxpProductType = nxpProductTypeMapper.selectNxpProductTypeById(Long.valueOf(productTypeId));
                productTypeName = productTypeName + nxpProductType.getProductTypeName() + ",";
            }
        } else {
            NxpProductType nxpProductType = nxpProductTypeMapper.selectNxpProductTypeById(Long.valueOf(nxpFacilityProductType.getProductTypeId()));
            productTypeName = productTypeName + nxpProductType.getProductTypeName() + ",";
        }
        nxpFacilityProductType.setProductTypeName(productTypeName.substring(0, productTypeName.length() - 1));
        nxpFacilityProductType.setUpdateTime(DateUtils.getNowDate());
        return nxpFacilityProductTypeMapper.updateNxpFacilityProductType(nxpFacilityProductType);
    }

    /**
     * 删除设备&产品类型对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpFacilityProductTypeByIds(String ids) {
        return nxpFacilityProductTypeMapper.deleteNxpFacilityProductTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备&产品类型信息
     *
     * @param facilityId 设备&产品类型ID
     * @return 结果
     */
    @Override
    public int deleteNxpFacilityProductTypeById(Long facilityId) {
        return nxpFacilityProductTypeMapper.deleteNxpFacilityProductTypeById(facilityId);
    }
}
