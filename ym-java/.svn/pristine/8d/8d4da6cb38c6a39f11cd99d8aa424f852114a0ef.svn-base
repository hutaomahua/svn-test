package com.lyht.business.abm.land.service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.land.bean.RequisitionPlanDetail;
import com.lyht.business.abm.land.vo.RequisitionPlanVo;
import com.lyht.business.abm.land.dao.LatePeopleDao;
import com.lyht.business.abm.land.dao.LatePopulationDao;
import com.lyht.business.abm.land.dao.RequisitionPlanDao;
import com.lyht.business.abm.land.entity.LatePeopleEntity;
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
public class LatePeopleService {


    private Logger log = LoggerFactory.getLogger(LatePeopleService.class);
    @Autowired
    LatePeopleDao dao;


    public LatePeopleEntity saveYearAnnualPlan(LatePeopleEntity entity) {
    	LatePeopleEntity result = null;
     
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

    public LyhtResultBody<List<LatePeopleEntity>> getList(LyhtPageVO lyhtPageVO,String region,String name) {


        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<LatePeopleEntity> page = dao.getList(region,name,pageable);
        List<LatePeopleEntity> result = page.getContent();
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(result, pageVO);
    }

    public LyhtResultBody<Integer> delete(Integer id) {

        try {
        	LatePeopleEntity one = dao.getOne(id);
            dao.delete(one);
        } catch (Exception e) {
            log.error("=====LandPlanService=====Method=delete=====Params:" + id + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        return new LyhtResultBody<>(id);
    }

    public LyhtResultBody<Integer> deletes(Integer id) {

        try {
        	LatePeopleEntity one = dao.getOne(id);
            dao.delete(one);
        } catch (Exception e) {
            log.error("=====LandPlanService=====Method=delete=====Params:" + id + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        return new LyhtResultBody<>(id);
    }

    public LyhtResultBody<String> batchDel(String ids) {
        List<Integer> idList = null;
        List<LatePeopleEntity> yearAnnualList = null;
        try {
            idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
            yearAnnualList=new ArrayList<>();
            for(Integer integer : idList){
            	LatePeopleEntity e = new LatePeopleEntity();
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
