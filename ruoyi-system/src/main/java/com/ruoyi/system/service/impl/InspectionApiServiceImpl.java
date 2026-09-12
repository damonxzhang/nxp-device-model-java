package com.ruoyi.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.common.utils.MultipartFileUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.DTO.InspectionInfoDTO;
import com.ruoyi.system.domain.DTO.NxpFacilityDTO;
import com.ruoyi.system.domain.DTO.NxpFacilityListDTO;
import com.ruoyi.system.domain.DTO.OperateDTO;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Yancheng Liu
 * @create 2021-02-05 16:42
 */
@Service("IInspectionApiService")
@DataSource(value = DataSourceType.SLAVE)
public class InspectionApiServiceImpl implements IInspectionApiService {

    @Resource
    private NxpInspectionMapper nxpInspectionMapper;
    @Resource
    private NxpFacilityMapper nxpFacilityMapper;
    @Resource
    private NxpUserInfoMapper nxpUserInfoMapper;
    @Resource
    private NxpInspectionFileMapper nxpInspectionFileMapper;
    @Resource
    private NxpInspectionServiceMapper nxpInspectionServiceMapper;
    @Autowired
    private ServerConfig serverConfig;
    @Resource
    private NxpInspectionPositionMapper nxpInspectionPositionMapper;
    @Resource
    private NxpInspectionContentMapper nxpInspectionContentMapper;
    @Resource
    private NxpMachineProcessMapper nxpMachineProcessMapper;
    @Resource
    private NxpInspectionRevelantPeopleMapper nxpInspectionRevelantPeopleMapper;
    @Resource
    private NxpInspectionServiceSparepartMapper nxpInspectionServiceSparepartMapper;
    @Resource
    private NxpInspectionAboutReasonMapper nxpInspectionAboutReasonMapper;
    @Resource
    private NxpAppMessageMapper nxpAppMessageMapper;
    @Resource
    private NxpMaintainTypeMapper nxpMaintainTypeMapper;
    @Resource
    private NxpInspectionMaintenanceReasonMapper nxpInspectionMaintenanceReasonMapper;
    @Resource
    private NxpInspectionPositionModelMapper nxpInspectionPositionModelMapper;
    @Resource
    private NxpFacilityUserMapper nxpFacilityUserMapper;
    @Resource
    private NxpInspectionQuestionTypeMapper nxpInspectionQuestionTypeMapper;
    @Resource
    private NxpExternalRelationMapper nxpExternalRelationMapper;
    @Resource
    private NxpMachineModelMapper nxpMachineModelMapper;
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


