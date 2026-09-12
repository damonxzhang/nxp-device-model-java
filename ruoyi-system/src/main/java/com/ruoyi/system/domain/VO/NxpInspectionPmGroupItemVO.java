package com.ruoyi.system.domain.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author snkj
 * @create 2021-09-02 16:58
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NxpInspectionPmGroupItemVO {

    /** 主键 */
    private Long id;

    /** 分组 */
    private Long group_id;

    /** 检查项 */
    private String item_name;

    /** 状态 */
    private Integer item_status;

    /** 文件id */
    private String file_ids;

    /** 文件地址 */
    private List<String> group_item_pic;

}
