package com.lyht.business.message.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_message_notice")
@ApiModel(description = "消息提醒")
public class MessageNotice implements Serializable {
            private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nm")
    private String nm;

    @ApiModelProperty(value = "标题/主题")
    @Column(name = "subject")
    private String subject;

    @ApiModelProperty(value = "内容")
    @Column(name = "content")
    private String content;

    @ApiModelProperty(value = "接收人-nm内码")
    @Column(name = "to_staff_nm")
    private String toStaffNm;

    @ApiModelProperty(value = "发送人-nm内码")
    @Column(name = "sender_nm")
    private String senderNm;


    @ApiModelProperty(value = "发送时间")
    @Column(name = "sender_time")
    @CreatedDate
    private Date senderTime;

    @ApiModelProperty(value = "1:已读  0:未读，不传默认0")
    @Column(name = "readFlag")
    private Integer readFlag;


    @ApiModelProperty(value = "消息类别。 SHOW：展示 , JUMP:跳转 ,默认SHOW")
    @Column(name = "type")
    private String type;

    @ApiModelProperty(value = "创建人")
    @Column(name = "create_staff")
    private String createStaff;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    @Column(name = "update_staff")
    private String updateStaff;

    @ApiModelProperty(value = "修改时间")
    @Column(name = "update_time")
    private Date updateTime;





}
