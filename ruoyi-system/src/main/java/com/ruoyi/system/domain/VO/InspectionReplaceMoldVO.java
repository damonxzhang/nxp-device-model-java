package com.ruoyi.system.domain.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 维修更换模具参数实体类
 * @author snkj
 * @create 2023-07-07 09:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InspectionReplaceMoldVO {
    /**
     * 报修id
     */
    private Long inspectionId;
    /**
     * 维修id
     */
    private Long inspectionServiceId;
    /**
     * 维修位置id
     */
    private Long inspectionPositionId;
    /**
     * 维修内容id
     */
    private Long inspectionContentId;
    /**
     * 设备编号
     */
    private String facilityNumber;
}
