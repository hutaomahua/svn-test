package com.lyht.business.info.entity;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "企事业单位")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "t_info_enterprise")
public class InfoEnterpriseEntity {

	@ApiModelProperty(value = "唯一ID(新增不填，修改必填)")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ApiModelProperty(value = "唯一内码（新增不填，修改必填）")
	private String nm;

	@ApiModelProperty(value = "名称")
	@Column(name = "name")
	private String name;
	
	@ApiModelProperty(value = "阶段")
	@Column(name = "stage")
	private String stage;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}