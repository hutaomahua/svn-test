package com.lyht.business.process.vo;

import java.util.Map;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* @author HuangTianhao
* @date 2019年12月15日 
*/

@ApiModel(description = "流程启动VO")
public class ProcessStartVO {
	@ApiModelProperty(value = "审批用户UUID")
	private String user;
	@ApiModelProperty(value = "审批意见")
	private String comment;
	@ApiModelProperty(value = "审批附件地址")
	private String attachment;
	@ApiModelProperty(value = "流程地址")
	private String flowPath;
	@ApiModelProperty(value = "审批动作（Approved:同意，Rejected: 拒绝, PickBack:退回）", required = true)
	private String result;
	@ApiModelProperty(value = "附加数据")
	private Map<String, String> data;

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

	public String getFlowPath() {
		return flowPath;
	}

	public void setFlowPath(String flowPath) {
		this.flowPath = flowPath;
	}
}