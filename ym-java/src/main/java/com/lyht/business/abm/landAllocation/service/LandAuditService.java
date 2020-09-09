package com.lyht.business.abm.landAllocation.service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.landAllocation.dao.EmLandPoolDao;
import com.lyht.business.abm.landAllocation.dao.LandAuditDao;
import com.lyht.business.abm.landAllocation.dao.LandPoolDao;
import com.lyht.business.abm.landAllocation.dao.LandPoolProcessDao;
import com.lyht.business.abm.landAllocation.entity.LandAuditEntity;
import com.lyht.business.abm.landAllocation.entity.LandPoolEntity;
import com.lyht.business.abm.landAllocation.entity.LandPoolProcess;
import com.lyht.business.abm.landAllocation.vo.LandAuditVO;
import com.lyht.business.abm.plan.dao.LandFormulaDao;
import com.lyht.business.abm.plan.entity.TdPublicityEntity;
import com.lyht.business.abm.removal.dao.AbmFamilyDao;
import com.lyht.business.abm.removal.dao.TudiEntityDao;
import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import com.lyht.business.abm.removal.entity.AbmLandEntity;
import com.lyht.business.pub.dao.PubRegionDao;
import com.lyht.business.pub.entity.PubRegionEntity;
import com.lyht.business.pub.service.PubRegionService;
import com.lyht.util.DateUtil;
import com.lyht.util.Randomizer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

/**
 * @version: V1.0
 * @author: hjs
 * @className: LandAuditService
 * @packageName: com.lyht.business.abm.landAllocation.service
 * @description: 土地分解审核
 * @data: 2020年02月12日 16:38
 * @see []
 **/
@Service
public class LandAuditService {
	
	@Autowired
    private LandFormulaDao tdPublicityDao;
	

    @Autowired
    private LandAuditDao landAuditDao;

    @Autowired
    private EmLandPoolDao emLandPoolDao;

    @Autowired
    private LandDataSyncService landDataSyncService;

    @Autowired
    private AbmFamilyDao abmFamilyDao;

    @Autowired
    private TudiEntityDao tudiEntityDao;

    @Autowired
    private PubRegionDao pubRegionDao;
    
    @Autowired
    private LandPoolDao landPoolDao;
    
    @Autowired
    private LandPoolProcessDao landPoolProcessDao;
    
    /**
	 * 单个删除
	 *
	 * @param id
	 * @return
	 */
    @Transactional
	public LyhtResultBody<Integer> delete(Integer id) {
		// 参数校验
		if (id == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		LandAuditEntity landAuditEntity = landAuditDao.getOne(id);
		LandPoolEntity landPoolEntity = new LandPoolEntity();
		if(landAuditEntity!=null) {
			if (landAuditEntity.getTypeLevel() == 2) {
				landPoolEntity = landPoolDao.queryByFourCondition(landAuditEntity.getSourceRegion(),
						landAuditEntity.getScope(), landAuditEntity.getLandType(), null);
			} else {
				landPoolEntity = landPoolDao.queryByFourCondition(landAuditEntity.getSourceRegion(),
						landAuditEntity.getScope(), null, landAuditEntity.getLandType());
			}
		}
		landPoolProcessDao.deleteByAuditNmAndPoolId(landAuditEntity.getId(), landPoolEntity.getId());// 删除关联表中数据 以防计算偏差
		landAuditDao.deleteById(id);
		return new LyhtResultBody<>(id);
	}

    /**
     * 查询所有土地分解审核列表
     * @param auditCode
     * @param uname
     * @param city
     * @param sDate
     * @param eDate
     * @param lyhtPageVO
     * @return
     */
    public LyhtResultBody queryAuditList(String auditCode, String uname, String city,
                                         String sDate, String eDate, LyhtPageVO lyhtPageVO){
        try {
            Date sdate = null, edate = null;
            try {
                if(StringUtils.isNotBlank(sDate)){
                    sdate = DateUtil.strToDate(sDate, "yyyy-MM-dd HH:mm:ss");
                }
                if(StringUtils.isNotBlank(eDate)){
                    edate = DateUtil.strToDate(eDate, "yyyy-MM-dd HH:mm:ss");
                }
            }catch (Exception e){}
            Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
            PubRegionEntity byCityCode = pubRegionDao.findByCityCode(city);
            if(byCityCode != null){
                city = byCityCode.getMergerName();
            }
            //查询所有土地分解审核信息
            Page<Map> page = landAuditDao.queryAuditList(auditCode, uname, city, sdate, edate, pageable);;
            LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
                    page.getTotalElements(), lyhtPageVO.getSorter());
            return new LyhtResultBody<>(page.getContent(), pageVO);
        }catch (Exception e){
            e.printStackTrace();
            throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
        }
    }

