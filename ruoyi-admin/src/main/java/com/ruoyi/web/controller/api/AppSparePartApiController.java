package com.ruoyi.web.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.system.domain.Equipment;
import com.ruoyi.system.domain.NxpSparePartSparePart;
import com.ruoyi.system.domain.OutPut;
import com.ruoyi.system.service.IAppSparePartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Yancheng Liu
 * @create 2021-02-08 13:54
 */
@RestController
@RequestMapping("/api/spare-part")
public class AppSparePartApiController extends BaseController {

    @Resource
    private IAppSparePartService appSparePartService;

    /**
     * 备件工序列表
     * @return
     */
    @PostMapping("/process-list")
    public BaseVO processList(){
        return appSparePartService.processList();
    }

    /**
     * 提交备件领用
     * @param outPut
     * @return
     */
    @PostMapping("/receive-spare-part")
    public BaseVO receiveSparePart(@RequestBody OutPut outPut){
        return appSparePartService.receiveSparePart(outPut);
    }

    /**
     * 获取备件详细信息
     * @param nxpSparePartSparePart
     * @return
     */
    @PostMapping("/detail")
    public BaseVO detail(@RequestBody NxpSparePartSparePart nxpSparePartSparePart){
        return appSparePartService.detail(nxpSparePartSparePart);
    }

    /**
     * 备件列表
     * @param nxpSparePartSparePart
     * @return
     */
    @PostMapping("/spare-part-list")
    public BaseVO sparePartList(@RequestBody NxpSparePartSparePart nxpSparePartSparePart){
//        if (nxpSparePartSparePart.getPage() == null){
//            nxpSparePartSparePart.setPage(1);
//        }
//        if (nxpSparePartSparePart.getDefault_page() == null){
//            nxpSparePartSparePart.setDefault_page(10);
//        }
//        PageHelper.startPage(nxpSparePartSparePart.getPage(), nxpSparePartSparePart.getDefault_page());
        nxpSparePartSparePart.setPage((nxpSparePartSparePart.getPage() - 1) * nxpSparePartSparePart.getDefault_page());
        return appSparePartService.sparePartList(nxpSparePartSparePart);
    }

    /**
     * 通过工序获取设备型号
     * @param equipment
     * @return
     */
    @PostMapping("/getDeviceType")
    public BaseVO getDeviceType(@RequestBody Equipment equipment){
        return appSparePartService.getDeviceType(equipment);
    }

    /**
     * 通过工序获取设备型号
     * @param equipment
     * @return
     */
    @PostMapping("/getDeviceModel")
    public BaseVO getDeviceModel(@RequestBody Equipment equipment){
        return appSparePartService.getDeviceModel(equipment);
    }

}
