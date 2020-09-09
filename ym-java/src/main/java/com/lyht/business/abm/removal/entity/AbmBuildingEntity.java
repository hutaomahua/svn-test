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
 * 附属建筑
 * 
 * @author k
 *
 */
@ApiModel(description = "附属建筑")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_info_building_impl")
public class AbmBuildingEntity {
	
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

	@ApiModelProperty(value = "规格")
	@Column(name = "specifications")
	private String specifications;

	@ApiModelProperty(value = "单位")
	@Column(name = "unit")
	private String unit;

	@ApiModelProperty(value = "长")
	@Column(name = "longs")
	private BigDecimal longs;

	@ApiModelProperty(value = "宽")
	@Column(name = "width")
	private BigDecimal width;

	@ApiModelProperty(value = "高")
	@Column(name = "height")
	private BigDecimal height;

	@ApiModelProperty(value = "面积(㎡)")
	@Column(name = "area")
	private BigDecimal area;

	@ApiModelProperty(value = "体积")
	@Column(name = "volume")
	private BigDecimal volume;

	@ApiModelProperty(value = "数量")
	@Column(name = "num")
	private BigDecimal num;

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
