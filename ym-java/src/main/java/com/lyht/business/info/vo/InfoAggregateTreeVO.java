package com.lyht.business.info.vo;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("实物指标汇总--分项")
public class InfoAggregateTreeVO {

	@ApiModelProperty("序号")
	private String serialNumber;

	@ApiModelProperty("项目")
	private String project;

	@ApiModelProperty("单位")
	private String unit;

	@ApiModelProperty("枢纽工程建设区--合计")
	private BigDecimal reservoirTotal;

	@ApiModelProperty("书库淹没影响区--合计")
	private BigDecimal pivotTotal;

	@ApiModelProperty("枢纽临时")
	private BigDecimal temporary;

	@ApiModelProperty("枢纽永久")
	private BigDecimal permanent;

	@ApiModelProperty("水库淹没区")
	private BigDecimal flood;

	@ApiModelProperty("水库影响区")
	private BigDecimal influence;

	@ApiModelProperty("垫高（临时）")
	private BigDecimal raise;

	@ApiModelProperty("集镇新址占地区")
	private BigDecimal newTown;

	@ApiModelProperty("子集")
	private List<InfoAggregateTreeVO> children;

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getReservoirTotal() {
		return reservoirTotal;
	}

	public void setReservoirTotal(BigDecimal reservoirTotal) {
		this.reservoirTotal = reservoirTotal;
	}

	public BigDecimal getPivotTotal() {
		return pivotTotal;
	}

	public void setPivotTotal(BigDecimal pivotTotal) {
		this.pivotTotal = pivotTotal;
	}

	public BigDecimal getTemporary() {
		return temporary;
	}

	public void setTemporary(BigDecimal temporary) {
		this.temporary = temporary;
	}

	public BigDecimal getPermanent() {
		return permanent;
	}

	public void setPermanent(BigDecimal permanent) {
		this.permanent = permanent;
	}

	public BigDecimal getFlood() {
		return flood;
	}

	public void setFlood(BigDecimal flood) {
		this.flood = flood;
	}

	public BigDecimal getInfluence() {
		return influence;
	}

	public void setInfluence(BigDecimal influence) {
		this.influence = influence;
	}

	public BigDecimal getRaise() {
		return raise;
	}

	public void setRaise(BigDecimal raise) {
		this.raise = raise;
	}

	public BigDecimal getNewTown() {
		return newTown;
	}

	public void setNewTown(BigDecimal newTown) {
		this.newTown = newTown;
	}

	public List<InfoAggregateTreeVO> getChildren() {
		return children;
	}

	public void setChildren(List<InfoAggregateTreeVO> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
