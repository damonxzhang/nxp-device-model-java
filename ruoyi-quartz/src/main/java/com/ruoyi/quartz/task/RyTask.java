package com.ruoyi.quartz.task;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{
    @Resource
    private INxpInspectionService nxpInspectionService;
    @Resource
    private IMailService mailService;
    @Resource
    private INxpFacilityService nxpFacilityService;
    @Resource
    private INxpInspectionQuestionTypeService nxpInspectionQuestionTypeService;
    @Resource
    private INxpInspectionServiceService nxpInspectionServiceService;
    @Resource
    private INxpUserInfoService nxpUserInfoService;
    @Resource
    private INxpInspectionPositionService nxpInspectionPositionService;
    @Resource
    private INxpInspectionContentService nxpInspectionContentService;
    @Resource
    private INxpMachineProcessService nxpMachineProcessService;
    @Resource
    private INxpInspectionEmailService nxpInspectionEmailService;

    /**
     * 早晨8:05定时发送邮件
     */
    public void sendAmEmail() {
        // 获取当天早晨8点
        String endTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getEndTime());
        // 获取早晨8点前12小时
        String beginTime = DateUtils.getDate(endTime, -12);
        // 查询这个时间段内的报修记录
        NxpInspection nxpInspection = new NxpInspection();
        nxpInspection.setRepair_start_time(beginTime);
        nxpInspection.setRepair_end_time(endTime);
        // 总报修次数，包括所有状态
        List<NxpInspection> nxpInspections = nxpInspectionService.selectNxpInspectionListByTiming(nxpInspection);
        // 查询已完成次数
        nxpInspection.setStatus(1);
        List<NxpInspection> completedNums = nxpInspectionService.selectNxpInspectionListByTiming(nxpInspection);
        // 查询待维修次数
        nxpInspection.setStatus(0);
        List<NxpInspection> toBeRepairedNums = nxpInspectionService.selectNxpInspectionListByTiming(nxpInspection);
        // 查询维修中次数
        nxpInspection.setStatus(2);
        List<NxpInspection> underRepairNums = nxpInspectionService.selectNxpInspectionListByTiming(nxpInspection);
        // 查询待确认次数
        nxpInspection.setStatus(3);
        List<NxpInspection> toBeConfirmedNums = nxpInspectionService.selectNxpInspectionListByTiming(nxpInspection);

        int allNums1 = 0;
        int completedNums1 = 0;
        int toBeRepairedNums1 = 0;
        int underRepairNums1 = 0;
        int toBeConfirmedNums1 = 0;
        if (nxpInspections != null) {
            allNums1 = nxpInspections.size();
        }
        if (completedNums != null) {
            completedNums1 = completedNums.size();
        }
        if (toBeRepairedNums != null) {
            toBeRepairedNums1 = toBeRepairedNums.size();
        }
        if (underRepairNums != null) {
            underRepairNums1 = underRepairNums.size();
        }
        if (toBeConfirmedNums != null) {
            toBeConfirmedNums1 = toBeConfirmedNums.size();
        }
        // 处理列表
        for (NxpInspection inspection : nxpInspections) {
            // 设备表id
            Long facilityId = inspection.getFacility_id();
            // 通过设备表id查询设备
            NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(facilityId);
            if (nxpFacility != null) {
                // 设备名称
                inspection.setFacilityNumber(nxpFacility.getNumber());
                // 设备所属者
                Long headUser = nxpFacility.getHead_user();
                NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(headUser);
                inspection.setHead_user(nxpUserInfo.getChinese_name());
                // 查询工序
                String process = nxpFacility.getProcess();
                NxpMachineProcess nxpMachineProcess = nxpMachineProcessService.selectNxpMachineProcessById(Long.valueOf(process));
                inspection.setFacilityProcess(nxpMachineProcess.getName());
            }
            // 根据问题类型id查询问题
            Integer questionType = inspection.getQuestion_type();
            NxpInspectionQuestionType nxpInspectionQuestionType = nxpInspectionQuestionTypeService.selectNxpInspectionQuestionTypeById(questionType.longValue());
            if (nxpInspectionQuestionType != null) {
                inspection.setQuestionTypeName(nxpInspectionQuestionType.getName());
            }
            // 当前状态
            Integer status = inspection.getStatus();
            String statusName = "";
            switch (status) {
                case 0:
                    statusName = "待处理";
                    break;
                case 1:
                    statusName = "正常";
                    break;
                case 2:
                    statusName = "维修中";
                    break;
                case 3:
                    statusName = "待确认";
                    break;
            }
            inspection.setStatusName(statusName);
            // 查询该报修下的维修
            NxpInspectionService nxpInspectionService = new NxpInspectionService();
            nxpInspectionService.setInspection_id(inspection.getId());
            List<NxpInspectionService> nxpInspectionServices = nxpInspectionServiceService.selectNxpInspectionServiceListTiming(nxpInspectionService);
            if (nxpInspectionServices != null && nxpInspectionServices.size() > 0) {
                NxpInspectionService inspectionService = nxpInspectionServices.get(0);
                // 获取维修人姓名
                Long user_info_id = inspectionService.getUser_info_id();
                NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(user_info_id);
                inspectionService.setUser_info_name(nxpUserInfo.getChinese_name());
                // 处理维修总时长
                if (inspectionService.getEnd_time() != null && inspectionService.getService_time() != null) {
                    String datePoor = DateUtils.getDatePoor(inspectionService.getEnd_time(), inspectionService.getService_time());
                    inspectionService.setDatePoor(datePoor);
                }
                // 维修位置
                Long positionId = inspectionService.getPosition_id();
                NxpInspectionPosition nxpInspectionPosition = nxpInspectionPositionService.selectNxpInspectionPositionById(positionId);
                if (nxpInspectionPosition != null) {
                    inspectionService.setPositionName(nxpInspectionPosition.getPosition());
                }
                // 维修内容
                Long contentId = inspectionService.getContent_id();
                NxpInspectionContent nxpInspectionContent = nxpInspectionContentService.selectNxpInspectionContentById(contentId);
                if (nxpInspectionContent != null) {
                    inspectionService.setContentName(nxpInspectionContent.getContent());
                }
                inspection.setNxpInspectionService(inspectionService);
            }
        }

        // 排序
        nxpInspections = nxpInspections.stream().sorted(Comparator.comparing(NxpInspection::getFacilityProcess).thenComparing(Comparator.comparing(NxpInspection::getRepair_time)).reversed()).collect(Collectors.toList());
        // 遍历数据填充
        String datas = "";
        for (NxpInspection inspection : nxpInspections) {
            // 维修时间
            String serviceTime = "";
            // 维修完成时间
            String serviceEndTime = "";
            // 维修总时长
            String dateDoor = "";
            // 维修人
            String serviceUserInfoName = "";
            // 维修人班别
            String serviceUserInfoShift = "";
            // 维修位置
            String position = "";
            // 维修内容
            String contentName = "";
            // 维修内容
            String serviceContentName = "";
            if (inspection.getNxpInspectionService() != null) {
                if (inspection.getNxpInspectionService().getService_time() != null) {
                    serviceTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, inspection.getNxpInspectionService().getService_time());
                }
                if (inspection.getNxpInspectionService().getEnd_time() != null) {
                    serviceEndTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, inspection.getNxpInspectionService().getEnd_time());
                }
                if (StringUtils.isNotBlank(inspection.getNxpInspectionService().getDatePoor())) {
                    dateDoor = inspection.getNxpInspectionService().getDatePoor();
                }
                if (StringUtils.isNotBlank(inspection.getNxpInspectionService().getUser_info_name())) {
                    serviceUserInfoName = inspection.getNxpInspectionService().getUser_info_name();
                    //获取班别
                    NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(inspection.getNxpInspectionService().getUser_info_id());
                    switch (nxpUserInfo.getShift()){
                        case 1:
                            serviceUserInfoShift = "A";
                            break;
                        case 2:
                            serviceUserInfoShift = "B";
                            break;
                        case 3:
                            serviceUserInfoShift = "C";
                            break;
                        case 4:
                            serviceUserInfoShift = "D";
                            break;
                        case 5:
                            serviceUserInfoShift = "R";
                            break;
                        case 6:
                            serviceUserInfoShift = "AB";
                            break;
                        case 7:
                            serviceUserInfoShift = "CD";
                            break;

                    }
                }
                if (StringUtils.isNotBlank(inspection.getNxpInspectionService().getPositionName())) {
                    position = inspection.getNxpInspectionService().getPositionName();
                }
                if (StringUtils.isNotBlank(inspection.getNxpInspectionService().getContentName())) {
                    contentName = inspection.getNxpInspectionService().getContentName();
                }
                if (StringUtils.isNotBlank(inspection.getNxpInspectionService().getDescription())) {
                    serviceContentName = inspection.getNxpInspectionService().getDescription();
                }
            }
            // 产线异常
            String operate = "";
            if (inspection.getOperate() != null && inspection.getOperate() == 1) {
                operate = "√";
            }
            datas = datas + "<tr>\n" +
                    "<td style=\"color: rgb(38,116,207)\"><span style=\"border-bottom: 1px solid rgb(38,116,207);\">" + inspection.getFacilityNumber() + "</span></td>\n" +
                    "<td>" + inspection.getFacilityProcess() + "</td>\n" +
                    "<td>" + inspection.getQuestionTypeName() + "</td>\n" +
                    "<td style=\"text-align:left;\">" + inspection.getExplanation() + "</td>\n" ;

            switch (inspection.getStatusName()) {
                case "待处理":
                    datas = datas + "<td style=\"color:rgb(197, 0, 0)\">" + inspection.getStatusName() + "</td>\n" ;
                    break;
                case "正常":
                    datas = datas + "<td style=\"color:rgb(0, 196, 58)\">" + inspection.getStatusName() + "</td>\n" ;
                    break;
                case "维修中":
                    datas = datas + "<td style=\"color:rgb(255, 188, 28)\">" + inspection.getStatusName() + "</td>\n" ;
                    break;
                case "待确认":
                    datas = datas + "<td style=\"color:rgb(31, 114, 192)\">" + inspection.getStatusName() + "</td>\n" ;
                    break;
            }

            datas = datas + "<td>" + DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, inspection.getRepair_time()) + "</td>\n" +
                    "<td>" + serviceTime + "</td>\n" +
                    "<td>" + serviceEndTime + "</td>\n" +
                    "<td>" + dateDoor + "</td>\n" +
                    "<td>" + serviceUserInfoName + "</td>\n" +
                    "<td>" + serviceUserInfoShift + "</td>\n" +
                    "<td>" + inspection.getHead_user() + "</td>\n" +
                    "<td>" + position + "</td>\n" +
                    "<td>" + contentName + "</td>\n" +
                    "<td style=\"text-align:left;\">" + serviceContentName + "</td>\n" +
                    "<td>" + operate + "</td>\n" +
                    "</tr>\n";
        }

        String contentHtml = "<style>\n" +
                "      *{\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "        list-style: none;\n" +
                "      }\n" +
                "      .header ul li{\n" +
                "        margin: 0 96px 0 12px;\n" +
                "        font-size: 16px;\n" +
                "      }\n" +
                "      td{\n" +
                "        text-align:center;\n" +
                "        vertical-align:middle;\n" +
                "        height: 30px;\n" +
                "      }\n" +
                "    </style>" +
                "<body>\n" +
                "  <div class=\"box\" style=\"width: 94%;margin-left: 3%;\">\n" +
                "    <div class=\"title\" style=\"text-align: center;margin-bottom: 30px;\">\n" +
                "      <h2>" + DateUtils.getDate() + " 夜班 设备维修报告</h2>\n" +
                "    </div>\n" +
                "    <div class=\"header\" style=\"width: 94%;margin-left: 3%;\">\n" +
