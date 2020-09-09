package com.lyht.business.abm.household.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Description:
 * @Author: xzp
 * @Date: 2020/8/20 14:50
 **/
public class MergeOwnerKeyValueVO {

    @ApiModelProperty("被合并人内码")
    private String ownerNm;

    @ApiModelProperty("与户主关系(字典内码)")
    private String relation;

    public String getOwnerNm() {
        return ownerNm;
    }

    public void setOwnerNm(String ownerNm) {
        this.ownerNm = ownerNm;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
