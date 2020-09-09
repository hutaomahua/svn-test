package com.lyht.business.pub.service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.pub.dao.PubLayerDao;
import com.lyht.business.pub.entity.PubLayerEntity;
import com.lyht.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PubLayerService {
	private Logger log = LoggerFactory.getLogger(PubLayerService.class);

	@Autowired
	private PubLayerDao dao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

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
			log.error("====PubLayerService====deleteById====params:" + id, e);
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
			List<PubLayerEntity> findAllById = dao.findAllById(idList);
			dao.deleteInBatch(findAllById);
		} catch (Exception e) {
			log.error("====PubLayerService====deleteByIds====params:" + ids, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(ids);
	}

	/**
	 * 新增，修改
	 * @param pubLayerEntity
	 * @return
	 */
	public LyhtResultBody<PubLayerEntity> save(PubLayerEntity pubLayerEntity) {
		PubLayerEntity save = null;
		try {
			save = dao.save(pubLayerEntity);
		} catch (Exception e) {
			log.error("====PubLayerService====Method：save====params:" + pubLayerEntity, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(save);
	}
	
	/**
	 * 根据name模糊查询
	 * @param name
	 * @return
	 */
	public LyhtResultBody<List<PubLayerEntity>> list(String name) {
		List<PubLayerEntity> findAllByName = null;
		try {
			findAllByName = dao.findAllByName(name);
		} catch (Exception e) {
			log.error("====PubLayerService====Method：list====params:" + name, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(findAllByName);
	}
	
	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	public LyhtResultBody<PubLayerEntity> findById(Integer id) {
		PubLayerEntity pubLayerEntity = null;
		try {
			Optional<PubLayerEntity> findById = dao.findById(id);
			if (findById.isPresent()) {
				pubLayerEntity = findById.get();
			}
		} catch (Exception e) {
			log.error("====PubLayerService====findById====params:" + id, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(pubLayerEntity);
	}
	
	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	public LyhtResultBody<Boolean> clear() {
		try {
			jdbcTemplate.execute("TRUNCATE TABLE pub_layer;");
		} catch (Exception e) {
			log.error("====PubLayerService====clear", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody<>(true);
	}

}
