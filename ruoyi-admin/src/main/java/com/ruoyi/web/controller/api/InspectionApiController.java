package com.ruoyi.web.controller.api;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.DTO.OperateDTO;
import com.ruoyi.system.service.IInspectionApiService;
import com.ruoyi.system.service.INxpInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报修Controller
 * @author Yancheng Liu
 * @create 2021-02-05 14:47
 */
@RestController
@RequestMapping("/api/inspection-api")
public class InspectionApiController extends BaseController {

    @Resource
    private IInspectionApiService inspectionApiService;

    /**
     * 查询报修信息
     * @param nxpInspectionService
     * @return
     */
    @PostMapping("/inspection-info")
    public BaseVO inspectionInfo(@RequestBody NxpInspectionService nxpInspectionService){
        return inspectionApiService.inspectionInfo(nxpInspectionService);
    }

    /**
     * 新增报修信息
     * @param nxpInspection
     * @return
     */
    @PostMapping("/inspection-create")
    public BaseVO inspectionCreate(@RequestBody NxpInspection nxpInspection){
        return inspectionApiService.inspectionCreate(nxpInspection);
    }

    /**
     * 查询维修详情信息
     * @param nxpInspectionService
     * @return
     */
    @PostMapping("/inspection-service-info")
    public BaseVO inspectionServiceInfo(@RequestBody NxpInspectionService nxpInspectionService){
        return inspectionApiService.inspectionServiceInfo(nxpInspectionService);
    }

    /**
     * 查询指定设备历史报修列表
     * @param nxpInspection
     * @return
     */
    @PostMapping("/inspection-list")
    public BaseVO inspectionList(@RequestBody NxpInspection nxpInspection){
        startPage();
        return inspectionApiService.inspectionList(nxpInspection);
    }

    /**
     * 提交维修信息
     * @param nxpInspectionService
     * @return
     */
    @PostMapping("/inspection-service-create")
    public BaseVO inspectionServiceCreate(@RequestBody NxpInspectionService nxpInspectionService){
        return inspectionApiService.inspectionServiceCreate(nxpInspectionService);
    }

    /**
     * 提交维修信息（new）
     * @param nxpInspectionService
     * @return
     */
    @PostMapping("/inspection-service-create-new")
    public BaseVO inspectionServiceCreateNew(@RequestBody NxpInspectionService nxpInspectionService){
        return inspectionApiService.inspectionServiceCreateNew(nxpInspectionService);
    }

    /**
     * 设备维修列表
     * @param nxpInspectionService
     * @return
     */
    @PostMapping("/inspection-service-list")
    public BaseVO inspectionServiceList(@RequestBody NxpInspectionService nxpInspectionService){
        if (nxpInspectionService.getPageNum() == null){
            nxpInspectionService.setPageNum(1);
        }
        if (nxpInspectionService.getPageSize() == null){
            nxpInspectionService.setPageSize(10);
        }
        PageHelper.startPage(nxpInspectionService.getPageNum(), nxpInspectionService.getPageSize());
        return BaseVO.success(getDataTable(inspectionApiService.inspectionServiceList(nxpInspectionService)));
    }

