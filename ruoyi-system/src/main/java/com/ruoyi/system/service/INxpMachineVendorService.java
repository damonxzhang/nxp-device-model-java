package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpMachineVendor;

/**
 * 设备供应商Service接口
 * 
 * @author DamonZhang
 * @date 2021-05-07
 */
public interface INxpMachineVendorService 
{
    /**
     * 查询设备供应商
     * 
     * @param id 设备供应商ID
     * @return 设备供应商
     */
    public NxpMachineVendor selectNxpMachineVendorById(Long id);

    /**
     * 查询设备供应商列表
     * 
     * @param nxpMachineVendor 设备供应商
     * @return 设备供应商集合
     */
    public List<NxpMachineVendor> selectNxpMachineVendorList(NxpMachineVendor nxpMachineVendor);

    /**
     * 新增设备供应商
     * 
     * @param nxpMachineVendor 设备供应商
     * @return 结果
     */
    public int insertNxpMachineVendor(NxpMachineVendor nxpMachineVendor);

    /**
     * 修改设备供应商
     * 
     * @param nxpMachineVendor 设备供应商
     * @return 结果
     */
    public int updateNxpMachineVendor(NxpMachineVendor nxpMachineVendor);

    /**
     * 批量删除设备供应商
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMachineVendorByIds(String ids);

    /**
     * 删除设备供应商信息
     * 
     * @param id 设备供应商ID
     * @return 结果
     */
    public int deleteNxpMachineVendorById(Long id);
}
