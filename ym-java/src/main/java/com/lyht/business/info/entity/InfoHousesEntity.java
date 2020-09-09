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

@ApiModel(description = "房屋表")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "t_info_houses")
public class InfoHousesEntity {

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

	@ApiModelProperty("栋数")
	@Column(name = "house_number")
	private Integer houseNumber;

	@ApiModelProperty("用途")
	@Column(name = "purpose")
	private String purpose;

	@ApiModelProperty("结构类型")
	@Column(name = "structure_type")
	private String structureType;

	@ApiModelProperty("层数")
	@Column(name = "layer_number")
	private Integer layerNumber;

	@ApiModelProperty("层高(m)")
	@Column(name = "layer_height")
	private BigDecimal layerHeight;

	@ApiModelProperty("形状")
	@Column(name = "shape")
	private String shape;

	@ApiModelProperty("地址")
	@Column(name = "address")
	private String address;

	@ApiModelProperty("长")
	@Column(name = "longs")
	private BigDecimal longs;

	@ApiModelProperty("宽")
	@Column(name = "width")
	private BigDecimal width;

	@ApiModelProperty("面积(㎡)")
	@Column(name = "area")
	private BigDecimal area;

	@ApiModelProperty("装修等级")
	@Column(name = "decorate_grade")
	private String decorateGrade;

	@ApiModelProperty("道路里程")
	@Column(name = "road_mileage")
	private String roadMileage;

	@ApiModelProperty("经度")
	@Column(name = "lgtd")
	private String lgtd;

	@ApiModelProperty("纬度")
	@Column(name = "lttd")
	private String lttd;

	@ApiModelProperty("高程")
	@Column(name = "altd")
	private String altd;

	@ApiModelProperty("图幅号")
	@Column(name = "in_map")
	private String inMap;

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

	public Integer getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(Integer houseNumber) {
		this.houseNumber = houseNumber;
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

	public BigDecimal getLayerHeight() {
		return layerHeight;
	}

	public void setLayerHeight(BigDecimal layerHeight) {
		this.layerHeight = layerHeight;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getLongs() {
		return longs;
	}

	public void setLongs(BigDecimal longs) {
		this.longs = longs;
	}

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
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

	public String getRoadMileage() {
		return roadMileage;
	}

	public void setRoadMileage(String roadMileage) {
		this.roadMileage = roadMileage;
	}

	public String getLgtd() {
		return lgtd;
	}

	public void setLgtd(String lgtd) {
		this.lgtd = lgtd;
	}

	public String getLttd() {
		return lttd;
	}

	public void setLttd(String lttd) {
		this.lttd = lttd;
	}

	public String getAltd() {
		return altd;
	}

	public void setAltd(String altd) {
		this.altd = altd;
	}

	public String getInMap() {
		return inMap;
	}

	public void setInMap(String inMap) {
		this.inMap = inMap;
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

	public String getOwnerNm() {
		return ownerNm;
	}

	public void setOwnerNm(String ownerNm) {
		this.ownerNm = ownerNm;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}