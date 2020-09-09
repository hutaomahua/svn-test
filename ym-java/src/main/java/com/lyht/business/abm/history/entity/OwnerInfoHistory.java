package com.lyht.business.abm.history.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 权属人历史信息记录表
 * 
 * @author wzw
 *
 */
@ApiModel(description = "权属人历史信息记录表")
@Entity
@Table(name = "t_abm_owner_info_history")
@EntityListeners(AuditingEntityListener.class)
@Data
public class OwnerInfoHistory {

	@ApiModelProperty(value = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ApiModelProperty(value = "唯一编码")
	private String nm;

	@ApiModelProperty(value = "权属人nm")
	@Column(name = "owner_nm")
	private String ownerNm;

	@ApiModelProperty(value = "权属人 信息json")
	@Column(name = "info_json")
	private String infoJson;

	@ApiModelProperty(value = "移民人口json")
	@Column(name = "immigrant_population_json")
	private String immigrantPopulationJson;

	@ApiModelProperty(value = "土地数据json")
	@Column(name = "land_json")
	private String landJson;

	@ApiModelProperty(value = "房屋数据json")
	@Column(name = "house_json")
	private String houseJson;

	@ApiModelProperty(value = "零星树木json")
	@Column(name = "tree_json")
	private String treeJson;

	@ApiModelProperty(value = "其他附属数据json")
	@Column(name = "other_json")
	private String otherJson;

	@ApiModelProperty(value = "房屋装修json")
	@Column(name = "fitment_json")
	private String fitmentJson;

	@ApiModelProperty(value = "个体户数据json")
	@Column(name = "individual_household_json")
	private String individualHouseholdJson;
	
	@ApiModelProperty(value= "农副业设施")
	@Column(name = "agricultural_facilities_json")
	private String agriculturalFacilitiesJson;
	
	@ApiModelProperty(value= "宅基地")
	@Column(name = "homestead_json")
	private String homesteadJson;

	@ApiModelProperty(value = "变更操作人")
	@Column(name = "change_operator")
	@CreatedBy
	private String changeOperator;

	@ApiModelProperty(value = "变更操作时间")
	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "operator_time", insertable = true)
	private Date operatorTime;
	
	

}
