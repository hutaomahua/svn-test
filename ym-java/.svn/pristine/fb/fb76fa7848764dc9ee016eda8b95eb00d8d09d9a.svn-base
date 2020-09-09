package com.lyht.business.abm.plan.service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.plan.dao.ProjectPlanAdjustDao;
import com.lyht.business.abm.plan.dao.ProjectPlanDao;
import com.lyht.business.abm.plan.dao.YearAnnualPlanDao;
import com.lyht.business.abm.plan.entity.ProjectPlanAdjustEntity;
import com.lyht.business.abm.plan.entity.ProjectPlanEntity;
import com.lyht.business.abm.plan.entity.YearAnnualPlanAdjustEntity;
import com.lyht.business.abm.plan.entity.YearAnnualPlanEntity;
import com.lyht.system.pojo.SysStaff;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ProjectPlanService {

    private Logger log = LoggerFactory.getLogger(YearAnnualPlanService.class);
    @Autowired
    ProjectPlanDao projectPlanDao;
    @Autowired
    ProjectPlanAdjustDao adjustDao;
    @Autowired
    YearAnnualPlanDao yearAnnualPlanDao;

    public ProjectPlanEntity saveYearAnnualPlan(ProjectPlanEntity projectPlanEntity) {
        ProjectPlanEntity result = null;
        try {
        	if(projectPlanEntity.getId()!=null&&projectPlanEntity.getId()!=0) 
        	{
        		//修改
        		List<Map> list=yearAnnualPlanDao.getCode(projectPlanEntity.getTaskType());
    			if(list.size()>0)
    			{
    				if(projectPlanEntity.getTaskType().matches("[\u4E00-\u9FA5]+"))
            		{
    					projectPlanEntity.setTaskType(list.get(0).get("code").toString());
            		}
    			}
        		//修改
                result = projectPlanDao.save(projectPlanEntity);
                
        	}else 
        	{
        		
        		projectPlanEntity.setNm(Randomizer.generCode(10));
                result = projectPlanDao.save(projectPlanEntity);
        	}
            
        } catch (Exception e) {
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        return result;
    }

    public LyhtResultBody<List<ProjectPlanEntity>> getList(LyhtPageVO lyhtPageVO, String region,String area,String taskName,String nm) {


        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<ProjectPlanEntity> page = projectPlanDao.getList(region,area,taskName,nm, pageable);
        List<ProjectPlanEntity> result = page.getContent();
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(result, pageVO);
    }

    public LyhtResultBody<Integer> delete(Integer id) {

        try {
            ProjectPlanEntity one = projectPlanDao.getOne(id);
            projectPlanDao.delete(one);
        } catch (Exception e) {
            log.error("=====YearAnnualPlanService=====Method=delete=====Params:" + id + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        return new LyhtResultBody<>(id);
    }

    public LyhtResultBody<Integer> deletes(Integer id) {

        try {
            ProjectPlanEntity one = projectPlanDao.getOne(id);
            projectPlanDao.delete(one);
        } catch (Exception e) {
            log.error("=====YearAnnualPlanService=====Method=delete=====Params:" + id + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        return new LyhtResultBody<>(id);
    }

    public LyhtResultBody<String> batchDel(String ids) {
        List<Integer> idList = null;
        List<ProjectPlanEntity> yearAnnualList = null;
        try {
            idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
            yearAnnualList=new ArrayList<>();
            for(Integer integer : idList){
                ProjectPlanEntity e = new ProjectPlanEntity();
                e.setId(integer);
                yearAnnualList.add(e);
            }
            projectPlanDao.deleteInBatch(yearAnnualList);
        } catch (Exception e) {
            log.error("=====YearAnnualPlanService=====Method=batchDel=====Params:" + ids + "=====", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        return new LyhtResultBody<>(ids);
    }

    public LyhtResultBody<List<ProjectPlanEntity>> getDetail(LyhtPageVO lyhtPageVO, String id) {


        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
        Page<ProjectPlanEntity> page = projectPlanDao.getDetail(id, pageable);
        List<ProjectPlanEntity> result = page.getContent();
        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                page.getTotalElements(), lyhtPageVO.getSorter());
        return new LyhtResultBody<>(result, pageVO);
    }



    public ProjectPlanEntity tzjh(ProjectPlanEntity projectPlanEntity, SysStaff sysStaff, String cause, String type) {
        ProjectPlanEntity result = null;
        ProjectPlanAdjustEntity logEntity=new ProjectPlanAdjustEntity();
        try {
            if(projectPlanEntity.getId()!=null&&projectPlanEntity.getId()!=0)
            {
            	 Date date = new Date(); 
        		 Timestamp timestamp = new Timestamp(date.getTime());
        		 projectPlanEntity.setUpdateTime(timestamp);
                ProjectPlanEntity entity=projectPlanDao.getDetails(projectPlanEntity.getId());
                logEntity.setNm(Randomizer.generCode(10));
                logEntity.setDataNm(entity.getNm());
                logEntity.setTaskName(entity.getTaskName());
                logEntity.setRegion(entity.getRegion());
                logEntity.setRegionName(entity.getRegionName());
            	if(projectPlanEntity.getTaskType().matches("[\u4E00-\u9FA5]+"))
        		{
        			List<Map> list=yearAnnualPlanDao.getCode(projectPlanEntity.getTaskType());
        			if(list.size()>0)
        			{
        				projectPlanEntity.setTaskType(list.get(0).get("code").toString());
        				logEntity.setTaskType(list.get(0).get("code").toString());


        			}

        		}else{
        			logEntity.setTaskType(entity.getTaskType());
        		}
                logEntity.setStartDate(entity.getStartDate());
                logEntity.setCompletDate(entity.getCompletDate());
                logEntity.setUnitNumber(entity.getUnitNumber());
                logEntity.setSumTaskNumber(entity.getSumTaskNumber());
                logEntity.setSumInvest(entity.getSumInvest());

              
                logEntity.setPlanAdjustDate(timestamp);//调整日期
                if(sysStaff!=null)
                {
                    logEntity.setAdjustStaffNm(sysStaff.getStaffName());//调整人
                }
                logEntity.setAdjustState(projectPlanEntity.getState());//1待审核
                logEntity.setAdjustCause(cause);//调整原因
                logEntity.setTaskContent(entity.getTaskContent());//工作内容
                logEntity.setDutyUnit(entity.getDutyUnit());//责任人
                logEntity.setControlUnit(entity.getControlUnit());//监督人

                
                if(type.matches("[\u4E00-\u9FA5]+"))
        		{
        			List<Map> list=yearAnnualPlanDao.getCode(type);
        			if(list.size()>0)
        			{
        				logEntity.setAdjustType(list.get(0).get("code").toString());//调整计划类别
        				projectPlanEntity.setType(list.get(0).get("code").toString());

        			}else{
        				logEntity.setAdjustType(type);//调整计划类别
        				projectPlanEntity.setType(type);

        			}

        		}else{
    				logEntity.setAdjustType(type);//调整计划类别
    				projectPlanEntity.setType(type);


        		}
                if(projectPlanEntity.getState().matches("[\u4E00-\u9FA5]+"))
        		{
        			List<Map> list=yearAnnualPlanDao.getCode(projectPlanEntity.getState());
        			if(list.size()>0)
        			{
        				projectPlanEntity.setState(list.get(0).get("code").toString());

        			}

        		}
                

                adjustDao.save(logEntity);//调整前的数据

                //修改
                result = projectPlanDao.save(projectPlanEntity);//调整计划
            }

        } catch (Exception e) {
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        return result;
    }

}
