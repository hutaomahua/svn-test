package com.lyht.business.change.vo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author HuangAn
 * @date 2019/12/5 14:53
 */
@ApiModel(value = "变更申请信息")
public interface ChangeApplicationVO {
	@ApiModelProperty(value = "唯一id")
	Integer getId();

	@ApiModelProperty(value = "唯一nm")
	String getNm();

	@ApiModelProperty(value = "行政区nm")
	String getRegion();

	@ApiModelProperty(value = "设计变更内容")
	String getContentItems();

	@ApiModelProperty(value = "设计变更项目")
	String getChangeProject();

	@ApiModelProperty(value = "概况")
	String getGeneralSituation();

	@ApiModelProperty(value = "设计变更必要性")
	String getChangeNeed();

	@ApiModelProperty(value = "设计变更主要情况")
	String getMainCase();

	@ApiModelProperty(value = "水利水电工程名称")
	String getProjectName();

	@ApiModelProperty(value = "申请人nm")
	String getApplicant();

	@ApiModelProperty(value = "申请时间")
	Date getApplyTime();

	@ApiModelProperty(value = "技术经济分析")
	String getEconomicAnalysis();

	@ApiModelProperty(value = "所属变更类型")
	Integer getChangeType();

	@ApiModelProperty(value = "所属变更申请状态")
	Integer getChangeStatus();

	@ApiModelProperty(value = "设计变更内容关联")
	String getDesignContentNm();

	@ApiModelProperty(value = "所属变更申请类型")
	Integer getChangeRequestType();

	@ApiModelProperty(value = "申请人名称")
	String getStaffName();

	@ApiModelProperty(value = "行政区名称（去掉前三级）")
	String getDiming();

	@ApiModelProperty(value = "行政区名称（全称）")
	String getMergerName();

	@ApiModelProperty(value = "流程ID")
	String getProcessId();

	@ApiModelProperty(value = "流程状态")
	String getProcessStatus();

	@ApiModelProperty(value = "流程状态（中文）")
	String getProcessCnStatus();
}
