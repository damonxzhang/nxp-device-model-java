package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpInspectionPmFileMapper;
import com.ruoyi.system.domain.NxpInspectionPmFile;
import com.ruoyi.system.service.INxpInspectionPmFileService;
import com.ruoyi.common.core.text.Convert;

/**
 * pm图片Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionPmFileServiceImpl implements INxpInspectionPmFileService 
{
    @Autowired
    private NxpInspectionPmFileMapper nxpInspectionPmFileMapper;

    /**
     * 查询pm图片
     * 
     * @param id pm图片ID
     * @return pm图片
     */
    @Override
    public NxpInspectionPmFile selectNxpInspectionPmFileById(Long id)
    {
        return nxpInspectionPmFileMapper.selectNxpInspectionPmFileById(id);
    }

    /**
     * 查询pm图片列表
     * 
     * @param nxpInspectionPmFile pm图片
     * @return pm图片
     */
    @Override
    public List<NxpInspectionPmFile> selectNxpInspectionPmFileList(NxpInspectionPmFile nxpInspectionPmFile)
    {
        return nxpInspectionPmFileMapper.selectNxpInspectionPmFileList(nxpInspectionPmFile);
    }

    /**
     * 新增pm图片
     * 
     * @param nxpInspectionPmFile pm图片
     * @return 结果
     */
    @Override
    public int insertNxpInspectionPmFile(NxpInspectionPmFile nxpInspectionPmFile)
    {
        nxpInspectionPmFile.setCreateTime(DateUtils.getNowDate());
        return nxpInspectionPmFileMapper.insertNxpInspectionPmFile(nxpInspectionPmFile);
    }

    /**
     * 修改pm图片
     * 
     * @param nxpInspectionPmFile pm图片
     * @return 结果
     */
    @Override
    public int updateNxpInspectionPmFile(NxpInspectionPmFile nxpInspectionPmFile)
    {
        return nxpInspectionPmFileMapper.updateNxpInspectionPmFile(nxpInspectionPmFile);
    }

    /**
     * 删除pm图片对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPmFileByIds(String ids)
    {
        return nxpInspectionPmFileMapper.deleteNxpInspectionPmFileByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除pm图片信息
     * 
     * @param id pm图片ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPmFileById(Long id)
    {
        return nxpInspectionPmFileMapper.deleteNxpInspectionPmFileById(id);
    }
}
