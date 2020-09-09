package com.lyht.business.abm.removal.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PersonaWealthDecorationObjectVO {
	
	@ApiModelProperty(value = "户主姓名")
	private String ownerName;

	@ApiModelProperty(value = "户主nm")
	private String ownerNm;

	@ApiModelProperty(value = "id")
	private Integer id;

	@ApiModelProperty(value = "nm")
	private String nm;

	@ApiModelProperty(value = "项目Nm")
	private String projectNm;

	@ApiModelProperty(value = "项目名称")
	private String projectName;

	@ApiModelProperty(value = "用途")
	private String purpose;

	@ApiModelProperty(value = "面积")
	private Double area;
	
	@ApiModelProperty(value = "征地范围名称")
	private String scopeName;

	@ApiModelProperty(value = "征地范围")
	private String scope;

	@ApiModelProperty(value = "层数")
	private Integer layerNumber;
	
	@ApiModelProperty(value = "房屋性质 ")
	private String houseNature;
	
	@ApiModelProperty(value = "结构类型")
	private String structureType;
	
	@ApiModelProperty(value="尺寸1")
	private String measurement1;
	
	@ApiModelProperty(value="尺寸2")
	private String measurement2;
	
	@ApiModelProperty(value = "装修等级")
	private String decorateGrade;

	@ApiModelProperty(value = "行政区全称")
	private String mergerName;

	@ApiModelProperty(value = "行政区编码")
	private String region;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "文件数量")
	private Integer fileCount;
	
}
