package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.NxpFacility;
import com.ruoyi.system.domain.ReplacedList;
import com.ruoyi.system.domain.VO.ReplacedListVO;

/**
 * 待更换模具Service接口
 * 
 * @author YanCheng Liu
 * @date 2023-06-26
 */
public interface IReplacedListService 
{
    /**
     * 查询待更换模具
     * 
     * @param replaceId 待更换模具ID
     * @return 待更换模具
     */
    public ReplacedList selectReplacedListById(Long replaceId);

    /**
     * 查询待更换模具列表
     * 
     * @param replacedList 待更换模具
     * @return 待更换模具集合
     */
    public List<ReplacedList> selectReplacedListList(ReplacedList replacedList);

    /**
     * 新增待更换模具
     * 
     * @param replacedList 待更换模具
     * @return 结果
     */
    public int insertReplacedList(ReplacedList replacedList);

    /**
     * 修改待更换模具
     * 
     * @param replacedList 待更换模具
     * @return 结果
     */
    public int updateReplacedList(ReplacedList replacedList);

    /**
     * 批量删除待更换模具
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteReplacedListByIds(String ids);

    /**
     * 删除待更换模具信息
     * 
     * @param replaceId 待更换模具ID
     * @return 结果
     */
    public int deleteReplacedListById(Long replaceId);

    /**
     * 根据设备id和工序id查询
     * @param replacedList
     * @return
     */
    public ReplacedList selectByFacilityIdAndProcessIdLimit1(ReplacedList replacedList);

    /**
     * 根据模具编号和type查询
     * @param params
     * @return
     */
    ReplacedList selectByMoldCodeAndTypeLimit1(ReplacedList params);

    List<ReplacedList> selectReplacedListListByParams(ReplacedListVO replacedListVO);

    List<ReplacedList> selectReplacedListListQuartz(ReplacedList replacedList);

    /**
     * 根据工序查询设备
     * @param nxpFacility
     * @return
     */
    AjaxResult getFacilityByProcess(NxpFacility nxpFacility);

    ReplacedList selectByStatus(ReplacedList replacedList);
}
