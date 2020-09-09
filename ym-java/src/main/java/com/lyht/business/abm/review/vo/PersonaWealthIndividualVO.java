package com.lyht.business.abm.review.vo;

import io.swagger.annotations.ApiModelProperty;

public interface PersonaWealthIndividualVO {

	@ApiModelProperty(value = "id")
	Integer getId();

	@ApiModelProperty(value = "nm")
	String getNm();

	@ApiModelProperty(value = "行政区编码")
	String getRegion();

	@ApiModelProperty(value = "行政区全称")
	String getMergerName();

	@ApiModelProperty(value = "数据状态")
	String getStatus();

	@ApiModelProperty(value = "备注")
	String getRemark();

	@ApiModelProperty(value = "指标类型")
	String getZblx();

	@ApiModelProperty(value = "征地范围名称")
	String getScopeName();

	@ApiModelProperty(value = "征地范围nm")
	String getScope();

	@ApiModelProperty(value = "权属人姓名")
	String getOwnerName();

	@ApiModelProperty(value = "权属人nm")
	String getOwnerNm();

	@ApiModelProperty(value = "姓名")
	String operatorName();

	@ApiModelProperty(value = "工商证号 （执照）")
	String getRegisterNumber();

	@ApiModelProperty(value = "执照有效期 ")
	String getRegisterValidity();

	@ApiModelProperty(value = "身份证号 ")
	String getIdCard();

	@ApiModelProperty(value = "联系电话 ")
	String getPhoneNumber();

	@ApiModelProperty(value = "经营场所 ")
	String getPremises();

	@ApiModelProperty(value = "租赁场所(权属人) nm")
	String getLeaseOwnerNm();

	@ApiModelProperty(value = "租赁场所(权属人) 姓名")
	String getLeaseOwnerName();

	@ApiModelProperty(value = "行业类别及代码 ")
	String getIndustryTypeCode();

	@ApiModelProperty(value = "资金数额 ")
	String getMoneyAmount();

	@ApiModelProperty(value = "主要业务活动 ")
	String getMainActivity();

	@ApiModelProperty(value = "从业人员 ")
	String getPractitioners();

	@ApiModelProperty(value = "经营状况 ")
	String getOperCond();

	@ApiModelProperty(value = "主管部门 ")
	String getCompetentDept();

	@ApiModelProperty(value = "经营场所占地面积(亩) ")
	String getOperatingArea();

	@ApiModelProperty(value = "经营占地情况说明 ")
	String getOperatingRemork();

	@ApiModelProperty(value = "房附纳入权属人指标调查 ")
	String getIsSurvey();

	@ApiModelProperty(value = "高程 ")
	String getAltd();

	@ApiModelProperty(value = "图幅号")
	String getInMap();

	@ApiModelProperty(value = "阶段名称 ")
	String getStageName();

	@ApiModelProperty(value = "阶段nm")
	String getStage();

	@ApiModelProperty(value = "字号名称 ")
	String getTypeName();

	@ApiModelProperty(value = "租赁面积 ")
	String getLeaseArea();

	@ApiModelProperty(value = "国税登记号 ")
	String getIrsRegisterNumber();

	@ApiModelProperty(value = "地税登记号 ")
	String getTaxRegisterNumber();

	@ApiModelProperty(value = "经营者住所 ")
	String getOperatorAbode();

	@ApiModelProperty(value = "开业年月 ")
	String getOpeningYears();

	@ApiModelProperty(value = "免税原因 ")
	String getReasonsRemork();

	@ApiModelProperty(value = "经营范围 ")
	String getBusinessScope();

	@ApiModelProperty(value = "类型名称")
	String getIndiTypeName();

	@ApiModelProperty(value = "类型nm")
	String getIndiType();

	@ApiModelProperty(value = "制砖机(台) ")
	String getBrick();

	@ApiModelProperty(value = "搅拌机(台) ")
	String getBlender();

	@ApiModelProperty(value = "碎石机(台) ")
	String getCrusher();

	@ApiModelProperty(value = "搬迁物资数量 (m3)")
	String getMoveMaterialNum();

	@ApiModelProperty(value = "搬迁物资车数 (车)")
	String getMoveVehicleNumber();

	@ApiModelProperty(value = "搬迁物资金额 (元)")
	String getMoveMaterialMoney();

	@ApiModelProperty(value = "停业补助面积 (m2)")
	String getClosureAssistanceArea();

	@ApiModelProperty(value = "停业补助金额 (元)")
	String getClosureAssistanceMoney();

	@ApiModelProperty(value = "补偿费用合计 (元)")
	String getCompensationAmount();
}
