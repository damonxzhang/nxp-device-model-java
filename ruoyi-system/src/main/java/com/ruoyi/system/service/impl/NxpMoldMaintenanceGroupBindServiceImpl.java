package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.NxpMold;
import com.ruoyi.system.domain.NxpMoldMaintenanceGroup;
import com.ruoyi.system.mapper.NxpMoldMaintenanceGroupMapper;
import com.ruoyi.system.mapper.NxpMoldMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpMoldMaintenanceGroupBindMapper;
import com.ruoyi.system.domain.NxpMoldMaintenanceGroupBind;
import com.ruoyi.system.service.INxpMoldMaintenanceGroupBindService;
import com.ruoyi.common.core.text.Convert;

import javax.annotation.Resource;

/**
 * 模具检查组绑定Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpMoldMaintenanceGroupBindServiceImpl implements INxpMoldMaintenanceGroupBindService 
{
    @Resource
    private NxpMoldMaintenanceGroupBindMapper nxpMoldMaintenanceGroupBindMapper;
    @Resource
    private NxpMoldMapper nxpMoldMapper;
    @Resource
    private NxpMoldMaintenanceGroupMapper nxpMoldMaintenanceGroupMapper;

    /**
     * 查询模具检查组绑定
     * 
     * @param id 模具检查组绑定ID
     * @return 模具检查组绑定
     */
    @Override
    public NxpMoldMaintenanceGroupBind selectNxpMoldMaintenanceGroupBindById(Long id)
    {
        return nxpMoldMaintenanceGroupBindMapper.selectNxpMoldMaintenanceGroupBindById(id);
    }

    /**
     * 查询模具检查组绑定列表
     * 
     * @param nxpMoldMaintenanceGroupBind 模具检查组绑定
     * @return 模具检查组绑定
     */
    @Override
    public List<NxpMoldMaintenanceGroupBind> selectNxpMoldMaintenanceGroupBindList(NxpMoldMaintenanceGroupBind nxpMoldMaintenanceGroupBind)
    {
        List<NxpMoldMaintenanceGroupBind> nxpMoldMaintenanceGroupBinds = nxpMoldMaintenanceGroupBindMapper.selectNxpMoldMaintenanceGroupBindList(nxpMoldMaintenanceGroupBind);
        for (NxpMoldMaintenanceGroupBind moldMaintenanceGroupBind : nxpMoldMaintenanceGroupBinds) {
            Long moldId = moldMaintenanceGroupBind.getMoldId();
            NxpMold nxpMold = nxpMoldMapper.selectNxpMoldById(moldId);
            if (nxpMold != null){
                moldMaintenanceGroupBind.setMoldCode(nxpMold.getMoldCode());
            }
            Long moldMaintenanceGroupId = moldMaintenanceGroupBind.getMoldMaintenanceGroupId();
            NxpMoldMaintenanceGroup nxpMoldMaintenanceGroup = nxpMoldMaintenanceGroupMapper.selectNxpMoldMaintenanceGroupById(moldMaintenanceGroupId);
            if (nxpMoldMaintenanceGroup != null){
                moldMaintenanceGroupBind.setGroupName(nxpMoldMaintenanceGroup.getGroupName());
            }
        }
        return nxpMoldMaintenanceGroupBinds;
    }

    /**
     * 新增模具检查组绑定
     * 
     * @param nxpMoldMaintenanceGroupBind 模具检查组绑定
     * @return 结果
     */
    @Override
    public int insertNxpMoldMaintenanceGroupBind(NxpMoldMaintenanceGroupBind nxpMoldMaintenanceGroupBind)
    {
        return nxpMoldMaintenanceGroupBindMapper.insertNxpMoldMaintenanceGroupBind(nxpMoldMaintenanceGroupBind);
    }

    /**
     * 修改模具检查组绑定
     * 
     * @param nxpMoldMaintenanceGroupBind 模具检查组绑定
     * @return 结果
     */
    @Override
    public int updateNxpMoldMaintenanceGroupBind(NxpMoldMaintenanceGroupBind nxpMoldMaintenanceGroupBind)
    {
        return nxpMoldMaintenanceGroupBindMapper.updateNxpMoldMaintenanceGroupBind(nxpMoldMaintenanceGroupBind);
    }

    /**
     * 删除模具检查组绑定对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldMaintenanceGroupBindByIds(String ids)
    {
        return nxpMoldMaintenanceGroupBindMapper.deleteNxpMoldMaintenanceGroupBindByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除模具检查组绑定信息
     * 
     * @param id 模具检查组绑定ID
     * @return 结果
     */
    @Override
    public int deleteNxpMoldMaintenanceGroupBindById(Long id)
    {
        return nxpMoldMaintenanceGroupBindMapper.deleteNxpMoldMaintenanceGroupBindById(id);
    }

    /**
     * 根据模具id查询分组
     * @param moldId
     * @return
     */
    @Override
    public NxpMoldMaintenanceGroupBind selectNxpMoldMaintenanceGroupBindByMoldId(Long moldId) {
        return nxpMoldMaintenanceGroupBindMapper.selectNxpMoldMaintenanceGroupBindByMoldId(moldId);
    }
}
