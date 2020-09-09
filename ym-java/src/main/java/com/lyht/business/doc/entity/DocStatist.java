package com.lyht.business.doc.entity;

import com.alibaba.fastjson.JSON;

public class DocStatist {

	// 类型
	private String type;

	// 数量
	private Integer count;

	// 行政区域
	private String diming;

	public String getDiming() {
		return diming;
	}

	public void setDiming(String diming) {
		this.diming = diming;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}