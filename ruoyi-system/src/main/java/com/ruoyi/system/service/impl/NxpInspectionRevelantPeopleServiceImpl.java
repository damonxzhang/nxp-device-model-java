package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpInspectionRevelantPeopleMapper;
import com.ruoyi.system.domain.NxpInspectionRevelantPeople;
import com.ruoyi.system.service.INxpInspectionRevelantPeopleService;
import com.ruoyi.common.core.text.Convert;

/**
 * 设备相关人员Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionRevelantPeopleServiceImpl implements INxpInspectionRevelantPeopleService 
{
    @Autowired
    private NxpInspectionRevelantPeopleMapper nxpInspectionRevelantPeopleMapper;

    /**
     * 查询设备相关人员
     * 
     * @param id 设备相关人员ID
     * @return 设备相关人员
     */
    @Override
    public NxpInspectionRevelantPeople selectNxpInspectionRevelantPeopleById(Long id)
    {
        return nxpInspectionRevelantPeopleMapper.selectNxpInspectionRevelantPeopleById(id);
    }

    /**
     * 查询设备相关人员列表
     * 
     * @param nxpInspectionRevelantPeople 设备相关人员
     * @return 设备相关人员
     */
    @Override
    public List<NxpInspectionRevelantPeople> selectNxpInspectionRevelantPeopleList(NxpInspectionRevelantPeople nxpInspectionRevelantPeople)
    {
        return nxpInspectionRevelantPeopleMapper.selectNxpInspectionRevelantPeopleList(nxpInspectionRevelantPeople);
    }

    /**
     * 新增设备相关人员
     * 
     * @param nxpInspectionRevelantPeople 设备相关人员
     * @return 结果
     */
    @Override
    public int insertNxpInspectionRevelantPeople(NxpInspectionRevelantPeople nxpInspectionRevelantPeople)
    {
        return nxpInspectionRevelantPeopleMapper.insertNxpInspectionRevelantPeople(nxpInspectionRevelantPeople);
    }

    /**
     * 修改设备相关人员
     * 
     * @param nxpInspectionRevelantPeople 设备相关人员
     * @return 结果
     */
    @Override
    public int updateNxpInspectionRevelantPeople(NxpInspectionRevelantPeople nxpInspectionRevelantPeople)
    {
        return nxpInspectionRevelantPeopleMapper.updateNxpInspectionRevelantPeople(nxpInspectionRevelantPeople);
    }

    /**
     * 删除设备相关人员对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionRevelantPeopleByIds(String ids)
    {
        return nxpInspectionRevelantPeopleMapper.deleteNxpInspectionRevelantPeopleByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备相关人员信息
     * 
     * @param id 设备相关人员ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionRevelantPeopleById(Long id)
    {
        return nxpInspectionRevelantPeopleMapper.deleteNxpInspectionRevelantPeopleById(id);
    }
}
