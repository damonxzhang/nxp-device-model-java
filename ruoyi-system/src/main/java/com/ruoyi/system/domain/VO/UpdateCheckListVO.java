package com.ruoyi.system.domain.VO;

import com.ruoyi.system.domain.NxpCheckListItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author snkj
 * @create 2022-09-23 11:07
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCheckListVO {

    private List<NxpCheckListItem> nxpCheckListItems;
}
