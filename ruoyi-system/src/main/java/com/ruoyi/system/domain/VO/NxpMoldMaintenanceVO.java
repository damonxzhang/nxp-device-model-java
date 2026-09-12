package com.ruoyi.system.domain.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 模具维护列表对象 nxp_mold_maintenance
 * 
 * @author YanCheng Liu
 * @date 2023-07-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpMoldMaintenanceVO
{
    /** 用户id */
    private Integer userInfoId;

    private Integer pageNum = 1;
    private Integer pageSize = 10;

    private Integer moldType;

}
