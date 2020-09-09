package com.lyht.business.tab.vo;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.lyht.business.tab.entity.ConfigFormEntity;
import com.lyht.business.tab.entity.ConfigSearchEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "系统表字段注册表")
public class ConfigTableFieldVO {
	
	@ApiModelProperty(value = "唯一ID（新增不填，修改必填）")
	private Integer id;

	@ApiModelProperty(value = "唯一内码（新增不填，修改必填）")
	private String nm;

	@ApiModelProperty(value = "配置表id（必填）")
	private Integer configTableId;

	@ApiModelProperty(value = "字段名称（小写英文+下划线组合，必填）")
	private String configFieldName;

	@ApiModelProperty(value = "字段显示名称（页面显示名称，必填）")
	private String configFieldDisplayName;

	@ApiModelProperty(value = "字段数据库类型（int：整型数字，decimal：小数，varchar：字符串，date：日期，datetime：日期时间），必填；默认为字符串varchar")
	private String configFieldType;

	@ApiModelProperty(value = "能否为空（0：否，1：是；默认为1）")
	private Integer configFieldIsempty;

	@ApiModelProperty(value = "是否显示（0：否，1：是；默认为1）")
	private Integer configFieldShow;

	@ApiModelProperty(value = "是否可编辑（0：否（隐藏编辑配置表单），1：是（展开编辑配置表单）；默认为0）")
	private Integer configFieldEdit;

	@ApiModelProperty(value = "是否可查询（0：否（隐藏查询配置表单），1：是（展开查询配置表单）；默认为0）")
	private Integer configFieldSearch;

	@ApiModelProperty(value = "列宽")
	private Integer configFieldWidth;
	
	@ApiModelProperty(value = "显示字符数（页面显示字段的长度，多余的用......代替）")
	private Integer configFieldShowSize;

	@ApiModelProperty(value = "字段默认值（不能大于字段总长度，如果是小数，小数位数长度不能超长）")
	private String configFieldDefault;
	
	@ApiModelProperty(value = "是否唯一（0：否，1：是；默认为0）")
	private Integer configFieldUnique;

	@ApiModelProperty(value = "数据库长度(字段总长度。如果是小数，则为包含小数位数的总长度)，字符串、小数，非必填")
	private Integer configFieldLength;

	@ApiModelProperty(value = "数据库小数点长度（小数位数长度），小数，非必填")
	private Integer configFieldDouble;

	@ApiModelProperty(value = "字段状态（0：无效，1：有效），非页面处理字段，不填")
	private Integer configFieldState;

	@ApiModelProperty(value = "是否外键（0：否（隐藏外键配置字段），1：是（展开外键配置字段）；默认为0）")
	private Integer fieldIsFk;

	@ApiModelProperty(value = "外键配置字段--关联表字段名（小写英文+下划线组合），选择外键后必填")
	private String fieldFkName;

	@ApiModelProperty(value = "外键配置字段--关联表名称（小写英文+下划线组合），选择外键后必填")
	private String fieldFkTable;

	@ApiModelProperty(value = "外键配置字段--关联表显示字段（外键关联表显示字段，小写英文+下划线组合），选择外键后必填")
	private String fieldFkShowName;

	@ApiModelProperty(value = "字段显示名附加信息(如：㎡)，非必填")
	private String configFieldDisplayNameFujia;
	
	@ApiModelProperty(value = "字段排序")
	private Integer sorted;
	
	@ApiModelProperty(value = "编辑配置表单，编辑必填")
	private ConfigFormEntity configFormEntitys;
	
	@ApiModelProperty(value = "查询配置表单，查询必填")
	private ConfigSearchEntity configSearchEntity;
	
	@ApiModelProperty(value = "下拉框数据")
	private List<Map<String, Object>> selectDataList;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}