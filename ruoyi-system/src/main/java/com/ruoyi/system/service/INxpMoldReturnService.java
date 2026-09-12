package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.NxpMoldReturn;

/**
 * 待归还模具Service接口
 * 
 * @author YanCheng Liu
 * @date 2023-07-07
 */
public interface INxpMoldReturnService 
{
    /**
     * 查询待归还模具
     * 
     * @param id 待归还模具ID
     * @return 待归还模具
     */
    public NxpMoldReturn selectNxpMoldReturnById(Long id);

    /**
     * 查询待归还模具列表
     * 
     * @param nxpMoldReturn 待归还模具
     * @return 待归还模具集合
     */
    public List<NxpMoldReturn> selectNxpMoldReturnList(NxpMoldReturn nxpMoldReturn);

    /**
     * 新增待归还模具
     * 
     * @param nxpMoldReturn 待归还模具
     * @return 结果
     */
    public int insertNxpMoldReturn(NxpMoldReturn nxpMoldReturn);

    /**
     * 修改待归还模具
     * 
     * @param nxpMoldReturn 待归还模具
     * @return 结果
     */
    public AjaxResult updateNxpMoldReturn(NxpMoldReturn nxpMoldReturn);

    /**
     * 批量删除待归还模具
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMoldReturnByIds(String ids);

    /**
     * 删除待归还模具信息
     * 
     * @param id 待归还模具ID
     * @return 结果
     */
    public int deleteNxpMoldReturnById(Long id);

    List<NxpMoldReturn> selectNxpMoldReturnListNew(NxpMoldReturn nxpMoldReturn);

    int updateNxpMoldReturnApi(NxpMoldReturn nxpMoldReturn);
}
