package com.ruoyi.system.domain.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 维修更换模具确认类型下一步参数实体类
 * @author snkj
 * @create 2023-07-07 09:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmTypeNextStepVO {
    /**
     * 模具编号
     */
    private String moldCode;
    /**
     * 模具更换类型 1-整套 2-部件
     */
    private Integer moldReplaceType;
    /**
     * 部件id集合
     */
    private List<Long> moldComponentIds;
    /**
     * 设备编号
     */
    private String facilityNumber;
    /**
     * 提交人id
     */
    private Long userInfoId;
    /**
     * 产品类型id
     */
    private Long productTypeId;
}
