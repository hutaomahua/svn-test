package com.lyht.business.change.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author HuangAn
 * @date 2019/12/5 14:41
 */
@ApiModel(value = "变更申请表")
@Entity
@Table(name = "t_change_design_content")
public class DesignContent implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "唯一nm")
    private String nm;

    @ApiModelProperty(value = "内容项")
    @Column(name = "content_items")
    private String contentItems;

    @ApiModelProperty(value = "所属变更类型")
    @Column(name = "change_type")
    private Integer changeType;
    @ApiModelProperty(value = "所属变更申请类型")
    @Column(name = "change_request_type")
    private Integer changeRequestType;
    @ApiModelProperty(value = "排序")
    @Column(name = "sort")
    private Integer sort;

    public Integer getId() {
        return id;
    }

    public String getNm() {
        return nm;
    }

    public String getContentItems() {
        return contentItems;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public Integer getChangeRequestType() {
        return changeRequestType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public void setContentItems(String contentItems) {
        this.contentItems = contentItems;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public void setChangeRequestType(Integer changeRequestType) {
        this.changeRequestType = changeRequestType;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
