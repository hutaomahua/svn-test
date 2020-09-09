package com.lyht.business.abm.removal.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("搬迁安置界定")
public class AbmMoveDefinitionVO {

	@ApiModelProperty("户主NM")
	private String ownerNm;

	@ApiModelProperty("安置类型(字典NM)")
	private String placeType;

	@ApiModelProperty("安置名称")
	private String placeName;
	
	@ApiModelProperty("安置位置（分散安置）")
	private String placeAddress;

	@ApiModelProperty("县")
	private String county;

	@ApiModelProperty("乡镇")
	private String town;

	@ApiModelProperty("村")
	private String village;

	@ApiModelProperty("去向")
	private String toWhere;

	@ApiModelProperty("家庭成员界定信息")
	private List<AbmMoveFamilyDefinitionVO> familyList;

}
