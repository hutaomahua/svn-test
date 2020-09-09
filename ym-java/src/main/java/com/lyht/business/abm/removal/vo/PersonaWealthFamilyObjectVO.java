package com.lyht.business.abm.removal.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class PersonaWealthFamilyObjectVO {
	@ApiModelProperty(value = "户主姓名")
	private String ownerName;

	@ApiModelProperty(value = "户主nm")
	private String ownerNm;

	@ApiModelProperty(value = "id")
	private Integer id;

	@ApiModelProperty(value = "nm")
	private String nm;

	@ApiModelProperty(value = "姓名")
	private String name;

	@ApiModelProperty(value = "身份证号")
	private String idCard;

	@ApiModelProperty(value = "与户主关系nm")
	private String masterRelationship;

	@ApiModelProperty(value = "与户主关系Name")
	private String relationshipName;
	
	@ApiModelProperty(value = "征地范围名称nm")
	private String scope;

	@ApiModelProperty(value = "建设征地范围")
	private String scopeName;

	@ApiModelProperty(value = "性别 明文")
	private String gender;

	@ApiModelProperty(value = "民族nm")
	private String national;

	@ApiModelProperty(value = "民族Name")
	private String nationalName;

	@ApiModelProperty(value = "年龄")
	private String age;

	@ApiModelProperty(value = "文化程度")
	private String educationLevel;

	@ApiModelProperty(value = "现从事职业")
	private String presentOccupation;

	@ApiModelProperty(value = "现就读学校")
	private String currentSchool;

	@ApiModelProperty(value = "户口所在地")
	private String householdsHome;

	@ApiModelProperty(value = "户口类型")
	private String householdsType;
	
	@ApiModelProperty(value = "户口类型明码")
	private String householdsTypeName;

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
