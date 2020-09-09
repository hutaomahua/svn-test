package com.lyht.business.doc.pojo;

import java.util.List;

public class DocDirectoryVO {
	
	private Integer key;
	
	private Integer value;
	
	private String title;
	
	private List<DocDirectoryVO> children;

	public Integer getKey() {
		return key;
	}

	public Integer getValue() {
		return value;
	}

	public String getTitle() {
		return title;
	}

	public List<DocDirectoryVO> getChildren() {
		return children;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setChildren(List<DocDirectoryVO> children) {
		this.children = children;
	}
	
	
	
}
