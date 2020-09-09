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
@ApiModel("零星树木补偿费")
@Entity
@Table(name = "t_cost_trees")
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@GenericGenerator(name = "jpa-guid", strategy = "guid")
public class CostTreesEntity {

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

	@ApiModelProperty("单位")
	@Column(name = "unit")
	private String unit;

	@ApiModelProperty("单价")
	@Column(name = "price")
	private BigDecimal price;

	@ApiModelProperty("数量")
	@Column(name = "num")
	private BigDecimal num;

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
