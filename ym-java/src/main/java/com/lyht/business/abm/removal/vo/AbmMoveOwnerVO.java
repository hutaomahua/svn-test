package com.lyht.business.abm.removal.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("搬迁安置户主信息")
public interface AbmMoveOwnerVO {

	@ApiModelProperty("户主编码")
	String getNm();

	@ApiModelProperty("姓名")
	String getName();

	@ApiModelProperty("身份证号")
	String getIdCard();

	@ApiModelProperty("是否符合(0或其他：默认符合；1：不符合；2：符合)")
	String getIsSatisfy();

	@ApiModelProperty("安置类型--编码")
	String getPlaceType();

	@ApiModelProperty("安置类型--值")
	String getPlaceTypeValue();

	@ApiModelProperty("安置名称")
	String getPlaceName();
	
	@ApiModelProperty("安置位置（分散安置）")
	String getPlaceAddress();

	@ApiModelProperty("安置去向")
	String getToWhere();

	@ApiModelProperty("界定条件")
	String getDefine();

	@ApiModelProperty("户口类型--编码")
	String getHouseholdsType();

	@ApiModelProperty("户口类型--值")
	String getHouseholdsTypeValue();

	@ApiModelProperty("移民人口")
	BigDecimal getIPopulation();

	@ApiModelProperty("民族--编码")
	String getNational();

	@ApiModelProperty("民族--值")
	String getNationalValue();

	@ApiModelProperty("行政区编码")
	String getCityCode();

	@ApiModelProperty("行政区全称")
	String getMergerName();

	@ApiModelProperty("征地范围--编码")
	String getScope();

	@ApiModelProperty("征地范围--值")
	String getScopeValue();

	@ApiModelProperty("安置状态（0：正常，可去向界定；1：已修改，可导出界定表和发起流程；2：界定确认流程处理中，不可做任何操作；）")
	String getMoveState();
	
	@ApiModelProperty(value = "协议签订状态 0：未签订任何协议，1：已签订一项或多项")
	String getProtocolState();

}
