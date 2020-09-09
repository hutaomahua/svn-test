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
@Table(name = "t_abm_late_detail")
@ApiModel(description = "后期扶持人口(人)")
public class LateDetailEntity {
	
	 private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "主键")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false )
    private Integer id;
 	
 	 @ApiModelProperty(value = "内码")
     private String nm;
   
     
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
   
     
     @ApiModelProperty(value = "户主关系")
     @Column(name = "huzhu_type")
     private String huzhuType;
     
    
     
     @ApiModelProperty(value = "身份证")
     @Column(name = "id_card")
     private String idCard;
     
     
     @ApiModelProperty(value = "搬迁时间")
     @Column(name = "move_date")
     private String moveDate;
     
     @ApiModelProperty(value = "迁入地址")
     @Column(name = "move_addr")
     private String moveAddr;
     
     @ApiModelProperty(value = "迁出地址")
     @Column(name = "move_qcaddr")
     private String moveQcaddr;
     
    

	
	public Integer getId() {
		return id;
	}


	public String getNm() {
		return nm;
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


	public String getHuzhuType() {
		return huzhuType;
	}


	public String getIdCard() {
		return idCard;
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





	public void setId(Integer id) {
		this.id = id;
	}


	public void setNm(String nm) {
		this.nm = nm;
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


	public void setHuzhuType(String huzhuType) {
		this.huzhuType = huzhuType;
	}


	public void setIdCard(String idCard) {
		this.idCard = idCard;
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


	


	@Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
