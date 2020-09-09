package com.lyht.business.info.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "个体工商户")
public interface InfoIndividualVO {

	@ApiModelProperty(value = "唯一ID(新增不填，修改必填)")
	Integer getId();

	@ApiModelProperty(value = "唯一内码（新增不填，修改必填）")
	String getNm();
	
	@ApiModelProperty("权属人编码")
	String getOwnerNm();

	@ApiModelProperty("项目编码")
	String getProjectNm();

	@ApiModelProperty("行政区")
	String getRegion();

	@ApiModelProperty("指标类型")
	String getZblx();

	@ApiModelProperty("征地范围")
	String getScope();

	@ApiModelProperty("字号名称")
	String getTypeName();

	@ApiModelProperty("个体工商户类型")
	String getIndiType();

	@ApiModelProperty("工商证号（执照）")
	String getRegisterNumber();

	@ApiModelProperty("执照有效期")
	BigDecimal getRegisterValidity();

	@ApiModelProperty("姓名")
	String getOperatorName();

	@ApiModelProperty("身份证号")
	String getIdCard();

	@ApiModelProperty("联系电话")
	String getPhoneNumber();

	@ApiModelProperty("经营场所")
	String getPremises();

	@ApiModelProperty("租赁场所(权属人)")
	String getLeaseOwnerNm();

	@ApiModelProperty("租赁面积")
	BigDecimal getLeaseArea();

	@ApiModelProperty("行业类别及代码")
	String getIndustryTypeCode();

	@ApiModelProperty("资金数额")
	BigDecimal getMoneyAmount();

	@ApiModelProperty("主要业务活动")
	String getMainActivity();

	@ApiModelProperty("从业人员")
	Integer getPractitioners();

	@ApiModelProperty("经营状况")
	String getOperCond();

	@ApiModelProperty("主管部门")
	String getCompetentDept();

	@ApiModelProperty("经营场所占地面积")
	BigDecimal getOperatingArea();

	@ApiModelProperty("经营占地情况说明")
	String getOperatingRemork();

	@ApiModelProperty("房附纳入权属人指标调查")
	Integer getIsSurvey();

	@ApiModelProperty("国税登记号")
	String getIrsRegisterNumber();

	@ApiModelProperty("地税登记号")
	String getTaxRegisterNumber();

	@ApiModelProperty("经营者住所")
	String getOperatorAbode();

	@ApiModelProperty("开业时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	Date getOpeningYears();

	@ApiModelProperty("免税原因")
	String getReasonsRemork();

	@ApiModelProperty("经营范围")
	String getBusinessScope();

	@ApiModelProperty("制砖机(台)")
	Integer getBrick();

	@ApiModelProperty("搅拌机(台)")
	Integer getBlender();

	@ApiModelProperty("碎石机(台)")
	Integer getCrusher();

	@ApiModelProperty("搬迁物资数量(车)")
	BigDecimal getMoveMaterialNum();

	@ApiModelProperty("搬迁物资车数")
	BigDecimal getMoveVehicleNumber();

	@ApiModelProperty("搬迁物资金额")
	BigDecimal getMoveMaterialMoney();

	@ApiModelProperty("停业补助面积")
	BigDecimal getClosureAssistanceArea();

	@ApiModelProperty("停业补助金额")
	BigDecimal getClosureAssistanceMoney();

	@ApiModelProperty("补偿费用合计")
	BigDecimal getCompensationAmount();

	@ApiModelProperty("高程")
	BigDecimal getAltd();

	@ApiModelProperty("图幅号")
	String getInMap();
	
	@ApiModelProperty("数据状态")
	String getStatus();

	@ApiModelProperty("阶段")
	String getStage();

	@ApiModelProperty(value = "创建时间")
	Date getCreateTime();

	@ApiModelProperty(value = "创建用户")
	String getCreateUser();

	@ApiModelProperty(value = "修改时间")
	Date getModifyTime();

	@ApiModelProperty(value = "修改用户")
	String getModifyUser();

	@ApiModelProperty("备注")
	String getRemark();
	
	

	@ApiModelProperty("项目--值")
	String getProjectValue();

	@ApiModelProperty("行政区--值")
	String getRegionValue();

	@ApiModelProperty("指标类型--值")
	String getZblxValue();

	@ApiModelProperty("阶段--值")
	String getStageValue();

	@ApiModelProperty("征地范围--值")
	String getScopeValue();

	@ApiModelProperty("户主姓名--值")
	String getOwnerValue();
	
	@ApiModelProperty("个体工商户类型--值")
	String getIndiTypeValue();

}