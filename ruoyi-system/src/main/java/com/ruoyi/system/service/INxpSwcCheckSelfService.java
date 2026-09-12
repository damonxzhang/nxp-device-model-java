package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpSwcCheckSelf;

/**
 * SWC自查问题Service接口
 * 
 * @author DamonZhang
 * @date 2021-03-18
 */
public interface INxpSwcCheckSelfService 
{
    /**
     * 查询SWC自查问题
     * 
     * @param id SWC自查问题ID
     * @return SWC自查问题
     */
    public NxpSwcCheckSelf selectNxpSwcCheckSelfById(Long id);

    /**
     * 查询SWC自查问题列表
     * 
     * @param nxpSwcCheckSelf SWC自查问题
     * @return SWC自查问题集合
     */
    public List<NxpSwcCheckSelf> selectNxpSwcCheckSelfList(NxpSwcCheckSelf nxpSwcCheckSelf);

    /**
     * 新增SWC自查问题
     * 
     * @param nxpSwcCheckSelf SWC自查问题
     * @return 结果
     */
    public int insertNxpSwcCheckSelf(NxpSwcCheckSelf nxpSwcCheckSelf);

    /**
     * 修改SWC自查问题
     * 
     * @param nxpSwcCheckSelf SWC自查问题
     * @return 结果
     */
    public int updateNxpSwcCheckSelf(NxpSwcCheckSelf nxpSwcCheckSelf);

    /**
     * 批量删除SWC自查问题
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpSwcCheckSelfByIds(String ids);

    /**
     * 删除SWC自查问题信息
     * 
     * @param id SWC自查问题ID
     * @return 结果
     */
    public int deleteNxpSwcCheckSelfById(Long id);
}
