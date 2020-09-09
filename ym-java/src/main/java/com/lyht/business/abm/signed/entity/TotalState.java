package com.lyht.business.abm.signed.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "状态总表")
@Entity
@Table(name = "t_abm_total_state")
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
@Data
public class TotalState {

	@ApiModelProperty(value = "唯一id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ApiModelProperty(value = "唯一nm")
	private String nm;

	@ApiModelProperty(value = "权属人")
	@Column(name = "owner_nm")
	private String ownerNm;

	@ApiModelProperty(value = "协议完成状态 ")
	@Column(name = "protocol_state")
	private Integer protocolState;

	@ApiModelProperty("房屋补偿状态(0：未签订，1：已签订)")
	@Column(name = "house_status")
	private Integer houseStatus;

	@ApiModelProperty("房屋装修补偿状态(0：未签订，1：已签订)")
	@Column(name = "house_decoration_status")
	private Integer houseDecorationStatus;

	@ApiModelProperty("附属建筑物补偿状态(0：未签订，1：已签订)")
	@Column(name = "building_status")
	private Integer buildingStatus;

	@ApiModelProperty("农副业设施补偿状态(0：未签订，1：已签订)")
	@Column(name = "agricultural_facilities_status")
	private Integer agriculturalFacilitiesStatus;

	@ApiModelProperty("零星树木补偿状态(0：未签订，1：已签订)")
	@Column(name = "trees_status")
	private Integer treesStatus;

	@ApiModelProperty("个体工商户补偿状态(0：未签订，1：已签订)")
	@Column(name = "individual_status")
	private Integer individualStatus;

	@ApiModelProperty("搬迁补助状态(0：未签订，1：已签订)")
	@Column(name = "relocation_allowance_status")
	private Integer relocationAllowanceStatus;

	@ApiModelProperty("其他补助状态(0：未签订，1：已签订)")
	@Column(name = "other_status")
	private Integer otherStatus;

	@ApiModelProperty("困难补助状态(0：未签订，1：已签订)")
	@Column(name = "difficult_status")
	private Integer difficultStatus;

	@ApiModelProperty("基础设施补助状态(0：未签订，1：已签订)")
	@Column(name = "infrastructure_status")
	private Integer infrastructureStatus;

	@ApiModelProperty("宅基地补偿状态(0：未签订，1：已签订)")
	@Column(name = "homestead_status")
	private Integer homesteadStatus;

	@ApiModelProperty("征收土地补偿状态(0：未签订，1：已签订)")
	@Column(name = "levy_land_status")
	private Integer levyLandStatus;

	@ApiModelProperty("成片青苗及果木补偿状态(0：未签订，1：已签订)")
	@Column(name = "young_crops_status")
	private Integer youngCropsStatus;
	
	@ApiModelProperty(value = "生产安置补偿签订状态(0：未签订，1：已签订)")
	@Column(name = "production_status")
	private Integer productionStatus;
	
	@ApiModelProperty(value = "搬迁安置补偿签订状态(0：未签订，1：已签订)")
	@Column(name = "move_status")
	private Integer moveStatus;
	
	@ApiModelProperty(value = "创建时间")
	@CreatedDate
	@Column(name = "create_time", updatable = false)
	private Date createTime;
	@ApiModelProperty(value = "创建用户")
	@CreatedBy
	@Column(name = "create_user", updatable = false)
	private String createUser;
	@ApiModelProperty(value = "修改时间")
	@LastModifiedDate
	@Column(name = "modify_time", insertable = false)
	private Date modifyTime;
	@ApiModelProperty(value = "修改用户")
	@LastModifiedBy
	@Column(name = "modify_user", insertable = false)
	private String modifyUser;
	
}
