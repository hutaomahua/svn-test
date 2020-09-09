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
@Table(name = "t_abm_late_population")
@ApiModel(description = "后期扶持人口")
public class LatePopulationEntity implements Serializable{
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
	     
	     @ApiModelProperty(value = "水库")
	     @Column(name = "reservoir")
	     private String reservoir;
	     
	     @ApiModelProperty(value = "水库单位")
	     @Column(name = "reservoir_unit")
	     private String reservoirUnit;
	     
	     @ApiModelProperty(value = "人口数")
	     @Column(name = "people_num")
	     private Integer peopleNum;
	     
	     @ApiModelProperty(value = "搬迁时间")
	     @Column(name = "move_date")
	     private String moveDate;
	     
	     @ApiModelProperty(value = "搬迁地址")
	     @Column(name = "move_addr")
	     private String moveAddr;
	     
	     @ApiModelProperty(value = "迁出地址")
	     @Column(name = "move_qcAddr")
	     private String moveQcAddr;
	     
	     @ApiModelProperty(value = "核实原因")
	     @Column(name = "cause")
	     private String cause;
	     
	     @ApiModelProperty(value = "村民意见")
	     @Column(name = "village")
	     private String village;
	     
	     @ApiModelProperty(value = "村民负责人")
	     @Column(name = "village_bear")
	     private String villageBear;
	     
	     @ApiModelProperty(value = "委员会意见")
	     @Column(name = "committee")
	     private String committee;
	     
	     @ApiModelProperty(value = "委员会")
	     @Column(name = "committee_bear")
	     private String committeeBear;
	     
	     @ApiModelProperty(value = "政府意见")
	     @Column(name = "organ")
	     private String organ;
	     
	     @ApiModelProperty(value = "政府负责人")
	     @Column(name = "organ_bear")
	     private String organBear;
	     
	     @ApiModelProperty(value = "政府时间")
	     @Column(name = "organ_date")
	     private String organDate;
	     
	     @ApiModelProperty(value = "委员会时间")
	     @Column(name = "committee_date")
	     private String committeeDate;
	     
	     @ApiModelProperty(value = "村民时间")
	     @Column(name = "village_date")
	     private String villageDate;
	     
	     
	     
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



		public String getRegionName() {
			return regionName;
		}



		public void setRegionName(String regionName) {
			this.regionName = regionName;
		}



		public String getReservoir() {
			return reservoir;
		}



		public void setReservoir(String reservoir) {
			this.reservoir = reservoir;
		}



		public String getReservoirUnit() {
			return reservoirUnit;
		}



		public void setReservoirUnit(String reservoirUnit) {
			this.reservoirUnit = reservoirUnit;
		}



		public Integer getPeopleNum() {
			return peopleNum;
		}



		public void setPeopleNum(Integer peopleNum) {
			this.peopleNum = peopleNum;
		}



		public String getMoveDate() {
			return moveDate;
		}



		public void setMoveDate(String moveDate) {
			this.moveDate = moveDate;
		}



		public String getMoveAddr() {
			return moveAddr;
		}



		public void setMoveAddr(String moveAddr) {
			this.moveAddr = moveAddr;
		}



		public String getCause() {
			return cause;
		}



		public void setCause(String cause) {
			this.cause = cause;
		}



		public String getVillage() {
			return village;
		}



		public void setVillage(String village) {
			this.village = village;
		}



		public String getVillageBear() {
			return villageBear;
		}



		public void setVillageBear(String villageBear) {
			this.villageBear = villageBear;
		}



		public String getCommittee() {
			return committee;
		}



		public void setCommittee(String committee) {
			this.committee = committee;
		}



		public String getCommitteeBear() {
			return committeeBear;
		}



		public void setCommitteeBear(String committeeBear) {
			this.committeeBear = committeeBear;
		}



		public String getOrgan() {
			return organ;
		}



		public void setOrgan(String organ) {
			this.organ = organ;
		}



		public String getOrganBear() {
			return organBear;
		}



		public void setOrganBear(String organBear) {
			this.organBear = organBear;
		}



		public String getOrganDate() {
			return organDate;
		}



		public void setOrganDate(String organDate) {
			this.organDate = organDate;
		}



		public String getCommitteeDate() {
			return committeeDate;
		}



		public void setCommitteeDate(String committeeDate) {
			this.committeeDate = committeeDate;
		}



		



		public String getVillageDate() {
			return villageDate;
		}



		public void setVillageDate(String villageDate) {
			this.villageDate = villageDate;
		}



		public String getMoveQcAddr() {
			return moveQcAddr;
		}



		public void setMoveQcAddr(String moveQcAddr) {
			this.moveQcAddr = moveQcAddr;
		}



		@Override
	     public String toString() {
	         return JSON.toJSONString(this);
	     }
}
