package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpSwcCheckSelf;

/**
 * SWC自查问题Mapper接口
 * 
 * @author DamonZhang
 * @date 2021-03-18
 */
public interface NxpSwcCheckSelfMapper 
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
     * 删除SWC自查问题
     * 
     * @param id SWC自查问题ID
     * @return 结果
     */
    public int deleteNxpSwcCheckSelfById(Long id);

    /**
     * 批量删除SWC自查问题
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpSwcCheckSelfByIds(String[] ids);
}
