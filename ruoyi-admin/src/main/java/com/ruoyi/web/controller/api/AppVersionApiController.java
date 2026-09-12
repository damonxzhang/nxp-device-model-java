package com.ruoyi.web.controller.api;

import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.system.domain.NxpAppVersion;
import com.ruoyi.system.service.IAppVersionApiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Yancheng Liu
 * @create 2021-02-08 10:19
 */
@RestController
@RequestMapping("/api/app-version")
public class AppVersionApiController {

    @Resource
    private IAppVersionApiService appVersionApiService;

    /**
     * 获取软件版本信息
     * @param
     * @return
     */
    @PostMapping("/version")
    public BaseVO version(){
        return appVersionApiService.version();
    }

}
