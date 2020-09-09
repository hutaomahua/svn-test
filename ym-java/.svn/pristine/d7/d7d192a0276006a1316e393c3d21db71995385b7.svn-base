package com.lyht.business.process.dto.request;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * 流程操作
 * @author hxl
 *
 */
public class ProcessOperateRequest {

	// 审批用户UUID
	private String user;
	// 审批意见
	private String comment;
	// 审批附件地址
	private String attachment;
	// 审批动作（Approved:同意，Rejected: 拒绝, PickBack:退回）
	private String result;
	// 用户密码（key-value键值对JSON数据）
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
}
