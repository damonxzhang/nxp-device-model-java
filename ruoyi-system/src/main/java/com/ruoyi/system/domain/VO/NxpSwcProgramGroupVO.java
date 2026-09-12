package com.ruoyi.system.domain.VO;

import com.ruoyi.system.domain.NxpSwcProgramGroup;
import lombok.Data;

/**
 * @author Y.C Liu
 * @create 2021-03-25 10:31
 */
@Data
public class NxpSwcProgramGroupVO extends NxpSwcProgramGroup {

    private Long nsptId;

    private Long nsptGroupId;

    private Integer nsptStatus;

    private String nsptTypeName;

    private Long nspId;

    private Long nspTypeId;

    private String nspProgram;

    private Integer nspStatus;

}
