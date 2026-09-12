package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionRevelantPeople;

/**
 * 设备相关人员Mapper接口
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
public interface NxpInspectionRevelantPeopleMapper 
{
    /**
     * 查询设备相关人员
     * 
     * @param id 设备相关人员ID
     * @return 设备相关人员
     */
    public NxpInspectionRevelantPeople selectNxpInspectionRevelantPeopleById(Long id);

    /**
     * 查询设备相关人员列表
     * 
     * @param nxpInspectionRevelantPeople 设备相关人员
     * @return 设备相关人员集合
     */
    public List<NxpInspectionRevelantPeople> selectNxpInspectionRevelantPeopleList(NxpInspectionRevelantPeople nxpInspectionRevelantPeople);

    /**
     * 新增设备相关人员
     * 
     * @param nxpInspectionRevelantPeople 设备相关人员
     * @return 结果
     */
    public int insertNxpInspectionRevelantPeople(NxpInspectionRevelantPeople nxpInspectionRevelantPeople);

    /**
     * 修改设备相关人员
     * 
     * @param nxpInspectionRevelantPeople 设备相关人员
     * @return 结果
     */
    public int updateNxpInspectionRevelantPeople(NxpInspectionRevelantPeople nxpInspectionRevelantPeople);

    /**
     * 删除设备相关人员
     * 
     * @param id 设备相关人员ID
     * @return 结果
     */
    public int deleteNxpInspectionRevelantPeopleById(Long id);

    /**
     * 批量删除设备相关人员
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionRevelantPeopleByIds(String[] ids);
}
