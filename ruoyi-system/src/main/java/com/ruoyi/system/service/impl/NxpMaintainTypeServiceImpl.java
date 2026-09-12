package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpMaintainTypeMapper;
import com.ruoyi.system.domain.NxpMaintainType;
import com.ruoyi.system.service.INxpMaintainTypeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 设备问题类型Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2021-03-04
 */
@Service
public class NxpMaintainTypeServiceImpl implements INxpMaintainTypeService 
{
    @Autowired
    private NxpMaintainTypeMapper nxpMaintainTypeMapper;

    /**
     * 查询设备问题类型
     * 
     * @param id 设备问题类型ID
     * @return 设备问题类型
     */
    @Override
    public NxpMaintainType selectNxpMaintainTypeById(Long id)
    {
        return nxpMaintainTypeMapper.selectNxpMaintainTypeById(id);
    }

    /**
     * 查询设备问题类型列表
     * 
     * @param nxpMaintainType 设备问题类型
     * @return 设备问题类型
     */
    @Override
    public List<NxpMaintainType> selectNxpMaintainTypeList(NxpMaintainType nxpMaintainType)
    {
        return nxpMaintainTypeMapper.selectNxpMaintainTypeList(nxpMaintainType);
    }

    /**
     * 新增设备问题类型
     * 
     * @param nxpMaintainType 设备问题类型
     * @return 结果
     */
    @Override
    public int insertNxpMaintainType(NxpMaintainType nxpMaintainType)
    {
        return nxpMaintainTypeMapper.insertNxpMaintainType(nxpMaintainType);
    }

    /**
     * 修改设备问题类型
     * 
     * @param nxpMaintainType 设备问题类型
     * @return 结果
     */
    @Override
    public int updateNxpMaintainType(NxpMaintainType nxpMaintainType)
    {
        return nxpMaintainTypeMapper.updateNxpMaintainType(nxpMaintainType);
    }

    /**
     * 删除设备问题类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpMaintainTypeByIds(String ids)
    {
        return nxpMaintainTypeMapper.deleteNxpMaintainTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备问题类型信息
     * 
     * @param id 设备问题类型ID
     * @return 结果
     */
    @Override
    public int deleteNxpMaintainTypeById(Long id)
    {
        return nxpMaintainTypeMapper.deleteNxpMaintainTypeById(id);
    }
}
