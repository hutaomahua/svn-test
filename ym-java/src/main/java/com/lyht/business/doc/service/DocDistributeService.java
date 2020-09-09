package com.lyht.business.doc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

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
import com.lyht.business.doc.dao.DocDistributeDao;
import com.lyht.business.doc.entity.DocDistribute;
import com.lyht.business.doc.pojo.DocDistributeVO;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

@Service
public class DocDistributeService {

	@Autowired
	private DocDistributeDao dao;
	
	@Transactional
	public Integer deteleByDataNm(String dataNm){
		Integer count = dao.deteleByDataNm(dataNm);
		return count;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })//查询已共享文档
	public LyhtResultBody<List<DocDistributeVO>> page(LyhtPageVO lyhtPageVO, String issueNm, String docType) {
		// 分页,排序
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = dao.page(issueNm, docType, pageable);
		// 结果集
		String jsonString = JSON.toJSONString(page.getContent());
		List<DocDistributeVO> result = (List<DocDistributeVO>) JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(result, pageVO);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })//查询被共享文档
	public LyhtResultBody<List<DocDistributeVO>> page01(LyhtPageVO lyhtPageVO, String staffNm, String docType) {
		// 分页,排序
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = dao.page01(staffNm, docType, pageable);
		// 结果集
		String jsonString = JSON.toJSONString(page.getContent());
		List<DocDistributeVO> result = (List<DocDistributeVO>) JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(result, pageVO);
	}

	/**
	 * 批量新增
	 */
	public LyhtResultBody<List<DocDistribute>> batchSave(List<DocDistribute> disList) {
		LyhtResultBody<List<DocDistribute>> result = new LyhtResultBody<>();
		for (DocDistribute dis : disList) {
				if (StringUtils.isBlank(dis.getNm())) {//如果数组内id 则判断为新增 按新增处理
					dis.setNm(Randomizer.generCode(10));
					dis.setSourceTable("t_doc_info");
				}
		}
		for (int i = 0; i < disList.size(); i++) {
			
			Integer count = dao.getCount(disList.get(i).getDataNm(), disList.get(i).getStaffNm());
			if(count > 0) {
				disList.remove(i);
				result.setMsg("共享成功，重复共享文档已过滤，请勿重复共享");
			}
			
		}
		List<DocDistribute> list = new ArrayList<DocDistribute>();
		if (disList.size() > 0) {
			list = dao.saveAll(disList);
		}
		result.setList(list);
		return result;
	}

	/**
	 * 添加 修改
	 *
	 * @param t_funds_info
	 * @return
	 */
	public LyhtResultBody<DocDistribute> save(DocDistribute docDistribute) {
		// 参数校验
		if (docDistribute == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// 内码赋值
		String nm = docDistribute.getNm();
		if (StringUtils.isBlank(nm)) {
			docDistribute.setNm(Randomizer.generCode(10));
		}
		DocDistribute result = dao.save(docDistribute);
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
		List<DocDistribute> deleteDocInfo = dao.findAllById(idList);
		dao.deleteInBatch(deleteDocInfo);
		return new LyhtResultBody<>(ids);
	}

	public List<DocDistribute> getByids(String ids) {
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
