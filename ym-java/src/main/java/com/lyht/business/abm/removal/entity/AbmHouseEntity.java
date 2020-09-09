package com.lyht.business.abm.removal.entity;

import java.math.BigDecimal;

import javax.persistence.*;


import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 房屋
 * 
 * @author k
 *
 */
@ApiModel(description = " 房屋")
@Data
@Entity
@Table(name = "t_info_houses_impl")
public class AbmHouseEntity{
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

	@ApiModelProperty(value = "指标类型")
	@Column(name = "zblx")
	private String zblx;

	@ApiModelProperty(value = "征地范围")
	@Column(name = "scope")
	private String scope;

	@ApiModelProperty(value = "权属人编码")
	@Column(name = "owner_nm")
	private String ownerNm;

	@ApiModelProperty(value = "栋数")
	@Column(name = "house_number")
	private Integer houseNumber;

	@ApiModelProperty(value = "用途")
	@Column(name = "purpose")
	private String purpose;

	@ApiModelProperty(value = "结构类型")
	@Column(name = "structure_type")
	private String structureType;

	@ApiModelProperty(value = "层数")
	@Column(name = "layer_number")
	private Integer layerNumber;

	@ApiModelProperty(value = "层高(m)")
	@Column(name = "layer_height")
	private BigDecimal layerHeight;

	@ApiModelProperty(value = "形状")
	@Column(name = "shape")
	private String shape;

	@ApiModelProperty(value = "地址")
	@Column(name = "address")
	private BigDecimal address;

	@ApiModelProperty(value = "长")
	@Column(name = "longs")
	private BigDecimal longs;

	@ApiModelProperty(value = "宽")
	@Column(name = "width")
	private BigDecimal width;

	@ApiModelProperty(value = "面积(㎡)")
	@Column(name = "area")
	private BigDecimal area;

	@ApiModelProperty(value = "装修等级")
	@Column(name = "decorate_grade")
	private String decorateGrade;

	@ApiModelProperty(value = "道路里程")
	@Column(name = "road_mileage")
	private String roadMileage;

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

	@ApiModelProperty(value = "文件数量")
	@Transient
	private Integer fileCount;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
