package com.lyht.business.abm.household.vo;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("数据拆分--参数")
@Data
public class AbmSplitNumberParamVO {

	@ApiModelProperty("分户--户主nm")
	private String splitOwnerNm;

	@ApiModelProperty("操作对应的户主nm")
	private String ownerNm;

	@ApiModelProperty("指标类型（土地：land；房屋：house；零星树木：trees；附属建筑物：building；房屋装修：decoration）")
	private String type;

	@ApiModelProperty("拆分的指标数据nm")
	private String dataNm;

	@ApiModelProperty("长（数组）")
	private List<BigDecimal> longsList;

	@ApiModelProperty("面积（数组）")
	private List<BigDecimal> areaList;

	@ApiModelProperty("体积（数组）")
	private List<BigDecimal> volumeList;

	@ApiModelProperty("数量（数组，必须是整数）")
	private List<BigDecimal> numList;

}