package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionQuestionType;

/**
 * 设备问题类型Service接口
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
public interface INxpInspectionQuestionTypeService 
{
    /**
     * 查询设备问题类型
     * 
     * @param id 设备问题类型ID
     * @return 设备问题类型
     */
    public NxpInspectionQuestionType selectNxpInspectionQuestionTypeById(Long id);

    /**
     * 查询设备问题类型列表
     * 
     * @param nxpInspectionQuestionType 设备问题类型
     * @return 设备问题类型集合
     */
    public List<NxpInspectionQuestionType> selectNxpInspectionQuestionTypeList(NxpInspectionQuestionType nxpInspectionQuestionType);

    /**
     * 新增设备问题类型
     * 
     * @param nxpInspectionQuestionType 设备问题类型
     * @return 结果
     */
    public int insertNxpInspectionQuestionType(NxpInspectionQuestionType nxpInspectionQuestionType);

    /**
     * 修改设备问题类型
     * 
     * @param nxpInspectionQuestionType 设备问题类型
     * @return 结果
     */
    public int updateNxpInspectionQuestionType(NxpInspectionQuestionType nxpInspectionQuestionType);

    /**
     * 批量删除设备问题类型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionQuestionTypeByIds(String ids);

    /**
     * 删除设备问题类型信息
     * 
     * @param id 设备问题类型ID
     * @return 结果
     */
    public int deleteNxpInspectionQuestionTypeById(Long id);
}
