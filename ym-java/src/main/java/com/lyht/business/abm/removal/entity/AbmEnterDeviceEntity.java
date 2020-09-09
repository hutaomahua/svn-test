package com.lyht.business.abm.removal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "企业设备")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_info_device_impl")
public class AbmEnterDeviceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "唯一ID，修改必填")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ApiModelProperty(value = "内码")
	private String nm;

	@ApiModelProperty(value = "行政区")
	private String region;

	@ApiModelProperty(value = "项目编码")
	@Column(name = "project_nm")
	private String projectNm;

	@ApiModelProperty(value = "指标类型")
	@Column(name = "zblx")
	private String zblx;

	@ApiModelProperty(value = "征地范围")
	@Column(name = "scope")
	private String scope;

	@ApiModelProperty(value = "企事业编码")
	@Column(name = "enter_nm")
	private String enterNm;

	@ApiModelProperty(value = "资产编号")
	@Column(name = "asset_numbers")
	private String assetNumbers;

	@ApiModelProperty(value = "设备名称")
	@Column(name = "device_name")
	private String deviceName;

	@ApiModelProperty(value = "设备类型")
	@Column(name = "device_type")
	private String deviceType;

	@ApiModelProperty(value = "规格型号")
	@Column(name = "specifications")
	private String specifications;

	@ApiModelProperty(value = "制造厂家")
	@Column(name = "manufacturer")
	private String manufacturer;

	@ApiModelProperty(value = "联系电话")
	@Column(name = "phone_number")
	private String phoneNumber;

	@ApiModelProperty(value = "数量")
	@Column(name = "numbers")
	private Integer numbers;

	@ApiModelProperty(value = "生产时间")
	@Column(name = "production_time")
	private Date productionTime;

	@ApiModelProperty(value = "购置时间")
	@Column(name = "purchase_time")
	private Date purchaseTime;

	@ApiModelProperty(value = "是否需安装调试")
	@Column(name = "is_install")
	private String isInstall;

	@ApiModelProperty(value = "重量")
	@Column(name = "weight")
	private Float weight;

	@ApiModelProperty(value = "体积")
	@Column(name = "volume")
	private Float volume;

	@ApiModelProperty(value = "原价值")
	@Column(name = "init_value")
	private BigDecimal initValue;

	@ApiModelProperty(value = "净价值")
	@Column(name = "net_value")
	private BigDecimal netValue;

	@ApiModelProperty(value = "是否国家淘汰设备")
	@Column(name = "is_out")
	private String isOut;

	@ApiModelProperty(value = "是否可搬迁")
	@Column(name = "is_reloca")
	private String isReloca;

	@ApiModelProperty(value = "数据状态")
	@Column(name = "status")
	private String status;

	@ApiModelProperty(value = "备注")
	@Column(name = "remark")
	private String remark;

	@ApiModelProperty(value = "阶段")
	@Column(name = "stage")
	private String stage;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
