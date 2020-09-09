package com.lyht.business.pub.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "年度计划树结构")
@Entity
@Table(name = "t_abm_year_subject")
public class PubYearSubject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	@Id
	@ApiModelProperty(value = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	/**
	 * 唯一编号
	 */
	 @ApiModelProperty(value = "内码")
	@Column(name = "nm")
	private String nm;

	/**
	 * 序号
	 */
	@ApiModelProperty(value = "序号")
	@Column(name = "serial_number", nullable = false)
	private String serialNumber;
	
	/**
	 * 短编码
	 */
	@ApiModelProperty(value = "短编码")
	@Column(name = "scode", nullable = false)
	private String scode;
	
	/**
	 * 全编码
	 */
	@ApiModelProperty(value = "全编码")
	@Column(name = "fcode", nullable = false)
	private String fcode;
	
	/**
	 *父级编码
	 */
	@ApiModelProperty(value = "父级编码")
	@Column(name = "super_id", nullable = false)
	private Integer superId;
	
	/**
	 *项目名称
	 */
	@ApiModelProperty(value = "项目名称")
	@Column(name = "name", nullable = false)
	private String name;
	
	/**
	 *项目类型
	 */
	@ApiModelProperty(value = "项目类型")
	@Column(name = "type", nullable = false)
	private String type;
	
	/**
	 *计量单位
	 */
	@ApiModelProperty(value = "计量单位")
	@Column(name = "danwei", nullable = false)
	private String danwei;
	
	/**
	 *单价
	 */
	@ApiModelProperty(value = "单价")
	@Column(name = "money", nullable = false)
	private BigDecimal money;
	
	/**
	 *概算指标
	 */
	@ApiModelProperty(value = "概算指标")
	@Column(name = "target", nullable = false)
	private BigDecimal target;
	
	/**
	 *排序
	 */
	@ApiModelProperty(value = "排序")
	@Column(name = "sorted", nullable = false)
	private  Integer sorted;
	
	/**
	 *级别
	 */
	@ApiModelProperty(value = "级别")
	@Column(name = "level", nullable = false)
	private  Integer level;
	
	/**
	 *状态
	 */
	@ApiModelProperty(value = "状态")
	@Column(name = "flag", nullable = false)
	private  Integer flag;
	
	/**
	 *备注
	 */
	@ApiModelProperty(value = "备注")
	@Column(name = "memo", nullable = false)
	private  String memo;
	
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

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public String getFcode() {
		return fcode;
	}

	public void setFcode(String fcode) {
		this.fcode = fcode;
	}

	

	public Integer getSuperId() {
		return superId;
	}

	public void setSuperId(Integer superId) {
		this.superId = superId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDanwei() {
		return danwei;
	}

	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getTarget() {
		return target;
	}

	public void setTarget(BigDecimal target) {
		this.target = target;
	}

	public Integer getSorted() {
		return sorted;
	}

	public void setSorted(int sorted) {
		this.sorted = sorted;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setSorted(Integer sorted) {
		this.sorted = sorted;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
}
