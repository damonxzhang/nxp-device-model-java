package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NxpInspectionEmail;

/**
 * 设备维修邮件名单Mapper接口
 * 
 * @author YanCheng Liu
 * @date 2021-08-30
 */
public interface NxpInspectionEmailMapper 
{
    /**
     * 查询设备维修邮件名单
     * 
     * @param id 设备维修邮件名单ID
     * @return 设备维修邮件名单
     */
    public NxpInspectionEmail selectNxpInspectionEmailById(Long id);

    /**
     * 查询设备维修邮件名单列表
     * 
     * @param nxpInspectionEmail 设备维修邮件名单
     * @return 设备维修邮件名单集合
     */
    public List<NxpInspectionEmail> selectNxpInspectionEmailList(NxpInspectionEmail nxpInspectionEmail);

    /**
     * 新增设备维修邮件名单
     * 
     * @param nxpInspectionEmail 设备维修邮件名单
     * @return 结果
     */
    public int insertNxpInspectionEmail(NxpInspectionEmail nxpInspectionEmail);

    /**
     * 修改设备维修邮件名单
     * 
     * @param nxpInspectionEmail 设备维修邮件名单
     * @return 结果
     */
    public int updateNxpInspectionEmail(NxpInspectionEmail nxpInspectionEmail);

    /**
     * 删除设备维修邮件名单
     * 
     * @param id 设备维修邮件名单ID
     * @return 结果
     */
    public int deleteNxpInspectionEmailById(Long id);

    /**
     * 批量删除设备维修邮件名单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNxpInspectionEmailByIds(String[] ids);
}
