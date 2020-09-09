package com.lyht.business.abm.household.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 分户暂存参数
 * @Author: xzp
 * @Date: 2020/8/4 8:49
 **/
public class StorageHouseholdVO {

    @ApiModelProperty("指标内码")
    private String indexNm;

    @ApiModelProperty("指标类型，1家庭人口/2土地/3房屋/4零星树木/5其他附属/6房屋装修/7个体工商户")
    private Integer indexType;

    @ApiModelProperty("分户接收人内码(例：张三的房屋分给张四，则填张四的内码)")
    private String recipient;

    public String getIndexNm() {
        return indexNm;
    }

    public void setIndexNm(String indexNm) {
        this.indexNm = indexNm;
    }

    public Integer getIndexType() {
        return indexType;
    }

    public void setIndexType(Integer indexType) {
        this.indexType = indexType;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
