package com.lyht.business.change.controller;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.change.entity.ChangeApplication;
import com.lyht.business.change.entity.DesignContent;
import com.lyht.business.change.service.ChangeApplicationService;
import com.lyht.business.change.service.DesignContentService;
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
@Api(value = "/change/designContent", tags = "变更申请内容")
@RestController
@RequestMapping("/change/designContent")
public class DesignContentController {
    @Autowired
    private DesignContentService service;


    /**
     * 单个删除
     *
     * @param type
     * @return
     */
    @GetMapping("/findByChangeRequestType")
    @ApiOperation(value = "根据变更申请表，查询变更申请内容项")
    public LyhtResultBody<List<DesignContent>> findByChangeRequestType(Integer type) {
        return new LyhtResultBody<>(service.findByChangeRequestType(type));
    }
    /**
     * 单个删除
     *
     * @param id
     * @return
     */
    @GetMapping("/delete")
    @ApiOperation(value = "删除", notes = "删除")
    public LyhtResultBody<Integer> delete(Integer id) {
        // 参数校验
        if (id == null) {
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        service.delete(id);
        return new LyhtResultBody<>();
    }

    /**
     * 添加 修改
     * @param designContent
     * @return
     */
    @ApiOperation(value = "新增修改", notes = "新增修改 修改必须传入id,nm")
    @PostMapping("/save")
    public LyhtResultBody<DesignContent> save(DesignContent designContent) {
         service.save(designContent);
        return new LyhtResultBody<>();
    }

}
