package com.lyht.business.abm.protocol.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author HuangAn
 * @date 2019/12/9 16:48
 */
@ApiModel(value = "搬迁安置协议表")
@Entity
@Table(name = "t_abm_move_protocol")
@EntityListeners(AuditingEntityListener.class)
public class MoveProtocol  implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "唯一nm")
    private String nm;

    @ApiModelProperty(value = "协议编号")
    @Column(name = "protocol_no")
    private String protocolNo;

    @ApiModelProperty(value = "协议名称")
    @Column(name = "protocol_name")
    private String protocolName;

    @ApiModelProperty(value = "协议金额")
    @Column(name = "protocol_money")
    private String protocolMoney;

    @ApiModelProperty(value = "人口核定表内码")
    @Column(name = "approve_nm")
    private String approveNm;

    @ApiModelProperty(value = "费用计算内码")
    @Column(name = "cost_nm")
    private String costNm;

    @ApiModelProperty(value = "是否签订搬迁安置协议")
    @Column(name = "is_move_reset")
    private Integer isMoveReset;

    @ApiModelProperty(value = "搬迁安置协议签订时间")
    @Column(name = "move_reset_time")
    private Date moveResetTime;

    @ApiModelProperty(value = "是否签订资金代管协议")
    @Column(name = "is_money_escrow")
    private Integer isMoneyEscrow;

    @ApiModelProperty(value = "资金代管协议签订时间")
    @Column(name = "money_escrow_time")
    private Date moneyEscrowTime;

    @ApiModelProperty(value = "阶段")
    @Column(name = "stage")
    private String stage;

    @ApiModelProperty(value = "备注")
    @Column(name = "remark")
    private String remark;

    public Integer getId() {
        return id;
    }

    public String getNm() {
        return nm;
    }

    public String getApproveNm() {
        return approveNm;
    }

    public String getCostNm() {
        return costNm;
    }

    public Integer getIsMoveReset() {
        return isMoveReset;
    }

    public Date getMoveResetTime() {
        return moveResetTime;
    }

    public Integer getIsMoneyEscrow() {
        return isMoneyEscrow;
    }

    public Date getMoneyEscrowTime() {
        return moneyEscrowTime;
    }

    public String getStage() {
        return stage;
    }

    public String getProtocolNo() {
        return protocolNo;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public String getProtocolMoney() {
        return protocolMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setProtocolNo(String protocolNo) {
        this.protocolNo = protocolNo;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public void setProtocolMoney(String protocolMoney) {
        this.protocolMoney = protocolMoney;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public void setApproveNm(String approveNm) {
        this.approveNm = approveNm;
    }

    public void setCostNm(String costNm) {
        this.costNm = costNm;
    }

    public void setIsMoveReset(Integer isMoveReset) {
        this.isMoveReset = isMoveReset;
    }

    public void setMoveResetTime(Date moveResetTime) {
        this.moveResetTime = moveResetTime;
    }

    public void setIsMoneyEscrow(Integer isMoneyEscrow) {
        this.isMoneyEscrow = isMoneyEscrow;
    }

    public void setMoneyEscrowTime(Date moneyEscrowTime) {
        this.moneyEscrowTime = moneyEscrowTime;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}
