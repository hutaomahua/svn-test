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
 * 土地
 * 
 * @author Administrator
 *
 */
@ApiModel(description = "土地")
@Data
@Entity
@Table(name = "t_info_land_impl")
public class AbmLandEntity {

	/**
	 * 主键id
	 */
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

	@ApiModelProperty(value = "征地范围")
	@Column(name = "scope")
	private String scope;

	@ApiModelProperty(value = "权属人编码")
	@Column(name = "owner_nm")
	private String ownerNm;

	@ApiModelProperty(value = "地块编号")
	@Column(name = "land_nm")
	private String landNm;

	@ApiModelProperty(value = "面积")
	@Column(name = "area")
	private BigDecimal area;

	@ApiModelProperty(value = "单位")
	@Column(name = "unit")
	private String unit;

	@ApiModelProperty(value = "经度")
	@Column(name = "lgtd")
	private String lgtd;

	@ApiModelProperty(value = "纬度")
	@Column(name = "lttd")
	private String lttd;

	@ApiModelProperty(value = "高程")
	@Column(name = "altd")
	private String altd;

	@ApiModelProperty(value = "图幅号")
	@Column(name = "in_map")
	private String inMap;

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

	@ApiModelProperty(value = "一级分类")
	@Column(name = "type_one")
	private String typeOne;

	@ApiModelProperty(value = "二级分类")
	@Column(name = "type_two")
	private String typeTwo;

	@ApiModelProperty(value = "三级分类")
	@Column(name = "type_three")
	private String typeThree;

	@ApiModelProperty(value = "大类")
	@Column(name = "all_type")
	private String allType;

	@ApiModelProperty(value = "权属人身份证")
	@Column(name = "id_card")
	private String idCard;

	@ApiModelProperty(value = "land_project_nm")
	@Column(name = "land_project_nm")
	private String landProjectNm;

	@ApiModelProperty(value = "文件数量")
	@Transient
	private Integer fileCount;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
