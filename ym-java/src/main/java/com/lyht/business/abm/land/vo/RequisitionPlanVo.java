package com.lyht.business.abm.land.vo;

import io.swagger.annotations.ApiModel;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

@ApiModel(description = "周报汇总")
public class RequisitionPlanVo {

    private String region;
    private String taskName;
    private String taskType;
    
    
    

    public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

   
}
