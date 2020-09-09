package com.lyht.business.info.vo;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "房屋装修")
public interface InfoHousesDecorationVO {

	@ApiModelProperty(value = "唯一ID(新增不填，修改必填)")
	Integer getId();

	@ApiModelProperty(value = "唯一内码（新增不填，修改必填）")
	String getNm();	
	
	@ApiModelProperty("项目编码")
	String getProjectNm();

	@ApiModelProperty("行政区")
	String getRegion();

	@ApiModelProperty("指标类型")
	String getZblx();

	@ApiModelProperty("征地范围")
	String getScope();

	@ApiModelProperty("权属人编码")
	String getOwnerNm();

	@ApiModelProperty("房屋性质")
	String getHouseNature();

	@ApiModelProperty("用途")
	String getPurpose();

	@ApiModelProperty("结构类型")
	String getStructureType();

	@ApiModelProperty("层数")
	Integer getLayerNumber();

	@ApiModelProperty("尺寸1")
	String getMeasurement1();

	@ApiModelProperty("尺寸2")
	String getMeasurement2();

	@ApiModelProperty("面积(㎡)")
	BigDecimal getArea();

	@ApiModelProperty("装修等级")
	String getDecorateGrade();

	@ApiModelProperty("权属性质")
	String getOwnerNature();

	@ApiModelProperty("数据状态")
	String getStatus();

	@ApiModelProperty("阶段")
	String getStage();

	@ApiModelProperty(value = "创建时间")
	Date getCreateTime();
	@ApiModelProperty(value = "创建用户")
	String getCreateUser();
	@ApiModelProperty(value = "修改时间")
	Date getModifyTime();
	@ApiModelProperty(value = "修改用户")
	String getModifyUser();

	@ApiModelProperty("备注")
	String Remark();
	
	
	@ApiModelProperty("项目--值")
	String getProjectValue();

	@ApiModelProperty("行政区--值")
	String getRegionValue();
	
	@ApiModelProperty("阶段--值")
	String getStageValue();
	
	@ApiModelProperty("指标类型--值")
	String getZblxValue();
	
	@ApiModelProperty("征地范围--值")
	String getScopeValue();

	@ApiModelProperty("权属人姓名")
	String getOwnerValue();
	
	@ApiModelProperty("房屋性质--值")
	String getHouseNatureValue();
	
	@ApiModelProperty("权属性质--值")
	String getOwnerNatureValue();
	
	@ApiModelProperty("装修等级--值")
	String getDecorateGradeValue();
	
	@ApiModelProperty("用途--值")
	String getPurposeValue();

}