package com.lyht.business.pub.vo;

public interface PubDictValueVO {
	/**
	 * 主键
	 */
	Integer getId();

	/**
	 * 内码
	 */
	String getNm();

	/**
	 * 编码
	 */
	String getCode();

	/**
	 * 名称
	 */
	String getName();

	/**
	 * 备注
	 */
	String getMemo();

	/**
	 * 状态
	 */
	Integer getFlag();

	/**
	 * 字典分类
	 */
	String getListnmSysDictCate();

	/**
	 * 父级编码
	 */
	String getParentNm();

	/**
	 * 排序
	 */
	Integer getSorted();

	/**
	 * 级别
	 */
	Integer getLevels();

}