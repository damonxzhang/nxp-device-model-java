package com.ruoyi.web.controller.api;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MultipartFileUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.VO.UpdateCheckListVO;
import com.ruoyi.system.mapper.NxpAppMessageMapper;
import com.ruoyi.system.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Yancheng Liu
 * @create 2022-09-01 15:09
 */
@RestController
@RequestMapping("/api/check")
public class AppCheckApiController extends BaseController {

    @Autowired
    private INxpCheckListService nxpCheckListService;
    @Autowired
    private INxpMachineProcessService nxpMachineProcessService;
    @Autowired
    private INxpInspectionService nxpInspectionService;
    @Autowired
    private INxpFacilityService nxpFacilityService;
    @Autowired
    private INxpInspectionPmService nxpInspectionPmService;
    @Autowired
    private INxpBindRelationshipService nxpBindRelationshipService;
    @Autowired
    private INxpCheckContentService nxpCheckContentService;
    @Autowired
    private INxpInspectionPositionService nxpInspectionPositionService;
    @Autowired
    private INxpInspectionContentService nxpInspectionContentService;
    @Autowired
    private INxpSwcJobContentService nxpSwcJobContentService;
    @Autowired
    private INxpInspectionPmGroupService nxpInspectionPmGroupService;
    @Autowired
    private INxpInspectionPmGroupItemService nxpInspectionPmGroupItemService;
    @Autowired
    private INxpCheckListFileService nxpCheckListFileService;
    @Autowired
    private ServerConfig serverConfig;
    @Resource
    private INxpCheckListItemService nxpCheckListItemService;
    @Resource
    private INxpAppMessageService nxpAppMessageService;

    /**
     * 检查列表
     * @param nxpCheckList
     * @return
     */
    @PostMapping("/checkList")
    public TableDataInfo checkList(@RequestBody NxpCheckList nxpCheckList){
        if (nxpCheckList.getPageNum() == null){
            nxpCheckList.setPageNum(1);
        }
        if (nxpCheckList.getPageSize() == null){
            nxpCheckList.setPageSize(10);
        }
        // 时间 1-近七天 2-近十四天 3-近21天 0-不限
        if (nxpCheckList.getSearchTime() == 1){
            String date = DateUtils.getDateByDay(DateUtils.getDate(), -7);
            String now = DateUtils.getDate();
            date = date + " 00:00:00";
            now = now + " 23:59:59";
            nxpCheckList.getParams().put("startTime",date);
            nxpCheckList.getParams().put("endTime",now);
        }
        if (nxpCheckList.getSearchTime() == 2){
            String date = DateUtils.getDateByDay(DateUtils.getDate(), -14);
            String now = DateUtils.getDate();
            date = date + " 00:00:00";
            now = now + " 23:59:59";
            nxpCheckList.getParams().put("startTime",date);
            nxpCheckList.getParams().put("endTime",now);
        }
        if (nxpCheckList.getSearchTime() == 3){
            String date = DateUtils.getDateByDay(DateUtils.getDate(), -21);
            String now = DateUtils.getDate();
            date = date + " 00:00:00";
            now = now + " 23:59:59";
            nxpCheckList.getParams().put("startTime",date);
            nxpCheckList.getParams().put("endTime",now);
        }
        PageHelper.startPage(nxpCheckList.getPageNum(), nxpCheckList.getPageSize());
        return getDataTable(selectNxpCheckListList(nxpCheckList));
    }

