package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpTeamMapper;
import com.ruoyi.system.domain.NxpTeam;
import com.ruoyi.system.service.INxpTeamService;
import com.ruoyi.common.core.text.Convert;

/**
 * GEMBA小组Service业务层处理
 * 
 * @author DamonZhang
 * @date 2021-03-19
 */
@Service
public class NxpTeamServiceImpl implements INxpTeamService 
{
    @Autowired
    private NxpTeamMapper nxpTeamMapper;

    /**
     * 查询GEMBA小组
     * 
     * @param id GEMBA小组ID
     * @return GEMBA小组
     */
    @Override
    public NxpTeam selectNxpTeamById(Long id)
    {
        return nxpTeamMapper.selectNxpTeamById(id);
    }

    /**
     * 查询GEMBA小组列表
     * 
     * @param nxpTeam GEMBA小组
     * @return GEMBA小组
     */
    @Override
    public List<NxpTeam> selectNxpTeamList(NxpTeam nxpTeam)
    {
        return nxpTeamMapper.selectNxpTeamList(nxpTeam);
    }

    /**
     * 新增GEMBA小组
     * 
     * @param nxpTeam GEMBA小组
     * @return 结果
     */
    @Override
    public int insertNxpTeam(NxpTeam nxpTeam)
    {
        return nxpTeamMapper.insertNxpTeam(nxpTeam);
    }

    /**
     * 修改GEMBA小组
     * 
     * @param nxpTeam GEMBA小组
     * @return 结果
     */
    @Override
    public int updateNxpTeam(NxpTeam nxpTeam)
    {
        return nxpTeamMapper.updateNxpTeam(nxpTeam);
    }

    /**
     * 删除GEMBA小组对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpTeamByIds(String ids)
    {
        return nxpTeamMapper.deleteNxpTeamByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除GEMBA小组信息
     * 
     * @param id GEMBA小组ID
     * @return 结果
     */
    @Override
    public int deleteNxpTeamById(Long id)
    {
        return nxpTeamMapper.deleteNxpTeamById(id);
    }
}
