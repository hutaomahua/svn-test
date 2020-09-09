package com.lyht.business.abm.signed.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.abm.signed.entity.ProtocolInfo;
import com.lyht.business.abm.signed.vo.GraphVO;
import com.lyht.business.abm.signed.vo.OwnerInfoVO;
import com.lyht.business.abm.signed.vo.ProtocolInfoAndEscrowVO;
import com.lyht.business.abm.signed.vo.ProtocolInfoVO;
import com.lyht.business.abm.signed.vo.ProtocolItemVO;
import com.lyht.business.abm.signed.vo.ProtocolVO;

/**
 * 协议信息表
 * 
 * @author wzw
 *
 */
@Repository
public interface ProtocolInfoDao extends JpaRepository<ProtocolInfo, Integer> {

	@Query(value = "select `name`,code,mergerName,area,ownerName,ownerId,ownerNm,placeType,placeName,place,moveNum,FLOOR(productionNum) productionNum,CAST(house AS DECIMAL(19,2)) house,  "
			+ " CAST(decoration AS DECIMAL(19,2)) decoration,CAST(building AS DECIMAL(19,2)) building,CAST(facilities AS DECIMAL(19,2)) facilities,CAST(individual AS DECIMAL(19,2)) individual,CAST(relocation AS DECIMAL(19,2)) relocation,CAST(other AS DECIMAL(19,2)) other,   "
			+ " CAST(basics AS DECIMAL(19,2)) basics,CAST(trees AS DECIMAL(19,2)) trees,CAST(young AS DECIMAL(19,2)) young,CAST(land AS DECIMAL(19,2)) land,CAST(productionAmount AS DECIMAL(19,2)) productionAmount,idCard,CAST(homestead AS DECIMAL(19,2)) homestead   "
			+ " from (select tt.protocol_name name,tt.protocol_code code,t2.merger_name mergerName,t1.`name` ownerName,t1.`id` ownerId,t1.`nm` ownerNm,t5.name placeType,t5.code typeCode,t1.place_name placeName,CONCAT(t1.xiang,',',t1.cun,',',t1.zu) as place, "
			+ " IFNULL((select count(1) from t_info_family_impl where owner_nm = tt.owner_nm and is_satisfy= 2),0) as moveNum,IFNULL((select count(1) from t_info_family_impl where owner_nm = tt.owner_nm and is_produce= 1),0) as productionNum,     "
			+ " if(position('房屋' in tt.protocol_area)>0,IFNULL(t4.house_amount,0),0)house,  "
			+ " if(position('装修' in tt.protocol_area)>0,IFNULL(t4.house_decoration_amount,0),0)decoration,  "
			+ " if(position('附属建筑物' in tt.protocol_area)>0,IFNULL(t4.building_amount,0),0)building  ,  "
			+ " if(position('农副业设施' in tt.protocol_area)>0,IFNULL(t4.agricultural_facilities_amount,0),0)facilities ,  "
			+ " if(position('个体工商户' in tt.protocol_area)>0,IFNULL(t4.individual_amount,0),0)individual,     "
			+ " if(position('搬迁补助' in tt.protocol_area)>0,IFNULL(t4.relocation_allowance_amount,0),0)relocation,  "
			+ " if(position('其他补助' in tt.protocol_area)>0,(IFNULL(t4.other_amount,0)+ ifnull(t4.difficult_amount,0)),0)other,     "
			+ " if(position('搬迁安置基础设施补助' in tt.protocol_area)>0,(IFNULL(t4.infrastructure_amount,0)),0)basics,   "
			+ " if(position('零星树木' in tt.protocol_area)>0,IFNULL(t4.trees_amount,0),0)trees ,  "
			+ " if(position('成片青苗及果木' in tt.protocol_area)>0,IFNULL(t4.young_crops_amount,0),0) young  ,    "
			+ " if(position('征收土地' in tt.protocol_area)>0,IFNULL(t4.levy_land_amount,0),0)land, if(position('生产安置' in tt.protocol_area)>0,IFNULL(t4.production_amount,0),0)productionAmount,t1.id_card idCard,  "
			+ " if(position('宅基地' in tt.protocol_area)>0,(IFNULL(t4.homestead_amount,0)),0)homestead,tt.protocol_area area  "
			+ " from t_abm_protocol_info tt left join t_info_owner_impl t1 on tt.owner_nm = t1.nm     "
			+ " left join pub_region t2 on t1.region = t2.city_code     "
			+ " left join pub_dict_value t5 on t1.place_type = t5.nm   "
			+ " left join t_compensation_cost t4 on tt.owner_nm = t4.owner_nm  where tt.id = :id ) as dd ", nativeQuery = true)
	public Map<String, Object> exportVersionTwo(@Param("id") Integer id);

