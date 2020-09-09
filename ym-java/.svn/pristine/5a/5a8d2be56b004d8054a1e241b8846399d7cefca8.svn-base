package com.lyht.business.abm.signed.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel(value = "资金代管协议信息表")
@Entity
@Table(name = "t_abm_protocol_escrow")
@Data
@ToString
public class ProtocolEscrow {

	@ApiModelProperty(value = "唯一id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ApiModelProperty(value = "唯一nm")
	private String nm;

	@ApiModelProperty(value = "权属人")
	@Column(name = "owner_nm")
	private String ownerNm;

	@ApiModelProperty(value = "协议编号")
	@Column(name = "protocol_code")
	private String protocolCode;

	@ApiModelProperty(value = "合同/协议名称")
	@Column(name = "protocol_name")
	private String protocolName;

	@ApiModelProperty(value = "户型")
	@Column(name = "house_type")
	private String houseType;

	@ApiModelProperty(value = "宅基地编号")
	@Column(name = "homestead_code")
	private String homesteadCode;

	@ApiModelProperty(value = "建设单位")
	private String company;

	@ApiModelProperty(value = "安置房总费用(元)")
	@Column(name = "placement_money")
	private Double placementMoney;

	@ApiModelProperty(value = "代管资金(元)")
	@Column(name = "escrow_money")
	private Double escrowMoney;

	@ApiModelProperty(value = "签订时间")
	@Column(name = "complete_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date completeTime;

	@ApiModelProperty(value = "操作人")
	@Column(name = "staff_nm")
	private String staffNm;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "可编辑内容")
	private String content;

	@ApiModelProperty(value = "签订状态  0 未签订 1 已签订")
	private Integer state;
	
	@ApiModelProperty(value = "流程id")
	@Column(name = "process_id")
	private String processId;
	
	@ApiModelProperty(value = "安置点可编辑")
	private String place;
	
}
