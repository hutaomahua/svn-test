package com.lyht.business.abm.review.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实物指标复核申请（个人财产）
 * @author wzw
 *
 */
@ApiModel(description = "实物指标复核申请（个人财产）")
@Entity
@Table(name = "t_abm_personal_wealth")
@EntityListeners(AuditingEntityListener.class)
@Data
public class PersonalWealth {

	@ApiModelProperty(value ="id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ApiModelProperty(value ="唯一编码")
	private String nm;
	
	@ApiModelProperty(value ="权属人nm")
	@Column(name = "owner_nm")
	private String ownerNm;
	
	@ApiModelProperty(value ="复核申请人")
	@CreatedBy
	private String reviewer;
	
	@ApiModelProperty(value ="申请编号")
	@Column(name = "application_number")
	private String applicationNumber;
	
	@ApiModelProperty(value ="复核项目")
	@Column(name = "review_project")
	private String reviewProject;
	
	@ApiModelProperty(value ="征地范围")
	private String scope;
	
	@ApiModelProperty(value = "复核申请时间")
	@Column(name = "review_time", updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
	@CreationTimestamp
	private Date reviewTime;
	
	@ApiModelProperty(value = "复审申请原由")
	@Column(name = "review_reason")
	private String reviewReason;
	
	@ApiModelProperty(value ="复核流程id")
	@Column(name = "review_process_id")
	private String reviewProcessId;
	
	@ApiModelProperty(value ="变更操作人")
	@Column(name = "change_operator")
	@LastModifiedBy
	private String changeOperator;
	
	@ApiModelProperty(value ="变更操作时间")
	@UpdateTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "operator_time", insertable = false, updatable = true)
	private Date operatorTime;
	
	@ApiModelProperty(value ="变更流程id")
	@Column(name = "change_process_id")
	private String changeProcessId;
	
	@ApiModelProperty(value ="复核状态")
	@Column(name = "review_state")
	private Integer reviewState;
	
	@ApiModelProperty(value ="公示记录nm")
	@Column(name = "publicity_nm")
	private String publicityNm;
	
}
