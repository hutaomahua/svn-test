package com.lyht.business.process.dto.response;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * 同步接口响应对象
 * 
 * @author hxl
 *
 */
public class ProcessInfoResponse {

	@ApiModelProperty(value = "响应识别码(0：成功，其他：失败)")
	@JsonProperty(value = "ErrorCode")
	private Integer errorCode;

	@ApiModelProperty(value = "响应信息")
	@JsonProperty(value = "Message")
	private String message;

	@ApiModelProperty(value = "响应数据")
	@JsonProperty(value = "Data")
	private Data data;

	@ApiModelProperty(value = "响应状态(true:成功，false：失败)")
	@JsonProperty(value = "Success")
	private Boolean success;

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public class Data {
		@ApiModelProperty("流程名称")
		@JsonProperty(value = "Name")
		private String name;

		@ApiModelProperty("申请人")
		@JsonProperty("Applicant")
		private String applicant;
		
		@ApiModelProperty("代理申请人")
		@JsonProperty("Agent")
		private String agent;

		@ApiModelProperty("申请时间")
		@JsonProperty("ApplyAt")
		private Date applyAt;

		@ApiModelProperty("完成时间")
		@JsonProperty("CompleteAt")
		private Date completeAt;

		@ApiModelProperty("表单地址")
		@JsonProperty("ReadUrl")
		private String readUrl;

		@ApiModelProperty("说明")
		@JsonProperty("Description")
		private String description;

		@ApiModelProperty("流程状态")
		@JsonProperty("State")
		private String state;

		@ApiModelProperty("中文状态")
		@JsonProperty("ChsState")
		private String chsState;

		@ApiModelProperty("流程数据")
		@JsonProperty("TaskData")
		private String taskData;
		
		@ApiModelProperty("流程对应的类路径")
		@JsonProperty("InstanceClass")
		private String instanceClass;
		
		@ApiModelProperty("来源于外部流程")
		@JsonProperty("FromOutside")
		private Boolean fromOutside;
		
		@ApiModelProperty("流程归属的系统")
		@JsonProperty("OwnerSystem")
		private String ownerSystem;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getApplicant() {
			return applicant;
		}

		public void setApplicant(String applicant) {
			this.applicant = applicant;
		}

		public String getAgent() {
			return agent;
		}

		public void setAgent(String agent) {
			this.agent = agent;
		}

		public Date getApplyAt() {
			return applyAt;
		}

		public void setApplyAt(Date applyAt) {
			this.applyAt = applyAt;
		}

		public Date getCompleteAt() {
			return completeAt;
		}

		public void setCompleteAt(Date completeAt) {
			this.completeAt = completeAt;
		}

		public String getReadUrl() {
			return readUrl;
		}

		public void setReadUrl(String readUrl) {
			this.readUrl = readUrl;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getChsState() {
			return chsState;
		}

		public void setChsState(String chsState) {
			this.chsState = chsState;
		}

		public String getTaskData() {
			return taskData;
		}

		public void setTaskData(String taskData) {
			this.taskData = taskData;
		}

		public String getInstanceClass() {
			return instanceClass;
		}

		public void setInstanceClass(String instanceClass) {
			this.instanceClass = instanceClass;
		}

		public Boolean getFromOutside() {
			return fromOutside;
		}

		public void setFromOutside(Boolean fromOutside) {
			this.fromOutside = fromOutside;
		}

		public String getOwnerSystem() {
			return ownerSystem;
		}

		public void setOwnerSystem(String ownerSystem) {
			this.ownerSystem = ownerSystem;
		}

		@Override
		public String toString() {
			return JSON.toJSONString(this);
		}
	}
}
