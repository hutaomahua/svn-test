package com.lyht.business.abm.settle.service;

import java.util.List;
import java.util.Map;

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
import com.lyht.business.abm.removal.service.AbmFamilyService;
import com.lyht.business.abm.settle.dao.SettleInfoDao;
import com.lyht.business.abm.settle.dao.SettleStatusDao;
import com.lyht.business.abm.settle.entity.SettleInfo;
import com.lyht.business.abm.settle.entity.SettleStatus;
import com.lyht.business.abm.settle.vo.SettleVo;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

/**
 * @author HuangTianhao
 * @date 2019年11月28日
 */
@Service("/SettleStatusService")
public class SettleStatusService {
	@Autowired
	private SettleStatusDao statusDao;
	@Autowired
	private SettleInfoDao infoDao;

	private Logger log = LoggerFactory.getLogger(SettleStatusService.class);
	
	//条件分页
	public LyhtResultBody<List<Map>> getList(LyhtPageVO lyhtPageVO, SettleVo vo) {
		List<Map> list;
		LyhtPageVO pageVO;
		try {
			Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
			Page<Map> page = statusDao.getList(pageable, vo.getName(),vo.getTimeStart(),vo.getBuiltStatus());
			String jsonString = JSON.toJSONString(page.getContent());
			list = (List<Map>) JSON.parse(jsonString);
			pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(), page.getTotalElements(),
					lyhtPageVO.getSorter());
		} catch (Exception e) {
			log.error("=====SettleStatusService=====Method=getList=====Params1:" + vo + "Params2:" + lyhtPageVO + "=====",
					e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(list, pageVO);
	}

	//新增status
	public LyhtResultBody<SettleStatus> addStatus(SettleStatus status) {
		SettleStatus result;
		try {
			String nameString = status.getName();
			//名称不可为空
			if (status == null||status.getName()==null ) {
				throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
			} else {
				//判断名称是否与info一致（已通过前端下拉菜单保证，冗余措施）
	            if(infoDao.getNameList(status.getName()).size()==0) {
	            	throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
	            }
				// 修改
				if (status.getId() != null) {			
				}
				// 新增
				else {
					// 内码赋值
					status.setNm(Randomizer.generCode(10));
					status.setInfoNm(infoDao.getNm(nameString));
				}
				result = statusDao.save(status);
			}

		} catch (Exception e) {
			log.error("=====SettleStatusService=====Method=add=====Params:" + status + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}

		return new LyhtResultBody<>(result);
	}
	
    //根据id删除
	public LyhtResultBody<Integer> deleteById(Integer id) {
		try {
			if (id == null) {
				throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
			}
			statusDao.deleteById(id);
		} catch (Exception e) {
			log.error("=====SettleStatusService=====Method=deleteById=====Params:" + id + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(id);
	}
	//批量删除status
		public LyhtResultBody<String> batchStatus(String ids) {
	        List<Integer> idList;
			if (ids == null) {
				throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
			}
			// 转换id整形数组
	        idList = CommonUtil.parseIntegerList(ids);
	        List<SettleStatus> list = statusDao.findAllById(idList);
	        for(SettleStatus s:list) {
	          	try {
	    			statusDao.deleteById(s.getId());
	    		} catch (Exception e) {
	    			log.error("=====SettleStatusService=====Method=batchStatus===deleteById==Params:" + ids + "=====", e);
	    		}
	        }
			return new LyhtResultBody<>(ids);
		}

	//info主表项删除时，根据nm关联删除对应status信息
	public LyhtResultBody<String> delete(String nm) {
		try {
			if (nm == null) {
				throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
			}
			statusDao.delete(nm);
		} catch (Exception e) {
			log.error("=====SettleStatusService=====Method=delete=====Params:" + nm + "=====", e);
		}
		return new LyhtResultBody<>(nm);
	}
	
	
}
