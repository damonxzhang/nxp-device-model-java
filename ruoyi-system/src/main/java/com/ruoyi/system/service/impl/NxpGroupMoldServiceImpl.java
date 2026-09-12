package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.service.INxpGroupMoldService;
import com.ruoyi.common.core.text.Convert;

import javax.annotation.Resource;

/**
 * 模具&检查组Service业务层处理
 *
 * @author YanCheng Liu
 * @date 2023-10-13
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpGroupMoldServiceImpl implements INxpGroupMoldService {
    @Resource
    private NxpGroupMoldMapper nxpGroupMoldMapper;
    @Resource
    private NxpMoldMaintenanceGroupMapper nxpMoldMaintenanceGroupMapper;
    @Resource
    private NxpMoldMapper nxpMoldMapper;
    @Resource
    private NxpMoldMaintenanceGroupBindMapper nxpMoldMaintenanceGroupBindMapper;

    /**
     * 查询模具&检查组
     *
     * @param id 模具&检查组ID
     * @return 模具&检查组
     */
    @Override
    public NxpGroupMold selectNxpGroupMoldById(Long id) {
        return nxpGroupMoldMapper.selectNxpGroupMoldById(id);
    }

    /**
     * 查询模具&检查组列表
     *
     * @param nxpGroupMold 模具&检查组
     * @return 模具&检查组
     */
    @Override
    public List<NxpGroupMold> selectNxpGroupMoldList(NxpGroupMold nxpGroupMold) {
        List<NxpGroupMold> nxpGroupMolds = nxpGroupMoldMapper.selectNxpGroupMoldList(nxpGroupMold);
        for (NxpGroupMold groupMold : nxpGroupMolds) {
            String moldIds = groupMold.getMoldIds();
            List<String> moldList = new ArrayList<>();
            if (moldIds.contains(",")) {
                String[] split = moldIds.split(",");
                moldList = Arrays.asList(split);
            } else {
                moldList.add(moldIds);
            }
            List<NxpMold> nxpMolds = nxpMoldMapper.selectByIdList(moldList);
            String moldCode = "";
            for (NxpMold nxpMold : nxpMolds) {
                moldCode = moldCode + nxpMold.getMoldCode() + ",";
            }
            groupMold.setMoldCode(moldCode.substring(0, moldCode.length() - 1));
        }
        return nxpGroupMolds;
    }

    /**
     * 新增模具&检查组
     *
     * @param nxpGroupMold 模具&检查组
     * @return 结果
     */
    @Override
    public AjaxResult insertNxpGroupMold(NxpGroupMold nxpGroupMold) {
        // 1. 先判断组
        List<Long> moldTypeIds = nxpMoldMaintenanceGroupMapper.selectMoldTypeIdByGroupId(nxpGroupMold.getGroupId());
        if (moldTypeIds != null && (moldTypeIds.size() != 1 && !moldTypeIds.contains(null))) {
            return AjaxResult.error("维护组别绑定模具种类有误，请检查");
        }
        // 2. 检查模具
        String moldIds = nxpGroupMold.getMoldIds();
        List<String> moldList = new ArrayList<>();
        if (moldIds.contains(",")) {
            String[] split = moldIds.split(",");
            moldList = Arrays.asList(split);
        } else {
            moldList.add(moldIds);
        }
        for (String moldIdStr : moldList) {
            List<Long> moldTypeIdList = nxpMoldMapper.selectMoldTypeIdByMoldId(moldIdStr);
            if (moldTypeIdList != null && moldTypeIdList.size() != 1) {
                return AjaxResult.error("模具绑定模具种类有误，请检查");
            }
        }
        nxpGroupMold.setCreateTime(DateUtils.getNowDate());
        Long groupId = nxpGroupMold.getGroupId();
        NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup = nxpMoldMaintenanceGroupMapper.selectNxpMoldMaintenanceGroupById(groupId);
        nxpGroupMold.setGroupName(nxpMoldMaintenanceGroup.getGroupName());
        nxpGroupMoldMapper.insertNxpGroupMold(nxpGroupMold);
        // 3. 插入到中间表
        for (String moldId : moldList) {
            NxpMoldMaintenanceGroupBind nxpMoldMaintenanceGroupBind = new NxpMoldMaintenanceGroupBind();
            nxpMoldMaintenanceGroupBind.setMoldMaintenanceGroupId(groupId);
            nxpMoldMaintenanceGroupBind.setMoldId(Long.valueOf(moldId));
            nxpMoldMaintenanceGroupBindMapper.insertNxpMoldMaintenanceGroupBind(nxpMoldMaintenanceGroupBind);
        }
        return AjaxResult.success();
    }

    /**
     * 修改模具&检查组
     *
     * @param nxpGroupMold 模具&检查组
     * @return 结果
     */
    @Override
    public AjaxResult updateNxpGroupMold(NxpGroupMold nxpGroupMold) {
        // 1. 先判断组
        List<Long> moldTypeIds = nxpMoldMaintenanceGroupMapper.selectMoldTypeIdByGroupId(nxpGroupMold.getGroupId());
        if (moldTypeIds != null && (moldTypeIds.size() != 1 && !moldTypeIds.contains(null))) {
            return AjaxResult.error("维护组别绑定模具种类有误，请检查");
        }
        // 2. 检查模具
        String moldIds = nxpGroupMold.getMoldIds();
        List<String> moldList = new ArrayList<>();
        if (moldIds.contains(",")) {
            String[] split = moldIds.split(",");
            moldList = Arrays.asList(split);
        } else {
            moldList.add(moldIds);
        }
        for (String moldIdStr : moldList) {
            List<Long> moldTypeIdList = nxpMoldMapper.selectMoldTypeIdByMoldId(moldIdStr);
            if (moldTypeIdList != null && moldTypeIdList.size() != 1) {
                return AjaxResult.error("模具绑定模具种类有误，请检查");
            }
        }
        nxpGroupMold.setUpdateTime(DateUtils.getNowDate());
        Long groupId = nxpGroupMold.getGroupId();
        NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup = nxpMoldMaintenanceGroupMapper.selectNxpMoldMaintenanceGroupById(groupId);
        nxpGroupMold.setGroupName(nxpMoldMaintenanceGroup.getGroupName());
        nxpGroupMoldMapper.updateNxpGroupMold(nxpGroupMold);
        // 3. 先删除在新增
        NxpMoldMaintenanceGroupBind nxpMoldMaintenanceGroupBind = new NxpMoldMaintenanceGroupBind();
        nxpMoldMaintenanceGroupBind.setMoldMaintenanceGroupId(groupId);
        nxpMoldMaintenanceGroupBindMapper.deleteNxpMoldMaintenanceGroupBindByParams(nxpMoldMaintenanceGroupBind);
        for (String moldId : moldList) {
            NxpMoldMaintenanceGroupBind nxpMoldMaintenanceGroupBindParams = new NxpMoldMaintenanceGroupBind();
            nxpMoldMaintenanceGroupBindParams.setMoldMaintenanceGroupId(groupId);
            nxpMoldMaintenanceGroupBindParams.setMoldId(Long.valueOf(moldId));
            nxpMoldMaintenanceGroupBindMapper.insertNxpMoldMaintenanceGroupBind(nxpMoldMaintenanceGroupBindParams);
        }
        return AjaxResult.success();
    }

    /**
     * 删除模具&检查组对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpGroupMoldByIds(String ids) {
        List<String> idList = new ArrayList<>();
        if (ids.contains(",")) {
            String[] split = ids.split(",");
            idList = Arrays.asList(split);
        } else {
            idList.add(ids);
        }
        for (String id : idList) {
            NxpGroupMold nxpGroupMold = nxpGroupMoldMapper.selectNxpGroupMoldById(Long.valueOf(id));
            NxpMoldMaintenanceGroupBind nxpMoldMaintenanceGroupBind = new NxpMoldMaintenanceGroupBind();
            nxpMoldMaintenanceGroupBind.setMoldMaintenanceGroupId(nxpGroupMold.getGroupId());
            nxpMoldMaintenanceGroupBindMapper.deleteNxpMoldMaintenanceGroupBindByParams(nxpMoldMaintenanceGroupBind);
        }
        return nxpGroupMoldMapper.deleteNxpGroupMoldByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除模具&检查组信息
     *
     * @param id 模具&检查组ID
     * @return 结果
     */
    @Override
    public int deleteNxpGroupMoldById(Long id) {
        return nxpGroupMoldMapper.deleteNxpGroupMoldById(id);
    }

    @Override
    public NxpGroupMold selectByMoldIds(String moldIds) {
        return nxpGroupMoldMapper.selectByMoldIds(moldIds);
    }
}
