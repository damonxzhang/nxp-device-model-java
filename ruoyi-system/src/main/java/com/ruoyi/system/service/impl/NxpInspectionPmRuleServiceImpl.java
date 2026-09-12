package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.service.INxpInspectionPmRuleService;
import com.ruoyi.common.core.text.Convert;

/**
 * 周PM检查规则Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-03-19
 */
@Service
@DataSource(value = DataSourceType.SLAVE)
public class NxpInspectionPmRuleServiceImpl implements INxpInspectionPmRuleService 
{
    @Autowired
    private NxpInspectionPmRuleMapper nxpInspectionPmRuleMapper;
    @Autowired
    private NxpUserInfoMapper nxpUserInfoMapper;
    @Autowired
    private NxpFacilityMapper nxpFacilityMapper;
    @Autowired
    private NxpInspectionPmFacilityMapper nxpInspectionPmFacilityMapper;
    @Autowired
    private NxpInspectionPmGroupMapper nxpInspectionPmGroupMapper;
    @Autowired
    private NxpInspectionPmMapper nxpInspectionPmMapper;
    @Autowired
    private NxpMachineProcessMapper nxpMachineProcessMapper;

    /**
     * 查询周PM检查规则
     * 
     * @param id 周PM检查规则ID
     * @return 周PM检查规则
     */
    @Override
    public NxpInspectionPmRule selectNxpInspectionPmRuleById(Long id)
    {
        return nxpInspectionPmRuleMapper.selectNxpInspectionPmRuleById(id);
    }

    /**
     * 查询周PM检查规则列表
     * 
     * @param nxpInspectionPmRule 周PM检查规则
     * @return 周PM检查规则
     */
    @Override
    public List<NxpInspectionPmRule> selectNxpInspectionPmRuleList(NxpInspectionPmRule nxpInspectionPmRule)
    {

        if (nxpInspectionPmRule.getCycleShow() != null){
            nxpInspectionPmRule.setCycle(BigDecimal.valueOf(nxpInspectionPmRule.getCycleShow()*10));
        }

        nxpInspectionPmRule.setType(1L);
        List<NxpInspectionPmRule> nxpInspectionPmRules = nxpInspectionPmRuleMapper.selectNxpInspectionPmRuleList1(nxpInspectionPmRule);
        for (NxpInspectionPmRule inspectionPmRule : nxpInspectionPmRules) {
            String facility_id = inspectionPmRule.getFacility_id();

            NxpFacility nxpFacility1 = nxpFacilityMapper.selectNxpFacilityById(Long.valueOf(facility_id));
            if (nxpFacility1.getReview_user() != null){
                NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(nxpFacility1.getReview_user());
                if (nxpUserInfo != null){
                    inspectionPmRule.setReviewUserName(nxpUserInfo.getChinese_name());
                }

                NxpInspectionPmFacility nxpInspectionPmFacility = new NxpInspectionPmFacility();
                nxpInspectionPmFacility.setFacility_id(nxpFacility1.getId());
                List<NxpInspectionPmFacility> nxpInspectionPmFacilities = nxpInspectionPmFacilityMapper.selectNxpInspectionPmFacilityListByFacilityId(nxpInspectionPmFacility);
                if (nxpInspectionPmFacilities != null){
                    for (NxpInspectionPmFacility inspectionPmFacility : nxpInspectionPmFacilities) {
                        if (inspectionPmFacility.getGroup_id() != null){
                            NxpInspectionPmGroup nxpInspectionPmGroup = nxpInspectionPmGroupMapper.selectNxpInspectionPmGroupById(inspectionPmFacility.getGroup_id());
                            if (nxpInspectionPmGroup != null)
                                inspectionPmRule.setGroupName(nxpInspectionPmGroup.getName());
                        }
                    }
                }


            }

            // 查询设备编号
            String facilityNumber = "";
            if (facility_id.indexOf(",") != -1){
                // 多个
                String[] split = facility_id.split(",");
                List<NxpFacility> nxpFacilityList = nxpFacilityMapper.selectNxpFacilityByIds(split);
                for (NxpFacility nxpFacility : nxpFacilityList) {
                    facilityNumber = facilityNumber + nxpFacility.getNumber() + ",";
                }
            }else {
                // 单个
                NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(Long.valueOf(facility_id));
                if (nxpFacility != null){
                    facilityNumber = nxpFacility.getNumber();
                }
            }
            if (facilityNumber.indexOf(",") != -1){
                inspectionPmRule.setFacilityNumber(facilityNumber.substring(0,facilityNumber.length() - 1));
            }else {
                inspectionPmRule.setFacilityNumber(facilityNumber);
            }
            Long user_id = inspectionPmRule.getUser_id();
            NxpUserInfo userInfo = nxpUserInfoMapper.selectNxpUserInfoById(Long.valueOf(user_id));
            if (userInfo != null){
                inspectionPmRule.setUserName(userInfo.getChinese_name());
            }

//            修改显示周期
            inspectionPmRule.setCycleShow(inspectionPmRule.getCycle().floatValue()/10);

//            设置可以编辑的人
            if (inspectionPmRule.getFacility_id() != null){
                NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(Long.valueOf(inspectionPmRule.getFacility_id()));
                if (nxpFacility != null){
                    NxpMachineProcess nxpMachineProcess = nxpMachineProcessMapper.selectNxpMachineProcessById(Long.valueOf(nxpFacility.getProcess()));
                    if (nxpMachineProcess != null){
                        inspectionPmRule.setProcessOwner(nxpMachineProcess.getOwner());
                    }
                }
            }

        }
        return nxpInspectionPmRules;
    }