//                "      <div style=\"display: flex;line-height: 72px;border-top: 1px solid #000;border-bottom: 1px solid #000;\">\n" +
//                "        <div>发生报修: " + allNums1 + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  </div>\n" +
//                "        <div style=\"color: rgb(0, 196, 58);\">已完成: " + completedNums1 + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  </div>\n" +
//                "        <div style=\"color: rgb(197, 0, 0);\">待维修: " + toBeRepairedNums1 + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  </div>\n" +
//                "        <div style=\"color: rgb(255, 188, 28);\">维修中: " + underRepairNums1 + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  </div>\n" +
//                "        <div style=\"color: rgb(31, 114, 192);\">待确认: " + toBeConfirmedNums1 + "</div>\n" +
//                "      </div>\n" +
                "      <table border=\"2\" style=\"border-style:none;width: 100%\">\n" +
                "        <tr>\n" +
                "          <th>发生报修: " + allNums1 + "</th>\n" +
                "          <th style=\"color: rgb(0, 196, 58);\">已完成：" + completedNums1 + "</th>\n" +
                "          <th style=\"color: rgb(197, 0, 0);\">待维修: " + toBeRepairedNums1 + "</th>\n" +
                "          <th style=\"color: rgb(255, 188, 28);\">维修中: " + underRepairNums1 + "</th>\n" +
                "          <th style=\"color: rgb(31, 114, 192);\">待确认: " + toBeConfirmedNums1 + "</th>\n" +
                "        </tr>\n" +
                "      </table>\n" +
                "      <h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h3>\n" +
                "    </div>\n" +
                "    <div class=\"section\" style=\"width: 94%;margin-left: 3%;\">\n" +
                "      <table border=\"2\" style=\"border-collapse:collapse;width: 100%\">\n" +
                "        <tr>\n" +
                "          <th style=\"width:80px\">设备号</th>\n" +
                "          <th>工序</th>\n" +
                "          <th>问题类型</th>\n" +
                "          <th>报修情况说明</th>\n" +
                "          <th>当前状态</th>\n" +
                "          <th>报修时间</th>\n" +
                "          <th>维修时间</th>\n" +
                "          <th>完成时间</th>\n" +
                "          <th>维修时长</th>\n" +
                "          <th>维修人</th>\n" +
                "          <th>班别</th>\n" +
                "          <th>设备owner</th>\n" +
                "          <th>维修位置</th>\n" +
                "          <th>维修内容</th>\n" +
                "          <th>维修情况说明</th>\n" +
                "          <th>产线异常</th>\n" +
                "        </tr>\n" + datas +
                "      </table>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</body>";
        // 调用发送邮件服务
