package com.lyht.business.abm.plan.service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.plan.dao.ProjectPlanAdjustDao;
import com.lyht.business.abm.plan.dao.ProjectPlanDao;
import com.lyht.business.abm.plan.entity.ProjectPlanAdjustEntity;
import com.lyht.business.abm.plan.entity.ProjectPlanEntity;
import com.lyht.business.abm.plan.entity.YearAnnualPlanAdjustEntity;
import com.lyht.business.abm.plan.entity.YearAnnualPlanEntity;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProjectPlanAdjustService {

    private Logger log = LoggerFactory.getLogger(ProjectPlanAdjustService.class);
    @Autowired
    ProjectPlanAdjustDao projectAdjustPlanDao;


    

    public LyhtResultBody<List<Map>> getList(LyhtPageVO lyhtPageVO, String region,String area,String taskName) {


        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<Map> page = projectAdjustPlanDao.getList(region,area,taskName, pageable);
    	String jsonString = JSON.toJSONString(page.getContent());
		List<Map> list = (List<Map>) JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
    }

 
    public LyhtResultBody<List<Map>> getLogList(LyhtPageVO lyhtPageVO, String region,String area,String taskName) {



        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<Map> page = projectAdjustPlanDao.getLogList(region,area,taskName, pageable);
    	String jsonString = JSON.toJSONString(page.getContent());
		List<Map> list = (List<Map>) JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
    }
    
    public List<ProjectPlanAdjustEntity> getDetails(Integer id)
    {
    	List<ProjectPlanAdjustEntity> list = null;
    	try {
    		list=projectAdjustPlanDao.getDetails(id);
    	} catch (Exception e) {
            log.error("=====ProjectPlanAdjustService=====Method=getDetails=====Params:" + id + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
    	
    	return list;
    	
    }
    public List<Map> getNm( String nm) {


      
        List<Map> page = projectAdjustPlanDao.getNm(nm);
      
        return page;
    }

    public List<Map> getSuccessively(String nm,String id){
        List<Map> list = null;
        try {
            list=projectAdjustPlanDao.getSuccessively(nm,id);

        } catch (Exception e) {
            log.error("=====YearAnnualPlanAdjustService=====Method=getSuccessively=====Params:" + nm + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }

        return list;
    }
}
