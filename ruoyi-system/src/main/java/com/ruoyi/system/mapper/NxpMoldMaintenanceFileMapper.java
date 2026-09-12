package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpMoldMaintenanceFile;

/**
 * 模具维护文件Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
public interface NxpMoldMaintenanceFileMapper 
{
    /**
     * 查询模具维护文件
     * 
     * @param id 模具维护文件ID
     * @return 模具维护文件
     */
    public NxpMoldMaintenanceFile selectNxpMoldMaintenanceFileById(Long id);

    /**
     * 查询模具维护文件列表
     * 
     * @param nxpMoldMaintenanceFile 模具维护文件
     * @return 模具维护文件集合
     */
    public List<NxpMoldMaintenanceFile> selectNxpMoldMaintenanceFileList(NxpMoldMaintenanceFile nxpMoldMaintenanceFile);

    /**
     * 新增模具维护文件
     * 
     * @param nxpMoldMaintenanceFile 模具维护文件
     * @return 结果
     */
    public int insertNxpMoldMaintenanceFile(NxpMoldMaintenanceFile nxpMoldMaintenanceFile);

    /**
     * 修改模具维护文件
     * 
     * @param nxpMoldMaintenanceFile 模具维护文件
     * @return 结果
     */
    public int updateNxpMoldMaintenanceFile(NxpMoldMaintenanceFile nxpMoldMaintenanceFile);

    /**
     * 删除模具维护文件
     * 
     * @param id 模具维护文件ID
     * @return 结果
     */
    public int deleteNxpMoldMaintenanceFileById(Long id);

    /**
     * 批量删除模具维护文件
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMoldMaintenanceFileByIds(String[] ids);
}
