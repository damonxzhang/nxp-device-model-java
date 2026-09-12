package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpCheckList;

/**
 * 检查记录Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2022-09-01
 */
public interface NxpCheckListMapper 
{
    /**
     * 查询检查记录
     * 
     * @param id 检查记录ID
     * @return 检查记录
     */
    public NxpCheckList selectNxpCheckListById(Long id);

    /**
     * 查询检查记录列表
     * 
     * @param nxpCheckList 检查记录
     * @return 检查记录集合
     */
    public List<NxpCheckList> selectNxpCheckListList(NxpCheckList nxpCheckList);
    public List<NxpCheckList> selectCheckList(NxpCheckList nxpCheckList);

    /**
     * 新增检查记录
     * 
     * @param nxpCheckList 检查记录
     * @return 结果
     */
    public int insertNxpCheckList(NxpCheckList nxpCheckList);

    /**
     * 修改检查记录
     * 
     * @param nxpCheckList 检查记录
     * @return 结果
     */
    public int updateNxpCheckList(NxpCheckList nxpCheckList);

    /**
     * 删除检查记录
     * 
     * @param id 检查记录ID
     * @return 结果
     */
    public int deleteNxpCheckListById(Long id);

    /**
     * 批量删除检查记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpCheckListByIds(String[] ids);
}
