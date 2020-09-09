package com.lyht.business.abm.removal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = " 企业基本情况调查表")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_info_enterprise_impl")
public class AbmEnterpriseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "唯一ID，修改必填")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ApiModelProperty(value = "内码")
	private String nm;

	@ApiModelProperty(value = "项目编码")
	@Column(name = "project_nm")
	private String projectNm;

	@ApiModelProperty(value = "行政区")
	private String region;

	@ApiModelProperty(value = "指标类型")
	@Column(name = "zblx")
	private String zblx;

	@ApiModelProperty(value = "征地范围")
	@Column(name = "scope")
	private String scope;

	@ApiModelProperty(value = "名称")
	@Column(name = "name")
	private String name;

	@ApiModelProperty(value = "权属人编码")
	@Column(name = "owner_nm")
	private String ownerNm;

	@ApiModelProperty(value = "企业法人")
	@Column(name = "legal_person")
	private String legalPerson;

	@ApiModelProperty(value = "身份证号")
	@Column(name = "id_card")
	private String idCard;

	@ApiModelProperty(value = "注册时间")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Column(name = "register_time")
	private Date registerTime;

	@ApiModelProperty(value = "执照注册号")
	@Column(name = "register_number")
	private String registerNumber;

	@ApiModelProperty(value = "注册资金(万元)")
	@Column(name = "register_money")
	private BigDecimal registerMoney;

	@ApiModelProperty(value = "联系电话")
	@Column(name = "phone_number")
	private String phoneNumber;

	@ApiModelProperty(value = "行业类别及代码")
	@Column(name = "industry_cate")
	private String industryCate;

	@ApiModelProperty(value = "企业性质")
	@Column(name = "enterprise_nature")
	private String enterpriseNature;

	@ApiModelProperty(value = "企业规模")
	@Column(name = "enterprise_scale")
	private String enterpriseScale;

	@ApiModelProperty(value = "主管部门")
	@Column(name = "competent_dept")
	private String competentDept;

	@ApiModelProperty(value = "职工总人数")
	@Column(name = "employees_number")
	private int employeesNumber;

	@ApiModelProperty(value = "正式职工人数")
	@Column(name = "official_staff")
	private int officialStaff;

	@ApiModelProperty(value = "投产时间")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Column(name = "production_time")
	private Date productionTime;

	@ApiModelProperty(value = "年产值")
	@Column(name = "output_value")
	private BigDecimal outputValue;

	@ApiModelProperty(value = "主要业务活动")
	@Column(name = "business_activities")
	private String businessActivities;

	@ApiModelProperty(value = "经营状况")
	@Column(name = "oper_cond")
	private String operCond;

	@ApiModelProperty(value = "企业占地")
	@Column(name = "covers_area")
	private BigDecimal coversArea;

	@ApiModelProperty(value = "出让面积")
	@Column(name = "transfer_area")
	private BigDecimal transferArea;

	@ApiModelProperty(value = "划拨面积")
	@Column(name = "allocated_area")
	private BigDecimal allocatedArea;

	@ApiModelProperty(value = "租赁面积")
	@Column(name = "lease_area")
	private BigDecimal leaseArea;

	@ApiModelProperty(value = "企业存在本库区的同时拆迁的全资子公司、分公司情况")
	@Column(name = "branch_info")
	private String branchInfo;

	@ApiModelProperty(value = "企业存在本库区的同时拆迁的投资企业情况")
	@Column(name = "investment_enterprises")
	private String investmentEnterprises;

	@ApiModelProperty(value = "土地使用证号")
	@Column(name = "land_use_no")
	private String landUseNo;

	@ApiModelProperty(value = "高程")
	@Column(name = "altd")
	private BigDecimal altd;

	@ApiModelProperty(value = "图幅号")
	@Column(name = "in_map")
	private String inMap;

	@ApiModelProperty(value = "remark")
	@Column(name = "remark")
	private String remark;

	@ApiModelProperty(value = "复核申请")
	@Column(name = "fh_state")
	private String fhstate;

}
