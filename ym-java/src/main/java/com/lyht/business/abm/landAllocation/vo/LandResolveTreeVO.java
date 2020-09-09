package com.lyht.business.abm.landAllocation.vo;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("土地分解汇总--树")
public class LandResolveTreeVO {
	
	@ApiModelProperty("序号")
	private String serialNumber;
	
	@ApiModelProperty("行政区编码")
	private String cityCode;
	
	@ApiModelProperty("父节点编码")
	private String gparentCode;
	
	@ApiModelProperty("行政区名称")
	private String name;
	
	@ApiModelProperty("行政区全称")
	private String mergerName;
	
	@ApiModelProperty("土地总面积")
	private BigDecimal area;
	
	@ApiModelProperty("土地未分解面积")
	private BigDecimal separateArea;
	
	@ApiModelProperty("土地已分解面积")
	private BigDecimal resolveArea;

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getGparentCode() {
		return gparentCode;
	}

	public void setGparentCode(String gparentCode) {
		this.gparentCode = gparentCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMergerName() {
		return mergerName;
	}

	public void setMergerName(String mergerName) {
		this.mergerName = mergerName;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public BigDecimal getSeparateArea() {
		return separateArea;
	}

	public void setSeparateArea(BigDecimal separateArea) {
		this.separateArea = separateArea;
	}

	public BigDecimal getResolveArea() {
		return resolveArea;
	}

	public void setResolveArea(BigDecimal resolveArea) {
		this.resolveArea = resolveArea;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