	/**
	 * 根据权属人身份证号查询协议信息
	 * 
	 * @param idCard
	 * @return
	 */
	@Query(value = "select tt.nm,t2.name ownerName,t2.id_card idCard,tt.protocol_code protocolCode,tt.protocol_name protocolName,  "
			+ "tt.protocol_area protocolArea, CAST(tt.protocol_amount AS DECIMAL(19,2)) protocolAmount,tt.complete_time completeTime, 0 as flag,tt.state, "
			+ "(select CONCAT(file_name,'.',file_type) from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_info' order by upload_time desc LIMIT 1) as fileName , "
			+ "(select file_url from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_info' order by upload_time desc LIMIT 1) as fileUrl ,IFNULL(t1.cn_status,'未提交审核') status    "
			+ "from t_abm_protocol_info tt left join t_bpm_process t1 on tt.process_id = t1.process_id   "
			+ "left join t_info_owner_impl t2 on tt.owner_nm = t2.nm  " + "where t2.id_card = :idCard  UNION ALL  "
			+ "select tt.nm,t2.name ownerName,t2.id_card idCard,tt.protocol_code protocolCode,tt.protocol_name protocolName,  "
			+ "'资金代管协议' protocolArea,CAST(tt.escrow_money AS DECIMAL(19,2)) protocolAmount,tt.complete_time completeTime,1 as flag,tt.state, "
			+ "(select CONCAT(file_name,'.',file_type) from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_escrow' order by upload_time desc LIMIT 1) as fileName , "
			+ "(select file_url from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_escrow' order by upload_time desc LIMIT 1) as fileUrl ,IFNULL(t1.cn_status,'未提交审核') status   "
			+ "from t_abm_protocol_escrow tt left join t_bpm_process t1 on tt.process_id = t1.process_id   "
			+ "left join t_info_owner_impl t2 on tt.owner_nm = t2.nm  "
			+ "where t2.id_card = :idCard  ", nativeQuery = true)
	List<ProtocolVO> getProtocolByIdCard(@Param("idCard") String idCard);

	/**
	 * 带有资金代管协议的房屋协议
	 * 
	 * @param processId
	 * @return
	 */
	@Query(value = "select tt.id,tt.nm,'补偿协议'  as protocolType,tt.protocol_code protocolCode,tt.protocol_name protocolName,FORMAT(tt.protocol_amount,2) protocolAmount,tt.protocol_area protocolArea,  "
			+ "t1.`name` ownerName,t3.id escrowId,t3.nm escrowNm,'资金代管协议' as protocolType1,t3.protocol_code protocolCode1,t3.protocol_name protocolName1,t3.house_type houseType,t3.homestead_code homesteadCode,  "
			+ "t4.`name` deptName,FORMAT(t3.placement_money,2) placementMoney,FORMAT(t3.escrow_money,2) escrowMoney  ,"
			+ "(select count(1) from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_info') as infoFileCount ,"
			+ "(select count(1) from pub_files where table_pk_column = t3.nm and table_name = 't_abm_protocol_escrow' ) as escrowFileCount "
			+ "from t_abm_protocol_info tt left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  "
			+ "left join t_bpm_process t2 on tt.process_id = t2.process_id  "
			+ "left join t_abm_protocol_escrow t3 on tt.owner_nm = t3.owner_nm  "
			+ "left join sys_dept t4 on t3.company = t4.nm where tt.process_id = :processId", nativeQuery = true)
	ProtocolInfoAndEscrowVO getBytaskIdEscrow(@Param("processId") String processId);

	/**
	 * 不带有资金代管协议的房屋协议
	 * 
	 * @param processId
	 * @return
	 */
	@Query(value = "select tt.id,tt.nm,'补偿协议'  as protocolType,tt.protocol_code protocolCode,tt.protocol_name protocolName,FORMAT(tt.protocol_amount,2) protocolAmount,tt.protocol_area protocolArea,  "
			+ " t1.`name` ownerName,(select count(1) from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_info') as infoFileCount   "
			+ "from t_abm_protocol_info tt     left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  "
			+ "left join t_bpm_process t2 on tt.process_id = t2.process_id where tt.process_id = :processId ", nativeQuery = true)
	ProtocolInfoAndEscrowVO getBytaskId(@Param("processId") String processId);

	@Query(value = "select * from t_abm_protocol_info where nm =:nm", nativeQuery = true)
	ProtocolInfo findByNm(@Param("nm") String nm);

	@Modifying
	@Query(value = "update t_abm_protocol_info set state = 1 where nm = :nm", nativeQuery = true)
	Integer updateStateFinish(@Param("nm") String nm);

	@Modifying
	@Query(value = "update t_abm_protocol_info set state = 1 where process_id = :processId", nativeQuery = true)
	Integer updateStateFinishByProcessId(@Param("processId") String processId);

	@Modifying
	@Query(value = "update t_abm_protocol_info set state = 0 where nm = :nm", nativeQuery = true)
	Integer updateStateFail(@Param("nm") String nm);

	ProtocolInfo findByProcessId(String processId);

