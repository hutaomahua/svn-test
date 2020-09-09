package com.lyht.business.abm.land.service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.land.bean.RequisitionPlanDetail;
import com.lyht.business.abm.land.vo.RequisitionPlanVo;
import com.lyht.business.abm.land.dao.LatePopulationDao;
import com.lyht.business.abm.land.dao.RequisitionPlanDao;
import com.lyht.business.abm.land.entity.LatePopulationEntity;
import com.lyht.business.abm.land.entity.RequisitionPlanEntity;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LatePopulationService {


    private Logger log = LoggerFactory.getLogger(LatePopulationService.class);
    @Autowired
    LatePopulationDao dao;


    public LatePopulationEntity saveYearAnnualPlan(LatePopulationEntity entity) {
    	LatePopulationEntity result = null;
     
            if(entity.getId()!=null&&entity.getId()!=0)
            {
                //修改
                result = dao.save(entity);
            }else
            {

            	entity.setNm(Randomizer.generCode(10));
                result = dao.save(entity);
            }

      
        return result;
    }

    public LyhtResultBody<List<LatePopulationEntity>> getList(LyhtPageVO lyhtPageVO,String region) {


        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<LatePopulationEntity> page = dao.getList(region,pageable);
        List<LatePopulationEntity> result = page.getContent();
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(result, pageVO);
    }

    public LyhtResultBody<Integer> delete(Integer id) {

        try {
        	LatePopulationEntity one = dao.getOne(id);
            dao.delete(one);
        } catch (Exception e) {
            log.error("=====LandPlanService=====Method=delete=====Params:" + id + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        return new LyhtResultBody<>(id);
    }

    public LyhtResultBody<Integer> deletes(Integer id) {

        try {
        	LatePopulationEntity one = dao.getOne(id);
            dao.delete(one);
        } catch (Exception e) {
            log.error("=====LandPlanService=====Method=delete=====Params:" + id + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        return new LyhtResultBody<>(id);
    }

    public LyhtResultBody<String> batchDel(String ids) {
        List<Integer> idList = null;
        List<LatePopulationEntity> yearAnnualList = null;
        try {
            idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
            yearAnnualList=new ArrayList<>();
            for(Integer integer : idList){
            	LatePopulationEntity e = new LatePopulationEntity();
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
