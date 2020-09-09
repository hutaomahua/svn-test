package com.lyht.business.change.controller;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.change.entity.ChangeApplication;
import com.lyht.business.change.service.ChangeApplicationService;
import com.lyht.business.change.vo.ChangeApplicationVO;
import com.lyht.business.pub.service.PubFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author HuangAn
 * @date 2019/12/5 15:02
 */
@Api(value = "/change/changeApplication", tags = "设计变更申请")
@RestController
@RequestMapping("/change/changeApplication")
public class ChangeApplicationController {
    @Autowired
    private ChangeApplicationService service;

    @Autowired
    private PubFilesService pubFilesService;

    @ApiOperation(value = "查询 模糊查询 分页查询", notes = "查询 条件查询")
    @PostMapping("/page")
    public LyhtResultBody<List<ChangeApplicationVO>> page(ChangeApplication changeApplication, LyhtPageVO lyhtPageVO){
        return service.page(changeApplication,lyhtPageVO);
    }

    /**
     * 单个删除
     *
     * @param id
     * @return
     */
    @GetMapping("/delete")
    @ApiOperation(value = "删除", notes = "删除")
    public LyhtResultBody<Integer> delete(HttpServletRequest request, Integer id) {
        // 参数校验
        if (id == null) {
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        ChangeApplication changeApplication = service.findOneById(id);
        pubFilesService.deleteBytablePkColumn(request, changeApplication.getNm());
        return service.delete(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @GetMapping("/batchDel")
    @ApiOperation(value = "批量删除", notes = "批量删除")
    public LyhtResultBody<String> batchDel(String ids) {
        return service.batchDel(ids);
    }

    /**
     * 添加 修改
     * @param changeApplication
     * @return
     */
    @ApiOperation(value = "新增修改", notes = "新增修改 修改必须传入id,nm")
    @PostMapping("/save")
    public LyhtResultBody<ChangeApplication> save(ChangeApplication changeApplication) {
        return service.save(changeApplication);
    }
    
    /**
     * 发起实物指标变更
     * @param id
     * @param request
     * @return
     */
    @ApiOperation(value = "发起实物指标变更", notes = "发起实物指标变更")
    @GetMapping("/start")
    public LyhtResultBody<ChangeApplication> startChange(Integer id, HttpServletRequest request) {
    	ChangeApplication startChange = service.startChange(id, request);
        return new LyhtResultBody<>(startChange);
    }
    
    @ApiOperation(value = "通过流程ID查询表单", notes = "通过流程ID查询表单")
    @PostMapping("/get")
    public LyhtResultBody<ChangeApplicationVO> page(String taskId){
    	ChangeApplicationVO changeApplicationVO = service.findByTaskId(taskId);
        return new LyhtResultBody<>(changeApplicationVO);
    }

}
