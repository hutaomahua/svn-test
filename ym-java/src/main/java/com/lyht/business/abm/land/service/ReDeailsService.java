package com.lyht.business.abm.land.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.land.dao.LateCountyDao;
import com.lyht.business.abm.land.dao.ReDeailsDao;
import com.lyht.business.abm.land.entity.ReDeailsEntity;
import com.lyht.util.CommonUtil;

@Service
public class ReDeailsService {
	 private Logger log = LoggerFactory.getLogger(ReDeailsService.class);
	    @Autowired
	    ReDeailsDao dao;
	    

	    public ReDeailsEntity saveYearAnnualPlan(ReDeailsEntity entity) {
	    	ReDeailsEntity result = null;
	     
	            if(entity.getId()!=null&&entity.getId()!=0)
	            {
	                //修改
	                result = dao.save(entity);
	            }else
	            {
	                result = dao.save(entity);
	            }

	      
	        return result;
	    }

	    public LyhtResultBody<List<ReDeailsEntity>> getList(LyhtPageVO lyhtPageVO,String nm) {


	        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
	        Page<ReDeailsEntity> page = dao.getList(nm,pageable);
	        List<ReDeailsEntity> result = page.getContent();
	        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
	                page.getTotalElements(), lyhtPageVO.getSorter());
	        return new LyhtResultBody<>(result, pageVO);
	    }

	    public LyhtResultBody<Integer> delete(Integer id) {

	        try {
	        	ReDeailsEntity one = dao.getOne(id);
	            dao.delete(one);
	        } catch (Exception e) {
	            log.error("=====LandPlanService=====Method=delete=====Params:" + id + "=====", e);
	            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
	        }
	        return new LyhtResultBody<>(id);
	    }

	    public LyhtResultBody<Integer> deletes(Integer id) {

	        try {
	        	ReDeailsEntity one = dao.getOne(id);
	            dao.delete(one);
	        } catch (Exception e) {
	            log.error("=====LandPlanService=====Method=delete=====Params:" + id + "=====", e);
	            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
	        }
	        return new LyhtResultBody<>(id);
	    }

	    public LyhtResultBody<String> batchDel(String ids) {
	        List<Integer> idList = null;
	        List<ReDeailsEntity> yearAnnualList = null;
	        try {
	            idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
	            yearAnnualList=new ArrayList<>();
	            for(Integer integer : idList){
	            	ReDeailsEntity e = new ReDeailsEntity();
	                e.setId(integer);
	                yearAnnualList.add(e);
	            }
	            dao.deleteInBatch(yearAnnualList);
	        } catch (Exception e) {
	            log.error("=====LandPlanService=====Method=batchDel=====Params:" + ids + "=====", e);
	            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
	        }
	        return new LyhtResultBody<>(ids);
	    }

}
