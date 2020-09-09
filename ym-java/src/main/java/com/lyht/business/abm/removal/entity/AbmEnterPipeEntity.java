package com.lyht.business.abm.removal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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

@ApiModel(description = "企业管道、沟槽及线路调查表")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_info_enter_pipe_impl")
public class AbmEnterPipeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

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

	@ApiModelProperty(value = "企事业编码")
	@Column(name = "enter_nm")
	private String enterNm;

	@ApiModelProperty(value = "名称")
	@Column(name = "name")
	private String name;

	@ApiModelProperty(value = "规格")
	@Column(name = "specifications")
	private String specifications;

	@ApiModelProperty(value = "材质")
	@Column(name = "material")
	private String material;

	@ApiModelProperty(value = "长度")
	@Column(name = "length")
	private BigDecimal length;

	@ApiModelProperty(value = "槽深")
	@Column(name = "depth")
	private BigDecimal depth;

	@ApiModelProperty(value = "沟宽/管径")
	@Column(name = "diameter")
	private BigDecimal diameter;

	@ApiModelProperty(value = "沟厚/壁厚")
	@Column(name = "ditch")
	private BigDecimal ditch;

	@ApiModelProperty(value = "建成年月")
	@Column(name = "built_year")
	private Date builtYear;

	@ApiModelProperty(value = "原价值")
	@Column(name = "init_value")
	private BigDecimal initValue;

	@ApiModelProperty(value = "净价值")
	@Column(name = "net_value")
	private BigDecimal netValue;

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
