package com.lyht.business.cost.vo;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 补偿费用协议信息
 * 
 * @author hxl
 *
 */
@Data
@ApiModel("补偿费用协议信息")
public class CostSignVO {

	@ApiModelProperty("权属人编码")
	private String ownerNm;

	@ApiModelProperty("权属人姓名")
	private String ownerName;

	@ApiModelProperty("搬迁安置是否核定（2：已核定）")
	private String isSatisfy;

	@ApiModelProperty("搬迁地点(县)")
	private String county;

	@ApiModelProperty("搬迁地点(乡镇)")
	private String towns;

	@ApiModelProperty("搬迁地点(村)")
	private String village;

	@ApiModelProperty("搬迁安置人口数")
	private Integer moveNum;

	@ApiModelProperty("搬迁安置类型（农村集中安置、集镇集中安置、分散后靠安置、分散货币安置）")
	private String placeType;

	@ApiModelProperty("安置点名称")
	private String placeName;
	
	@ApiModelProperty("生产安置类型（兰永、非兰永）")
	private String produceType;

	@ApiModelProperty("生产安置数量")
	private Integer produceNum;

	@ApiModelProperty("指标类型（农村、集镇）")
	private String zblx;

	@ApiModelProperty("性质（移民私有、集体公有）")
	private String nature;

	/*---------------新增的------------------*/

	@ApiModelProperty("原住址（行政区域）")
	private String regionName;

	@ApiModelProperty("身份证号")
	private String idCard;

	@ApiModelProperty("征地范围")
	private String scopeName;

	@ApiModelProperty("人民政府位置（县、镇地址）")
	private String governmentRegion;

	/*---------------协议信息------------------*/

	@ApiModelProperty("协议编号")
	private String signNumber;

	@ApiModelProperty("协议签订日期(格式：xxxx-xx-xx)")
	private String signDate;

	@ApiModelProperty("协议签订日期(中文日期  如：二〇二〇年一月一日)")
	private String signChineseDate;

	@ApiModelProperty("搬迁新住址")
	private String newRegionName;

	/*---------------每一项费用的金额------------------*/
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
