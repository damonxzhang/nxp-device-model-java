package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpCheckListFileMapper;
import com.ruoyi.system.domain.NxpCheckListFile;
import com.ruoyi.system.service.INxpCheckListFileService;
import com.ruoyi.common.core.text.Convert;

/**
 * 检查列表文件Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2022-09-04
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpCheckListFileServiceImpl implements INxpCheckListFileService 
{
    @Autowired
    private NxpCheckListFileMapper nxpCheckListFileMapper;

    /**
     * 查询检查列表文件
     * 
     * @param id 检查列表文件ID
     * @return 检查列表文件
     */
    @Override
    public NxpCheckListFile selectNxpCheckListFileById(Long id)
    {
        return nxpCheckListFileMapper.selectNxpCheckListFileById(id);
    }

    /**
     * 查询检查列表文件列表
     * 
     * @param nxpCheckListFile 检查列表文件
     * @return 检查列表文件
     */
    @Override
    public List<NxpCheckListFile> selectNxpCheckListFileList(NxpCheckListFile nxpCheckListFile)
    {
        return nxpCheckListFileMapper.selectNxpCheckListFileList(nxpCheckListFile);
    }

    /**
     * 新增检查列表文件
     * 
     * @param nxpCheckListFile 检查列表文件
     * @return 结果
     */
    @Override
    public int insertNxpCheckListFile(NxpCheckListFile nxpCheckListFile)
    {
        nxpCheckListFile.setCreateTime(DateUtils.getNowDate());
        return nxpCheckListFileMapper.insertNxpCheckListFile(nxpCheckListFile);
    }

    /**
     * 修改检查列表文件
     * 
     * @param nxpCheckListFile 检查列表文件
     * @return 结果
     */
    @Override
    public int updateNxpCheckListFile(NxpCheckListFile nxpCheckListFile)
    {
        return nxpCheckListFileMapper.updateNxpCheckListFile(nxpCheckListFile);
    }

    /**
     * 删除检查列表文件对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpCheckListFileByIds(String ids)
    {
        return nxpCheckListFileMapper.deleteNxpCheckListFileByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除检查列表文件信息
     * 
     * @param id 检查列表文件ID
     * @return 结果
     */
    @Override
    public int deleteNxpCheckListFileById(Long id)
    {
        return nxpCheckListFileMapper.deleteNxpCheckListFileById(id);
    }
}
