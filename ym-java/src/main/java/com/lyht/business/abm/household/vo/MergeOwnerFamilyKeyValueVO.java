package com.lyht.business.abm.household.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Description:
 * @Author: xzp
 * @Date: 2020/9/1 9:19
 **/
public class MergeOwnerFamilyKeyValueVO {

    @ApiModelProperty("家庭指标内码")
    private String familyNm;

    @ApiModelProperty("与户主关系(字典内码)")
    private String relation;

    public String getFamilyNm() {
        return familyNm;
    }

    public void setFamilyNm(String familyNm) {
        this.familyNm = familyNm;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
