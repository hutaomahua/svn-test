package com.lyht.business.abm.settle.service;
/**
* @author HuangTianhao
* @date 2019年11月28日 
*/

import java.util.ArrayList;
import java.util.Date;

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
import com.lyht.business.abm.land.vo.RequisitionPlanVo;
import com.lyht.business.abm.settle.dao.SettleInfoDao;
import com.lyht.business.abm.settle.dao.SettleStatusDao;
import com.lyht.business.abm.settle.entity.SettleInfo;
import com.lyht.business.abm.settle.entity.SettleStatus;
import com.lyht.business.abm.settle.vo.SettleInfoVo;
import com.lyht.business.abm.settle.vo.SettleVo;
import com.lyht.business.letter.entity.Letters;
import com.lyht.business.letter.vo.LettersVO;
import com.lyht.business.rests.pojo.LetterManagerEntity;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.settle.entity.*;

@Service("/SettleInfoService")
public class SettleInfoService {
	@Autowired
	private SettleInfoDao infoDao;
	@Autowired
	private SettleStatusService service;
	private Logger log = LoggerFactory.getLogger(SettleStatusService.class);
    
	//查询返回所有居民点名称
	public LyhtResultBody<List<String>> getNameList(){
        String string = null;
		List<SettleInfo> lists = infoDao.getNameList(string);
		List<String> list = new ArrayList<String>();
		for(SettleInfo s:lists) {
			String str = s.getName();
			list.add(str);
		}		
		return new LyhtResultBody<List<String>>(list);
	}
	//条件分页查询
	public LyhtResultBody<List<Map>> getList(LyhtPageVO lyhtPageVO, SettleVo vo) {
		List<Map> list;
		LyhtPageVO pageVO;
		try {
			Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
			Page<Map> page = infoDao.getList(pageable, vo.getName(), vo.getRegion(), vo.getRegionType(),vo.getSetttleType());
			String jsonString = JSON.toJSONString(page.getContent());
			list = (List<Map>) JSON.parse(jsonString);
			pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(), page.getTotalElements(),
					lyhtPageVO.getSorter());
		} catch (Exception e) {
			log.error("=====SettleInfoService=====Method=getList=====Params1:" + vo + "Params2:" + lyhtPageVO + "=====",
					e);
			list = null;pageVO = null;
		}
		return new LyhtResultBody<>(list, pageVO);
	}
   //新增或修改info
	public LyhtResultBody<SettleInfo> addInfo(SettleInfo info) {
		SettleInfo result;
		LyhtResultBody<SettleInfo> re;
		try {
			if (info == null) {
				throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
			}
			// 修改
			if (info.getId() != null) {
			}
			// 新增
			else {
				//重名判断
	            if(info.getName()!=null&&infoDao.getNameList(info.getName()).size()>0) {
	            	throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
	            }
				// 内码赋值
				info.setNm(Randomizer.generCode(10));
			}
			result = infoDao.save(info);
			re = new LyhtResultBody<>(0, true, result);
		} catch (Exception e) {
			log.error("=====SettleInfoService=====Method=addInfo=====Params:" + info + "=====", e);
			result = null;
			re = new LyhtResultBody<>(-1, false, null);
			return re;
		}
		return re;
	}

	//删除info
	public LyhtResultBody<Integer> deleteInfo(Integer id) {

		if (id == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		SettleInfo s = infoDao.findById(id).get();

		try {
			infoDao.deleteById(id);
		} catch (Exception e) {
			log.error("=====SettleInfoService=====Method=deleteInfo=====Params:" + id + "=====", e);
		}
		//关联删除status
		try {
			service.delete(s.getNm());
		} catch (Exception e) {
			log.error("=====SettleInfoService=====Method=deleteInfo=====Params:" + id + "=====", e);
		}
		return new LyhtResultBody<>(id);
	}
	//批量删除info
	public LyhtResultBody<String> batchInfo(String ids) {
        List<Integer> idList;
		if (ids == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// 转换id整形数组
        idList = CommonUtil.parseIntegerList(ids);
        List<SettleInfo> list = infoDao.findAllById(idList);
        for(SettleInfo s:list) {
          	try {
    			infoDao.deleteById(s.getId());
    		} catch (Exception e) {
    			log.error("=====SettleInfoService=====Method=batchInfo===deleteById==Params:" + ids + "=====", e);
    		}
          	//关联删除status
    		try {
    			service.delete(s.getNm());
    		} catch (Exception e) {
    			log.error("=====SettleInfoService=====Method=batchInfo===delete==Params:" + ids + "=====", e);
    		}
        }
		return new LyhtResultBody<>(ids);
	}

}
