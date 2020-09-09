package com.lyht.business.abm.removal.entity;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(description = "搬迁安置")
@Entity
@Table(name = "t_abm_move_identity")
public class MoveIdentity implements Serializable {
    private static final long serialVersionUID = 1L;

    public MoveIdentity()
    {
    	
    }
    /**
     * 主键id
     */
    @ApiModelProperty(value = "唯一ID，修改必填")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 计划名
     */
    @ApiModelProperty(value = "内码")
    private String nm;

    /**
     * owner_nm
     */
    @ApiModelProperty(value = "权属人nm")
    @Column(name = "owner_nm", nullable = false)
    private String ownerNm;

    /**
     * isno
     */
    @ApiModelProperty(value = "是否符合")
    private Integer isno;

    /**
     * condition
     */
    @ApiModelProperty(value = "界定条件")
    private String condition;

    /**
     * research
     */
    @ApiModelProperty(value = "可研阶段去向")
    private String research;

    /**
     * research_place_type
     */
    @ApiModelProperty(value = "可研阶段安置类型")
    @Column(name = "research_place_type", nullable = false)
    private String researchPlaceType;

    /**
     * impl_direction
     */
    @ApiModelProperty(value = "实施阶段去向")
    @Column(name = "impl_direction", nullable = false)
    private String implDirection;

    /**
     * rimpl_place_types
     */
    @ApiModelProperty(value = "实施阶段安置类型")
    @Column(name = "rimpl_place_types", nullable = false)
    private String rimplPlaceTypes;

    /**
     * is_agreement
     */
    @ApiModelProperty(value = "是否已签订协议")
    @Column(name = "is_agreement", nullable = false)
    private String isAgreement;

    /**
     * agreement_time
     */
    @ApiModelProperty(value = "协议签订时间")
    @Column(name = "agreement_time", nullable = false)
    private Date agreementTime;

    /**
     * agreement_money
     */
    @ApiModelProperty(value = "协议金额")
    @Column(name = "agreement_money", nullable = false)
    private BigDecimal agreementMoney;

    /**
     * is_move
     */
    @ApiModelProperty(value = "是否已搬迁")
    @Column(name = "is_move", nullable = false)
    private Integer isMove;

    /**
     * move_num
     */
    @ApiModelProperty(value = "搬迁人口")
    @Column(name = "move_num", nullable = false)
    private Integer moveNum;

    /**
     * move_time
     */
    @ApiModelProperty(value = "搬迁时间")
    @Column(name = "move_time", nullable = false)
    private Date moveTime;

    /**
     * is_payment
     */
    @ApiModelProperty(value = "是否已付款")
    @Column(name = "is_payment", nullable = false)
    private Integer isPayment;

    /**
     * water
     */
    @ApiModelProperty(value = "付款流水号")
    private String water;

    /**
     * remarks
     */
    @ApiModelProperty(value = "备注")
    private String remarks;

    /**
     * state
     */
    @ApiModelProperty(value = "状态")
    private Integer state;

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

    public Integer getIsno() {
        return isno;
    }

    public void setIsno(Integer isno) {
        this.isno = isno;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getResearch() {
        return research;
    }

    public void setResearch(String research) {
        this.research = research;
    }

    public String getResearchPlaceType() {
        return researchPlaceType;
    }

    public void setResearchPlaceType(String researchPlaceType) {
        this.researchPlaceType = researchPlaceType;
    }

    public String getImplDirection() {
        return implDirection;
    }

    public void setImplDirection(String implDirection) {
        this.implDirection = implDirection;
    }

    public String getRimplPlaceTypes() {
        return rimplPlaceTypes;
    }

    public void setRimplPlaceTypes(String rimplPlaceTypes) {
        this.rimplPlaceTypes = rimplPlaceTypes;
    }

    public String getIsAgreement() {
        return isAgreement;
    }

    public void setIsAgreement(String isAgreement) {
        this.isAgreement = isAgreement;
    }

    public Date getAgreementTime() {
        return agreementTime;
    }

    public void setAgreementTime(Date agreementTime) {
        this.agreementTime = agreementTime;
    }

    public BigDecimal getAgreementMoney() {
        return agreementMoney;
    }

    public void setAgreementMoney(BigDecimal agreementMoney) {
        this.agreementMoney = agreementMoney;
    }

    public Integer getIsMove() {
        return isMove;
    }

    public void setIsMove(Integer isMove) {
        this.isMove = isMove;
    }

    public Integer getMoveNum() {
        return moveNum;
    }

    public void setMoveNum(Integer moveNum) {
        this.moveNum = moveNum;
    }

    public Date getMoveTime() {
        return moveTime;
    }

    public void setMoveTime(Date moveTime) {
        this.moveTime = moveTime;
    }

    public Integer getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(Integer isPayment) {
        this.isPayment = isPayment;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    
    
    public MoveIdentity(Integer id, String nm, String ownerNm, Integer isno, String condition, String research,
			String researchPlaceType, String implDirection, String rimplPlaceTypes, String isAgreement,
			Date agreementTime, BigDecimal agreementMoney, Integer isMove, Integer moveNum, Date moveTime,
			Integer isPayment, String water, String remarks, Integer state) {
		super();
		this.id = id;
		this.nm = nm;
		this.ownerNm = ownerNm;
		this.isno = isno;
		this.condition = condition;
		this.research = research;
		this.researchPlaceType = researchPlaceType;
		this.implDirection = implDirection;
		this.rimplPlaceTypes = rimplPlaceTypes;
		this.isAgreement = isAgreement;
		this.agreementTime = agreementTime;
		this.agreementMoney = agreementMoney;
		this.isMove = isMove;
		this.moveNum = moveNum;
		this.moveTime = moveTime;
		this.isPayment = isPayment;
		this.water = water;
		this.remarks = remarks;
		this.state = state;
	}

	@Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
