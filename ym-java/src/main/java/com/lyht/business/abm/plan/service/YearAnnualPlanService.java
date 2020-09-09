package com.lyht.business.abm.plan.service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.plan.bean.YearAnnualPlanDetail;
import com.lyht.business.abm.plan.dao.YearAnnualPlanAdjustDao;
import com.lyht.business.abm.plan.dao.YearAnnualPlanDao;
import com.lyht.business.abm.plan.entity.PubNianDuTree;
import com.lyht.business.abm.plan.entity.YearAnnualPlanAdjustEntity;
import com.lyht.business.abm.plan.entity.YearAnnualPlanEntity;
import com.lyht.business.pub.dao.PubYearTree;
import com.lyht.business.pub.entity.PubYearSubject;
import com.lyht.system.pojo.SysStaff;
import com.lyht.util.CommonUtil;
import com.lyht.util.EntityUtils;
import com.lyht.util.Randomizer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import javax.transaction.Transactional;


/**
 * 年度计划编制
 *
 * @author lj
 * 2019/10/22
 */
@Transactional
@Service
public class YearAnnualPlanService {
    private Logger log = LoggerFactory.getLogger(YearAnnualPlanService.class);
    @Autowired
    YearAnnualPlanDao yearAnnualPlanDao;
    @Autowired
    YearAnnualPlanAdjustDao adjustDao;
    
    public void delYear(String region,String year)
    {
    	yearAnnualPlanDao.delYear(region, year);
    }
    public List<PubNianDuTree> getTopBean(String name ,String region,String scode) {

		return EntityUtils.toEntityList(yearAnnualPlanDao.getTopBean(name,region,scode), PubNianDuTree.class);
	}
	 
	public List<PubNianDuTree> findSon(String name ,String region) {
		return EntityUtils.toEntityList(yearAnnualPlanDao.findSon(name,region), PubNianDuTree.class);
	}

	public List<Map> isPresence(String region,String year){
		
		return yearAnnualPlanDao.isPresence(region, year);
	}
    public YearAnnualPlanEntity saveYearAnnualPlan(YearAnnualPlanEntity yearAnnualPlanEntity) {
        if (yearAnnualPlanEntity.getId() == null) {
            yearAnnualPlanEntity.setNm(Randomizer.generCode(10));
        }

        YearAnnualPlanEntity result = yearAnnualPlanDao.save(yearAnnualPlanEntity);;

        return result;
    }
    
    public YearAnnualPlanEntity tzjh(YearAnnualPlanEntity yearAnnualPlanEntity,SysStaff sysStaff,String cause,String type) {
        YearAnnualPlanEntity result = null;

        return result;
    }

    public LyhtResultBody< List<YearAnnualPlanDetail>> getList() {


        List<YearAnnualPlanEntity>  list= yearAnnualPlanDao.getList();

        List<YearAnnualPlanDetail>resultSet=new ArrayList<>();
        if(list != null || list .size() > 0){
            resultSet=getTreeVlaue(list,0);
        }

        return new LyhtResultBody<>(resultSet);
    }

    public LyhtResultBody<Integer> delete(Integer id) {

        try {
            YearAnnualPlanEntity one = yearAnnualPlanDao.getOne(id);
            yearAnnualPlanDao.delete(one);
        } catch (Exception e) {
            log.error("=====YearAnnualPlanService=====Method=delete=====Params:" + id + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        return new LyhtResultBody<>(id);
    }

    public LyhtResultBody<Integer> deletes(Integer id) {

        try {
            YearAnnualPlanEntity one = yearAnnualPlanDao.getOne(id);
            yearAnnualPlanDao.delete(one);
        } catch (Exception e) {
            log.error("=====YearAnnualPlanService=====Method=delete=====Params:" + id + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        return new LyhtResultBody<>(id);
    }

    public LyhtResultBody<String> batchDel(String ids) {
        List<Integer> idList = null;

        return new LyhtResultBody<>(ids);
    }

    public LyhtResultBody<List<YearAnnualPlanEntity>> getDetail(LyhtPageVO lyhtPageVO, String id) {


        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<YearAnnualPlanEntity> page = yearAnnualPlanDao.getDetail(id, pageable);
        List<YearAnnualPlanEntity> result = page.getContent();
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
        page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(result, pageVO);
    }

    public List<YearAnnualPlanDetail> getTreeVlaue(List<YearAnnualPlanEntity> parseArray ,Integer parentNm) {
        List<YearAnnualPlanDetail> childMenu =new ArrayList<>();
        for (YearAnnualPlanEntity dictValueDetail:parseArray) {
            YearAnnualPlanDetail yearAnnualPlanDetail=new YearAnnualPlanDetail();
            yearAnnualPlanDetail.setId(dictValueDetail.getId());
            yearAnnualPlanDetail.setNm(dictValueDetail.getNm());
            yearAnnualPlanDetail.setProjectNm(dictValueDetail.getProjectNm());
            yearAnnualPlanDetail.setUnitNm(dictValueDetail.getUnitNm());
            yearAnnualPlanDetail.setTaskName(dictValueDetail.getTaskName());
            yearAnnualPlanDetail.setBudgetWan(dictValueDetail.getBudgetWan());
            
            yearAnnualPlanDetail.setBudgetWan(dictValueDetail.getBudgetWan());
            yearAnnualPlanDetail.setCapital(dictValueDetail.getCapital());
            yearAnnualPlanDetail.setCompletDate(dictValueDetail.getCompletDate());
            yearAnnualPlanDetail.setCompletTaskNumber(dictValueDetail.getCompletTaskNumber());
            yearAnnualPlanDetail.setDutyUnit(dictValueDetail.getDutyUnit());
            yearAnnualPlanDetail.setLastyearCapital(dictValueDetail.getLastyearCapital());
            yearAnnualPlanDetail.setLastyearCapitalTwo(dictValueDetail.getLastyearCapitalTwo());
            yearAnnualPlanDetail.setNewCapital(dictValueDetail.getNewCapital());
            yearAnnualPlanDetail.setSort(dictValueDetail.getSort());
            yearAnnualPlanDetail.setResidueInvest(dictValueDetail.getResidueInvest());
            yearAnnualPlanDetail.setResidueSumCapital(dictValueDetail.getResidueSumCapital());
            yearAnnualPlanDetail.setSumTaskNumber(dictValueDetail.getSumTaskNumber());
            yearAnnualPlanDetail.setParentId(dictValueDetail.getParentId());
            yearAnnualPlanDetail.setSketch(dictValueDetail.getSketch());
            yearAnnualPlanDetail.setSupervisionUnit(dictValueDetail.getSupervisionUnit());
            yearAnnualPlanDetail.setToBeCapital(dictValueDetail.getToBeCapital());

            Integer nm = dictValueDetail.getId();
            Integer parent = dictValueDetail.getParentId();
            if(parentNm.equals(parent)){
                List<YearAnnualPlanDetail> mapList=getTreeVlaue(parseArray,nm);
                if(mapList!=null || mapList.size() > 0){
                  yearAnnualPlanDetail.setChildren(mapList);
                }
                childMenu.add(yearAnnualPlanDetail);
            }

        }
        return childMenu;
    }
}
