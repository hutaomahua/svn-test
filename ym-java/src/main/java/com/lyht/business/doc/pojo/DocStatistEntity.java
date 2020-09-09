package com.lyht.business.doc.pojo;

import com.alibaba.fastjson.JSON;

public class DocStatistEntity {
	
    //行政区域
    private String diming;

	public String getDiming() {
		return diming;
	}

	public void setDiming(String diming) {
		this.diming = diming;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
    
}