    /**
     * 检查详情
     * @param id
     * @return
     */
    @GetMapping("/details/{id}")
    public AjaxResult details(@PathVariable("id") Long id){
        if (id == null){
            return AjaxResult.error("id参数不能为空");
        }
        NxpCheckList checkList = nxpCheckListService.selectNxpCheckListById(id);
        if (ObjectUtil.isNotNull(checkList)){
            if (checkList.getType() == 1){
                // 报修
                NxpInspection nxpInspection = nxpInspectionService.selectNxpInspectionById(checkList.getInspectionId());
                NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(nxpInspection.getFacility_id());
                checkList.setFacilityName(nxpFacility.getNumber());
                // 工序
                NxpMachineProcess nxpMachineProcess = nxpMachineProcessService.selectNxpMachineProcessById(checkList.getProcessId());
                checkList.setProcessName(nxpMachineProcess.getName());
                // 查询check_list_list_item表
                List<NxpCheckListItem> nxpCheckListItems = nxpCheckListItemService.selectByCheckListId(id);

                // 检查项根据绑定关系查询
                /*NxpBindRelationship nxpBindRelationship = new NxpBindRelationship();
                nxpBindRelationship.setInspectionContentId(checkList.getInspectionContentId());
                nxpBindRelationship.setInspectionContentState("1");
                List<NxpBindRelationship> nxpBindRelationships = nxpBindRelationshipService.selectNxpBindRelationshipList(nxpBindRelationship);
                for (NxpBindRelationship bindRelationship : nxpBindRelationships) {
                    NxpCheckContent nxpCheckContent = nxpCheckContentService.selectNxpCheckContentById(bindRelationship.getCheckContentId());
                    bindRelationship.setCheckContentName(nxpCheckContent.getCheckContent());
                    NxpInspectionPosition nxpInspectionPosition = nxpInspectionPositionService.selectNxpInspectionPositionById(bindRelationship.getPositionId());
                    bindRelationship.setPositionName(nxpInspectionPosition.getPosition());
                    NxpInspectionContent nxpInspectionContent = nxpInspectionContentService.selectNxpInspectionContentById(bindRelationship.getInspectionContentId());
                    bindRelationship.setInspectionContentName(nxpInspectionContent.getContent());
                    NxpSwcJobContent nxpSwcJobContent = nxpSwcJobContentService.selectNxpSwcJobContentById(bindRelationship.getSwcJobContentId());
                    bindRelationship.setSwcJobContentName(nxpSwcJobContent.getJobContent());
                    NxpInspectionPmGroup nxpInspectionPmGroup = nxpInspectionPmGroupService.selectNxpInspectionPmGroupById(bindRelationship.getPmGroupId());
                    bindRelationship.setPmGroupName(nxpInspectionPmGroup.getName());
                    NxpInspectionPmGroupItem nxpInspectionPmGroupItem = nxpInspectionPmGroupItemService.selectNxpInspectionPmGroupItemById(bindRelationship.getPmGroupItemId());
                    bindRelationship.setPmGroupItemName(nxpInspectionPmGroupItem.getItem_name());
                }*/
                checkList.setChecks(nxpCheckListItems);
            }
            if (checkList.getType() == 2){
                // pm
                NxpInspectionPm nxpInspectionPm = nxpInspectionPmService.selectNxpInspectionPmById(checkList.getPmId());
                NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(nxpInspectionPm.getFacility_id());
                checkList.setFacilityName(nxpFacility.getNumber());
                List<NxpCheckListItem> nxpCheckListItems = nxpCheckListItemService.selectByCheckListId(id);
                // 检查项根据绑定关系查询
                /*NxpBindRelationship nxpBindRelationship = new NxpBindRelationship();
                nxpBindRelationship.setPmGroupItemId(checkList.getPmGroupItemId());
                nxpBindRelationship.setPmGroupItemState("1");
                List<NxpBindRelationship> nxpBindRelationships = nxpBindRelationshipService.selectNxpBindRelationshipList(nxpBindRelationship);
                for (NxpBindRelationship bindRelationship : nxpBindRelationships) {
                    NxpCheckContent nxpCheckContent = nxpCheckContentService.selectNxpCheckContentById(bindRelationship.getCheckContentId());
                    bindRelationship.setCheckContentName(nxpCheckContent.getCheckContent());
                    NxpInspectionPosition nxpInspectionPosition = nxpInspectionPositionService.selectNxpInspectionPositionById(bindRelationship.getPositionId());
                    bindRelationship.setPositionName(nxpInspectionPosition.getPosition());
                    NxpInspectionContent nxpInspectionContent = nxpInspectionContentService.selectNxpInspectionContentById(bindRelationship.getInspectionContentId());
                    bindRelationship.setInspectionContentName(nxpInspectionContent.getContent());
                    NxpSwcJobContent nxpSwcJobContent = nxpSwcJobContentService.selectNxpSwcJobContentById(bindRelationship.getSwcJobContentId());
                    bindRelationship.setSwcJobContentName(nxpSwcJobContent.getJobContent());
                    NxpInspectionPmGroup nxpInspectionPmGroup = nxpInspectionPmGroupService.selectNxpInspectionPmGroupById(bindRelationship.getPmGroupId());
                    bindRelationship.setPmGroupName(nxpInspectionPmGroup.getName());
                    NxpInspectionPmGroupItem nxpInspectionPmGroupItem = nxpInspectionPmGroupItemService.selectNxpInspectionPmGroupItemById(bindRelationship.getPmGroupItemId());
                    bindRelationship.setPmGroupItemName(nxpInspectionPmGroupItem.getItem_name());
                }*/
                checkList.setChecks(nxpCheckListItems);
            }
            if (checkList.getType() == 3){
                // 检查项根据绑定关系查询
                List<NxpCheckListItem> nxpCheckListItems = nxpCheckListItemService.selectByCheckListId(id);
                /*NxpBindRelationship nxpBindRelationship = new NxpBindRelationship();
                nxpBindRelationship.setSwcJobContentId(checkList.getSwcJobContentId());
                nxpBindRelationship.setSwcJobContentState("1");
                List<NxpBindRelationship> nxpBindRelationships = nxpBindRelationshipService.selectNxpBindRelationshipList(nxpBindRelationship);
                for (NxpBindRelationship bindRelationship : nxpBindRelationships) {
                    NxpCheckContent nxpCheckContent = nxpCheckContentService.selectNxpCheckContentById(bindRelationship.getCheckContentId());
                    bindRelationship.setCheckContentName(nxpCheckContent.getCheckContent());
                    NxpInspectionPosition nxpInspectionPosition = nxpInspectionPositionService.selectNxpInspectionPositionById(bindRelationship.getPositionId());
                    bindRelationship.setPositionName(nxpInspectionPosition.getPosition());
                    NxpInspectionContent nxpInspectionContent = nxpInspectionContentService.selectNxpInspectionContentById(bindRelationship.getInspectionContentId());
                    bindRelationship.setInspectionContentName(nxpInspectionContent.getContent());
                    NxpSwcJobContent nxpSwcJobContent = nxpSwcJobContentService.selectNxpSwcJobContentById(bindRelationship.getSwcJobContentId());
                    bindRelationship.setSwcJobContentName(nxpSwcJobContent.getJobContent());
                    NxpInspectionPmGroup nxpInspectionPmGroup = nxpInspectionPmGroupService.selectNxpInspectionPmGroupById(bindRelationship.getPmGroupId());
                    bindRelationship.setPmGroupName(nxpInspectionPmGroup.getName());
                    NxpInspectionPmGroupItem nxpInspectionPmGroupItem = nxpInspectionPmGroupItemService.selectNxpInspectionPmGroupItemById(bindRelationship.getPmGroupItemId());
                    bindRelationship.setPmGroupItemName(nxpInspectionPmGroupItem.getItem_name());
                }*/
                checkList.setChecks(nxpCheckListItems);
            }
            // 文件
            List<NxpCheckListItem> checks = checkList.getChecks();
            for (NxpCheckListItem check : checks) {
                String files = check.getFiles();
                List<String> filePaths = new ArrayList<>();
                if (StringUtils.isNotBlank(files)){
                    if (files.contains(",")){
                        String[] file = files.split(",");
                        for (String fileId : file) {
                            NxpCheckListFile nxpCheckListFile = nxpCheckListFileService.selectNxpCheckListFileById(Long.valueOf(fileId));
                            if (nxpCheckListFile != null){
                                filePaths.add(nxpCheckListFile.getFilePath());
                            }
                        }
                        check.setFilePaths(filePaths);
                    }else {
                        NxpCheckListFile nxpCheckListFile = nxpCheckListFileService.selectNxpCheckListFileById(Long.valueOf(files));
                        if (nxpCheckListFile != null){
                            filePaths.add(nxpCheckListFile.getFilePath());
                        }
                        check.setFilePaths(filePaths);
                    }
                }
            }
        }
        return AjaxResult.success(checkList);
    }

