package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpCheckListFile;

/**
 * 检查列表文件Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2022-09-04
 */
public interface NxpCheckListFileMapper 
{
    /**
     * 查询检查列表文件
     * 
     * @param id 检查列表文件ID
     * @return 检查列表文件
     */
    public NxpCheckListFile selectNxpCheckListFileById(Long id);

    /**
     * 查询检查列表文件列表
     * 
     * @param nxpCheckListFile 检查列表文件
     * @return 检查列表文件集合
     */
    public List<NxpCheckListFile> selectNxpCheckListFileList(NxpCheckListFile nxpCheckListFile);

    /**
     * 新增检查列表文件
     * 
     * @param nxpCheckListFile 检查列表文件
     * @return 结果
     */
    public int insertNxpCheckListFile(NxpCheckListFile nxpCheckListFile);

    /**
     * 修改检查列表文件
     * 
     * @param nxpCheckListFile 检查列表文件
     * @return 结果
     */
    public int updateNxpCheckListFile(NxpCheckListFile nxpCheckListFile);

    /**
     * 删除检查列表文件
     * 
     * @param id 检查列表文件ID
     * @return 结果
     */
    public int deleteNxpCheckListFileById(Long id);

    /**
     * 批量删除检查列表文件
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpCheckListFileByIds(String[] ids);
}
