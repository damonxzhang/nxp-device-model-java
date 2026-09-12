package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 史工输出表对象 tobuy
 * 
 * @author d
 * @date 2021-05-21
 */
public class Tobuy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private String partID;

    /**  */
    @Excel(name = "")
    private String description;

    /**  */
    @Excel(name = "")
    private String model;

    /**  */
    @Excel(name = "")
    private String userID;

    /**  */
    @Excel(name = "")
    private Long quantity;

    /**  */
    @Excel(name = "")
    private String vendor;

    public void setPartID(String partID) 
    {
        this.partID = partID;
    }

    public String getPartID() 
    {
        return partID;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setModel(String model) 
    {
        this.model = model;
    }

    public String getModel() 
    {
        return model;
    }
    public void setUserID(String userID) 
    {
        this.userID = userID;
    }

    public String getUserID() 
    {
        return userID;
    }
    public void setQuantity(Long quantity) 
    {
        this.quantity = quantity;
    }

    public Long getQuantity() 
    {
        return quantity;
    }
    public void setVendor(String vendor) 
    {
        this.vendor = vendor;
    }

    public String getVendor() 
    {
        return vendor;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("partID", getPartID())
            .append("description", getDescription())
            .append("model", getModel())
            .append("userID", getUserID())
            .append("quantity", getQuantity())
            .append("vendor", getVendor())
            .toString();
    }
}
