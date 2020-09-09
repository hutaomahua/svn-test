package com.lyht.business.abm.removal.vo;


import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author wangZhenWei
 */
public class PlacementVO {

    @ApiModelProperty(value = "序号")
    private String serialNumber;

    @ApiModelProperty(value = "安置类别")
    private String placeType;

    @ApiModelProperty(value = "安置点名称")
    private String placeName;

    @ApiModelProperty(value = "户数")
    private int sumHourse;

    @ApiModelProperty(value = "人数")
    private int sumNumber;

    private List<PlacementVO> placementVOList;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public List<PlacementVO> getPlacementVOList() {
        return placementVOList;
    }

    public void setPlacementVOList(List<PlacementVO> placementVOList) {
        this.placementVOList = placementVOList;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public int getSumHourse() {
        return sumHourse;
    }

    public void setSumHourse(int sumHourse) {
        this.sumHourse = sumHourse;
    }

    public int getSumNumber() {
        return sumNumber;
    }

    public void setSumNumber(int sumNumber) {
        this.sumNumber = sumNumber;
    }
}