    @Override
    public List<NxpInspectionPmRule> selectNxpInspectionPmRuleListNew(NxpInspectionPmRule nxpInspectionPmRule) {
        return nxpInspectionPmRuleMapper.selectNxpInspectionPmRuleListNew(nxpInspectionPmRule);
    }

    @Override
    public List<NxpInspectionPmRule> selectNxpInspectionPmRuleListGroupBy(NxpInspectionPmRule nxpInspectionPmRule) {
        return nxpInspectionPmRuleMapper.selectNxpInspectionPmRuleListGroupBy(nxpInspectionPmRule);
    }

    /**
     * 新增周PM检查规则
     * 
     * @param nxpInspectionPmRule 周PM检查规则
     * @return 结果
     */
    @Override
    public int insertNxpInspectionPmRule(NxpInspectionPmRule nxpInspectionPmRule)
    {
//        先判断设备是否存在
        if (nxpInspectionPmRule.getFacility_id() != null){
            NxpInspectionPmRule nxpInspectionPmRule1 = new NxpInspectionPmRule();
            nxpInspectionPmRule1.setFacility_id(nxpInspectionPmRule.getFacility_id());
            List<NxpInspectionPmRule> nxpInspectionPmRules = nxpInspectionPmRuleMapper.selectNxpInspectionPmRuleList(nxpInspectionPmRule1);
            if (nxpInspectionPmRules.size() != 0)
                return 2;

            if (nxpInspectionPmRule.getGroup_id() != null){
                NxpInspectionPmFacility nxpInspectionPmFacility = new NxpInspectionPmFacility();
                nxpInspectionPmFacility.setFacility_id(Long.valueOf(nxpInspectionPmRule.getFacility_id()));
                List<NxpInspectionPmFacility> nxpInspectionPmFacilities = nxpInspectionPmFacilityMapper.selectNxpInspectionPmFacilityList(nxpInspectionPmFacility);
                if (nxpInspectionPmFacilities.size() != 0){
                    nxpInspectionPmFacility.setGroup_id(nxpInspectionPmRule.getGroup_id());
                    int i = nxpInspectionPmFacilityMapper.updateNxpInspectionPmFacilityByFacility(nxpInspectionPmFacility);
                }else {
                    nxpInspectionPmFacility.setGroup_id(nxpInspectionPmRule.getGroup_id());
                    int i = nxpInspectionPmFacilityMapper.insertNxpInspectionPmFacility(nxpInspectionPmFacility);
                }
            }
        }

        if (nxpInspectionPmRule.getCycle() != null){
            int j = nxpInspectionPmRule.getCycle().intValue()*10;
            nxpInspectionPmRule.setCycle(BigDecimal.valueOf(j));
        }
        nxpInspectionPmRule.setType((long)1);
        int i = nxpInspectionPmRuleMapper.insertNxpInspectionPmRule(nxpInspectionPmRule);

        if (nxpInspectionPmRule.getBeginTime() != null){
            NxpInspectionPm nxpInspectionPm = new NxpInspectionPm();
            nxpInspectionPm.setCheck_time(nxpInspectionPmRule.getBeginTime());
            nxpInspectionPm.setFacility_id(Long.valueOf(nxpInspectionPmRule.getFacility_id()));
            nxpInspectionPm.setType((long)1);
            nxpInspectionPm.setUserInfo_id(nxpInspectionPmRule.getUser_id());
            nxpInspectionPm.setStatus(1);
            nxpInspectionPmMapper.insertNxpInspectionPm(nxpInspectionPm);
        }

        return i;
    }

