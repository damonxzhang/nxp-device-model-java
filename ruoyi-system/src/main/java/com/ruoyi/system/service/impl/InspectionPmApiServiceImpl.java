package com.ruoyi.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.MultipartFileUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.DTO.NxpInspectionPmDTO;
import com.ruoyi.system.domain.DTO.PmCheckDTO;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Yancheng Liu
 * @create 2021-02-08 17:11
 */
@Service("IInspectionPmApiService")
@DataSource(value = DataSourceType.SLAVE)
public class InspectionPmApiServiceImpl implements IInspectionPmApiService {

    @Resource
    private NxpInspectionPmMapper nxpInspectionPmMapper;
    @Resource
    private NxpInspectionPmCheckMapper nxpInspectionPmCheckMapper;
    @Resource
    private NxpInspectionPmGroupItemMapper nxpInspectionPmGroupItemMapper;
    @Resource
    private NxpInspectionPmFileMapper nxpInspectionPmFileMapper;
    @Autowired
    private ServerConfig serverConfig;
    @Resource
    private NxpInspectionPmRuleMapper nxpInspectionPmRuleMapper;
    @Resource
    private NxpFacilityMapper nxpFacilityMapper;
    @Resource
    private NxpUserInfoMapper nxpUserInfoMapper;
    @Resource
    private NxpMachineModelMapper nxpMachineModelMapper;
    @Resource
    private NxpAppMessageMapper nxpAppMessageMapper;
    @Resource
    private INxpCheckListService nxpCheckListService;
    @Resource
    private INxpCheckListItemService nxpCheckListItemService;
    @Resource
    private INxpCheckContentService nxpCheckContentService;
    @Resource
    private INxpBindRelationshipService nxpBindRelationshipService;
    @Resource
    private INxpAppMessageService nxpAppMessageService;

    @Override
    public BaseVO getPmCheck(NxpInspectionPmCheck nxpInspectionPmCheck) {
        // 1. 校验参数
        if (nxpInspectionPmCheck.getPm_id() == null){
            return BaseVO.error("pm检查id为必传参数");
        }
        nxpInspectionPmCheck.setType((long)1);
        List<PmCheckDTO> pmCheckDTOS = nxpInspectionPmCheckMapper.getPmCheck(nxpInspectionPmCheck);
        PmCheckDTO result = new PmCheckDTO();
        if (pmCheckDTOS.size() > 0){
            result.setPm_id(nxpInspectionPmCheck.getPm_id().toString());
            result.setCheck_time(pmCheckDTOS.get(0).getCheck_time());
            result.setUser_info(pmCheckDTOS.get(0).getUser_info());
            List<NxpInspectionPmGroupItem> groupItems = new ArrayList<>();
            // 处理groupItem
            for (PmCheckDTO pmCheckDTO : pmCheckDTOS) {
                String group_item_id = pmCheckDTO.getGroup_item_id();
                NxpInspectionPmGroupItem nxpInspectionPmGroupItem = nxpInspectionPmGroupItemMapper.selectNxpInspectionPmGroupItemById(Long.valueOf(group_item_id));
                if (nxpInspectionPmGroupItem != null){
                    NxpInspectionPmFile nxpInspectionPmFile = new NxpInspectionPmFile();
                    nxpInspectionPmFile.setPm_check_id(Long.valueOf(pmCheckDTO.getCheck_id()));
                    nxpInspectionPmFile.setType(1L);
                    List<NxpInspectionPmFile> nxpInspectionPmFiles = nxpInspectionPmFileMapper.selectNxpInspectionPmFileList(nxpInspectionPmFile);
                    List<String> pics = new ArrayList<>();
                    for (NxpInspectionPmFile inspectionPmFile : nxpInspectionPmFiles) {
                        pics.add(inspectionPmFile.getFile_path());
                    }
                    nxpInspectionPmGroupItem.setGroup_item_pic(pics);
                    nxpInspectionPmGroupItem.setGroup_item_result(pmCheckDTO.getGroup_item_result());
                    groupItems.add(nxpInspectionPmGroupItem);
                }
            }
            result.setGroup_item(groupItems);
        }
        return BaseVO.success(result);
    }

