package com.lyht.business.info.vo;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "宅基地")
public interface InfoAgriculturalFacilitiesVO {

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

	@ApiModelProperty("规格")
	String getSpecifications();

	@ApiModelProperty("单位")
	String getUnit();

	@ApiModelProperty("长")
	BigDecimal getLongs();

	@ApiModelProperty("宽")
	BigDecimal getWidth();

	@ApiModelProperty("高")
	BigDecimal getHeight();

	@ApiModelProperty("面积")
	BigDecimal getArea();

	@ApiModelProperty("体积")
	BigDecimal getVolume();

	@ApiModelProperty("数量")
	BigDecimal getNum();

	@ApiModelProperty("经度")
	String getLgtd();

	@ApiModelProperty("纬度")
	String getLttd();

	@ApiModelProperty("高程")
	String getAltd();

	@ApiModelProperty("图幅号")
	String getInMap();

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
	String getRemark();
	
	

	@ApiModelProperty("项目--值")
	String getProjectValue();

	@ApiModelProperty("行政区--值")
	String getRegionValue();

	@ApiModelProperty("指标类型--值")
	String getZblxValue();

	@ApiModelProperty("阶段--值")
	String getStageValue();

	@ApiModelProperty("征地范围--值")
	String getScopeValue();

	@ApiModelProperty("户主姓名--值")
	String getOwnerValue();
	
	@ApiModelProperty("权属性质--值")
	String getOwnerNatureValue();
	
	@ApiModelProperty("规格--值")
	String getSpecificationsValue();
	
	@ApiModelProperty("单位--值")
	String getUnitValue();

}