    /**
     * 修改周PM检查规则
     * 
     * @param nxpInspectionPmRule 周PM检查规则
     * @return 结果
     */
    @Override
    public int updateNxpInspectionPmRule(NxpInspectionPmRule nxpInspectionPmRule)
    {
//        int k = 0;
        if (nxpInspectionPmRule.getBeginTime() != null) {
            SimpleDateFormat  df=new SimpleDateFormat("yyyy-MM-dd");
            int compareTo = timeCompare(df.format(nxpInspectionPmRule.getBeginTime()),df.format(new Date()));

            if (compareTo < 1){
                return 2;
            }

            NxpInspectionPmRule nxpInspectionPmRule1 = new NxpInspectionPmRule();
            nxpInspectionPmRule1.setFacility_id(nxpInspectionPmRule.getFacility_id());
            List<NxpInspectionPmRule> nxpInspectionPmRules = nxpInspectionPmRuleMapper.selectNxpInspectionPmRuleList(nxpInspectionPmRule1);
            if (nxpInspectionPmRules != null){
                for (NxpInspectionPmRule inspectionPmRule : nxpInspectionPmRules) {
                    if (inspectionPmRule.getBeginTime() == null){
                        NxpInspectionPm nxpInspectionPm1 = new NxpInspectionPm();
                        nxpInspectionPm1.setCheck_time(nxpInspectionPmRule.getBeginTime());
                        nxpInspectionPm1.setFacility_id(Long.valueOf(nxpInspectionPmRule.getFacility_id()));
                        nxpInspectionPm1.setType((long)1);
                        nxpInspectionPm1.setUserInfo_id(nxpInspectionPmRule.getUser_id());
                        nxpInspectionPm1.setStatus(1);
                        nxpInspectionPmMapper.insertNxpInspectionPm(nxpInspectionPm1);
                    }else{
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

                        if (!sdf.format(inspectionPmRule.getBeginTime()).equals(sdf.format(nxpInspectionPmRule.getBeginTime()))){
                            // 删除最后一个，如果不是未完成就不用删除了
                            NxpInspectionPm nxpInspectionPm = nxpInspectionPmMapper.selectNxpInspectionPmLastOne(Long.valueOf(nxpInspectionPmRule.getFacility_id()));
                            if (nxpInspectionPm != null){
                                if (nxpInspectionPm.getStatus() == 1){
                                    nxpInspectionPmMapper.deleteNxpInspectionPmById(nxpInspectionPm.getId());
                                }
                            }

                            NxpInspectionPm nxpInspectionPm1 = new NxpInspectionPm();
                            nxpInspectionPm1.setCheck_time(nxpInspectionPmRule.getBeginTime());
                            nxpInspectionPm1.setFacility_id(Long.valueOf(nxpInspectionPmRule.getFacility_id()));
                            nxpInspectionPm1.setType((long)1);
                            nxpInspectionPm1.setUserInfo_id(nxpInspectionPmRule.getUser_id());
                            nxpInspectionPm1.setStatus(1);
                            nxpInspectionPmMapper.insertNxpInspectionPm(nxpInspectionPm1);
                        }
                    }
                }
            }
        }
        if (nxpInspectionPmRule.getCycle() != null){
            int j = nxpInspectionPmRule.getCycle().intValue()*10;
            nxpInspectionPmRule.setCycle(BigDecimal.valueOf(j));
        }
        int i = nxpInspectionPmRuleMapper.updateNxpInspectionPmRule(nxpInspectionPmRule);
        if (nxpInspectionPmRule.getGroup_id() != null) {
            NxpInspectionPmFacility nxpInspectionPmFacility1 = nxpInspectionPmFacilityMapper.selectNxpInspectionPmByFacilityId(Long.valueOf(nxpInspectionPmRule.getFacility_id()));
            if (nxpInspectionPmFacility1 != null){
                nxpInspectionPmFacility1.setFacility_id(Long.valueOf(nxpInspectionPmRule.getFacility_id()));
                nxpInspectionPmFacility1.setGroup_id(nxpInspectionPmRule.getGroup_id());
                nxpInspectionPmFacilityMapper.updateNxpInspectionPmFacilityByFacility(nxpInspectionPmFacility1);
            }else {
                NxpInspectionPmFacility nxpInspectionPmFacility = new NxpInspectionPmFacility();
                nxpInspectionPmFacility.setFacility_id(Long.valueOf(nxpInspectionPmRule.getFacility_id()));
                nxpInspectionPmFacility.setGroup_id(nxpInspectionPmRule.getGroup_id());
                nxpInspectionPmFacilityMapper.insertNxpInspectionPmFacility(nxpInspectionPmFacility);
            }
        }else {
            nxpInspectionPmFacilityMapper.deleteNxpInspectionPmFacilityByFacilityId(Long.valueOf(nxpInspectionPmRule.getFacility_id()));
        }
            int i1 = 1;
        if (nxpInspectionPmRule.getReview_user_id() != null){
            NxpFacility nxpFacility = new NxpFacility();
            nxpFacility.setId(Long.valueOf(nxpInspectionPmRule.getFacility_id()));
            nxpFacility.setReview_user(nxpInspectionPmRule.getReview_user_id());
            i1 = nxpFacilityMapper.updateNxpFacility(nxpFacility);
        }

        if (nxpInspectionPmRule.getBeginTime() != null){
            NxpInspectionPm nxpInspectionPm1 = new NxpInspectionPm();
            nxpInspectionPm1.setFacility_id(Long.valueOf(nxpInspectionPmRule.getFacility_id()));
            nxpInspectionPm1.setType(1L);
            List<NxpInspectionPm> nxpInspectionPms = nxpInspectionPmMapper.selectNxpInspectionPmList(nxpInspectionPm1);
            if (nxpInspectionPms == null){
                NxpInspectionPm nxpInspectionPm = new NxpInspectionPm();
                nxpInspectionPm.setCheck_time(nxpInspectionPmRule.getBeginTime());
                nxpInspectionPm.setFacility_id(Long.valueOf(nxpInspectionPmRule.getFacility_id()));
                nxpInspectionPm.setType((long)1);
                nxpInspectionPm.setUserInfo_id(nxpInspectionPmRule.getUser_id());
                nxpInspectionPm.setStatus(1);
                nxpInspectionPmMapper.insertNxpInspectionPm(nxpInspectionPm);
            }

        }

        if (i>0 && i1>0){
            return i;
        }else{
            return 1;
        }

    }

    /*时间比大小*/
    public static int timeCompare(String t1,String t2){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1=Calendar.getInstance();
        Calendar c2=Calendar.getInstance();
        try {
            c1.setTime(formatter.parse(t1));
            c2.setTime(formatter.parse(t2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result=c1.compareTo(c2);
        return result;
    }

    /**
     * 删除周PM检查规则对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPmRuleByIds(String ids)
    {
        return nxpInspectionPmRuleMapper.deleteNxpInspectionPmRuleByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除周PM检查规则信息
     * 
     * @param id 周PM检查规则ID
     * @return 结果
     */
    @Override
    public int deleteNxpInspectionPmRuleById(Long id)
    {
        return nxpInspectionPmRuleMapper.deleteNxpInspectionPmRuleById(id);
    }
}
