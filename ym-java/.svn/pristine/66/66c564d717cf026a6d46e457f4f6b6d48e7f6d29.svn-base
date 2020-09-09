package com.lyht.business.tab.vo;

import java.util.List;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "数据分类列表")
public class DatebaseFiledListVO {

	@ApiModelProperty(value = "查询条件")
	private List<ConfigTableFieldVO> searchList;

	@ApiModelProperty(value = "列表显示字段")
	private List<ConfigTableFieldVO> showList;

	@ApiModelProperty(value = "表单可编辑字段")
	private List<ConfigTableFieldVO> formList;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}