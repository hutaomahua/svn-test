package com.lyht.business.abm.removal.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PersonaWealthBuildingObjectVO {
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
	
	@ApiModelProperty(value = "征地范围名称")
	private String scopeName;
	
	@ApiModelProperty(value = "征地范围")
	private String scope;
	
	@ApiModelProperty(value = "单位")
	private String unit;
	
	@ApiModelProperty(value = "数量(长/体积/面积/其他单位)")
	private Double num;
	
	@ApiModelProperty(value = "规格")
	private String specifications;
	
	@ApiModelProperty(value = "经度")
	private String lgtd;

	@ApiModelProperty(value = "纬度")
	private String lttd;

	@ApiModelProperty(value = "图幅号")
	private String inMap;
	
	@ApiModelProperty(value = "高程")
	private String altd;
	
	@ApiModelProperty(value = "行政区全称")
	private String mergerName;
	
	@ApiModelProperty(value = "行政区编码")
	private String region;
	
	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "文件数量")
	private Integer fileCount;
}
