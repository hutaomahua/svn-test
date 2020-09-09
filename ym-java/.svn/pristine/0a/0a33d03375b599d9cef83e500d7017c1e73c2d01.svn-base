package com.lyht.business.abm.removal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "生产安置可研")
@Entity
@Table(name = "t_abm_move_ky")
public class MoveKyEntity implements Serializable{
	
    private static final long serialVersionUID = 1L;

	  /**
     * 主键id
     */
    @ApiModelProperty(value = "唯一ID，修改必填")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    
    @ApiModelProperty(value = "内码")
    private String nm;
    
    
    @ApiModelProperty(value = "行政区")
    private String region;
    
    @ApiModelProperty(value = "方案")
    private String pian;
    
    @ApiModelProperty(value = "科研搬迁人数")
    private String number_ky;
    
    @ApiModelProperty(value = "实施搬迁人数")
    private String number_ss;
    
    @ApiModelProperty(value = "可研土地数量")
    private String land_ky;
    
    @ApiModelProperty(value = "实施土地数量")
    private String land_ss;

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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPian() {
		return pian;
	}

	public void setPian(String pian) {
		this.pian = pian;
	}

	public String getNumber_ky() {
		return number_ky;
	}

	public void setNumber_ky(String number_ky) {
		this.number_ky = number_ky;
	}

	public String getNumber_ss() {
		return number_ss;
	}

	public void setNumber_ss(String number_ss) {
		this.number_ss = number_ss;
	}

	public String getLand_ky() {
		return land_ky;
	}

	public void setLand_ky(String land_ky) {
		this.land_ky = land_ky;
	}

	public String getLand_ss() {
		return land_ss;
	}

	public void setLand_ss(String land_ss) {
		this.land_ss = land_ss;
	}
    
    
    

}
