package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpSwcProgramType;
import com.ruoyi.system.domain.NxpSwcProgram;

/**
 * 检查问题类型Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2021-03-18
 */
public interface NxpSwcProgramTypeMapper 
{
    /**
     * 查询检查问题类型
     * 
     * @param id 检查问题类型ID
     * @return 检查问题类型
     */
    public NxpSwcProgramType selectNxpSwcProgramTypeById(Long id);
    public List<NxpSwcProgramType> selectNxpSwcProgramTypeByParam(NxpSwcProgramType nxpSwcProgramType);

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
     * 删除检查问题类型
     * 
     * @param id 检查问题类型ID
     * @return 结果
     */
    public int deleteNxpSwcProgramTypeById(Long id);

    /**
     * 批量删除检查问题类型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpSwcProgramTypeByIds(String[] ids);

    /**
     * 批量删除${subTable.functionName}
     * 
     * @param customerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpSwcProgramByTypeIds(String[] ids);
    
    /**
     * 批量新增${subTable.functionName}
     * 
     * @param nxpSwcProgramList ${subTable.functionName}列表
     * @return 结果
     */
    public int batchNxpSwcProgram(List<NxpSwcProgram> nxpSwcProgramList);
    

    /**
     * 通过检查问题类型ID删除${subTable.functionName}信息
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteNxpSwcProgramByTypeId(Long id);
}
