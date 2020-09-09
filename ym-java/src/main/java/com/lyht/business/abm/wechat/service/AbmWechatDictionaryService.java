package com.lyht.business.abm.wechat.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyht.business.abm.wechat.vo.AbmWechatRegionWithScopeVO;
import com.lyht.business.pub.entity.PubDictValue;
import com.lyht.business.pub.entity.PubRegionEntity;
import com.lyht.business.pub.service.PubDictValueService;
import com.lyht.business.pub.service.PubRegionService;

@Service
public class AbmWechatDictionaryService {

	@Autowired
	private PubRegionService pubRegionService;

	@Autowired
	private PubDictValueService pubDictValueService;

	/**
	 * 获取行政区以及征地范围字典
	 * 
	 * @return
	 */
	public AbmWechatRegionWithScopeVO getRegionAndScope() {
		AbmWechatRegionWithScopeVO abmWechatRegionWithScopeVO = new AbmWechatRegionWithScopeVO();

		List<PubRegionEntity> regionTree = getRegionTree();
		abmWechatRegionWithScopeVO.setRegion(regionTree);

		List<PubDictValue> scope = pubDictValueService.getScope();
		abmWechatRegionWithScopeVO.setScope(scope);

		return abmWechatRegionWithScopeVO;
	}

	/**
	 * 获取行政区--树
	 */
	public List<PubRegionEntity> getRegionTree() {
		List<PubRegionEntity> findByMergerName = pubRegionService.findByMergerName("云南省,迪庆藏族自治州,维西傈僳族自治县,");
		if (CollectionUtils.isEmpty(findByMergerName)) {
			return null;
		}
		// step1：遍历获取顶层节点，并从集合中删除
		List<PubRegionEntity> rootList = null;
		for (PubRegionEntity pubRegionEntity : findByMergerName) {
			Integer levelType = pubRegionEntity.getLevelType();
			if (levelType != null && levelType == 4) {// 从维西傈僳族自治县下一级开始
				if (CollectionUtils.isEmpty(rootList)) {
					rootList = new ArrayList<>();
				}
				rootList.add(pubRegionEntity);
			}
		}
		if (CollectionUtils.isEmpty(rootList)) {
			return null;
		}
		findByMergerName.removeAll(rootList);

		// step2：递归获取子节点
		for (PubRegionEntity pubRegionEntity : rootList) {
			getRegionChildren(pubRegionEntity, findByMergerName);
		}

		return rootList;
	}

	/**
	 * 获取子节点
	 * 
	 * @param parent
	 * @param list
	 */
	private void getRegionChildren(PubRegionEntity parent, List<PubRegionEntity> list) {
		if (parent == null || CollectionUtils.isEmpty(list)) {
			return;
		}

		// step1：遍历匹配子节点
		List<PubRegionEntity> children = null;// 子集
		String cityCode = parent.getCityCode();// 父节点的编码
		for (PubRegionEntity pubRegionEntity : list) {
			String parentCode = pubRegionEntity.getParentCode();// 当前节点绑定的父节点编码
			// 匹配是否相等
			if (StringUtils.equals(cityCode, parentCode)) {
				if (children == null) {
					children = new ArrayList<>();
				}
				children.add(pubRegionEntity);
			}
		}
		if (CollectionUtils.isEmpty(children)) {
			return;
		}

		// step2：删除已匹配的节点，并递归
		list.removeAll(children);
		for (PubRegionEntity pubRegionEntity : children) {
			getRegionChildren(pubRegionEntity, list);
		}

		// step3：拼装树
		parent.setChildren(children);
	}

}