//        获取邮件人员列表
        String userList = "";
        NxpInspectionEmail nxpInspectionEmail = new NxpInspectionEmail();
        List<NxpInspectionEmail> nxpInspectionEmails = nxpInspectionEmailService.selectNxpInspectionEmailList(nxpInspectionEmail);
        if (nxpInspectionEmails != null){
            for (NxpInspectionEmail inspectionEmail : nxpInspectionEmails) {
                userList = userList + inspectionEmail.getEmail() + ",";
            }
        }

        mailService.sendHtmlMail(userList, DateUtils.getYesterdayDate() + " 夜班 报修记录", contentHtml);
    }

    /**
     * 20：05发送邮件
     */
    public void sendPmEmail() {
        // 获取当天20点
        String endTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getNightTime());
        // 获取前12小时时间
        String beginTime = DateUtils.getDate(endTime, -12);
        // 查询这个时间段内的报修记录
        NxpInspection nxpInspection = new NxpInspection();
        nxpInspection.setRepair_start_time(beginTime);
        nxpInspection.setRepair_end_time(endTime);
        // 总报修次数，包括所有状态
        List<NxpInspection> nxpInspections = nxpInspectionService.selectNxpInspectionListByTiming(nxpInspection);
        // 查询已完成次数
        nxpInspection.setStatus(1);
        List<NxpInspection> completedNums = nxpInspectionService.selectNxpInspectionListByTiming(nxpInspection);
        // 查询待维修次数
        nxpInspection.setStatus(0);
        List<NxpInspection> toBeRepairedNums = nxpInspectionService.selectNxpInspectionListByTiming(nxpInspection);
        // 查询维修中次数
        nxpInspection.setStatus(2);
        List<NxpInspection> underRepairNums = nxpInspectionService.selectNxpInspectionListByTiming(nxpInspection);
        // 查询待确认次数
        nxpInspection.setStatus(3);
        List<NxpInspection> toBeConfirmedNums = nxpInspectionService.selectNxpInspectionListByTiming(nxpInspection);

        int allNums1 = 0;
        int completedNums1 = 0;
        int toBeRepairedNums1 = 0;
        int underRepairNums1 = 0;
        int toBeConfirmedNums1 = 0;
        if (nxpInspections != null) {
            allNums1 = nxpInspections.size();
        }
        if (completedNums != null) {
            completedNums1 = completedNums.size();
        }
        if (toBeRepairedNums != null) {
            toBeRepairedNums1 = toBeRepairedNums.size();
        }
        if (underRepairNums != null) {
            underRepairNums1 = underRepairNums.size();
        }
        if (toBeConfirmedNums != null) {
            toBeConfirmedNums1 = toBeConfirmedNums.size();
        }
        // 处理列表
        for (NxpInspection inspection : nxpInspections) {
            // 设备表id
            Long facilityId = inspection.getFacility_id();
            // 通过设备表id查询设备
            NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(facilityId);
            if (nxpFacility != null) {
                // 设备名称
                inspection.setFacilityNumber(nxpFacility.getNumber());
                // 设备所属者
                Long headUser = nxpFacility.getHead_user();
                NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(headUser);
                inspection.setHead_user(nxpUserInfo.getChinese_name());
                // 查询工序
                String process = nxpFacility.getProcess();
                NxpMachineProcess nxpMachineProcess = nxpMachineProcessService.selectNxpMachineProcessById(Long.valueOf(process));
                inspection.setFacilityProcess(nxpMachineProcess.getName());
            }
            // 根据问题类型id查询问题
            Integer questionType = inspection.getQuestion_type();
            NxpInspectionQuestionType nxpInspectionQuestionType = nxpInspectionQuestionTypeService.selectNxpInspectionQuestionTypeById(questionType.longValue());
            if (nxpInspectionQuestionType != null) {
                inspection.setQuestionTypeName(nxpInspectionQuestionType.getName());
            }
            // 当前状态
            Integer status = inspection.getStatus();
            String statusName = "";
            switch (status) {
                case 0:
                    statusName = "待处理";
                    break;
                case 1:
                    statusName = "正常";
                    break;
                case 2:
                    statusName = "维修中";
                    break;
                case 3:
                    statusName = "待确认";
                    break;
            }
            inspection.setStatusName(statusName);
            // 查询该报修下的维修
            NxpInspectionService nxpInspectionService = new NxpInspectionService();
            nxpInspectionService.setInspection_id(inspection.getId());
            List<NxpInspectionService> nxpInspectionServices = nxpInspectionServiceService.selectNxpInspectionServiceListTiming(nxpInspectionService);
            if (nxpInspectionServices != null && nxpInspectionServices.size() > 0) {
                NxpInspectionService inspectionService = nxpInspectionServices.get(0);
                // 获取维修人姓名
                Long user_info_id = inspectionService.getUser_info_id();
                NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(user_info_id);
                inspectionService.setUser_info_name(nxpUserInfo.getChinese_name());
                // 处理维修总时长
                if (inspectionService.getEnd_time() != null && inspectionService.getService_time() != null) {
                    String datePoor = DateUtils.getDatePoor(inspectionService.getEnd_time(), inspectionService.getService_time());
                    inspectionService.setDatePoor(datePoor);
                }
                // 维修位置
                Long positionId = inspectionService.getPosition_id();
                NxpInspectionPosition nxpInspectionPosition = nxpInspectionPositionService.selectNxpInspectionPositionById(positionId);
                if (nxpInspectionPosition != null) {
                    inspectionService.setPositionName(nxpInspectionPosition.getPosition());
                }
                // 维修内容
                Long contentId = inspectionService.getContent_id();
                NxpInspectionContent nxpInspectionContent = nxpInspectionContentService.selectNxpInspectionContentById(contentId);
                if (nxpInspectionContent != null) {
                    inspectionService.setContentName(nxpInspectionContent.getContent());
                }
                inspection.setNxpInspectionService(inspectionService);
            }
        }

        // 排序
        nxpInspections = nxpInspections.stream().sorted(Comparator.comparing(NxpInspection::getFacilityProcess).thenComparing(Comparator.comparing(NxpInspection::getRepair_time)).reversed()).collect(Collectors.toList());
        // 遍历数据填充
        String datas = "";
        for (NxpInspection inspection : nxpInspections) {
            // 维修时间
            String serviceTime = "";
            // 维修完成时间
            String serviceEndTime = "";
            // 维修总时长
            String dateDoor = "";
            // 维修人
            String serviceUserInfoName = "";
            // 维修人班别
            String serviceUserInfoShift = "";
            // 维修位置
            String position = "";
            // 维修内容
            String contentName = "";
            // 维修内容
            String serviceContentName = "";
            if (inspection.getNxpInspectionService() != null) {
                if (inspection.getNxpInspectionService().getService_time() != null) {
                    serviceTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, inspection.getNxpInspectionService().getService_time());
                }
                if (inspection.getNxpInspectionService().getEnd_time() != null) {
                    serviceEndTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, inspection.getNxpInspectionService().getEnd_time());
                }
                if (StringUtils.isNotBlank(inspection.getNxpInspectionService().getDatePoor())) {
                    dateDoor = inspection.getNxpInspectionService().getDatePoor();
                }
                if (StringUtils.isNotBlank(inspection.getNxpInspectionService().getUser_info_name())) {
                    serviceUserInfoName = inspection.getNxpInspectionService().getUser_info_name();
                    //获取班别
                    NxpUserInfo nxpUserInfo = nxpUserInfoService.selectNxpUserInfoById(inspection.getNxpInspectionService().getUser_info_id());
                    switch (nxpUserInfo.getShift()){
                        case 1:
                            serviceUserInfoShift = "A";
                            break;
                        case 2:
                            serviceUserInfoShift = "B";
                            break;
                        case 3:
                            serviceUserInfoShift = "C";
                            break;
                        case 4:
                            serviceUserInfoShift = "D";
                            break;
                        case 5:
                            serviceUserInfoShift = "R";
                            break;
                        case 6:
                            serviceUserInfoShift = "AB";
                            break;
                        case 7:
                            serviceUserInfoShift = "CD";
                            break;

                    }
                }
                if (StringUtils.isNotBlank(inspection.getNxpInspectionService().getPositionName())) {
                    position = inspection.getNxpInspectionService().getPositionName();
                }
                if (StringUtils.isNotBlank(inspection.getNxpInspectionService().getContentName())) {
                    contentName = inspection.getNxpInspectionService().getContentName();
                }
                if (StringUtils.isNotBlank(inspection.getNxpInspectionService().getDescription())) {
                    serviceContentName = inspection.getNxpInspectionService().getDescription();
                }
            }
            // 产线异常
            String operate = "";
            if (inspection.getOperate() != null && inspection.getOperate() == 1) {
                operate = "√";
            }
            datas = datas + "<tr>\n" +
                    "<td style=\"color: rgb(38,116,207)\"><span style=\"border-bottom: 1px solid rgb(38,116,207);\">" + inspection.getFacilityNumber() + "</span></td>\n" +
                    "<td>" + inspection.getFacilityProcess() + "</td>\n" +
                    "<td>" + inspection.getQuestionTypeName() + "</td>\n" +
                    "<td style=\"text-align:left;\">" + inspection.getExplanation() + "</td>\n" ;

            switch (inspection.getStatusName()) {
                case "待处理":
                    datas = datas + "<td style=\"color:rgb(197, 0, 0)\">" + inspection.getStatusName() + "</td>\n" ;
                    break;
                case "正常":
                    datas = datas + "<td style=\"color:rgb(0, 196, 58)\">" + inspection.getStatusName() + "</td>\n" ;
                    break;
                case "维修中":
                    datas = datas + "<td style=\"color:rgb(255, 188, 28)\">" + inspection.getStatusName() + "</td>\n" ;
                    break;
                case "待确认":
                    datas = datas + "<td style=\"color:rgb(31, 114, 192)\">" + inspection.getStatusName() + "</td>\n" ;
                    break;
            }

            datas = datas + "<td>" + DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, inspection.getRepair_time()) + "</td>\n" +
                    "<td>" + serviceTime + "</td>\n" +
                    "<td>" + serviceEndTime + "</td>\n" +
                    "<td>" + dateDoor + "</td>\n" +
                    "<td>" + serviceUserInfoName + "</td>\n" +
                    "<td>" + serviceUserInfoShift + "</td>\n" +
                    "<td>" + inspection.getHead_user() + "</td>\n" +
                    "<td>" + position + "</td>\n" +
                    "<td>" + contentName + "</td>\n" +
                    "<td style=\"text-align:left;\">" + serviceContentName + "</td>\n" +
                    "<td>" + operate + "</td>\n" +
                    "</tr>\n";
        }

        String contentHtml = "<style>\n" +
                "      *{\n" +
                "        margin: 0;\n" +
                "        padding: 0;\n" +
                "        list-style: none;\n" +
                "      }\n" +
                "      .header ul li{\n" +
                "        margin: 0 96px 0 12px;\n" +
                "        font-size: 16px;\n" +
                "      }\n" +
                "      td{\n" +
                "        text-align:center;\n" +
                "        vertical-align:middle;\n" +
                "        height: 30px;\n" +
                "      }\n" +
                "    </style>" +
                "<body>\n" +
                "  <div class=\"box\" style=\"width: 94%;margin-left: 3%;\">\n" +
                "    <div class=\"title\" style=\"text-align: center;margin-bottom: 30px;\">\n" +
                "      <h2>" + DateUtils.getDate() + "白班 设备维修报告</h2>\n" +
                "    </div>\n" +
                "    <div class=\"header\" style=\"width: 94%;margin-left: 3%;\">\n" +