    @Override
    public BaseVO getPmReview(NxpInspectionPmCheck nxpInspectionPmCheck) {
        // 1. 校验参数
        if (nxpInspectionPmCheck.getPm_id() == null){
            return BaseVO.error("pm检查id为必传参数");
        }
        nxpInspectionPmCheck.setType(2L);
        List<PmCheckDTO> pmCheckDTOS = nxpInspectionPmCheckMapper.getPmCheck(nxpInspectionPmCheck);
        PmCheckDTO result = new PmCheckDTO();
        if (pmCheckDTOS.size() > 0){
            result.setPm_id(nxpInspectionPmCheck.getPm_id().toString());
            result.setCheck_time(pmCheckDTOS.get(0).getCheck_time());
            result.setUser_info(pmCheckDTOS.get(0).getUser_info());
            List<NxpInspectionPmGroupItem> groupItems = new ArrayList<>();
            // 处理groupItem
            for (PmCheckDTO pmCheckDTO : pmCheckDTOS) {
                String group_item_id = pmCheckDTO.getGroup_item_id();
                NxpInspectionPmGroupItem nxpInspectionPmGroupItem = nxpInspectionPmGroupItemMapper.selectNxpInspectionPmGroupItemById(Long.valueOf(group_item_id));
                if (nxpInspectionPmGroupItem != null){
                    NxpInspectionPmFile nxpInspectionPmFile = new NxpInspectionPmFile();
                    nxpInspectionPmFile.setPm_check_id(Long.valueOf(pmCheckDTO.getCheck_id()));
                    nxpInspectionPmFile.setType(2L);
                    List<NxpInspectionPmFile> nxpInspectionPmFiles = nxpInspectionPmFileMapper.selectNxpInspectionPmFileList(nxpInspectionPmFile);
                    List<String> pics = new ArrayList<>();
                    for (NxpInspectionPmFile inspectionPmFile : nxpInspectionPmFiles) {
                        pics.add(inspectionPmFile.getFile_path());
                    }
                    nxpInspectionPmGroupItem.setGroup_item_pic(pics);
                    nxpInspectionPmGroupItem.setGroup_item_result(pmCheckDTO.getGroup_item_result());
                    groupItems.add(nxpInspectionPmGroupItem);
                }
            }
            result.setGroup_item(groupItems);
        }
        return BaseVO.success(result);
    }

    @Override
    public List<NxpInspectionPm> pmList(NxpInspectionPm nxpInspectionPm) {
        nxpInspectionPm.setCheck_time(new Date());
        List<NxpInspectionPm> list = nxpInspectionPmMapper.selectPmList(nxpInspectionPm);
        for (NxpInspectionPm inspectionPm : list) {

            String statusName = "";
            Integer status = inspectionPm.getStatus();
            if (status == 1){
                statusName = "待周PM";
            }else if (status == 2){
                statusName = "已完成";
            }else if (status == 3){
                statusName = "逾期";
            }
            inspectionPm.setStatus_name(statusName);


            String typeName = "";
            Long type = inspectionPm.getType();
            if (type == 1){
                typeName = "周PM";
            }else if (type == 2){
                typeName = "周PM复查";
            }
            inspectionPm.setType_name(typeName);


            if (inspectionPm.getUserInfo_id() != null){
                NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(inspectionPm.getUserInfo_id());
                if (nxpUserInfo != null){
                    inspectionPm.setUserName(nxpUserInfo.getChinese_name());
                }
            }

            if (inspectionPm.getReview_user_info_id() != null){
                NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(inspectionPm.getReview_user_info_id());
                if (nxpUserInfo != null){
                    inspectionPm.setReviewUserName(nxpUserInfo.getChinese_name());
                }
            }

            if (inspectionPm.getCheck_time() != null){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                inspectionPm.setCheckTime(sdf.format(inspectionPm.getCheck_time()));
            }

        }
        return list;
    }

