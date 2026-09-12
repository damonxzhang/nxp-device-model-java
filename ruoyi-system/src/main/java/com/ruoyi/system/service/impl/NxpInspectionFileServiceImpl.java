package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpInspectionFileMapper;
import com.ruoyi.system.domain.NxpInspectionFile;
import com.ruoyi.system.service.INxpInspectionFileService;
import com.ruoyi.common.core.text.Convert;

/**
 * 维修上传图片Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionFileServiceImpl implements INxpInspectionFileService 
{
    @Autowired
    private NxpInspectionFileMapper nxpInspectionFileMapper;

    /**
     * 查询维修上传图片
     * 
     * @param id 维修上传图片ID
     * @return 维修上传图片
     */
    @Override
    public NxpInspectionFile selectNxpInspectionFileById(Long id)
    {
        return nxpInspectionFileMapper.selectNxpInspectionFileById(id);
    }
    @Override
    public List<NxpInspectionFile> selectNxpInspectionFileByIds(String[] ids)
    {
        return nxpInspectionFileMapper.selectNxpInspectionFileByIds(ids);
    }

    /**
     * 查询维修上传图片列表
     * 
     * @param nxpInspectionFile 维修上传图片
     * @return 维修上传图片
     */
    @Override
    public List<NxpInspectionFile> selectNxpInspectionFileList(NxpInspectionFile nxpInspectionFile)
    {
        return nxpInspectionFileMapper.selectNxpInspectionFileList(nxpInspectionFile);
    }

    /**
     * 新增维修上传图片
     * 
     * @param nxpInspectionFile 维修上传图片
     * @return 结果
     */
    @Override
    public int insertNxpInspectionFile(NxpInspectionFile nxpInspectionFile)
    {
        return nxpInspectionFileMapper.insertNxpInspectionFile(nxpInspectionFile);
    }

    /**
     * 修改维修上传图片
     * 
     * @param nxpInspectionFile 维修上传图片
     * @return 结果
     */
    @Override
    public int updateNxpInspectionFile(NxpInspectionFile nxpInspectionFile)
    {
        return nxpInspectionFileMapper.updateNxpInspectionFile(nxpInspectionFile);
    }

    /**
     * 删除维修上传图片对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionFileByIds(String ids)
    {
        return nxpInspectionFileMapper.deleteNxpInspectionFileByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除维修上传图片信息
     * 
     * @param id 维修上传图片ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionFileById(Long id)
    {
        return nxpInspectionFileMapper.deleteNxpInspectionFileById(id);
    }
}
