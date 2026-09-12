package com.ruoyi.system.mapper;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.NxpMoldType;
import com.ruoyi.system.domain.NxpMoldTypeComponent;

/**
 * 部件种类Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2023-10-06
 */
public interface NxpMoldTypeMapper 
{
    /**
     * 查询部件种类
     * 
     * @param id 部件种类ID
     * @return 部件种类
     */
    public NxpMoldType selectNxpMoldTypeById(Long id);

    /**
     * 查询部件种类列表
     * 
     * @param nxpMoldType 部件种类
     * @return 部件种类集合
     */
    public List<NxpMoldType> selectNxpMoldTypeList(NxpMoldType nxpMoldType);

    /**
     * 新增部件种类
     * 
     * @param nxpMoldType 部件种类
     * @return 结果
     */
    public int insertNxpMoldType(NxpMoldType nxpMoldType);

    /**
     * 修改部件种类
     * 
     * @param nxpMoldType 部件种类
     * @return 结果
     */
    public int updateNxpMoldType(NxpMoldType nxpMoldType);

    /**
     * 删除部件种类
     * 
     * @param id 部件种类ID
     * @return 结果
     */
    public int deleteNxpMoldTypeById(Long id);

    /**
     * 批量删除部件种类
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMoldTypeByIds(String[] ids);

    /**
     * 批量删除模具部件
     * 
     * @param customerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMoldTypeComponentByMoldTypeIds(String[] ids);
    
    /**
     * 批量新增模具部件
     * 
     * @param nxpMoldTypeComponentList 模具部件列表
     * @return 结果
     */
    public int batchNxpMoldTypeComponent(List<NxpMoldTypeComponent> nxpMoldTypeComponentList);
    

    /**
     * 通过部件种类ID删除模具部件信息
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteNxpMoldTypeComponentByMoldTypeId(Long id);

    int insertNxpMoldTypeComponent(NxpMoldTypeComponent nxpMoldTypeComponent);

    int updateNxpMoldTypeComponent(NxpMoldTypeComponent nxpMoldTypeComponent);

    List<NxpMoldTypeComponent> getByMoldTypeId(NxpMoldTypeComponent nxpMoldTypeComponent);

    NxpMoldTypeComponent getByComponentId(Long componentId);

    NxpMoldType selectNxpMoldTypeByMachineModel(String machineModel);
}
