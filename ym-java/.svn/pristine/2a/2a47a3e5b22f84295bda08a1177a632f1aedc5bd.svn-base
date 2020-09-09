package com.lyht.business.abm.land.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "t_abm_schedule_details")
@ApiModel(description = "进度管理详情")
public class ReDeailsEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false )
    private Integer id;
  
    @ApiModelProperty(value = "内码")
    private String nm;
    
    
    @ApiModelProperty(value = "任务量")
    @Column(name = "sum_task_number")
    private String sum_task_number;
    
    @ApiModelProperty(value = "投资额")
    @Column(name = "sum_invest")
    private String sum_invest;
    
    @ApiModelProperty(value = "完成年月")
    @Column(name = "install_date")
    private String install_date;
    
    
    
    
    

	public String getInstall_date() {
		return install_date;
	}




	public void setInstall_date(String install_date) {
		this.install_date = install_date;
	}




	public Integer getId() {
		return id;
	}




	public String getNm() {
		return nm;
	}




	public String getSum_task_number() {
		return sum_task_number;
	}




	public String getSum_invest() {
		return sum_invest;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public void setNm(String nm) {
		this.nm = nm;
	}




	public void setSum_task_number(String sum_task_number) {
		this.sum_task_number = sum_task_number;
	}




	public void setSum_invest(String sum_invest) {
		this.sum_invest = sum_invest;
	}




	@Override
    public String toString() {
        return JSON.toJSONString(this);
    }


}
