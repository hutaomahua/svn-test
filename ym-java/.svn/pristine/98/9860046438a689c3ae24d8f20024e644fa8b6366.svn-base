package com.lyht.business.abm.removal.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 房屋装修
 * 
 * @author Administrator
 *
 */
@ApiModel(description = "房屋装修")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_info_houses_decoration_impl")
public class AbmHousesDecorationEntity{

	@ApiModelProperty(value = "唯一ID，修改必填")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ApiModelProperty(value = "内码")
	private String nm;

	@ApiModelProperty(value = "行政区")
	private String region;

	@ApiModelProperty(value = "项目编码")
	@Column(name = "project_nm")
	private String projectNm;

	@ApiModelProperty(value = "指标类型")
	@Column(name = "zblx")
	private String zblx;

	@ApiModelProperty(value = "征地范围")
	@Column(name = "scope")
	private String scope;

	@ApiModelProperty(value = "权属人编码")
	@Column(name = "owner_nm")
	private String ownerNm;

	@ApiModelProperty(value = "房屋性质")
	@Column(name = "house_nature")
	private String houseNature;

	@ApiModelProperty(value = "用途")
	@Column(name = "purpose")
	private String purpose;

	@ApiModelProperty(value = "结构类型")
	@Column(name = "structure_type")
	private String structureType;

	@ApiModelProperty(value = "层数")
	@Column(name = "layer_number")
	private Integer layerNumber;

	@ApiModelProperty(value = "尺寸1")
	@Column(name = "measurement_1")
	private String measurement1;

	@ApiModelProperty(value = "尺寸2")
	@Column(name = "measurement_2")
	private String measurement2;

	@ApiModelProperty(value = "装修面积(㎡)")
	@Column(name = "area")
	private BigDecimal area;

	@ApiModelProperty(value = "装修等级")
	@Column(name = "decorate_grade")
	private String decorateGrade;

	@ApiModelProperty(value = "权属性质")
	@Column(name = "owner_nature")
	private String ownerNature;

	@ApiModelProperty(value = "数据状态")
	@Column(name = "status")
	private String status;

	@ApiModelProperty(value = "备注")
	@Column(name = "remark")
	private String remark;

	@ApiModelProperty(value = "阶段")
	@Column(name = "stage")
	private String stage;

	@ApiModelProperty(value = "文件数量")
	@Transient
	private Integer fileCount;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
