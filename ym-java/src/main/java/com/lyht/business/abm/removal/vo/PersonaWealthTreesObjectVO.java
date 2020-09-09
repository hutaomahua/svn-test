package com.lyht.business.abm.removal.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PersonaWealthTreesObjectVO {

	@ApiModelProperty(value = "户主姓名")
	private String ownerName;
	
	@ApiModelProperty(value = "户主nm")
	private String ownerNm;
	
	@ApiModelProperty(value = "id")
	private Integer id;
	
	@ApiModelProperty(value = "nm")
	private String nm;
	
	@ApiModelProperty(value = "类别(品种/项目)Id")
	private String projectNm;
	
	@ApiModelProperty(value = "类别(品种/项目)")
	private String projectName;
	
	@ApiModelProperty(value = "品种")
	private String varieties;
	
	@ApiModelProperty(value = "规格")
	private String specifications;
	
	@ApiModelProperty(value = "征地范围名称")
	private String scopeName;
	
	@ApiModelProperty(value = "征地范围")
	private String scope;
	
	@ApiModelProperty(value = "单位")
	private String unit;
	
	@ApiModelProperty(value ="树龄")
	private Integer treeAge;
	
	@ApiModelProperty(value ="树高")
	private Double treeHeight;
	
	@ApiModelProperty(value = "胸径")
	private Double breastHeight;
	
	@ApiModelProperty(value = "数量")
	private Double num;
	
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
