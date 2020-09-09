package com.lyht.business.pub.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.pub.dao.PubRegionDao;
import com.lyht.business.pub.entity.PubRegionEntity;
import com.lyht.util.Randomizer;

/**
 * 行政区域
 * 
 * @author hxl
 *
 */
@Service
public class PubRegionService {
//	private Logger log = Logger.getLogger(PubRegionService.class);

	@Autowired
	private PubRegionDao pubRegionDao;

	/**
	 * 删除 并删除子集
	 */
	@Transactional
	public List<PubRegionEntity> delete(Integer id) {
		PubRegionEntity region = pubRegionDao.getOne(id);// 根据id查询删除对象
		List<PubRegionEntity> list = pubRegionDao.getChildList(region.getMergerName());// 查询删除对象的子集一并删除
		try {
			pubRegionDao.delete(region);
			pubRegionDao.deleteAll(list);
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		list.add(region);
		return list;
	}

	/**
	 * 修改子集
	 */
	@Transactional
	public Integer updateSons(String oldmerger, String newmerger, String oldshort, String newshort) {
		return pubRegionDao.updateSons(oldmerger, newmerger, oldshort, newshort);
	}

	/**
	 * 查询子集
	 * 
	 * @param mergerName
	 * @return
	 */
	public List<PubRegionEntity> getChildList(String mergerName) {
		return pubRegionDao.getChildList(mergerName);
	}

	/**
	 * 新增 修改...
	 * 
	 * @return
	 */
	public PubRegionEntity save(PubRegionEntity pubRegionEntity) {
		// 参数校验
		if (pubRegionEntity == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}

		// 根据 parent_code 查询父级信息
		PubRegionEntity parent = pubRegionDao.findByCityCode(pubRegionEntity.getParentCode());
		if (StringUtils.isNotBlank(pubRegionEntity.getLat()) && StringUtils.isNotBlank(pubRegionEntity.getLng())) {
			pubRegionEntity.setNameFirstChar(pubRegionEntity.getLng() + "," + pubRegionEntity.getLat());// 设置经纬度字段
		}
		pubRegionEntity.setMergerName(parent.getMergerName() + "," + pubRegionEntity.getName());// 设置省市区全称聚合
		pubRegionEntity.setMergerShortName(parent.getMergerShortName() + "," + pubRegionEntity.getShortName());// 行政区划简称聚合
		pubRegionEntity.setLevelType(parent.getLevelType() + 1);
		// 内码赋值 新增
		String nm = pubRegionEntity.getNm();
		if (StringUtils.isBlank(nm)) {// 新增
			pubRegionEntity.setNm(Randomizer.generCode(10));// 设置内码
			pubRegionEntity.setCityCode(Randomizer.generCode(10));// 设置随机城市编码
		}

		if (pubRegionEntity.getLevelType() == 5) {// 村
			pubRegionEntity.setLevel("village");
		} else if (pubRegionEntity.getLevelType() == 6) {// 组
			pubRegionEntity.setLevel("group");
		}
		// 状态码默认1
		pubRegionEntity.setStatus(1);
		PubRegionEntity result = pubRegionDao.save(pubRegionEntity);
		return result;
	}

	public PubRegionEntity findByCityCode(String cityCode) {
		return pubRegionDao.findByCityCode(cityCode);
	}

	@SuppressWarnings("unchecked")
	public List<PubRegionEntity> findByLevelTypeGuipai(Integer level, Integer leveltype,
			@SuppressWarnings("rawtypes") Map<Integer, List> mp) {
		List<PubRegionEntity> plist = mp.get(level);
		List<PubRegionEntity> slist = mp.get(level + 1);
		if (level + 1 > leveltype)
			return mp.get(1);
		for (int i = 0; i < plist.size(); i++) {
			List<PubRegionEntity> pclist = new ArrayList<PubRegionEntity>();
			for (int j = 0; j < slist.size(); j++) {
				if (plist.get(i).getCityCode().equals(slist.get(j).getParentCode())) {
					pclist.add(slist.get(j));
				}
			}
			plist.get(i).setChildren(pclist);
		}
		mp.put(level, plist);
		level++;
		return level > leveltype ? mp.get(1) : findByLevelTypeGuipai(level, leveltype, mp);
	}

	/**
	 * 树形查询 动态级别
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<PubRegionEntity> findBylevelTypeTree() {
		// 获取等级 最低级
		Integer levelType = pubRegionDao.getMinLevelType();
		List<PubRegionEntity> result = new ArrayList<>();
		Map<Integer, List> mlist = new HashMap<Integer, List>();
		for (int i = 1; i <= levelType; i++) {
			mlist.put(i, pubRegionDao.findByLevelType(i));
		}
		result = findByLevelTypeGuipai(1, levelType, mlist);
		return result;
	}

	public void test(List<PubRegionEntity> list, String parentCode) {
		if (list != null) {
			for (PubRegionEntity map : list) {
				map.setParentCode(parentCode);
				List<PubRegionEntity> childList = map.getChildren();
				String generCode = Randomizer.generCode(10);
				map.setCityCode(generCode);
				pubRegionDao.save(map);
				test(childList, generCode);
			}
		}
	}

	/**
	 * 列表查询
	 * 
	 * @param pubRegionEntity
	 * @return
	 */
	public List<PubRegionEntity> list(PubRegionEntity pubRegionEntity) {
		return pubRegionDao.list(pubRegionEntity.getId(), pubRegionEntity.getCityCode(),
				pubRegionEntity.getParentCode(), pubRegionEntity.getMergerName(), pubRegionEntity.getLevelType());
	}

	/**
	 * 树状查询
	 * 
	 * @param pubRegionEntity
	 * @return
	 */
	public List<PubRegionEntity> treeList(PubRegionEntity pubRegionEntity) {
		List<PubRegionEntity> pubRegionEntitieProvinces = pubRegionDao.findByLevelType(1);
		List<PubRegionEntity> pubRegionEntitieCitys = pubRegionDao.findByLevelType(2);
		List<PubRegionEntity> pubRegionEntitieStreets = pubRegionDao.findByLevelType(3);
		List<PubRegionEntity> pubRegionEntitieStreets1 = pubRegionDao.findByLevelType(4);
		List<PubRegionEntity> resultList = new ArrayList<>();
		for (PubRegionEntity pubRegionEntity1 : pubRegionEntitieProvinces) {
			List<PubRegionEntity> childrens = new ArrayList<>();
			for (PubRegionEntity pubRegionEntity2 : pubRegionEntitieCitys) {
				List<PubRegionEntity> childrens1 = new ArrayList<>();
				for (PubRegionEntity pubRegionEntity3 : pubRegionEntitieStreets) {
					List<PubRegionEntity> childrens2 = new ArrayList<>();
					for (PubRegionEntity pubRegionEntity4 : pubRegionEntitieStreets1) {
						if (pubRegionEntity4.getParentCode().equals(pubRegionEntity3.getCityCode())) {
							childrens2.add(pubRegionEntity4);
						}
					}
//					pubRegionEntity3.setChildren(childrens2);
					if (pubRegionEntity3.getParentCode().equals(pubRegionEntity2.getCityCode())) {
						childrens1.add(pubRegionEntity3);
					}
				}
//				pubRegionEntity2.setChildren(childrens1);
				if (pubRegionEntity2.getParentCode().equals(pubRegionEntity1.getCityCode())) {
					childrens.add(pubRegionEntity2);
				}
			}
//			pubRegionEntity1.setChildren(childrens);
			resultList.add(pubRegionEntity1);
		}
		return resultList;
	}

	/**
	 * 根据行政区域名称模糊查询
	 * 
	 * @param mergerName
	 * @return
	 */
	public List<PubRegionEntity> findAllByName(String name) {
		List<PubRegionEntity> findAllByName = pubRegionDao.findAllByName(name);
		return findAllByName;
	}

	/**
	 * 查询所有父级节点
	 * 
	 * @param cityCode
	 * @param mergerName
	 * @return
	 */
	public List<PubRegionEntity> getParents(String cityCode, String mergerName, Integer levelType) {
		if (StringUtils.isNotBlank(cityCode) && StringUtils.isBlank(mergerName)) {
			PubRegionEntity findByCityCode = pubRegionDao.findByCityCode(cityCode);
			mergerName = findByCityCode.getMergerName();
		}
		if (StringUtils.isBlank(mergerName)) {
			return null;
		}
		String[] split = StringUtils.split(mergerName, ",");
		List<String> names = Arrays.asList(split);

		List<PubRegionEntity> findAllByNames = pubRegionDao.findAllByNames(names, levelType);

		return findAllByNames;
	}

	/**
	 * 获取以mergername(去逗号)为key的Map
	 * 
	 * @return
	 */
	public Map<String, PubRegionEntity> getMergerNameMap() {
		List<PubRegionEntity> findAll = pubRegionDao.findAll();
		Map<String, PubRegionEntity> map = new HashMap<>();
		for (PubRegionEntity pubRegionEntity : findAll) {
			String mergerName = pubRegionEntity.getMergerName();
			String replace = mergerName.replace(",", "");
			map.put(replace, pubRegionEntity);
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public List<PubRegionEntity> findByLevelTypeGuipai1(Integer level, Integer leveltype,
			@SuppressWarnings("rawtypes") Map<Integer, List> mp) {
		List<PubRegionEntity> plist = mp.get(level);
		List<PubRegionEntity> slist = mp.get(level + 1);
		if (level + 1 > leveltype)
			return mp.get(4);
		for (int i = 0; i < plist.size(); i++) {
			List<PubRegionEntity> pclist = new ArrayList<PubRegionEntity>();
			for (int j = 0; j < slist.size(); j++) {
				if (plist.get(i).getCityCode().equals(slist.get(j).getParentCode())) {
					pclist.add(slist.get(j));
				}
			}
			plist.get(i).setChildren(pclist);
		}
		mp.put(level, plist);
		level++;
		return level > leveltype ? mp.get(4) : findByLevelTypeGuipai1(level, leveltype, mp);
	}

	/**
	 * 树形查询 动态级别
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<PubRegionEntity> findBylevelTypeTree1() {
		// 获取等级 最低级
		Integer levelType = pubRegionDao.getMinLevelType();
		List<PubRegionEntity> result = new ArrayList<>();
		Map<Integer, List> mlist = new HashMap<Integer, List>();
		for (int i = 4; i <= levelType; i++) {
			mlist.put(i, pubRegionDao.findByLevelType1(i));
		}
		result = findByLevelTypeGuipai1(4, levelType, mlist);
		return result;
	}
	
	/**
	 * 模糊查询(已“行政区--全称”开头)
	 * @param mergerName
	 * @return
	 */
	public List<PubRegionEntity> findByMergerName(String mergerName) {
		List<PubRegionEntity> findByMergerName = pubRegionDao.findByMergerName(mergerName);
		return findByMergerName;
	}
	
	public LyhtResultBody<List<Map<String,Object>>> getTypeThanThree(){
		List<Map<String,Object>> list = pubRegionDao.getTypeThanThree();
		JSONArray array = listToTree(JSONArray.parseArray(JSON.toJSONString(list)), "cityCode", "parentCode", "children");
		list= (List<Map<String,Object>>) JSONArray.parse(array.toJSONString());
		return new LyhtResultBody<>(list);
	}
	
	public JSONArray listToTree(JSONArray arr, String id, String pid, String child) {
        JSONArray r = new JSONArray();
        JSONObject hash = new JSONObject();
        // 将数组转为Object的形式，key为数组中的id
        for (int i = 0; i < arr.size(); i++) {
            JSONObject json = (JSONObject) arr.get(i);
            hash.put(json.getString(id), json);
        }
        // 遍历结果集
        for (int j = 0; j < arr.size(); j++) {
            // 单条记录
            JSONObject aVal = (JSONObject) arr.get(j);
            // 在hash中取出key为单条记录中pid的值
            JSONObject hashVP = (JSONObject) hash.get(aVal.get(pid).toString());
            // 如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
            if (hashVP != null) {
                // 检查是否有child属性
                if (hashVP.get(child) != null) {
                    JSONArray ch = (JSONArray) hashVP.get(child);
                    ch.add(aVal);
                    hashVP.put(child, ch);
                } else {
                    JSONArray ch = new JSONArray();
                    ch.add(aVal);
                    hashVP.put(child, ch);
                }
            } else {
                r.add(aVal);
            }
        }
        return r;
    }
	
}
