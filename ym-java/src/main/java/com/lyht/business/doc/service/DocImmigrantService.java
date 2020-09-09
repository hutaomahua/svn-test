package com.lyht.business.doc.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.doc.dao.DocImmigrantDao;
import com.lyht.business.doc.entity.DocImmigrant;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

@Service
public class DocImmigrantService {
	
	@Autowired
	private DocImmigrantDao dao;

	/**
	 * 分页查询
	 *
	 * @param pageable
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LyhtResultBody<List<Map>> page(LyhtPageVO lyhtPageVO, String word, String type) {
		// 分页,排序
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = dao.page(word , type,pageable);
		// 结果集
		String jsonString = JSON.toJSONString(page.getContent());
		List<Map> list = (List<Map>) JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}
	
	/**
	 * 添加 修改
	 *
	 * @param t_funds_info
	 * @return
	 */
	public LyhtResultBody<DocImmigrant> save(DocImmigrant docImmigrant) {
		// 参数校验
		if (docImmigrant == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// 内码赋值
		String nm = docImmigrant.getNm();
		if (StringUtils.isBlank(nm)) {
			docImmigrant.setNm(Randomizer.generCode(10));
		}
		docImmigrant.setState("1");
		DocImmigrant result = dao.save(docImmigrant);
		return new LyhtResultBody<>(result);
	}

	/**
	 * 单个删除
	 *
	 * @param id
	 * @return
	 */
	public LyhtResultBody<Integer> delete(Integer id) {
		// 参数校验
		if (id == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		dao.deleteById(id);
		return new LyhtResultBody<>(id);
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	public LyhtResultBody<String> batchDel(String ids) {
		if (StringUtils.isBlank(ids)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		List<Integer> idList = null;
		try {
			idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		List<DocImmigrant> deleteDocInfo = dao.findAllById(idList);
		dao.deleteInBatch(deleteDocInfo);
		return new LyhtResultBody<>(ids);
	}
	
	public List<DocImmigrant> getByids(String ids) {
		if (StringUtils.isBlank(ids)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		List<Integer> idList = null;
		try {
			idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return dao.findAllById(idList);
	}

	
}
