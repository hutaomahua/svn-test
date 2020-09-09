package com.lyht.business.process.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

//流程发起审批响应对象
public class ProcessProceedResponse {
	@ApiModelProperty(value = "数据")
	@JsonProperty(value = "Data")
	private Data data;
	@ApiModelProperty(value = "错误代码")
	@JsonProperty("ErrorCode")
	private Integer errorcode;
	@ApiModelProperty(value = "信息")
	@JsonProperty("Message")
	private String message;
	@ApiModelProperty(value = "成功")
	@JsonProperty("Success")
	private Boolean success;
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public Integer getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(Integer errorcode) {
		this.errorcode = errorcode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public class Data{
		@ApiModelProperty(value = "工作流是否已完成")
		@JsonProperty(value = "WorkflowFinish")
		private Boolean workflowFinish;
		@ApiModelProperty(value = "工作流是否被拒绝")
		@JsonProperty("WorkflowRejected")
		private Boolean workflowRejected;
		@ApiModelProperty(value = "工作流是否异常")
		@JsonProperty("WorkflowError")
		private Boolean workflowError;
		@ApiModelProperty(value = "工作流Id")
		@JsonProperty("TaskId")
		private String taskId;
		
		public Boolean getWorkflowFinish() {
			return workflowFinish;
		}
		public void setWorkflowFinish(Boolean workflowFinish) {
			this.workflowFinish = workflowFinish;
		}
		public Boolean getWorkflowRejected() {
			return workflowRejected;
		}
		public void setWorkflowRejected(Boolean workflowRejected) {
			this.workflowRejected = workflowRejected;
		}
		public Boolean getWorkflowError() {
			return workflowError;
		}
		public void setWorkflowError(Boolean workflowError) {
			this.workflowError = workflowError;
		}
		public String getTaskId() {
			return taskId;
		}
		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}
	}

}
//{"Data":
//{"WorkflowFinish":true,
//	"WorkflowRejected":false,
//	"WorkflowError":false,
//	"TaskId":"107ea4dc-9960-4007-9a4f-2c07bf8c5062"},
//"ErrorCode":0,
//"Message":null,
//"Success":true}