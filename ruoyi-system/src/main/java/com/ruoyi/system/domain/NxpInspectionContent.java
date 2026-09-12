package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 维修内容对象 nxp_inspection_content
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpInspectionContent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 维修位置id */
    @Excel(name = "维修位置id")
    private Long position_id;

    private Long processOwner;

    private Long vendorId;

    private Long modelId;

    private String positionName;

    /** 维修内容 */
    @Excel(name = "维修内容")
    private String content;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private Long created_at;

    /** 修改时间 */
    @Excel(name = "修改时间")
    private Long updated_at;

    /** 是否必须上传图片 */
    @Excel(name = "是否必须上传图片")
    private Integer pic_type;

    /** 检查人 */
    @Excel(name = "检查人")
    private String check_person;

    /** 工序id */
    private Long processId;

    /** 工序名称 */
    private String processName;

    private String modelName;

    private String vendorName;

    private Integer isMe;
    private Integer isMenu;
    private List<Integer> positionIds;
    /**
     * 是否检查
     */
    private String is_check;
    /**
     * 检查组id
     */
    private Long check_group_id;

    private String checkGroupName;

    private String checkProcessName;

    private Long check_process_id;
    /**
     * 是否更换模具 0-是 1-否
     */
    private Integer is_replace_mold;
}
