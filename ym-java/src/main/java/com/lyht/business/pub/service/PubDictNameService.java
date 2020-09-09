package com.lyht.business.pub.service;


import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.pub.dao.PubDictNameDao;
import com.lyht.business.pub.entity.PubDictName;
import com.lyht.business.pub.vo.PubDictNameDetail;
import com.lyht.business.pub.vo.PubDictValueVO;
import com.lyht.util.CommonUtil;
import com.lyht.util.EntityUtils;
import com.lyht.util.Randomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
  * 创建人： liuamang 
  * 脚本日期:2019年2月21日 22:40:44
  * 说明:  字典分类
  */
@Service("/pubDictNameService")
public class PubDictNameService{
	private Logger log = LoggerFactory.getLogger(PubDictNameService.class);
	
	@Autowired  private PubDictNameDao dao;
	
	/** 查询字典分类
	 * @param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public LyhtResultBody<List<PubDictNameDetail>> page(LyhtPageVO lyhtPageVO, PubDictNameDetail pubDictNameDetail) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> resultList = dao.page(pubDictNameDetail.getName(),pubDictNameDetail.getCode(),pageable);
		LyhtPageVO pageVO = new LyhtPageVO(resultList.getNumber(), resultList.getTotalPages(), resultList.getSize(),
				resultList.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(EntityUtils.toEntityList(resultList.getContent(), PubDictNameDetail.class), pageVO);
		
	}


	/** 根据字典分类查询字典
	 * @return
	 */
	public LyhtResultBody<List<PubDictValueVO>> getPubDictValiue(PubDictNameDetail pubDictNameDetail) {
		List<PubDictValueVO> findAllByParams = dao.getPubDictValue(pubDictNameDetail.getId(), pubDictNameDetail.getNm(),pubDictNameDetail.getCode());
		return new LyhtResultBody<>(findAllByParams);

	}

	/** 查询所有字典分类
	 * @return
	 */
	public LyhtResultBody<List<PubDictName>> findAll() {
		List<PubDictName> list = dao.findAll();
		return new LyhtResultBody<>(list);
	}
	
	/**
	 * 详情查询，根据id
	 * @param id
	 * @return
	 */
	public LyhtResultBody<PubDictName> detail(Integer id) {
		Optional<PubDictName> findById = dao.findById(id);
		PubDictName findOne = findById.get();
		return new LyhtResultBody<>(findOne);
	}
	
	/**
	 * 保存
	 * @param pubDictName
	 * @return
	 */
	public LyhtResultBody<PubDictName> save(PubDictName pubDictName) {
		if (pubDictName == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		if (pubDictName.getId() == null) {
			if (dao.countByCode(pubDictName.getCode())>0){
				throw new LyhtRuntimeException(LyhtExceptionEnums.DATA_UNREPEATABLE);
			}
			pubDictName.setNm(Randomizer.generCode(10));
			pubDictName.setFlag(0);
		}
		PubDictName save = dao.save(pubDictName);
		return new LyhtResultBody<>(save);
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
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class) 
	public LyhtResultBody<String> deleteByIds(String ids) {
		try {
			List<Integer> idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
			List<PubDictName> findAll = dao.findAllById(idList);
			dao.deleteInBatch(findAll);
		} catch (Exception e) {
			log.error("====PubDictNameService====deleteByIds====params:" + ids, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(ids);
	}
}
 