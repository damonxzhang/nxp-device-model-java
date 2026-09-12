package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.config.datasource.DynamicDataSourceContextHolder;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.NxpMoldComponentAbnormalLogsMapper;
import com.ruoyi.system.mapper.NxpMoldMapper;
import com.ruoyi.system.mapper.NxpMoldTypeMapper;
import com.ruoyi.system.service.INxpMoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpMoldComponentMapper;
import com.ruoyi.system.service.INxpMoldComponentService;
import com.ruoyi.common.core.text.Convert;

import javax.annotation.Resource;

/**
 * 模具部件管理Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2023-06-28
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpMoldComponentServiceImpl implements INxpMoldComponentService 
{
    @Resource
    private NxpMoldComponentMapper nxpMoldComponentMapper;
    @Resource
    private NxpMoldMapper nxpMoldMapper;
    @Resource
    private NxpMoldTypeMapper nxpMoldTypeMapper;
    @Resource
    private NxpMoldComponentAbnormalLogsMapper nxpMoldComponentAbnormalLogsMapper;

    /**
     * 查询模具部件管理
     * 
     * @param id 模具部件管理ID
     * @return 模具部件管理
     */
    @Override
    public NxpMoldComponent selectNxpMoldComponentById(Long id)
    {
        return nxpMoldComponentMapper.selectNxpMoldComponentById(id);
    }

    /**
     * 查询模具部件管理列表
     * 
     * @param nxpMoldComponent 模具部件管理
     * @return 模具部件管理
     */
    @Override
    public List<NxpMoldComponent> selectNxpMoldComponentList(NxpMoldComponent nxpMoldComponent)
    {
        List<NxpMoldComponent> components = nxpMoldComponentMapper.selectNxpMoldComponentList(nxpMoldComponent);
        for (NxpMoldComponent component : components) {
            NxpMoldType nxpMoldType = nxpMoldTypeMapper.selectNxpMoldTypeById(component.getMoldTypeId());
            if (nxpMoldType != null){
                component.setMoldTypeName(nxpMoldType.getMoldTypeName());
            }
        }
        return components;
    }

    /**
     * 新增模具部件管理
     * 
     * @param nxpMoldComponent 模具部件管理
     * @return 结果
     */
    @Override
    public AjaxResult insertNxpMoldComponent(NxpMoldComponent nxpMoldComponent)
    {
        // 判断该模具是否已配置模具分类
        Long moldId = nxpMoldComponent.getMoldId();
        List<NxpMoldComponent> components = nxpMoldComponentMapper.selectNxpMoldComponentByMoldId(moldId);
        if (components.size() > 0){
            return AjaxResult.error("该模具已配置过模具种类");
        }
        NxpMold nxpMold = nxpMoldMapper.selectNxpMoldById(moldId);
        NxpMoldType nxpMoldType = nxpMoldTypeMapper.selectNxpMoldTypeById(nxpMoldComponent.getMoldTypeId());
        List<NxpMoldTypeComponent> nxpMoldTypeComponentList = nxpMoldType.getNxpMoldTypeComponentList();
        for (NxpMoldTypeComponent nxpMoldTypeComponent : nxpMoldTypeComponentList) {
            NxpMoldComponent moldComponent = new NxpMoldComponent();
            moldComponent.setMoldCode(nxpMold.getMoldCode());
            moldComponent.setMoldRmsCode(nxpMold.getMoldRmsCode());
            moldComponent.setMoldId(moldId);
            moldComponent.setMoldTypeId(nxpMoldType.getId());
            moldComponent.setMoldTypeName(nxpMoldType.getMoldTypeName());
            moldComponent.setStatus(1);
            moldComponent.setCreateTime(DateUtils.getNowDate());
            moldComponent.setComponentName(nxpMoldTypeComponent.getComponentName());
            moldComponent.setComponentId(nxpMoldTypeComponent.getComponentId());
            nxpMoldComponentMapper.insertNxpMoldComponent(moldComponent);
        }
        return AjaxResult.success();
    }

    /**
     * 修改模具部件管理
     * 
     * @param nxpMoldComponent 模具部件管理
     * @return 结果
     */
    @Override
    public int updateNxpMoldComponent(NxpMoldComponent nxpMoldComponent)
    {
        if (nxpMoldComponent.getMoldId() != null){
            NxpMold nxpMold = nxpMoldMapper.selectNxpMoldById(nxpMoldComponent.getMoldId());
            if (nxpMold != null){
                nxpMoldComponent.setMoldCode(nxpMold.getMoldCode());
                nxpMoldComponent.setMoldRmsCode(nxpMold.getMoldRmsCode());
            }
        }
        nxpMoldComponent.setUpdateTime(DateUtils.getNowDate());

        // 判断是否异常或正常
        NxpMoldComponent moldComponent = nxpMoldComponentMapper.selectNxpMoldComponentById(nxpMoldComponent.getId());
        if (nxpMoldComponent.getStatus() != moldComponent.getStatus()){
            NxpMoldComponentAbnormalLogs nxpMoldComponentAbnormalLogs = new NxpMoldComponentAbnormalLogs();
            if (nxpMoldComponent.getStatus() == 1){
                nxpMoldComponentAbnormalLogs.setLogType(4);
            }else {
                nxpMoldComponentAbnormalLogs.setLogType(3);
            }
            nxpMoldComponentAbnormalLogs.setComponentId(moldComponent.getComponentId());
            nxpMoldComponentAbnormalLogs.setComponentName(moldComponent.getComponentName());
            nxpMoldComponentAbnormalLogs.setMoldId(moldComponent.getMoldId());
            nxpMoldComponentAbnormalLogs.setMoldCode(moldComponent.getMoldCode());
            nxpMoldComponentAbnormalLogs.setRemark(nxpMoldComponent.getRemark());
            nxpMoldComponentAbnormalLogs.setCreateTime(DateUtils.getNowDate());
            nxpMoldComponentAbnormalLogsMapper.insertNxpMoldComponentAbnormalLogs(nxpMoldComponentAbnormalLogs);
        }

        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
        int i = nxpMoldComponentMapper.updateNxpMoldComponent(nxpMoldComponent);
        DynamicDataSourceContextHolder.clearDataSourceType();

        return i;
    }

    /**
     * 删除模具部件管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldComponentByIds(String ids)
    {
        NxpMoldComponent nxpMoldComponentResult = null;
        if (ids.contains(",")){
            String[] split = ids.split(",");
            String s = split[0];
            nxpMoldComponentResult = nxpMoldComponentMapper.selectNxpMoldComponentById(Long.valueOf(s));
        }else {
            nxpMoldComponentResult = nxpMoldComponentMapper.selectNxpMoldComponentById(Long.valueOf(ids));
        }
        NxpMoldComponent nxpMoldComponent = new NxpMoldComponent();
        nxpMoldComponent.setMoldId(nxpMoldComponentResult.getMoldId());
        nxpMoldComponent.setMoldTypeId(nxpMoldComponentResult.getMoldTypeId());
        nxpMoldComponent.setDelFlag("2");
        return nxpMoldComponentMapper.updateNxpMoldComponentByDel(nxpMoldComponent);
    }

    /**
     * 删除模具部件管理信息
     * 
     * @param id 模具部件管理ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldComponentById(Long id)
    {
        return nxpMoldComponentMapper.deleteNxpMoldComponentById(id);
    }

    /**
     * 根据模具编号查询
     * @param moldCode
     * @return
     */
    @Override
    public List<NxpMoldComponent> selectNxpMoldComponentByMoldCode(String moldCode) {
        return nxpMoldComponentMapper.selectNxpMoldComponentByMoldCode(moldCode);
    }

    /**
     * 根据模具id查询
     * @param moldId
     * @return
     */
    @Override
    public List<NxpMoldComponent> selectNxpMoldComponentByMoldId(Long moldId) {
        return nxpMoldComponentMapper.selectNxpMoldComponentByMoldId(moldId);
    }

    /**
     * 根据部件id查询
     * @param componentId
     * @return
     */
    @Override
    public NxpMoldComponent selectNxpMoldComponentByComponentId(Long componentId) {
        return nxpMoldComponentMapper.selectNxpMoldComponentByComponentId(componentId);
    }

    /**
     * 根据模具id和部件id查询
     * @param nxpMoldComponent
     * @return
     */
    @Override
    public NxpMoldComponent selectNxpMoldComponentByMoldIdAndComponentId(NxpMoldComponent nxpMoldComponent) {
        return nxpMoldComponentMapper.selectNxpMoldComponentByMoldIdAndComponentId(nxpMoldComponent);
    }

    @Override
    public int deleteNxpMoldComponentByMoldId(Long id) {
        return nxpMoldComponentMapper.deleteNxpMoldComponentByMoldId(id);
    }

    @Override
    public int insertNxpMoldComponentApi(NxpMoldComponent nxpMoldComponent) {
        return nxpMoldComponentMapper.insertNxpMoldComponent(nxpMoldComponent);
    }
}