	/**
	 * 查询所有已存在协议的权属人协议信息
	 */
	@Query(value = "select t1.nm,t2.name region,t1.name,t1.id_card idCard,t4.name placeType, "
			+ " CAST((select IFNULL(sum(amount),0) from ( select owner_nm,amount from t_cost_building   "
			+ " UNION ALL    select owner_nm,amount from t_cost_agricultural_facilities   UNION ALL   "
			+ " select owner_nm,amount from t_cost_trees    UNION ALL   "
			+ " select owner_nm,amount from t_cost_houses   UNION ALL   "
			+ " select owner_nm,amount from t_cost_individual  UNION ALL   "
			+ " select owner_nm,amount from t_cost_houses_decoration   UNION ALL   "
			+ " select owner_nm,amount from t_cost_other   UNION ALL   "
			+ " select owner_nm,amount from t_cost_infrastructure  UNION ALL   "
			+ " select owner_nm,amount from t_cost_homestead   UNION ALL   "
			+ " select owner_nm,amount from t_cost_levy_land   UNION ALL   "
			+ " select owner_nm,amount from t_cost_young_crops   UNION ALL   "
			+ " select owner_nm,amount from t_cost_relocation_allowance) dd where owner_nm = tt.owner_nm)AS DECIMAL(19,2)) as total, "
			+ " CAST(ifnull(sum(tt.protocol_amount),0)AS DECIMAL(19,2)) protocoled, "
			+ "CAST((select IFNULL(sum(escrow_money),0) from t_abm_protocol_escrow where owner_nm = tt.owner_nm)AS DECIMAL(19,2)) as escrow, "
			+ " (select count(1) from pub_files t9   "
			+ " left join (select nm,owner_nm from t_abm_protocol_escrow  UNION ALL   "
			+ " select nm,owner_nm from t_abm_protocol_info ) t8   "
			+ " on t9.table_pk_column = t8.nm and t9.table_name = 't_abm_protocol_escrow' or t9.table_name = 't_abm_protocol_info'   "
			+ " where t9.table_pk_column = t8.nm and t8.owner_nm = tt.owner_nm) as fujian "
			+ " from t_abm_protocol_info tt left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t4 on t1.place_type = t4.nm  " + " GROUP BY t1.id_card", nativeQuery = true)
	public List<OwnerInfoVO> getOwnerInfo();

	/**
	 * 查询已签订完成的协议 flag 0 补偿协议 1 资金代管协议
	 */
	@Query(value = "select id,nm,protocol_code protocolCode,protocol_name protocolName,null content,is_escrow isEscrow,  "
			+ "protocol_area protocolArea, CAST(protocol_amount AS DECIMAL(19,2)) protocolAmount,complete_time completeTime, 0 as flag, "
			+ "(select CONCAT(file_name,'.',file_type) from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_info' order by upload_time desc LIMIT 1) as fileName,  "
			+ "(select file_url from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_info' order by upload_time desc LIMIT 1) as fileUrl  "
			+ "from t_abm_protocol_info tt where owner_nm = :ownerNm   and state = 1  UNION ALL  "
			+ "select id,nm,protocol_code protocolCode,protocol_name protocolName, content,'' isEscrow,  "
			+ "'资金代管协议' protocolArea, CAST(escrow_money AS DECIMAL(19,2)) protocolAmount,complete_time completeTime,1 as flag,  "
			+ "(select CONCAT(file_name,'.',file_type) from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_escrow' order by upload_time desc LIMIT 1) as fileName ,  "
			+ "(select file_url from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_info' order by upload_time desc LIMIT 1) as fileUrl  "
			+ "from t_abm_protocol_escrow tt where owner_nm = :ownerNm  and state = 1 ", nativeQuery = true)
	public List<ProtocolInfoVO> getFinishProtocol(@Param("ownerNm") String ownerNm);

