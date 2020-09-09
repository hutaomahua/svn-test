package com.lyht.business.abm.settle.entity;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author HuangTianhao
 * @date 2019年11月28日
 */
@ApiModel(description = "集中居民点建设实施基本信息表")
@Entity
@Table(name = "t_abm_construction_settle_info")
public class SettleInfo implements Serializable {
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
	   * 区分字段
	 */
	@Column(name = "diff")
	@ApiModelProperty(value = "区分字段")
	private String diff;
	/**
	   * 居民点名称
	 */
	@Column(name = "name")
	@ApiModelProperty(value = "居民点名称")
	private String name;
	/**
	 * 行政区
	 */
	@Column(name = "region")
	@ApiModelProperty(value = "行政区")
	private String region;
	/**
	 * 行政区
	 */
	@Column(name = "region_limit")
	@ApiModelProperty(value = "行政区")
	private String regionLimit;
	/**
	 * 专业大类
	 */
	@Column(name = "region_type")
	@ApiModelProperty(value = "专业大类")
	private String regionType;
	/**
	 * 安置类型
	 */
	@Column(name = "setttle_type")
	@ApiModelProperty(value = "安置类型")
	private String setttleType;
	/**
	 * 规划安置规模（人）
	 */
	@Column(name = "built_size")
	@ApiModelProperty(value = "规划安置规模（人）")
	private int builtSize;
	/**
	 * 规划概算（万元）
	 */
	@Column(name = "built_cost")
	@ApiModelProperty(value = "规划概算（万元）")
	private double builtCost;
	/**
	 * 关联规划项目权属编号
	 */
	@Column(name = "info_code")
	@ApiModelProperty(value = "关联规划项目权属编号")
	private String infoCode;
	/**
	 * 新址坐标
	 */
	@Column(name = "gps")
	@ApiModelProperty(value = "新址坐标")
	private String gps;
	/**
	 * 居民点权属编号
	 */
	@Column(name = "setttle_code")
	@ApiModelProperty(value = "居民点权属编号")
	private String setttleCode;
	/**
	 * 实施责任单位
	 */
	@Column(name = "res_unit")
	@ApiModelProperty(value = "实施责任单位")
	private String resUnit;
	/**
	 * 施工图预算
	 */
	@Column(name = "built_map_cost")
	@ApiModelProperty(value = "施工图预算")
	private double builtMapCost;
	/**
	 * 是否代建
	 */
	@Column(name = "is_deputied")
	@ApiModelProperty(value = "是否代建")
	private int isDeputied;
	/**
	 * 代建单位
	 */
	@Column(name = "deputy_unit")
	@ApiModelProperty(value = "代建单位")
	private String deputyUnit;
	/**
	 * 施工设计单位
	 */
	@Column(name = "design_unit")
	@ApiModelProperty(value = "施工设计单位")
	private String designUnit;
	/**
	 * 施工单位
	 */
	@Column(name = "built_unit")
	@ApiModelProperty(value = "施工单位")
	private String builtuUnit;
	/**
	 * 监理单位
	 */
	@Column(name = "super_unit")
	@ApiModelProperty(value = "监理单位")
	private String superUnit;
	/**
	 * 数据状态
	 */
	@Column(name = "status")
	@ApiModelProperty(value = "数据状态")
	private String status;
	/**
	 * 
	 * /** 内码
	 */
	@Column(name = "nm")
	@ApiModelProperty(value = "内码")
	private String nm;

	/**
	 * 创建时间
	 */

	@CreatedDate
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "创建时间")
	@Column(name = "create_time", nullable = false, updatable = false)
	private Date createTime;

	/**
	 * 创建用户
	 */
	@ApiModelProperty(value = "创建用户")
	@CreatedBy
	@Column(name = "create_user", nullable = false, updatable = false)
	private String createUser;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	@CreatedDate
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "modify_time", nullable = false, insertable = false, updatable = true)
	private Date modifyTime;

	/**
	 * 修改用户
	 */
	@ApiModelProperty(value = "修改用户")
	@LastModifiedBy
	@Column(name = "modify_user", nullable = false, insertable = false, updatable = true)
	private String modifyUser;

	public String getRegionLimit() {
		return regionLimit;
	}

	public void setRegionLimit(String regionLimit) {
		this.regionLimit = regionLimit;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRegion() {
		return region;
	}

	public String getRegionType() {
		return regionType;
	}

	public String getSetttleType() {
		return setttleType;
	}

	public int getBuiltSize() {
		return builtSize;
	}

	public double getBuiltCost() {
		return builtCost;
	}

	public String getInfoCode() {
		return infoCode;
	}

	public String getGps() {
		return gps;
	}

	public String getSetttleCode() {
		return setttleCode;
	}

	public String getResUnit() {
		return resUnit;
	}

	public double getBuiltMapCost() {
		return builtMapCost;
	}

	public int getIsDeputied() {
		return isDeputied;
	}

	public String getDeputyUnit() {
		return deputyUnit;
	}

	public String getDesignUnit() {
		return designUnit;
	}

	public String getBuiltuUnit() {
		return builtuUnit;
	}

	public String getSuperUnit() {
		return superUnit;
	}

	public String getStatus() {
		return status;
	}

	public String getNm() {
		return nm;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	public void setSetttleType(String setttleType) {
		this.setttleType = setttleType;
	}

	public void setBuiltSize(int builtSize) {
		this.builtSize = builtSize;
	}

	public void setBuiltCost(double builtCost) {
		this.builtCost = builtCost;
	}

	public void setInfoCode(String infoCode) {
		this.infoCode = infoCode;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public void setSetttleCode(String setttleCode) {
		this.setttleCode = setttleCode;
	}

	public void setResUnit(String resUnit) {
		this.resUnit = resUnit;
	}

	public void setBuiltMapCost(double builtMapCost) {
		this.builtMapCost = builtMapCost;
	}

	public void setIsDeputied(int isDeputied) {
		this.isDeputied = isDeputied;
	}

	public void setDeputyUnit(String deputyUnit) {
		this.deputyUnit = deputyUnit;
	}

	public void setDesignUnit(String designUnit) {
		this.designUnit = designUnit;
	}

	public void setBuiltuUnit(String builtuUnit) {
		this.builtuUnit = builtuUnit;
	}

	public void setSuperUnit(String superUnit) {
		this.superUnit = superUnit;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getDiff() {
		return diff;
	}

	public void setDiff(String diff) {
		this.diff = diff;
	}

}
