package com.lyht.business.abm.land.entity;

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
@Table(name = "t_abm_late_county")
@ApiModel(description = "后期扶持人口(县)")
public class LateCountyEntity {
	 private static final long serialVersionUID = 1L;
	 

		@ApiModelProperty(value = "主键")
	    @Id
	    @GeneratedValue(strategy= GenerationType.IDENTITY)
	    @Column(name = "id" , nullable = false )
	    private Integer id;
	 	
	 	 @ApiModelProperty(value = "内码")
	     private String nm;
	   
	     @ApiModelProperty(value = "行政区域")
	     @Column(name = "region")
	     private String region;
	     
	     @ApiModelProperty(value = "行政区域名称")
	     @Column(name = "region_name")
	     private String regionName;
	     
	     @ApiModelProperty(value = "所属水库")
	     @Column(name = "reservoir")
	     private String reservoir;
	     
	     @ApiModelProperty(value = "水库单位")
	     @Column(name = "reservoir_unit")
	     private String reservoirUnit;
	     
	     @ApiModelProperty(value = "填报人")
	     @Column(name = "fill_name")
	     private String fillName;
	     
	     @ApiModelProperty(value = "填报单位")
	     @Column(name = "fill_date")
	     private String fillDate;
	     
	     @ApiModelProperty(value = "备注")
	     @Column(name = "remark")
	     private String remark;

	     
	     
	 	public Integer getId() {
			return id;
		}




		public String getNm() {
			return nm;
		}




		public String getRegion() {
			return region;
		}




		public String getRegionName() {
			return regionName;
		}




		public String getReservoir() {
			return reservoir;
		}




		public String getReservoirUnit() {
			return reservoirUnit;
		}




		public String getFillName() {
			return fillName;
		}




		




		public void setId(Integer id) {
			this.id = id;
		}




		public void setNm(String nm) {
			this.nm = nm;
		}




		public void setRegion(String region) {
			this.region = region;
		}




		public void setRegionName(String regionName) {
			this.regionName = regionName;
		}




		public void setReservoir(String reservoir) {
			this.reservoir = reservoir;
		}




		public void setReservoirUnit(String reservoirUnit) {
			this.reservoirUnit = reservoirUnit;
		}




		public void setFillName(String fillName) {
			this.fillName = fillName;
		}




		



		public String getFillDate() {
			return fillDate;
		}




		public void setFillDate(String fillDate) {
			this.fillDate = fillDate;
		}




		public String getRemark() {
			return remark;
		}




		public void setRemark(String remark) {
			this.remark = remark;
		}




		@Override
	    public String toString() {
	        return JSON.toJSONString(this);
	    }

}
