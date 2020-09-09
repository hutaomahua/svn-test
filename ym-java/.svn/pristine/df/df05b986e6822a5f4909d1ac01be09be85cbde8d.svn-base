package com.lyht.business.abm.plan.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.plan.dao.LandFormulaDao;
import com.lyht.business.abm.plan.dao.ProjectPlanDao;
import com.lyht.business.abm.plan.dao.PublicityDao;
import com.lyht.business.abm.plan.dao.TdPublicityDetailDao;
import com.lyht.business.abm.plan.entity.PublicityEntity;
import com.lyht.business.abm.plan.entity.TdPublicityDetailEntity;
import com.lyht.business.abm.plan.entity.TdPublicityEntity;
import com.lyht.util.Randomizer;

@Service
public class LandFormulaService {
	@Autowired
	TdPublicityDetailDao tdPublicityDetailDao;
	
	@Autowired
	 ProjectPlanDao projectPlanDao;
	
	@Autowired
	PublicityDao publicityDao;
	
	@Autowired
	LandFormulaDao dao;
	
	@Autowired
	 OwnerVerifyService ownerVerifyService;
	
	 @SuppressWarnings("rawtypes")
	public LyhtResultBody<List<Map>> getList(LyhtPageVO lyhtPageVO ,String region, String name, String startTime, String endTime) {
	        Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
	        Page<Map> page = dao.getList(pageable,region,name,startTime,endTime);
	        List<Map> list = page.getContent();
	        LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
	                page.getTotalElements(), lyhtPageVO.getSorter());
	        return new LyhtResultBody<>(list, pageVO);

	    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LyhtResultBody<TdPublicityEntity> save(TdPublicityEntity entity,String list) {
		//生成内码
		String nm = Randomizer.generCode(10);
		
		List<Map<String,String>> listObjectFir = (List<Map<String,String>>) JSONArray.parse(list);
		
		if(listObjectFir.size() > 0 ) {
		for (int i = 0; i < listObjectFir.size(); i++) {
			TdPublicityDetailEntity tdentity=new TdPublicityDetailEntity();

			  String ownerNm=listObjectFir.get(i).getOrDefault(("owner_nm").toString(), "owner_nm".toString());
			  String zbType=listObjectFir.get(i).getOrDefault(("zb_type").toString(), "zb_type".toString());
//			  String nm=listObjectFir.get(i).getOrDefault(("nm").toString(), "nm".toString());
			  String endTime=listObjectFir.get(i).getOrDefault(("end_time").toString(), "end_time".toString());
			  String processId=listObjectFir.get(i).getOrDefault(("process_id").toString(), "process_id".toString());

			  tdentity.setOwnerNm(ownerNm);
			  tdentity.setZbType(zbType);
			  tdentity.setNm(nm);
			  tdentity.setEndTime(endTime);
			  tdentity.setProcessId(processId);
			  ownerVerifyService.editfgState("1",processId);//0未公示 1待公示 2已公示
			  tdPublicityDetailDao.save(tdentity);
			}
		}
		
		
		
		TdPublicityEntity result = null;
	        
         //ownerVerifyService.editGsState("1", entity.getNm());//1 公示中
         entity.setNm(nm);
         entity.setState("0");
     	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		Date date=new Date();
		String time="";
		List<Map> code=dao.getGsCode();
     	if(code.size()>0)
		{
			String s=code.get(0).get("code").toString();
			Integer i=Integer.valueOf(s.substring(s.length()-4));
			i=i+1;
			String str=i.toString();
			if(str.length()==1)
			{
				time="GS"+sdf1.format(date)+"000"+str;
			}
			if(str.length()==2)
			{
				time="GS"+sdf1.format(date)+"00"+str;
			}
			if(str.length()==3)
			{
				time="GS"+sdf1.format(date)+"0"+str;
			}
			if(str.length()==4)
			{
				time="GS"+sdf1.format(date)+str;
			}
			entity.setCode(time);
		}else{
			time="GS"+sdf1.format(date)+"0001";
			entity.setCode(time);
		}
         result = dao.save(entity);
         List<Map> listMap=projectPlanDao.regionName(result.getRegion());
      	if(listMap.size()>0)
      	{
      		result.setRegionName(listMap.get(0).get("merger_name").toString());
      	}
     
    
     return new LyhtResultBody<>(result);

 }

	@SuppressWarnings("rawtypes")
	public LyhtResultBody<List<Map>> getUserList(String region) {
		 List<Map> userList = dao.getUserList(region);
		return new LyhtResultBody<>(userList);
	}

	
	public LyhtResultBody<Integer> delete(Integer id) {
		  try {
	        	TdPublicityEntity resultSet = dao.getOne(id);
	        	List<TdPublicityDetailEntity> findNm = tdPublicityDetailDao.findNm(resultSet.getNm());
	        	for(TdPublicityDetailEntity td :findNm) {
	        		ownerVerifyService.editfgState("0", td.getProcessId());//0 未发布
	        	}
	        	dao.delete(resultSet);
	        	dao.deleteNm(resultSet.getNm());
	        } catch (Exception e) {
	            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
	        }
	        return new LyhtResultBody(id);
	}

	
	public void delete(String nm) {
		List<TdPublicityDetailEntity> findNm = tdPublicityDetailDao.findNm(nm);
    	for(TdPublicityDetailEntity td :findNm) {
    		ownerVerifyService.editfgState("0", td.getProcessId());//0 未发布
    	}
    	dao.deleteNm(nm);
		
	}

	public List<Map> detaiList(String nm) {
		return tdPublicityDetailDao.detaiList(nm);
	}

	
	public void confirmPublicity(int id) {
		TdPublicityEntity resultSet = dao.getOne(id);
    	List<TdPublicityDetailEntity> findNm = tdPublicityDetailDao.findNm(resultSet.getNm());
    	for(TdPublicityDetailEntity td :findNm) {
    		ownerVerifyService.editfgState("1", td.getProcessId());//0 未发布
    	}
    	resultSet.setState("1");
    	dao.save(resultSet);
    	
	}

	public void endPublicity(int id) {
		TdPublicityEntity resultSet = dao.getOne(id);
    	List<TdPublicityDetailEntity> findNm = tdPublicityDetailDao.findNm(resultSet.getNm());
    	for(TdPublicityDetailEntity td :findNm) {
    		ownerVerifyService.editfgState("2", td.getProcessId());//0 未发布
    	}
    	resultSet.setState("2");
    	dao.save(resultSet);
	}


}
