package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpInspectionServiceDetailMapper;
import com.ruoyi.system.domain.NxpInspectionServiceDetail;
import com.ruoyi.system.service.INxpInspectionServiceDetailService;
import com.ruoyi.common.core.text.Convert;

/**
 * 维修详情备件Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionServiceDetailServiceImpl implements INxpInspectionServiceDetailService 
{
    @Autowired
    private NxpInspectionServiceDetailMapper nxpInspectionServiceDetailMapper;

    /**
     * 查询维修详情备件
     * 
     * @param id 维修详情备件ID
     * @return 维修详情备件
     */
    @Override
    public NxpInspectionServiceDetail selectNxpInspectionServiceDetailById(Long id)
    {
        return nxpInspectionServiceDetailMapper.selectNxpInspectionServiceDetailById(id);
    }

    /**
     * 查询维修详情备件列表
     * 
     * @param nxpInspectionServiceDetail 维修详情备件
     * @return 维修详情备件
     */
    @Override
    public List<NxpInspectionServiceDetail> selectNxpInspectionServiceDetailList(NxpInspectionServiceDetail nxpInspectionServiceDetail)
    {
        return nxpInspectionServiceDetailMapper.selectNxpInspectionServiceDetailList(nxpInspectionServiceDetail);
    }

    /**
     * 新增维修详情备件
     * 
     * @param nxpInspectionServiceDetail 维修详情备件
     * @return 结果
     */
    @Override
    public int insertNxpInspectionServiceDetail(NxpInspectionServiceDetail nxpInspectionServiceDetail)
    {
        return nxpInspectionServiceDetailMapper.insertNxpInspectionServiceDetail(nxpInspectionServiceDetail);
    }

    /**
     * 修改维修详情备件
     * 
     * @param nxpInspectionServiceDetail 维修详情备件
     * @return 结果
     */
    @Override
    public int updateNxpInspectionServiceDetail(NxpInspectionServiceDetail nxpInspectionServiceDetail)
    {
        return nxpInspectionServiceDetailMapper.updateNxpInspectionServiceDetail(nxpInspectionServiceDetail);
    }

    /**
     * 删除维修详情备件对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionServiceDetailByIds(String ids)
    {
        return nxpInspectionServiceDetailMapper.deleteNxpInspectionServiceDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除维修详情备件信息
     * 
     * @param id 维修详情备件ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionServiceDetailById(Long id)
    {
        return nxpInspectionServiceDetailMapper.deleteNxpInspectionServiceDetailById(id);
    }
}
