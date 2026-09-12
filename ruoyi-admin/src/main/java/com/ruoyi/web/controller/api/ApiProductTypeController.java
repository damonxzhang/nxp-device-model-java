package com.ruoyi.web.controller.api;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.NxpProductType;
import com.ruoyi.system.service.INxpProductTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author snkj
 * @create 2023-06-23 10:02
 */
@RestController
@RequestMapping("/api/productType")
public class ApiProductTypeController {

    @Resource
    private INxpProductTypeService nxpProductTypeService;

    /**
     * 产品类型列表
     * @return
     */
    @GetMapping("/list")
    public AjaxResult list(){
        NxpProductType params = new NxpProductType();
        params.setStatus(1);
        return AjaxResult.success(nxpProductTypeService.selectNxpProductTypeList(params));
    }

}
