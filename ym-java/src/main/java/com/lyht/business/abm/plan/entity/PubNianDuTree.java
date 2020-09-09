package com.lyht.business.abm.plan.entity;

import java.util.List;


public class PubNianDuTree {


  	private  String key;
    private  String value;
    private  String nm;
    private  String title;
    private  String fCode;
    private String SuperId;
    
    
    private String unitNm;
    private String taskName;
    private String budgetWan;
    private String completTaskNumber;
    private String sumTaskNumber;
    private String sketch;
    private String capitalTwo;
    private String lastyearCapitalTwo;
    private String newCapital;

    private List<PubNianDuTree> children;

    
    public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

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

    public List<PubNianDuTree> getChildren() {
        return children;
    }

    public void setChildren(List<PubNianDuTree> children) {
        this.children = children;
    }

	public String getUnitNm() {
		return unitNm;
	}

	public void setUnitNm(String unitNm) {
		this.unitNm = unitNm;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getBudgetWan() {
		return budgetWan;
	}

	public void setBudgetWan(String budgetWan) {
		this.budgetWan = budgetWan;
	}

	public String getCompletTaskNumber() {
		return completTaskNumber;
	}

	public void setCompletTaskNumber(String completTaskNumber) {
		this.completTaskNumber = completTaskNumber;
	}

	public String getSumTaskNumber() {
		return sumTaskNumber;
	}

	public void setSumTaskNumber(String sumTaskNumber) {
		this.sumTaskNumber = sumTaskNumber;
	}

	public String getSketch() {
		return sketch;
	}

	public void setSketch(String sketch) {
		this.sketch = sketch;
	}

	public String getCapitalTwo() {
		return capitalTwo;
	}

	public void setCapitalTwo(String capitalTwo) {
		this.capitalTwo = capitalTwo;
	}

	public String getLastyearCapitalTwo() {
		return lastyearCapitalTwo;
	}

	public void setLastyearCapitalTwo(String lastyearCapitalTwo) {
		this.lastyearCapitalTwo = lastyearCapitalTwo;
	}

	public String getNewCapital() {
		return newCapital;
	}

	public void setNewCapital(String newCapital) {
		this.newCapital = newCapital;
	}

	
	    
}
