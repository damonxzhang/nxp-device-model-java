package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.service.INxpInspectionPmService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 周PM检查Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-07
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionPmServiceImpl implements INxpInspectionPmService 
{
    @Autowired
    private NxpInspectionPmMapper nxpInspectionPmMapper;
    @Autowired
    private NxpFacilityMapper nxpFacilityMapper;
    @Autowired
    private NxpUserInfoMapper nxpUserInfoMapper;
    @Autowired
    private NxpInspectionPmFacilityMapper nxpInspectionPmFacilityMapper;
    @Autowired
    private NxpInspectionPmCheckMapper nxpInspectionPmCheckMapper;
    @Autowired
    private NxpInspectionPmGroupItemMapper nxpInspectionPmGroupItemMapper;

    /**
     * 查询周PM检查
     * 
     * @param id 周PM检查ID
     * @return 周PM检查
     */
    @Override
    public NxpInspectionPm selectNxpInspectionPmById(Long id)
    {
        return nxpInspectionPmMapper.selectNxpInspectionPmById(id);
    }

    /**
     * 查询周PM检查列表
     * 
     * @param nxpInspectionPm 周PM检查
     * @return 周PM检查
     */
    @Override
    public List<NxpInspectionPm> selectNxpInspectionPmList(NxpInspectionPm nxpInspectionPm)
    {
        List<NxpInspectionPm> nxpInspectionPms = nxpInspectionPmMapper.selectNxpInspectionPmList2(nxpInspectionPm);
        for (NxpInspectionPm inspectionPm : nxpInspectionPms) {
            // 设备名
            NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(inspectionPm.getFacility_id());
            if (nxpFacility != null){
                inspectionPm.setFacility_name(nxpFacility.getName());
            }
            // 检查人
            Long userInfo_id = inspectionPm.getUserInfo_id();
            NxpUserInfo userInfo = nxpUserInfoMapper.selectNxpUserInfoById(userInfo_id);
            if (userInfo != null){
                inspectionPm.setHead_user(userInfo.getChinese_name());
            }
            // 复查人
            Long reviewUserInfoId = inspectionPm.getReview_user_info_id();
            NxpUserInfo userInfo1 = nxpUserInfoMapper.selectNxpUserInfoById(reviewUserInfoId);
            if (userInfo1 != null){
                inspectionPm.setReviewUserName(userInfo1.getChinese_name());
            }
            // 创建时间
            if (inspectionPm.getCreated_at() != null){
                inspectionPm.setCreateTime(new Date(inspectionPm.getCreated_at() * 1000));
            }
            // 更新时间
            if (inspectionPm.getUpdated_at() != null){
                inspectionPm.setUpdateTime(new Date(inspectionPm.getUpdated_at() * 1000));
            }
            // 待处理人
            Long dealUserId = inspectionPm.getDeal_user_id();
            NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(dealUserId);
            if (nxpUserInfo != null){
                inspectionPm.setDealUserName(nxpUserInfo.getChinese_name());
            }
        }
        return nxpInspectionPms;
    }


    /**
     * 新增周PM检查
     * 
     * @param nxpInspectionPm 周PM检查
     * @return 结果
     */
    @Override
    public int insertNxpInspectionPm(NxpInspectionPm nxpInspectionPm)
    {
        return nxpInspectionPmMapper.insertNxpInspectionPm(nxpInspectionPm);
    }

    /**
     * 修改周PM检查
     * 
     * @param nxpFacility 设备
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNxpInspectionPm(NxpFacility nxpFacility)
    {
        // groupId和设备id查询nxp_inspection_pm_facility
        NxpInspectionPmFacility nxpInspectionPmFacility = new NxpInspectionPmFacility();
        nxpInspectionPmFacility.setFacility_id(nxpFacility.getId());
        List<NxpInspectionPmFacility> nxpInspectionPmFacilities = nxpInspectionPmFacilityMapper.selectNxpInspectionPmFacilityList(nxpInspectionPmFacility);
        int i = 0;
        if (nxpInspectionPmFacilities.isEmpty()){
            NxpInspectionPmFacility nxpInspectionPmFacility1 = new NxpInspectionPmFacility();
            nxpInspectionPmFacility1.setFacility_id(nxpFacility.getId());
            nxpInspectionPmFacility1.setGroup_id(Long.valueOf(nxpFacility.getGroupId()));
            i = nxpInspectionPmFacilityMapper.insertNxpInspectionPmFacility(nxpInspectionPmFacility1);
        }else {
            for (NxpInspectionPmFacility inspectionPmFacility : nxpInspectionPmFacilities) {
                // 判断groupId是否修改
                if (inspectionPmFacility.getGroup_id() != Long.valueOf(nxpFacility.getGroupId())){
                    // 表示修改了分组
                    inspectionPmFacility.setGroup_id(Long.valueOf(nxpFacility.getGroupId()));
                    i += nxpInspectionPmFacilityMapper.updateNxpInspectionPmFacility(inspectionPmFacility);
                }
            }
        }

        return i;
    }

    /**
     * 删除周PM检查对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPmByIds(String ids)
    {
        return nxpInspectionPmMapper.deleteNxpInspectionPmByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除周PM检查信息
     * 
     * @param id 周PM检查ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPmById(Long id)
    {
        return nxpInspectionPmMapper.deleteNxpInspectionPmById(id);
    }

    @Override
    public NxpInspectionPm selectNxpInspectionPmByfacilityId(Long facilityId) {
        return nxpInspectionPmMapper.selectNxpInspectionPmByfacilityId(facilityId);
    }

    @Override
    public List<NxpInspectionPm> selectNxpInspectionPmOverDue(NxpInspectionPm nxpInspectionPm) {
        return nxpInspectionPmMapper.selectNxpInspectionPmOverDue(nxpInspectionPm);
    }
}
