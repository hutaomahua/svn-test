package com.lyht.business.tab.entity;

import javax.persistence.*;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "系统表注册表")
@Entity
@Table(name = "sys_config_table")
public class ConfigTableEntity {

	@ApiModelProperty(value = "唯一ID（新增不填，修改必填）")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ApiModelProperty(value = "唯一内码（新增不填，修改必填）")
	private String nm;

	@ApiModelProperty(value = "配置表名(英文小写+下划线组合，必填)")
	@Column(name = "config_table_name")
	private String configTableName;

	@ApiModelProperty(value = "配置表中文名称（必填）")
	@Column(name = "config_table_display_name")
	private String configTableDisplayName;

	@ApiModelProperty(value = "配置表说明（非必填）")
	@Column(name = "config_table_remark")
	private String configTableRemark;

	@ApiModelProperty(value = "是否有附件(0：没有附件，1：有附件；默认为0)")
	@Column(name = "config_isFile")
	private Integer configIsfile;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}