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

@ApiModel(description = "企业生产经营情况及财务指标认定表")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_info_enter_business_impl")
public class AbmEnterBusinessEntity implements Serializable {

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

	@ApiModelProperty(value = "时间")
	@Column(name = "times")
	private String times;

	@ApiModelProperty(value = "主要产品")
	@Column(name = "main_products")
	private String mainProducts;

	@ApiModelProperty(value = "产量")
	@Column(name = "production")
	private String production;

	@ApiModelProperty(value = "产品主要市场")
	@Column(name = "main_market")
	private String mainMarket;

	@ApiModelProperty(value = "原料与燃烧来源")
	@Column(name = "combustion_sources")
	private String combustionSources;

	@ApiModelProperty(value = "企业总产值")
	@Column(name = "total_output")
	private BigDecimal totalOutput;

	@ApiModelProperty(value = "主营业收入")
	@Column(name = "taking")
	private BigDecimal taking;

	@ApiModelProperty(value = "营业费用")
	@Column(name = "operating_expenses")
	private BigDecimal operatingExpenses;

	@ApiModelProperty(value = "管理费")
	@Column(name = "management_fee")
	private BigDecimal managementFee;

	@ApiModelProperty(value = "财务费")
	@Column(name = "financial_cost")
	private BigDecimal financialCost;

	@ApiModelProperty(value = "工人工资")
	@Column(name = "workers_wages")
	private BigDecimal workersWages;

	@ApiModelProperty(value = "三险支出	")
	@Column(name = "three_risk_spending")
	private BigDecimal threeRiskSpending;

	@ApiModelProperty(value = "排污费总额")
	@Column(name = "total_discharge")
	private BigDecimal totalDischarge;

	@ApiModelProperty(value = "税收总额")
	@Column(name = "total_revenue")
	private BigDecimal totalRevenue;

	@ApiModelProperty(value = "年利润")
	@Column(name = "year_profit")
	private BigDecimal yearProfit;

	@ApiModelProperty(value = "固定资产原值")
	@Column(name = "original_value")
	private BigDecimal originalValue;

	@ApiModelProperty(value = "固定资产净值")
	@Column(name = "net_value")
	private BigDecimal netValue;

	@ApiModelProperty(value = "高程")
	@Column(name = "altd")
	private String altd;

	@ApiModelProperty(value = "图幅号")
	@Column(name = "in_map")
	private String inMap;

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