    @Autowired
    private PubRegionService pubRegionService;

    /**
     * 土地分解审核
     * @param id
     * @param auditCode
     * @param remarks
     */
    @Transactional
    public void landAudit(Integer id, String auditCode, String remarks){
        if(id == null || StringUtils.isBlank(auditCode)){
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
        try {
            //获得土地分解审核数据
            LandAuditEntity auditEntity = findById(id);
            if(auditEntity == null){
                throw new LyhtRuntimeException("数据不存在！");
            }
            //只有审核通过才能分解土地
            if("64FABB5F0D".equals(auditCode)){
                if("64FABB5F0D".equals(auditEntity.getAuditCode())){
                    //土地不能重复分解
                    throw new LyhtRuntimeException(LyhtExceptionEnums.DUPLICATE_DATA);
                }
                //进行土地分解相关操作
                LanFJ(auditEntity);
            }
            //修改审核表的数据
            auditEntity.setAuditCode(auditCode);
            if(StringUtils.isNotBlank(remarks)){
                auditEntity.setRemark(remarks);
            }
        }catch (LyhtRuntimeException e){
            throw e;
        }catch (Exception e){
            throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
        }
    }

    /**
     * 根据土地分解的审核数据  对土地进行分解
     * @param auditEntity
     */
    @Transactional
    public void LanFJ(LandAuditEntity auditEntity){
    	String processId = auditEntity.getProcessId();
        int typeLevel = auditEntity.getTypeLevel();
        String landType = auditEntity.getLandType();
        //需要分解土地的行政区的编码
        String sourceRegion = auditEntity.getSourceRegion();
        //获得需要征地范围
        String scope = auditEntity.getScope();
        //获得需要分解的土地
        LandPoolEntity land = emLandPoolDao.findLand(typeLevel, sourceRegion, auditEntity.getScope(), landType);
        if(land != null){
            //计算出剩余面积  只需要计算最上级的行政区就行了
            BigDecimal multiply = land.getArea().subtract(land.getSeparateArea());
            //需要分解的面积
            BigDecimal resolvArea = new BigDecimal(String.valueOf(auditEntity.getResolveArea()));
            //如果剩余面积大于或等于分解面积则进行土地分解数据
            if(multiply.compareTo(resolvArea) > -1){
                //对源行政区进行减土地面积
                emLandPoolDao.updateLandPool(land.getId(), resolvArea);
                //获得行政区列表
                List<PubRegionEntity> pubRegionEntityList = pubRegionService.list(new PubRegionEntity());
                if(auditEntity.getFlag() == 1){   //分解至行政区
                    //获得从源行政区到目标行政区中间的所有行政区（不包含源行政区）
                    pubRegionEntityList = findNode(auditEntity.getTargetRegion(), sourceRegion, pubRegionEntityList);
                    //土地分解到行政区
                    landFjToRegion(typeLevel, scope, landType, resolvArea, land, pubRegionEntityList, true);
                }else if(auditEntity.getFlag() == 0){  //分解土地到户
                    //获得该户主信息
                    AbmFamilyEntity byNm = abmFamilyDao.findByNm(auditEntity.getNm());
                    //获得从源行政区到目标行政区中间的所有行政区（不包含源行政区）
                    pubRegionEntityList = findNode(byNm.getRegion(), sourceRegion, pubRegionEntityList);
                    //土地分解到行政区
                    landFjToRegion(typeLevel, scope, landType, resolvArea, land, pubRegionEntityList, false);
                    //土地分解到户
                    AbmLandEntity tudiEntityTemp = tudiEntityDao.findByCondition(byNm.getIdCard(), typeLevel, byNm.getRegion(), scope, landType);
                    if(tudiEntityTemp == null){
                        AbmLandEntity tudiEntity = new AbmLandEntity();
                        tudiEntity.setNm(Randomizer.nextString(10));
                        tudiEntity.setRegion(byNm.getRegion());
                        tudiEntity.setOwnerNm(byNm.getOwnerNm());
                        tudiEntity.setArea(resolvArea);
                        tudiEntity.setAllType(land.getAllType());
                        tudiEntity.setTypeOne(land.getTypeOne());
                        tudiEntity.setTypeTwo(land.getTypeTwo());
                        tudiEntity.setTypeThree(land.getTypeThree());
                        tudiEntity.setIdCard(byNm.getIdCard());
                        tudiEntity.setScope(scope);
                        tudiEntityDao.save(tudiEntity);
                        
                    }else{
                        tudiEntityDao.addLandArea(tudiEntityTemp.getId(), resolvArea);
                        	
                    }
                }
            }else{
                //分解失败！土地剩余面积不够！
                throw new LyhtRuntimeException("土地面积不够！分解失败");
            }
        }else {
            throw new LyhtRuntimeException("土地不存在！");
        }
    }

    /**
     * 土地分解到行政区
     * @param typeLevel 级别
     * @param scope 征地范围
     * @param landType 土地类型
     * @param resolvArea 分解面积
     * @param land 需要分解的土地
     * @param pubRegionEntityList 行政区数组
     * @param flag 最后一级是否不需要增加已分解土地
     */
    public void landFjToRegion(int typeLevel, String scope, String landType, BigDecimal resolvArea, LandPoolEntity land, List<PubRegionEntity> pubRegionEntityList, Boolean flag){
        for (int i = 0; i < pubRegionEntityList.size(); i++) {
            //第一个为增加土地面积的行政区 所以不需要增加已分解面积
            Boolean isOne = (flag && i == 0);
            PubRegionEntity pubRegionEntity = pubRegionEntityList.get(i);
            LandPoolEntity temp = emLandPoolDao.findLand(typeLevel, pubRegionEntity.getCityCode(), scope, landType);
            //如果该行政区还不存在这样的土地则进行添加
            if(temp == null){
                LandPoolEntity landPoolEntity = new LandPoolEntity();
                //设置内码
                landPoolEntity.setNm(Randomizer.nextString(10));
                landPoolEntity.setRegion(pubRegionEntity.getCityCode());
                landPoolEntity.setRegionLevel(pubRegionEntity.getLevel());
                landPoolEntity.setScope(scope);
                landPoolEntity.setAllType(land.getAllType());
                landPoolEntity.setTypeOne(land.getTypeOne());
                landPoolEntity.setTypeTwo(land.getTypeTwo());
                landPoolEntity.setTypeThree(land.getTypeThree());
                landPoolEntity.setArea(resolvArea);
                if(isOne){
                    landPoolEntity.setSeparateArea(new BigDecimal(0));
                }else{
                    landPoolEntity.setSeparateArea(resolvArea);
                }
                landDataSyncService.save(landPoolEntity);
            }else{
                Boolean retain = isOne ? true : false;
                emLandPoolDao.addLandArea(temp.getId(), resolvArea, retain);
            }
        }
    }

    public LandAuditEntity findById(Integer id){
        Optional<LandAuditEntity> byId = landAuditDao.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        return null;
    }

    /**
     * 根据传入的父行政区编码和子行政区编码 返回中间的所有节点
     * @param childen 子行政区编码
     * @param parent 父行政区编码
     * @param mapList 树形数组
     * @return
     */
    public List<PubRegionEntity> findNode(String childen, String parent, List<PubRegionEntity> mapList){
        List<PubRegionEntity> rData = new ArrayList<>();
        for (PubRegionEntity pe : mapList) {
            String parentCode = pe.getParentCode(),
                    cityCode = pe.getCityCode();
            if(cityCode.equals(childen)){
                //如果当前行政区编码等于传入的父行政区编码则跳出
                if(cityCode.equals(parent)){
                    return rData;
                }
                rData.add(pe);
                //假如父节点不为空 则去查找所有的父级节点
                if(StringUtils.isNotBlank(parentCode)){
                    //返回父节点
                    List<PubRegionEntity> node = findNode(parentCode, parent , mapList);
                    rData.addAll(node);
                }
            }
        }
        return rData;
    }
    
    public LyhtResultBody<LandAuditVO> getDataByTaskId(String taskId){
    	return new LyhtResultBody<>(landAuditDao.getDataByTaskId(taskId));
    }
}
