package com.lyht.business.info.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "个体工商户")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "t_info_individual")
public class InfoIndividualEntity {

	@ApiModelProperty(value = "唯一ID(新增不填，修改必填)")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
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

	@ApiModelProperty("文件数量")
	@Transient
	private Integer fileCount;

	public Integer getFileCount() {
		return fileCount;
	}

	public void setFileCount(Integer fileCount) {
		this.fileCount = fileCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public String getOwnerNm() {
		return ownerNm;
	}

	public void setOwnerNm(String ownerNm) {
		this.ownerNm = ownerNm;
	}

	public String getProjectNm() {
		return projectNm;
	}

	public void setProjectNm(String projectNm) {
		this.projectNm = projectNm;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getZblx() {
		return zblx;
	}

	public void setZblx(String zblx) {
		this.zblx = zblx;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getIndiType() {
		return indiType;
	}

	public void setIndiType(String indiType) {
		this.indiType = indiType;
	}

	public String getRegisterNumber() {
		return registerNumber;
	}

	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	public BigDecimal getRegisterValidity() {
		return registerValidity;
	}

	public void setRegisterValidity(BigDecimal registerValidity) {
		this.registerValidity = registerValidity;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPremises() {
		return premises;
	}

	public void setPremises(String premises) {
		this.premises = premises;
	}

	public String getLeaseOwnerNm() {
		return leaseOwnerNm;
	}

	public void setLeaseOwnerNm(String leaseOwnerNm) {
		this.leaseOwnerNm = leaseOwnerNm;
	}

	public BigDecimal getLeaseArea() {
		return leaseArea;
	}

	public void setLeaseArea(BigDecimal leaseArea) {
		this.leaseArea = leaseArea;
	}

	public String getIndustryTypeCode() {
		return industryTypeCode;
	}

	public void setIndustryTypeCode(String industryTypeCode) {
		this.industryTypeCode = industryTypeCode;
	}

	public BigDecimal getMoneyAmount() {
		return moneyAmount;
	}

	public void setMoneyAmount(BigDecimal moneyAmount) {
		this.moneyAmount = moneyAmount;
	}

	public String getMainActivity() {
		return mainActivity;
	}

	public void setMainActivity(String mainActivity) {
		this.mainActivity = mainActivity;
	}

	public Integer getPractitioners() {
		return practitioners;
	}

	public void setPractitioners(Integer practitioners) {
		this.practitioners = practitioners;
	}

	public String getOperCond() {
		return operCond;
	}

	public void setOperCond(String operCond) {
		this.operCond = operCond;
	}

	public String getCompetentDept() {
		return competentDept;
	}

	public void setCompetentDept(String competentDept) {
		this.competentDept = competentDept;
	}

	public BigDecimal getOperatingArea() {
		return operatingArea;
	}

	public void setOperatingArea(BigDecimal operatingArea) {
		this.operatingArea = operatingArea;
	}

	public String getOperatingRemork() {
		return operatingRemork;
	}

	public void setOperatingRemork(String operatingRemork) {
		this.operatingRemork = operatingRemork;
	}

	public Integer getIsSurvey() {
		return isSurvey;
	}

	public void setIsSurvey(Integer isSurvey) {
		this.isSurvey = isSurvey;
	}

	public String getIrsRegisterNumber() {
		return irsRegisterNumber;
	}

	public void setIrsRegisterNumber(String irsRegisterNumber) {
		this.irsRegisterNumber = irsRegisterNumber;
	}

	public String getTaxRegisterNumber() {
		return taxRegisterNumber;
	}

	public void setTaxRegisterNumber(String taxRegisterNumber) {
		this.taxRegisterNumber = taxRegisterNumber;
	}

	public String getOperatorAbode() {
		return operatorAbode;
	}

	public void setOperatorAbode(String operatorAbode) {
		this.operatorAbode = operatorAbode;
	}

	public Date getOpeningYears() {
		return openingYears;
	}

	public void setOpeningYears(Date openingYears) {
		this.openingYears = openingYears;
	}

	public String getReasonsRemork() {
		return reasonsRemork;
	}

	public void setReasonsRemork(String reasonsRemork) {
		this.reasonsRemork = reasonsRemork;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public Integer getBrick() {
		return brick;
	}

	public void setBrick(Integer brick) {
		this.brick = brick;
	}

	public Integer getBlender() {
		return blender;
	}

	public void setBlender(Integer blender) {
		this.blender = blender;
	}

	public Integer getCrusher() {
		return crusher;
	}

	public void setCrusher(Integer crusher) {
		this.crusher = crusher;
	}

	public BigDecimal getMoveMaterialNum() {
		return moveMaterialNum;
	}

	public void setMoveMaterialNum(BigDecimal moveMaterialNum) {
		this.moveMaterialNum = moveMaterialNum;
	}

	public BigDecimal getMoveVehicleNumber() {
		return moveVehicleNumber;
	}

	public void setMoveVehicleNumber(BigDecimal moveVehicleNumber) {
		this.moveVehicleNumber = moveVehicleNumber;
	}

	public BigDecimal getMoveMaterialMoney() {
		return moveMaterialMoney;
	}

	public void setMoveMaterialMoney(BigDecimal moveMaterialMoney) {
		this.moveMaterialMoney = moveMaterialMoney;
	}

	public BigDecimal getClosureAssistanceArea() {
		return closureAssistanceArea;
	}

	public void setClosureAssistanceArea(BigDecimal closureAssistanceArea) {
		this.closureAssistanceArea = closureAssistanceArea;
	}

	public BigDecimal getClosureAssistanceMoney() {
		return closureAssistanceMoney;
	}

	public void setClosureAssistanceMoney(BigDecimal closureAssistanceMoney) {
		this.closureAssistanceMoney = closureAssistanceMoney;
	}

	public BigDecimal getCompensationAmount() {
		return compensationAmount;
	}

	public void setCompensationAmount(BigDecimal compensationAmount) {
		this.compensationAmount = compensationAmount;
	}

	public BigDecimal getAltd() {
		return altd;
	}

	public void setAltd(BigDecimal altd) {
		this.altd = altd;
	}

	public String getInMap() {
		return inMap;
	}

	public void setInMap(String inMap) {
		this.inMap = inMap;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}