    @Override
    public List<NxpInspectionPm> pmListByFacility(NxpInspectionPm nxpInspectionPm) {
        NxpInspectionPm nxpInspectionPm1 = new NxpInspectionPm();
        nxpInspectionPm1.setFacility_id(nxpInspectionPm.getFacility_id());
        nxpInspectionPm1.setType((long)1);
        List<NxpInspectionPm> nxpInspectionPms = nxpInspectionPmMapper.selectNxpInspectionPmList1(nxpInspectionPm1);

        for (NxpInspectionPm inspectionPm : nxpInspectionPms) {

            String statusName = "";
            Integer status = inspectionPm.getStatus();
            if (status == 1){
                statusName = "待检查";
            }else if (status == 2){
                statusName = "已完成";
            }else if (status == 3){
                statusName = "逾期";
            }
            inspectionPm.setStatus_name(statusName);


            String typeName = "";
            Long type = inspectionPm.getType();
            if (type == 1){
                typeName = "周PM";
            }else if (type == 2){
                typeName = "周PM复查";
            }
            inspectionPm.setType_name(typeName);


            if (inspectionPm.getUserInfo_id() != null){
                NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(inspectionPm.getUserInfo_id());
                if (nxpUserInfo != null){
                    inspectionPm.setUserName(nxpUserInfo.getChinese_name());
                }
            }

            inspectionPm.setCheck_time(inspectionPm.getReal_check_time());

        }

        return nxpInspectionPms;
    }

    @Override
    public List<NxpInspectionPm> pmReviewListByFacility(NxpInspectionPm nxpInspectionPm) {
        NxpInspectionPm nxpInspectionPm1 = new NxpInspectionPm();
        nxpInspectionPm1.setFacility_id(nxpInspectionPm.getFacility_id());
        nxpInspectionPm1.setType((long)2);
        List<NxpInspectionPm> nxpInspectionPms = nxpInspectionPmMapper.selectNxpInspectionPmList1(nxpInspectionPm1);

        for (NxpInspectionPm inspectionPm : nxpInspectionPms) {

            String statusName = "";
            Integer status = inspectionPm.getStatus();
            if (status == 1){
                statusName = "待复查";
            }else if (status == 2){
                statusName = "已完成";
            }else if (status == 3){
                statusName = "逾期";
            }
            inspectionPm.setStatus_name(statusName);

            String typeName = "";
            Long type = inspectionPm.getType();
            if (type == 1){
                typeName = "周PM";
            }else if (type == 2){
                typeName = "周PM复查";
            }
            inspectionPm.setType_name(typeName);

            if (inspectionPm.getReview_user_info_id() != null){
                NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(inspectionPm.getReview_user_info_id());
                if (nxpUserInfo != null){
                    inspectionPm.setReviewUserName(nxpUserInfo.getChinese_name());
                }
            }
        }

        return nxpInspectionPms;
    }

    @Override
    public NxpInspectionPmDTO pmInfo(NxpInspectionPm nxpInspectionPm) {
        NxpInspectionPmDTO nxpInspectionPmDTO = nxpInspectionPmMapper.pmInfo(nxpInspectionPm);
        if (nxpInspectionPmDTO != null){
            List<NxpInspectionPmGroupItem> list = new ArrayList<>();
            // 根据pmId查询check
            NxpInspectionPmCheck nxpInspectionPmCheck = new NxpInspectionPmCheck();
            nxpInspectionPmCheck.setPm_id(Long.valueOf(nxpInspectionPmDTO.getPm_id()));
//            if (nxpInspectionPmDTO.getPm_status().equalsIgnoreCase("0")){
//                nxpInspectionPmCheck.setType((long)1);
//            }else {
//                nxpInspectionPmCheck.setType((long)2);
//            }
            nxpInspectionPmCheck.setType((long)1);
            List<NxpInspectionPmCheck> nxpInspectionPmChecks = nxpInspectionPmCheckMapper.selectNxpInspectionPmCheckList(nxpInspectionPmCheck);
            for (NxpInspectionPmCheck inspectionPmCheck : nxpInspectionPmChecks) {
                NxpInspectionPmGroupItem nxpInspectionPmGroupItem = nxpInspectionPmGroupItemMapper.selectNxpInspectionPmGroupItemById(inspectionPmCheck.getGroup_item_id());
                if (nxpInspectionPmGroupItem != null){
                    List<String> pics = new ArrayList<>();
                    String fileId = inspectionPmCheck.getFile_id();
                    if (StringUtils.isNotBlank(fileId)){
                        if (fileId.indexOf(",") != -1){
                            String[] split = fileId.split(",");
                            List<NxpInspectionPmFile> nxpInspectionPmFiles = nxpInspectionPmFileMapper.selectNxpInspectionPmFileByIds(split);
                            for (NxpInspectionPmFile nxpInspectionPmFile : nxpInspectionPmFiles) {
                                String filePath = nxpInspectionPmFile.getFile_path();
                                pics.add(filePath);
                            }
                        }else {
                            NxpInspectionPmFile nxpInspectionPmFile = nxpInspectionPmFileMapper.selectNxpInspectionPmFileById(Long.valueOf(fileId));
                            if (nxpInspectionPmFile != null){
                                pics.add(nxpInspectionPmFile.getFile_path());
                            }
                        }
                    }
                    nxpInspectionPmGroupItem.setGroup_item_pic(pics);
                    list.add(nxpInspectionPmGroupItem);
                }
            }
            nxpInspectionPmDTO.setPm_name_list(list);
        }
        return nxpInspectionPmDTO;
    }

