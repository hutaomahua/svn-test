package com.lyht.business.pub.entity;

import com.alibaba.fastjson.JSON;
import com.lyht.business.pub.vo.PubDictValueTree;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

/**
 * 作者： liuamang 脚本日期:2019年2月19日 23:23:38 说明: 字典
 */
@ApiModel(description = "字典")
@Entity
@Table(name = "pub_dict_value")
public class PubDictValue implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "唯一ID，修改必填")
	private Integer id;
	/**
	 * 内码
	 */
	@ApiModelProperty(value = "唯一内码，修改必填（关联字段）")
	private String nm;
	/**
	 * 编码
	 */
	@ApiModelProperty(value = "自定义编码")
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
	/**
	 * 字典分类
	 */
	@ApiModelProperty(value = "字典分类")
	private String listnmSysDictCate;
	/**
	 * 父级编码
	 */
	@ApiModelProperty(value = "父级nm")
	@Column(name = "parent_nm", nullable = false)
	private String parentNm;
	/**
	 * 排序
	 */
	private Integer sorted;
	/**
	 * 级别
	 */
	private Integer levels;

	private List<PubDictValueTree> children;

	/** default constructor */
	public PubDictValue() {

	}
	/** full constructor */

	// 属性get/set定义

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 内码
	 */
	@Column(name = "nm", nullable = false)
	public String getNm() {
		return this.nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	/**
	 * 编码
	 */
	@Column(name = "code", nullable = false)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 名称
	 */
	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 备注
	 */
	@Column(name = "memo")
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 状态
	 */
	@Column(name = "flag", nullable = false)
	public Integer getFlag() {
		return this.flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	/**
	 * 字典分类
	 */
	@Column(name = "listnm_sys_dict_cate")
	public String getListnmSysDictCate() {
		return this.listnmSysDictCate;
	}

	public void setListnmSysDictCate(String listnmSysDictCate) {
		this.listnmSysDictCate = listnmSysDictCate;
	}

	@Column(name = "parent_nm")
	public String getParentNm() {
		return parentNm;
	}

	public void setParentNm(String parentNm) {
		this.parentNm = parentNm;
	}

	@Column(name = "sorted")
	public Integer getSorted() {
		return sorted;
	}

	public void setSorted(Integer sorted) {
		this.sorted = sorted;
	}

	@Column(name = "level")
	public Integer getLevels() {
		return levels;
	}

	public void setLevels(Integer levels) {
		this.levels = levels;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	@Transient
	public List<PubDictValueTree> getChildren() {
		return children;
	}

	public void setChildren(List<PubDictValueTree> children) {
		this.children = children;
	}
}