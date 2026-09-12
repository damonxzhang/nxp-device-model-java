package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpTeam;

/**
 * GEMBA小组Service接口
 * 
 * @author DamonZhang
 * @date 2021-03-19
 */
public interface INxpTeamService 
{
    /**
     * 查询GEMBA小组
     * 
     * @param id GEMBA小组ID
     * @return GEMBA小组
     */
    public NxpTeam selectNxpTeamById(Long id);

    /**
     * 查询GEMBA小组列表
     * 
     * @param nxpTeam GEMBA小组
     * @return GEMBA小组集合
     */
    public List<NxpTeam> selectNxpTeamList(NxpTeam nxpTeam);

    /**
     * 新增GEMBA小组
     * 
     * @param nxpTeam GEMBA小组
     * @return 结果
     */
    public int insertNxpTeam(NxpTeam nxpTeam);

    /**
     * 修改GEMBA小组
     * 
     * @param nxpTeam GEMBA小组
     * @return 结果
     */
    public int updateNxpTeam(NxpTeam nxpTeam);

    /**
     * 批量删除GEMBA小组
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpTeamByIds(String ids);

    /**
     * 删除GEMBA小组信息
     * 
     * @param id GEMBA小组ID
     * @return 结果
     */
    public int deleteNxpTeamById(Long id);
}
