package com.lyht.business.abm.review.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 查询移民档案信息
 * 
 * @author wzw
 *
 */
public interface OwnerDocGetInfoVO {

	@ApiModelProperty(value = "户主姓名")
	String getOwnerName();

	@ApiModelProperty(value = "户主nm")
	String getOwnerNm();

	@ApiModelProperty(value = "行政区")
	String getMergerName();

	@ApiModelProperty(value = "行政区编码")
	String getCityCode();

	@ApiModelProperty(value = "搬迁安置人口数")
	Integer getFamilyCount();

	@ApiModelProperty(value = "家庭成员总数")
	Integer getPopulation();

	@ApiModelProperty(value = "房屋总面积")
	Double getHouseArea();

	@ApiModelProperty(value = "房屋装修总面积")
	Double getHouseDecorationArea();

	@ApiModelProperty(value = "土地总面积")
	Double getLandArea();

	@ApiModelProperty(value = "复审项目")
	String getReviewProject();

	@ApiModelProperty(value = "身份证号")
	String getIdCard();

	@ApiModelProperty(value = "征地范围")
	String getScopeName();

	@ApiModelProperty(value = "民族")
	String getNationalName();

	@ApiModelProperty(value = "性别")
	String getGender();

	@ApiModelProperty(value = "复核申请附件名称")
	String getReviewFileName();

	@ApiModelProperty(value = "复核申请附件地址URL")
	String getReviewFileUrl();

	@ApiModelProperty(value = "变更申请附件名称")
	String getChangeFileName();

	@ApiModelProperty(value = "变更申请附件地址URL")
	String getChangeFileUrl();

	@ApiModelProperty(value = "征地范围nm")
	String getScope();

	@ApiModelProperty(value = "民族nm")
	String getNational();

	@ApiModelProperty(value = "户口所在地")
	String getHouseholdsHome();

	@ApiModelProperty(value = "户口类型")
	String getHouseholdsType();

	@ApiModelProperty(value = "经度")
	Double getLgtd();

	@ApiModelProperty(value = "纬度")
	Double getLttd();

	@ApiModelProperty(value = "高程")
	Double getAltd();

	@ApiModelProperty(value = "图幅号")
	String getInMap();
}
