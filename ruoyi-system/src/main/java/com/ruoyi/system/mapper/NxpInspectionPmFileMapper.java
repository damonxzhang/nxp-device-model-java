package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionPmFile;

/**
 * pm图片Mapper接口
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
public interface NxpInspectionPmFileMapper 
{
    /**
     * 查询pm图片
     * 
     * @param id pm图片ID
     * @return pm图片
     */
    public NxpInspectionPmFile selectNxpInspectionPmFileById(Long id);

    /**
     * 查询pm图片列表
     * 
     * @param nxpInspectionPmFile pm图片
     * @return pm图片集合
     */
    public List<NxpInspectionPmFile> selectNxpInspectionPmFileList(NxpInspectionPmFile nxpInspectionPmFile);

    /**
     * 新增pm图片
     * 
     * @param nxpInspectionPmFile pm图片
     * @return 结果
     */
    public int insertNxpInspectionPmFile(NxpInspectionPmFile nxpInspectionPmFile);

    /**
     * 修改pm图片
     * 
     * @param nxpInspectionPmFile pm图片
     * @return 结果
     */
    public int updateNxpInspectionPmFile(NxpInspectionPmFile nxpInspectionPmFile);

    /**
     * 删除pm图片
     * 
     * @param id pm图片ID
     * @return 结果
     */
    public int deleteNxpInspectionPmFileById(Long id);

    /**
     * 批量删除pm图片
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionPmFileByIds(String[] ids);

    /**
     * 根据id批量查询
     * @param ids
     * @return
     */
    List<NxpInspectionPmFile> selectNxpInspectionPmFileByIds(String[] ids);
}
