package com.lyht.business.abm.plan.entity;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(description = "项目实施计划")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_abm_project_implement")
public class ProjectPlanEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "唯一ID，修改必填")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

   
    @ApiModelProperty(value = "唯一内码 ")
    private String nm;
   
    @ApiModelProperty(value = "工作名称 ")
    @Column(name = "task_name", nullable = false)
    private String taskName;
    
    @ApiModelProperty(value = "工作内容 ")
    @Column(name = "task_content", nullable = false)
    private String taskContent;
    
    @ApiModelProperty(value = "工作类别 ")
    @Column(name = "task_type", nullable = false)
    private String taskType;
   
    @ApiModelProperty(value = "行政去")
    private String region;

    
    @Column(name = "region_name", nullable = false)
    @ApiModelProperty(value = "行政去名称")
    private String regionName;
   

   
    @ApiModelProperty(value = "开始时间")
    @Column(name = "start_Date", nullable = false)
    private String startDate;

  
    @ApiModelProperty(value = "完成时间")
    @Column(name = "complet_Date", nullable = false)
    private String completDate;

  
    @ApiModelProperty(value = "数量单位")
    @Column(name = "unit_Number", nullable = false)
    private String unitNumber;

  
    @ApiModelProperty(value = "总任务量 只能为数据")
    @Column(name = "sum_task_Number", nullable = false)
    private Integer sumTaskNumber;

   
    @ApiModelProperty(value = "总投资万元")
    @Column(name = "sum_invest", nullable = false)
    private BigDecimal sumInvest;

   
    
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @Column(name = "state", nullable = false)
    private String state;
  

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建用户")
    @CreatedBy
    @Column(name = "create_staff", nullable = false, updatable = false)
    private String createStaff;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;

    /**
     * 修改人
     */

    @ApiModelProperty(value = "修改用户")
    @LastModifiedBy
    @Column(name = "update_staff", nullable = false, insertable = false, updatable = true)
    private String updateStaff;

    @ApiModelProperty(value = "创建时间")
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "update_time", nullable = false, insertable = false, updatable = true)
    private Date updateTime;

   
    @ApiModelProperty(value = " 类别")
    @Column(name = "type", nullable = false)
    private String type;
    
    @ApiModelProperty(value = " 原因")
    @Column(name = "cause", nullable = false)
    private String cause;
    
    @ApiModelProperty(value = "责任单位")
    @Column(name = "duty_unit", nullable = false)
    private String dutyUnit;
    
    @ApiModelProperty(value = "监督单位")
    @Column(name = "control_unit", nullable = false)
    private String controlUnit;
    
    
    
    
  


	public Integer getId() {
		return id;
	}







	public void setId(Integer id) {
		this.id = id;
	}







	public String getNm() {
		return nm;
	}







	public void setNm(String nm) {
		this.nm = nm;
	}







	public String getTaskName() {
		return taskName;
	}







	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}







	public String getTaskContent() {
		return taskContent;
	}







	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}







	public String getTaskType() {
		return taskType;
	}







	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}







	public String getRegion() {
		return region;
	}







	public void setRegion(String region) {
		this.region = region;
	}







	public String getRegionName() {
		return regionName;
	}







	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}







	public String getStartDate() {
		return startDate;
	}







	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}







	public String getCompletDate() {
		return completDate;
	}







	public void setCompletDate(String completDate) {
		this.completDate = completDate;
	}







	public String getUnitNumber() {
		return unitNumber;
	}







	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}







	public Integer getSumTaskNumber() {
		return sumTaskNumber;
	}







	public void setSumTaskNumber(Integer sumTaskNumber) {
		this.sumTaskNumber = sumTaskNumber;
	}







	public BigDecimal getSumInvest() {
		return sumInvest;
	}







	public void setSumInvest(BigDecimal sumInvest) {
		this.sumInvest = sumInvest;
	}







	public String getState() {
		return state;
	}







	public void setState(String state) {
		this.state = state;
	}







	public String getCreateStaff() {
		return createStaff;
	}







	public void setCreateStaff(String createStaff) {
		this.createStaff = createStaff;
	}







	public Date getCreateTime() {
		return createTime;
	}







	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}







	public String getUpdateStaff() {
		return updateStaff;
	}







	public void setUpdateStaff(String updateStaff) {
		this.updateStaff = updateStaff;
	}







	public Date getUpdateTime() {
		return updateTime;
	}







	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}







	public String getType() {
		return type;
	}







	public void setType(String type) {
		this.type = type;
	}







	public String getCause() {
		return cause;
	}







	public void setCause(String cause) {
		this.cause = cause;
	}







	public String getDutyUnit() {
		return dutyUnit;
	}







	public void setDutyUnit(String dutyUnit) {
		this.dutyUnit = dutyUnit;
	}







	public String getControlUnit() {
		return controlUnit;
	}







	public void setControlUnit(String controlUnit) {
		this.controlUnit = controlUnit;
	}







	@Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
