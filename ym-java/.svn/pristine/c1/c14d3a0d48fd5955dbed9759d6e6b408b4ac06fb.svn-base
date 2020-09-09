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
@ApiModel("个体工商户补偿费")
@Entity
@Table(name = "t_cost_individual")
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@GenericGenerator(name = "jpa-guid", strategy = "guid")
public class CostIndividualEntity {

	@ApiModelProperty("主键")
	@Id
	@GeneratedValue(generator = "jpa-guid")
	@Column(name = "id")
	private String id;

	@ApiModelProperty("权属人编码")
	@Column(name = "owner_nm")
	private String ownerNm;

	@ApiModelProperty("项目")
	@Column(name = "project_name")
	private String projectName;

	@ApiModelProperty("搬迁物资数量")
	@Column(name = "move_material_num")
	private BigDecimal moveMaterialNum;

	@ApiModelProperty("搬迁物资车数")
	@Column(name = "move_vehicle_number")
	private BigDecimal moveVehicleNumber;

	@ApiModelProperty("搬迁物资金额")
	@Column(name = "move_material_money")
	private BigDecimal moveMaterialMoney;

	@ApiModelProperty("停业补助面积")
	@Column(name = "closure_assistance_area")
	private BigDecimal closureAssistanceArea;

	@ApiModelProperty("停业补助金额")
	@Column(name = "closure_assistance_money")
	private BigDecimal closureAssistanceMoney;

	@ApiModelProperty("总额")
	@Column(name = "amount")
	private BigDecimal amount;

	@ApiModelProperty("数据状态(0:正常，-1：无效)")
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
