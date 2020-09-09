package com.lyht.business.cost.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("补偿费用总表")
@Entity
@Table(name = "t_compensation_cost")
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@GenericGenerator(name = "jpa-guid", strategy = "guid")
public class CostEntity {

	@ApiModelProperty("主键")
	@Id
	@GeneratedValue(generator = "jpa-guid")
	@Column(name = "id")
	private String id;

	@ApiModelProperty("权属人编码")
	@Column(name = "owner_nm")
	private String ownerNm;

	@ApiModelProperty("搬迁安置费用(元)")
	@Column(name = "move_amount")
	private BigDecimal moveAmount;

	@ApiModelProperty("生产安置费用(元)")
	@Column(name = "production_amount")
	private BigDecimal productionAmount;

	@ApiModelProperty("房屋补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	@Column(name = "house_status")
	private Integer houseStatus;

	@ApiModelProperty("房屋装修补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	@Column(name = "house_decoration_status")
	private Integer houseDecorationStatus;

	@ApiModelProperty("附属建筑物补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	@Column(name = "building_status")
	private Integer buildingStatus;

	@ApiModelProperty("农副业设施补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	@Column(name = "agricultural_facilities_status")
	private Integer agriculturalFacilitiesStatus;

	@ApiModelProperty("零星树木补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	@Column(name = "trees_status")
	private Integer treesStatus;

	@ApiModelProperty("个体工商户补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	@Column(name = "individual_status")
	private Integer individualStatus;

	@ApiModelProperty("搬迁补助状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿（远迁），允许签订协议，2：有补偿（近迁），允许签订协议）")
	@Column(name = "relocation_allowance_status")
	private Integer relocationAllowanceStatus;

	@ApiModelProperty("其他补助状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿（农村），允许签订协议，2：有补偿（集镇），允许签订协议）")
	@Column(name = "other_status")
	private Integer otherStatus;

	@ApiModelProperty("困难户补助状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	@Column(name = "difficult_status")
	private Integer difficultStatus;

	@ApiModelProperty("基础设施补助状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	@Column(name = "infrastructure_status")
	private Integer infrastructureStatus;

	@ApiModelProperty("宅基地补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿（分散安置），允许签订协议，2：有补偿（集中安置），允许签订协议）")
	@Column(name = "homestead_status")
	private Integer homesteadStatus;

	@ApiModelProperty("征收土地补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	@Column(name = "levy_land_status")
	private Integer levyLandStatus;

	@ApiModelProperty("成片青苗及果木补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	@Column(name = "young_crops_status")
	private Integer youngCropsStatus;

	@ApiModelProperty("生产安置人口补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	@Column(name = "produce_population_status")
	private Integer producePopulationStatus;

	@ApiModelProperty("生产安置土地补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	@Column(name = "produce_land_status")
	private Integer produceLandStatus;

	@ApiModelProperty("房屋补偿费用(元)")
	@Column(name = "house_amount")
	private BigDecimal houseAmount;

	@ApiModelProperty("房屋装修补偿费用(元)")
	@Column(name = "house_decoration_amount")
	private BigDecimal houseDecorationAmount;

	@ApiModelProperty("附属建筑物补偿费用(元)")
	@Column(name = "building_amount")
	private BigDecimal buildingAmount;

	@ApiModelProperty("农副业设施补偿费用(元)")
	@Column(name = "agricultural_facilities_amount")
	private BigDecimal agriculturalFacilitiesAmount;

	@ApiModelProperty("零星树木补偿费用(元)")
	@Column(name = "trees_amount")
	private BigDecimal treesAmount;

	@ApiModelProperty("个体工商户补偿费用(元)")
	@Column(name = "individual_amount")
	private BigDecimal individualAmount;

	@ApiModelProperty("搬迁补助费用(元)")
	@Column(name = "relocation_allowance_amount")
	private BigDecimal relocationAllowanceAmount;

	@ApiModelProperty("其他补助费用(元)")
	@Column(name = "other_amount")
	private BigDecimal otherAmount;

	@ApiModelProperty("困难户补助费用(元)")
	@Column(name = "difficult_amount")
	private BigDecimal difficultAmount;

	@ApiModelProperty("基础设施补助费用(元)")
	@Column(name = "infrastructure_amount")
	private BigDecimal infrastructureAmount;

	@ApiModelProperty("宅基地补偿费用(元)")
	@Column(name = "homestead_amount")
	private BigDecimal homesteadAmount;

	@ApiModelProperty("征收土地补偿费用(元)")
	@Column(name = "levy_land_amount")
	private BigDecimal levyLandAmount;

	@ApiModelProperty("成片青苗及果木补偿费用(元)")
	@Column(name = "young_crops_amount")
	private BigDecimal youngCropsAmount;

	@ApiModelProperty("生产安置人口补偿(元/人·月)")
	@Column(name = "produce_population_amount")
	private BigDecimal producePopulationAmount;

	@ApiModelProperty("生产安置土地补偿(元/亩·月)")
	@Column(name = "produce_land_amount")
	private BigDecimal produceLandAmount;

	@ApiModelProperty("数据状态(0：正常，1：部分已计算，2：计算完成)")
	@Column(name = "status")
	private Integer status;

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

	@ApiModelProperty("备注")
	@Column(name = "remark")
	private String remark;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
