package com.lyht.business.doc.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.doc.dao.DocInfoDao;
import com.lyht.business.doc.entity.DocInfo;
import com.lyht.business.doc.entity.DocStatist;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

/**
 * 数据档案权限： 先按部门划分权限（各参建单位只能看到自己单位的档案数据）。 用户可选择某条档案数据，共享给某用户查阅
 * （可共享给多个人，被共享人不允许在共享文档，被共享人只有查看档案的权限）。
 * 
 * @author wzw
 *
 */
@Service
public class DocInfoService {
	private static Logger logger = LoggerFactory.getLogger(DocInfoService.class);

	@Autowired
	DocInfoDao docInfoDao;

	public LyhtResultBody<List<Map>> shareDoc(LyhtPageVO lyhtPageVO, String staffNm, String docType) {
		// 分页,排序
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = docInfoDao.shareDoc(staffNm, docType, pageable);
		// 结果集
		String jsonString = JSON.toJSONString(page.getContent());
		List<Map> list = (List<Map>) JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}

	/**
     * 分页查询
     *
     * @param pageable
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public LyhtResultBody<List<Map>> page(LyhtPageVO lyhtPageVO, String type, String word, String dType, String fCode,
                    String staffNm) {
            // 分页,排序
            Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
            Page<Map> page = docInfoDao.page(type, word, dType, fCode, staffNm, pageable);
            // 结果集
            String jsonString = JSON.toJSONString(page.getContent());
            List<Map> list = (List<Map>) JSON.parse(jsonString);
            LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                            page.getTotalElements(), lyhtPageVO.getSorter());
            return new LyhtResultBody<>(list, pageVO);
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LyhtResultBody<List<Map>> serach(String word) {
		List<Map> infoList = docInfoDao.serach(word);
		String jsonString = JSON.toJSONString(infoList);
		List<Map> list = (List<Map>) JSON.parse(jsonString);
		return new LyhtResultBody<>(list);
	}

	/**
	 * 根据Id查询
	 */
	public LyhtResultBody<DocInfo> findById(Integer id) {
		DocInfo docInfo = docInfoDao.getOne(id);
		return new LyhtResultBody<>(docInfo);
	}

//	/**
//	 * 分页查询
//	 *
//	 * @param pageable
//	 * @return
//	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public LyhtResultBody<List<Map>> page(LyhtPageVO lyhtPageVO, String type, String word, String dType, String level) {
//		// 分页,排序
//		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
//		Page<Map> page = docInfoDao.page(type, word , dType, pageable);
//		// 结果集
//		String jsonString = JSON.toJSONString(page.getContent());
//		List<Map> list = (List<Map>) JSON.parse(jsonString);
//		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
//				page.getTotalElements(), lyhtPageVO.getSorter());
//		return new LyhtResultBody<>(list, pageVO);
//	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public List<Map> list(String type, String word, String dType, String level) {
		List<Map> list = docInfoDao.list(type, word, dType);
		return list;
	}

	/**
	 * 添加 修改
	 *
	 * @param DocInfo
	 * @return
	 */
	public LyhtResultBody<DocInfo> save(DocInfo docInfo) {
		// 参数校验
		if (docInfo == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// 内码赋值
		String nm = docInfo.getNm();
		if (StringUtils.isBlank(nm)) {
			docInfo.setNm(Randomizer.generCode(10));
		}
		docInfo.setFlag(1);
		DocInfo result = docInfoDao.save(docInfo);
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
		docInfoDao.deleteById(id);
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
		List<DocInfo> deleteDocInfo = docInfoDao.findAllById(idList);
		docInfoDao.deleteInBatch(deleteDocInfo);
		return new LyhtResultBody<>(ids);
	}

	public List<DocInfo> getByids(String ids) {
		if (StringUtils.isBlank(ids)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		List<Integer> idList = null;
		try {
			idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return docInfoDao.findAllById(idList);
	}

	/**
	 * 导入excel数据
	 *
	 * @param list
	 */
	public List<DocInfo> importExcelData(List<DocInfo> list) {
		List<DocInfo> importList = null;
		try {
			for (DocInfo docInfo : list) {
				if (docInfo.getId() == null && StringUtils.isBlank(docInfo.getNm())) {
					docInfo.setNm(Randomizer.generCode(10));
				}
			}
			importList = docInfoDao.saveAll(list);
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return importList;
	}

	/**
	 * 获取分了统计图表信息
	 * 
	 * @param docStatist
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<DocStatist> getTypeStatist(DocStatist docStatist) {
		String diming = "%" + docStatist.getDiming() + "%";
		// 查询结果
		List<Map> findAllByParams = docInfoDao.getTypeStatist(diming);
		String jsonString = JSON.toJSONString(findAllByParams);
		List<DocStatist> parseArray = JSON.parseArray(jsonString, DocStatist.class);
		return parseArray;
	}

}
