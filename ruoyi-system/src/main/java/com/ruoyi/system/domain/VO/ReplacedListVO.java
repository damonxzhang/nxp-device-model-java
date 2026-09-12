package com.ruoyi.system.domain.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 待更换模具对象 t_replaced_list
 * 
 * @author YanCheng Liu
 * @date 2023-06-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplacedListVO
{

    private Integer pageNum = 1;
    private Integer pageSize = 10;

    private Integer isReplace;

    private Integer moldType;

    private Integer status;

    private Long userId;

    private Integer userInfoId;

    private String process;

    private List<String> processList;
}
