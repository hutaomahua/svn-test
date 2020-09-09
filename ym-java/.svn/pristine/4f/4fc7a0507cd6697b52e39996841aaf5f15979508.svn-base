package com.lyht.business.process.entity;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 流程
 * 
 * @author hxl
 *
 */
@ApiModel("流程")
@Entity
//@DynamicUpdate
@Table(name = "t_bpm_process")
public class ProcessEntity {

	@ApiModelProperty("流程id")
	@Id
	@Column(name = "process_id")
	private String processId;

	@ApiModelProperty("流程名称")
	@Column(name = "name")
	private String name;

	@ApiModelProperty("申请人")
	@Column(name = "applicant")
	private String applicant;
	
	@ApiModelProperty("代理申请人")
	@Column(name = "agent")
	private String agent;

	@ApiModelProperty("申请时间")
	@Column(name = "apply_time")
	private Date applyTime;

	@ApiModelProperty("完成时间")
	@Column(name = "complete_time")
	private Date completeTime;

	@ApiModelProperty("表单地址")
	@Column(name = "read_url")
	private String readUrl;

	@ApiModelProperty("说明")
	@Column(name = "remark")
	private String remark;

	@ApiModelProperty("流程状态")
	@Column(name = "status")
	private String status;
	
	@ApiModelProperty("中文状态")
	@Column(name = "cn_status")
	private String cnStatus;
	
	@ApiModelProperty("流程数据")
	@Column(name = "process_data")
	private String processData;
	
	@ApiModelProperty("流程对应的类路径")
	@Column(name = "inst_cla")
	private String instCla;
	
	@ApiModelProperty("是否来源于外部流程")
	@Column(name = "form_out_side")
	private Boolean formOutSide;
	
	@ApiModelProperty("流程归属系统")
	@Column(name = "owner_system")
	private String ownerSystem;
	
	@ApiModelProperty("流程审批data")
	@Column(name = "process_operate_data", updatable = false)
	private String processOperateData;

	@ApiModelProperty(value = "创建时间")
	@CreatedDate
	@Column(name = "create_time", updatable = false)
	private Date createTime;
	@ApiModelProperty(value = "创建用户")
	@CreatedBy
	@Column(name = "create_user", updatable = false)
	private String createUser;
	@ApiModelProperty(value = "修改时间")
	@LastModifiedDate
	@Column(name = "modify_time", insertable = false)
	private Date modifyTime;
	@ApiModelProperty(value = "修改用户")
	@LastModifiedBy
	@Column(name = "modify_user", insertable = false)
	private String modifyUser;

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

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

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public String getReadUrl() {
		return readUrl;
	}

	public void setReadUrl(String readUrl) {
		this.readUrl = readUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCnStatus() {
		return cnStatus;
	}

	public void setCnStatus(String cnStatus) {
		this.cnStatus = cnStatus;
	}

	public String getProcessData() {
		return processData;
	}

	public void setProcessData(String processData) {
		this.processData = processData;
	}

	public String getInstCla() {
		return instCla;
	}

	public void setInstCla(String instCla) {
		this.instCla = instCla;
	}

	public Boolean getFormOutSide() {
		return formOutSide;
	}

	public void setFormOutSide(Boolean formOutSide) {
		this.formOutSide = formOutSide;
	}

	public String getOwnerSystem() {
		return ownerSystem;
	}

	public void setOwnerSystem(String ownerSystem) {
		this.ownerSystem = ownerSystem;
	}

	public String getProcessOperateData() {
		return processOperateData;
	}

	public void setProcessOperateData(String processOperateData) {
		this.processOperateData = processOperateData;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