	/**
	 * 查询权属人查询补偿信息 权属人补偿信息列表 t_info_owner_impl：权属人信息表 t_abm_protocol_info：补偿协议信息表
	 * t_abm_protocol_escrow：资金代管协议信息表 pub_region：行政区信息表 关联权属人信息表 region = cityCode
	 * total：总金额 根据十二张费用计算表合计 pub_dict_value：字典表 关联权属人信息表 安置类型 place_type = nm
	 * t_abm_total_state:权属人协议签订总状态表 取权属人各项实物指标签订状态 是否全部完成状态
	 */
	@Query(value = " select tt.place_name place,t5.name placeType,t5.code typeCode,tt.nm,tt.name,tt.id_card idCard,t1.`merger_name` region,  "
			+ " (if(ts.protocol_state=1,'已完成','未完成')) as flag,    (select count(1) from pub_files t9   "
			+ " left join (   select nm,owner_nm from t_abm_protocol_escrow   UNION ALL  "
			+ " select nm,owner_nm from t_abm_protocol_info ) t8  "
			+ " on t9.table_pk_column = t8.nm and t9.table_name = 't_abm_protocol_escrow' or t9.table_name = 't_abm_protocol_info'  "
			+ " where t9.table_pk_column = t8.nm and t8.owner_nm = tt.nm) as fujian,    "
			+ " CAST((select IFNULL(sum(protocol_amount),0) from t_abm_protocol_info where owner_nm = tt.nm) AS DECIMAL(19,2)) as protocoled,  "
			+ " CAST( (select IFNULL(sum(escrow_money),0) from t_abm_protocol_escrow where owner_nm = tt.nm) AS DECIMAL(19,2)) as escrow, "
			+ " CAST((IFNULL(house_amount,0)+IFNULL(house_decoration_amount,0)+IFNULL(building_amount,0)+ "
			+ " IFNULL(agricultural_facilities_amount,0)+IFNULL(trees_amount,0)+IFNULL(individual_amount,0)+ "
			+ " IFNULL(relocation_allowance_amount,0)+IFNULL(other_amount,0)+IFNULL(difficult_amount,0)+ "
			+ " IFNULL(infrastructure_amount,0)+IFNULL(homestead_amount,0)+ "
			+ " IFNULL(levy_land_amount,0)+IFNULL(young_crops_amount,0)+IFNULL(production_amount,0)) AS DECIMAL(19,2)) as total  from t_compensation_cost t9 "
			+ " left join t_info_owner_impl tt on t9.owner_nm = tt.nm "
			+ " left join pub_region t1 on tt.region = t1.city_code  "
			+ " left join pub_dict_value t5 on tt.place_type = t5.`nm`  "
			+ " left join t_abm_total_state  ts on tt.nm = ts.owner_nm where  t9.status=2 "
			+ " and if(:ownerNm is not null, tt.nm =:ownerNm ,1=1 )"
			+ " and if(:idCard is not null, tt.id_card like CONCAT('%',:idCard,'%') ,1=1 ) and if(:flag is not null, ts.protocol_state=:flag ,1=1 ) "
			+ " and if(:region is not null, t1.merger_name like CONCAT('%',:region,'%') ,1=1) and if(:word is not null, tt.name like CONCAT('%',:word,'%') ,1=1)   ", countQuery = "select count(1) from (select t5.name placeType,tt.nm,tt.name,tt.id_card idCard,t1.`merger_name` region,  "
					+ "(if(ts.protocol_state=1,'已完成','未完成')) as flag,    (select count(1) from pub_files t9   "
					+ "left join (   select nm,owner_nm from t_abm_protocol_escrow   UNION ALL  "
					+ "select nm,owner_nm from t_abm_protocol_info ) t8  "
					+ "on t9.table_pk_column = t8.nm and t9.table_name = 't_abm_protocol_escrow' or t9.table_name = 't_abm_protocol_info'  "
					+ "where t9.table_pk_column = t8.nm and t8.owner_nm = tt.nm) as fujian,    "
					+ "(select IFNULL(sum(protocol_amount),0) from t_abm_protocol_info where owner_nm = tt.nm) as protocoled,  "
					+ "(select IFNULL(sum(escrow_money),0) from t_abm_protocol_escrow where owner_nm = tt.nm) as escrow, "
					+ "(IFNULL(house_amount,0)+IFNULL(house_decoration_amount,0)+IFNULL(building_amount,0)+ "
					+ "IFNULL(agricultural_facilities_amount,0)+IFNULL(trees_amount,0)+IFNULL(individual_amount,0)+ "
					+ "IFNULL(relocation_allowance_amount,0)+IFNULL(other_amount,0)+IFNULL(difficult_amount,0)+ "
					+ "IFNULL(infrastructure_amount,0)+IFNULL(homestead_amount,0)+ "
					+ "IFNULL(levy_land_amount,0)+IFNULL(young_crops_amount,0)) as total  "
					+ "from t_compensation_cost t9 " + "left join t_info_owner_impl tt on t9.owner_nm = tt.nm "
					+ " left join pub_dict_value t5 on tt.place_type = t5.`nm`"
					+ "left join pub_region t1 on tt.region = t1.city_code  "
					+ "left join t_abm_total_state  ts on tt.nm = ts.owner_nm    "
					+ "where t9.status=2 and if(:ownerNm is not null, tt.nm =:ownerNm ,1=1 )  and if(:idCard is not null, tt.id_card like CONCAT('%',:idCard,'%') ,1=1 ) and if(:flag is not null, ts.protocol_state=:flag ,1=1 )  and if(:region is not null, t1.merger_name like CONCAT('%',:region,'%') ,1=1)    "
					+ "and if(:word is not null, tt.name like CONCAT('%',:word,'%') ,1=1)) as dd", nativeQuery = true)
	public Page<OwnerInfoVO> page(@Param("flag") Integer flag, @Param("region") String region,
			@Param("word") String word, @Param("idCard") String idCard, @Param("ownerNm") String nm, Pageable pageable);

