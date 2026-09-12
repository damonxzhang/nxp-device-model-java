package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.config.datasource.DynamicDataSourceContextHolder;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.ReplacedListMapper;
import com.ruoyi.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpMoldReturnMapper;
import com.ruoyi.common.core.text.Convert;

import javax.annotation.Resource;

/**
 * 待归还模具Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2023-07-07
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpMoldReturnServiceImpl implements INxpMoldReturnService 
{
    @Resource
    private NxpMoldReturnMapper nxpMoldReturnMapper;
    @Resource
    private INxpMachineProcessService nxpMachineProcessService;
    @Resource
    private INxpUserInfoService nxpUserInfoService;
    @Resource
    private INxpMoldService nxpMoldService;
    @Resource
    private INxpFacilityService nxpFacilityService;
    @Resource
    private ReplacedListMapper replacedListMapper;

    /**
     * 查询待归还模具
     * 
     * @param id 待归还模具ID
     * @return 待归还模具
     */
    @Override
    public NxpMoldReturn selectNxpMoldReturnById(Long id)
    {
        return nxpMoldReturnMapper.selectNxpMoldReturnById(id);
    }

    /**
     * 查询待归还模具列表
     * 
     * @param nxpMoldReturn 待归还模具
     * @return 待归还模具
     */
    @Override
    public List<NxpMoldReturn> selectNxpMoldReturnList(NxpMoldReturn nxpMoldReturn)
    {
        List<NxpMoldReturn> nxpMoldReturns = nxpMoldReturnMapper.selectNxpMoldReturnList(nxpMoldReturn);
        for (NxpMoldReturn moldReturn : nxpMoldReturns) {
            // 工序
            if (StringUtils.isNotEmpty(moldReturn.getProcessId())){
                NxpMachineProcess nxpMachineProcess = nxpMachineProcessService.selectNxpMachineProcessById(Long.valueOf(moldReturn.getProcessId()));
                if (nxpMachineProcess != null){
                    moldReturn.setProcess(nxpMachineProcess.getName());
                }
            }
            // 提交人
            NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(moldReturn.getUserInfoId());
            if (nxpUserInfo != null){
                moldReturn.setUserInfoName(nxpUserInfo.getChinese_name());
            }
        }
        return nxpMoldReturns;
    }

    /**
     * 新增待归还模具
     * 
     * @param nxpMoldReturn 待归还模具
     * @return 结果
     */
    @Override
    public int insertNxpMoldReturn(NxpMoldReturn nxpMoldReturn)
    {
        nxpMoldReturn.setCreateTime(DateUtils.getNowDate());
        return nxpMoldReturnMapper.insertNxpMoldReturn(nxpMoldReturn);
    }

    /**
     * 修改待归还模具
     * 
     * @param nxpMoldReturn 待归还模具
     * @return 结果
     */
    @Override
    public AjaxResult updateNxpMoldReturn(NxpMoldReturn nxpMoldReturn)
    {
        nxpMoldReturn.setUpdateTime(DateUtils.getNowDate());
        // 查询模具
        String moldCode = nxpMoldReturn.getMoldCode();
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        NxpMold nxpMold = nxpMoldService.selectNxpMoldByCode(moldCode);
        DynamicDataSourceContextHolder.clearDataSourceType();

        if (nxpMold == null){
            return AjaxResult.error("模具不存在");
        }

        // 获取状态
        Integer status = nxpMoldReturn.getStatus();
        switch (status){
            case 1:
                // 待归还
                break;
            case 2:
                // 已归还到仓库
                nxpMoldReturn.setReturnPosition(1);

                if (nxpMold.getStatus() == 2 || nxpMold.getStatus() == 3) {
                    // 模具为异常状态时不处理
                } else {
                    nxpMold.setStatus(1);
                }
                nxpMold.setFacilityId(0);
                break;
            case 3:
                if (nxpMoldReturn.getFacilityId() == null){
                    return AjaxResult.error("归还到设备上时，设备为必传字段");
                }
                DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
                NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(nxpMoldReturn.getFacilityId());
                DynamicDataSourceContextHolder.clearDataSourceType();

                if (nxpFacility == null){
                    return AjaxResult.error("设备不存在");
                }
                // 已归还到设备
                nxpMoldReturn.setReturnPosition(2);
                // 表示归还到设备上，那么直接该模具直接与设备进行绑定
                if (nxpMold.getStatus() == 2 || nxpMold.getStatus() == 3) {
                    // 模具为异常状态时不处理
                } else {
                    nxpMold.setStatus(1);
                }

                // 查询现在的设备上有无模具,如果设备上没有模具，则绑定
                DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
                NxpMold moldFacility = nxpMoldService.selectNxpMoldByFacility(nxpFacility.getId());
                DynamicDataSourceContextHolder.clearDataSourceType();

                if (moldFacility == null) {
                    nxpMold.setFacilityId(nxpFacility.getId().intValue());
                    nxpMold.setStatus(4);
                } else {
                    // 如果设备上有模具，老模具变成待归还，新模具被绑定
                    moldFacility.setStatus(6);
                    // 老模具设备id变成0
                    moldFacility.setFacilityId(0);
                    DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
                    nxpMoldService.updateNxpMoldApi(moldFacility);
                    DynamicDataSourceContextHolder.clearDataSourceType();

                    // 老模具新增数据到待归还列表
                    NxpMoldReturn nxpMoldReturnParams = new NxpMoldReturn();
                    nxpMoldReturnParams.setMoldId(moldFacility.getId());
                    nxpMoldReturnParams.setMoldCode(moldFacility.getMoldCode());
                    nxpMoldReturnParams.setMoldRmsCode(moldFacility.getMoldRmsCode());
                    nxpMoldReturnParams.setMoldName(moldFacility.getMoldName());
                    nxpMoldReturnParams.setFacilityId(nxpFacility.getId());
                    nxpMoldReturnParams.setFacilityNumber(nxpFacility.getNumber());
                    nxpMoldReturnParams.setProcessId(nxpFacility.getProcess());
                    nxpMoldReturnParams.setStatus(1);
                    nxpMoldReturnParams.setMoldType(moldFacility.getMoldType());
                    DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
                    nxpMoldReturnMapper.insertNxpMoldReturn(nxpMoldReturnParams);
                    DynamicDataSourceContextHolder.clearDataSourceType();

                    //新模具被绑定
                    nxpMold.setFacilityId(nxpFacility.getId().intValue());
                    nxpMold.setStatus(4);

                }
                break;
        }

        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        nxpMoldService.updateNxpMoldApi(nxpMold);
        DynamicDataSourceContextHolder.clearDataSourceType();

        if (nxpMoldReturn.getFacilityId() != null){
            DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
            NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(nxpMoldReturn.getFacilityId());
            DynamicDataSourceContextHolder.clearDataSourceType();
            nxpMoldReturn.setFacilityNumber(nxpFacility.getNumber());
        }
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        nxpMoldReturnMapper.updateNxpMoldReturn(nxpMoldReturn);
        DynamicDataSourceContextHolder.clearDataSourceType();

        // 同一套模具归还一次，那么待归还数据全部更新为已归还
        NxpMoldReturn returnParams = new NxpMoldReturn();
        returnParams.setMoldRmsCode(nxpMoldReturn.getMoldCode());
        returnParams.setStatus(1);
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        List<NxpMoldReturn> nxpMoldReturns = nxpMoldReturnMapper.selectNxpMoldReturnList(returnParams);
        DynamicDataSourceContextHolder.clearDataSourceType();
        for (NxpMoldReturn moldReturn : nxpMoldReturns) {
            moldReturn.setStatus(nxpMoldReturn.getStatus());
            moldReturn.setReturnPosition(nxpMoldReturn.getReturnPosition());
            DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
            nxpMoldReturnMapper.updateNxpMoldReturn(moldReturn);
            DynamicDataSourceContextHolder.clearDataSourceType();
        }

        // 处理模具待更换列表模具和模具盒
        ReplacedList params = new ReplacedList();
        params.setMoldCode(nxpMold.getMoldCode());
        params.setType(1);
        params.setPageNum(null);
        params.setPageSize(null);
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        ReplacedList moldReplacedList = replacedListMapper.selectByMoldCodeAndTypeLimit1(params);
        DynamicDataSourceContextHolder.clearDataSourceType();
        if (moldReplacedList != null) {
            moldReplacedList.setStatus(4);
            DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
            replacedListMapper.updateReplacedList(moldReplacedList);
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
        params.setType(2);
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        ReplacedList moldBoxReplacedList = replacedListMapper.selectByMoldCodeAndTypeLimit1(params);
        DynamicDataSourceContextHolder.clearDataSourceType();
        if (moldBoxReplacedList != null) {
            moldBoxReplacedList.setStatus(4);
            DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
            replacedListMapper.updateReplacedList(moldBoxReplacedList);
            DynamicDataSourceContextHolder.clearDataSourceType();
        }

        return AjaxResult.success();
    }

    /**
     * 删除待归还模具对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldReturnByIds(String ids)
    {
        return nxpMoldReturnMapper.deleteNxpMoldReturnByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除待归还模具信息
     * 
     * @param id 待归还模具ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldReturnById(Long id)
    {
        return nxpMoldReturnMapper.deleteNxpMoldReturnById(id);
    }

    @Override
    public List<NxpMoldReturn> selectNxpMoldReturnListNew(NxpMoldReturn nxpMoldReturn) {
        List<NxpMoldReturn> nxpMoldReturns = nxpMoldReturnMapper.selectNxpMoldReturnListNew(nxpMoldReturn);
        return nxpMoldReturns;
    }

    @Override
    public int updateNxpMoldReturnApi(NxpMoldReturn nxpMoldReturn) {
        nxpMoldReturn.setUpdateTime(DateUtils.getNowDate());
        return nxpMoldReturnMapper.updateNxpMoldReturn(nxpMoldReturn);
    }
}
