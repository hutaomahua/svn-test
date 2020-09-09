package com.lyht.business.doc.service;

import java.math.BigInteger;
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
import com.lyht.business.doc.dao.DocDirectoryDao;
import com.lyht.business.doc.entity.DocDirectory;
import com.lyht.business.doc.pojo.DocDirectoryDetail;
import com.lyht.business.doc.pojo.DocDirectoryVO;
import com.lyht.util.Randomizer;

@Transactional
@Service
public class DocDirectoryService {

	@Autowired
	DocDirectoryDao docDirectoryDao;

	// 下拉框
	public List<DocDirectoryDetail> getDocDirSuper(String docType) {
		List<Map> list = docDirectoryDao.getDocDirSuper(docType);
		String jsonString = JSON.toJSONString(list);
		List<DocDirectoryDetail> superList = JSON.parseArray(jsonString, DocDirectoryDetail.class);
		for (DocDirectoryDetail doc : superList) {
			List<DocDirectoryDetail> childList = getDocDirList(doc.getKey());
			if (childList.size() > 0) {
				doc.setChildren(childList);
			}
		}
		// 结果集
		return superList;
	}

	public List<DocDirectoryDetail> getDocDirList(Integer superId) {
		List<DocDirectoryDetail> list = getDocDirChild(superId);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				DocDirectoryDetail mp = list.get(i);
				List<DocDirectoryDetail> m = getDocDirList(mp.getKey());
				if (m.size() > 0) {
					mp.setChildren(m);
				}
			}
		}
		return list;
	}

	public List<DocDirectoryDetail> getDocDirChild(Integer superId) {
		List<Map> list = docDirectoryDao.getDocDirChild(superId);
		String jsonString = JSON.toJSONString(list);
		List<DocDirectoryDetail> childList = JSON.parseArray(jsonString, DocDirectoryDetail.class);
		return childList;
	}

	// 分页
	@SuppressWarnings("unchecked")
	public LyhtResultBody<List<DocDirectory>> page(String docType, LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<DocDirectory> page = docDirectoryDao.page(docType, pageable);
		for (DocDirectory map : page.getContent()) {
			List<DocDirectory> childList = getList(map.getId());
			if (childList.size() > 0) {
				map.setChildren(childList);
			}
		}
		// 结果集
		String jsonString = JSON.toJSONString(page.getContent());
		List<DocDirectory> list = (List<DocDirectory>) JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}

	public List<DocDirectory> getList(Integer superId) {
		List<DocDirectory> list = getChild(superId);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				DocDirectory mp = list.get(i);
				List<DocDirectory> m = getList(mp.getId());
				if (m.size() > 0) {
					mp.setChildren(m);
				}
			}
		}
		return list;
	}

	public List<DocDirectory> getChild(Integer superId) {
		List<DocDirectory> list = docDirectoryDao.getChild(superId);
		return list;
	}

	// 下拉框
	public List<DocDirectoryVO> getSuperIdSelect() {
		List<Map> list = docDirectoryDao.getSuperIdSelect();
		String jsonString = JSON.toJSONString(list);
		List<DocDirectoryVO> superList = JSON.parseArray(jsonString, DocDirectoryVO.class);
		for (DocDirectoryVO doc : superList) {
			List<DocDirectoryVO> childList = getListSelect(doc.getKey());
			if (childList.size() > 0) {
				doc.setChildren(childList);
			}
		}
		// 结果集
		return superList;
	}

	public List<DocDirectoryVO> getListSelect(Integer superId) {
		List<DocDirectoryVO> list = getChildSelect(superId);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				DocDirectoryVO mp = list.get(i);
				List<DocDirectoryVO> m = getListSelect(mp.getKey());
				if (m.size() > 0) {
					mp.setChildren(m);
				}
			}
		}
		return list;
	}

	public List<DocDirectoryVO> getChildSelect(Integer superId) {
		List<Map> list = docDirectoryDao.getChildSelect(superId);
		String jsonString = JSON.toJSONString(list);
		List<DocDirectoryVO> childList = JSON.parseArray(jsonString, DocDirectoryVO.class);
		return childList;
	}

	@SuppressWarnings("unchecked")
	public LyhtResultBody<List<DocDirectory>> list() {
		List<DocDirectory> dirList = docDirectoryDao.findAll();
		String jsonString = JSON.toJSONString(dirList);
		List<DocDirectory> list = (List<DocDirectory>) JSON.parse(jsonString);
		return new LyhtResultBody<>(list);
	}

	/**
	 * 导出
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> getList() {
		List<Map> list = docDirectoryDao.getList();
		return list;
	}
	
	public DocDirectory update(DocDirectory docDirectory) {
		// 参数校验
		if (docDirectory == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		String sCode = "";// 最终返回的sCode
		String fCode = "";// 最终返回的fCode
		// 父级为空，父级不为空 新增修共同操作 给sCode fCode 赋值
		if (docDirectory.getSuperId() == null) {// 父级为空时 找到当前父类为空最大的scode
			// 查询原数据对象
			DocDirectory children = docDirectoryDao.getOne(docDirectory.getId());
			if (children.getSuperId() != docDirectory.getSuperId()) {
				// 拿到superid为空的最大scode
				String maxSCode = docDirectoryDao.findMaxSCode();
				if (StringUtils.isNotBlank(maxSCode)) {// 判断是否为第一条数据
					// 将拿到的最大scode加一
					fCode = sCode = String.format("%03d", Integer.parseInt(maxSCode) + 1);// 返回最终的sCode fCode
				} else {
					fCode = sCode = "001";// 返回最终的sCode fCode
				}
				docDirectory.setfCode(fCode);
				docDirectory.setsCode(sCode);
			}
			
		} else {// 拥有父级 根据父级查询父级下子级当前最大sCode
			// 查询当前父级对象
			DocDirectory father = docDirectoryDao.getOne(docDirectory.getSuperId());
			// 查询原数据对象
			DocDirectory children = docDirectoryDao.getOne(docDirectory.getId());
			if (children.getSuperId() != docDirectory.getSuperId()) {
				String maxSCode = docDirectoryDao.findMaxSCodeBySuperId(docDirectory.getSuperId());
				if (StringUtils.isBlank(maxSCode)) {// 判断是否为当前父级下第一条数据
					sCode = "001";// 返回最终的sCode fCode
					fCode = father.getfCode() + "" + sCode;// 拼接完整的fCode
				} else {// 否则
						// 将拿到的最大scode加一
					sCode = String.format("%03d", Integer.parseInt(maxSCode) + 1);// 返回最终的sCode fCode
					fCode = father.getfCode() + "" + sCode;// 拼接完整的fCode
				}
				docDirectory.setfCode(fCode);
				docDirectory.setsCode(sCode);
			}
			
		}
		System.out.println("docDirectory:"+docDirectory);
		docDirectoryDao.updateDir(docDirectory.getDocType(), docDirectory.getSubCode(), docDirectory.getSubName(),
				docDirectory.getRemark(), docDirectory.getSuperId(),docDirectory.getId(),docDirectory.getsCode(),docDirectory.getfCode());
		return docDirectory;// 返回修改长短编码后的对象
	}

	public DocDirectory save(DocDirectory docDirectory) {
		// 参数校验
		if (docDirectory == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		if (StringUtils.isBlank(docDirectory.getNm())) {
			docDirectory.setNm(Randomizer.generCode(10));
		}
		String sCode = "";// 最终返回的sCode
		String fCode = "";// 最终返回的fCode
		// 父级为空，父级不为空 新增修共同操作 给sCode fCode 赋值
		if (docDirectory.getSuperId() == null) {// 父级为空时 找到当前父类为空最大的scode
			// 查询原数据对象
			// 拿到superid为空的最大scode
			String maxSCode = docDirectoryDao.findMaxSCode();
			if (StringUtils.isNotBlank(maxSCode)) {// 判断是否为第一条数据
				// 将拿到的最大scode加一
				fCode = sCode = String.format("%03d", Integer.parseInt(maxSCode) + 1);// 返回最终的sCode fCode
			} else {
				fCode = sCode = "001";// 返回最终的sCode fCode
			}
		} else {// 拥有父级 根据父级查询父级下子级当前最大sCode
			// 查询当前父级对象
			DocDirectory father = docDirectoryDao.getOne(docDirectory.getSuperId());
			// 查询原数据对象
			String maxSCode = docDirectoryDao.findMaxSCodeBySuperId(docDirectory.getSuperId());
			if (StringUtils.isBlank(maxSCode)) {// 判断是否为当前父级下第一条数据
				sCode = "001";// 返回最终的sCode fCode
				fCode = father.getfCode() + "" + sCode;// 拼接完整的fCode
			} else {// 否则
					// 将拿到的最大scode加一
				sCode = String.format("%03d", Integer.parseInt(maxSCode) + 1);// 返回最终的sCode fCode
				fCode = father.getfCode() + "" + sCode;// 拼接完整的fCode
			}
		}
		docDirectory.setfCode(fCode);
		docDirectory.setsCode(sCode);
		DocDirectory result = docDirectoryDao.save(docDirectory);
		return result;// 返回修改长短编码后的对象
	}

	// 修改子级code
	
	public void updateDirSon(String oldFCode, String newFCode,String docType) {
		docDirectoryDao.updateDirSon(oldFCode, newFCode, oldFCode,docType);
	};

	/**
	 * 添加 修改
	 * 
	 * @param t_funds_info
	 * @return
	 */