	/**
	 * 移民协议签订项名称、金额及状态查询 1 可以签订 2 已签订 0 无状态 费用计算十二项
	 */
	@Query(value = "select  CAST(amount AS DECIMAL(19,2)) as total,name,flag from (   "
			+ " select IFNULL(sum(tt.amount),0) amount,'附属建筑物' as name,case t2.building_status when 0 then 1 when 1 then 2   "
			+ " when 2 then 2 else 1 end flag from t_cost_building tt    "
			+ " left join t_compensation_cost t1 on tt.owner_nm = t1.owner_nm   "
			+ " left join t_abm_total_state t2 on tt.owner_nm = t2.owner_nm   " + " where tt.owner_nm = :nm    "
			+ " UNION ALL   "
			+ " select IFNULL(sum(tt.amount),0) amount,'农副业设施' as name,case t2.agricultural_facilities_status when 0 then 1 when 1 then 2   "
			+ " when 2 then 2 else 1 end flag  from t_cost_agricultural_facilities tt    "
			+ " left join t_compensation_cost t1 on tt.owner_nm = t1.owner_nm   "
			+ " left join t_abm_total_state t2 on tt.owner_nm = t2.owner_nm   " + " where tt.owner_nm = :nm    "
			+ " UNION ALL   "
			+ " select IFNULL(sum(tt.amount),0) amount,'零星树木' as name,case t2.trees_status when 0 then 1 when 1 then 2   "
			+ " when 2 then 2 else 1 end flag from t_cost_trees tt    "
			+ " left join t_compensation_cost t1 on tt.owner_nm = t1.owner_nm   "
			+ " left join t_abm_total_state t2 on tt.owner_nm = t2.owner_nm   " + " where tt.owner_nm = :nm    "
			+ " UNION ALL   "
			+ " select IFNULL(sum(tt.amount),0) amount,'房屋' as name,case t2.house_status when 0 then 1 when 1 then 2   "
			+ " when 2 then 2 else 1 end flag from t_cost_houses tt    "
			+ " left join t_compensation_cost t1 on tt.owner_nm = t1.owner_nm   "
			+ " left join t_abm_total_state t2 on tt.owner_nm = t2.owner_nm   " + " where tt.owner_nm = :nm    "
			+ " UNION ALL   "
			+ " select IFNULL(sum(tt.amount),0) amount,'其他补助' as name, case t2.other_status when 0 then 1 when 1 then 2    "
			+ " when 2 then 2 else 1 end flag from t_cost_other tt    "
			+ " left join t_compensation_cost t1 on tt.owner_nm = t1.owner_nm   "
			+ " left join t_abm_total_state t2 on tt.owner_nm = t2.owner_nm   " + " where tt.owner_nm = :nm   "
			+ " UNION ALL   "
			+ " select IFNULL(sum(tt.amount),0) amount,'个体工商户' as name,case t2.individual_status when 0 then 1 when 1 then 2    "
			+ " when -1 then 2 else 1 end flag from t_cost_individual tt    "
			+ " left join t_compensation_cost t1 on tt.owner_nm = t1.owner_nm   "
			+ " left join t_abm_total_state t2 on tt.owner_nm = t2.owner_nm   " + " where tt.owner_nm = :nm    "
			+ " UNION ALL   "
			+ " select IFNULL(sum(tt.amount),0) amount,'装修' as name,case t2.house_decoration_status when 0 then 1 when 1 then 2    "
			+ " when -1 then 2 else 1 end flag from t_cost_houses_decoration tt    "
			+ " left join t_compensation_cost t1 on tt.owner_nm = t1.owner_nm   "
			+ " left join t_abm_total_state t2 on tt.owner_nm = t2.owner_nm   " + " where tt.owner_nm = :nm   "
			+ " UNION ALL   "
			+ " select IFNULL(sum(tt.amount),0) amount,'成片青苗及果木' as name,case t2.young_crops_status when 0 then 1 when 1 then 2   "
			+ " when 2 then 2 else 1 end flag from t_cost_young_crops tt    "
			+ " left join t_compensation_cost t1 on tt.owner_nm = t1.owner_nm   "
			+ " left join t_abm_total_state t2 on tt.owner_nm = t2.owner_nm   " + " where tt.owner_nm = :nm   "
			+ " UNION ALL   "
			+ " select IFNULL(sum(tt.amount),0) amount,'征收土地' as name,0 flag from t_cost_levy_land tt    "
			+ " left join t_compensation_cost t1 on tt.owner_nm = t1.owner_nm   "
			+ " left join t_abm_total_state t2 on tt.owner_nm = t2.owner_nm   " + " where tt.owner_nm = :nm    "
			+ " UNION ALL   "
			+ " select IFNULL(sum(tt.amount),0) amount,'搬迁安置基础设施补助' as name,case t2.infrastructure_status when 0 then 1 when 1 then 2   "
			+ " when 2 then 2 else 1 end flag from t_cost_infrastructure tt    "
			+ " left join t_compensation_cost t1 on tt.owner_nm = t1.owner_nm   "
			+ " left join t_abm_total_state t2 on tt.owner_nm = t2.owner_nm   " + " where tt.owner_nm = :nm    "
			+ " UNION ALL   "
			+ " select IFNULL(sum(tt.amount),0) amount,'宅基地' as name,case t2.homestead_status when 0 then 1 when 1 then 2 "
			+ " when 2 then 2 else 1 end flag from t_cost_homestead tt    "
			+ " left join t_compensation_cost t1 on tt.owner_nm = t1.owner_nm   "
			+ " left join t_abm_total_state t2 on tt.owner_nm = t2.owner_nm   " + " where tt.owner_nm = :nm    "
			+ " UNION ALL   "
			+ " select IFNULL(sum(tt.amount),0) amount,'搬迁补助'as name,case t2.relocation_allowance_status when 0 then 1 when 1 then 2   "
			+ " when 2 then 2 else 1 end flag from t_cost_relocation_allowance tt    "
			+ " left join t_compensation_cost t1 on tt.owner_nm = t1.owner_nm   "
			+ " left join t_abm_total_state t2 on tt.owner_nm = t2.owner_nm    " + " where tt.owner_nm = :nm "
			+ " UNION ALL"
			+ " select IFNULL(tt.production_amount,0) amount,'生产安置' as name,case t1.production_status when 0 then 1 when 1 then 2 "
			+ " when 2 then 2 else 1 end flag from t_compensation_cost tt "
			+ " left join t_abm_total_state t1 on tt.owner_nm = t1.owner_nm  where tt.owner_nm = 'nm'  ) as ss where amount !=0", nativeQuery = true)
	public List<ProtocolItemVO> getProtocolItem(@Param("nm") String nm);

