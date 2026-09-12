package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionFile;

/**
 * 维修上传图片Service接口
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
public interface INxpInspectionFileService 
{
    /**
     * 查询维修上传图片
     * 
     * @param id 维修上传图片ID
     * @return 维修上传图片
     */
    public NxpInspectionFile selectNxpInspectionFileById(Long id);
    public List<NxpInspectionFile> selectNxpInspectionFileByIds(String[] ids);

    /**
     * 查询维修上传图片列表
     * 
     * @param nxpInspectionFile 维修上传图片
     * @return 维修上传图片集合
     */
    public List<NxpInspectionFile> selectNxpInspectionFileList(NxpInspectionFile nxpInspectionFile);

    /**
     * 新增维修上传图片
     * 
     * @param nxpInspectionFile 维修上传图片
     * @return 结果
     */
    public int insertNxpInspectionFile(NxpInspectionFile nxpInspectionFile);

    /**
     * 修改维修上传图片
     * 
     * @param nxpInspectionFile 维修上传图片
     * @return 结果
     */
    public int updateNxpInspectionFile(NxpInspectionFile nxpInspectionFile);

    /**
     * 批量删除维修上传图片
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionFileByIds(String ids);

    /**
     * 删除维修上传图片信息
     * 
     * @param id 维修上传图片ID
     * @return 结果
     */
    public int deleteNxpInspectionFileById(Long id);
}
