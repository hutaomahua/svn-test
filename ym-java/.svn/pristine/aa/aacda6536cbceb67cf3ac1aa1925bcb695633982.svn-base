package com.lyht.business.abm.removal.vo;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(description = "年度计划调整台帐")
public class MoveIdVo implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "权属人姓名")
    private String name;
    
    @ApiModelProperty(value = "权属人nm")
    private String home;

    @ApiModelProperty(value = "权属人姓名")
    private Integer state;
    @ApiModelProperty(value = "权属人姓名")
    private String region;

    @ApiModelProperty(value = "下一户", required = true)
    private Integer nextCount;
    @ApiModelProperty(value = "是否界定", required = true)
    private Integer isno;

    
    
   

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getNextCount() {
        return nextCount;
    }

    public void setNextCount(Integer nextCount) {
        this.nextCount = nextCount;
    }

    public Integer getIsno() {
        return isno;
    }

    public void setIsno(Integer isno) {
        this.isno = isno;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
