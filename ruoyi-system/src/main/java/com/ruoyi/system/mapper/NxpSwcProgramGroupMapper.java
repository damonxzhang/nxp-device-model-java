package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpSwcProgramGroup;
import com.ruoyi.system.domain.VO.NxpSwcProgramGroupVO;

/**
 * 检查组管理Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2021-03-18
 */
public interface NxpSwcProgramGroupMapper 
{
    /**
     * 查询检查组管理
     * 
     * @param id 检查组管理ID
     * @return 检查组管理
     */
    public NxpSwcProgramGroup selectNxpSwcProgramGroupById(Long id);

    /**
     * 查询检查组管理列表
     * 
     * @param nxpSwcProgramGroup 检查组管理
     * @return 检查组管理集合
     */
    public List<NxpSwcProgramGroup> selectNxpSwcProgramGroupList(NxpSwcProgramGroup nxpSwcProgramGroup);

    /**
     * 新增检查组管理
     * 
     * @param nxpSwcProgramGroup 检查组管理
     * @return 结果
     */
    public int insertNxpSwcProgramGroup(NxpSwcProgramGroup nxpSwcProgramGroup);

    /**
     * 修改检查组管理
     * 
     * @param nxpSwcProgramGroup 检查组管理
     * @return 结果
     */
    public int updateNxpSwcProgramGroup(NxpSwcProgramGroup nxpSwcProgramGroup);

    /**
     * 删除检查组管理
     * 
     * @param id 检查组管理ID
     * @return 结果
     */
    public int deleteNxpSwcProgramGroupById(Long id);

    /**
     * 批量删除检查组管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpSwcProgramGroupByIds(String[] ids);

    /**
     * 根据groupId连表查询所有
     * @param nxpSwcProgramGroup
     * @return
     */
    List<NxpSwcProgramGroupVO> selectThreeMenu(NxpSwcProgramGroup nxpSwcProgramGroup);
}
