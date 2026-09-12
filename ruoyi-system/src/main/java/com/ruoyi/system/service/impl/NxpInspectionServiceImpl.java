package com.ruoyi.system.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.service.INxpInspectionService;
import com.ruoyi.common.core.text.Convert;

/**
 * 待处理设备Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionServiceImpl implements INxpInspectionService 
{
    @Autowired
    private NxpInspectionMapper nxpInspectionMapper;
    @Autowired
    private NxpFacilityMapper nxpFacilityMapper;
    @Autowired
    private NxpUserInfoMapper nxpUserInfoMapper;
    @Autowired
    private NxpInspectionQuestionTypeMapper nxpInspectionQuestionTypeMapper;
    @Autowired
    private NxpMachineProcessMapper nxpMachineProcessMapper;
    @Autowired
    private NxpMaintainTypeMapper nxpMaintainTypeMapper;

    /**
     * 查询待处理设备
     * 
     * @param id 待处理设备ID
     * @return 待处理设备
     */
    @Override
    public NxpInspection selectNxpInspectionById(Long id)
    {
        return nxpInspectionMapper.selectNxpInspectionById(id);
    }

    /**
     * 查询待处理设备列表
     * 
     * @param nxpInspection 待处理设备
     * @return 待处理设备
     */
    @Override
    public List<NxpInspection> selectNxpInspectionList(NxpInspection nxpInspection)
    {
        /*String repair_start_time = (String) nxpInspection.getParams().get("repair_start_time");
        String repair_end_time = (String) nxpInspection.getParams().get("repair_end_time");
        if (StringUtils.isNoneBlank(repair_end_time)){
            DateTime dateTime = DateUtil.parseDateTime(repair_end_time);
            long time = dateTime.getTime();
            nxpInspection.getParams().put("repair_end_time",time / 1000);
        }
        if (StringUtils.isNoneBlank(repair_start_time)){
            DateTime dateTime = DateUtil.parseDateTime(repair_start_time);
            long time = dateTime.getTime();
            nxpInspection.getParams().put("repair_start_time",time / 1000);
        }*/
        List<NxpInspection> nxpInspections = nxpInspectionMapper.selectNxpInspectionListNew(nxpInspection);
        for (NxpInspection inspection : nxpInspections) {
            // 1. 处理设备名称
            Long facilityId = inspection.getFacility_id();
            NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(facilityId);
            if (nxpFacility != null){
                inspection.setFacility_name(nxpFacility.getName());
                inspection.setFacilityNumber(nxpFacility.getNumber());
                String process = nxpFacility.getProcess();
                NxpMachineProcess machineProcess = nxpMachineProcessMapper.selectNxpMachineProcessById(Long.valueOf(process));
                if (machineProcess != null){
                    inspection.setFacilityProcess(machineProcess.getName());
                }
            }
            // 2. 设备相关人员
            String relevant_people = inspection.getRelevant_people();
            String relevantPeopleName = "";
            if (StringUtils.isNoneBlank(relevant_people)){
                if (relevant_people.indexOf(",") != -1){
                    String[] split = relevant_people.split(",");
                    for (String userId : split) {
                        NxpUserInfo userInfo = nxpUserInfoMapper.selectNxpUserInfoById(Long.valueOf(userId));
                        if (userInfo != null){
                            relevantPeopleName = relevantPeopleName + userInfo.getChinese_name() + ",";
                        }
                    }
                }else {
                    NxpUserInfo userInfo = nxpUserInfoMapper.selectNxpUserInfoById(Long.valueOf(relevant_people));
                    if (userInfo != null){
                        relevantPeopleName = userInfo.getChinese_name();
                    }
                }
            }
            if (StringUtils.isNoneBlank(relevantPeopleName)){
                if (relevantPeopleName.indexOf(",") != -1){
                    inspection.setRelevant_people_name(relevantPeopleName.substring(0,relevantPeopleName.length()-1));
                }else {
                    inspection.setRelevant_people_name(relevantPeopleName);
                }
            }
            // 3. 处理问题
            Integer question_type = inspection.getQuestion_type();
            NxpMaintainType nxpMaintainType = nxpMaintainTypeMapper.selectNxpMaintainTypeById(question_type.longValue());
            if (nxpMaintainType != null){
                inspection.setQuestionTypeName(nxpMaintainType.getName());
            }
            // 4. 负责人
            String head_user = inspection.getHead_user();
            NxpUserInfo userInfo = nxpUserInfoMapper.selectNxpUserInfoById(Long.valueOf(head_user));
            if (userInfo != null){
                inspection.setHead_user(userInfo.getChinese_name());
            }
            Date startTime = new Date();
            Date endTime = new Date();
            // 处理时间
            if (inspection.getCreated_at() != null){
                inspection.setCreateTime(new Date(inspection.getCreated_at()*1000));
                startTime = new Date(inspection.getCreated_at()*1000);
            }

            if (inspection.getUpdated_at() != null){
                inspection.setUpdateTime(new Date(inspection.getUpdated_at()*1000));
                endTime = new Date(inspection.getUpdated_at()*1000);
            }


            // 总时长
            if (inspection.getUpdated_at() != null && inspection.getCreated_at() != null){
                if (startTime == endTime)
                    inspection.setTotalTime("尚未结束");
                else
                    inspection.setTotalTime((((inspection.getUpdated_at() - inspection.getCreated_at()) ) / 60) + "分钟");

            }


            // 产线异常
            if (inspection.getOperate() == null || inspection.getOperate() == 0)
                inspection.setOperateName("未提交");
            else
                inspection.setOperateName("已提交");


            // 报修人
            NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(inspection.getUser_info_id());
            if (nxpUserInfo != null)
                inspection.setUser_info_name(nxpUserInfo.getChinese_name());

            // 问题类型
            NxpInspectionQuestionType nxpInspectionQuestionType = nxpInspectionQuestionTypeMapper.selectNxpInspectionQuestionTypeById(Long.valueOf(inspection.getQuestion_type()));
            if (nxpInspectionQuestionType != null)
                inspection.setQuestionTypeName(nxpInspectionQuestionType.getName());
        }
        return nxpInspections;
    }

    /**
     * 新增待处理设备
     * 
     * @param nxpInspection 待处理设备
     * @return 结果
     */
    @Override
    public int insertNxpInspection(NxpInspection nxpInspection)
    {
        return nxpInspectionMapper.insertNxpInspection(nxpInspection);
    }

    /**
     * 修改待处理设备
     * 
     * @param nxpInspection 待处理设备
     * @return 结果
     */
    @Override
    public int updateNxpInspection(NxpInspection nxpInspection)
    {
        return nxpInspectionMapper.updateNxpInspection(nxpInspection);
    }

    /**
     * 删除待处理设备对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionByIds(String ids)
    {
        return nxpInspectionMapper.deleteNxpInspectionByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除待处理设备信息
     * 
     * @param id 待处理设备ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionById(Long id)
    {
        return nxpInspectionMapper.deleteNxpInspectionById(id);
    }

    /**
     * 查询待处理设备列表(定时任务用)
     *
     * @param nxpInspection 待处理设备
     * @return 待处理设备集合
     */
    @Override
    public List<NxpInspection> selectNxpInspectionListByTiming(NxpInspection nxpInspection) {
        return nxpInspectionMapper.selectNxpInspectionListByTiming(nxpInspection);
    }
}
