package com.lyht.business.abm.land.entity;


import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "t_abm_schedule_weekly")
@ApiModel(description = "周报汇总")
public class RequisitionPlanEntity implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @ApiModelProperty(value = "主键")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false )
    private Integer id;
  
    @ApiModelProperty(value = "内码")
    private String nm;
  
    @ApiModelProperty(value = "行政区域")
    @Column(name = "region")
    private String region;
    
    @ApiModelProperty(value = "行政区域名称")
    @Column(name = "region_name")
    private String regionName;
    
    @ApiModelProperty(value = "工作名称")
    @Column(name = "task_name")
    private String taskName;
    
    @ApiModelProperty(value = "工作类别")
    @Column(name = "task_type")
    private String taskType;
    
    @ApiModelProperty(value = "工作内容")
    @Column(name = "task_content")
    private String taskContent;
    
    @ApiModelProperty(value = "开始时间")
    @Column(name = "start_date")
    private String startDate;
    
    @ApiModelProperty(value = "结束时间")
    @Column(name = "complet_date")
    private String completDate;
    
    @ApiModelProperty(value = "进度情况")
    @Column(name = "progress")
    private String progress;
    
    @ApiModelProperty(value = "数量单位")
    @Column(name = "unit_number")
    private String unitNumber;
   
    @ApiModelProperty(value = "责任部门")
    @Column(name = "duty_unit")
    private String dutyUnit;
    
    @ApiModelProperty(value = "监督部门")
    @Column(name = "control_unit")
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



	public String getTaskName() {
		return taskName;
	}



	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}



	public String getTaskType() {
		return taskType;
	}



	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}



	public String getTaskContent() {
		return taskContent;
	}



	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
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



	public String getProgress() {
		return progress;
	}



	public void setProgress(String progress) {
		this.progress = progress;
	}



	public String getUnitNumber() {
		return unitNumber;
	}



	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
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
