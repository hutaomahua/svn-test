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
import lombok.Data;

@ApiModel(description = "农副业设施")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Data
@Table(name = "t_info_agricultural_facilities")
public class InfoAgriculturalFacilitiesEntity {

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

	@ApiModelProperty("规格")
	@Column(name = "specifications")
	private String specifications;

	@ApiModelProperty("单位")
	@Column(name = "unit")
	private String unit;

	@ApiModelProperty("长")
	@Column(name = "longs")
	private BigDecimal longs;

	@ApiModelProperty("宽")
	@Column(name = "width")
	private BigDecimal width;

	@ApiModelProperty("高")
	@Column(name = "height")
	private BigDecimal height;

	@ApiModelProperty("面积")
	@Column(name = "area")
	private BigDecimal area;

	@ApiModelProperty("体积")
	@Column(name = "volume")
	private BigDecimal volume;

	@ApiModelProperty("数量")
	@Column(name = "num")
	private BigDecimal num;

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

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}