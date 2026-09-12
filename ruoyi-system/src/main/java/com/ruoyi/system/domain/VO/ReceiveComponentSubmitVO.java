package com.ruoyi.system.domain.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author snkj
 * @create 2023-07-07 10:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveComponentSubmitVO {
    /**
     * 推荐模具编号
     */
    private String recommendedMoldNumber;
    /**
     * 实际模具编号
     */
    private String actualMoldNumber;
    /**
     * 部件id集合
     */
    private List<Long> moldComponentIds;
    /**
     * 列表id
     */
    private Long replacedListId;
    /**
     * 模具是否异常 0-是 1否
     */
    private Integer moldIsAbnormal;
    /**
     * 设备编号
     */
    private String facilityNumber;

    private Integer moldType;
    /**
     * 异常原因
     */
    private String remark;

    /**
     * 模具更换类型 1-整套 2-部件
     */
    private Integer moldReplaceType;

    private Long moldId;
}
