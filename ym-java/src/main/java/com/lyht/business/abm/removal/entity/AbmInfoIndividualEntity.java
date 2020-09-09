package com.lyht.business.abm.removal.entity;

import java.math.BigDecimal;
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
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "个体工商户")
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "t_info_individual_impl")
public class AbmInfoIndividualEntity {
	@ApiModelProperty(value = "唯一ID(新增不填，修改必填)")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ApiModelProperty(value = "唯一内码（新增不填，修改必填）")
	private String nm;

	@ApiModelProperty("权属人编码")
	@Column(name = "owner_nm")
	private String ownerNm;

	@ApiModelProperty("项目编码")
	@Column(name = "project_nm")
	private String projectNm;

	@ApiModelProperty("行政区")
	@Column(name = "region")
	private String region;

	@ApiModelProperty("指标类型")
	@Column(name = "zblx")
	private String zblx;

	@ApiModelProperty("征地范围")
	@Column(name = "scope")
	private String scope;

	@ApiModelProperty("字号名称")
	@Column(name = "type_name")
	private String typeName;

	@ApiModelProperty("个体工商户类型")
	@Column(name = "indi_type")
	private String indiType;

	@ApiModelProperty("工商证号（执照）")
	@Column(name = "register_number")
	private String registerNumber;

	@ApiModelProperty("执照有效期")
	@Column(name = "register_validity")
	private BigDecimal registerValidity;

	@ApiModelProperty("姓名")
	@Column(name = "operator_name")
	private String operatorName;

	@ApiModelProperty("身份证号")
	@Column(name = "id_card")
	private String idCard;

	@ApiModelProperty("联系电话")
	@Column(name = "phone_number")
	private String phoneNumber;

	@ApiModelProperty("经营场所")
	@Column(name = "premises")
	private String premises;

	@ApiModelProperty("租赁场所(权属人)")
	@Column(name = "lease_owner_nm")
	private String leaseOwnerNm;

	@ApiModelProperty("租赁面积")
	@Column(name = "lease_area")
	private BigDecimal leaseArea;

	@ApiModelProperty("行业类别及代码")
	@Column(name = "industry_type_code")
	private String industryTypeCode;

	@ApiModelProperty("资金数额")
	@Column(name = "money_amount")
	private BigDecimal moneyAmount;

	@ApiModelProperty("主要业务活动")
	@Column(name = "main_activity")
	private String mainActivity;

	@ApiModelProperty("从业人员")
	@Column(name = "practitioners")
	private Integer practitioners;

	@ApiModelProperty("经营状况")
	@Column(name = "oper_cond")
	private String operCond;

	@ApiModelProperty("主管部门")
	@Column(name = "competent_dept")
	private String competentDept;

	@ApiModelProperty("经营场所占地面积")
	@Column(name = "operating_area")
	private BigDecimal operatingArea;

	@ApiModelProperty("经营占地情况说明")
	@Column(name = "operating_remork")
	private String operatingRemork;

	@ApiModelProperty("房附纳入权属人指标调查")
	@Column(name = "is_survey")
	private Integer isSurvey;

	@ApiModelProperty("国税登记号")
	@Column(name = "irs_register_number")
	private String irsRegisterNumber;

	@ApiModelProperty("地税登记号")
	@Column(name = "tax_register_number")
	private String taxRegisterNumber;

	@ApiModelProperty("经营者住所")
	@Column(name = "operator_abode")
	private String operatorAbode;

	@ApiModelProperty("开业时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "opening_years")
	private Date openingYears;

	@ApiModelProperty("免税原因")
	@Column(name = "reasons_remork")
	private String reasonsRemork;

	@ApiModelProperty("经营范围")
	@Column(name = "business_scope")
	private String businessScope;

	@ApiModelProperty("制砖机(台)")
	@Column(name = "brick")
	private Integer brick;

	@ApiModelProperty("搅拌机(台)")
	@Column(name = "blender")
	private Integer blender;

	@ApiModelProperty("碎石机(台)")
	@Column(name = "crusher")
	private Integer crusher;

	@ApiModelProperty("搬迁物资数量(车)")
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

	@ApiModelProperty("补偿费用合计")
	@Column(name = "compensation_amount")
	private BigDecimal compensationAmount;

	@ApiModelProperty("高程")
	@Column(name = "altd")
	private BigDecimal altd;

	@ApiModelProperty("图幅号")
	@Column(name = "in_map")
	private String inMap;

	@ApiModelProperty("数据状态")
	@Column(name = "status")
	private String status;

	@ApiModelProperty("阶段")
	@Column(name = "stage")
	private String stage;

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
	@Column(name = "modify_time")
	private Date modifyTime;

	@ApiModelProperty(value = "修改用户")
	@LastModifiedBy
	@Column(name = "modify_user")
	private String modifyUser;

	@ApiModelProperty("备注")
	@Column(name = "remark")
	private String remark;

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