    /**
     * 查询报修信息
     *
     * @param nxpInspectionService
     * @return
     */
    @Override
    public BaseVO inspectionInfo(NxpInspectionService nxpInspectionService) {
        // 1. 校验参数是否为空
        if (nxpInspectionService.getInspection_id() == null) {
            return BaseVO.error("报修ID不能为空");
        }
        InspectionInfoDTO inspectionInfoDTO = nxpInspectionMapper.inspectionInfo(nxpInspectionService);
//        inspectionInfoDTO.setSubmit_time(stampToDate(Integer.valueOf(inspectionInfoDTO.getSubmit_time())));
        String repairFileId = inspectionInfoDTO.getFile_ids();
        if (StringUtils.isNoneBlank(repairFileId)) {
            List<String> filePathList = new ArrayList<>();
            if (repairFileId.indexOf(",") != -1){
                // 表示多个file
                String[] fileIds = repairFileId.split(",");
                // 批量查询file的path
                List<NxpInspectionFile> files = nxpInspectionFileMapper.selectByBatchIds(fileIds);
                for (NxpInspectionFile file : files) {
                    filePathList.add(file.getFile_path());
                }
            }else {
                NxpInspectionFile nxpInspectionFile = nxpInspectionFileMapper.selectNxpInspectionFileById(Long.valueOf(repairFileId));
                filePathList.add(nxpInspectionFile.getFile_path());
            }
            inspectionInfoDTO.setRepair_file_id(filePathList);
        }
        NxpInspectionQuestionType nxpInspectionQuestionType = nxpInspectionQuestionTypeMapper.selectNxpInspectionQuestionTypeById(inspectionInfoDTO.getQuestion_type());
        if (nxpInspectionQuestionType != null)
            inspectionInfoDTO.setQuestion_type_name(nxpInspectionQuestionType.getName());
        return BaseVO.success(inspectionInfoDTO);
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(int time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time_Date = sdf.format(new Date(time * 1000L));
        return time_Date;

    }

    /**
     * 新增报修信息
     * @param nxpInspection
     * @return
     */
    @Override
    @Transactional
    public BaseVO inspectionCreate(NxpInspection nxpInspection) {
        // 1. 校验参数
        if (nxpInspection.getFacility_id() == null){
            return BaseVO.error("设备id为必传参数");
        }
        /*if (StringUtils.isBlank(nxpInspection.getExplanation())){
            return BaseVO.error("报修描述为必传参数");
        }*/
        if (nxpInspection.getUser_info_id() == null){
            return BaseVO.error("报修人id为必传参数");
        }
        if (nxpInspection.getQuestion_type() == null){
            return BaseVO.error("问题类型为必传参数");
        }
        // 这里增加判断，如果该设备报修过，且状态不为1，也就是正常的状态，则直接return，不允许重复报修
        NxpInspection inspection = new NxpInspection();
        inspection.setFacility_id(nxpInspection.getFacility_id());
        NxpInspection statusAndDeviceId = nxpInspectionMapper.selectByStatusAndDeviceId(inspection);
        if (statusAndDeviceId != null){
            // 设备有报修，且未维修完不允许再次报修
            return BaseVO.error("该设备有未完成的报修，不允许再次报修");
        }
        // 获取设备
        NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(nxpInspection.getFacility_id());
        if (nxpFacility == null){
            return BaseVO.error("设备不存在，不允许报修");
        }
        // 设置设备报修状态为待处理
        nxpFacility.setInspection_status(0);
        nxpFacilityMapper.updateNxpFacility(nxpFacility);

        // 通过中间表查询设备相关人员
        NxpFacilityUser nxpFacilityUser = new NxpFacilityUser();
        nxpFacilityUser.setFacilityId(nxpFacility.getId());
        List<NxpFacilityUser> nxpFacilityUsers = nxpFacilityUserMapper.selectNxpFacilityUserList(nxpFacilityUser);
        String relevantPeopleIds = "";

        // 2. 处理图片参数，图片传进来的格式为id加逗号
        nxpInspection.setRelevant_people(relevantPeopleIds);
        nxpInspection.setRepair_file_id(nxpInspection.getPic_id());
        nxpInspection.setCreated_at(System.currentTimeMillis() / 1000);
        nxpInspection.setStatus(0);
        nxpInspectionMapper.insertNxpInspection(nxpInspection);

        // 统计问题类型
        NxpInspectionQuestionType nxpInspectionQuestionType = nxpInspectionQuestionTypeMapper.selectNxpInspectionQuestionTypeById(Long.valueOf(nxpInspection.getQuestion_type()));
        Integer i = nxpInspectionQuestionType.getCount() + 1;
        NxpInspectionQuestionType nxpInspectionQuestionType1 = new NxpInspectionQuestionType();
        nxpInspectionQuestionType1.setId(Long.valueOf(nxpInspection.getQuestion_type()));
        nxpInspectionQuestionType1.setCount(i);
        nxpInspectionQuestionTypeMapper.updateNxpInspectionQuestionType(nxpInspectionQuestionType1);

        for (NxpFacilityUser facilityUser : nxpFacilityUsers) {
            relevantPeopleIds = relevantPeopleIds + facilityUser.getUserId() + ",";
            NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(facilityUser.getUserId());
            if (nxpUserInfo != null){
                // 发送站内消息通知设备相关人员有设备报修
                NxpAppMessage nxpAppMessage = new NxpAppMessage();
                nxpAppMessage.setStatus(1);
                nxpAppMessage.setType(1);
                nxpAppMessage.setCreate_user_id(nxpInspection.getUser_info_id());
                nxpAppMessage.setInspection_id(nxpInspection.getId());
                nxpAppMessage.setFacility_id(nxpInspection.getFacility_id());
                nxpAppMessage.setComment(nxpUserInfo.getChinese_name() + " 提交了报修信息，请及时查收");
                nxpAppMessage.setUser_id(facilityUser.getUserId());
                nxpAppMessage.setDel("NO");
                nxpAppMessage.setCreated_at(DateUtil.now());
                nxpAppMessage.setIs_receive(1);
                nxpAppMessage.setTypeName("报修信息");
                nxpAppMessageService.insertNxpAppMessage(nxpAppMessage);
            }
        }

        /*String relevant_people = nxpFacility.getRelevant_people();
        if (StringUtils.isNoneBlank(relevant_people)){
            Long userInfoId = nxpInspection.getUser_info_id();
            NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(userInfoId);
            if (relevant_people.indexOf(",") != -1){
                String[] user_info_id = relevant_people.split(",");
                for (String s : user_info_id) {
                    NxpInspectionRevelantPeople nxpInspectionRevelantPeople = new NxpInspectionRevelantPeople();
                    nxpInspectionRevelantPeople.setInspection_id(nxpInspection.getId());
                    nxpInspectionRevelantPeople.setFacility_id(nxpFacility.getId());
                    nxpInspectionRevelantPeople.setRevelant_people(Long.valueOf(s));
                    nxpInspectionRevelantPeopleMapper.insertNxpInspectionRevelantPeople(nxpInspectionRevelantPeople);
                    // 发送站内消息通知设备相关人员有设备报修
                    NxpAppMessage nxpAppMessage = new NxpAppMessage();
                    nxpAppMessage.setStatus(1);
                    nxpAppMessage.setType(1);
                    nxpAppMessage.setFacility_id(nxpInspection.getFacility_id());
                    nxpAppMessage.setComment(nxpUserInfo.getChinese_name() + " 提交了报修信息，请及时查收");
                    nxpAppMessage.setUser_id(Long.valueOf(s));
                    nxpAppMessage.setDel("NO");
                    nxpAppMessage.setCreated_at(DateUtil.now());
                    nxpAppMessage.setIs_receive(1);
                    nxpAppMessage.setTypeName("报修信息");
                    nxpAppMessageMapper.insertNxpAppMessage(nxpAppMessage);
                }
            }else {
                NxpInspectionRevelantPeople nxpInspectionRevelantPeople = new NxpInspectionRevelantPeople();
                nxpInspectionRevelantPeople.setInspection_id(nxpInspection.getId());
                nxpInspectionRevelantPeople.setFacility_id(nxpFacility.getId());
                nxpInspectionRevelantPeople.setRevelant_people(Long.valueOf(relevant_people));
                nxpInspectionRevelantPeopleMapper.insertNxpInspectionRevelantPeople(nxpInspectionRevelantPeople);
                // 发送站内消息通知设备相关人员有设备报修
                NxpAppMessage nxpAppMessage = new NxpAppMessage();
                nxpAppMessage.setType(1);
                nxpAppMessage.setStatus(1);
                nxpAppMessage.setFacility_id(nxpInspection.getFacility_id());
                nxpAppMessage.setComment(nxpUserInfo.getChinese_name() + " 提交了报修信息，请及时查收");
                nxpAppMessage.setUser_id(Long.valueOf(relevant_people));
                nxpAppMessage.setDel("NO");
                nxpAppMessage.setCreated_at(DateUtil.now());
                nxpAppMessage.setIs_receive(1);
                nxpAppMessage.setTypeName("报修信息");
                nxpAppMessageMapper.insertNxpAppMessage(nxpAppMessage);
            }
        }*/
        return BaseVO.success();
    }

    @Override
    public BaseVO inspectionServiceInfo(NxpInspectionService nxpInspectionService) {
        // 1. 校验参数
        if (StringUtils.isBlank(nxpInspectionService.getInspection_service_id())){
            return BaseVO.error("维修ID为必传参数");
        }
        String[] split = null;
        if (nxpInspectionService.getInspection_service_id().indexOf(",") != -1){
            split = nxpInspectionService.getInspection_service_id().split(",");
        }else {
            split = new String[]{nxpInspectionService.getInspection_service_id()};
        }
        List<NxpInspectionService> nxpInspectionServices = nxpInspectionServiceMapper.selectNxpInspectionServiceByIds(split);
        for (NxpInspectionService inspectionService : nxpInspectionServices) {
            // 3. 处理所需设备名称，负责人姓名，报修人姓名，文件内容
            Long facilityId = inspectionService.getFacility_id();
            NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(facilityId);
            if (nxpFacility != null) {
                inspectionService.setFacility_name(nxpFacility.getName());
                NxpMachineModel nxpMachineModel = nxpMachineModelMapper.selectNxpMachineModelById(Long.valueOf(nxpFacility.getModel()));
                inspectionService.setFacility_model(nxpMachineModel.getName());
//                NxpMachineProcess nxpMachineProcess = nxpMachineProcessMapper.selectNxpMachineProcessById(Long.valueOf(nxpFacility.getProcess_id()));
//                inspectionService.setProcess_name(nxpMachineProcess.getName());
            }
            // 设备负责人
            Long headUser = nxpFacility.getHead_user();
            NxpUserInfo facilityNxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(headUser);
            if (facilityNxpUserInfo != null) {
                inspectionService.setFacility_user_name(facilityNxpUserInfo.getChinese_name());
            }
            // 维修人
            Long userInfoId = inspectionService.getUser_info_id();
            NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(userInfoId);
            if (nxpUserInfo != null) {
                inspectionService.setHead_user(nxpUserInfo.getChinese_name());
            }
            String repairFileId = inspectionService.getFile_id();
            if (StringUtils.isNoneBlank(repairFileId)) {
                List<String> filePathList = new ArrayList<>();
                if (repairFileId.indexOf(",") != -1){
                    // 表示多个file
                    String[] fileIds = repairFileId.split(",");
                    // 批量查询file的path
                    List<NxpInspectionFile> files = nxpInspectionFileMapper.selectByBatchIds(fileIds);
                    for (NxpInspectionFile file : files) {
                        filePathList.add(file.getFile_path());
                    }
                    inspectionService.setRepair_file_paths(filePathList);
                }else {
                    NxpInspectionFile nxpInspectionFile = nxpInspectionFileMapper.selectNxpInspectionFileById(Long.valueOf(repairFileId));
                    filePathList.add(nxpInspectionFile.getFile_path());
                    inspectionService.setRepair_file_paths(filePathList);
                }
            }

            // 查询position和content
            Long position_id = inspectionService.getPosition_id();
            NxpInspectionPosition nxpInspectionPosition = nxpInspectionPositionMapper.selectNxpInspectionPositionById(position_id);
            if (nxpInspectionPosition != null){
                inspectionService.setPosition(nxpInspectionPosition.getPosition());
            }
            Long content_id = inspectionService.getContent_id();
            NxpInspectionContent nxpInspectionContent = nxpInspectionContentMapper.selectNxpInspectionContentById(content_id);
            if (nxpInspectionContent != null){
                inspectionService.setContent(nxpInspectionContent.getContent());
            }
            // 处理备件
            Long id = inspectionService.getId();
            NxpInspectionServiceSparepart nxpInspectionServiceSparepart = new NxpInspectionServiceSparepart();
            nxpInspectionServiceSparepart.setInspection_service_id(id);
            List<NxpInspectionServiceSparepart> nxpInspectionServiceSpareparts = nxpInspectionServiceSparepartMapper.selectNxpInspectionServiceSparepartList(nxpInspectionServiceSparepart);
            inspectionService.setSparepart_List(nxpInspectionServiceSpareparts);

            // 设备型号



        }
        return BaseVO.success(nxpInspectionServices);
    }

    @Override
    public BaseVO inspectionList(NxpInspection nxpInspection) {
        // 1.校验参数
        if (nxpInspection.getFacility_id() == null){
            return BaseVO.error("设备id为必传参数");
        }
        nxpInspection.setStatus(4);
        List<NxpInspection> nxpInspections = nxpInspectionMapper.selectNxpInspectionList(nxpInspection);
        for (NxpInspection inspection : nxpInspections) {
            // 3. 处理所需设备名称，负责人姓名，报修人姓名，文件内容
            Long facilityId = inspection.getFacility_id();
            NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(facilityId);
            if (nxpFacility != null) {
                inspection.setFacility_name(nxpFacility.getName());
                inspection.setFacilityNumber(nxpFacility.getNumber());
            }
            Long headUser = nxpFacility.getHead_user();
            NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(headUser);
            if (nxpUserInfo != null) {
                inspection.setHead_user(nxpUserInfo.getChinese_name());
            }
            String repairFileId = inspection.getRepair_file_id();
            if (StringUtils.isNoneBlank(repairFileId) && repairFileId.indexOf(",") != -1) {
                // 表示多个file
                String[] fileIds = repairFileId.split(",");
                // 批量查询file的path
                List<NxpInspectionFile> files = nxpInspectionFileMapper.selectByBatchIds(fileIds);
                List<String> filePathList = new ArrayList<>();
                for (NxpInspectionFile file : files) {
                    filePathList.add(file.getFile_path());
                }
                inspection.setRepair_file_paths(filePathList);
            }

            String relevant_people = inspection.getRelevant_people();
            StringBuilder stringBuilder = new StringBuilder();
            if (StringUtils.isNoneBlank(relevant_people)){
                if (relevant_people.indexOf(",") != -1){
                    String[] peopleId = relevant_people.split(",");
                    for (String userId : peopleId) {
                        NxpUserInfo userInfo = nxpUserInfoMapper.selectNxpUserInfoById(Long.valueOf(userId));
                        if (userInfo != null){
                            stringBuilder.append(userInfo.getChinese_name() + ",");
                        }
                    }
                }
            }
            if (StringUtils.isNoneBlank(stringBuilder.toString())){
                inspection.setRelevant_people_name(stringBuilder.toString().substring(0,stringBuilder.toString().length() - 1));
            }else {
                inspection.setRelevant_people_name(stringBuilder.toString());
            }
            // 处理questionTypeName
//            NxpMaintainType nxpMaintainType = nxpMaintainTypeMapper.selectNxpMaintainTypeById(Long.valueOf(inspection.getQuestion_type()));
//            if (nxpMaintainType != null){
//                inspection.setQuestionTypeName(nxpMaintainType.getName());
//            }
            NxpInspectionQuestionType nxpInspectionQuestionType = nxpInspectionQuestionTypeMapper.selectNxpInspectionQuestionTypeById(Long.valueOf(inspection.getQuestion_type()));
            if (nxpInspectionQuestionType != null){
                inspection.setQuestionTypeName(nxpInspectionQuestionType.getName());
            }
        }
        return BaseVO.success(nxpInspections);
    }

    @Override
    @Transactional
    public BaseVO inspectionServiceCreate(NxpInspectionService nxpInspectionService) {
        // 创建时间
        nxpInspectionService.setCreated_at(System.currentTimeMillis() / 1000);
//        nxpInspectionService.setService_time(new Date());
        nxpInspectionServiceMapper.insertNxpInspectionService(nxpInspectionService);

        // 通过报修id，查询设备报修信息
        Long inspectionId = nxpInspectionService.getInspection_id();
        NxpInspection inspection = nxpInspectionMapper.selectNxpInspectionById(inspectionId);
        //插入关联批号
        if (StringUtils.isNoneBlank(nxpInspectionService.getPart_no())){
            inspection.setPart_no(nxpInspectionService.getPart_no());
        }
        if (nxpInspectionService.getStatus() == 1){
            // 已完成,报修表更新成待确认
            if (inspection != null){
                inspection.setStatus(3);
                nxpInspectionMapper.updateNxpInspection(inspection);
                // 查询设备，设置设备表的报修状态
                Long facility_id = inspection.getFacility_id();
                NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(facility_id);
                nxpFacility.setInspection_status(3);
                nxpFacilityMapper.updateNxpFacility(nxpFacility);
            }
            nxpInspectionService.setEnd_time(new Date());
            nxpInspectionServiceMapper.updateNxpInspectionService(nxpInspectionService);
            // 查询维修人姓名
            NxpUserInfo userInfo = nxpUserInfoMapper.selectNxpUserInfoById(nxpInspectionService.getUser_info_id());
            String serviceUserName = "";
            if (userInfo != null){
                serviceUserName = userInfo.getChinese_name();
            }
            // 发送待确认消息
            NxpAppMessage nxpAppMessage = new NxpAppMessage();
            nxpAppMessage.setType(1);
            nxpAppMessage.setStatus(1);
            nxpAppMessage.setCreate_user_id(nxpInspectionService.getUser_info_id());
            nxpAppMessage.setInspection_id(inspectionId);
            nxpAppMessage.setFacility_id(inspection.getFacility_id());
            nxpAppMessage.setComment(serviceUserName + " 提交了维修信息，请及时确认");
            nxpAppMessage.setUser_id(inspection.getUser_info_id());
            nxpAppMessage.setDel("NO");
            nxpAppMessage.setCreated_at(DateUtil.now());
            nxpAppMessage.setIs_receive(1);
            nxpAppMessage.setTypeName("待确认信息");
            nxpAppMessageService.insertNxpAppMessage(nxpAppMessage);
        }else {
            if (inspection != null){
                // 中止维修,设置为待处理状态
                inspection.setStatus(0);
                nxpInspectionMapper.updateNxpInspection(inspection);
                // 查询设备，设置设备表的报修状态
                Long facility_id = inspection.getFacility_id();
                NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(facility_id);
                nxpFacility.setInspection_status(0);
                nxpFacilityMapper.updateNxpFacility(nxpFacility);
                // 查询维修人姓名
                NxpUserInfo userInfo = nxpUserInfoMapper.selectNxpUserInfoById(nxpInspectionService.getUser_info_id());
                String serviceUserName = "";
                if (userInfo != null){
                    serviceUserName = userInfo.getChinese_name();
                }
                // 发送待确认消息
                NxpAppMessage nxpAppMessage = new NxpAppMessage();
                nxpAppMessage.setType(1);
                nxpAppMessage.setStatus(1);
                nxpAppMessage.setCreate_user_id(nxpInspectionService.getUser_info_id());
                nxpAppMessage.setInspection_id(inspectionId);
                nxpAppMessage.setFacility_id(inspection.getFacility_id());
                nxpAppMessage.setComment(serviceUserName + " 提交了维修信息，请及时确认");
                nxpAppMessage.setUser_id(inspection.getUser_info_id());
                nxpAppMessage.setDel("NO");
                nxpAppMessage.setCreated_at(DateUtil.now());
                nxpAppMessage.setIs_receive(1);
                nxpAppMessage.setTypeName("中止维修信息");
                nxpAppMessageService.insertNxpAppMessage(nxpAppMessage);
            }
        }
        // 备件
        List<OutPut> sparepartList = nxpInspectionService.getSparepart_list();
        if (sparepartList.size() > 0){
            for (OutPut outPut : sparepartList) {
                NxpInspectionServiceSparepart nxpInspectionServiceSparepart = new NxpInspectionServiceSparepart();
                nxpInspectionServiceSparepart.setInspection_service_id(nxpInspectionService.getId());
                nxpInspectionServiceSparepart.setS_id(outPut.getS_id());
                nxpInspectionServiceSparepart.setE_id(outPut.getE_id());
                nxpInspectionServiceSparepart.setO_amount(outPut.getO_amount());
                nxpInspectionServiceSparepartMapper.insertNxpInspectionServiceSparepart(nxpInspectionServiceSparepart);
            }
        }
        return BaseVO.success();
    }

    @Override
    @Transactional
    public BaseVO inspectionServiceCreateNew(NxpInspectionService nxpInspectionService) {
        if (nxpInspectionService.getId() == null){
            return BaseVO.error("ID为必传字段");

        }
        // 创建时间
        nxpInspectionService.setUpdated_at(System.currentTimeMillis() / 1000);
        nxpInspectionServiceMapper.updateNxpInspectionService(nxpInspectionService);

        // 通过报修id，查询设备报修信息
        Long inspectionId = nxpInspectionService.getInspection_id();
        NxpInspection inspection = nxpInspectionMapper.selectNxpInspectionById(inspectionId);
        //插入关联批号
        if (StringUtils.isNoneBlank(nxpInspectionService.getPart_no())){
            inspection.setPart_no(nxpInspectionService.getPart_no());
        }
        if (nxpInspectionService.getStatus() == 1){
            // 已完成,报修表更新成待确认
            if (inspection != null){
//                if (inspection.getStatus() != 1 && inspection.getStatus() != 3){
                    inspection.setStatus(3);
                    nxpInspectionMapper.updateNxpInspection(inspection);
                    // 查询设备，设置设备表的报修状态
                    Long facility_id = inspection.getFacility_id();
                    NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(facility_id);
                    nxpFacility.setInspection_status(3);
                    nxpFacilityMapper.updateNxpFacility(nxpFacility);
//                }

            }
            nxpInspectionService.setEnd_time(new Date());
            nxpInspectionServiceMapper.updateNxpInspectionService(nxpInspectionService);

            // 查询维修人姓名
            NxpUserInfo userInfo = nxpUserInfoMapper.selectNxpUserInfoById(nxpInspectionService.getUser_info_id());
            String serviceUserName = "";
            if (userInfo != null){
                serviceUserName = userInfo.getChinese_name();
            }

            // 判断是否需要提交到检查列表
            if (StringUtils.isNotBlank(nxpInspectionService.getIsInsertCheckList())){
                if ("0".equals(nxpInspectionService.getIsInsertCheckList())){
                    // 0是 1否
                    // 判断维修内容是否为检查项
                    Long contentId = nxpInspectionService.getContent_id();
                    NxpInspectionContent nxpInspectionContent = nxpInspectionContentMapper.selectNxpInspectionContentById(contentId);
                    if (nxpInspectionContent != null){
                        if ("0".equals(nxpInspectionContent.getIs_check())){
                            // 表示需要检查
                            NxpCheckList nxpCheckList = new NxpCheckList();
                            if (StringUtils.isNotBlank(nxpInspectionService.getProcess_id())){
                                nxpCheckList.setProcessId(Long.valueOf(nxpInspectionService.getProcess_id()));
                            }
                            nxpCheckList.setFacilityId(nxpInspectionService.getFacility_id());
                            nxpCheckList.setSubmitterId(userInfo.getId());
                            nxpCheckList.setSubmitterName(serviceUserName);
                            nxpCheckList.setType(1L);
                            nxpCheckList.setInspectionContentStr(nxpInspectionService.getContent());
                            nxpCheckList.setContent(nxpInspectionService.getContent());
                            nxpCheckList.setInspectionId(nxpInspectionService.getInspection_id());
                            nxpCheckList.setInspectionContentId(contentId);
                            nxpCheckList.setInspectionServiceId(nxpInspectionService.getId());
                            NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(inspection.getFacility_id());
                            nxpCheckList.setFacilityNumber(nxpFacility.getNumber());
                            nxpCheckList.setProcessId(Long.valueOf(nxpFacility.getProcess()));
                            nxpCheckListService.insertNxpCheckList(nxpCheckList);
                            // 1. 先拿出检查组
                            Long checkGroupId = nxpInspectionContent.getCheck_group_id();
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
                }
            }

            // 发送待确认消息
            NxpAppMessage nxpAppMessage = new NxpAppMessage();
            nxpAppMessage.setType(1);
            nxpAppMessage.setStatus(1);
            nxpAppMessage.setCreate_user_id(nxpInspectionService.getUser_info_id());
            nxpAppMessage.setInspection_id(inspectionId);
            nxpAppMessage.setFacility_id(inspection.getFacility_id());
            nxpAppMessage.setComment(serviceUserName + " 提交了维修信息，请及时确认");
            nxpAppMessage.setUser_id(inspection.getUser_info_id());
            nxpAppMessage.setDel("NO");
            nxpAppMessage.setCreated_at(DateUtil.now());
            nxpAppMessage.setIs_receive(1);
            nxpAppMessage.setTypeName("待确认信息");
            nxpAppMessageService.insertNxpAppMessage(nxpAppMessage);
        }else {
            if (inspection != null){
//                if (inspection.getStatus() != 1 && inspection.getStatus() != 3){
                    // 中止维修,设置为待处理状态
                    inspection.setStatus(0);
                    nxpInspectionMapper.updateNxpInspection(inspection);
                    // 查询设备，设置设备表的报修状态
                    Long facility_id = inspection.getFacility_id();
                    NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(facility_id);
                    nxpFacility.setInspection_status(0);
                    nxpFacilityMapper.updateNxpFacility(nxpFacility);
//                }

                // 查询维修人姓名
                NxpUserInfo userInfo = nxpUserInfoMapper.selectNxpUserInfoById(nxpInspectionService.getUser_info_id());
                String serviceUserName = "";
                if (userInfo != null){
                    serviceUserName = userInfo.getChinese_name();
                }
                // 发送待确认消息
                NxpAppMessage nxpAppMessage = new NxpAppMessage();
                nxpAppMessage.setType(1);
                nxpAppMessage.setStatus(1);
                nxpAppMessage.setCreate_user_id(nxpInspectionService.getUser_info_id());
                nxpAppMessage.setInspection_id(inspectionId);
                nxpAppMessage.setFacility_id(inspection.getFacility_id());
                nxpAppMessage.setComment(serviceUserName + " 提交了维修信息，请及时确认");
                nxpAppMessage.setUser_id(inspection.getUser_info_id());
                nxpAppMessage.setDel("NO");
                nxpAppMessage.setCreated_at(DateUtil.now());
                nxpAppMessage.setIs_receive(1);
                nxpAppMessage.setTypeName("中止维修信息");
                nxpAppMessageService.insertNxpAppMessage(nxpAppMessage);
            }
        }
        // 备件
        List<OutPut> sparepartList = nxpInspectionService.getSparepart_list();
        if (sparepartList.size() > 0){
            for (OutPut outPut : sparepartList) {
                NxpInspectionServiceSparepart nxpInspectionServiceSparepart = new NxpInspectionServiceSparepart();
                nxpInspectionServiceSparepart.setInspection_service_id(nxpInspectionService.getId());
                nxpInspectionServiceSparepart.setS_id(outPut.getS_id());
                nxpInspectionServiceSparepart.setE_id(outPut.getE_id());
                nxpInspectionServiceSparepart.setO_amount(outPut.getO_amount());
                nxpInspectionServiceSparepartMapper.insertNxpInspectionServiceSparepart(nxpInspectionServiceSparepart);
            }
        }
        return BaseVO.success();
    }

    @Override
    public List<NxpInspectionService> inspectionServiceList(NxpInspectionService nxpInspectionService) {
        List<NxpInspectionService> nxpInspectionServices = nxpInspectionServiceMapper.selectNxpInspectionServiceList(nxpInspectionService);
        return nxpInspectionServices;
    }

    @Override
    public BaseVO inspectionServiceUpdate(NxpInspection nxpInspection) {
        NxpInspection inspection = nxpInspectionMapper.selectNxpInspectionById(Long.valueOf(nxpInspection.getInspection_id()));
        if (inspection == null){
            return BaseVO.error("该维修不存在");
        }
        // 无需维修设置为待确认状态
        inspection.setStatus(3);
        inspection.setUpdated_at(new Date().getTime() / 1000);
        nxpInspectionMapper.updateNxpInspection(inspection);
        // 通过设备id查询设备
        Long facility_id = inspection.getFacility_id();
        NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(facility_id);
        // 设置设备表的维修状态为待确认状态
        nxpFacility.setInspection_status(3);
        nxpFacilityMapper.updateNxpFacility(nxpFacility);
        // 因为无需维修客户要求增加无需维修原因，故维修表增加一条数据,状态为无需维修
        NxpInspectionService nxpInspectionService = new NxpInspectionService();
        nxpInspectionService.setInspection_id(Long.valueOf(nxpInspection.getInspection_id()));
        //user_id 为维修人id
        nxpInspectionService.setUser_info_id(nxpInspection.getUser_id());
        nxpInspectionService.setDescription("无需维修");
        nxpInspectionService.setCreated_at(new Date().getTime() / 1000);
        nxpInspectionService.setStatus(3);
        nxpInspectionService.setFacility_id(facility_id);
        nxpInspectionService.setEnd_time(new Date());
        nxpInspectionService.setService_time(new Date());
        nxpInspectionService.setMaintenance_reason_id(nxpInspection.getMaintenance_reason_id());
        nxpInspectionService.setMaintenance_reason_name(nxpInspection.getMaintenance_reason_name());
        nxpInspectionServiceMapper.insertNxpInspectionService(nxpInspectionService);
        // 查询维修人姓名
        NxpUserInfo userInfo = nxpUserInfoMapper.selectNxpUserInfoById(nxpInspection.getUser_id());
        String serviceUserName = "";
        if (userInfo != null){
            serviceUserName = userInfo.getChinese_name();
        }
        // 发送待确认消息
        NxpAppMessage nxpAppMessage = new NxpAppMessage();
        nxpAppMessage.setType(1);
        nxpAppMessage.setStatus(1);
        nxpAppMessage.setCreate_user_id(nxpInspection.getUser_id());
        nxpAppMessage.setInspection_id(Long.valueOf(nxpInspection.getInspection_id()));
        nxpAppMessage.setFacility_id(inspection.getFacility_id());
        nxpAppMessage.setComment(serviceUserName + " 提交了维修信息，请及时确认");
        nxpAppMessage.setUser_id(inspection.getUser_info_id());
        nxpAppMessage.setDel("NO");
        nxpAppMessage.setCreated_at(DateUtil.now());
        nxpAppMessage.setIs_receive(1);
        nxpAppMessage.setTypeName("待确认无需维修信息");
        nxpAppMessageService.insertNxpAppMessage(nxpAppMessage);
        return BaseVO.success();
    }

    @Override
    public BaseVO uploadFile(Picture picture) {
        String base64 = picture.getImg_picture();
        if (StringUtils.isBlank(base64)){
            return BaseVO.error("base64编码为空");
        }
        // 转换base64为MultipartFile
        MultipartFile multipartFile = MultipartFileUtils.base64ToMultipart(base64);
        NxpInspectionFile nxpInspectionFile = new NxpInspectionFile();
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
        nxpInspectionFile.setCreate_time(new Date());
        nxpInspectionFileMapper.insertNxpInspectionFile(nxpInspectionFile);
        return BaseVO.success(nxpInspectionFile);
    }

    @Override
    public BaseVO getPosition(NxpFacility nxpFacility) {
        NxpFacility facility = nxpFacilityMapper.selectNxpFacilityById(nxpFacility.getFacility_id());
        List<NxpInspectionPosition> positionList = new ArrayList<>();
        if (facility != null){
            List<NxpInspectionPositionModel> nxpInspectionPositionModels = nxpInspectionPositionModelMapper.selectNxpInspectionPositionModelBymodelId(Long.valueOf(facility.getModel()));
            for (NxpInspectionPositionModel nxpInspectionPositionModel : nxpInspectionPositionModels) {
                NxpInspectionPosition nxpInspectionPosition1 = nxpInspectionPositionMapper.selectNxpInspectionPositionById(nxpInspectionPositionModel.getPositionId());
                if (nxpInspectionPosition1 != null){
                    NxpInspectionPosition nxpInspectionPosition = new NxpInspectionPosition();
                    nxpInspectionPosition.setId(nxpInspectionPosition1.getId());
                    nxpInspectionPosition.setPosition(nxpInspectionPosition1.getPosition());
                    nxpInspectionPosition.setStatus(nxpInspectionPosition1.getStatus());
                    positionList.add(nxpInspectionPosition);
                }
            }
            return BaseVO.success(positionList);
//            nxpInspectionPosition.setModel_id(facility.getModel());
        }else {
            return BaseVO.error("没有找到对应的设备");
        }
    }

    @Override
    public BaseVO getContent(NxpInspectionContent nxpInspectionContent) {
        // 校验参数
        if (nxpInspectionContent.getPosition_id() == null){
            return BaseVO.error("位置id为必传参数");
        }
        // 只查询已启用的
        nxpInspectionContent.setStatus(1);
        List<NxpInspectionContent> nxpInspectionContents = nxpInspectionContentMapper.selectNxpInspectionContentList(nxpInspectionContent);
        return BaseVO.success(nxpInspectionContents);
    }

    @Override
    public BaseVO selectFacilityList(NxpFacility nxpFacility) {

//        if (nxpFacility.getStatus().equals("4")){
//            List<NxpFacilityListDTO> list = new ArrayList<>();
//
//            nxpFacility.setStatus("0");
//            List<NxpFacilityListDTO> nxpFacilityListDTOS = nxpFacilityMapper.selectFacilityList(nxpFacility);
//            if (nxpFacilityListDTOS.size() > 0){
//                // 遍历所有设备，设备存在非1 正常时，查询报修表，获取报修时间和报修问题类型
//                for (NxpFacilityListDTO nxpFacilityListDTO : nxpFacilityListDTOS) {
//                    String i_status = nxpFacilityListDTO.getI_status();
//                    if (!i_status.equals("1")){
//                        // 查询报修表
//                        List<NxpInspection> inspections = nxpInspectionMapper.selectNxpInspectionListByFacilityId(nxpFacilityListDTO.getId());
//                        if (inspections.size() > 0){
//                            NxpInspection inspection = inspections.get(0);
//                            nxpFacilityListDTO.setI_repair_time(DateUtil.formatDateTime(inspection.getRepair_time()));
//                            // 查询报修问题名称
//                            Integer question_type = inspection.getQuestion_type();
//                            NxpMaintainType nxpMaintainType = nxpMaintainTypeMapper.selectNxpMaintainTypeById(question_type.longValue());
//                            if (nxpMaintainType != null){
//                                nxpFacilityListDTO.setI_question_type(nxpMaintainType.getName());
//                            }
//                        }
//                    }
//                    list.add(nxpFacilityListDTO);
//
//                }
//            }
//
//
//            nxpFacility.setStatus("2");
//            List<NxpFacilityListDTO> nxpFacilityListDTOS1 = nxpFacilityMapper.selectFacilityList(nxpFacility);
//            if (nxpFacilityListDTOS1.size() > 0){
//                // 遍历所有设备，设备存在非1 正常时，查询报修表，获取报修时间和报修问题类型
//                for (NxpFacilityListDTO nxpFacilityListDTO : nxpFacilityListDTOS1) {
//                    String i_status = nxpFacilityListDTO.getI_status();
//                    if (!i_status.equals("1")){
//                        // 查询报修表
//                        List<NxpInspection> inspections = nxpInspectionMapper.selectNxpInspectionListByFacilityId(nxpFacilityListDTO.getId());
//                        if (inspections.size() > 0){
//                            NxpInspection inspection = inspections.get(0);
//                            nxpFacilityListDTO.setI_repair_time(DateUtil.formatDateTime(inspection.getRepair_time()));
//                            // 查询报修问题名称
//                            Integer question_type = inspection.getQuestion_type();
//                            NxpMaintainType nxpMaintainType = nxpMaintainTypeMapper.selectNxpMaintainTypeById(question_type.longValue());
//                            if (nxpMaintainType != null){
//                                nxpFacilityListDTO.setI_question_type(nxpMaintainType.getName());
//                            }
//                        }
//                    }
//                    list.add(nxpFacilityListDTO);
//
//                }
//            }
//
//
//            nxpFacility.setStatus("3");
//            List<NxpFacilityListDTO> nxpFacilityListDTOS2 = nxpFacilityMapper.selectFacilityList(nxpFacility);
//            if (nxpFacilityListDTOS2.size() > 0){
//                // 遍历所有设备，设备存在非1 正常时，查询报修表，获取报修时间和报修问题类型
//                for (NxpFacilityListDTO nxpFacilityListDTO : nxpFacilityListDTOS2) {
//                    String i_status = nxpFacilityListDTO.getI_status();
//                    if (!i_status.equals("1")){
//                        // 查询报修表
//                        List<NxpInspection> inspections = nxpInspectionMapper.selectNxpInspectionListByFacilityId(nxpFacilityListDTO.getId());
//                        if (inspections.size() > 0){
//                            NxpInspection inspection = inspections.get(0);
//                            nxpFacilityListDTO.setI_repair_time(DateUtil.formatDateTime(inspection.getRepair_time()));
//                            // 查询报修问题名称
//                            Integer question_type = inspection.getQuestion_type();
//                            NxpMaintainType nxpMaintainType = nxpMaintainTypeMapper.selectNxpMaintainTypeById(question_type.longValue());
//                            if (nxpMaintainType != null){
//                                nxpFacilityListDTO.setI_question_type(nxpMaintainType.getName());
//                            }
//                        }
//                    }
//                    list.add(nxpFacilityListDTO);
//                }
//            }
//
//            nxpFacility.setStatus("1");
//            List<NxpFacilityListDTO> nxpFacilityListDTOS3 = nxpFacilityMapper.selectFacilityList(nxpFacility);
//            if (nxpFacilityListDTOS3.size() > 0){
//                // 遍历所有设备，设备存在非1 正常时，查询报修表，获取报修时间和报修问题类型
//                for (NxpFacilityListDTO nxpFacilityListDTO : nxpFacilityListDTOS3) {
//                    String i_status = nxpFacilityListDTO.getI_status();
//                    if (!i_status.equals("1")){
//                        // 查询报修表
//                        List<NxpInspection> inspections = nxpInspectionMapper.selectNxpInspectionListByFacilityId(nxpFacilityListDTO.getId());
//                        if (inspections.size() > 0){
//                            NxpInspection inspection = inspections.get(0);
//                            nxpFacilityListDTO.setI_repair_time(DateUtil.formatDateTime(inspection.getRepair_time()));
//                            // 查询报修问题名称
//                            Integer question_type = inspection.getQuestion_type();
//                            NxpMaintainType nxpMaintainType = nxpMaintainTypeMapper.selectNxpMaintainTypeById(question_type.longValue());
//                            if (nxpMaintainType != null){
//                                nxpFacilityListDTO.setI_question_type(nxpMaintainType.getName());
//                            }
//                        }
//                    }
//                    list.add(nxpFacilityListDTO);
//                }
//            }
//
//            return BaseVO.success(new PageInfo<>(list));
//
//        }else {
        if (nxpFacility.getTime_choose() != null){
            switch (nxpFacility.getTime_choose().intValue()){
                case 0:
//                    nxpFacility.setStart_time(System.currentTimeMillis() / 1000 - 3600*12);
                    break;
                case 1:
                    nxpFacility.setStart_time(System.currentTimeMillis() / 1000 - 3600*12);
                    break;
                case 2:
                    nxpFacility.setStart_time(System.currentTimeMillis() / 1000 - 3600*24);
                    break;
                case 3:
                    nxpFacility.setStart_time(System.currentTimeMillis() / 1000 - 3600*24*4);
                    break;
                case 4:
                    nxpFacility.setStart_time(System.currentTimeMillis() / 1000 - 3600*24*7);
                    break;
                default:
                    break;
            }
        }


            List<NxpFacilityListDTO> nxpFacilityListDTOS = nxpFacilityMapper.selectFacilityList(nxpFacility);
            if (nxpFacilityListDTOS.size() > 0){
                // 遍历所有设备，设备存在非1 正常时，查询报修表，获取报修时间和报修问题类型
                for (NxpFacilityListDTO nxpFacilityListDTO : nxpFacilityListDTOS) {

//                    增加型号参数
                    NxpFacility nxpFacility1 = nxpFacilityMapper.selectNxpFacilityById(Long.valueOf(nxpFacilityListDTO.getId()));
                    if (nxpFacility1.getModel() != null){
                        NxpMachineModel nxpMachineModel = nxpMachineModelMapper.selectNxpMachineModelById(Long.valueOf(nxpFacility1.getModel()));
                        if (nxpMachineModel != null)
                            nxpFacilityListDTO.setModel(nxpMachineModel.getName());

                    }

                    String i_status = nxpFacilityListDTO.getI_status();
                    if (!i_status.equals("1")){
                        // 查询报修表
                        List<NxpInspection> inspections = nxpInspectionMapper.selectNxpInspectionListByFacilityId(nxpFacilityListDTO.getId());
                        if (inspections.size() > 0){
                            NxpInspection inspection = inspections.get(0);
                            nxpFacilityListDTO.setI_repair_time(DateUtil.formatDateTime(inspection.getRepair_time()));
                            // 查询报修问题名称
                            Integer question_type = inspection.getQuestion_type();
                            NxpInspectionQuestionType nxpInspectionQuestionType = nxpInspectionQuestionTypeMapper.selectNxpInspectionQuestionTypeById(question_type.longValue());
                            if (nxpInspectionQuestionType != null){
                                nxpFacilityListDTO.setI_question_type(nxpInspectionQuestionType.getName());
                            }
//                            NxpMaintainType nxpMaintainType = nxpMaintainTypeMapper.selectNxpMaintainTypeById(question_type.longValue());
//                            if (nxpMaintainType != null){
//                                nxpFacilityListDTO.setI_question_type(nxpMaintainType.getName());
//                            }
                        }
                    }
                }
            }
            return BaseVO.success(new PageInfo<>(nxpFacilityListDTOS));
//        }
    }

    @Override
    public BaseVO facility(NxpFacility nxpFacility) {
        NxpFacility facility = nxpFacilityMapper.selectNxpFacilityByIdOrNumber(nxpFacility);
        // 处理headUser和relevant_people
        if (facility != null){
            // 1. headUser
            Long head_user = facility.getHead_user();
            NxpUserInfo userInfo = nxpUserInfoMapper.selectNxpUserInfoById(head_user);
            facility.setHead_user_name(userInfo.getChinese_name());
            // 2. relevant_people
            NxpFacilityUser nxpFacilityUser = new NxpFacilityUser();
            nxpFacilityUser.setFacilityId(facility.getId());
            List<NxpFacilityUser> nxpFacilityUsers = nxpFacilityUserMapper.selectNxpFacilityUserList(nxpFacilityUser);
            String relevant_people_name = "";
            for (NxpFacilityUser facilityUser : nxpFacilityUsers) {
                NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(facilityUser.getUserId());
                if (nxpUserInfo != null){
                    relevant_people_name = relevant_people_name + nxpUserInfo.getChinese_name() + ",";
                }
            }
            if (StringUtils.isNoneBlank(relevant_people_name))
                facility.setRelevant_people_name(relevant_people_name.substring(0,relevant_people_name.length() - 1));
            else
                facility.setRelevant_people_name(relevant_people_name);


            /*String relevant_people = facility.getRelevant_people();
            if (StringUtils.isNoneBlank(relevant_people)){
                if (relevant_people.indexOf(",") != -1){
                    String[] userIds = relevant_people.split(",");
                    for (String userId : userIds) {
                        NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(Long.valueOf(userId));
                        if (nxpUserInfo != null){
                            relevant_people_name = relevant_people_name + nxpUserInfo.getChinese_name() + ",";
                        }
                    }
                }else {
                    NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(Long.valueOf(relevant_people));
                    if (nxpUserInfo != null){
                        relevant_people_name = relevant_people_name + nxpUserInfo.getChinese_name() + ",";
                    }
                }
                facility.setRelevant_people_name(relevant_people_name.substring(0,relevant_people_name.length() - 1));
            }*/
            // 3. create_user
            Long create_user = facility.getCreate_user();
            NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(create_user);
            if (nxpUserInfo != null){
                facility.setCreate_user_name(nxpUserInfo.getChinese_name());
            }
            // process
            String process = facility.getProcess();
            facility.setProcess_id(process);
            NxpMachineProcess machineProcess = nxpMachineProcessMapper.selectNxpMachineProcessById(Long.valueOf(process));
            if (machineProcess != null){
                facility.setProcess(machineProcess.getName());
            }
            // model
            NxpMachineModel nxpMachineModel = nxpMachineModelMapper.selectNxpMachineModelById(Long.valueOf(facility.getModel()));
            if (nxpMachineModel != null){
                facility.setModel_name(nxpMachineModel.getName());
            }
            // i_id
            NxpInspection nxpInspection = new NxpInspection();
            nxpInspection.setFacility_id(facility.getId());
            nxpInspection.setStatus(4);
            List<NxpInspection> nxpInspections = nxpInspectionMapper.selectNxpInspectionList(nxpInspection);
            String i_id = "";
            for (NxpInspection inspection : nxpInspections) {
                i_id = i_id + inspection.getId() + ",";
            }
            if (i_id.indexOf(",") != -1){
                if (StringUtils.isNoneBlank(i_id)){
                    facility.setI_id(i_id.substring(0,i_id.length() - 1));
                }
            }else {
                facility.setI_id(i_id);
            }
            // 处理status为ni
            Long id = facility.getId();
            NxpInspection inspection = new NxpInspection();
            inspection.setFacility_id(id);
            NxpInspection inspection1 = nxpInspectionMapper.selectByStatusAndDeviceId(inspection);
            if (inspection1 != null){
                facility.setI_status(inspection1.getStatus().toString());
                if (inspection1.getOperate() == null){
                    facility.setOperate(0);
                }else {
                    facility.setOperate(inspection1.getOperate());
                }
            }else {
                facility.setI_status("1");
                facility.setOperate(0);
            }
            // 查询维修
            if (StringUtils.isNoneBlank(facility.getI_id())){
                NxpInspectionService nxpInspectionService = new NxpInspectionService();
                String iIdStr = facility.getI_id();
                if (StringUtils.isNoneBlank(iIdStr)){
                    if (iIdStr.indexOf(",") != -1){
                        String[] split = iIdStr.split(",");
                        List<String> iidList = Arrays.asList(split);
                        nxpInspectionService.setiIds(iidList);
                    }else {
                        ArrayList<String> iidList = new ArrayList<>();
                        iidList.add(iIdStr);
                        nxpInspectionService.setiIds(iidList);
                    }
                }
                nxpInspectionService.setFacility_id(id);
                List<NxpInspectionService> nxpInspectionServices = nxpInspectionServiceMapper.selectNxpInspectionServiceListByIids(nxpInspectionService);
                String service_id = "";
                for (NxpInspectionService inspectionService : nxpInspectionServices) {
                    service_id = service_id + inspectionService.getId() + ",";
                }
                if (service_id.indexOf(",") != -1){
                    if (StringUtils.isNoneBlank(service_id)){
                        facility.setInspection_service_id(service_id.substring(0,service_id.length() - 1));
                    }
                }else {
                    facility.setInspection_service_id(service_id);
                }
            }
        }
        return BaseVO.success(facility);
    }

    @Override
    public BaseVO inspectionQuestionType(NxpMaintainType nxpMaintainType) {
        NxpInspectionQuestionType questionType = new NxpInspectionQuestionType();
        questionType.setStatus(1);
        if (nxpMaintainType.getProcess() != null){
            questionType.setProcess_id(nxpMaintainType.getProcess().intValue());
        }

        List<NxpInspectionQuestionType> nxpInspectionQuestionTypes = nxpInspectionQuestionTypeMapper.selectNxpInspectionQuestionTypeList(questionType);
//        nxpMaintainType.setStatus(1L);
//        List<NxpMaintainType> nxpMaintainTypes = nxpMaintainTypeMapper.selectNxpMaintainTypeList(nxpMaintainType);
        return BaseVO.success(nxpInspectionQuestionTypes);
    }

    @Override
    public BaseVO pendingInspection(NxpInspection nxpInspection) {
        // 1. 校验参数
        if (nxpInspection.getUser_id() == null){
            return BaseVO.error("userId为必传参数");
        }
        List<NxpFacilityDTO> nxpFacilityDTOS = nxpFacilityMapper.pendingInspection(nxpInspection);
        for (NxpFacilityDTO nxpFacilityDTO : nxpFacilityDTOS) {
            // 处理报修状态
            String inspection_status = nxpFacilityDTO.getI_status();
            switch (inspection_status){
                case "0":
                    inspection_status = "待处理";
                    break;
                case "1":
                    inspection_status = "正常";
                    break;
                case "2":
                    inspection_status = "维修中";
                    break;
                case "3":
                    inspection_status = "待确认";
                    break;
            }
            nxpFacilityDTO.setI_status(inspection_status);
            // 处理报修人名称
            String user_info_id = nxpFacilityDTO.getUser_info_id();
            if (StringUtils.isNoneBlank(user_info_id)){
                NxpUserInfo userInfo = nxpUserInfoMapper.selectNxpUserInfoById(Long.valueOf(user_info_id));
                if (userInfo != null){
                    nxpFacilityDTO.setUser_info_id(userInfo.getChinese_name());
                }
            }
        }
        return BaseVO.success(new PageInfo<>(nxpFacilityDTOS));
    }

    @Override
    public BaseVO getProcess() {
        NxpMachineProcess machineProcess = new NxpMachineProcess();
        machineProcess.setStatus(1);
        List<NxpMachineProcess> nxpMachineProcesses = nxpMachineProcessMapper.selectNxpMachineProcessList(machineProcess);
        return BaseVO.success(nxpMachineProcesses);
    }

    @Override
    @Transactional
    public BaseVO submitIr(NxpInspection nxpInspection) {
        if (StringUtils.isBlank(nxpInspection.getInspection_id())){
            return BaseVO.error("报修id为必传参数");
        }
        if (nxpInspection.getUser_id() == 0){
            return BaseVO.error("报修人为必传参数");
        }

        NxpInspection inspection = nxpInspectionMapper.selectNxpInspectionById(Long.valueOf(nxpInspection.getInspection_id()));

        // 校验是否可提交
        // 根据外部系统配置的用户组来判断
        List<NxpExternalRelation> nxpExternalRelations = nxpExternalRelationMapper.selectNxpExternalRelationByUserId(inspection.getUser_info_id());
        List<NxpExternalRelation> nxpExternalRelations1 = nxpExternalRelationMapper.selectNxpExternalRelationByUserId(nxpInspection.getUser_id());

        String flag = "false";
        for (NxpExternalRelation nxpExternalRelation : nxpExternalRelations) {
            for (NxpExternalRelation externalRelation : nxpExternalRelations1) {
                if (nxpExternalRelation.getAuthorityId().longValue() == externalRelation.getAuthorityId().longValue()){
                    flag = "true";
                }
            }
        }
        if (flag.equals("false")){
            return BaseVO.error("您无权进行确认");
        }


        inspection.setStatus(1);
        inspection.setUpdated_at(System.currentTimeMillis() / 1000);
        if (StringUtils.isNoneBlank(nxpInspection.getPart_no())){
            inspection.setPart_no(nxpInspection.getPart_no());
        }
        nxpInspectionMapper.updateNxpInspection(inspection);
        // 通过设备id查询设备，并且设置设备维修状态为正常
        Long facility_id = inspection.getFacility_id();
        NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(facility_id);
        nxpFacility.setInspection_status(1);
        nxpFacilityMapper.updateNxpFacility(nxpFacility);

        NxpInspectionService nxpInspectionService = new NxpInspectionService();
        nxpInspectionService.setInspection_id(inspection.getId());
        List<NxpInspectionService> nxpInspectionServices = nxpInspectionServiceMapper.selectNxpInspectionServiceList(nxpInspectionService);
        for (NxpInspectionService inspectionService : nxpInspectionServices) {
            if (inspectionService.getEnd_time() == null) {
                NxpInspectionService nxpInspectionService1 = new NxpInspectionService();
                nxpInspectionService1.setEnd_time(new Date());
                nxpInspectionService1.setStatus(1);
                nxpInspectionService1.setId(inspectionService.getId());
                nxpInspectionServiceMapper.updateNxpInspectionService(nxpInspectionService1);
            }
        }

        //更新该报修相关的所有消息为已读
        nxpAppMessageMapper.updateNxpAppMessageByInspectionId(Long.valueOf(nxpInspection.getInspection_id()));

        return BaseVO.success();
    }

    @Override
    @Transactional
    public BaseVO updateInspectionServiceStatus(NxpInspection nxpInspection) {
        NxpInspection inspection = nxpInspectionMapper.selectNxpInspectionById(Long.valueOf(nxpInspection.getInspection_id()));
        if (inspection == null){
            return BaseVO.error("该维修不存在");
        }
        inspection.setStatus(2);
        inspection.setUpdated_at(new Date().getTime() / 1000);
        nxpInspectionMapper.updateNxpInspection(inspection);
        // 通过设备id查询设备
        Long facility_id = inspection.getFacility_id();
        NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(facility_id);
        // 设置设备表的维修状态为维修中
        nxpFacility.setInspection_status(2);
        nxpFacilityMapper.updateNxpFacility(nxpFacility);
        return BaseVO.success();
    }

    @Override
    public BaseVO inspectionServiceRecord(NxpInspectionService nxpInspectionService) {
        List<NxpInspectionService> nxpInspectionServices = nxpInspectionServiceMapper.selectNxpInspectionServiceList(nxpInspectionService);
        for (NxpInspectionService inspectionService : nxpInspectionServices) {
            // 维修图片
            String fileId = inspectionService.getFile_id();
            List<String> filePath = new ArrayList<>();
            if (StringUtils.isNotBlank(fileId)){
                if (fileId.indexOf(",") != -1){
                    String[] split = fileId.split(",");
                    List<NxpInspectionFile> nxpInspectionFiles = nxpInspectionFileMapper.selectByBatchIds(split);
                    for (NxpInspectionFile nxpInspectionFile : nxpInspectionFiles) {
                        filePath.add(nxpInspectionFile.getFile_path());
                    }
                }else {
                    NxpInspectionFile nxpInspectionFile = nxpInspectionFileMapper.selectNxpInspectionFileById(Long.valueOf(fileId));
                    filePath.add(nxpInspectionFile.getFile_path());
                }
            }
            inspectionService.setRepair_file_paths(filePath);
            // 维修设备相关信息
            Long facilityId = inspectionService.getFacility_id();
            NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(facilityId);
            inspectionService.setFacility_name(nxpFacility.getName());
//            Long headUser = nxpFacility.getHead_user();
            Long userInfoId = inspectionService.getUser_info_id();
            NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(userInfoId);
            inspectionService.setHead_user(nxpUserInfo.getChinese_name());
            // 查询position和content
            Long position_id = inspectionService.getPosition_id();
            NxpInspectionPosition nxpInspectionPosition = nxpInspectionPositionMapper.selectNxpInspectionPositionById(position_id);
            if (nxpInspectionPosition != null){
                inspectionService.setPosition(nxpInspectionPosition.getPosition());
            }
            Long content_id = inspectionService.getContent_id();
            NxpInspectionContent nxpInspectionContent = nxpInspectionContentMapper.selectNxpInspectionContentById(content_id);
            if (nxpInspectionContent != null){
                inspectionService.setContent(nxpInspectionContent.getContent());
            }
        }
        return BaseVO.success(nxpInspectionServices);
    }

    @Override
    public BaseVO inspectionAboutReason() {
        NxpInspectionAboutReason nxpInspectionAboutReason = new NxpInspectionAboutReason();
        nxpInspectionAboutReason.setStatus("1");
        List<NxpInspectionAboutReason> nxpInspectionAboutReasons = nxpInspectionAboutReasonMapper.selectNxpInspectionAboutReasonList(nxpInspectionAboutReason);
        return BaseVO.success(nxpInspectionAboutReasons);
    }

    @Override
    public BaseVO inspectionMaintenanceReason() {
        NxpInspectionMaintenanceReason nxpInspectionMaintenanceReason = new NxpInspectionMaintenanceReason();
        nxpInspectionMaintenanceReason.setStatus(1);
        List<NxpInspectionMaintenanceReason> nxpInspectionMaintenanceReasons = nxpInspectionMaintenanceReasonMapper.selectNxpInspectionMaintenanceReasonList(nxpInspectionMaintenanceReason);
        return BaseVO.success(nxpInspectionMaintenanceReasons);
    }

    @Override
    public BaseVO inspectionListByFacilityId(NxpInspection nxpInspection) {
        // 1.校验参数
        if (nxpInspection.getFacility_id() == null){
            return BaseVO.error("设备id为必传参数");
        }
        List<NxpInspection> inspections = nxpInspectionMapper.selectNxpInspectionList01(nxpInspection);
        // 查询设备相关信息
        Long facilityId = nxpInspection.getFacility_id();
        NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(facilityId);
        if (nxpFacility != null){
            // 处理设备负责人
            Long headUser = nxpFacility.getHead_user();
            NxpUserInfo userInfo = nxpUserInfoMapper.selectNxpUserInfoById(headUser);
            if (userInfo != null){
                nxpFacility.setHead_user_name(userInfo.getChinese_name());
            }
            // 处理设备工序
            String process = nxpFacility.getProcess();
            NxpMachineProcess machineProcess = nxpMachineProcessMapper.selectNxpMachineProcessById(Long.valueOf(process));
            if (machineProcess != null){
                nxpFacility.setProcess(machineProcess.getName());
                nxpFacility.setProcess_id(machineProcess.getId().toString());
            }
        }
        for (NxpInspection inspection : inspections) {
            // 处理questionTypeName
            Long id = Long.valueOf(inspection.getQuestion_type());
            NxpInspectionQuestionType nxpInspectionQuestionType = nxpInspectionQuestionTypeMapper.selectNxpInspectionQuestionTypeById(id);
            if (nxpInspectionQuestionType != null){
                inspection.setQuestionTypeName(nxpInspectionQuestionType.getName());

            }
//            NxpMaintainType nxpMaintainType = nxpMaintainTypeMapper.selectNxpMaintainTypeById(Long.valueOf(inspection.getQuestion_type()));
//            if (nxpMaintainType != null){
//            }
            // 处理设备负责人
            Long user_info_id = inspection.getUser_info_id();
            NxpUserInfo userInfo1 = nxpUserInfoMapper.selectNxpUserInfoById(user_info_id);
            if (userInfo1 != null){
                inspection.setUser_info_name(userInfo1.getChinese_name());
            }
            // 处理维修id
            NxpInspectionService nxpInspectionService = new NxpInspectionService();
            nxpInspectionService.setInspection_id(inspection.getId());
//            nxpInspectionService.setFacility_id(facilityId);
            List<NxpInspectionService> nxpInspectionServices = nxpInspectionServiceMapper.selectNxpInspectionServiceList(nxpInspectionService);
            String serviceId = "";
            for (NxpInspectionService inspectionService : nxpInspectionServices) {
                serviceId = serviceId + inspectionService.getId() + ",";
            }
            if (StringUtils.isNotBlank(serviceId)){
                if (serviceId.indexOf(",") != -1){
                    inspection.setInspection_service_id(serviceId.substring(0,serviceId.length() - 1));
                }else {
                    inspection.setInspection_service_id(serviceId);
                }
            }
            // 处理图片
            String repairFileId = inspection.getRepair_file_id();
            List<String> filePath = new ArrayList<>();
            if (StringUtils.isNoneBlank(repairFileId)){
                if (repairFileId.indexOf(",") != -1){
                    // 多个
                    String[] fileIds = repairFileId.split(",");
                    List<NxpInspectionFile> nxpInspectionFiles = nxpInspectionFileMapper.selectByBatchIds(fileIds);
                    for (NxpInspectionFile nxpInspectionFile : nxpInspectionFiles) {
                        filePath.add(nxpInspectionFile.getFile_path());
                    }
                }else {
                    // 单个
                    NxpInspectionFile nxpInspectionFile = nxpInspectionFileMapper.selectNxpInspectionFileById(Long.valueOf(repairFileId));
                    filePath.add(nxpInspectionFile.getFile_path());
                }
            }
            inspection.setRepair_file_paths(filePath);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("facility",nxpFacility);
        map.put("inspections",new PageInfo<>(inspections));
        return BaseVO.success(map);
    }

    @Override
    public BaseVO checkFacilityInspectionStatus(NxpFacility nxpFacility) {
        // 校验参数是否传输
        if (StringUtils.isBlank(nxpFacility.getNumber())){
            return BaseVO.error("设备编号为必传参数");
        }
        // 1. 检查设备是否存在
        nxpFacility.setStatus("1");
        NxpFacility facility = nxpFacilityMapper.selectNxpFacilityByNumber(nxpFacility.getNumber());
        if (facility == null){
            return BaseVO.error("该设备不存在");
        }
        // 2. 检查该设备是否存在未完成的报修
        int count = nxpInspectionMapper.selectNxpInspectionListByFacilityIdCount(facility.getId());
        Map<String,Object> map = new HashMap<>();
        if (count > 0){
            map.put("isInspection",1);
            return BaseVO.success(map);
        }else {
            map.put("isInspection",0);
            return BaseVO.success(map);
        }
    }

    @Override
    public BaseVO inspectionServiceDetails(NxpInspectionService nxpInspectionService) {
        // 1. 校验参数
        if (StringUtils.isBlank(nxpInspectionService.getInspection_service_id())){
            return BaseVO.error("维修ID为必传参数");
        }
        List<NxpInspectionService> nxpInspectionServices = new ArrayList<>();
        // 判断是否是多个维修
        if (nxpInspectionService.getInspection_service_id().indexOf(",") != -1){
            String[] serviceIds = nxpInspectionService.getInspection_service_id().split(",");
            nxpInspectionServices.addAll(nxpInspectionServiceMapper.selectNxpInspectionServiceByIds(serviceIds));
        }else {
            nxpInspectionServices.add(nxpInspectionServiceMapper.selectNxpInspectionServiceById(Long.valueOf(nxpInspectionService.getInspection_service_id())));
        }

        for (NxpInspectionService inspectionService : nxpInspectionServices) {
            // 2. 处理所需设备名称，负责人姓名，报修人姓名，文件内容
            Long facilityId = inspectionService.getFacility_id();
            NxpFacility nxpFacility = nxpFacilityMapper.selectNxpFacilityById(facilityId);
            if (nxpFacility != null) {
                inspectionService.setFacility_name(nxpFacility.getName());
            }
            // 设备负责人
            Long headUser = nxpFacility.getHead_user();
            NxpUserInfo facilityNxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(headUser);
            if (facilityNxpUserInfo != null) {
                inspectionService.setFacility_user_name(facilityNxpUserInfo.getChinese_name());
            }
            // 维修人
            Long userInfoId = inspectionService.getUser_info_id();
            NxpUserInfo nxpUserInfo = nxpUserInfoMapper.selectNxpUserInfoById(userInfoId);
            if (nxpUserInfo != null) {
                inspectionService.setHead_user(nxpUserInfo.getChinese_name());
            }
            String repairFileId = inspectionService.getFile_id();
            if (StringUtils.isNoneBlank(repairFileId)) {
                List<String> filePathList = new ArrayList<>();
                if (repairFileId.indexOf(",") != -1){
                    // 表示多个file
                    String[] fileIds = repairFileId.split(",");
                    // 批量查询file的path
                    List<NxpInspectionFile> files = nxpInspectionFileMapper.selectByBatchIds(fileIds);
                    for (NxpInspectionFile file : files) {
                        filePathList.add(file.getFile_path());
                    }
                    inspectionService.setRepair_file_paths(filePathList);
                }else {
                    NxpInspectionFile nxpInspectionFile = nxpInspectionFileMapper.selectNxpInspectionFileById(Long.valueOf(repairFileId));
                    filePathList.add(nxpInspectionFile.getFile_path());
                    inspectionService.setRepair_file_paths(filePathList);
                }
            }

            // 查询position和content
            Long position_id = inspectionService.getPosition_id();
            NxpInspectionPosition nxpInspectionPosition = nxpInspectionPositionMapper.selectNxpInspectionPositionById(position_id);
            if (nxpInspectionPosition != null){
                inspectionService.setPosition(nxpInspectionPosition.getPosition());
            }
            Long content_id = inspectionService.getContent_id();
            NxpInspectionContent nxpInspectionContent = nxpInspectionContentMapper.selectNxpInspectionContentById(content_id);
            if (nxpInspectionContent != null){
                inspectionService.setContent(nxpInspectionContent.getContent());
            }
            // 处理备件
            Long id = inspectionService.getId();
            NxpInspectionServiceSparepart nxpInspectionServiceSparepart = new NxpInspectionServiceSparepart();
            nxpInspectionServiceSparepart.setInspection_service_id(id);
            List<NxpInspectionServiceSparepart> nxpInspectionServiceSpareparts = nxpInspectionServiceSparepartMapper.selectNxpInspectionServiceSparepartList(nxpInspectionServiceSparepart);
            inspectionService.setSparepart_List(nxpInspectionServiceSpareparts);
        }
        return BaseVO.success(nxpInspectionServices);
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
