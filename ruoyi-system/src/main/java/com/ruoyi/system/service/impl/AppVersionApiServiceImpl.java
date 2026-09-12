package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.core.domain.BaseVO;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.NxpAppVersion;
import com.ruoyi.system.mapper.NxpAppVersionMapper;
import com.ruoyi.system.service.IAppVersionApiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yancheng Liu
 * @create 2021-02-08 10:21
 */
@Service("IAppVersionApiService")
@DataSource(value = DataSourceType.SLAVE)
public class AppVersionApiServiceImpl implements IAppVersionApiService {

    @Resource
    private NxpAppVersionMapper nxpAppVersionMapper;

    @Override
    public BaseVO version() {
        NxpAppVersion nxpAppVersion = new NxpAppVersion();
        nxpAppVersion.setStatus(1);
        List<NxpAppVersion> nxpAppVersions = nxpAppVersionMapper.selectNxpAppVersionList(nxpAppVersion);
        for (NxpAppVersion appVersion : nxpAppVersions) {
            appVersion.setContent(appVersion.getRemark());
        }

        NxpAppVersion appVersion = new NxpAppVersion();
        if (nxpAppVersions.size() > 0){
            appVersion = nxpAppVersions.get(nxpAppVersions.size() - 1);
        }
        return BaseVO.success(appVersion);
    }
}
