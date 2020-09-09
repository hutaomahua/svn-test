package com.lyht.business.doc.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;

@Table(name = "t_doc_directory")
@Entity
@ApiModel(value = "档案目录分类(树形结构)")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class DocDirectory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 内码
	 */
	private String nm;
	/**
	 * 分类编码
	 */
	@Column(name = "sub_code")
	private String subCode;
	/**
	 * 分类名称
	 */
	@Column(name = "sub_name")
	private String subName;

	/**
	 * 所属档案分类
	 */
	@Column(name = "doc_type")
	private String docType;

	/**
	 * 短编码
	 */
	@Column(name = "s_code")
	private String sCode;
	/**
	 * 全编码
	 */
	@Column(name = "f_code")
	private String fCode;
	/**
	 * 上级id
	 */
	@Column(name = "super_id")
	private Integer superId;
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 子级
	 */
	@Transient
	private List<DocDirectory> children;

	public DocDirectory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDocType() {
		return docType;
	}

	public List<DocDirectory> getChildren() {
		return children;
	}

	public void setChildren(List<DocDirectory> children) {
		this.children = children;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public DocDirectory(String nm, String subCode, String subName, String sCode, String fCode, Integer superId,
			String remark) {
		this.nm = nm;
		this.subCode = subCode;
		this.subName = subName;
		this.sCode = sCode;
		this.fCode = fCode;
		this.superId = superId;
		this.remark = remark;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getsCode() {
		return sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public String getfCode() {
		return fCode;
	}

	public void setfCode(String fCode) {
		this.fCode = fCode;
	}

	public Integer getSuperId() {
		return superId;
	}

	public void setSuperId(Integer superId) {
		this.superId = superId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
