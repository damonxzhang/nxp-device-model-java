package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.NxpMoldType;
import com.ruoyi.system.domain.NxpMoldTypeComponent;

/**
 * 部件种类Service接口
 * 
 * @author YanCheng Liu
 * @date 2023-10-06
 */
public interface INxpMoldTypeService 
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
     * 批量删除部件种类
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMoldTypeByIds(String ids);

    /**
     * 删除部件种类信息
     * 
     * @param id 部件种类ID
     * @return 结果
     */
    public int deleteNxpMoldTypeById(Long id);

    List<NxpMoldTypeComponent> getByMoldTypeId(NxpMoldTypeComponent nxpMoldTypeComponent);

    NxpMoldTypeComponent selectByComponentId(Long componentId);

    List<NxpMoldType> selectNxpMoldTypeListAdmin(NxpMoldType nxpMoldType);

    NxpMoldType selectNxpMoldTypeByMachineModel(String machineModelId);
}
