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

@ApiModel(description = "项目实施计划调整记录")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_abm_project_implement_log")
public class ProjectPlanAdjustEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "唯一ID，修改必填")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 内码
     */
    @ApiModelProperty(value = "唯一内码 ")
    private String nm;
    
    /**
     * 数据唯一内码
     */
    @ApiModelProperty(value = "数据唯一内码 ")
    @Column(name = "data_nm", nullable = false)
    private String dataNm;
    
   

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
    @Column(name = "start_date", nullable = false)
    private String startDate;

  
    @ApiModelProperty(value = "完成时间")
    @Column(name = "complet_date", nullable = false)
    private String completDate;

  
    @ApiModelProperty(value = "数量单位")
    @Column(name = "unit_number", nullable = false)
    private String unitNumber;

  
    @ApiModelProperty(value = "总任务量 只能为数据")
    @Column(name = "sum_task_number", nullable = false)
    private Integer sumTaskNumber;

   
    @ApiModelProperty(value = "总投资万元")
    @Column(name = "sum_invest", nullable = false)
    private BigDecimal sumInvest;

    @ApiModelProperty(value = "责任单位")
    @Column(name = "duty_unit", nullable = false)
    private String dutyUnit;
    
    @ApiModelProperty(value = "监督单位")
    @Column(name = "control_unit", nullable = false)
    private String controlUnit;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Column(name = "remark", nullable = false)
    private String remark;
    
    /**
     * 调整计划类别
     */
    @ApiModelProperty(value = " 调整计划类别")
    @Column(name = "adjust_type", nullable = false)
    private String adjustType;
   
   
    /**
     * 调整日期时间
     */
    @ApiModelProperty(value = "创建时间")
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "plan_adjust_date", nullable = false, updatable = false)
    private Date planAdjustDate;
   

    /**
     * 调整人
     */
    @ApiModelProperty(value = " 调整人")
    @Column(name = "adjust_staff_nm", nullable = false)
    private String adjustStaffNm;

    /**
     * 调整原因
     */
    @ApiModelProperty(value = " 调整原因")
    @Column(name = "adjust_cause", nullable = false)
    private String adjustCause;
    
    /**
     * 调整审核状态
     */
    @ApiModelProperty(value = "调整审核状态")
    @Column(name = "adjust_state", nullable = false)
    private String adjustState;




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




	public String getDataNm() {
		return dataNm;
	}




	public void setDataNm(String dataNm) {
		this.dataNm = dataNm;
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




	public String getRemark() {
		return remark;
	}




	public void setRemark(String remark) {
		this.remark = remark;
	}




	public String getAdjustType() {
		return adjustType;
	}




	public void setAdjustType(String adjustType) {
		this.adjustType = adjustType;
	}




	public Date getPlanAdjustDate() {
		return planAdjustDate;
	}




	public void setPlanAdjustDate(Date planAdjustDate) {
		this.planAdjustDate = planAdjustDate;
	}




	public String getAdjustStaffNm() {
		return adjustStaffNm;
	}




	public void setAdjustStaffNm(String adjustStaffNm) {
		this.adjustStaffNm = adjustStaffNm;
	}




	public String getAdjustCause() {
		return adjustCause;
	}




	public void setAdjustCause(String adjustCause) {
		this.adjustCause = adjustCause;
	}




	public String getAdjustState() {
		return adjustState;
	}




	public void setAdjustState(String adjustState) {
		this.adjustState = adjustState;
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