	/**
	 * 根据 nm 查询权属人查询补偿信息 t_info_owner_impl：权属人信息表 t_abm_protocol_info：补偿协议信息表
	 * t_abm_protocol_escrow：资金代管协议信息表 pub_region：行政区信息表 关联权属人信息表 region = cityCode
	 * total：总金额 根据十二张费用计算表合计 pub_dict_value：字典表 关联权属人信息表 安置类型 place_type = nm
	 * t_abm_total_state:权属人协议签订总状态表 取权属人各项实物指标签订状态 是否全部完成状态
	 */
	@Query(value = "select tt.place_name place,t4.name placeType,t4.code typeCode,tt.nm,tt.name,tt.id_card idCard,t1.`name` region, "
			+ "(if(ts.protocol_state=1,'已完成','未完成')) as flag,  (select count(1) from pub_files t9  left join ( "
			+ "select nm,owner_nm from t_abm_protocol_escrow " + "UNION ALL "
			+ "select nm,owner_nm from t_abm_protocol_info ) t8 "
			+ "on t9.table_pk_column = t8.nm and t9.table_name = 't_abm_protocol_escrow' or t9.table_name = 't_abm_protocol_info' "
			+ "where t9.table_pk_column = t8.nm and t8.owner_nm = tt.nm) as fujian,  "
			+ " CAST((select IFNULL(sum(amount),0) from ( select owner_nm,amount from t_cost_building " + " UNION ALL "
			+ " select owner_nm,amount from t_cost_agricultural_facilities " + " UNION ALL "
			+ " select owner_nm,amount from t_cost_trees " + " UNION ALL "
			+ " select owner_nm,amount from t_cost_houses " + " UNION ALL "
			+ " select owner_nm,amount from t_cost_individual " + " UNION ALL "
			+ " select owner_nm,amount from t_cost_houses_decoration " + " UNION ALL "
			+ " select owner_nm,amount from t_cost_other  " + " UNION ALL "
			+ " select owner_nm,amount from t_cost_infrastructure  " + " UNION ALL "
			+ " select owner_nm,amount from t_cost_homestead  " + " UNION ALL "
			+ " select owner_nm,amount from t_cost_levy_land  " + " UNION ALL "
			+ " select owner_nm,amount from t_cost_young_crops  " + " UNION ALL "
			+ " select owner_nm,amount from t_cost_relocation_allowance  UNION ALL"
			+ "	select owner_nm,production_amount from t_compensation_cost) dd where owner_nm = tt.nm)AS DECIMAL(19,2)) as total, "
			+ " CAST((select IFNULL(sum(protocol_amount),0) from t_abm_protocol_info where owner_nm = tt.nm)AS DECIMAL(19,2)) as protocoled, "
			+ " CAST((select IFNULL(sum(escrow_money),0) from t_abm_protocol_escrow where owner_nm = tt.nm)AS DECIMAL(19,2)) as escrow "
			+ "from t_info_owner_impl tt left join pub_region t1 on tt.region = t1.city_code "
			+ " left join pub_dict_value t4 on tt.place_type = t4.nm "
			+ "left join t_abm_total_state  ts on tt.nm = ts.owner_nm where tt.nm=:nm ", nativeQuery = true)
	public OwnerInfoVO getOwnerInfoByNm(@Param("nm") String nm);

