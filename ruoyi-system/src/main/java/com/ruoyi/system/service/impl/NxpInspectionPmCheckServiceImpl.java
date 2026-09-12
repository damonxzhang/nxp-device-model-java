package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.DTO.PmCheckDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpInspectionPmCheckMapper;
import com.ruoyi.system.domain.NxpInspectionPmCheck;
import com.ruoyi.system.service.INxpInspectionPmCheckService;
import com.ruoyi.common.core.text.Convert;

/**
 * 周PM复查Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionPmCheckServiceImpl implements INxpInspectionPmCheckService 
{
    @Autowired
    private NxpInspectionPmCheckMapper nxpInspectionPmCheckMapper;

    /**
     * 查询周PM复查
     * 
     * @param id 周PM复查ID
     * @return 周PM复查
     */
    @Override
    public NxpInspectionPmCheck selectNxpInspectionPmCheckById(Long id)
    {
        return nxpInspectionPmCheckMapper.selectNxpInspectionPmCheckById(id);
    }

    /**
     * 查询周PM复查列表
     * 
     * @param nxpInspectionPmCheck 周PM复查
     * @return 周PM复查
     */
    @Override
    public List<NxpInspectionPmCheck> selectNxpInspectionPmCheckList(NxpInspectionPmCheck nxpInspectionPmCheck)
    {
        return nxpInspectionPmCheckMapper.selectNxpInspectionPmCheckList(nxpInspectionPmCheck);
    }

    /**
     * 新增周PM复查
     * 
     * @param nxpInspectionPmCheck 周PM复查
     * @return 结果
     */
    @Override
    public int insertNxpInspectionPmCheck(NxpInspectionPmCheck nxpInspectionPmCheck)
    {
        return nxpInspectionPmCheckMapper.insertNxpInspectionPmCheck(nxpInspectionPmCheck);
    }

    /**
     * 修改周PM复查
     * 
     * @param nxpInspectionPmCheck 周PM复查
     * @return 结果
     */
    @Override
    public int updateNxpInspectionPmCheck(NxpInspectionPmCheck nxpInspectionPmCheck)
    {
        return nxpInspectionPmCheckMapper.updateNxpInspectionPmCheck(nxpInspectionPmCheck);
    }

    /**
     * 删除周PM复查对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPmCheckByIds(String ids)
    {
        return nxpInspectionPmCheckMapper.deleteNxpInspectionPmCheckByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除周PM复查信息
     * 
     * @param id 周PM复查ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPmCheckById(Long id)
    {
        return nxpInspectionPmCheckMapper.deleteNxpInspectionPmCheckById(id);
    }

    @Override
    public NxpInspectionPmCheck selectNxpInspectionPmByPmId(Long id) {
        return nxpInspectionPmCheckMapper.selectNxpInspectionPmByPmId(id);
    }

//    @Override
//    public List<NxpInspectionPmCheck> selectNxpInspectionPmCheckByPmId(NxpInspectionPmCheck nxpInspectionPmCheck) {
//        return nxpInspectionPmCheckMapper.getPmCheckFile(nxpInspectionPmCheck);
//    }
}
