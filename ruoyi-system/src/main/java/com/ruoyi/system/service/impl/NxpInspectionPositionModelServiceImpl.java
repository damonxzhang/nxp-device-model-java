package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpInspectionPositionModelMapper;
import com.ruoyi.system.domain.NxpInspectionPositionModel;
import com.ruoyi.system.service.INxpInspectionPositionModelService;
import com.ruoyi.common.core.text.Convert;

/**
 * 维修位置&设备型号Service业务层处理
 * 
 * @author DamonZhang
 * @date 2021-04-02
 */
@Service
@DataSource(value = DataSourceType.SLAVE)

public class NxpInspectionPositionModelServiceImpl implements INxpInspectionPositionModelService 
{
    @Autowired
    private NxpInspectionPositionModelMapper nxpInspectionPositionModelMapper;

    /**
     * 查询维修位置&设备型号
     * 
     * @param positionId 维修位置&设备型号ID
     * @return 维修位置&设备型号
     */
    @Override
    public NxpInspectionPositionModel selectNxpInspectionPositionModelById(Long positionId)
    {
        return nxpInspectionPositionModelMapper.selectNxpInspectionPositionModelById(positionId);
    }

    /**
     * 查询维修位置&设备型号列表
     * 
     * @param nxpInspectionPositionModel 维修位置&设备型号
     * @return 维修位置&设备型号
     */
    @Override
    public List<NxpInspectionPositionModel> selectNxpInspectionPositionModelList(NxpInspectionPositionModel nxpInspectionPositionModel)
    {
        return nxpInspectionPositionModelMapper.selectNxpInspectionPositionModelList(nxpInspectionPositionModel);
    }

    @Override
    public List<NxpInspectionPositionModel> selectNxpInspectionPositionModelListByModelId(NxpInspectionPositionModel nxpInspectionPositionModel)
    {
        return nxpInspectionPositionModelMapper.selectNxpInspectionPositionModelListByModelId(nxpInspectionPositionModel);
    }

    /**
     * 新增维修位置&设备型号
     * 
     * @param nxpInspectionPositionModel 维修位置&设备型号
     * @return 结果
     */
    @Override
    public int insertNxpInspectionPositionModel(NxpInspectionPositionModel nxpInspectionPositionModel)
    {
        return nxpInspectionPositionModelMapper.insertNxpInspectionPositionModel(nxpInspectionPositionModel);
    }

    /**
     * 修改维修位置&设备型号
     * 
     * @param nxpInspectionPositionModel 维修位置&设备型号
     * @return 结果
     */
    @Override
    public int updateNxpInspectionPositionModel(NxpInspectionPositionModel nxpInspectionPositionModel)
    {
        return nxpInspectionPositionModelMapper.updateNxpInspectionPositionModel(nxpInspectionPositionModel);
    }

    /**
     * 删除维修位置&设备型号对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPositionModelByIds(String ids)
    {
        return nxpInspectionPositionModelMapper.deleteNxpInspectionPositionModelByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除维修位置&设备型号信息
     * 
     * @param positionId 维修位置&设备型号ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPositionModelById(Long positionId)
    {
        return nxpInspectionPositionModelMapper.deleteNxpInspectionPositionModelById(positionId);
    }
}
