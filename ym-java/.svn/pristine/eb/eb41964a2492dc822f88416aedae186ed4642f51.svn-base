package com.lyht.business.abm.household.service;

import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.business.abm.household.dao.AbmSplitHouseholdDao;
import com.lyht.business.abm.household.vo.MergeOwnerFamilyKeyValueVO;
import com.lyht.business.abm.household.vo.MergeOwnerKeyValueVO;
import com.lyht.business.abm.removal.dao.AbmOwnerDao;
import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
import com.lyht.business.info.dao.InfoFamilyDao;
import com.lyht.business.info.dao.InfoOwnerDao;
import com.lyht.business.info.entity.InfoFamilyEntity;
import com.lyht.business.info.entity.InfoOwnerEntity;
import com.lyht.business.info.service.InfoFamilyService;
import com.lyht.business.info.vo.InfoOwnerDetailVO;
import com.lyht.util.Randomizer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AbmMergeOwnerService {

	@PersistenceContext private EntityManager entityManager;
	@Autowired private AbmSplitHouseholdDao abmSplitHouseholdDao;
	@Autowired private InfoFamilyService infoFamilyService;
	@Autowired private AbmOwnerDao abmOwnerDao;
	@Autowired private InfoFamilyDao infoFamilyDao;

	public List<Map> getFamilyList(List<String> nm){
		return infoFamilyDao.queryFamilyListByOwnerNm(nm);
	}

	/*
	 * @Description: 权属人合并(保存)
	 * @Author: xzp
	 * @Date: 2020/8/20 14:45
	 * @param 》ownerNm:发起人内码,list:其余人内码
	 * @return 》
	 **/
	@Transactional
	public void save(String ownerNm, List<MergeOwnerKeyValueVO> list, List<MergeOwnerFamilyKeyValueVO> familyList){
		list = list.stream().distinct().collect(Collectors.toList());
		int size = list.size(), i = 0, population;
		//将其他户主转换成新户主的家庭人口指标
		for(MergeOwnerKeyValueVO s : list){
			//判断是否经签协议
			if(abmSplitHouseholdDao.queryOwnerAgreementCount(s.getOwnerNm()) > 0){
				i++;
			}else if(i == 0){
				AbmOwnerEntity owner = abmOwnerDao.findByNm(ownerNm);
				if(null != owner){
					if(!ownerNm.equals(s.getOwnerNm())){
						InfoFamilyEntity infoFamily = new InfoFamilyEntity();
						infoFamily.setRegion(owner.getRegion());
						infoFamily.setScope(owner.getScope());
						infoFamily.setOwnerNm(ownerNm);
						infoFamily.setName(owner.getName());
						infoFamily.setGender(owner.getGender());
						infoFamily.setIdCard(owner.getIdCard());
						infoFamily.setNational(owner.getNational());
						infoFamily.setAge(owner.getAge());
						infoFamily.setPresentOccupation(owner.getPresentOccupation());
						infoFamily.setHouseholdsType(owner.getHouseholdsType());
						infoFamily.setMasterRelationship(s.getRelation());
						infoFamily.setIsMove("是");
						//新增家庭人口
						infoFamilyService.save(infoFamily);
						//删除旧户主
						abmOwnerDao.deleteById(owner.getId());
					}
				}
			}
		}
		if(i > 0){
			throw new LyhtRuntimeException("已经签协议的不能再合并,一共:" + size + "条数据,其中:" + i + "条已经签订协议");
		}
		//修改家庭人口指标的户主关系
		for(MergeOwnerFamilyKeyValueVO s : familyList){
			infoFamilyDao.updateFamilyByNm(s.getFamilyNm(), s.getRelation());
		}
		String whereIn = getWhereInSql(list);
		//将户主的指标归属新户主
		String newOwnerNm = Randomizer.generCode(10);
		modifySqlByTableName("t_info_agricultural_facilities_impl", newOwnerNm, whereIn);//农副业设施
		modifySqlByTableName("t_info_homestead_impl", newOwnerNm, whereIn);//宅基地
		modifySqlByTableName("t_info_houses_impl", newOwnerNm, whereIn);//房屋
		modifySqlByTableName("t_info_houses_decoration_impl", newOwnerNm, whereIn);//房屋装修
		modifySqlByTableName("t_info_building_impl", newOwnerNm, whereIn);//附属建筑
		modifySqlByTableName("t_info_trees_impl", newOwnerNm, whereIn);//零星树木
		modifySqlByTableName("t_info_land_impl", newOwnerNm, whereIn);//土地
		AbmOwnerEntity infoOwner = abmOwnerDao.findByNm(ownerNm);
		if(null == infoOwner){
			infoOwner = infoFamilyService.queryOwnerInfoByNm(ownerNm);
			if(null == infoOwner){
				return;
			}
			infoOwner.setNm(newOwnerNm);
		}
		population = list.size() + familyList.size();
		infoOwner.setIPopulation(population + infoOwner.getIPopulation());//移民人口数量
		//修改新户主移民人口数量
		abmOwnerDao.save(infoOwner);
	}

	/*
	 * @Description: 获取WHERE IN 条件SQL
	 * @Author: xzp
	 * @Date: 2020/8/20 14:58
	 * @param 》list:被合并人和关系列表
	 * @return 》
	 **/
	public String getWhereInSql(List<MergeOwnerKeyValueVO> list){
		StringBuffer sql = new StringBuffer();
		list = list.stream().distinct().collect(Collectors.toList());
		int size = list.size(), i = 1;
		sql.append(" ( ");
		for (MergeOwnerKeyValueVO s : list){
			sql.append("\'");
			sql.append(s.getOwnerNm());
			sql.append("\'");
			if(i != size){
				sql.append(", ");
			}
			i ++;
		}
		sql.append(" ) ");
		return sql.toString();
	}

	/*
	 * @Description: 根据表名和条件修改SQL
	 * @Author: xzp
	 * @Date: 2020/8/20 14:55
	 * @param 》tableName:表名,ownerNm:户主内码,where:条件
	 * @return 》
	 **/
	public void modifySqlByTableName(String tableName, String ownerNm, String where){
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ");
		sql.append(tableName);
		sql.append(" SET ");
		sql.append(" owner_nm = ");
		sql.append("\'");
		sql.append(ownerNm);
		sql.append("\'");
		sql.append(" WHERE id > 0 AND ");
		sql.append(" id IN (SELECT s.id FROM (SELECT id FROM ");
		sql.append(tableName);
		sql.append(" WHERE owner_nm IN ");
		sql.append(where);
		sql.append(" ) s ) ");
		System.err.println("权属人合并(保存)SQL:" + sql);
		Query dataQuery = entityManager.createNativeQuery(sql.toString());
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		dataQuery.executeUpdate();
	}

}