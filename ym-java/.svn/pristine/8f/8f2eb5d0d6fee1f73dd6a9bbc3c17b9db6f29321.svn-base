package com.lyht.business.message.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 界面展示
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageNoticeVO {

    private Integer id;

    private String nm;

    @ApiModelProperty(value = "标题/主题")
    private String subject;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "接收人名字")
    private String toStaffName;

    @ApiModelProperty(value = "发送人名字")
    private String senderName;

    @ApiModelProperty(value = "发送时间")
    private String senderTime;

    @ApiModelProperty(value = "1:已读  0:未读")
    private Integer readFlag;

    @ApiModelProperty(value = "消息类别。 SHOW：展示 , JUMP:跳转")
    private String type;

    @ApiModelProperty(value = "新增消息类别（新增必填）。U:指定人  R:角色下人员  D:部门下人员 ")
    private String addType;

    @ApiModelProperty(value = "发送人--nm内码,消息发送者")
    private String senderNm;

    @ApiModelProperty(value = "接收人--nm内码,用于发送到指定人员消息")
    private String toStaffNm;

    @ApiModelProperty(value = "接收角色--nm内码,用于发送到指定角色下人员消息")
    private String roleNm;

    @ApiModelProperty(value = "接收部门内码,用于发送指定部门下人员消息")
    private String deptNm;


}
