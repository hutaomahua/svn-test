package com.lyht.business.abm.landAllocation.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Table(name = "t_abm_land_pool")
@Data
@ApiModel(description = "县土地分解池")
public class LandPoolEntity {

	@ApiModelProperty(value = "主键")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ApiModelProperty(value = "内码")
	private String nm;

	@ApiModelProperty(value = "行政区域")
	@Column(name = "region")
	private String region;

	@ApiModelProperty(value = "行政区级别")
	@Column(name = "region_level")
	private String regionLevel;

	@ApiModelProperty(value = "征地范围")
	@Column(name = "scope")
	private String scope;

	@ApiModelProperty(value = "大类")
	@Column(name = "all_type")
	private String allType;

	@ApiModelProperty(value = "一级分类")
	@Column(name = "type_one")
	private String typeOne;

	@ApiModelProperty(value = "二级分类")
	@Column(name = "type_two")
	private String typeTwo;

	@ApiModelProperty(value = "三级分类")
	@Column(name = "type_three")
	private String typeThree;

	@ApiModelProperty(value = "土地总面积")
	@Column(name = "area")
	private BigDecimal area;

	@ApiModelProperty(value = "已分解面积")
	@Column(name = "separate_area")
	private BigDecimal separateArea;
	
	@ApiModelProperty(value = "是否可分解 0：可分解,1：不可分级")
	private Integer status;

}
