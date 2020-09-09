package com.lyht.business.abm.plan.entity;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 	年度计划编制
 *
 * @author jms
 *           2019/10/18
 *
 */

@ApiModel(description = "年度计划编制表")
@Entity        
@Table(name = "t_abm_year_plan")
public class YearAnnualPlanEntity  implements Serializable {
    private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "唯一ID，修改必填")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;//'主键id'
	@ApiModelProperty(value = "内码")
	private String nm;//'内码'

	@ApiModelProperty(value = "排序序号")
	private Integer sort;//'排序序号'

	@ApiModelProperty(value = "数据关联nm")
	@Column(name = "data_nm", nullable = false)
	private String dataNm;//
	
	@ApiModelProperty(value = "项目名称")
	@Column(name = "project_nm", nullable = false)
	private String projectNm;//'项目名称'

	@ApiModelProperty(value = "单位")
	@Column(name = "unit_nm", nullable = false)
	private String unitNm;//'单位'
	
	@ApiModelProperty(value = "行政区")
	@Column(name = "region", nullable = false)
	private String region;//'单位'
	
	@ApiModelProperty(value = "年份")
	@Column(name = "year", nullable = false)
	private String year;//'单位'

	@ApiModelProperty(value = "任务名称")
	@Column(name = "task_name", nullable = false)
	private String taskName;//'任务名称'

	@ApiModelProperty(value = "概算万元")
	@Column(name = "budget_wan", nullable = false)
	private BigDecimal budgetWan;//'概算万元'

	@ApiModelProperty(value = "完成任务量")
	@Column(name = "complet_task_number", nullable = false)
	private String completTaskNumber;//'完成任务量'

	@ApiModelProperty(value = "资金万元")
	@Column(name = "capital", nullable = false)
	private BigDecimal capital;//'资金万元'

	@ApiModelProperty(value = "剩余概算")
	@Column(name = "residue_invest", nullable = false)
	private BigDecimal residueInvest;//'剩余概算'

	@ApiModelProperty(value = "上年结算资金")
	@Column(name = "lastyear_capital", nullable = false)
	private BigDecimal lastyearCapital;//'上年结算资金'

	@ApiModelProperty(value = "任务量")
	@Column(name = "sum_task_number", nullable = false)
	private String sumTaskNumber;//'任务量'

	@ApiModelProperty(value = "简述")
	@Column(name = "sketch", nullable = false)
	private String sketch;//'简述'

	@ApiModelProperty(value = "资金万元（2019年任务及资金计划）")
	@Column(name = "capital_two", nullable = false)
	private BigDecimal capitalTwo;//'资金万元（2019年任务及资金计划）'

	@ApiModelProperty(value = "上年结算资金")
	@Column(name = "lastyear_capital_two", nullable = false)
	private BigDecimal lastyearCapitalTwo;//'上年结算资金'

	@ApiModelProperty(value = "新增资金")
	@Column(name = "new_capital", nullable = false)
	private BigDecimal newCapital;//'新增资金'

	@ApiModelProperty(value = "待安排资金")
	@Column(name = "to_be_capital", nullable = false)
	private BigDecimal toBeCapital;//'待安排资金'

	@ApiModelProperty(value = "剩余结存资金")
	@Column(name = "residue_sum_capital", nullable = false)
	private BigDecimal  residueSumCapital;//'剩余结存资金'

	@ApiModelProperty(value = "责任单位")
	@Column(name = "duty_unit", nullable = false)
	private String dutyUnit;//'责任单位'

	@ApiModelProperty(value = "完成时间")
	@Column(name = "complet_date", nullable = false)
	private String completDate; //完成时间

	@ApiModelProperty(value = "监督单位")
	@Column(name = "supervision_unit", nullable = false)
	private String supervisionUnit;//'监督单位'

	@ApiModelProperty(value = "父级id")
	@Column(name = "parent_id", nullable = false)
	private int parentId;//'父级id'

	@ApiModelProperty(value = "修改人")
	@Column(name = "update_staff", nullable = false)
	private String updateStaff;//'修改人'

	@ApiModelProperty(value = "创建人")
	@Column(name = "create_staff", nullable = false)
	private String createStaff;//'创建人'

	@Column(name = "create_time", nullable = false)
	private String createTime;//'创建人'

	@Column(name = "start_date", nullable = false)
	private String startDate;//'修改时间'

	@Column(name = "update_time", nullable = false)
	private String updateTime;//'开始时间'

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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getProjectNm() {
		return projectNm;
	}

	public void setProjectNm(String projectNm) {
		this.projectNm = projectNm;
	}

	public String getUnitNm() {
		return unitNm;
	}

	public void setUnitNm(String unitNm) {
		this.unitNm = unitNm;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public BigDecimal getBudgetWan() {
		return budgetWan;
	}

	public void setBudgetWan(BigDecimal budgetWan) {
		this.budgetWan = budgetWan;
	}

	public String getCompletTaskNumber() {
		return completTaskNumber;
	}

	public void setCompletTaskNumber(String completTaskNumber) {
		this.completTaskNumber = completTaskNumber;
	}

	public BigDecimal getCapital() {
		return capital;
	}

	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}

	public BigDecimal getResidueInvest() {
		return residueInvest;
	}

	public void setResidueInvest(BigDecimal residueInvest) {
		this.residueInvest = residueInvest;
	}

	public BigDecimal getLastyearCapital() {
		return lastyearCapital;
	}

	public void setLastyearCapital(BigDecimal lastyearCapital) {
		this.lastyearCapital = lastyearCapital;
	}

	public String getSumTaskNumber() {
		return sumTaskNumber;
	}

	public void setSumTaskNumber(String sumTaskNumber) {
		this.sumTaskNumber = sumTaskNumber;
	}

	public String getSketch() {
		return sketch;
	}

	public void setSketch(String sketch) {
		this.sketch = sketch;
	}

	public BigDecimal getCapitalTwo() {
		return capitalTwo;
	}

	public void setCapitalTwo(BigDecimal capitalTwo) {
		this.capitalTwo = capitalTwo;
	}

	public BigDecimal getLastyearCapitalTwo() {
		return lastyearCapitalTwo;
	}

	public void setLastyearCapitalTwo(BigDecimal lastyearCapitalTwo) {
		this.lastyearCapitalTwo = lastyearCapitalTwo;
	}

	public BigDecimal getNewCapital() {
		return newCapital;
	}

	public void setNewCapital(BigDecimal newCapital) {
		this.newCapital = newCapital;
	}

	public BigDecimal getToBeCapital() {
		return toBeCapital;
	}

	public void setToBeCapital(BigDecimal toBeCapital) {
		this.toBeCapital = toBeCapital;
	}

	public BigDecimal getResidueSumCapital() {
		return residueSumCapital;
	}

	public void setResidueSumCapital(BigDecimal residueSumCapital) {
		this.residueSumCapital = residueSumCapital;
	}

	public String getDutyUnit() {
		return dutyUnit;
	}

	public void setDutyUnit(String dutyUnit) {
		this.dutyUnit = dutyUnit;
	}

	public String getCompletDate() {
		return completDate;
	}

	public void setCompletDate(String completDate) {
		this.completDate = completDate;
	}

	public String getSupervisionUnit() {
		return supervisionUnit;
	}

	public void setSupervisionUnit(String supervisionUnit) {
		this.supervisionUnit = supervisionUnit;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getUpdateStaff() {
		return updateStaff;
	}

	public void setUpdateStaff(String updateStaff) {
		this.updateStaff = updateStaff;
	}

	public String getCreateStaff() {
		return createStaff;
	}

	public void setCreateStaff(String createStaff) {
		this.createStaff = createStaff;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	
	
	public String getDataNm() {
		return dataNm;
	}

	public void setDataNm(String dataNm) {
		this.dataNm = dataNm;
	}

	
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
