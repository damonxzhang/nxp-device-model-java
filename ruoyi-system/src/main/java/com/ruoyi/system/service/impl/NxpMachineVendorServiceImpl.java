package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpMachineVendorMapper;
import com.ruoyi.system.domain.NxpMachineVendor;
import com.ruoyi.system.service.INxpMachineVendorService;
import com.ruoyi.common.core.text.Convert;

/**
 * 设备供应商Service业务层处理
 * 
 * @author DamonZhang
 * @date 2021-05-07
 */
@Service
@DataSource(value = DataSourceType.SLAVE)

public class NxpMachineVendorServiceImpl implements INxpMachineVendorService 
{
    @Autowired
    private NxpMachineVendorMapper nxpMachineVendorMapper;

    /**
     * 查询设备供应商
     * 
     * @param id 设备供应商ID
     * @return 设备供应商
     */
    @Override
    public NxpMachineVendor selectNxpMachineVendorById(Long id)
    {
        return nxpMachineVendorMapper.selectNxpMachineVendorById(id);
    }

    /**
     * 查询设备供应商列表
     * 
     * @param nxpMachineVendor 设备供应商
     * @return 设备供应商
     */
    @Override
    public List<NxpMachineVendor> selectNxpMachineVendorList(NxpMachineVendor nxpMachineVendor)
    {
        return nxpMachineVendorMapper.selectNxpMachineVendorList(nxpMachineVendor);
    }

    /**
     * 新增设备供应商
     * 
     * @param nxpMachineVendor 设备供应商
     * @return 结果
     */
    @Override
    public int insertNxpMachineVendor(NxpMachineVendor nxpMachineVendor)
    {
        nxpMachineVendor.setCreateTime(DateUtils.getNowDate());
        return nxpMachineVendorMapper.insertNxpMachineVendor(nxpMachineVendor);
    }

    /**
     * 修改设备供应商
     * 
     * @param nxpMachineVendor 设备供应商
     * @return 结果
     */
    @Override
    public int updateNxpMachineVendor(NxpMachineVendor nxpMachineVendor)
    {
        return nxpMachineVendorMapper.updateNxpMachineVendor(nxpMachineVendor);
    }

    /**
     * 删除设备供应商对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpMachineVendorByIds(String ids)
    {
        return nxpMachineVendorMapper.deleteNxpMachineVendorByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备供应商信息
     * 
     * @param id 设备供应商ID
     * @return 结果
     */
    @Override
    public int deleteNxpMachineVendorById(Long id)
    {
        return nxpMachineVendorMapper.deleteNxpMachineVendorById(id);
    }
}
