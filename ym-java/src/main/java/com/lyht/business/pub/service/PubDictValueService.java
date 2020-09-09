package com.lyht.business.pub.service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.pub.dao.PubDictValueDao;
import com.lyht.business.pub.entity.PubDictValue;
import com.lyht.business.pub.vo.PubDictValueDetail;
import com.lyht.business.pub.vo.PubDictValueTree;
import com.lyht.business.pub.vo.PubDictValueTreeVO;
import com.lyht.util.CommonUtil;
import com.lyht.util.EntityUtils;
import com.lyht.util.Randomizer;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 创建人： liuamang 脚本日期:2019年2月21日 22:40:27 说明: 字典
 */
@Service("/pubDictValueService")
public class PubDictValueService {
	private Logger log = LoggerFactory.getLogger(PubDictValueService.class);

	@Autowired
	private PubDictValueDao dao;

	/**
	 * 按id查询详情
	 * 
	 * @param id
	 * @return
	 */
	public LyhtResultBody<PubDictValue> findById(Integer id) {
		PubDictValue bean = null;
		try {
			if (id != null) {
				Optional<PubDictValue> findById = dao.findById(id);
				bean = findById.get();
			}
		} catch (Exception e) {
			log.error("====PubDictValueService====findById====params:" + id, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(bean);
	}

	/**
	 * 按code查询详情
	 * 
	 * @param code
	 * @return
	 */
	public LyhtResultBody<PubDictValue> findByCode(String code) {
		PubDictValue bean = null;
		try {
			if (code != null) {
				bean = dao.findByCode(code);
			}
		} catch (Exception e) {
			log.error("====PubDictValueService====findById====params:" + code, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(bean);
	}

	/**
	 * 按id删除
	 * 
	 * @param id
	 * @return
	 */
	public LyhtResultBody<Integer> deleteById(Integer id) {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			log.error("====PubDictValueService====deleteById====params:" + id, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(id);
	}

	/**
	 * 按ids删除
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public LyhtResultBody<String> deleteByIds(String ids) {
		try {
			List<Integer> idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
			List<PubDictValue> findAll = dao.findAllById(idList);
			dao.deleteInBatch(findAll);
		} catch (Exception e) {
			log.error("====PubDictValueService====deleteByIds====params:" + ids, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(ids);
	}

//	/**
//	 * 查询角色列表信息
//	 * 
//	 * @param searchBean
//	 * @return
//	 */
//	@SuppressWarnings("rawtypes")
//	public Page<Map> getList(PubDictValueSearchBean searchBean) {
//		return dao.getList(searchBean.getName(),searchBean.getDictName(),
//				PageRequest.of(searchBean.getCurrPage() - 1, searchBean.getPageSize()));
//
//	}
	/**
	 * 根据字典分类查询字典列表
	 * 
	 * @param dictCate
	 * @return
	 */
	public List<PubDictValue> getdictByCate(String dictCate, String parentCode, HttpServletRequest request) {
		return dao.getdictByCate(parentCode, dictCate);
	}

	public List<PubDictValue> getdictByCate01(String dictCate, String parentCode, HttpServletRequest request) {
		return dao.getdictByCate01(parentCode, dictCate);
	}

	/**
	 * 查询所有子级字典
	 *
	 * @param dictCate
	 * @return
	 */
	public List<PubDictValueTree> getdictByCateSon(String dictCate) {
		return dao.getdictByCateSon(dictCate);
	}

	/**
	 * 查询字典分类
	 *
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public LyhtResultBody<List<PubDictValue>> list(PubDictValueDetail pubDictValueDetail) {
		return new LyhtResultBody<>(
				dao.list(pubDictValueDetail.getCode(), pubDictValueDetail.getId(), pubDictValueDetail.getNm(),
						pubDictValueDetail.getListnm_sys_dict_cate(), pubDictValueDetail.getParent_nm()));
	}

	/**
	 * 查询最上级菜单
	 *
	 * @param
	 * @return
	 */
	public LyhtResultBody<List<PubDictValueTree>> getDictIsNotParent(PubDictValueDetail pubDictValueDetail) {
		List<Map> findAllByParams = dao.getDictIsNotParent(pubDictValueDetail.getListnm_sys_dict_cate());
		List<Map> newMapList = new ArrayList<>();
		for (int i = 0; i < findAllByParams.size(); i++) {
			Map<String, Object> one = findAllByParams.get(i);
			HashMap<String, Object> newOne = new HashMap<>();
			newOne.putAll(one);
			if (Integer.parseInt(findAllByParams.get(i).get("isLeaf") + "") > 0) {
				newOne.put("isLeaf", false);
			} else {
				newOne.put("isLeaf", true);
			}
			newMapList.add(newOne);
		}
		return new LyhtResultBody<>(EntityUtils.toEntityList(newMapList, PubDictValueTree.class));
	}

	/**
	 * 查询最上级菜单
	 *
	 * @param
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LyhtResultBody<List<PubDictValueTree>> getDictByParent(String ParentNm) {
		List<Map> findAllByParams = dao.getDictByParent(ParentNm);
		List<Map> reMap = new ArrayList<>();
		for (int i = 0; i < findAllByParams.size(); i++) {
			Map mp = new HashMap();
			mp.putAll(findAllByParams.get(i));
			reMap.add(mp);
			if (Integer.parseInt(reMap.get(i).get("isLeaf") + "") > 0) {
				reMap.get(i).put("isLeaf", false);
			} else {
				reMap.get(i).put("isLeaf", true);
			}
		}
		return new LyhtResultBody<>(EntityUtils.toEntityList(reMap, PubDictValueTree.class));
	}

	/**
	 * 根据nm查详情
	 * 
	 * @param nm
	 * @return
	 */
	public LyhtResultBody<PubDictValue> getNameByNm(String nm) {
		PubDictValue findByNm = null;
		try {
			findByNm = dao.findByNm(nm);
		} catch (Exception e) {
			log.error("====PubDictValueService====Method：getNameByNm====params:" + nm, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(findByNm);
	}

	/**
	 * 字典保存
	 * 
	 * @param pubDictValue
	 * @return
	 */
	public LyhtResultBody<PubDictValue> save(PubDictValue pubDictValue) {
		PubDictValue save = null;
		int i = 0;
		try {

			List<PubDictValue> pubDictValues = dao.findByListnmSysDictCate(pubDictValue.getListnmSysDictCate());
			for (PubDictValue pubDictValue1 : pubDictValues) {
				if ((pubDictValue1.getId() + "").equals(pubDictValue.getId() + "")) {
					// 如果遍历为当前对象则跳过名称筛选
				} else if (pubDictValue1.getName().equals(pubDictValue.getName())) {
					i = 1;
					throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
				}
			}
			if (pubDictValue != null && pubDictValue.getId() == null) {
				pubDictValue.setNm(Randomizer.generCode(10));
				pubDictValue.setFlag(0);
				save = pubDictValue;
			} else {
				Optional<PubDictValue> findById = dao.findById(pubDictValue.getId());
				save = findById.get();
				save.setListnmSysDictCate(pubDictValue.getListnmSysDictCate());
				save.setMemo(pubDictValue.getMemo());
				save.setName(pubDictValue.getName());
				save.setSorted(pubDictValue.getSorted());
				save.setCode(pubDictValue.getCode());
				save.setParentNm(pubDictValue.getParentNm());
			}

			PubDictValue pub = null;
			if (CommonUtil.getLength(save.getParentNm()) > 0) {
				pub = findParentNm(save.getParentNm());
			}
			if (pub != null && pub.getId() > 0) {
				save.setLevels(pub.getLevels() + 1);
			} else {
				save.setLevels(1);
			}
			save = dao.save(save);
		} catch (Exception e) {
			if (i == 1) {
				throw new LyhtRuntimeException(LyhtExceptionEnums.DICT_NAME_FALSE);
			} else {
				log.error("====PubDictValueService====Method：save====params:" + pubDictValue, e);
				throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
			}

		}
		return new LyhtResultBody<>(save);
	}

	/**
	 * 通过内码获取父级字典
	 * 
	 * @param nm
	 * @return
	 */
	public PubDictValue findParentNm(String nm) {
		return dao.findByNm(nm);
	}

	/**
	 * 分页查询
	 * 
	 * @param lyhtPageVO
	 * @param pubDictValueDetail
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public LyhtResultBody<List<PubDictValueDetail>> page(LyhtPageVO lyhtPageVO, PubDictValueDetail pubDictValueDetail) {
		// 分页,排序
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = dao.page(pubDictValueDetail.getCode(), pubDictValueDetail.getId(), pubDictValueDetail.getNm(),
				pubDictValueDetail.getListnm_sys_dict_cate(), pubDictValueDetail.getSearchName(), pageable);
		// 结果集
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(EntityUtils.toEntityList(page.getContent(), PubDictValueDetail.class), pageVO);
	}

	/**
	 * 获取对应分类下的Map
	 * 
	 * @param listnmSysDictCate
	 * @return
	 */
	public Map<String, PubDictValue> getDictMap(String listnmSysDictCate) {
		List<PubDictValue> findByListnmSysDictCate = dao.findByListnmSysDictCate(listnmSysDictCate);
		Map<String, PubDictValue> map = new HashMap<>();
		for (PubDictValue pubDictValue : findByListnmSysDictCate) {
			String name = pubDictValue.getName();
			map.put(name, pubDictValue);
		}
		return map;
	}

	/**
	 * 获取征地范围字典
	 * 
	 * @return
	 */
	public List<PubDictValue> getScope() {
		List<PubDictValue> scope = dao.getScope();
		return scope;
	}

	/**
	 * 通过nm查询name
	 * 
	 * @param nm
	 * @return
	 */
	public String findNameByNm(String nm) {
		String name = dao.findNameByNm(nm);
		return name;
	}

	/**
	 * 根据字典分类查询
	 * 
	 * @param type
	 * @return
	 */
	public List<PubDictValue> findByType(String type) {
		List<PubDictValue> findByCate = dao.findByType(type);
		return findByCate;
	}

	/**
	 * 获取字典树
	 * 
	 * @param type
	 * @return
	 */
	public List<PubDictValueTreeVO> getTreeByType(String type) {
		List<PubDictValue> findByType = findByType(type);
		if (CollectionUtils.isEmpty(findByType)) {
			return null;
		}
		String jsonString = JSON.toJSONString(findByType);
		List<PubDictValueTreeVO> list = JSON.parseArray(jsonString, PubDictValueTreeVO.class);
		// step1：获取根节点
		List<PubDictValueTreeVO> rootList = null;
		for (PubDictValueTreeVO pubDictValueTreeVO : list) {
			String parentNm = pubDictValueTreeVO.getParentNm();
			if (StringUtils.isBlank(parentNm)) {
				if (rootList == null) {
					rootList = new ArrayList<>();
				}
				rootList.add(pubDictValueTreeVO);
			}
		}
		// step2：从原有集合中删除根节点，并遍历递归找出子节点
		if (CollectionUtils.isNotEmpty(rootList)) {
			list.removeAll(rootList);
			for (PubDictValueTreeVO pubDictValueTreeVO : rootList) {
				getTree(list, pubDictValueTreeVO);
			}
		}

		return rootList;
	}

	/**
	 * 获取子节点
	 * @param list
	 * @param node
	 */
	public void getTree(List<PubDictValueTreeVO> list, PubDictValueTreeVO node) {
		if (CollectionUtils.isEmpty(list) || node == null) {
			return;
		}

		String nm = node.getNm();// 当前节点编码

		// step1：找出子节点
		List<PubDictValueTreeVO> children = null;
		for (PubDictValueTreeVO pubDictValueTreeVO : list) {
			String parentNm = pubDictValueTreeVO.getParentNm();// 父节点编码
			if (StringUtils.equalsIgnoreCase(nm, parentNm)) {
				if (children == null) {
					children = new ArrayList<>();
				}
				children.add(pubDictValueTreeVO);
			}
		}

		// step2：从原有集合中删除已匹配的子节点，并遍历递归
		if (CollectionUtils.isNotEmpty(children)) {
			list.removeAll(children);
			for (PubDictValueTreeVO pubDictValueTreeVO : children) {
				getTree(list, pubDictValueTreeVO);
			}
			node.setChildren(children);
		}
	}

}
