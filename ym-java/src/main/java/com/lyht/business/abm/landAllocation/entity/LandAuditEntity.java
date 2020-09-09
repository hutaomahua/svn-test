package com.lyht.business.abm.landAllocation.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "t_abm_land_audit")
@ApiModel(description = "土地审核")
@ToString
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class LandAuditEntity {

    @ApiModelProperty(value = "主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ApiModelProperty(value = "源行政区域", required = true)
    @Column(name = "source_region")
    private String sourceRegion;

	@ApiModelProperty(value = "目标行政区域", required = true)
	@Column(name = "target_region")
	private String targetRegion;

	@ApiModelProperty(value = "用户内码", required = true)
	@Column(name = "nm")
    private String nm;

	@ApiModelProperty(value = "分解到户：0, 分解到行政区：1", required = true)
	@Column(name = "flag")
	private Integer flag;

    @ApiModelProperty(value = "土地类型", required = true)
    @Column(name = "land_type")
    private String landType;

    @ApiModelProperty(value = "征地范围", required = true)
    @Column(name = "scope")
    private String scope;

    @ApiModelProperty(value = "分类的级别", required = true)
    @Column(name = "type_level")
    private Integer typeLevel;

    @ApiModelProperty(value = "分解面积", required = true)
    @Column(name = "resolve_area")
	@JSONField(name = "resolveArea", serializeUsing = ToStringSerializer.class)
    private Double resolveArea;

    @ApiModelProperty(value = "待分解面积", required = true)
    @Column(name = "separate_area")
	@JSONField(name = "separateArea", serializeUsing = ToStringSerializer.class)
    private Double separateArea;

    @ApiModelProperty(value = "审核编码")
    @Column(name = "audit_code")
    private String auditCode;

	@ApiModelProperty(value = "审核申请人")
	@Column(name = "apply_user")
	private String applyUser;

	@ApiModelProperty(value = "审核申请时间")
	@Column(name = "apply_date")
	private String applyDate;

	@ApiModelProperty(value = "流程ID")
	@Column(name = "process_id")
	private String processId;

    @ApiModelProperty(value = "通过或拒绝内容")
    @Column(name = "remark")
    private String remark;

	@ApiModelProperty(value = "审核流程的类型")
	@Column(name = "city_type")
    private String cityType;

	@ApiModelProperty(value = "是否公示 ")
	@Column(name = "fg_state")
    private String fgState;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSourceRegion() {
		return sourceRegion;
	}

	public void setSourceRegion(String sourceRegion) {
		this.sourceRegion = sourceRegion;
	}

	public String getTargetRegion() {
		return targetRegion;
	}

	public void setTargetRegion(String targetRegion) {
		this.targetRegion = targetRegion;
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getLandType() {
		return landType;
	}

	public void setLandType(String landType) {
		this.landType = landType;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Integer getTypeLevel() {
		return typeLevel;
	}

	public void setTypeLevel(Integer typeLevel) {
		this.typeLevel = typeLevel;
	}

	public Double getResolveArea() {
		return resolveArea;
	}

	public void setResolveArea(Double resolveArea) {
		this.resolveArea = resolveArea;
	}

	public Double getSeparateArea() {
		return separateArea;
	}

	public void setSeparateArea(Double separateArea) {
		this.separateArea = separateArea;
	}

	public String getAuditCode() {
		return auditCode;
	}

	public void setAuditCode(String auditCode) {
		this.auditCode = auditCode;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}

	public String getFgState() {
		return fgState;
	}

	public void setFgState(String fgState) {
		this.fgState = fgState;
	}
	
}
