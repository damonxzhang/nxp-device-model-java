package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpMachineModelVendor;

/**
 * 机型&vendor中间表Mapper接口
 * 
 * @author DamonZhang
 * @date 2021-05-07
 */
public interface NxpMachineModelVendorMapper 
{
    /**
     * 查询机型&vendor中间表
     * 
     * @param id 机型&vendor中间表ID
     * @return 机型&vendor中间表
     */
    public NxpMachineModelVendor selectNxpMachineModelVendorById(Long id);
    public List<NxpMachineModelVendor> selectNxpMachineModelVendorListByModelId(Long model_id);
    public List<NxpMachineModelVendor> selectNxpMachineModelVendorListByVendorId(Long vendor_id);

    /**
     * 查询机型&vendor中间表列表
     * 
     * @param nxpMachineModelVendor 机型&vendor中间表
     * @return 机型&vendor中间表集合
     */
    public List<NxpMachineModelVendor> selectNxpMachineModelVendorList(NxpMachineModelVendor nxpMachineModelVendor);
    public List<NxpMachineModelVendor> selectNxpMachineModelVendorListByName(NxpMachineModelVendor nxpMachineModelVendor);

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
     * 删除机型&vendor中间表
     * 
     * @param id 机型&vendor中间表ID
     * @return 结果
     */
    public int deleteNxpMachineModelVendorById(Long id);
    public int deleteNxpMachineModelVendorByModelId(Long model_id);

    /**
     * 批量删除机型&vendor中间表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMachineModelVendorByIds(String[] ids);
}
