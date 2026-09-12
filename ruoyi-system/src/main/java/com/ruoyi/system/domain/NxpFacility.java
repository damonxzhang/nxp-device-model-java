package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 设备管理对象 nxp_facility
 * 
 * @author ruoyi
 * @date 2021-02-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpFacility extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String name;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String number;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long create_user;

    /** 型号 */
    @Excel(name = "型号")
    private String model;

    private String model_name;

    /** 区域 */
    @Excel(name = "区域")
    private Long area;

    /** 班别 */
    @Excel(name = "班别")
    private Long shift;

    /** 序列号 */
    @Excel(name = "序列号")
    private String serial_no;

    /** 设备厂家 */
    @Excel(name = "设备厂家")
    private Long vendor_id;

    /** 资产号 */
    @Excel(name = "资产号")
    private String asset_no;

    /** 购买日期 */
    @Excel(name = "购买日期")
    private Long buy_off_date;

    /** cba通过日期 */
    @Excel(name = "cba通过日期")
    private Long cba_pass_date;

    /** 负责人 */
    @Excel(name = "负责人")
    private Long head_user;

    private Long review_user;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 是否可用 */
    @Excel(name = "是否可用")
    private Long is_ok;

    /** 附件 */
    @Excel(name = "附件")
    private String file;

    /** 创建时间 */
    private Long create_time;

    /** 更新时间 */
    private Long update_time;

    /** 删除时间 */
    @Excel(name = "删除时间")
    private Long delete_time;

    /** 主键 */
    private Long id;

    /** 工序 */
    @Excel(name = "工序")
    private String process;

    /** 设备相关人 */
    @Excel(name = "设备相关人")
    private String relevant_people;

    /** 设备维修状态 维修状态 0 待处理 1:正常 2维修中 3:待确认 */
    @Excel(name = "设备维修状态")
    private Integer inspection_status;

    private String head_user_name;

    private String relevant_people_name;

    /** 关系： 1:与我相关 */
    private Long relation;

    /** 时间选择 1:12小时 2：1天 3:4天 4：7天 */
    private Long time_choose;
    /**
     * 设备id
     */
    private Long facility_id;
    private String facility_number;
    private String create_user_name;
    private String i_id;
    private String inspection_service_id;
    private String i_status;
    private String i_repair_time;


    private Long start_time;

    private String i_question_type;

    private String groupName;
    private String groupId;
    /**
     * 分页相关
     */
    private Integer pageNum;
    private Integer pageSize;
    /**
     * pm检查状态
     */
    private Integer pm_status;
    /**
     * 检查时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date check_time;

    private String process_id;

    private Long user_id;

    private boolean flag;

    private Integer operate;

    /**
     * 工序名称
     */
    private String processName;
    /**
     * 更换角色 1-pm更换 2-PM和作业员都可以换
     */
    private Integer is_replace;

}
