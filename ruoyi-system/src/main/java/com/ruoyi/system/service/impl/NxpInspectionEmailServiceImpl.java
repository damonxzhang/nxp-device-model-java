package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NxpInspectionEmailMapper;
import com.ruoyi.system.domain.NxpInspectionEmail;
import com.ruoyi.system.service.INxpInspectionEmailService;
import com.ruoyi.common.core.text.Convert;

/**
 * 设备维修邮件名单Service业务层处理
 * 
 * @author YanCheng Liu
 * @date 2021-08-30
 */
@Service
@DataSource(value = DataSourceType.SLAVE)

public class NxpInspectionEmailServiceImpl implements INxpInspectionEmailService 
{
    @Autowired
    private NxpInspectionEmailMapper nxpInspectionEmailMapper;

    /**
     * 查询设备维修邮件名单
     * 
     * @param id 设备维修邮件名单ID
     * @return 设备维修邮件名单
     */
    @Override
    public NxpInspectionEmail selectNxpInspectionEmailById(Long id)
    {
        return nxpInspectionEmailMapper.selectNxpInspectionEmailById(id);
    }

    /**
     * 查询设备维修邮件名单列表
     * 
     * @param nxpInspectionEmail 设备维修邮件名单
     * @return 设备维修邮件名单
     */
    @Override
    public List<NxpInspectionEmail> selectNxpInspectionEmailList(NxpInspectionEmail nxpInspectionEmail)
    {
        return nxpInspectionEmailMapper.selectNxpInspectionEmailList(nxpInspectionEmail);
    }

    /**
     * 新增设备维修邮件名单
     * 
     * @param nxpInspectionEmail 设备维修邮件名单
     * @return 结果
     */
    @Override
    public int insertNxpInspectionEmail(NxpInspectionEmail nxpInspectionEmail)
    {
        return nxpInspectionEmailMapper.insertNxpInspectionEmail(nxpInspectionEmail);
    }

    /**
     * 修改设备维修邮件名单
     * 
     * @param nxpInspectionEmail 设备维修邮件名单
     * @return 结果
     */
    @Override
    public int updateNxpInspectionEmail(NxpInspectionEmail nxpInspectionEmail)
    {
        return nxpInspectionEmailMapper.updateNxpInspectionEmail(nxpInspectionEmail);
    }

    /**
     * 删除设备维修邮件名单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionEmailByIds(String ids)
    {
        return nxpInspectionEmailMapper.deleteNxpInspectionEmailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备维修邮件名单信息
     * 
     * @param id 设备维修邮件名单ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionEmailById(Long id)
    {
        return nxpInspectionEmailMapper.deleteNxpInspectionEmailById(id);
    }
}
