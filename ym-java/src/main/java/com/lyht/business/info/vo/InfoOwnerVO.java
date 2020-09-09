package com.lyht.business.info.vo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "权属人")
public interface InfoOwnerVO{
	
	@ApiModelProperty(value = "唯一ID(新增不填，修改必填)")
	Integer getId();

	@ApiModelProperty(value = "唯一内码（新增不填，修改必填）")
	String getNm();

	@ApiModelProperty("项目")
	String getProjectNm();
	
	@ApiModelProperty("行政区")
	String getRegion();

	@ApiModelProperty("征地范围")
	String getScope();

	@ApiModelProperty("姓名")
	String getName();

	@ApiModelProperty("性别")
	String getGender();

	@ApiModelProperty("身份证号")
	String getIdCard();

	@ApiModelProperty("民族")
	String getNational();

	@ApiModelProperty("年龄")
	Integer getAge();

	@ApiModelProperty("移民户")
	Integer getImmigrant();

	@ApiModelProperty("农业人口")
	Integer getAp();

	@ApiModelProperty("非农业人口")
	Integer getNonAp();

	@ApiModelProperty("移民人口")
	Integer getiPopulation();

	@ApiModelProperty("文化程度")
	String getEducationLevel();

	@ApiModelProperty("现从事职业")
	String getPresentOccupation();

	@ApiModelProperty("现就读学校")
	String getCurrentSchool();

	@ApiModelProperty("户口所在地")
	String getHouseholdsHome();

	@ApiModelProperty("户口类型")
	String getHouseholdsType();

	@ApiModelProperty("经度")
	String getLgtd();

	@ApiModelProperty("纬度")
	String getLttd();

	@ApiModelProperty("高程")
	String getAltd();

	@ApiModelProperty("图幅号")
	String getInMap();

	@ApiModelProperty("阶段")
	String getStage();
	
	@ApiModelProperty("指标类型")
	String getZblx();

	@ApiModelProperty("权属性质")
	String getNature();

	@ApiModelProperty("权属人类型")
	String getOwnerType();

	@ApiModelProperty("档案编号")
	String getDosNumber();

	@ApiModelProperty("流程id")
	String getProcessId();

	@ApiModelProperty("复核状态")
	String getFhState();

	@ApiModelProperty("村")
	String getCun();

	@ApiModelProperty("乡")
	String getXiang();

	@ApiModelProperty("组")
	String getZu();

	@ApiModelProperty("安置类型")
	String getPlaceType();

	@ApiModelProperty("安置点名称")
	String getPlaceName();
	
	@ApiModelProperty("安置位置（分散安置）")
	String getPlaceAddress();
	
	@ApiModelProperty("安置界定条件")
	String getDefine();

	@ApiModelProperty("是否搬迁安置（2：已搬迁安置）")
	String getIsSatisfy();

	@ApiModelProperty("复核人")
	String getFhNm();

	@ApiModelProperty("公示状态")
	String getGsState();
	
	@ApiModelProperty("数据状态")
	String getStatus();
	
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
	
	@ApiModelProperty("民族--值")
	String getNationalValue();
	
	@ApiModelProperty("文化程度--值")
	String getEducationLevelValue();
	
	@ApiModelProperty("户口类型--值")
	String getHouseholdsTypeValue();
	
	@ApiModelProperty("权属性质--值")
	String getNatureValue();
	
	@ApiModelProperty("权属人类型--值")
	String getOwnerTypeValue();
	
	@ApiModelProperty("安置类型--值")
	String getPlaceTypeValue();
	
	@ApiModelProperty("安置类型--值")
	String getToWhere();
	
}