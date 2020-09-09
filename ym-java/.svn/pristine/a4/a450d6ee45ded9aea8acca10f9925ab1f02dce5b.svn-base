package com.lyht.business.abm.removal.entity;

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

@ApiModel(description = "搬迁安置-人口核定")
@Entity
@Table(name = "t_abm_move_approve")
public class MoveApproveEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    
	  @ApiModelProperty(value = "唯一ID，修改必填")
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id", nullable = false)
	    private Integer id;

	    @ApiModelProperty(value = "内码")
	    private String nm;
  
	    @ApiModelProperty(value = "权属人nm")
	    @Column(name = "owner_nm", nullable = false)
	    private String ownerNm;
	    
	    @ApiModelProperty(value = "去向")
	    @Column(name = "to_where", nullable = false)
	    private String toWhere;
	    
	    @ApiModelProperty(value = "安置类型")
	    @Column(name = "place_type", nullable = false)
	    private String placeType;
	    
	    @ApiModelProperty(value = "安置名称")
	    @Column(name = "place_name", nullable = false)
	    private String placeName;
	    
	    @ApiModelProperty(value = "安置位置")
	    @Column(name = "place_addr", nullable = false)
	    private String placeAddr;

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

		public String getOwnerNm() {
			return ownerNm;
		}

		public void setOwnerNm(String ownerNm) {
			this.ownerNm = ownerNm;
		}

		public String getToWhere() {
			return toWhere;
		}

		public void setToWhere(String toWhere) {
			this.toWhere = toWhere;
		}

		public String getPlaceType() {
			return placeType;
		}

		public void setPlaceType(String placeType) {
			this.placeType = placeType;
		}

		public String getPlaceName() {
			return placeName;
		}

		public void setPlaceName(String placeName) {
			this.placeName = placeName;
		}

		public String getPlaceAddr() {
			return placeAddr;
		}

		public void setPlaceAddr(String placeAddr) {
			this.placeAddr = placeAddr;
		}
	    

		@Override
	    public String toString() {
	        return JSON.toJSONString(this);
	    }
}
