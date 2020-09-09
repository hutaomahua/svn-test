package com.lyht.business.abm.protocol.controller;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.protocol.entity.MoveProtocol;
import com.lyht.business.abm.protocol.service.MoveProtocolService;
import com.lyht.business.abm.protocol.vo.MoveProtocolVO;
import com.lyht.business.change.entity.ChangeApplication;
import com.lyht.business.change.service.ChangeApplicationService;
import com.lyht.business.land.bean.LandApplyDetail;
import com.lyht.business.land.entity.LandApply;
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
 * @date 2019/12/9 17:14
 */
@RequestMapping("/moveProtocol")
@RestController
@Api(value = "/moveProtocol", tags = "搬迁安置协议表")
public class MoveProtocolController {
    @Autowired
    private MoveProtocolService service;

    @Autowired
    private PubFilesService pubFilesService;

    /**
     * 分页
     * @param lyhtPageVO
     * @param moveProtocolVO
     * @return
     */
    @ApiOperation(value = "分页查询，排序查询，条件查询", notes = "分页查询")
    @PostMapping("/page")
    public LyhtResultBody<List<MoveProtocolVO>> page(LyhtPageVO lyhtPageVO, MoveProtocolVO moveProtocolVO) {
        return service.page(lyhtPageVO, moveProtocolVO);
    }
    /**
     * 添加 修改
     * @param moveProtocol
     * @return
     */
    @ApiOperation(value = "新增修改", notes = "新增修改 修改必须传入id,nm")
    @PostMapping("/save")
    public LyhtResultBody<MoveProtocol> save(MoveProtocol moveProtocol) {
        return new LyhtResultBody<>(service.save(moveProtocol));
    }
}
