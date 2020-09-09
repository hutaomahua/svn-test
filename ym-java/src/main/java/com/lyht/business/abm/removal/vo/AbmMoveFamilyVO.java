package com.lyht.business.abm.removal.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("搬迁安置流程详情")
public interface AbmMoveFamilyVO {
	
	@ApiModelProperty("家庭成员编码")
	String getNm();
	
	@ApiModelProperty("姓名")
	String getName();
	
	@ApiModelProperty("身份证号")
	String getIdCard();
	
	@ApiModelProperty("民族")
	String getNational();
	
	@ApiModelProperty("性别")
	String getGender();
	
	@ApiModelProperty("年龄")
	Integer getAge();
	
	@ApiModelProperty("户主编码")
	String getOwnerNm();
	
	@ApiModelProperty("户口类型")
	String getHouseholdsType();
	
	@ApiModelProperty("与户主关系--编码")
	String getMasterRelationship();
	
	@ApiModelProperty("与户主关系--值")
	String getMasterRelationshipValue();

	@ApiModelProperty("是否符合(0或其他：默认符合；1：不符合；2：符合)")
	String getIsSatisfy();
	
	@ApiModelProperty("界定条件(符合（查询符合相关字典），不符合（查询不符合相关字典）)")
	String getDefine();
	
	@ApiModelProperty("阶段--编码")
	String getStage();
	
	@ApiModelProperty("阶段--值")
	String getStageValue();
	
	@ApiModelProperty("安置类型--编码")
	String getPlaceType();
	
	@ApiModelProperty("安置类型--值")
	String getPlaceTypeValue();
	
	@ApiModelProperty("安置名称")
	String getPlaceName();
	
	@ApiModelProperty("安置位置（分散安置）")
	String getPlaceAddress();
	
	@ApiModelProperty("县")
	String getCounty();
	
	@ApiModelProperty("乡镇")
	String getTown();
	
	@ApiModelProperty("村")
	String getVillage();
	
	@ApiModelProperty("去向")
	String getToWhere();
	
	@ApiModelProperty("备注")
	String getRemark();

}
