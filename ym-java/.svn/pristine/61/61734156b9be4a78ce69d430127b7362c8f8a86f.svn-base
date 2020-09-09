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
 *脚本日期:2020年2月19日 1:24:20
 *说明: 
*/
@Entity
@Table(name = "t_abm_payment_detail")
@ApiModel(description = "兑付明细")
public class PaymentDetailEntity {

    private static final long serialVersionUID = 1L;
    
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

    @Column(name = "apply_time"  )
    private String applyTime;

    @Column(name = "apply_batch"  )
    private Integer applyBatch;

    @Column(name = "apply_amount"  )
    private BigDecimal applyAmount;

    @Column(name = "proposer"  )
    private String proposer;

    @Column(name = "proposer_dept"  )
    private String proposerDept;

    @Column(name = "payee"  )
    private String payee;

    @Column(name = "payment_method"  )
    private String paymentMethod;

    @Column(name = "bank_card"  )
    private String bankCard;

    @Column(name = "deposit_bank"  )
    private String depositBank;

    @Column(name = "description"  )
    private String description;

    @Column(name = "remark"  )
    private String remark;

	@ApiModelProperty(value = "兑付申请流程实例id")
	@Column(name = "process_id")
	private String processId;

	@ApiModelProperty(value = "兑付确认批次内码")
	@Column(name = "confirmation_batch")
	private String confirmationBatch;
    
    /** default constructor */
    public PaymentDetailEntity() {
    
    }
    		
    public  Integer getId() {
        return this.id;
    }
    public void setId( Integer id) {
        this.id = id;
    }
    
    

    public  String getNm() {
        return this.nm;
    }
    public void setNm( String nm) {
        this.nm = nm;
    }
    
    
    public  String getOwnerNm() {
        return this.ownerNm;
    }
    public void setOwnerNm( String ownerNm) {
        this.ownerNm = ownerNm;
    }
    
    
    public  String getProtocolCode() {
        return this.protocolCode;
    }
    public void setProtocolCode( String protocolCode) {
        this.protocolCode = protocolCode;
    }
    
    public Integer getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(Integer protocolType) {
		this.protocolType = protocolType;
	}

	public  String getApplyTime() {
        return this.applyTime;
    }
    public void setApplyTime( String applyTime) {
        this.applyTime = applyTime;
    }
    
    public Integer getApplyBatch() {
		return applyBatch;
	}
	public void setApplyBatch(Integer applyBatch) {
		this.applyBatch = applyBatch;
	}

	public  BigDecimal getApplyAmount() {
        return this.applyAmount;
    }
    public void setApplyAmount( BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }
    
    
    public  String getProposer() {
        return this.proposer;
    }
    public void setProposer( String proposer) {
        this.proposer = proposer;
    }
    
    
    public  String getProposerDept() {
        return this.proposerDept;
    }
    public void setProposerDept( String proposerDept) {
        this.proposerDept = proposerDept;
    }
    
    
    public  String getPayee() {
        return this.payee;
    }
    public void setPayee( String payee) {
        this.payee = payee;
    }
    
    
    public  String getPaymentMethod() {
        return this.paymentMethod;
    }
    public void setPaymentMethod( String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    
    public  String getBankCard() {
        return this.bankCard;
    }
    public void setBankCard( String bankCard) {
        this.bankCard = bankCard;
    }
    
    
    public  String getDepositBank() {
        return this.depositBank;
    }
    public void setDepositBank( String depositBank) {
        this.depositBank = depositBank;
    }
    
    
    public  String getDescription() {
        return this.description;
    }
    public void setDescription( String description) {
        this.description = description;
    }
    
    
    public  String getRemark() {
        return this.remark;
    }
    public void setRemark( String remark) {
        this.remark = remark;
    }

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getConfirmationBatch() {
		return confirmationBatch;
	}

	public void setConfirmationBatch(String confirmationBatch) {
		this.confirmationBatch = confirmationBatch;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}