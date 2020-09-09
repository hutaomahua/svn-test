package com.lyht.business.abm.household.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("指标划分--参数")
@Data
public class AbmSplitDivisionParamVO {

	@ApiModelProperty("分户--户主nm")
	private String splitOwnerNm;

	@ApiModelProperty("指标的原户主nm")
	private String preOwnerNm;

	@ApiModelProperty("指标划分的户主nm")
	private String afterOwnerNm;

	@ApiModelProperty("指标类型（家庭成员：family；土地：land；房屋：house；零星树木：trees；附属建筑物：building；房屋装修：decoration；农副业设施：facilities；宅基地：homestead）")
	private String type;

	@ApiModelProperty("选中的指标nm数组（只允许操作一种指标）")
	private List<String> dataNm;

}