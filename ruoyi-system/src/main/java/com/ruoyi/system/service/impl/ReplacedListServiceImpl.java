package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.config.datasource.DynamicDataSourceContextHolder;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.VO.ReplacedListVO;
import com.ruoyi.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ReplacedListMapper;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 待更换模具Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2023-06-26
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class ReplacedListServiceImpl implements IReplacedListService 
{
    @Resource
    private ReplacedListMapper replacedListMapper;
    @Resource
    private INxpMachineProcessService nxpMachineProcessService;
    @Resource
    private INxpUserInfoService nxpUserInfoService;
    @Resource
    private INxpFacilityService nxpFacilityService;
    @Resource
    private INxpMoldService nxpMoldService;

    /**
     * 查询待更换模具
     * 
     * @param replaceId 待更换模具ID
     * @return 待更换模具
     */
    @Override
    public ReplacedList selectReplacedListById(Long replaceId)
    {
        return replacedListMapper.selectReplacedListById(replaceId);
    }

    /**
     * 查询待更换模具列表
     * 
     * @param replacedList 待更换模具
     * @return 待更换模具
     */
    @Override
    public List<ReplacedList> selectReplacedListList(ReplacedList replacedList)
    {
        List<ReplacedList> replacedLists = replacedListMapper.selectReplacedListList(replacedList);
        for (ReplacedList list : replacedLists) {
            // 工序
            NxpMachineProcess nxpMachineProcess = nxpMachineProcessService.selectNxpMachineProcessById(Long.valueOf(list.getProcessId()));
            if (nxpMachineProcess != null){
                list.setProcess(nxpMachineProcess.getName());
            }
            // 提交人
            NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(list.getUserInfoId());
            if (nxpUserInfo != null){
                list.setUserInfoName(nxpUserInfo.getChinese_name());
            }
            // 执行人
            if (list.getExecuteUserId() != null){
                NxpUserInfo executeUserInfo = nxpUserInfoService.selectNxpUserInfoById(list.getExecuteUserId().longValue());
                if (executeUserInfo != null){
                    list.setExecuteUserInfoName(executeUserInfo.getChinese_name());
                }
            }
            if (list.getNewMoldId() != null){
                NxpMold nxpMold = nxpMoldService.selectNxpMoldById(list.getNewMoldId());
                if (nxpMold != null){
                    list.setNewMoldCode(nxpMold.getMoldCode());
                }
            }
        }
        return replacedLists;
    }

    /**
     * 新增待更换模具
     * 
     * @param replacedList 待更换模具
     * @return 结果
     */
    @Override
    @Transactional
    public int insertReplacedList(ReplacedList replacedList)
    {
        replacedList.setCreateTime(DateUtils.getNowDate());
        return replacedListMapper.insertReplacedList(replacedList);
    }

    /**
     * 修改待更换模具
     * 
     * @param replacedList 待更换模具
     * @return 结果
     */
    @Override
    public int updateReplacedList(ReplacedList replacedList)
    {
        replacedList.setUpdateTime(DateUtils.getNowDate());
        return replacedListMapper.updateReplacedList(replacedList);
    }

    /**
     * 删除待更换模具对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteReplacedListByIds(String ids)
    {
        // 将模具恢复为正常状态
        String[] strArray = Convert.toStrArray(ids);
        for (String id : strArray) {
            ReplacedList replacedList = replacedListMapper.selectReplacedListById(Long.valueOf(id));
            Long moldId = replacedList.getMoldId();
            NxpMold nxpMold = new NxpMold();
            nxpMold.setId(moldId);
            nxpMold.setStatus(1);
            DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
            nxpMoldService.updateNxpMoldApi(nxpMold);
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        int i = replacedListMapper.deleteReplacedListByIds(Convert.toStrArray(ids));
        DynamicDataSourceContextHolder.clearDataSourceType();
        return i;
    }

    /**
     * 删除待更换模具信息
     * 
     * @param replaceId 待更换模具ID
     * @return 结果
     */
    @Override
    public int deleteReplacedListById(Long replaceId)
    {
        return replacedListMapper.deleteReplacedListById(replaceId);
    }

    /**
     * 根据设备id和工序id查询
     * @param replacedList
     * @return
     */
    @Override
    public ReplacedList selectByFacilityIdAndProcessIdLimit1(ReplacedList replacedList) {
        return replacedListMapper.selectByFacilityIdAndProcessIdLimit1(replacedList);
    }

    /**
     * 根据模具编号和type查询
     * @param params
     * @return
     */
    @Override
    public ReplacedList selectByMoldCodeAndTypeLimit1(ReplacedList params) {
        return replacedListMapper.selectByMoldCodeAndTypeLimit1(params);
    }

    @Override
    public List<ReplacedList> selectReplacedListListByParams(ReplacedListVO replacedListVO) {
        return replacedListMapper.selectReplacedListListByParams(replacedListVO);
    }

    @Override
    public List<ReplacedList> selectReplacedListListQuartz(ReplacedList replacedList) {
        return replacedListMapper.selectReplacedListListQuartz(replacedList);
    }

    /**
     * 根据工序查询设备
     * @param nxpFacility
     * @return
     */
    @Override
    public AjaxResult getFacilityByProcess(NxpFacility nxpFacility) {
        List<NxpFacility> nxpFacilities = nxpFacilityService.selectNxpFacilityList(nxpFacility);
        return AjaxResult.success(nxpFacilities);
    }

    @Override
    public ReplacedList selectByStatus(ReplacedList replacedList) {
        return replacedListMapper.selectByStatus(replacedList);
    }
}
