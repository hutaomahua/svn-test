package com.lyht.business.fund.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 可研阶段概算表_拆分
 * 
 * @author wzw
 *
 */
@ApiModel(description = "可研阶段概算表_拆分")
@Entity
@Table(name = "t_fund_info")
public class FundInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一ID，修改必填
	 */
	@ApiModelProperty(value = "唯一ID，修改必填")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 唯一内码
	 */
	@ApiModelProperty(value = "唯一内码")
	private String nm;

	/**
	 * 序号(Excel)
	 */
	@ApiModelProperty(value = "序号（Excel）")
	@Column(name = "serial_number")
	private String serialNumber;

	/**
	 * 建设征地范围
	 */
	@ApiModelProperty(value = "建设征地范围")
	private String area;

	/**
	 * 项目(费用)名称
	 */
	@ApiModelProperty(value = "项目(费用)名称")
	@Column(name = "project_nm")
	private String projectNm;

	/**
	 * 单位
	 */
	@ApiModelProperty(value = "单位 计量单位")
	private String unit;

	/**
	 * 单价/费率(元/--)
	 */
	@ApiModelProperty(value = "单价/费率(元/--)")
	@Column(name = "unit_price")
	private Double unitPrice;

	/**
	 * 指标数量
	 */
	@ApiModelProperty(value = "指标数量")
	private Double quantity;

	/**
	 * 费用（区域合计）
	 */
	@ApiModelProperty(value = "费用（区域合计）")
	private Double total;

	/**
	 * 概算指标(区域合计)
	 */
	@ApiModelProperty(value = "概算指标(区域合计)")
	private Double target;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
	
	/**
	 * 类别
	 * @return
	 */
	@ApiModelProperty(value = "类别")
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public String getNm() {
		return nm;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public String getArea() {
		return area;
	}


	public String getProjectNm() {
		return projectNm;
	}

	public String getUnit() {
		return unit;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public Double getQuantity() {
		return quantity;
	}

	public Double getTotal() {
		return total;
	}

	public Double getTarget() {
		return target;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setProjectNm(String projectNm) {
		this.projectNm = projectNm;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public void setTarget(Double target) {
		this.target = target;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}


}
