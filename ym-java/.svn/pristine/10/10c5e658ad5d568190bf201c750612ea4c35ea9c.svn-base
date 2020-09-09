package com.lyht.business.message.controller;


import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.message.entity.MessageNotice;
import com.lyht.business.message.service.MessageNoticeService;
import com.lyht.business.message.vo.MessageNoticeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 消息提醒
 */
@RequestMapping("/message")
@Api(value = "/message", tags = "消息提醒Api")
@RestController
public class MessageNoticeController {

    private static Logger logger = Logger.getLogger(MessageNoticeController.class);

    @Autowired
    private MessageNoticeService messageNoticeService;



    @ApiOperation(value = "列表查询", notes = "列表查询")
    @PostMapping("/page")
    public LyhtResultBody<List<MessageNoticeVO>> page(LyhtPageVO lyhtPageVO, MessageNotice messageNotice){
        return messageNoticeService.page(lyhtPageVO, messageNotice);
    }

    @ApiOperation(value = "新增消息,需指定新增类型（addType）", notes = "新增消息,需指定新增类型（addType）")
    @PostMapping("/save")
    public LyhtResultBody<MessageNotice> add(MessageNoticeVO noticeVO) {

        if(noticeVO==null|| StringUtils.isBlank(noticeVO.getAddType())){
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        // 推送指定人员
        if(StringUtils.equalsIgnoreCase("U",noticeVO.getAddType())){
            return messageNoticeService.sendMessageNoticeByUser(noticeVO.getSenderNm(),noticeVO.getToStaffNm(),noticeVO.getSubject(),noticeVO.getContent(),noticeVO.getType());
        }

        //推送到指定角色下所有人
        if(StringUtils.equalsIgnoreCase("R",noticeVO.getAddType())){
         return messageNoticeService.sendMessageNoticeByRole(noticeVO.getSenderNm(),noticeVO.getRoleNm(),noticeVO.getSubject(),noticeVO.getContent(),noticeVO.getType());
        }

        //推送到指定部门下所有人
        if(StringUtils.equalsIgnoreCase("D",noticeVO.getAddType())){
            return messageNoticeService.sendMessageNoticeByDepartment(noticeVO.getSenderNm(),noticeVO.getDeptNm(),noticeVO.getSubject(),noticeVO.getContent(),noticeVO.getType());
        }

        return  new LyhtResultBody<>();
    }


    @ApiOperation(value = "修改已读状态", notes = "修改已读状态")
    @PostMapping("/updateReadFlag")
    public LyhtResultBody updateReadFlag(MessageNotice notice) {
        return messageNoticeService.updateReadFlag(notice.getId(),notice.getReadFlag());
    }


}
