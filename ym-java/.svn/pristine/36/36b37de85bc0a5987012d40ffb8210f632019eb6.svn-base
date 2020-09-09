package com.lyht.business.pub.vo;

import com.alibaba.fastjson.JSON;

import lombok.Data;

import java.util.List;

/**
 * @author HuangAn
 * @date 2019/9/23 11:38
 */
@Data
public class PubDictValueTree {
	private String code;
	private String name;
	private String nm;
	private String label;
	private String value;
	private Boolean isLeaf;
	private String parentNm;
	private List<PubDictValueTree> children;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}