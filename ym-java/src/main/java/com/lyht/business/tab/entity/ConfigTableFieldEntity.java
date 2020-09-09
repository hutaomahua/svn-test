package com.lyht.business.tab.entity;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "系统表字段注册表")
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "sys_config_table_field")
public class ConfigTableFieldEntity {

	@ApiModelProperty(value = "唯一ID（新增不填，修改必填）")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ApiModelProperty(value = "唯一内码（新增不填，修改必填）")
	private String nm;

//	@ApiModelProperty(value = "是否主键（不开发放该字段，0：否，1：是）")
//	@Column(name = "config_field_isid")
//	private Integer configFieldIsid;

	@ApiModelProperty(value = "配置表id（必填）")
	@Column(name = "config_table_id")
	private Integer configTableId;

//	@ApiModelProperty(value = "配置表名称（必填）")
//	@Column(name = "config_table_name")
//	private String configTableName;

	@ApiModelProperty(value = "字段名称（小写英文+下划线组合，必填）")
	@Column(name = "config_field_name")
	private String configFieldName;

	@ApiModelProperty(value = "字段显示名称（页面显示名称，必填）")
	@Column(name = "config_field_display_name")
	private String configFieldDisplayName;

	@ApiModelProperty(value = "字段数据库类型（int：整型数字，decimal：小数，varchar：字符串，date：日期，datetime：日期时间），必填；默认为字符串varchar")
	@Column(name = "config_field_type")
	private String configFieldType;

	@ApiModelProperty(value = "能否为空（0：否，1：是；默认为1）")
	@Column(name = "config_field_isempty")
	private Integer configFieldIsempty;

	@ApiModelProperty(value = "是否显示（0：否，1：是；默认为1）")
	@Column(name = "config_field_show")
	private Integer configFieldShow;

	@ApiModelProperty(value = "是否可编辑（0：否（隐藏编辑配置表单），1：是（展开编辑配置表单）；默认为0）")
	@Column(name = "config_field_edit")
	private Integer configFieldEdit;

	@ApiModelProperty(value = "是否可查询（0：否（隐藏查询配置表单），1：是（展开查询配置表单）；默认为0）")
	@Column(name = "config_field_search")
	private Integer configFieldSearch;

	@ApiModelProperty(value = "列宽")
	@Column(name = "config_field_width")
	private Integer configFieldWidth;

	@ApiModelProperty(value = "显示字符数（页面显示字段的长度，多余的用......代替）")
	@Column(name = "config_field_show_size")
	private Integer configFieldShowSize;

	@ApiModelProperty(value = "字段默认值（不能大于字段总长度，如果是小数，小数位数长度不能超长）")
	@Column(name = "config_field_default")
	private String configFieldDefault;

	@ApiModelProperty(value = "是否唯一（0：否，1：是；默认为0）")
	@Column(name = "config_field_unique")
	private Integer configFieldUnique;

	@ApiModelProperty(value = "数据库长度(字段总长度。如果是小数，则为包含小数位数的总长度)，字符串、小数，非必填")
	@Column(name = "config_field_length")
	private Integer configFieldLength;

	@ApiModelProperty(value = "数据库小数点长度（小数位数长度），小数，非必填")
	@Column(name = "config_field_double")
	private Integer configFieldDouble;

	@ApiModelProperty(value = "字段状态（0：无效，1：有效），非页面处理字段，不填")
	@Column(name = "config_field_state")
	private Integer configFieldState;

	@ApiModelProperty(value = "是否外键（0：否（隐藏外键配置字段），1：是（展开外键配置字段）；默认为0）")
	@Column(name = "field_is_fk")
	private Integer fieldIsFk;

	@ApiModelProperty(value = "外键配置字段--关联表字段名（小写英文+下划线组合），选择外键后必填")
	@Column(name = "field_fk_name")
	private String fieldFkName;

	@ApiModelProperty(value = "外键配置字段--关联表名称（小写英文+下划线组合），选择外键后必填")
	@Column(name = "field_fk_table")
	private String fieldFkTable;

	@ApiModelProperty(value = "外键配置字段--关联表显示字段（外键关联表显示字段，小写英文+下划线组合），选择外键后必填")
	@Column(name = "field_fk_show_name")
	private String fieldFkShowName;

	@ApiModelProperty(value = "字段显示名附加信息(如：㎡)，非必填")
	@Column(name = "config_field_display_name_fujia")
	private String configFieldDisplayNameFujia;

	@ApiModelProperty(value = "字段排序")
	@Column(name = "sorted")
	private Integer sorted;

	@ApiModelProperty(value = "编辑配置表单，编辑必填")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "config_field_form_id", referencedColumnName = "id")
	private ConfigFormEntity configFormEntitys;

	@ApiModelProperty(value = "查询配置表单，查询必填")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "config_field_search_id", referencedColumnName = "id")
	private ConfigSearchEntity configSearchEntity;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}