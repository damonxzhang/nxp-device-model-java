package com.ruoyi.system.domain.VO;

import com.ruoyi.system.domain.NxpFacility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author Yancheng Liu
 * @create 2021-03-19 16:07
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NxpFacilityVO extends NxpFacility {
    /**
     * 设备id集合
     */
    private List<String> ids;

}
