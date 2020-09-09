package com.lyht.business.abm.paymentManagement.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *作者： 阎晓辉
 *脚本日期:2020年2月19日 1:26:41
 *说明: 
*/
@Entity
@Table(name = "t_abm_payment_info")
@ApiModel(description = "兑付信息")
public class PaymentInfoEntity {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false )
    private Integer id;

    @Column(name = "nm"  )
    private String nm;
    
    @Column(name = "owner_nm"  )
    private String ownerNm;
    
    @Column(name = "protocol_code"  )
    private String protocolCode;

    //协议类型  0：补偿协议  1：资金代管协议
    @Column(name = "protocol_type"  )
    private Integer protocolType;
    
    @Column(name = "protocol_amount"  )
    private BigDecimal protocolAmount;
    
    @Column(name = "protocol_payed"  )
    private BigDecimal protocolPayed;
    
    @Column(name = "protocol_real_payed"  )
    private BigDecimal protocolRealPayed;
    
    @Column(name = "protocol_surplus"  )
    private BigDecimal protocolSurplus;
    
    @Column(name = "batch"  )
    private Integer batch;

    @Column(name = "is_next"  )
    private Integer isnext;
    
    /** default constructor */
    public PaymentInfoEntity() {
    
    }

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

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public Integer getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(Integer protocolType) {
		this.protocolType = protocolType;
	}

	public BigDecimal getProtocolAmount() {
		return protocolAmount;
	}

	public void setProtocolAmount(BigDecimal protocolAmount) {
		this.protocolAmount = protocolAmount;
	}

	public BigDecimal getProtocolPayed() {
		return protocolPayed;
	}

	public void setProtocolPayed(BigDecimal protocolPayed) {
		this.protocolPayed = protocolPayed;
	}

	public BigDecimal getProtocolRealPayed() {
		return protocolRealPayed;
	}

	public void setProtocolRealPayed(BigDecimal protocolRealPayed) {
		this.protocolRealPayed = protocolRealPayed;
	}

	public BigDecimal getProtocolSurplus() {
		return protocolSurplus;
	}

	public void setProtocolSurplus(BigDecimal protocolSurplus) {
		this.protocolSurplus = protocolSurplus;
	}

	public Integer getBatch() {
		return batch;
	}

	

	public Integer getIsnext() {
		return isnext;
	}

	public void setIsnext(Integer isnext) {
		this.isnext = isnext;
	}

	public void setBatch(Integer batch) {
		this.batch = batch;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
 
}