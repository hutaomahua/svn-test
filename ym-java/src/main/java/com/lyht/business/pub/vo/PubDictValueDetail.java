package com.lyht.business.pub.vo;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "字典详情表")
public class PubDictValueDetail {
	@ApiModelProperty(value = "查询字段")
	private String searchName;
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "唯一ID")
	private Integer id;
	/**
	 * 内码
	 */
	@ApiModelProperty(value = "内码")
	private String nm;
	/**
	 * 编码
	 */
	@ApiModelProperty(value = "编码")
	private String code;
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String memo;
	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	private Integer flag;
	
	@ApiModelProperty(value = "分类名称")
	private String dictName;

	/**
	 * 字典分类
	 */
	private String listnm_sys_dict_cate;
	
	/**
     * 父级编码
     */
    private String parent_nm;
    /**
     * 排序
     */
    private Integer sorted;
	/**
	 * 级别
	 */
	private Integer level;
	/**
	 * 排序
	 */
	//private List children;

	private String parentName;

	private String parentCode;

	private String parentNames;

	public String getParentNames() {
		return parentNames;
	}

	public void setParentNames(String parentNames) {
		this.parentNames = parentNames;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getListnm_sys_dict_cate() {
		return listnm_sys_dict_cate;
	}

	public void setListnm_sys_dict_cate(String listnm_sys_dict_cate) {
		this.listnm_sys_dict_cate = listnm_sys_dict_cate;
	}

	public String getParent_nm() {
		return parent_nm;
	}

	public void setParent_nm(String parent_nm) {
		this.parent_nm = parent_nm;
	}

	public Integer getSorted() {
		return sorted;
	}

	public void setSorted(Integer sorted) {
		this.sorted = sorted;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	//	public List getChildren() {
//		return children;
//	}
//
//	public void setChildren(List children) {
//		this.children = children;
//	}
}
