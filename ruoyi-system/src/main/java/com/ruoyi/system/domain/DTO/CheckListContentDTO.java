package com.ruoyi.system.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author snkj
 * @create 2022-09-04 20:31
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CheckListContentDTO {

    private Long checkProjectId;

    private String checkContent;

    private String fileId;

}
