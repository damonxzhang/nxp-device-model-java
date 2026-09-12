package com.ruoyi.system.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 周PM分组对象 nxp_inspection_pm_group
 * 
 * @author ruoyi
 * @date 2021-02-21
 */
public class NxpInspectionPmGroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 分组名称 */
    @Excel(name = "分组名称")
    private String name;

    public String getCreateUserName() {
        return CreateUserName;
    }

    public void setCreateUserName(String createUserName) {
        CreateUserName = createUserName;
    }

    private String CreateUserName;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long create_user_id;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 周PM检查项信息 */
    private List<NxpInspectionPmGroupItem> nxpInspectionPmGroupItemList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setCreate_user_id(Long create_user_id) 
    {
        this.create_user_id = create_user_id;
    }

    public Long getCreate_user_id() 
    {
        return create_user_id;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public List<NxpInspectionPmGroupItem> getNxpInspectionPmGroupItemList()
    {
        return nxpInspectionPmGroupItemList;
    }

    public void setNxpInspectionPmGroupItemList(List<NxpInspectionPmGroupItem> nxpInspectionPmGroupItemList)
    {
        this.nxpInspectionPmGroupItemList = nxpInspectionPmGroupItemList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("create_user_id", getCreate_user_id())
            .append("status", getStatus())
            .append("nxpInspectionPmGroupItemList", getNxpInspectionPmGroupItemList())
            .toString();
    }
}
