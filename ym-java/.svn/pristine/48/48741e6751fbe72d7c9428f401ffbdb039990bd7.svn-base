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
@Table(name = "t_abm_late_people")
@ApiModel(description = "后期扶持人口(人)")
public class LatePeopleEntity {
	
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
     @Column(name = "regionName")
     private String regionName;
     
     
     @ApiModelProperty(value = "本人成分")
     @Column(name = "myself")
     private String myself;
     
     @ApiModelProperty(value = "出生年月")
     @Column(name = "years")
     private String years;
     
     
     @ApiModelProperty(value = "姓名")
     @Column(name = "name")
     private String name;
     
     @ApiModelProperty(value = "性别")
     @Column(name = "sex")
     private String sex;
     
     @ApiModelProperty(value = "户主")
     @Column(name = "huzhu")
     private String huzhu;
     
     @ApiModelProperty(value = "民族")
     @Column(name = "nation")
     private String nation;
     
     @ApiModelProperty(value = "联系方式")
     @Column(name = "phone")
     private String phone;
     
     @ApiModelProperty(value = "文化程度")
     @Column(name = "culture")
     private String culture;
     
     @ApiModelProperty(value = "婚姻")
     @Column(name = "marriage")
     private String marriage;
     
     @ApiModelProperty(value = "户主关系")
     @Column(name = "huzhu_type")
     private String huzhuType;
     
     @ApiModelProperty(value = "出生地")
     @Column(name = "addr")
     private String addr;
     
     @ApiModelProperty(value = "身份证")
     @Column(name = "id_card")
     private String idCard;
     
     @ApiModelProperty(value = "所属水库")
     @Column(name = "reservoir")
     private String reservoir;
     
     @ApiModelProperty(value = "水库类别")
     @Column(name = "reservoir_type")
     private String reservoirType;
     
     @ApiModelProperty(value = "水库单位")
     @Column(name = "reservoirUnit")
     private String reservoirUnit;
     
     @ApiModelProperty(value = "搬迁时间")
     @Column(name = "move_date")
     private String moveDate;
     
     @ApiModelProperty(value = "迁入地址")
     @Column(name = "move_addr")
     private String moveAddr;
     
     @ApiModelProperty(value = "迁出地址")
     @Column(name = "move_qcaddr")
     private String moveQcaddr;
     
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

	public String getName() {
		return name;
	}

	public String getSex() {
		return sex;
	}

	public String getHuzhu() {
		return huzhu;
	}

	public String getNation() {
		return nation;
	}

	public String getPhone() {
		return phone;
	}

	public String getCulture() {
		return culture;
	}

	public String getMarriage() {
		return marriage;
	}

	public String getHuzhuType() {
		return huzhuType;
	}

	public String getAddr() {
		return addr;
	}

	public String getIdCard() {
		return idCard;
	}

	public String getReservoir() {
		return reservoir;
	}

	public String getReservoirType() {
		return reservoirType;
	}

	public String getReservoirUnit() {
		return reservoirUnit;
	}

	public String getMoveDate() {
		return moveDate;
	}

	public String getMoveAddr() {
		return moveAddr;
	}

	public String getMoveQcaddr() {
		return moveQcaddr;
	}

	public String getRemark() {
		return remark;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setHuzhu(String huzhu) {
		this.huzhu = huzhu;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setCulture(String culture) {
		this.culture = culture;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public void setHuzhuType(String huzhuType) {
		this.huzhuType = huzhuType;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public void setReservoir(String reservoir) {
		this.reservoir = reservoir;
	}

	public void setReservoirType(String reservoirType) {
		this.reservoirType = reservoirType;
	}

	public void setReservoirUnit(String reservoirUnit) {
		this.reservoirUnit = reservoirUnit;
	}

	public void setMoveDate(String moveDate) {
		this.moveDate = moveDate;
	}

	public void setMoveAddr(String moveAddr) {
		this.moveAddr = moveAddr;
	}

	public void setMoveQcaddr(String moveQcaddr) {
		this.moveQcaddr = moveQcaddr;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
     
     
     
	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
	

	public String getMyself() {
		return myself;
	}

	public void setMyself(String myself) {
		this.myself = myself;
	}

	
	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	@Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
