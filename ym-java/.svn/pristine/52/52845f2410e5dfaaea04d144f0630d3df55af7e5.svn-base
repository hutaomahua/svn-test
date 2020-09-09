package com.lyht.business.info.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "房屋装修表")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "t_info_houses_decoration")
public class InfoHousesDecorationEntity {

	@ApiModelProperty(value = "唯一ID(新增不填，修改必填)")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ApiModelProperty(value = "唯一内码（新增不填，修改必填）")
	private String nm;

	@ApiModelProperty("项目编码")
	@Column(name = "project_nm")
	private String projectNm;

	@ApiModelProperty("行政区")
	@Column(name = "region")
	private String region;

	@ApiModelProperty("指标类型")
	@Column(name = "zblx")
	private String zblx;

	@ApiModelProperty("征地范围")
	@Column(name = "scope")
	private String scope;

	@ApiModelProperty("权属人编码")
	@Column(name = "owner_nm")
	private String ownerNm;

	@ApiModelProperty("房屋性质")
	@Column(name = "house_nature")
	private String houseNature;

	@ApiModelProperty("用途")
	@Column(name = "purpose")
	private String purpose;

	@ApiModelProperty("结构类型")
	@Column(name = "structure_type")
	private String structureType;

	@ApiModelProperty("层数")
	@Column(name = "layer_number")
	private Integer layerNumber;

	@ApiModelProperty("尺寸1")
	@Column(name = "measurement_1")
	private String measurement1;

	@ApiModelProperty("尺寸2")
	@Column(name = "measurement_2")
	private String measurement2;

	@ApiModelProperty("面积(㎡)")
	@Column(name = "area")
	private BigDecimal area;

	@ApiModelProperty("装修等级")
	@Column(name = "decorate_grade")
	private String decorateGrade;

	@ApiModelProperty("权属性质")
	@Column(name = "owner_nature")
	private String ownerNature;

	@ApiModelProperty("数据状态")
	@Column(name = "status")
	private String status;

	@ApiModelProperty("阶段")
	@Column(name = "stage")
	private String stage;

	@ApiModelProperty(value = "创建时间")
	@CreatedDate
	@Column(name = "create_time", updatable = false)
	private Date createTime;
	@ApiModelProperty(value = "创建用户")
	@CreatedBy
	@Column(name = "create_user", updatable = false)
	private String createUser;
	@ApiModelProperty(value = "修改时间")
	@LastModifiedDate
	@Column(name = "modify_time")
	private Date modifyTime;
	@ApiModelProperty(value = "修改用户")
	@LastModifiedBy
	@Column(name = "modify_user")
	private String modifyUser;

	@ApiModelProperty("备注")
	@Column(name = "remark")
	private String remark;

	@ApiModelProperty("文件数量")
	@Transient
	private Integer fileCount;

	public Integer getFileCount() {
		return fileCount;
	}

	public void setFileCount(Integer fileCount) {
		this.fileCount = fileCount;
	}

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

	public String getProjectNm() {
		return projectNm;
	}

	public void setProjectNm(String projectNm) {
		this.projectNm = projectNm;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getZblx() {
		return zblx;
	}

	public void setZblx(String zblx) {
		this.zblx = zblx;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getOwnerNm() {
		return ownerNm;
	}

	public void setOwnerNm(String ownerNm) {
		this.ownerNm = ownerNm;
	}

	public String getHouseNature() {
		return houseNature;
	}

	public void setHouseNature(String houseNature) {
		this.houseNature = houseNature;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getStructureType() {
		return structureType;
	}

	public void setStructureType(String structureType) {
		this.structureType = structureType;
	}

	public Integer getLayerNumber() {
		return layerNumber;
	}

	public void setLayerNumber(Integer layerNumber) {
		this.layerNumber = layerNumber;
	}

	public String getMeasurement1() {
		return measurement1;
	}

	public void setMeasurement1(String measurement1) {
		this.measurement1 = measurement1;
	}

	public String getMeasurement2() {
		return measurement2;
	}

	public void setMeasurement2(String measurement2) {
		this.measurement2 = measurement2;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public String getDecorateGrade() {
		return decorateGrade;
	}

	public void setDecorateGrade(String decorateGrade) {
		this.decorateGrade = decorateGrade;
	}

	public String getOwnerNature() {
		return ownerNature;
	}

	public void setOwnerNature(String ownerNature) {
		this.ownerNature = ownerNature;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
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