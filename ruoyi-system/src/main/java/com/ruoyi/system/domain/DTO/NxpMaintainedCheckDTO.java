package com.ruoyi.system.domain.DTO;

import com.ruoyi.system.domain.NxpMoldMaintenanceGroupItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author snkj
 * @create 2023-07-18 15:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxpMaintainedCheckDTO {

    private List<NxpMoldMaintenanceGroupItem> items;

    private Long moldMaintenanceId;

    private Long userInfoId;

}
