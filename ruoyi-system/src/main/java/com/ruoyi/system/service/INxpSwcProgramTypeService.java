package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpSwcProgramType;

/**
 * 检查问题类型Service接口
 * 
 * @author YanCheng Liu
 * @date 2021-03-18
 */
public interface INxpSwcProgramTypeService 
{
    /**
     * 查询检查问题类型
     * 
     * @param id 检查问题类型ID
     * @return 检查问题类型
     */
    public NxpSwcProgramType selectNxpSwcProgramTypeById(Long id);

    /**
     * 查询检查问题类型列表
     * 
     * @param nxpSwcProgramType 检查问题类型
     * @return 检查问题类型集合
     */
    public List<NxpSwcProgramType> selectNxpSwcProgramTypeList(NxpSwcProgramType nxpSwcProgramType);

    /**
     * 新增检查问题类型
     * 
     * @param nxpSwcProgramType 检查问题类型
     * @return 结果
     */
    public int insertNxpSwcProgramType(NxpSwcProgramType nxpSwcProgramType);

    /**
     * 修改检查问题类型
     * 
     * @param nxpSwcProgramType 检查问题类型
     * @return 结果
     */
    public int updateNxpSwcProgramType(NxpSwcProgramType nxpSwcProgramType);

    /**
     * 批量删除检查问题类型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpSwcProgramTypeByIds(String ids);

    /**
     * 删除检查问题类型信息
     * 
     * @param id 检查问题类型ID
     * @return 结果
     */
    public int deleteNxpSwcProgramTypeById(Long id);

    /**
     * 根据参数查询
     * @param nxpSwcProgramType
     * @return
     */
    public List<NxpSwcProgramType> selectNxpSwcProgramTypeByParam(NxpSwcProgramType nxpSwcProgramType);
}
