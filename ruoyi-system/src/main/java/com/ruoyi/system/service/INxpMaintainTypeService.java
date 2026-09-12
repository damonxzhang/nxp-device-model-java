package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpMaintainType;

/**
 * 设备问题类型Service接口
 * 
 * @author YanCheng Liu
 * @date 2021-03-04
 */
public interface INxpMaintainTypeService 
{
    /**
     * 查询设备问题类型
     * 
     * @param id 设备问题类型ID
     * @return 设备问题类型
     */
    public NxpMaintainType selectNxpMaintainTypeById(Long id);

    /**
     * 查询设备问题类型列表
     * 
     * @param nxpMaintainType 设备问题类型
     * @return 设备问题类型集合
     */
    public List<NxpMaintainType> selectNxpMaintainTypeList(NxpMaintainType nxpMaintainType);

    /**
     * 新增设备问题类型
     * 
     * @param nxpMaintainType 设备问题类型
     * @return 结果
     */
    public int insertNxpMaintainType(NxpMaintainType nxpMaintainType);

    /**
     * 修改设备问题类型
     * 
     * @param nxpMaintainType 设备问题类型
     * @return 结果
     */
    public int updateNxpMaintainType(NxpMaintainType nxpMaintainType);

    /**
     * 批量删除设备问题类型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpMaintainTypeByIds(String ids);

    /**
     * 删除设备问题类型信息
     * 
     * @param id 设备问题类型ID
     * @return 结果
     */
    public int deleteNxpMaintainTypeById(Long id);
}