//	@Transactional
//	public LyhtResultBody<DocDirectory> save1(DocDirectory docDirectory) {
//		// 参数校验
//		if (docDirectory == null) {
//			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
//		}
//		// 内码赋值 长编码赋值
//		String nm = docDirectory.getNm();
//		String fCode = docDirectory.getfCode();
//		if (StringUtils.isBlank(nm)) {
//			docDirectory.setNm(Randomizer.generCode(10));
//			// 当父类不位空时
//			if (docDirectory.getSuperId() != null && docDirectory.getSuperId() > 0) {
//				if (docDirectory.getSuperId() != docDirectory.getId()) {
//					DocDirectory father = new DocDirectory();// 找到父类 拿到父类全编码
//					father = docDirectoryDao.getOne(docDirectory.getSuperId());
//					// 拿到父类下最大短编码对象
//					String maxSCodeObj = docDirectoryDao.findMaxSCodeBySuperId(docDirectory.getSuperId());
//					if (maxSCodeObj != null) {
//						// 拿到scode 加一
//						String str = String.format("%03d", Integer.parseInt(maxSCodeObj) + 1);
//						docDirectory.setsCode(str);
//						docDirectory.setfCode(father.getfCode() + (str + ""));
//					} else {
//						docDirectory.setsCode("001");
//						docDirectory.setfCode(father.getfCode() + "001");
//					}
//				} else {
//					throw new LyhtRuntimeException(LyhtExceptionEnums.SUPER_ERROR);
//				}
//			} else {// 找到无父类科目的最大短编码
//				String maxSCodeObj = docDirectoryDao.findMaxSCode();
//				String str = String.format("%03d", Integer.parseInt(maxSCodeObj) + 1);
//				docDirectory.setsCode(str);
//				docDirectory.setfCode(str);
//			}
//
//		} else {
//			DocDirectory directory = docDirectoryDao.getOne(docDirectory.getId());
//			if (docDirectory.getSuperId() != directory.getSuperId()) {
//				if (docDirectory.getSuperId() != null && docDirectory.getSuperId() > 0) {
//					if (docDirectory.getSuperId() != docDirectory.getId()) {
//						DocDirectory father = new DocDirectory();
//						father = docDirectoryDao.getOne(docDirectory.getSuperId());
//						String maxSCodeObj = docDirectoryDao.findMaxSCodeBySuperId(docDirectory.getSuperId());
//						if (maxSCodeObj != null) {
//							// 拿到scode 加一
//							String str = String.format("%03d", Integer.parseInt(maxSCodeObj) + 1);
//							docDirectory.setsCode(str);
//							docDirectory.setfCode(father.getfCode() + (str + ""));
//						} else {
//							docDirectory.setsCode("001");
//							docDirectory.setfCode(father.getfCode() + "001");
//						}
//
//					} else {
//						throw new LyhtRuntimeException(LyhtExceptionEnums.SUPER_ERROR);
//					}
//				} else {
//					String maxSCodeObj = docDirectoryDao.findMaxSCode();
//					String str = String.format("%03d", Integer.parseInt(maxSCodeObj) + 1);
//					docDirectory.setsCode(str);
//					docDirectory.setfCode(str);
//				}
//			}
//		}
//		DocDirectory result = docDirectoryDao.save(docDirectory);
//		docDirectoryDao.updateDirSon(fCode, result.getfCode(), fCode);
//		return new LyhtResultBody<>(result);
//	}

	/**
	 * 单个删除
	 * 
	 * @param id
	 * @return
	 */
	public LyhtResultBody<String> delete(String fCode) {
		// 参数校验
		if (fCode == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		docDirectoryDao.deleteByFCode(fCode);
		return new LyhtResultBody<>(fCode);
	}

	/**
	 * 删除
	 * 
	 * @param fCode
	 * @return
	 */
	@Transactional
	public LyhtResultBody<String> deleteByFCode(String fCode) {
		// 参数校验
		if (fCode == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		docDirectoryDao.deleteByFCode(fCode + "%");
		return new LyhtResultBody<>(fCode);
	}

	/**
	 * 查询最上级的部门
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getTopBean01() {
		return docDirectoryDao.getTopBean01();
	}

	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @param superId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> findBySuperId01(int superId) {
		return docDirectoryDao.findBySuperId01(superId);
	}

	/**
	 * 查询最上级的部门
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getTopBean() {
		return docDirectoryDao.getTopBean();
	}

	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @param superId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> findBySuperId(int superId) {
		return docDirectoryDao.findBySuperId(superId);
	}

	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @param superId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> findBySuperIdAndLike(int superId) {
		return docDirectoryDao.findBySuperIdAndLike(superId);
	}

	/**
	 * 档案编号
	 * 
	 * @param docDirectory
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public LyhtResultBody<String> dcode(DocDirectory docDirectory) {
		LyhtResultBody<String> result = new LyhtResultBody<>();
		if (docDirectory != null && StringUtils.isNotBlank(docDirectory.getNm())) {
			// StringBuffer codeSql = new StringBuffer("SELECT sub_code code FROM
			// t_doc_directory WHERE nm='" + docDirectory.getNm() + "'");
			List<Map> codeList = docDirectoryDao.findSubCode(docDirectory.getNm());
			Map codeMap = codeList.get(0);
			String code = (String) codeMap.get("code") + "%";
			List<Map> countList = docDirectoryDao.findCountList(code);
			Map countMap = countList.get(0);
			int count = ((BigInteger) countMap.get("count")).intValue();
			if (count > 0) {
				String format = String.format("%06d", ++count);
				result.setList(code + format);
			} else {
				result.setList(code + "000001");
			}
		}
		return result;
	}

}
