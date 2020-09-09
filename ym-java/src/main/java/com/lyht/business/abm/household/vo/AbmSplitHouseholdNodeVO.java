package com.lyht.business.abm.household.vo;

import java.util.List;
import java.util.Map;

import com.lyht.business.abm.removal.entity.AbmBuildingEntity;
import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import com.lyht.business.abm.removal.entity.AbmHouseEntity;
import com.lyht.business.abm.removal.entity.AbmHousesDecorationEntity;
import com.lyht.business.abm.removal.entity.AbmLandEntity;
import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
import com.lyht.business.abm.removal.entity.AbmTreesEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("分户对象--节点")
@Data
public class AbmSplitHouseholdNodeVO {
	
	@ApiModelProperty("户主")
	private AbmOwnerEntity owner;
	
	@ApiModelProperty("家庭成员列表")
	private List<AbmFamilyEntity> familyList;
	
	@ApiModelProperty("土地列表")
	private List<AbmLandEntity> landList;
	
	@ApiModelProperty("房屋列表")
	private List<AbmHouseEntity> houseList;
	
	@ApiModelProperty("零星树木列表")
	private List<AbmTreesEntity> treeList;
	
	@ApiModelProperty("其他附属列表")
	private List<AbmBuildingEntity> buildingList;
	
	@ApiModelProperty("房屋装修列表")
	private List<AbmHousesDecorationEntity> housesDecorationList;

	@ApiModelProperty("农副业设施列表")
	private List<Map> facilitiesList;

	@ApiModelProperty("宅基地列表")
	private List<Map> homesteadList;
	
}