package com.lyht.business.abm.signed.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 协议信息表
 * @author wzw
 *
 */
@ApiModel(value = "协议信息表")
@Entity
@Table(name = "t_abm_protocol_info")
@Data
public class ProtocolInfo {

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

	@ApiModelProperty(value = "协议名称")
	@Column(name = "protocol_name")
	private String protocolName;
	
	@ApiModelProperty(value = "协议金额")
	@Column(name = "protocol_amount")
	private Double protocolAmount;
	
	@ApiModelProperty(value = "签订时间")
	@Column(name ="complete_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date completeTime;
	
	@ApiModelProperty(value = "是否资金代管")
	@Column(name = "is_escrow")
	private Integer isEscrow;
	
	@ApiModelProperty(value = "协议签订范围")
	@Column(name = "protocol_area")
	private String protocolArea;
	
	@ApiModelProperty(value = "签订状态 0 未签订 1 已签订")
	private Integer state;
	
	@ApiModelProperty(value = "流程id")
	@Column(name = "process_id")
	private String processId;
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