	/**
	 * 协议签订页面 列表 根据权属人nm 查询 名下已生成的协议 t_abm_protocol_info：补偿协议表
	 * t_abm_protocol_escrow：资金代管协议 t_bpm_process：流程信息表 通过 流程id关联
	 */
	@Query(value = "select tt.id,tt.nm,tt.protocol_code protocolCode,tt.protocol_name protocolName,'' content,tt.is_escrow isEscrow,  "
			+ "tt.protocol_area protocolArea, CAST(tt.protocol_amount AS DECIMAL(19,2)) protocolAmount,tt.complete_time completeTime, 0 as flag,tt.state, "
			+ "(select count(1) from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_info') as fileCount,  "
			+ "(select CONCAT(file_name,'.',file_type) from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_info' order by upload_time desc LIMIT 1) as fileName,  "
			+ "(select file_url from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_info' order by upload_time desc LIMIT 1) as fileUrl,IFNULL(t1.cn_status,'未提交审核') status,"
			+ "(select nm from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_info' order by upload_time desc LIMIT 1) as fileNm  "
			+ "from t_abm_protocol_info tt left join t_bpm_process t1 on tt.process_id = t1.process_id "
			+ "where owner_nm = :nm " + "UNION ALL  "
			+ "select tt.id,tt.nm,tt.protocol_code protocolCode,tt.protocol_name protocolName, tt.content,'' isEscrow,  "
			+ "'' protocolArea,CAST(tt.escrow_money AS DECIMAL(19,2)) protocolAmount,tt.complete_time completeTime,1 as flag,tt.state, "
			+ " (select count(1) from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_escrow') as fileCount,  "
			+ "(select CONCAT(file_name,'.',file_type) from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_escrow' order by upload_time desc LIMIT 1) as fileName ,  "
			+ "(select file_url from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_escrow' order by upload_time desc LIMIT 1) as fileUrl ,IFNULL(t1.cn_status,'未提交审核') status,"
			+ " (select nm from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_escrow' order by upload_time desc LIMIT 1) as fileNm   "
			+ "from t_abm_protocol_escrow tt left join t_bpm_process t1 on tt.process_id = t1.process_id "
			+ "where owner_nm = :nm ", countQuery = "select count(1) from (select tt.id,tt.nm,tt.protocol_code protocolCode,tt.protocol_name protocolName,'' content,tt.is_escrow isEscrow,  tt.protocol_area protocolArea, "
					+ "FORMAT(tt.protocol_amount,2) protocolAmount,tt.complete_time completeTime, 0 as flag,tt.state, (select CONCAT(file_name,'.',file_type) from pub_files  "
					+ "where table_pk_column = tt.nm and table_name = 't_abm_protocol_info' order by upload_time desc LIMIT 1) as fileName,  (select file_url from pub_files  "
					+ "where table_pk_column = tt.nm and table_name = 't_abm_protocol_info' order by upload_time desc LIMIT 1) as fileUrl,IFNULL(t1.cn_status,'未提交审核') status   "
					+ "from t_abm_protocol_info tt left join t_bpm_process t1 on tt.process_id = t1.process_id where owner_nm = :nm "
					+ " UNION ALL  select tt.id,tt.nm,tt.protocol_code protocolCode, "
					+ "tt.protocol_name protocolName, tt.content,'' isEscrow,  '' protocolArea,FORMAT(tt.escrow_money,2) protocolAmount,tt.complete_time completeTime,1 as flag,tt.state,  "
					+ "(select CONCAT(file_name,'.',file_type) from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_escrow' order by upload_time desc LIMIT 1) as fileName ,  "
					+ " (select file_url from pub_files where table_pk_column = tt.nm and table_name = 't_abm_protocol_info' order by upload_time desc LIMIT 1) as fileUrl ,IFNULL(t1.cn_status,'未提交审核') status   "
					+ "from t_abm_protocol_escrow tt left join t_bpm_process t1 on tt.process_id = t1.process_id where owner_nm = :nm ) as t0", nativeQuery = true)
	public Page<ProtocolInfoVO> getInfoByPage(@Param("nm") String nm, Pageable pageable);

	/**
	 * 获取当天最大流水号（后两位）
	 */
	@Query(value = "select protocol_code from t_abm_protocol_info where date_format(complete_time,'%Y-%m-%d') = date_format(now(),'%Y-%m-%d') order by complete_time desc limit 1", nativeQuery = true)
	public String getProtocolCode();

