package com.lyht.business.abm.land.service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.land.bean.RequisitionPlanDetail;
import com.lyht.business.abm.land.vo.RequisitionPlanVo;
import com.lyht.business.abm.plan.dao.PublicityDao;
import com.lyht.business.abm.land.dao.RequisitionPlanDao;
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
public class RequisitionPlanService {


    private Logger log = LoggerFactory.getLogger(RequisitionPlanService.class);
    @Autowired
    RequisitionPlanDao landPlanDao;
    @Autowired
	 PublicityDao publicityDaoao;

    public RequisitionPlanEntity saveYearAnnualPlan(RequisitionPlanEntity LandPlanEntity) {
        RequisitionPlanEntity result = null;
        try {
            if(LandPlanEntity.getId()!=null&&LandPlanEntity.getId()!=0)
            {
                //修改
                result = landPlanDao.save(LandPlanEntity);
            }else
            {

                LandPlanEntity.setNm(Randomizer.generCode(10));
                result = landPlanDao.save(LandPlanEntity);
            }

        } catch (Exception e) {
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        return result;
    }

    public LyhtResultBody<List<Map>> getList(LyhtPageVO lyhtPageVO, RequisitionPlanVo requisitionPlanDetail) {


        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<Map> page = landPlanDao.getList(pageable,requisitionPlanDetail.getRegion(),requisitionPlanDetail.getTaskName(),requisitionPlanDetail.getTaskType());
        List<Map> result = page.getContent();
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(result, pageVO);
    }
    public List<Map> getHdListExcl(String nm){
		   return landPlanDao.getHdListExcl(nm);
	   }
    public LyhtResultBody<List<Map>> getHdList(LyhtPageVO lyhtPageVO, String region,String name,String nm) {
        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        String flag=null;
        List<String> str=new ArrayList<>();
        if(!"".equals(nm)&&null!=nm){
      	  Page<Map> page1 = publicityDaoao.getListDetails(pageable,nm);
  	      List<Map> list1 = page1.getContent();  	    
  	      if(list1.size()>0){
  	    	  for (int i = 0; i < list1.size(); i++) {
  	    		str.add(list1.get(i).get("nm").toString());
  	  		}
  	    	flag="true";
  	      }else{
  	    	str.add("a999999");
  	      }      	
  	    
      }else{
    	  str.add("a999999");
      }
        Page<Map> page = landPlanDao.getHdList(pageable,region,name,str);
        List<Map> result = page.getContent();
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(result, pageVO);
    }

    public LyhtResultBody<Integer> delete(Integer id) {

        try {
            RequisitionPlanEntity one = landPlanDao.getOne(id);
            landPlanDao.delete(one);
        } catch (Exception e) {
            log.error("=====LandPlanService=====Method=delete=====Params:" + id + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        return new LyhtResultBody<>(id);
    }

    public LyhtResultBody<Integer> deletes(Integer id) {

        try {
            RequisitionPlanEntity one = landPlanDao.getOne(id);
            landPlanDao.delete(one);
        } catch (Exception e) {
            log.error("=====LandPlanService=====Method=delete=====Params:" + id + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        return new LyhtResultBody<>(id);
    }

    public LyhtResultBody<String> batchDel(String ids) {
        List<Integer> idList = null;
        List<RequisitionPlanEntity> yearAnnualList = null;
        try {
            idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
            yearAnnualList=new ArrayList<>();
            for(Integer integer : idList){
                RequisitionPlanEntity e = new RequisitionPlanEntity();
                e.setId(integer);
                yearAnnualList.add(e);
            }
            landPlanDao.deleteInBatch(yearAnnualList);
        } catch (Exception e) {
            log.error("=====LandPlanService=====Method=batchDel=====Params:" + ids + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        return new LyhtResultBody<>(ids);
    }

    public Map getDetails(Integer id)
    {
        Map<String,Object> result=null;
        try {
            result=landPlanDao.getDetails(id);
        } catch (Exception e) {
            log.error("=====LandPlanService=====Method=getDetails=====Params:" + id + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        System.out.println("result"+result);
        return result;
    }

    public List<Map> list(RequisitionPlanVo requisitionPlanVo) {

        List<Map> list = landPlanDao.list(requisitionPlanVo.getRegion());

        return  list;
    }
    
    public List<Map> selectName(String taskName) {
        List<Map> list = landPlanDao.selectName(taskName);
        return  list;
    }
}
