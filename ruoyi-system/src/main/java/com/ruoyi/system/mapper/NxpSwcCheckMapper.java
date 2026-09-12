package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpSwcCheck;

/**
 * SWC互查问题Mapper接口
 * 
 * @author DamonZhang
 * @date 2021-03-18
 */
public interface NxpSwcCheckMapper 
{
    /**
     * 查询SWC互查问题
     * 
     * @param id SWC互查问题ID
     * @return SWC互查问题
     */
    public NxpSwcCheck selectNxpSwcCheckById(Long id);

    /**
     * 查询SWC互查问题列表
     * 
     * @param nxpSwcCheck SWC互查问题
     * @return SWC互查问题集合
     */
    public List<NxpSwcCheck> selectNxpSwcCheckList(NxpSwcCheck nxpSwcCheck);

    /**
     * 新增SWC互查问题
     * 
     * @param nxpSwcCheck SWC互查问题
     * @return 结果
     */
    public int insertNxpSwcCheck(NxpSwcCheck nxpSwcCheck);

    /**
     * 修改SWC互查问题
     * 
     * @param nxpSwcCheck SWC互查问题
     * @return 结果
     */
    public int updateNxpSwcCheck(NxpSwcCheck nxpSwcCheck);

    /**
     * 删除SWC互查问题
     * 
     * @param id SWC互查问题ID
     * @return 结果
     */
    public int deleteNxpSwcCheckById(Long id);

    /**
     * 批量删除SWC互查问题
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpSwcCheckByIds(String[] ids);
}
