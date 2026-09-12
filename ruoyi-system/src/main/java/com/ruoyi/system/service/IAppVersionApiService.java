package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.system.domain.NxpAppVersion;

/**
 * @author Yancheng Liu
 * @create 2021-02-08 10:20
 */
public interface IAppVersionApiService {

    /**
     * 获取软件版本信息
     * @param
     * @return
     */
    BaseVO version();
}