//                "      <div style=\"display: flex;line-height: 72px;border-top: 1px solid #000;border-bottom: 1px solid #000;\">\n" +
//                "        <div>发生报修: " + allNums1 + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  " +
//                "        已完成: " + completedNums1 + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  " +
//                "        待维修: " + toBeRepairedNums1 + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " +
//                "        维修中: " + underRepairNums1 + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " +
//                "        待确认: " + toBeConfirmedNums1 + "</div>\n" +
                "      <table border=\"2\" style=\"border-style:none;width: 100%\">\n" +
                "        <tr>\n" +
                "          <th>发生报修: " + allNums1 + "</th>\n" +
                "          <th style=\"color: rgb(0, 196, 58);\">已完成：" + completedNums1 + "</th>\n" +
                "          <th style=\"color: rgb(197, 0, 0);\">待维修: " + toBeRepairedNums1 + "</th>\n" +
                "          <th style=\"color: rgb(255, 188, 28);\">维修中: " + underRepairNums1 + "</th>\n" +
                "          <th style=\"color: rgb(31, 114, 192);\">待确认: " + toBeConfirmedNums1 + "</th>\n" +
                "        </tr>\n" +
                "      </table>\n" +
//                "      </div>\n" +
                "      <h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h3>\n" +
                "    </div>\n" +
                "    <div class=\"section\" style=\"width: 94%;margin-left: 3%;\">\n" +
                "      <table border=\"2\" style=\"border-collapse:collapse;width: 100%\">\n" +
                "        <tr>\n" +
                "          <th style=\"width:80px\">设备号</th>\n" +
                "          <th>工序</th>\n" +
                "          <th>问题类型</th>\n" +
                "          <th>报修情况说明</th>\n" +
                "          <th>当前状态</th>\n" +
                "          <th>报修时间</th>\n" +
                "          <th>维修时间</th>\n" +
                "          <th>完成时间</th>\n" +
                "          <th>维修时长</th>\n" +
                "          <th>维修人</th>\n" +
                "          <th>班别</th>\n" +
                "          <th>设备owner</th>\n" +
                "          <th>维修位置</th>\n" +
                "          <th>维修内容</th>\n" +
                "          <th>维修情况说明</th>\n" +
                "          <th>产线异常</th>\n" +
                "        </tr>\n" + datas +
                "      </table>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</body>";
        // 调用发送邮件服务
//        获取邮件人员列表
        String userList = "";
        NxpInspectionEmail nxpInspectionEmail = new NxpInspectionEmail();
        List<NxpInspectionEmail> nxpInspectionEmails = nxpInspectionEmailService.selectNxpInspectionEmailList(nxpInspectionEmail);
        if (nxpInspectionEmails != null){
            for (NxpInspectionEmail inspectionEmail : nxpInspectionEmails) {
                userList = userList + inspectionEmail.getEmail() + ",";
            }
        }

        mailService.sendHtmlMail(userList, DateUtils.getDate() + " 白班 维修记录", contentHtml);
    }
}
