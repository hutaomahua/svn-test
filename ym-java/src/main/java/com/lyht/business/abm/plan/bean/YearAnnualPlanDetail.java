package com.lyht.business.abm.plan.bean;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 	年度计划编制
 *
 * @author jms
 *           2019/10/18
 *
 */

@ApiModel(description = "年度计划编制表")
public class YearAnnualPlanDetail implements Serializable {
    private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "唯一ID，修改必填")

	private Integer id;//'主键id'
	@ApiModelProperty(value = "内码")
	private String nm;//'内码'

	@ApiModelProperty(value = "排序序号")
	private Integer sort;//'排序序号'

	@ApiModelProperty(value = "项目名称")

	private String projectNm;//'项目名称'

	@ApiModelProperty(value = "单位")

	private String unitNm;//'单位'

	@ApiModelProperty(value = "任务名称")

	private String taskName;//'任务名称'

	@ApiModelProperty(value = "概算万元")

	private BigDecimal budgetWan;//'概算万元'

	@ApiModelProperty(value = "完成任务量")

	private String completTaskNumber;//'完成任务量'

	@ApiModelProperty(value = "资金万元")

	private BigDecimal capital;//'资金万元'

	@ApiModelProperty(value = "剩余概算")

	private BigDecimal residueInvest;//'剩余概算'

	@ApiModelProperty(value = "上年结算资金")

	private BigDecimal lastyearCapital;//'上年结算资金'

	@ApiModelProperty(value = "任务量")

	private String sumTaskNumber;//'任务量'

	@ApiModelProperty(value = "简述")

	private String sketch;//'简述'

	@ApiModelProperty(value = "资金万元（2019年任务及资金计划）")

	private BigDecimal capitalTwo;//'资金万元（2019年任务及资金计划）'

	@ApiModelProperty(value = "上年结算资金")

	private BigDecimal lastyearCapitalTwo;//'上年结算资金'

	@ApiModelProperty(value = "新增资金")

	private BigDecimal newCapital;//'新增资金'

	@ApiModelProperty(value = "待安排资金")

	private BigDecimal toBeCapital;//'待安排资金'

	@ApiModelProperty(value = "剩余结存资金")

	private BigDecimal  residueSumCapital;//'剩余结存资金'

	@ApiModelProperty(value = "责任单位")

	private String dutyUnit;//'责任单位'

	@ApiModelProperty(value = "完成时间")

	private String completDate; //完成时间

	@ApiModelProperty(value = "监督单位")

	private String supervisionUnit;//'监督单位'

	@ApiModelProperty(value = "父级id")

	private int parentId;//'父级id'

	@ApiModelProperty(value = "修改人")

	private String updateStaff;//'修改人'

	@ApiModelProperty(value = "创建人")

	private String createStaff;//'创建人'


	private String createTime;//'创建人'


	private String startDate;//'修改时间'

	private String updateTime;//'开始时间'

	private List<YearAnnualPlanDetail> children;//'开始时间'


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

	public List<YearAnnualPlanDetail> getChildren() {
		return children;
	}

	public void setChildren(List<YearAnnualPlanDetail> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
