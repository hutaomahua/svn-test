package com.lyht.business.abm.removal.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 零星数木
 * 
 * @author Administrator
 *
 */
@ApiModel(description = "零星数木")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_info_trees_impl")
public class AbmTreeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
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

	@ApiModelProperty(value = "品种")
	@Column(name = "varieties")
	private String varieties;

	@ApiModelProperty(value = "规格")
	@Column(name = "specifications")
	private String specifications;

	@ApiModelProperty(value = "单位")
	@Column(name = "unit")
	private String unit;

	@ApiModelProperty(value = "树龄")
	@Column(name = "tree_age")
	private Integer treeAge;

	@ApiModelProperty(value = "树高")
	@Column(name = "tree_height")
	private BigDecimal treeHeight;

	@ApiModelProperty(value = "胸径")
	@Column(name = "breast_height")
	private Integer breastHeight;

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

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
