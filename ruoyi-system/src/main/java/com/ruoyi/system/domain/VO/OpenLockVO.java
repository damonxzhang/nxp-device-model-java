package com.ruoyi.system.domain.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author snkj
 * @create 2023-08-22 15:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenLockVO {

    private String ipAddress;

    private Integer port;

    private Integer boxNum;

}
