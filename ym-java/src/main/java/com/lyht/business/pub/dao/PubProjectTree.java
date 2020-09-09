package com.lyht.business.pub.dao;

import java.util.List;


public class PubProjectTree {

	  	private  String key;
	    private  String value;
	    private  String title;
	    private  String fCode;
	    private String SuperId;
	    private String serialNumber;
	    private String danwei;
	    private String money;
	    private List<PubProjectTree> children;

	    public String getKey() {
	        return key;
	    }

	    public void setKey(String key) {
	        this.key = key;
	    }

	    public String getValue() {
	        return value;
	    }

	    public void setValue(String value) {
	        this.value = value;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public String getfCode() {
	        return fCode;
	    }

	    public void setfCode(String fCode) {
	        this.fCode = fCode;
	    }

	    public String getSuperId() {
	        return SuperId;
	    }

	    public void setSuperId(String superId) {
	        SuperId = superId;
	    }

	    public List<PubProjectTree> getChildren() {
	        return children;
	    }

	    public void setChildren(List<PubProjectTree> children) {
	        this.children = children;
	    }

		public String getSerialNumber() {
			return serialNumber;
		}

		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}

		public String getDanwei() {
			return danwei;
		}

		public void setDanwei(String danwei) {
			this.danwei = danwei;
		}

		public String getMoney() {
			return money;
		}

		public void setMoney(String money) {
			this.money = money;
		}
	    
}
