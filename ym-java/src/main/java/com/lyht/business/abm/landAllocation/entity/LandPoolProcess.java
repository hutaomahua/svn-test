package com.lyht.business.abm.landAllocation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "t_abm_land_pool_process")
@ApiModel(description = "土地审核")
@Data
@ToString
public class LandPoolProcess {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "id")
	private Integer id;
	
	@ApiModelProperty(value = "nm")
	private String nm;
	
	@ApiModelProperty(value = "分解面积")
	private Double area;
	
	@ApiModelProperty(value = "流程id")
	@Column(name = "wealth_nm")
	private String wealthNm;
	
	@ApiModelProperty(value = "分解池id")
	@Column(name = "pool_id")
	private Integer poolId;
	
	@ApiModelProperty(value = "土地分解nm")
	@Column(name = "audit_id")
	private Integer auditId;
}
