package com.lyht.business.tab.entity;

import javax.persistence.*;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "查询配置表单")
@Entity
@Table(name = "sys_config_search")
public class ConfigSearchEntity {

	@ApiModelProperty(value = "查询配置表单--唯一ID（新增不填，修改必填）")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ApiModelProperty(value = "查询配置表单--唯一内码（新增不填，修改必填）")
	private String nm;

	@ApiModelProperty(value = "查询配置表单--input框类型(textAcc：精确查询输入框，text：模糊查询输入框，select：下拉框，selectIn：查询下拉框，date：日期输入选择框，datetime：日期时间输入选择框)，必选")
	@Column(name = "config_input_type")
	private String configInputType;

	@ApiModelProperty(value = "查询配置表单--input框数据来源接口（仅下拉框必填）")
	@Column(name = "config_api_path")
	private String configApiPath;

	@ApiModelProperty(value = "查询配置表单--input框接口参数（仅下拉框可填，必须为json格式）")
	@Column(name = "config_api_param")
	private String configApiParam;

	@ApiModelProperty(value = "查询配置表单--input框请求方式（GET：get请求，POST：post请求）（仅下拉框必填）")
	@Column(name = "config_api_method")
	private String configApiMethod;

	@ApiModelProperty(value = "查询配置表单--input框传入字段（下拉框传值字段，仅下拉框必填）")
	@Column(name = "config_param_field")
	private String configParamField;

	@ApiModelProperty(value = "查询配置表单--input框显示字段（下拉框显示字段，仅下拉框必填）")
	@Column(name = "config_show_field")
	private String configShowField;

	@ApiModelProperty(value = "查询配置表单--input框行占比（基础比率24，如：4，则代表4：24），非必填")
	@Column(name = "config_width_gdp")
	private Integer configWidthGdp;

	@ApiModelProperty(value = "查询配置表单--select框默认加载数据（非必填）")
	@Column(name = "config_select_data")
	private String configSelectData;

	@ApiModelProperty(value = "查询配置表单--input数据加载方式（非必填）")
	@Column(name = "config_load_type")
	private Integer configLoadType;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}