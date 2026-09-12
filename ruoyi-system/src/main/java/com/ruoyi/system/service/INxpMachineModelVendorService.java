package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpMachineModelVendor;

/**
 * 机型&vendor中间表Service接口
 * 
 * @author DamonZhang
 * @date 2021-05-07
 */
public interface INxpMachineModelVendorService 
{
    /**
     * 查询机型&vendor中间表
     * 
     * @param id 机型&vendor中间表ID
     * @return 机型&vendor中间表
     */
    public NxpMachineModelVendor selectNxpMachineModelVendorById(Long id);

    /**
     * 查询机型&vendor中间表列表
     * 
     * @param nxpMachineModelVendor 机型&vendor中间表
     * @return 机型&vendor中间表集合
     */
    public List<NxpMachineModelVendor> selectNxpMachineModelVendorList(NxpMachineModelVendor nxpMachineModelVendor);

    /**
     * 新增机型&vendor中间表
     * 
     * @param nxpMachineModelVendor 机型&vendor中间表
     * @return 结果
     */
    public int insertNxpMachineModelVendor(NxpMachineModelVendor nxpMachineModelVendor);

    /**
     * 修改机型&vendor中间表
     * 
     * @param nxpMachineModelVendor 机型&vendor中间表
     * @return 结果
     */
    public int updateNxpMachineModelVendor(NxpMachineModelVendor nxpMachineModelVendor);

    /**
     * 批量删除机型&vendor中间表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMachineModelVendorByIds(String ids);

    /**
     * 删除机型&vendor中间表信息
     * 
     * @param id 机型&vendor中间表ID
     * @return 结果
     */
    public int deleteNxpMachineModelVendorById(Long id);
}