    @Override
    public BaseVO uploadFile(Picture picture) {
        String base64 = picture.getImg_picture();
        if (StringUtils.isBlank(base64)){
            return BaseVO.error("base64编码为空");
        }
        // 转换base64为MultipartFile
        MultipartFile multipartFile = MultipartFileUtils.base64ToMultipart(base64);
        NxpInspectionPmFile nxpInspectionFile = new NxpInspectionPmFile();
        try {
            Map<String, String> uploadFile = uploadFile(multipartFile);
            if (uploadFile != null){
                if (StringUtils.isNoneBlank(uploadFile.get("url"))){
                    nxpInspectionFile.setFile_path(uploadFile.get("url"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BaseVO.error("图片上传失败");
        }
        nxpInspectionFile.setCreateTime(new Date());
        nxpInspectionPmFileMapper.insertNxpInspectionPmFile(nxpInspectionFile);
        return BaseVO.success(nxpInspectionFile);
    }

    @Override
    @Transactional
    public BaseVO pmCheck(ParamsDTO paramsDTO) {
        // 1. 通过pm_id和group_item_id查询唯一pm
        Integer pmId = paramsDTO.getPm_id();
        NxpInspectionPm nxpInspectionPm = nxpInspectionPmMapper.selectNxpInspectionPmById(Long.valueOf(pmId));
        // 1.1 设置状态为待复查
        nxpInspectionPm.setStatus(2);

//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        nxpInspectionPm.setReal_check_time(new Date());
        nxpInspectionPmMapper.updateNxpInspectionPm(nxpInspectionPm);
        List<NxpInspectionPmGroupItem> group_item = paramsDTO.getGroup_item();
        for (NxpInspectionPmGroupItem nxpInspectionPmGroupItem : group_item) {
            Long groupItemId = nxpInspectionPmGroupItem.getId();
            NxpInspectionPmCheck pmCheck = new NxpInspectionPmCheck();
            pmCheck.setPm_id(Long.valueOf(pmId));
            pmCheck.setGroup_item_id(groupItemId);
            // type = 1 表示检查
            pmCheck.setType((long)1);
            // 2. 设置result的参数并更新
            NxpInspectionPmCheck nxpInspectionPmCheck = nxpInspectionPmCheckMapper.selectNxpInspectionPmByGroupItemIdAndPmId(pmCheck);
            nxpInspectionPmCheck.setCheck_time(new Date());
            nxpInspectionPmCheck.setUser_info_id(Long.valueOf(paramsDTO.getUser_id()));
            nxpInspectionPmCheck.setGroup_item_result(Long.valueOf(nxpInspectionPmGroupItem.getGroup_item_result()));
            String file_ids = nxpInspectionPmGroupItem.getFile_ids();
            nxpInspectionPmCheck.setFile_id(file_ids);
            nxpInspectionPmCheckMapper.updateNxpInspectionPmCheck(nxpInspectionPmCheck);
            //3. 处理pmfile
            if (StringUtils.isNoneBlank(file_ids)){
                if (file_ids.indexOf(",") != -1){
                    // 表示多个
                    String[] fileIds = file_ids.split(",");
                    List<NxpInspectionPmFile> files = nxpInspectionPmFileMapper.selectNxpInspectionPmFileByIds(fileIds);
                    for (NxpInspectionPmFile file : files) {
                        file.setPm_check_id(nxpInspectionPmCheck.getId());
                        file.setPm_id(Long.valueOf(pmId));
                        file.setType(1L);
                        nxpInspectionPmFileMapper.updateNxpInspectionPmFile(file);
                    }
                }else {
                    // 表示单个
                    NxpInspectionPmFile nxpInspectionPmFile = nxpInspectionPmFileMapper.selectNxpInspectionPmFileById(Long.valueOf(file_ids));
                    if (nxpInspectionPmFile != null){
                        nxpInspectionPmFile.setPm_check_id(nxpInspectionPmCheck.getId());
                        nxpInspectionPmFile.setPm_id(Long.valueOf(pmId));
                        nxpInspectionPmFile.setType(1L);
                        nxpInspectionPmFileMapper.updateNxpInspectionPmFile(nxpInspectionPmFile);
                    }
                }
            }

            NxpUserInfo userInfo = nxpUserInfoMapper.selectNxpUserInfoById(Long.valueOf(paramsDTO.getUser_id()));
            String serviceUserName = "";
            if (userInfo != null){
                serviceUserName = userInfo.getChinese_name();
            }
            NxpInspectionPmGroupItem inspectionPmGroupItem = nxpInspectionPmGroupItemMapper.selectNxpInspectionPmGroupItemById(groupItemId);
            if ("0".equals(inspectionPmGroupItem.getIs_check())){
                // 表示需要检查
                NxpCheckList nxpCheckList = new NxpCheckList();
                nxpCheckList.setSubmitterId(Long.valueOf(paramsDTO.getUser_id()));
                nxpCheckList.setSubmitterName(serviceUserName);
                nxpCheckList.setType(2L);
                nxpCheckList.setPmId(pmId.longValue());
                nxpCheckList.setPmGroupId(inspectionPmGroupItem.getGroup_id());
                nxpCheckList.setPmGroupItemId(groupItemId);
                nxpCheckList.setPmGroupItemStr(inspectionPmGroupItem.getItem_name());
                NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(nxpInspectionPm.getFacility_id());
                nxpCheckList.setFacilityId(nxpInspectionPm.getFacility_id());
                nxpCheckList.setFacilityNumber(nxpFacility.getNumber());
                nxpCheckList.setProcessId(Long.valueOf(nxpFacility.getProcess()));
                nxpCheckListService.insertNxpCheckList(nxpCheckList);
                // 1. 先拿出检查组
                Long checkGroupId = inspectionPmGroupItem.getCheck_group_id();
                // 2. 查询检查组下的所有检查项
                List<NxpCheckContent> nxpCheckContents = nxpCheckContentService.selectNxpCheckContentByCheckGroupId(checkGroupId);
                for (NxpCheckContent nxpCheckContent : nxpCheckContents) {
                    NxpCheckListItem nxpCheckListItem = new NxpCheckListItem();
                    nxpCheckListItem.setCheckListId(nxpCheckList.getId());
                    nxpCheckListItem.setCheckProjectId(nxpCheckContent.getId());
                    nxpCheckListItem.setCheckProjectName(nxpCheckContent.getCheckContent());
                    nxpCheckListItemService.insertNxpCheckListItem(nxpCheckListItem);
                }
            }
        }
        // todo 结束后，根据规则生成下一次检查
//        NxpInspectionPm nxpInspectionPm1 = new NxpInspectionPm();
//        nxpInspectionPm1.setStatus(1);
//        nxpInspectionPm1.setFacility_id(nxpInspectionPm.getFacility_id());
//        nxpInspectionPm1.setUserInfo_id(nxpInspectionPm.getUserInfo_id());
//        nxpInspectionPm1.setDeal_user_id(nxpInspectionPm.getDeal_user_id());
//        nxpInspectionPm1.setType(nxpInspectionPm.getType());
//
//        // 获取检查周期
//        NxpInspectionPmRule nxpInspectionPmRule = new NxpInspectionPmRule();
//        nxpInspectionPmRule.setFacility_id(nxpInspectionPm.getFacility_id().toString());
//        List<NxpInspectionPmRule> nxpInspectionPmRules = nxpInspectionPmRuleMapper.selectNxpInspectionPmRuleList(nxpInspectionPmRule);
//        int cycle = 0;
//        for (NxpInspectionPmRule inspectionPmRule : nxpInspectionPmRules) {
//            cycle = inspectionPmRule.getCycle().intValue() / 10;
//        }
//        Calendar c = Calendar.getInstance();
//        c.setTime(nxpInspectionPm.getCheck_time());
//        c.add(Calendar.DAY_OF_MONTH,cycle);//利用Calendar 实现 Date日期+1天  
//        nxpInspectionPm1.setCheck_time(c.getTime());
//
//        int i = nxpInspectionPmMapper.insertNxpInspectionPm(nxpInspectionPm1);


        return BaseVO.success();
    }

    @Override
    public BaseVO pmReview(PmReviewDTO pmReviewDTO) {
        // 1. 通过pm_id和group_item_id查询唯一pm
        Integer pmId = pmReviewDTO.getPm_id();
        NxpInspectionPm nxpInspectionPm = nxpInspectionPmMapper.selectNxpInspectionPmById(Long.valueOf(pmId));
        nxpInspectionPm.setStatus(2);
        nxpInspectionPm.setReal_check_time(new Date());
        nxpInspectionPmMapper.updateNxpInspectionPm(nxpInspectionPm);

        List<NxpInspectionPmGroupItem> review_item = pmReviewDTO.getGroup_item();
        for (NxpInspectionPmGroupItem nxpInspectionPmGroupItem : review_item) {
            Long groupItemId = nxpInspectionPmGroupItem.getId();
            NxpInspectionPmCheck pmCheck = new NxpInspectionPmCheck();
            pmCheck.setPm_id(Long.valueOf(pmId));
            pmCheck.setGroup_item_id(groupItemId);
            // type = 2 表示复查
            pmCheck.setType((long)2);
            // 2. 设置result的参数并更新
            NxpInspectionPmCheck nxpInspectionPmCheck = nxpInspectionPmCheckMapper.selectNxpInspectionPmByGroupItemIdAndPmId(pmCheck);
            nxpInspectionPmCheck.setGroup_item_result(Long.valueOf(nxpInspectionPmGroupItem.getGroup_item_result()));
            String file_ids = nxpInspectionPmGroupItem.getFile_ids();
            nxpInspectionPmCheck.setFile_id(file_ids);
            nxpInspectionPmCheck.setCheck_time(new Date());
            nxpInspectionPmCheck.setUser_info_id(Long.valueOf(pmReviewDTO.getUser_id()));
            nxpInspectionPmCheckMapper.updateNxpInspectionPmCheck(nxpInspectionPmCheck);
            // 3. 处理pmFile
            if (StringUtils.isNoneBlank(file_ids)){
                if (file_ids.indexOf(",") != -1){
                    // 表示多个
                    String[] fileIds = file_ids.split(",");
                    List<NxpInspectionPmFile> files = nxpInspectionPmFileMapper.selectNxpInspectionPmFileByIds(fileIds);
                    for (NxpInspectionPmFile file : files) {
                        file.setPm_check_id(nxpInspectionPmCheck.getId());
                        file.setPm_id(Long.valueOf(pmId));
                        file.setType((long)2);
                        nxpInspectionPmFileMapper.updateNxpInspectionPmFile(file);
                    }
                }else {
                    // 表示单个
                    NxpInspectionPmFile nxpInspectionPmFile = nxpInspectionPmFileMapper.selectNxpInspectionPmFileById(Long.valueOf(file_ids));
                    if (nxpInspectionPmFile != null){
                        nxpInspectionPmFile.setPm_check_id(nxpInspectionPmCheck.getId());
                        nxpInspectionPmFile.setPm_id(Long.valueOf(pmId));
                        nxpInspectionPmFile.setType((long)2);
                        nxpInspectionPmFileMapper.updateNxpInspectionPmFile(nxpInspectionPmFile);
                    }
                }
            }
        }

        NxpInspectionPm nxpInspectionPm2 = nxpInspectionPmMapper.selectNxpInspectionPmById(pmReviewDTO.getPm_id().longValue());
        if (nxpInspectionPm2 != null){

            NxpAppMessage nxpAppMessage = new NxpAppMessage();
            nxpAppMessage.setStatus(1);
            nxpAppMessage.setType(2);
            nxpAppMessage.setCreate_user_id(nxpInspectionPm2.getDeal_user_id());
            nxpAppMessage.setPm_id(nxpInspectionPm2.getId());
            nxpAppMessage.setFacility_id(nxpInspectionPm2.getFacility_id());

            NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(nxpInspectionPm2.getDeal_user_id());
            nxpAppMessage.setComment(nxpUserInfo.getChinese_name() + " 提交了周PM复查信息，请及时查收");

            NxpInspectionPmRule nxpInspectionPmRule = new NxpInspectionPmRule();
            nxpInspectionPmRule.setFacility_id(nxpInspectionPm2.getFacility_id().toString());
            List<NxpInspectionPmRule> nxpInspectionPmRules = nxpInspectionPmRuleMapper.selectNxpInspectionPmRuleList(nxpInspectionPmRule);
            String userId = "";
            if (nxpInspectionPmRules != null){
                for (NxpInspectionPmRule inspectionPmRule : nxpInspectionPmRules) {
                    userId = inspectionPmRule.getUser_id().toString();
                }
            }
            nxpAppMessage.setUser_id(Long.valueOf(userId));

            nxpAppMessage.setDel("NO");
            nxpAppMessage.setCreated_at(DateUtil.now());
            nxpAppMessage.setIs_receive(1);
            nxpAppMessage.setTypeName("周PM复查信息");
            nxpAppMessageService.insertNxpAppMessage(nxpAppMessage);

        }


        return BaseVO.success();
    }

    @Override
    public BaseVO getPmList(NxpInspectionPm nxpInspectionPm) {
        // 判断参数是否必填
        if (nxpInspectionPm.getUserInfo_id() == null){
            return BaseVO.error("userInfo_id为必传参数");
        }
        NxpInspectionPmRule nxpInspectionPmRule = new NxpInspectionPmRule();
        nxpInspectionPmRule.setUser_id(nxpInspectionPm.getUserInfo_id());
        List<NxpInspectionPmRule> nxpInspectionPmRules = nxpInspectionPmRuleMapper.selectNxpInspectionPmRuleList(nxpInspectionPmRule);
        List<NxpFacility> resultList = new ArrayList<>();
        for (NxpInspectionPmRule inspectionPmRule : nxpInspectionPmRules) {
            String facility_id = inspectionPmRule.getFacility_id();
            if (StringUtils.isNoneBlank(facility_id)){
                if (facility_id.indexOf(",") != -1){
                    String[] split = facility_id.split(",");
                    resultList.addAll(nxpFacilityMapper.selectNxpFacilityByIds(split));
                }else {
                    NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(Long.valueOf(facility_id));
                    resultList.add(nxpFacility);
                }
            }
        }
        for (NxpFacility nxpFacility : resultList) {
            NxpInspectionPm inspectionPm = nxpInspectionPmMapper.selectByFacilityIdMaxCheckTime(nxpFacility.getId());
            if (inspectionPm != null){
                nxpFacility.setPm_status(inspectionPm.getStatus());
                nxpFacility.setCheck_time(inspectionPm.getCheck_time());
            }
            Long head_user = nxpFacility.getHead_user();
            NxpUserInfo userInfo = nxpUserInfoMapper.selectNxpUserInfoById(head_user);
            if (userInfo != null){
                nxpFacility.setHead_user_name(userInfo.getChinese_name());
            }
            String model = nxpFacility.getModel();
            NxpMachineModel nxpMachineModel = nxpMachineModelMapper.selectNxpMachineModelById(Long.valueOf(model));
            if (nxpMachineModel != null){
                nxpFacility.setModel(nxpMachineModel.getName());
            }
        }
        return BaseVO.success(resultList);
    }

    /**
     * 通用上传请求
     */
    public Map<String, String> uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("fileName", fileName);
            resultMap.put("url", url);
            return resultMap;
        }
        catch (Exception e)
        {
            return new HashMap<>();
        }
    }
}
