package com.lyht.business.abm.removal.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "企业低值易耗品、物资及存货调查表")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_info_supplies_impl")
public class AbmEnterSuppliesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "唯一ID，修改必填")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ApiModelProperty(value = "内码")
	private String nm;

	@ApiModelProperty(value = "行政区")
	private String region;

	@ApiModelProperty(value = "项目编码")
	@Column(name = "project_nm")
	private String projectNm;

	@ApiModelProperty(value = "指标类型")
	@Column(name = "zblx")
	private String zblx;

	@ApiModelProperty(value = "征地范围")
	@Column(name = "scope")
	private String scope;

	@ApiModelProperty(value = "企事业编码")
	@Column(name = "enter_nm")
	private String enterNm;

	@ApiModelProperty(value = "物资名称")
	@Column(name = "name")
	private String name;

	@ApiModelProperty(value = "规格型号")
	@Column(name = "specifications")
	private String specifications;

	@ApiModelProperty(value = "数量")
	@Column(name = "numbers")
	private Integer numbers;

	@ApiModelProperty(value = "重量")
	@Column(name = "weight")
	private BigDecimal weight;

	@ApiModelProperty(value = "体积")
	@Column(name = "volume")
	private BigDecimal volume;

	@ApiModelProperty(value = "堆放地")
	@Column(name = "site_land")
	private String siteLand;

	@ApiModelProperty(value = "账面价值")
	@Column(name = "book_value")
	private BigDecimal bookValue;
	@ApiModelProperty(value = "数据状态")
	@Column(name = "status")
	private String status;

	@ApiModelProperty(value = "备注")
	@Column(name = "remark")
	private String remark;

	@ApiModelProperty(value = "阶段")
	@Column(name = "stage")
	private String stage;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