	/**
	 * 导出
	 */
	@Query(value = "select `name`,column00,column01,column02,column03,column04,column05,column07,column08,FLOOR(column09)column09,CAST(column10 AS DECIMAL(19,2)) column10, "
			+ " CAST(column11 AS DECIMAL(19,2)) column11,CAST(column12 AS DECIMAL(19,2)) column12,CAST(column13 AS DECIMAL(19,2)) column13,CAST(column14 AS DECIMAL(19,2)) column14,CAST(column15 AS DECIMAL(19,2)) column15,CAST(column16 AS DECIMAL(19,2)) column16,  "
			+ " CAST(if(column17=0,column26,column17) AS DECIMAL(19,2)) column17,CAST(column18 AS DECIMAL(19,2)) column18,CAST(column19 AS DECIMAL(19,2)) column19,CAST(column20 AS DECIMAL(19,2)) column20,column25,CAST(column26 AS DECIMAL(19,2)) column26  "
			+ " from (select tt.protocol_name name,tt.protocol_code column00,t2.merger_name column01,t1.`name` column02,t5.name column03,t5.code typeCode,t1.place_name column04,CONCAT(t1.xiang,',',t1.cun,',',t1.zu) as column05, "
			+ " CONCAT(t1.xiang,',',t1.cun,',',t1.zu) as column07,IFNULL((select count(1) from t_info_family_impl where owner_nm = tt.owner_nm and is_satisfy= 2),0) as column08,IFNULL((select count(1) from t_info_family_impl where owner_nm = tt.owner_nm and is_produce= 1),0) as column09,    "
			+ " if(position(\"房屋\" in tt.protocol_area)>0,IFNULL(t4.house_amount,0),0)column10, "
			+ " if(position(\"装修\" in tt.protocol_area)>0,IFNULL(t4.house_decoration_amount,0),0)column11, "
			+ " if(position(\"附属建筑物\" in tt.protocol_area)>0,IFNULL(t4.building_amount,0),0)column12  , "
			+ " if(position(\"农副业设施\" in tt.protocol_area)>0,IFNULL(t4.agricultural_facilities_amount,0),0)column13 , "
			+ " if(position(\"个体工商户\" in tt.protocol_area)>0,IFNULL(t4.individual_amount,0),0)column14,    "
			+ " if(position(\"搬迁补助\" in tt.protocol_area)>0,IFNULL(t4.relocation_allowance_amount,0),0)column15, "
			+ " if(position(\"其他补助\" in tt.protocol_area)>0,(IFNULL(t4.other_amount,0)+ ifnull(t4.difficult_amount,0)),0)column16,    "
			+ " if(position(\"搬迁安置基础设施补助\" in tt.protocol_area)>0,(IFNULL(t4.infrastructure_amount,0)+IFNULL(t4.homestead_amount,0)),0)column17,  "
			+ " if(position(\"零星树木\" in tt.protocol_area)>0,IFNULL(t4.trees_amount,0),0)column18 , "
			+ " if(position('成片青苗及果木' in tt.protocol_area)>0,IFNULL(t4.young_crops_amount,0),0)column19  ,   "
			+ " if(position('征收土地' in tt.protocol_area)>0,IFNULL(t4.levy_land_amount,0),0)column20,t1.id_card column25, "
			+ " if(position('宅基地' in tt.protocol_area)>0,(IFNULL(t4.infrastructure_amount,0)+IFNULL(t4.homestead_amount,0)),0)column26 "
			+ " from t_abm_protocol_info tt left join t_info_owner_impl t1 on tt.owner_nm = t1.nm    "
			+ " left join pub_region t2 on t1.region = t2.city_code    "
			+ " left join pub_dict_value t5 on t1.place_type = t5.nm  "
			+ " left join t_compensation_cost t4 on tt.owner_nm = t4.owner_nm  where tt.id = :id ) as dd ", nativeQuery = true)
	public Map<String, Object> export(@Param("id") Integer id);

	/**
	 * 查询未完成的补偿协议数量
	 */
	@Query(value = "select count(1) from t_abm_protocol_info where owner_nm = :ownerNm and state = 0", nativeQuery = true)
	public Integer getProtoColCount(@Param("ownerNm") String ownerNm);

	/**
	 * 查询未完成的资金代管协议数量
	 */
	@Query(value = "select count(1) from t_abm_protocol_escrow where owner_nm = :ownerNm and state = 0", nativeQuery = true)
	public Integer getProtocolEscrowCount(@Param("ownerNm") String ownerNm);

	/**
	 * 根据协议编码查询协议 author:Yanxh
	 * 
	 * @param protocolCode
	 */
	ProtocolInfo findByProtocolCode(String protocolCode);

	@Query(value = "select * from t_abm_protocol_info where owner_nm = :ownerNm and is_escrow =1", nativeQuery = true)
	ProtocolInfo findProtocolInfo(@Param("ownerNm") String ownerNm);

	@Query(value = "select * from t_abm_protocol_info where owner_nm =?1 and state = 0", nativeQuery = true)
	List<ProtocolInfo> getByOwnerNmIsNotSuccess(String ownerNm);
}
