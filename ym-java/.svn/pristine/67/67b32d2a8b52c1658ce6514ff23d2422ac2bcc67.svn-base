package com.lyht.business.pub.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "图层数据")
@Entity
@Table(name = "pub_layer")
public class PubLayerEntity{

	@ApiModelProperty(value = "唯一ID，修改必填")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ApiModelProperty(value = "搜索字段名称")
	@Column(name = "name")
	private String name;

	@ApiModelProperty(value = "所在行政区")
	@Column(name = "region_name")
	private String regionName;

	@ApiModelProperty(value = "经度")
	@Column(name = "lng")
	private Double lng;

	@ApiModelProperty(value = "纬度")
	@Column(name = "lat")
	private Double lat;

	@ApiModelProperty(value = "高程")
	@Column(name = "elev")
	private Double elev;

	@ApiModelProperty(value = "其它信息")
	@Column(name = "extra")
	private String extra;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getElev() {
		return elev;
	}

	public void setElev(Double elev) {
		this.elev = elev;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}