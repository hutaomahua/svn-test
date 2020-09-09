package com.lyht.business.process.vo;

import java.util.Map;

import io.swagger.annotations.ApiModelProperty;

public class ProcessVO {
	@ApiModelProperty(value = "流程UUID", required = true)
	private String taskId;
	@ApiModelProperty(value = "流程步骤UUID", required = true)
	private String stepId;
	@ApiModelProperty(value = "退回到的步骤UUID，只有退回操作才需设置此值")
	private String backStepId;

	@ApiModelProperty(value = "审批用户UUID")
	private String user;
	@ApiModelProperty(value = "审批意见")
	private String comment;
	@ApiModelProperty(value = "审批附件地址")
	private String attachment;
	@ApiModelProperty(value = "审批动作（Approved:同意，Rejected: 拒绝, PickBack:退回）", required = true)
	private String result;
	@ApiModelProperty(value = "附加数据")
	private Map<String, String> data;
	@ApiModelProperty(value = "海选uuid")
	private String selectId;
	

}