    /**
     * 更新维修状态
     * @param nxpInspection
     * @return
     */
    @PostMapping("inspection-service-update")
    public BaseVO inspectionServiceUpdate(@RequestBody NxpInspection nxpInspection){
        return inspectionApiService.inspectionServiceUpdate(nxpInspection);
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/camera")
    public BaseVO uploadFile(@RequestBody Picture picture) {
        return inspectionApiService.uploadFile(picture);
    }

    /**
     * 位置列表
     * @return
     */
    @PostMapping("/get-position")
    public BaseVO getPosition(@RequestBody NxpFacility nxpFacility){
        return inspectionApiService.getPosition(nxpFacility);
    }

    /**
     * 内容列表
     * @return
     */
    @PostMapping("/get-content")
    public BaseVO getContent(@RequestBody NxpInspectionContent nxpInspectionContent){
        return inspectionApiService.getContent(nxpInspectionContent);
    }

    /**
     * 设备列表
     * @param nxpFacility
     * @return
     */
    @PostMapping("/facility-list")
    public BaseVO facilityList(@RequestBody NxpFacility nxpFacility){
        if (nxpFacility.getPageNum() == null){
            nxpFacility.setPageNum(1);
        }
        if (nxpFacility.getPageSize() == null){
            nxpFacility.setPageSize(10);
        }
        PageHelper.startPage(nxpFacility.getPageNum(), nxpFacility.getPageSize());
        BaseVO baseVO = inspectionApiService.selectFacilityList(nxpFacility);
        return baseVO;
    }

    /**
     * 设备详情
     * @param nxpFacility
     * @return
     */
    @PostMapping("/facility")
    public BaseVO facility(@RequestBody NxpFacility nxpFacility){
        return inspectionApiService.facility(nxpFacility);
    }

    /**
     * 设备问题类型
     * @return
     */
    @RequestMapping("/inspection-question-type")
    public BaseVO inspectionQuestionType(@RequestBody NxpMaintainType nxpMaintainType){
        return inspectionApiService.inspectionQuestionType(nxpMaintainType);
    }

    /**
     * 待处理设备列表
     * @param nxpInspection
     * @return
     */
    @PostMapping("/pending-inspection")
    public BaseVO pendingInspection(@RequestBody NxpInspection nxpInspection){
        if (nxpInspection.getPageNum() == null){
            nxpInspection.setPageNum(1);
        }
        if (nxpInspection.getPageSize() == null){
            nxpInspection.setPageSize(10);
        }
        PageHelper.startPage(nxpInspection.getPageNum(), nxpInspection.getPageSize());
        return inspectionApiService.pendingInspection(nxpInspection);
    }

    /**
     * 获取工序列表
     * @return
     */
    @RequestMapping("/get-process")
    public BaseVO getProcess(){
        return inspectionApiService.getProcess();
    }

    /**
     * 获取后续操作下拉
     * @return
     */
    @RequestMapping("/getOperate")
    public BaseVO getOperate(){
        OperateDTO operateDTO = new OperateDTO();
        operateDTO.setId("1");
        operateDTO.setName("提交产线异常");
        OperateDTO operateDTO2 = new OperateDTO();
        operateDTO2.setId("2");
        operateDTO2.setName("无");
        List<OperateDTO> list = new ArrayList<>();
        list.add(operateDTO);
        list.add(operateDTO2);
        return BaseVO.success(list);
    }

    /**
     * 提交IR（报修确认）
     * @param nxpInspection
     * @return
     */
    @PostMapping("/submit-ir")
    public BaseVO submitIr(@RequestBody NxpInspection nxpInspection){
        return inspectionApiService.submitIr(nxpInspection);
    }

    /**
     * 更新保修状态为维修中
     * @return
     */
    @PostMapping("/updateInspectionServiceStatus")
    public BaseVO updateInspectionServiceStatus(@RequestBody NxpInspection nxpInspection){
        return inspectionApiService.updateInspectionServiceStatus(nxpInspection);
    }

    /**
     * 设备维修记录
     * @param nxpInspectionService
     * @return
     */
    @PostMapping("/inspection-service-record")
    public BaseVO inspectionServiceRecord(@RequestBody NxpInspectionService nxpInspectionService){
        return inspectionApiService.inspectionServiceRecord(nxpInspectionService);
    }

    /**
     * 中止维修原因列表
     * @return
     */
    @RequestMapping("/inspection-about-reason")
    public BaseVO inspectionAboutReason(){
        return inspectionApiService.inspectionAboutReason();
    }

    /**
     * 无需维修原因
     * @return
     */
    @RequestMapping("/inspection-maintenance-reason")
    public BaseVO inspectionMaintenanceReason(){
        return inspectionApiService.inspectionMaintenanceReason();
    }

    /**
     * 设备报修列表
     * @param nxpInspection
     * @return
     */
    @PostMapping("/inspectionList")
    public BaseVO inspectionListByFacilityId(@RequestBody NxpInspection nxpInspection){
        if (nxpInspection.getPageNum() == null){
            nxpInspection.setPageNum(1);
        }
        if (nxpInspection.getPageSize() == null){
            nxpInspection.setPageSize(10);
        }
        PageHelper.startPage(nxpInspection.getPageNum(), nxpInspection.getPageSize());
        return inspectionApiService.inspectionListByFacilityId(nxpInspection);
    }

    /**
     * 检查报修设备状态
     * @param nxpFacility
     * @return
     */
    @PostMapping("/checkFacilityInspectionStatus")
    public BaseVO checkFacilityInspectionStatus(@RequestBody NxpFacility nxpFacility){
        return inspectionApiService.checkFacilityInspectionStatus(nxpFacility);
    }

    /**
     * 维修详情
     * @param nxpInspectionService
     * @return
     */
    @PostMapping("/inspectionServiceDetails")
    public BaseVO inspectionServiceDetails(@RequestBody NxpInspectionService nxpInspectionService){
        return inspectionApiService.inspectionServiceDetails(nxpInspectionService);
    }
}
