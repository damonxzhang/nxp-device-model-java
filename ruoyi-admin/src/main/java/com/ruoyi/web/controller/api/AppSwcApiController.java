package com.ruoyi.web.controller.api;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yancheng Liu
 * @create 2022-09-01 12:02
 */
@RestController
@RequestMapping("/api/swc")
public class AppSwcApiController {

    @Resource
    private INxpSwcJobContentService nxpSwcJobContentService;
    @Resource
    private INxpCheckListService nxpCheckListService;
    @Resource
    private INxpBindRelationshipService nxpBindRelationshipService;
    @Resource
    private INxpCheckListItemService nxpCheckListItemService;
    @Resource
    private INxpCheckContentService nxpCheckContentService;

    /**
     * 获取swc作业内容
     * @return
     */
    @GetMapping("/getSwcJobContent")
    public AjaxResult getSwcJobContent(){
        NxpSwcJobContent nxpSwcJobContent = new NxpSwcJobContent();
        nxpSwcJobContent.setState(1);
        return AjaxResult.success(nxpSwcJobContentService.selectNxpSwcJobContentList(nxpSwcJobContent));
    }

    /**
     * 保存swc到检查列表
     * @param nxpCheckList
     * @return
     */
    @PostMapping("/saveSwc")
    public AjaxResult saveSwc(@RequestBody NxpCheckList nxpCheckList){
        // 类型
        nxpCheckList.setType(3L);
        nxpCheckListService.insertNxpCheckList(nxpCheckList);
        // 根据swcJobContentId查询检查项
        Long swcJobContentId = nxpCheckList.getSwcJobContentId();
        NxpSwcJobContent nxpSwcJobContent = nxpSwcJobContentService.selectNxpSwcJobContentById(swcJobContentId);
        if (nxpSwcJobContent != null){
            // 1. 先拿出检查组
            Long checkGroupId = nxpSwcJobContent.getCheckGroupId();
            // 2. 查询检查组下的所有检查项
            List<NxpCheckContent> nxpCheckContents = nxpCheckContentService.selectNxpCheckContentByCheckGroupId(checkGroupId);
            for (NxpCheckContent nxpCheckContent : nxpCheckContents) {
                NxpCheckListItem nxpCheckListItem = new NxpCheckListItem();
                nxpCheckListItem.setCheckListId(nxpCheckList.getId());
                nxpCheckListItem.setCheckProjectId(nxpCheckContent.getId());
                nxpCheckListItem.setCheckProjectName(nxpCheckContent.getCheckContent());
                nxpCheckListItemService.insertNxpCheckListItem(nxpCheckListItem);
            }
        }
        return AjaxResult.success("操作成功");
    }

}
