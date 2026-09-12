package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.NxpMoldMaintenanceGroupItem;
import com.ruoyi.system.domain.NxpUserInfo;
import com.ruoyi.system.service.INxpMoldMaintenanceGroupService;
import com.ruoyi.system.service.INxpUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpMoldMaintenanceCheckMapper;
import com.ruoyi.system.domain.NxpMoldMaintenanceCheck;
import com.ruoyi.system.service.INxpMoldMaintenanceCheckService;
import com.ruoyi.common.core.text.Convert;

/**
 * 模具维护检查结果Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpMoldMaintenanceCheckServiceImpl implements INxpMoldMaintenanceCheckService 
{
    @Autowired
    private NxpMoldMaintenanceCheckMapper nxpMoldMaintenanceCheckMapper;
    @Autowired
    private INxpMoldMaintenanceGroupService nxpMoldMaintenanceGroupService;
    @Autowired
    private INxpUserInfoService nxpUserInfoService;

    /**
     * 查询模具维护检查结果
     * 
     * @param id 模具维护检查结果ID
     * @return 模具维护检查结果
     */
    @Override
    public NxpMoldMaintenanceCheck selectNxpMoldMaintenanceCheckById(Long id)
    {
        return nxpMoldMaintenanceCheckMapper.selectNxpMoldMaintenanceCheckById(id);
    }

    /**
     * 查询模具维护检查结果列表
     * 
     * @param nxpMoldMaintenanceCheck 模具维护检查结果
     * @return 模具维护检查结果
     */
    @Override
    public List<NxpMoldMaintenanceCheck> selectNxpMoldMaintenanceCheckList(NxpMoldMaintenanceCheck nxpMoldMaintenanceCheck)
    {
        List<NxpMoldMaintenanceCheck> nxpMoldMaintenanceChecks = nxpMoldMaintenanceCheckMapper.selectNxpMoldMaintenanceCheckList(nxpMoldMaintenanceCheck);
        for (NxpMoldMaintenanceCheck moldMaintenanceCheck : nxpMoldMaintenanceChecks) {
            NxpMoldMaintenanceGroupItem nxpMoldMaintenanceGroupItem = nxpMoldMaintenanceGroupService.selectGroupItemById(moldMaintenanceCheck.getGroupItemId());
            moldMaintenanceCheck.setGroupItemName(nxpMoldMaintenanceGroupItem.getItemName());
            NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(moldMaintenanceCheck.getUserInfoId());
            moldMaintenanceCheck.setUserInfoName(nxpUserInfo.getChinese_name());
        }
        return nxpMoldMaintenanceChecks;
    }

    /**
     * 新增模具维护检查结果
     * 
     * @param nxpMoldMaintenanceCheck 模具维护检查结果
     * @return 结果
     */
    @Override
    public int insertNxpMoldMaintenanceCheck(NxpMoldMaintenanceCheck nxpMoldMaintenanceCheck)
    {
        nxpMoldMaintenanceCheck.setCreateTime(DateUtils.getNowDate());
        return nxpMoldMaintenanceCheckMapper.insertNxpMoldMaintenanceCheck(nxpMoldMaintenanceCheck);
    }

    /**
     * 修改模具维护检查结果
     * 
     * @param nxpMoldMaintenanceCheck 模具维护检查结果
     * @return 结果
     */
    @Override
    public int updateNxpMoldMaintenanceCheck(NxpMoldMaintenanceCheck nxpMoldMaintenanceCheck)
    {
        nxpMoldMaintenanceCheck.setUpdateTime(DateUtils.getNowDate());
        return nxpMoldMaintenanceCheckMapper.updateNxpMoldMaintenanceCheck(nxpMoldMaintenanceCheck);
    }

    /**
     * 删除模具维护检查结果对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldMaintenanceCheckByIds(String ids)
    {
        return nxpMoldMaintenanceCheckMapper.deleteNxpMoldMaintenanceCheckByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除模具维护检查结果信息
     * 
     * @param id 模具维护检查结果ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldMaintenanceCheckById(Long id)
    {
        return nxpMoldMaintenanceCheckMapper.deleteNxpMoldMaintenanceCheckById(id);
    }
}