    /**
     * 提交
     * @param updateCheckListVO
     * @return
     */
    @PostMapping("/submit")
    @ResponseBody
    public AjaxResult saveCheckContent(@RequestBody UpdateCheckListVO updateCheckListVO){
        List<NxpCheckListItem> nxpCheckListItems = updateCheckListVO.getNxpCheckListItems();
        for (NxpCheckListItem nxpCheckListItem : nxpCheckListItems) {
//            nxpCheckListItem.setIsCheck("0");
            nxpCheckListItem.setUpdateTime(DateUtils.getNowDate());
            nxpCheckListItemService.updateNxpCheckListItem(nxpCheckListItem);
            NxpCheckList checkList = nxpCheckListService.selectNxpCheckListById(nxpCheckListItem.getCheckListId());
            checkList.setState(0L);
            nxpCheckListService.updateNxpCheckList(checkList);
            // 发送消息
            NxpAppMessage nxpAppMessage = new NxpAppMessage();
            nxpAppMessage.setType(4);
            nxpAppMessage.setStatus(1);
            nxpAppMessage.setCreate_user_id(checkList.getSubmitterId());
            nxpAppMessage.setComment("您标准化作业已经有人检查");
            nxpAppMessage.setUser_id(checkList.getSubmitterId());
            nxpAppMessage.setDel("NO");
            nxpAppMessage.setCreated_at(DateUtil.now());
            nxpAppMessage.setIs_receive(1);
            nxpAppMessage.setTypeName("标准化作业信息");
            nxpAppMessageService.insertNxpAppMessage(nxpAppMessage);
        }
        return AjaxResult.success();
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult upload(@RequestBody Picture picture) {
        String base64 = picture.getImg_picture();
        if (StringUtils.isBlank(base64)){
            return AjaxResult.error("base64编码为空");
        }
        // 转换base64为MultipartFile
        MultipartFile multipartFile = MultipartFileUtils.base64ToMultipart(base64);
        NxpCheckListFile nxpCheckListFile = new NxpCheckListFile();
        try {
            Map<String, String> uploadFile = uploadFile(multipartFile);
            if (uploadFile != null){
                if (StringUtils.isNoneBlank(uploadFile.get("url"))){
                    nxpCheckListFile.setFilePath(uploadFile.get("url"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("图片上传失败");
        }
        nxpCheckListFileService.insertNxpCheckListFile(nxpCheckListFile);
        return AjaxResult.success(nxpCheckListFile);
    }

    private List<NxpCheckList> selectNxpCheckListList(NxpCheckList nxpCheckList){
        List<NxpCheckList> nxpCheckLists = nxpCheckListService.selectCheckList(nxpCheckList);
        for (NxpCheckList checkList : nxpCheckLists) {
            if (checkList.getProcessId() != null){
                // 工序
                NxpMachineProcess nxpMachineProcess = nxpMachineProcessService.selectNxpMachineProcessById(checkList.getProcessId());
                if (nxpMachineProcess != null){
                    checkList.setProcessName(nxpMachineProcess.getName());
                }
            }
/*            if (checkList.getType() != 3){

            }
//            if (checkList.getType() == 1){
//                // 报修
//                NxpInspection nxpInspection = nxpInspectionService.selectNxpInspectionById(checkList.getInspectionId());
//                NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(nxpInspection.getFacility_id());
//                checkList.setFacilityName(nxpFacility.getNumber());
//            }
            if (checkList.getType() == 2){
                // pm
                NxpInspectionPm nxpInspectionPm = nxpInspectionPmService.selectNxpInspectionPmById(checkList.getPmId());
                NxpFacility nxpFacility = nxpFacilityService.selectNxpFacilityById(nxpInspectionPm.getFacility_id());
                checkList.setFacilityName(nxpFacility.getNumber());
            }*/
        }
        return nxpCheckLists;
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
