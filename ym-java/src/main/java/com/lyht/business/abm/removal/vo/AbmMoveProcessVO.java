package com.lyht.business.abm.removal.vo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("搬迁安置流程详情")
public interface AbmMoveProcessVO {

	@ApiModelProperty("户主NM")
	String getOwnerNm();

	@ApiModelProperty("户主姓名")
	String getOwnerName();

	@ApiModelProperty("户主身份证")
	String getIdCard();

	@ApiModelProperty("行政区域--全称")
	String getMergerName();

	@ApiModelProperty("征地范围")
	String getScope();

	@ApiModelProperty("户口类型")
	String getHouseholdType();

	@ApiModelProperty("安置点名称")
	String getPlaceName();

	@ApiModelProperty("安置方式")
	String getPlaceType();
	
	@ApiModelProperty("安置位置（分散安置）")
	String getPlaceAddress();

	@ApiModelProperty("界定条件")
	String getDefine();

	@ApiModelProperty("安置去向")
	String getToWhere();

	@ApiModelProperty("县")
	String getCounty();

	@ApiModelProperty("乡镇")
	String getTown();

	@ApiModelProperty("村")
	String getVillage();

	@ApiModelProperty("是否符合")
	String getIsSatisfy();

	@ApiModelProperty("移民人口数")
	Integer getIPopulation();

	@ApiModelProperty("安置数据")
	String getMoveJsonData();

	@ApiModelProperty("安置状态")
	String getMoveState();

	@ApiModelProperty("流程ID")
	String getProcessId();

	@ApiModelProperty("流程名称")
	String getProcessName();

	@ApiModelProperty("流程发起时间")
	Date getProcessApplyTime();

	@ApiModelProperty("流程状态")
	String getProcessStatus();

	@ApiModelProperty("流程中文状态")
	String getProcessCnStatus();

	@ApiModelProperty("备注")
	String getRemark();

	@ApiModelProperty("签字表附件--编码")
	String getSignFileNm();

	@ApiModelProperty("签字表附件--名称")
	String getSignFileName();

	@ApiModelProperty("签字表附件--类型")
	String getSignFileType();

	@ApiModelProperty("签字表附件--url")
	String getSignFileUrl();

}
