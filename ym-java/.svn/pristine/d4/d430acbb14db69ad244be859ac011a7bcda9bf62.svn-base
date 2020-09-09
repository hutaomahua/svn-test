package com.lyht.business.abm.review.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 实物指标复核申请（个人财产） 变更数据
 * @author wzw
 *
 */
@ApiModel(description = "实物指标复核申请（个人财产） 变更数据")
@Entity
@Table(name = "t_abm_personal_wealth_data")
@Data
@ToString
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
@DynamicInsert
@DynamicUpdate
public class PersonalWealthData {

	@ApiModelProperty(value ="id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ApiModelProperty(value ="唯一编码")
	private String nm;
	
	@ApiModelProperty(value = "业务表nm")
	@Column(name = "master_nm")
	private String masterNm;
	
	@ApiModelProperty(value ="移民人口json")
	@Column(name = "immigrant_population_json")
	private String immigrantPopulationJson;
	
	@ApiModelProperty(value ="土地数据json")
	@Column(name = "land_json")
	private String landJson;
	
	@ApiModelProperty(value ="房屋数据json")
	@Column(name = "house_json")
	private String houseJson;
	
	@ApiModelProperty(value ="零星树木json")
	@Column(name = "tree_json")
	private String treeJson;
	
	@ApiModelProperty(value ="其他附属数据json")
	@Column(name ="other_json")
	private String otherJson;
	
	@ApiModelProperty(value ="房屋装修json")
	@Column(name = "fitment_json")
	private String fitmentJson;
	
	@ApiModelProperty(value ="个体户数据json")
	@Column(name = "individual_household_json")
	private String individualHouseholdJson;
	
	
	@ApiModelProperty(value= "农副业设施")
	@Column(name = "agricultural_facilities_json")
	private String agriculturalFacilitiesJson;
	
	@ApiModelProperty(value= "宅基地")
	@Column(name = "homestead_json")
	private String homesteadJson;
	
}
