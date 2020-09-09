package com.lyht.business.process.entity;

import javax.persistence.*;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("流程接口调用日志")
@Entity
@Table(name = "t_bpm_process_log")
public class ProcessLogEntity {

	@ApiModelProperty("id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ApiModelProperty("接口地址")
	@Column(name = "api_url")
	private String apiUrl;

	@ApiModelProperty("接口请求方式")
	@Column(name = "api_method")
	private String apiMethod;

	@ApiModelProperty("接口请求url参数")
	@Column(name = "api_url_param")
	private String apiUrlParam;
	
	@ApiModelProperty("接口请求body参数")
	@Column(name = "api_body_param")
	private String apiBodyParam;

	@ApiModelProperty("接口请求类型")
	@Column(name = "api_content_type")
	private String apiContentType;

	@ApiModelProperty("接口响应数据json")
	@Column(name = "api_response")
	private String apiResponse;

	@ApiModelProperty("接口响应状态")
	@Column(name = "api_response_status")
	private Integer apiResponseStatus;

	@ApiModelProperty("是否调用成功")
	@Column(name = "is_request")
	private Boolean isRequest;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getApiMethod() {
		return apiMethod;
	}

	public void setApiMethod(String apiMethod) {
		this.apiMethod = apiMethod;
	}

	public String getApiUrlParam() {
		return apiUrlParam;
	}

	public void setApiUrlParam(String apiUrlParam) {
		this.apiUrlParam = apiUrlParam;
	}

	public String getApiBodyParam() {
		return apiBodyParam;
	}

	public void setApiBodyParam(String apiBodyParam) {
		this.apiBodyParam = apiBodyParam;
	}

	public String getApiContentType() {
		return apiContentType;
	}

	public void setApiContentType(String apiContentType) {
		this.apiContentType = apiContentType;
	}

	public String getApiResponse() {
		return apiResponse;
	}

	public void setApiResponse(String apiResponse) {
		this.apiResponse = apiResponse;
	}

	public Integer getApiResponseStatus() {
		return apiResponseStatus;
	}

	public void setApiResponseStatus(Integer apiResponseStatus) {
		this.apiResponseStatus = apiResponseStatus;
	}

	public Boolean getIsRequest() {
		return isRequest;
	}

	public void setIsRequest(Boolean isRequest) {
		this.isRequest = isRequest;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
