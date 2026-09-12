package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpMachineModelVendorMapper;
import com.ruoyi.system.domain.NxpMachineModelVendor;
import com.ruoyi.system.service.INxpMachineModelVendorService;
import com.ruoyi.common.core.text.Convert;

/**
 * 机型&vendor中间表Service业务层处理
 * 
 * @author DamonZhang
 * @date 2021-05-07
 */
@Service
@DataSource(value = DataSourceType.SLAVE)

public class NxpMachineModelVendorServiceImpl implements INxpMachineModelVendorService 
{
    @Autowired
    private NxpMachineModelVendorMapper nxpMachineModelVendorMapper;

    /**
     * 查询机型&vendor中间表
     * 
     * @param id 机型&vendor中间表ID
     * @return 机型&vendor中间表
     */
    @Override
    public NxpMachineModelVendor selectNxpMachineModelVendorById(Long id)
    {
        return nxpMachineModelVendorMapper.selectNxpMachineModelVendorById(id);
    }

    /**
     * 查询机型&vendor中间表列表
     * 
     * @param nxpMachineModelVendor 机型&vendor中间表
     * @return 机型&vendor中间表
     */
    @Override
    public List<NxpMachineModelVendor> selectNxpMachineModelVendorList(NxpMachineModelVendor nxpMachineModelVendor)
    {
        return nxpMachineModelVendorMapper.selectNxpMachineModelVendorListByName(nxpMachineModelVendor);
//        return nxpMachineModelVendorMapper.selectNxpMachineModelVendorList(nxpMachineModelVendor);
    }

    /**
     * 新增机型&vendor中间表
     * 
     * @param nxpMachineModelVendor 机型&vendor中间表
     * @return 结果
     */
    @Override
    public int insertNxpMachineModelVendor(NxpMachineModelVendor nxpMachineModelVendor)
    {
        return nxpMachineModelVendorMapper.insertNxpMachineModelVendor(nxpMachineModelVendor);
    }

    /**
     * 修改机型&vendor中间表
     * 
     * @param nxpMachineModelVendor 机型&vendor中间表
     * @return 结果
     */
    @Override
    public int updateNxpMachineModelVendor(NxpMachineModelVendor nxpMachineModelVendor)
    {
        return nxpMachineModelVendorMapper.updateNxpMachineModelVendor(nxpMachineModelVendor);
    }

    /**
     * 删除机型&vendor中间表对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpMachineModelVendorByIds(String ids)
    {
        return nxpMachineModelVendorMapper.deleteNxpMachineModelVendorByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除机型&vendor中间表信息
     * 
     * @param id 机型&vendor中间表ID
     * @return 结果
     */
    @Override
    public int deleteNxpMachineModelVendorById(Long id)
    {
        return nxpMachineModelVendorMapper.deleteNxpMachineModelVendorById(id);
    }
}
