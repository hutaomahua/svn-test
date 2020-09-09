package com.lyht.business.process.vo;

import java.util.Map;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author hxl
 *
 */
@ApiModel(description = "流程操作VO")
public class ProcessOperateVO {
	@ApiModelProperty(value = "审批用户UUID（登录用户自动获取，swagger测试必填）")
	private String user;
	@ApiModelProperty(value = "流程地址（发起流程必填）")
	private String flowPath;
	@ApiModelProperty(value = "审批动作（Approved:同意，Rejected: 拒绝, PickBack:退回，发起流程填Approved）", required = true)
	private String result;
	@ApiModelProperty(value = "附加数据")
	private Map<String, String> data;
	@ApiModelProperty(value = "流程UUID（发起流程不填，流程操作必填）")
	private String taskId;
	@ApiModelProperty(value = "流程步骤UUID（发起流程不填，流程操作必填）")
	private String stepId;
	@ApiModelProperty(value = "退回到的步骤UUID，（发起流程不填，只有退回操作才需设置此值")
	private String backStepId;
	@ApiModelProperty(value = "审批意见（发起流程不填）")
	private String comment;
	@ApiModelProperty(value = "审批附件地址（发起流程不填）")
	private String attachment;
	@ApiModelProperty(value = "海选uuid（配置了海选的步骤必填）")
	private String selectId;
	public String getFlowPath() {
		return flowPath;
	}

	public void setFlowPath(String flowPath) {
		this.flowPath = flowPath;
	}
	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getBackStepId() {
		return backStepId;
	}

	public void setBackStepId(String backStepId) {
		this.backStepId = backStepId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}