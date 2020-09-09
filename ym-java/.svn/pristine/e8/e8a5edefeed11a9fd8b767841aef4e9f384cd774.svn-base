package com.lyht.business.cost.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("移民户信息")
public interface CostOwnerVO {

	@ApiModelProperty("权属人编码")
	String getOwnerNm();

	@ApiModelProperty("权属人姓名")
	String getOwnerName();

	@ApiModelProperty("搬迁安置是否核定（2：已核定）")
	String getIsSatisfy();

	@ApiModelProperty("搬迁地点(县)")
	String getCounty();
	
	@ApiModelProperty("搬迁地点(乡镇)")
	String getTowns();
	
	@ApiModelProperty("搬迁地点(村)")
	String getVillage();

	@ApiModelProperty("搬迁安置人口数")
	Integer getMoveNum();
	
	@ApiModelProperty("搬迁安置类型Code（move_type_once：分散货币；move_type_thrice：分散后靠；move_type_town：集镇集中安置；move_type_village：农村集中安置；）")
	String getPlaceTypeCode();
	
	@ApiModelProperty("搬迁安置类型（农村集中安置、集镇集中安置、分散后靠安置、分散货币安置）")
	String getPlaceType();
	
	@ApiModelProperty("生产安置类型（兰永、非兰永）")
	String getProduceType();
	
	@ApiModelProperty("生产安置数量")
	Integer getProduceNum();
	
	@ApiModelProperty("安置点名称")
	String getPlaceName();
	
	@ApiModelProperty("指标类型（农村、集镇）")
	String getZblx();
	
	@ApiModelProperty("性质（移民私有、集体公有）")
	String getNature();
	
	/*---------------新增的------------------*/

	@ApiModelProperty("原住址（行政区域）")
	String getRegionName();
	
	@ApiModelProperty("身份证号")
	String getIdCard();
	
	@ApiModelProperty("征地范围")
	String getScopeName();
	
	@ApiModelProperty("人民政府位置（县、镇地址）")
	String getGovernmentRegion();
	
}
