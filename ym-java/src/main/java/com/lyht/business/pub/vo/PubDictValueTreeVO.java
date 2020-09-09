package com.lyht.business.pub.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

import com.alibaba.fastjson.JSON;

@ApiModel(description = "字典--树")
@Data
public class PubDictValueTreeVO {

	@ApiModelProperty(value = "唯一ID，修改必填")
	private Integer id;

	@ApiModelProperty(value = "唯一内码，修改必填（关联字段）")
	private String nm;

	@ApiModelProperty(value = "自定义编码")
	private String code;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "备注")
	private String memo;

	@ApiModelProperty(value = "状态")
	private Integer flag;

	@ApiModelProperty(value = "字典分类")
	private String listnmSysDictCate;

	@ApiModelProperty(value = "父级nm")
	private String parentNm;

	@ApiModelProperty(value = "排序")
	private Integer sorted;

	@ApiModelProperty(value = "级别")
	private Integer levels;

	@ApiModelProperty(value = "子集")
	private List<PubDictValueTreeVO> children;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}