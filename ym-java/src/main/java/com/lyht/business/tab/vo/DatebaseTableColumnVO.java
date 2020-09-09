package com.lyht.business.tab.vo;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "数据库表字段信息")
public class DatebaseTableColumnVO {

	@ApiModelProperty(value = "数据库名")
	private String databaseName;

	@ApiModelProperty(value = "表名")
	private String tableName;

	@ApiModelProperty(value = "列名")
	private String columnName;

	@ApiModelProperty(value = "默认值")
	private String columnDefault;

	@ApiModelProperty(value = "是否为空（YES,NO）")
	private String isNullable;

	@ApiModelProperty(value = "数据类型")
	private String dataType;

	@ApiModelProperty(value = "字符长度")
	private String stringlength;

	@ApiModelProperty(value = "数字总长度")
	private String numericPrecision;

	@ApiModelProperty(value = "小数位数长度")
	private String numericScale;

	@ApiModelProperty(value = "索引类型（PRI,UNI）")
	private String columnKey;

	@ApiModelProperty(value = "字段描述")
	private String columnComment;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}