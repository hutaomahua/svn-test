package com.lyht.business.abm.removal.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("搬迁安置流程详情")
@Data
public class AbmMoveFamilyInfoVO {
	
	@ApiModelProperty("家庭成员编码")
	private String nm;
	
	@ApiModelProperty("姓名")
	private String name;
	
	@ApiModelProperty("身份证号")
	private String idCard;
	
	@ApiModelProperty("与户主关系--值")
	private String masterRelationshipValue;

	@ApiModelProperty("是否符合(0或其他：默认符合；1：不符合；2：符合)")
	private String isSatisfy;
	
	@ApiModelProperty("界定条件(符合（查询符合相关字典），不符合（查询不符合相关字典）)")
	private String define;

}
