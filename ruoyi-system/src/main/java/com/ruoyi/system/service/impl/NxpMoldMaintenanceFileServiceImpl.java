package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpMoldMaintenanceFileMapper;
import com.ruoyi.system.domain.NxpMoldMaintenanceFile;
import com.ruoyi.system.service.INxpMoldMaintenanceFileService;
import com.ruoyi.common.core.text.Convert;

/**
 * 模具维护文件Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpMoldMaintenanceFileServiceImpl implements INxpMoldMaintenanceFileService 
{
    @Autowired
    private NxpMoldMaintenanceFileMapper nxpMoldMaintenanceFileMapper;

    /**
     * 查询模具维护文件
     * 
     * @param id 模具维护文件ID
     * @return 模具维护文件
     */
    @Override
    public NxpMoldMaintenanceFile selectNxpMoldMaintenanceFileById(Long id)
    {
        return nxpMoldMaintenanceFileMapper.selectNxpMoldMaintenanceFileById(id);
    }

    /**
     * 查询模具维护文件列表
     * 
     * @param nxpMoldMaintenanceFile 模具维护文件
     * @return 模具维护文件
     */
    @Override
    public List<NxpMoldMaintenanceFile> selectNxpMoldMaintenanceFileList(NxpMoldMaintenanceFile nxpMoldMaintenanceFile)
    {
        return nxpMoldMaintenanceFileMapper.selectNxpMoldMaintenanceFileList(nxpMoldMaintenanceFile);
    }

    /**
     * 新增模具维护文件
     * 
     * @param nxpMoldMaintenanceFile 模具维护文件
     * @return 结果
     */
    @Override
    public int insertNxpMoldMaintenanceFile(NxpMoldMaintenanceFile nxpMoldMaintenanceFile)
    {
        nxpMoldMaintenanceFile.setCreateTime(DateUtils.getNowDate());
        return nxpMoldMaintenanceFileMapper.insertNxpMoldMaintenanceFile(nxpMoldMaintenanceFile);
    }

    /**
     * 修改模具维护文件
     * 
     * @param nxpMoldMaintenanceFile 模具维护文件
     * @return 结果
     */
    @Override
    public int updateNxpMoldMaintenanceFile(NxpMoldMaintenanceFile nxpMoldMaintenanceFile)
    {
        nxpMoldMaintenanceFile.setUpdateTime(DateUtils.getNowDate());
        return nxpMoldMaintenanceFileMapper.updateNxpMoldMaintenanceFile(nxpMoldMaintenanceFile);
    }

    /**
     * 删除模具维护文件对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldMaintenanceFileByIds(String ids)
    {
        return nxpMoldMaintenanceFileMapper.deleteNxpMoldMaintenanceFileByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除模具维护文件信息
     * 
     * @param id 模具维护文件ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldMaintenanceFileById(Long id)
    {
        return nxpMoldMaintenanceFileMapper.deleteNxpMoldMaintenanceFileById(id);
    }
}
