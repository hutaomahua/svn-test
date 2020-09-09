package com.lyht.business.abm.paymentManagement.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.business.abm.paymentManagement.dao.PaymentDetailDao;
import com.lyht.business.abm.paymentManagement.dao.PaymentInfoDao;
import com.lyht.business.abm.paymentManagement.entity.PaymentDetailEntity;
import com.lyht.business.abm.paymentManagement.entity.PaymentInfoEntity;
import com.lyht.business.abm.paymentManagement.entity.VProtocolFinish;
import com.lyht.business.abm.paymentManagement.pojo.ApplyDetail;
import com.lyht.business.abm.paymentManagement.pojo.ApplyInfo;
import com.lyht.business.abm.paymentManagement.pojo.InfoListPojo;
import com.lyht.business.abm.paymentManagement.pojo.InfoPagePojo;
import com.lyht.business.abm.paymentManagement.to.Msg;
import com.lyht.business.abm.paymentManagement.vo.ApplyAuditVO;
import com.lyht.business.abm.paymentManagement.vo.BankCardVO;
import com.lyht.business.abm.paymentManagement.vo.InfoListVO;
import com.lyht.business.abm.paymentManagement.vo.OwnerInfoVO;
import com.lyht.business.abm.paymentManagement.vo.OwnerPaymentInfoVO;
import com.lyht.business.abm.paymentManagement.vo.OwnerSelectVO;
import com.lyht.business.abm.paymentManagement.vo.PaymentInfoVO;
import com.lyht.business.abm.paymentManagement.vo.PaymentMethodVO;
import com.lyht.business.process.vo.ProcessOperateVO;
import com.lyht.business.pub.dao.PubDictValueDao;
import com.lyht.business.pub.dao.PubRegionDao;
import com.lyht.business.pub.entity.PubDictValue;
import com.lyht.business.pub.entity.PubRegionEntity;
import com.lyht.system.pojo.SysDept;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

@Service
@Transactional(rollbackFor = Exception.class)
public class PaymentApplyService {

	@Autowired
	private PaymentInfoDao paymentInfoDao;
	@Autowired
	private PaymentDetailDao paymentDetailDao;
	@Autowired
	private PaymentAuditService paymentAuditService;
	@Autowired
	private VProtocolFinishService protocolFinishService;
    @Autowired
    private PubRegionDao regionDao;
    @Autowired
	private PubDictValueDao pubDictValueDao;
    
	public List<Map<String, Object>> paymentStatistics(Integer levelType) {
		List<Map<String, Object>> allData = paymentInfoDao.finAllGroupByRegion();
		if(allData.size() == 0)return new ArrayList<>();
		
		List<String> levelList = new ArrayList<String>();
        levelList = regionDao.queryLevelList(levelType);
        for (int i = 0; i < levelList.size(); i++) {
            findAllParentRegionData(allData,levelList.get(i),levelType);
        }
		List<Map<String, Object>> treeList = toTree(allData,CommonUtil.trim(allData.get(allData.size()-1).get("parentcode")) , true, 0, null, new ArrayList<>(), new ArrayList<>());
		
		return treeList;
	}
	
	@SuppressWarnings("static-access")
	public Page<OwnerPaymentInfoVO> ownerPaymentInfoList(InfoPagePojo infoPagePojo, LyhtPageVO lyhtPageVO){
		Pageable pageable = lyhtPageVO.getPageable(lyhtPageVO);
		Page<OwnerPaymentInfoVO> infoPage = paymentInfoDao.ownerPaymentInfoList(
				CommonUtil.trim(infoPagePojo.getOwnername()),
				CommonUtil.trim(infoPagePojo.getIdcard()),
				CommonUtil.trim(infoPagePojo.getRegion()),
				pageable);
		return infoPage;
	}
	/**
	 * 递归查询所有行政区的父级  聚合数据
	 * @param pool
	 * @param level 
	 * @param levelType 
	 */
	public void findAllParentRegionData(List<Map<String, Object>> pool, String level, Integer levelType){
		ArrayList<Map<String, Object>> list = new ArrayList<>(pool);
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> row = list.get(i);
			String rowLevel = CommonUtil.trim(row.get("regionlevel")).toString();
			if (level.equals(rowLevel)) {
				PubRegionEntity parentRegion = regionDao.getParentRegionByCityCode(CommonUtil.trim(row.get("citycode")).toString(),levelType);
				if (parentRegion == null) return;
				//获取当前处理级别的父级数据，最终要改动这部分数据
                Boolean flag = false;
                for (int y = 0; y < pool.size(); y++) {
                	Map<String, Object> pRow = pool.get(y);
                	if(CommonUtil.trim(pRow.get("citycode").toString()).equals(parentRegion.getCityCode())){
                		flag = true;
                		//如若父级节点已存在，则追加数据
                		BigDecimal households = new BigDecimal(CommonUtil.trim(row.get("households")).toString());
                		BigDecimal pHouseholds = new BigDecimal(CommonUtil.trim(pRow.get("households")).toString());
                		pRow.put("households", households.add(pHouseholds));
                		
                		BigDecimal amount = new BigDecimal(CommonUtil.trim(row.get("amount")).toString());
                		BigDecimal pAmount = new BigDecimal(CommonUtil.trim(pRow.get("amount")).toString());
                		pRow.put("amount", amount.add(pAmount));
                		
                		BigDecimal payed = new BigDecimal(CommonUtil.trim(row.get("payed")).toString());
                		BigDecimal pPayed = new BigDecimal(CommonUtil.trim(pRow.get("payed")).toString());
                		pRow.put("payed",payed.add(pPayed));
                		
                		BigDecimal surplus = new BigDecimal(CommonUtil.trim(row.get("surplus")).toString());
                		BigDecimal pSurplus = new BigDecimal(CommonUtil.trim(pRow.get("surplus")).toString());
                		pRow.put("surplus",surplus.add(pSurplus));
                		
                		BigDecimal realpayed = new BigDecimal(CommonUtil.trim(row.get("realpayed")).toString());
                		BigDecimal pRealpayed = new BigDecimal(CommonUtil.trim(pRow.get("realpayed")).toString());
                		pRow.put("realpayed",realpayed.add(pRealpayed));
                		
                		BigDecimal realsurplus = new BigDecimal(CommonUtil.trim(row.get("realsurplus")).toString());
                		BigDecimal pRealsurplus = new BigDecimal(CommonUtil.trim(pRow.get("realsurplus")).toString());
                		pRow.put("realsurplus",realsurplus.add(pRealsurplus));
                	}
                }
                //父节节点不存在，新增父级节点
                if (!flag) {
                	HashMap<String, Object> map = new HashMap<String, Object>();
                	map.put("region"		, parentRegion.getName());
                	map.put("citycode"		, parentRegion.getCityCode());
                	map.put("parentcode"	, parentRegion.getParentCode());
                	map.put("regionlevel"	, parentRegion.getLevel());
                	
            		BigDecimal households = new BigDecimal(CommonUtil.trim(row.get("households")).toString());
                	map.put("households"	, households);
                	
            		BigDecimal amount = new BigDecimal(CommonUtil.trim(row.get("amount")).toString());
                	map.put("amount"		, amount);
                	
            		BigDecimal payed = new BigDecimal(CommonUtil.trim(row.get("payed")).toString());
                	map.put("payed"			, payed);
                	
            		BigDecimal surplus = new BigDecimal(CommonUtil.trim(row.get("surplus")).toString());
                	map.put("surplus"		, surplus);
                	
            		BigDecimal realpayed = new BigDecimal(CommonUtil.trim(row.get("realpayed")).toString());
                	map.put("realpayed"		, realpayed);
                	
            		BigDecimal realsurplus = new BigDecimal(CommonUtil.trim(row.get("realsurplus")).toString());
                	map.put("realsurplus"	, realsurplus);
                	
                	pool.add(map);
                }
			}
		}
	}
	public List<Map<String, Object>> allRegion(Integer levelType){
		List<Map<String, Object>> allData = paymentInfoDao.finAllRegion();
		if(allData.size() == 0)return new ArrayList<>();
		
		List<String> levelList = new ArrayList<String>();
        levelList = regionDao.queryLevelList(levelType);
        for (int i = 0; i < levelList.size(); i++) {
            findAllParentRegion(allData,levelList.get(i),levelType);
        }
		List<Map<String, Object>> treeList = toTree(allData,CommonUtil.trim(allData.get(allData.size()-1).get("parentcode")), true, 0, null, new ArrayList<>(), new ArrayList<>());
		
		return treeList;
	}
	/**
	 * 递归查询所有行政区的父级
	 * @param pool
	 * @param level 
	 * @param levelType 
	 */
	public void findAllParentRegion(List<Map<String, Object>> pool, String level, Integer levelType){
		for (int i = 0; i < pool.size(); i++) {
			Map<String, Object> row = pool.get(i);
			String rowLevel = CommonUtil.trim(row.get("regionlevel")).toString();
			if (level.equals(rowLevel)) {
				PubRegionEntity parentRegion = regionDao.getParentRegionByCityCode(CommonUtil.trim(row.get("citycode")).toString(),levelType);
				if (parentRegion == null) return;
				//获取当前处理级别的父级数据，最终要改动这部分数据
                Boolean flag = false;
                for (int y = 0; y < pool.size(); y++) {
                	Map<String, Object> pRow = pool.get(y);
                	if( CommonUtil.trim(pRow.get("citycode")).equals(parentRegion.getCityCode()) ){
                		flag = true;
                	}
                }
                //父节节点不存在，新增父级节点
                if (!flag) {
                	HashMap<String, Object> map = new HashMap<String, Object>();
                	map.put("region"		, parentRegion.getName());
                	map.put("citycode"		, parentRegion.getCityCode());
                	map.put("parentcode"	, parentRegion.getParentCode());
                	map.put("regionlevel"	, parentRegion.getLevel());
                	pool.add(map);
                }
			}
		}
	}
	
	/**
     * 转为树形结构
     *	每次循环完成去除掉已处理的数据，但是涉及到迭代循环删除数据使用for循环，循环执行效率没有foreach高，目前处理速度差不多，数据量很大的情况下可能会略优一点
     * @param mapList
     * @param pCode   父节点编码
     * @param isOne   是否第一次进入
     * @param level   级别
     * @param parent  父节点的序列号 1.1.2
     * @param parents 配合前端做查询时展开所用到的，为当前节点所有父节点编码
     * @return allData, "", true, 0, null, new ArrayList<>()
     */
    public List<Map<String, Object>> toTree2(List<Map<String, Object>> mapList, String pCode, Boolean isOne, Integer level, String parent, List<String> parentsCode, List<String> parentsName) {
        List<Map<String, Object>> rData = new ArrayList<>();
        //定义序号
        int serial = 1;
        //这个循环不能使用foreach委托给迭代器，否则会抛出异常
        int size = mapList.size();
        for (int i = 0 ; i < size ; i++) {
        	Map<String, Object> map = mapList.get(i);
            Map<String, Object> data = new HashMap<>();
            String parentCode 	= CommonUtil.trim(map.get("parentcode")).toString(),
                    cityCode 	= CommonUtil.trim(map.get("citycode")).toString(),
            		cityName	= CommonUtil.trim(map.get("region")).toString();
            if (parentCode.equals(pCode) || (cityCode.equals(pCode)) && isOne) {
                List<String> tempParentsCode = new ArrayList<>();
                List<String> tempParentsName = new ArrayList<>();
                tempParentsCode.addAll(parentsCode);
                tempParentsCode.add(cityCode);
                tempParentsName.addAll(parentsName);
                tempParentsName.add(cityName);
                //前端处理缩进时使用
                data.put("level", level);
                String serialS = isOne ? serial + "" : parent + "." + serial;
                if (!isOne) {
                    data.put("serial", parent + "." + serial);
                } else {
                    data.put("serial", serial);
                }
                serial++;
                //已经添加到结果集的数据不再处理//在数据开始递归之前根据索引删除
                mapList.remove(i);i--;size--;
                //寻找该节点的子节点					//防止多个循环指向一个对象做删除操作，创建新的对象继续做递归操作
                List<Map<String, Object>> children = toTree(new ArrayList<>(mapList), cityCode, false, level + 1, serialS, tempParentsCode,tempParentsName);
                data.putAll(map);
                data.put("parentsCode", parentsCode);
                data.put("parentsName", parentsName);
                //判断该节点是否含有子节点   如果没有则设置标识为最后一个节点
                if (children != null && children.size() > 0) {
                    data.put("children", children);
                } else {
                    data.put("lastNode", true);
                }
                rData.add(data);
            }
        }
        return rData;
    }
	/**
     * 转为树形结构
    *	foreach的执行更为高效一些，但是存在同样数据重复循环的情况
    * @param mapList
    * @param pCode   父节点编码
    * @param isOne   是否第一次进入
    * @param level   级别
    * @param parent  父节点的序列号 1.1.2
    * @param parents 配合前端做查询时展开所用到的，为当前节点所有父节点编码
    * @return allData, "", true, 0, null, new ArrayList<>()
    */
    public List<Map<String, Object>> toTree(List<Map<String, Object>> mapList, String pCode, Boolean isOne, int level, String parent, List<String> parentsCode, List<String> parentsName) {
        List<Map<String, Object>> rData = new ArrayList<>();
        //定义序号
        int serial = 1;
        for (Map<String, Object> map : mapList) {
            Map<String, Object> data = new HashMap<>();
            String parentCode 	= CommonUtil.trim(map.get("parentcode")).toString(),
                    cityCode 	= CommonUtil.trim(map.get("citycode")).toString(),
            		cityName	= CommonUtil.trim(map.get("region")).toString();
            if (parentCode.equals(pCode) || (cityCode.equals(pCode)) && isOne) {
                List<String> tempParentsCode = new ArrayList<>();
                List<String> tempParentsName = new ArrayList<>();
                tempParentsCode.addAll(parentsCode);
                tempParentsCode.add(cityCode);
                tempParentsName.addAll(parentsName);
                tempParentsName.add(cityName);
                //前端处理缩进时使用
                data.put("level", level);
                String serialS = isOne ? serial + "" : parent + "." + serial;
                if (!isOne) {
                    data.put("serial", parent + "." + serial);
                } else {
                    data.put("serial", serial);
                }
                serial++;
                //寻找该节点的子节点
                List<Map<String, Object>> children = toTree(mapList, cityCode, false, level + 1, serialS, tempParentsCode,tempParentsName);
                data.putAll(map);
                data.put("parentsCode", parentsCode);
                data.put("parentsName", parentsName);
                //判断该节点是否含有子节点   如果没有则设置标识为最后一个节点
                if (children != null && children.size() > 0) {
                    data.put("children", children);
                } else {
                    data.put("lastNode", true);
                }
                rData.add(data);
            }
        }
        return rData;
    }
    
    /**
     * 按照征地范围统计兑付申请金额
     * region:行政区明文/type:{amount:已发起兑付申请的协议的总额,payed:已申请过协议总额,surplus:待申请的协议总额}
     * @param region
     * @param type
     * @return
     */
	@SuppressWarnings("serial")
	public Map<String, Object> statisticsDetail(String region, String type){
    	
    	String herder = "SD_";
    	
    	List<Map<String, Object>> allScope = paymentInfoDao.getAllScope();
    	//生成头
    	List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>(){{
    		add(new HashMap<String, Object>(){{
    	        put("nm","project");
    	        put("name","项目");
    	    }});
    		add(new HashMap<String, Object>(){{
    	        put("nm","unit");
    	        put("name","单位");
    	    }});
    	}};
    	treeList.addAll(scopeDataToTree(allScope, "", true,herder));
    	
		List<Map<String, Object>> mapList = paymentInfoDao.getStatisticsDetailByRegion(region);
		ArrayList<HashMap<String, Object>> dataList = statisticsDetailCalculate(treeList,mapList,herder,type);
		
		
		return new HashMap<String, Object>(){{
			put("title",treeList);
			put("datalist",dataList);
		}};
    }
    @SuppressWarnings({ "unchecked", "serial" })
	public ArrayList<HashMap<String, Object>> statisticsDetailCalculate(List<Map<String, Object>> treeList, List<Map<String, Object>> mapList, String herder, String type){
		HashMap<String, Object> protocolMap = new HashMap<String, Object>();
		HashMap<String, Object> householdsMap = new HashMap<String, Object>();
		
		for (Map<String, Object> map : treeList) {
			List<Map<String, Object>> childrenList =  (List<Map<String, Object>>) map.get("children");
			String pnm =  map.get("nm").toString();
			if(childrenList != null){
				protocolMap.put(pnm+"Count", 0);
				householdsMap.put(pnm+"Count", 0);
				
				Map<String,BigDecimal> count = new HashMap<String,BigDecimal>(){{
					put("moneyCount",new BigDecimal("0"));
					put("personCount",new BigDecimal("0"));
				}};
				for (Map<String, Object> children : childrenList) {
					String nm = children.get("nm").toString();
					protocolMap.put(nm, 0);
					householdsMap.put(nm, 0);
					assignment(mapList,nm,herder,type,protocolMap,householdsMap,count);
				}
				
				HashMap<String, Object> countMap = new HashMap<String, Object>();
				countMap.put("nm", pnm+"Count");
				countMap.put("name", "合计");
				protocolMap.put(pnm+"Count", count.get("moneyCount"));
				householdsMap.put(pnm+"Count", count.get("personCount"));
				childrenList.add(0,countMap);//合计项放前边
			}else{
				protocolMap.put(pnm, 0);
				householdsMap.put(pnm, 0);
				assignment(mapList,pnm,herder,type,protocolMap,householdsMap,null);
			}
		}
		protocolMap.put("project", "金额");
		protocolMap.put("unit", "元");
		householdsMap.put("project", "户数");
		householdsMap.put("unit", "户");
		return new ArrayList<HashMap<String, Object>>(){{
			add(householdsMap);
			add(protocolMap);
		}};
    }
    public void assignment(List<Map<String, Object>> mapList,String nm, String herder, String type, HashMap<String, Object> protocolMap, HashMap<String, Object> householdsMap, Map<String, BigDecimal> count){
    	for (Map<String, Object> data : mapList) {
			String money = "0";
			String person = data.get("households").toString();
			if("amount".equals(type)){
				money = data.get("protocolAmount").toString();
			}else if("payed".equals(type)){
				money = data.get("protocolPayed").toString();
			}else if("surplus".equals(type)){
				money = data.get("protocolSurplus").toString();
			}
			if((herder + data.get("scopeNm")).equals(nm)){
				protocolMap.put(nm, money);
				householdsMap.put(nm, data.get("households").toString());
				if(count != null){
					count.put("moneyCount", count.get("moneyCount").add(new BigDecimal(money)));
					count.put("personCount", count.get("personCount").add(new BigDecimal(person)));
				}
			}
		}
    }
    /**
     * 征地范围转为树形结构
    * @param mapList
    * @param parentNm  父节点编码
    * @param isFirst   是否第一次进入
    * @return
    */
    public List<Map<String, Object>> scopeDataToTree(List<Map<String, Object>> mapList, String parentNm, Boolean isFirst,String header) {
        List<Map<String, Object>> rData = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            Map<String, Object> data = new HashMap<>();
            String pNm 	= CommonUtil.trim(map.get("parentNm")).toString(),
                    cityCode 	= CommonUtil.trim(map.get("nm")).toString();
            if (pNm.equals(parentNm) || (cityCode.equals(parentNm)) && isFirst) {
                //寻找该节点的子节点
                List<Map<String, Object>> children = scopeDataToTree(mapList, cityCode, false,header);
                data.put("nm", header + map.get("nm"));
                data.put("name", map.get("name"));
                //判断该节点是否含有子节点   如果没有则设置标识为最后一个节点
                if (children != null && children.size() > 0) {
                    data.put("children", children);
                }
                rData.add(data);
            }
        }
        return rData;
    }
    /**
     * 
     * @param infoPagePojo
     * @param lyhtPageVO
     * @return 
     */
	@SuppressWarnings("static-access")
	public Page<InfoListVO> infoPage(InfoPagePojo infoPagePojo, LyhtPageVO lyhtPageVO) {
		Pageable pageable = lyhtPageVO.getPageable(lyhtPageVO);
		Page<InfoListVO> infoPage = paymentInfoDao.infoPage(
				CommonUtil.trim(infoPagePojo.getOwnernm()),
				CommonUtil.trim(infoPagePojo.getRegion()),
				CommonUtil.trim(infoPagePojo.getOwnername()),
				CommonUtil.trim(infoPagePojo.getIdcard()),
				CommonUtil.trim(infoPagePojo.getProtocolname()),
				CommonUtil.trim(infoPagePojo.getSt()),
				CommonUtil.trim(infoPagePojo.getEt()),
				pageable);
		return infoPage;
	}
    
	public List<PaymentDetailEntity> detailList() {
		List<PaymentDetailEntity> list = paymentDetailDao.findAll();
		return list;
	}
	
	/**
	 * 审核列表
	 * @param infoPagePojo 
	 * @param lyhtPageVO 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public Page<ApplyAuditVO> auditList(InfoPagePojo infoPagePojo, LyhtPageVO lyhtPageVO){
		Pageable pageable = lyhtPageVO.getPageable(lyhtPageVO);
		Page<ApplyAuditVO> auditList = paymentDetailDao.auditList(infoPagePojo.getRegion(),infoPagePojo.getOwnername(),infoPagePojo.getIdcard(),infoPagePojo.getProtocolname(),
				infoPagePojo.getSt(),infoPagePojo.getEt(),pageable);
		return auditList;
	}

	/**
	 * 根据流程实例ID获取兑付申请信息
	 * @param processId
	 * @return
	 */
	public ApplyAuditVO getByProcessId(String processId) {
		ApplyAuditVO res = paymentDetailDao.getDetailByProcessId(processId);
		return res;
	}
	
	/**
	 * 审核
	 * @param nm
	 * @param "Approved".equals(anObject) state
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Msg audit(String processId,String  state){
		if("".equals(CommonUtil.trim(processId)))return new Msg(false,"流程ID为空");
		if(!("Approved".equals(state)||"Rejected".equals(state)))return new Msg(false,"未知的审核状态");
		
		PaymentDetailEntity paymentDetail = paymentDetailDao.findByProcessId(processId);
		if(paymentDetail == null)return new Msg(false,"查询不到该记录");
		//更新兑付信息（若不存在则新增）
		PaymentInfoEntity paymentInfo = paymentInfoDao.findByCodeAndType(paymentDetail.getProtocolCode(),paymentDetail.getProtocolType());
		if("Rejected".equals(state)){//审核不通过
			paymentInfo.setIsnext(0);
		}else if("Approved".equals(state)){//审核通过
			if(paymentInfo == null){
				Msg<VProtocolFinish> protocol = protocolFinishService.getByCodeAndFlag(paymentDetail.getProtocolCode(), paymentDetail.getProtocolType());
				if(!protocol.getFlag())return protocol;
				VProtocolFinish protocolInfo = protocol.getResult();
				if(protocolInfo == null)return new Msg(false,"在已签订完成协议列表中查询不到该协议");
				paymentInfo = new PaymentInfoEntity();
				paymentInfo.setNm(Randomizer.generCode(10));
				paymentInfo.setOwnerNm(paymentDetail.getOwnerNm());
				paymentInfo.setProtocolCode(paymentDetail.getProtocolCode());
				paymentInfo.setProtocolType(paymentDetail.getProtocolType());
				paymentInfo.setProtocolAmount(new BigDecimal(protocolInfo.getProtocolAmount().toString()));
				paymentInfo.setProtocolPayed(paymentDetail.getApplyAmount());
				paymentInfo.setProtocolSurplus(paymentInfo.getProtocolAmount().subtract(paymentInfo.getProtocolPayed()));
				paymentInfo.setBatch(paymentDetail.getApplyBatch());
				paymentInfo.setIsnext(0);
			}else{
				if(paymentDetail.getApplyBatch() <= paymentInfo.getBatch())return new Msg(false,"当前批次已兑付");
				paymentInfo.setProtocolPayed(paymentInfo.getProtocolPayed().add(paymentDetail.getApplyAmount()));
				paymentInfo.setProtocolSurplus(paymentInfo.getProtocolAmount().subtract(paymentInfo.getProtocolPayed()));
				paymentInfo.setBatch(paymentDetail.getApplyBatch());
				paymentInfo.setIsnext(0);
				//若协议总额小于以申请额，则申请不成立
			}
			if(paymentInfo.getProtocolAmount().compareTo(paymentInfo.getProtocolPayed()) == -1)return new Msg(false,"申请金额大于可申请金额");
		}
		paymentInfoDao.save(paymentInfo);
		paymentDetailDao.save(paymentDetail);
		return new Msg(true,"操作成功");
	}
	
	/**
	 * 查询已经签订过协议的权属人
	 * @param loginDept 
	 * @return
	 */
	public List<OwnerSelectVO> resolveOwnerList(SysDept loginDept) {
		String protocolTyoe = "";//权限控制，施工单位展示资金代管协议，政府机构展示全部，其余展示补偿协议
		if("6E4D421B38".equals(loginDept.getDeptType())){//施工单位
			protocolTyoe = "1";
		}else if("13FC1DFF04".equals(loginDept.getDeptType())){//政府机构
			protocolTyoe = "0,1";
		}else{
			protocolTyoe = "0";
		}
		List<OwnerSelectVO> ownerSelectList = protocolFinishService.getOwnerSelectList(protocolTyoe);
		return ownerSelectList;
	}
	

	public Msg<BigDecimal> save(ApplyInfo applyInfo,ProcessOperateVO processOperateVO,HttpServletRequest request) {
		List<PaymentDetailEntity> paymentDetailList = new ArrayList<PaymentDetailEntity>();
		for (ApplyDetail applyDetail : applyInfo.getDetailList()) {
			PaymentDetailEntity paymentDetail = new PaymentDetailEntity();
			//内码
			paymentDetail.setNm(Randomizer.generCode(10));
			//权属人内码
			paymentDetail.setOwnerNm(applyInfo.getOwnernm());
			//协议编号
			paymentDetail.setProtocolCode(applyDetail.getProtocolcode());
			//协议类型 0：补偿协议  1：资金代管协议
			paymentDetail.setProtocolType(applyDetail.getProtocoltype());
			//申请时间 yyyy-MM-dd hh:mm:ss
			paymentDetail.setApplyTime(applyDetail.getApplytime());
			//申请批次
			paymentDetail.setApplyBatch(applyDetail.getBatch());
			Msg<BigDecimal> applyAmountCalculate = ApplyAmountCalculate(
					applyInfo.getOwnernm(),
					applyDetail.getProtocolcode(),
					applyDetail.getProtocolamount(),
					applyDetail.getProtocolsurplus(),
					applyDetail.getProtocoltype(),
					applyInfo.getPlacetype(),
					applyDetail.getBatch());
			if(applyAmountCalculate.getFlag()){
				//申请金额
				paymentDetail.setApplyAmount(applyAmountCalculate.getResult());				
			}else{
				return applyAmountCalculate;
			}
			//申办人（明文）
			paymentDetail.setProposer(applyDetail.getProposer());
			//申办人部门（明文）
			paymentDetail.setProposerDept(applyDetail.getProposerdept());
			//收款人（明文）
			paymentDetail.setPayee(applyDetail.getPayee());
			//支付方式（字典）
			paymentDetail.setPaymentMethod(applyDetail.getPaymentmethod());
			//银行卡号
			paymentDetail.setBankCard(applyDetail.getBankcard());
			//开户行
			paymentDetail.setDepositBank(applyDetail.getDepositbank());
			//说明信息
			paymentDetail.setDescription(applyDetail.getDescription());
			//备注信息
			paymentDetail.setRemark(applyDetail.getRemark());
			//MIS流程实例ID
			paymentDetail.setProcessId(paymentAuditService.paymentApply(paymentDetail,processOperateVO,request));
			
			paymentDetailList.add(paymentDetail);
			//更新兑付信息
			PaymentInfoEntity paymentInfo = paymentInfoDao.findByCodeAndType(paymentDetail.getProtocolCode(),paymentDetail.getProtocolType());
			if( paymentInfo != null ){
				paymentInfo.setIsnext(1);
				paymentInfoDao.save(paymentInfo);
			}
		}
		paymentDetailDao.saveAll(paymentDetailList);
		return new Msg<BigDecimal>(true,"兑付申请发起成功");
	}
	//兑付申请金额计算
	public Msg<BigDecimal> ApplyAmountCalculate(
			String ownernm,
			String protocolCode,//协议编号
			BigDecimal protocolAmount,//协议总额
			BigDecimal protocolsurplus,//可申请金额
			Integer protocolType,//协议类型0:补偿协议  1:资金代管协议
			String placeType,//安置类型
			Integer batch//申请批次
	){
		//若为补偿协议则去查询该协议有无资金代管金额，若有  计算时在总额基础之上减去资金代管金额
		if(protocolType == 0){
			Msg<VProtocolFinish> protocol = protocolFinishService.getByCodeAndFlag(protocolCode,protocolType);
			if(!protocol.getFlag())return new Msg<BigDecimal>(protocol.getFlag(),protocol.getMsg());
			protocolAmount = protocolAmount.subtract(protocol.getResult().getProtocolEscrow());
		}
		//根据权属人内码调用接口查询 是否签订资金代管协议  // 如果=0 则为未签订资金代管协议
		boolean hasProtocolType1 = protocolFinishService.getEscrowProtocolCount(ownernm) == 0 ? false : true;
		//move_type_village	农村集中安置
		//move_type_town	集镇集中安置
		//move_type_thrice	分散后靠
		//move_type_once	分散货币
		@SuppressWarnings("unused")
		String[] moveType = {"move_type_village","move_type_town","move_type_thrice","move_type_once"};

		String ratio = "0";
		
		if(protocolType == 1){//资金代管协议计算
			if(batch == 1 || batch == 3){
				ratio = "0.3";
			}else if(batch == 2){
				ratio = "0.4";
			}else{
				return new Msg<BigDecimal>(false,"协议【"+protocolCode+"】 "+"错误的协议申请批次");
			}
		}else if(protocolType == 0){//补偿协议计算
			
			//校验安置类型
			if("move_type_once".equals(placeType)){//分散货币申请金额计算
				if(batch == 1){
					ratio = "1";
				}else{
					return new Msg<BigDecimal>(false,"协议【"+protocolCode+"】 "+"错误的协议申请批次");
				}
			}else if("move_type_thrice".equals(placeType)){//分散后靠
				if(batch == 1 || batch == 3){
					ratio = "0.3";
				}else if(batch == 2){
					ratio = "0.4";
				}else{
					return new Msg<BigDecimal>(false,"协议【"+protocolCode+"】 "+"错误的协议申请批次");
				}
			}else if("move_type_village".equals(placeType) || "move_type_town".equals(placeType)){
				if(hasProtocolType1){
					//有资金代管协议的第一次申请返回全部数据
					if(batch == 1){
						ratio = "1";
					}else if(batch == 2){//存在还没签订资金代管协议后边补充上的情况，依旧照常分批
						ratio = "0.4";
					}else if(batch == 3){//存在还没签订资金代管协议后边补充上的情况，依旧照常分批
						ratio = "0.3";
					}else{
						return new Msg<BigDecimal>(false,"协议【"+protocolCode+"】 "+"错误的协议申请批次");
					}
				}else{
					if(batch == 1 || batch == 3){
						ratio = "0.3";
					}else if(batch == 2){
						ratio = "0.4";
					}else{
						return new Msg<BigDecimal>(false,"协议【"+protocolCode+"】 "+"错误的协议申请批次");
					}
				}
			}else{
				return new Msg<BigDecimal>(false,"未知的安置类型");
			}
		}else{
			return new Msg<BigDecimal>(false,"协议【"+protocolCode+"】 "+"未知的协议类型");
		}
		
		BigDecimal ratioB = new BigDecimal(ratio);
		BigDecimal res = protocolAmount.multiply(ratioB);
		if(res.compareTo(protocolsurplus) != 1){
			return new Msg<BigDecimal>(res);
		}else{
			return new Msg<BigDecimal>(false,"协议【"+protocolCode+"】 "+"申请额有误");
		}
	}
	/**
	 * 根据权属人内码查询已签订的协议
	 * @param ownerNm
	 * @param protocolCode	带协议编号查询时只返回对应这条协议编号的协议
	 * @param protocolType 
	 * @param type	add:仅显示没有发起过兑付申请的协议
	 * @param protocolTyoe 
	 * @param loginDept 
	 * @return
	 */
	public List<Map<String, Object>> querySignedProtocol(String ownerNm, String protocolCode, Integer protocolType, String type, String protocolTypeSection) {
		
		List<Map<String, Object>> protocolMapList = new ArrayList<Map<String, Object>>();
		//根据权属人内码查询出已签订的协议
		List<VProtocolFinish> protocolList = protocolFinishService.getByOwnerNmAndProtocolType(ownerNm,protocolTypeSection);
		//根据权属人内码查询出已经做兑付的协议数据
		List<PaymentInfoEntity> paymentInfoList = paymentInfoDao.findByOwnerNm(ownerNm);
		
		//插入每条协议的已申请金额与剩余金额
		for (VProtocolFinish protocol : protocolList) {
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			//将实体拆解到map中   ps 暂时找不到快速自动化将接口定义的实体转为map的方法，用笨方法做
			map.put("id" 			, protocol.getId());
			map.put("nm" 			, protocol.getNm());
			map.put("protocolCode" 	, protocol.getProtocolCode());//协议编号
			map.put("protocolName" 	, protocol.getProtocolName());//协议名称
			map.put("protocolArea" 	, protocol.getProtocolArea());//协议范围
			map.put("flag" 			, protocol.getProtocolType());//用来判断协议类别 0:补偿协议,1:资金代管协议
			map.put("protocolAmount", protocol.getProtocolAmount());//协议金额（元）
			map.put("fileName" 		, protocol.getFileName());//协议附件名称
			map.put("fileUrl" 		, protocol.getFileUrl());//附件URL
			
			
			//赋予默认的已申请金额与剩余金额
			map.put("protocolPayed", 0);
			//减去资金代管的协议金额
			map.put("protocolPurplus", protocol.getProtocolAmount().subtract(protocol.getProtocolEscrow()));
			map.put("batch", 0);
			//标记当前协议是否已经存在兑付申请
			boolean flag = false;
			//若当前协议有做过兑付申请，则修改已申请金额与剩余金额
			for (PaymentInfoEntity paymentInfo : paymentInfoList) {
				if(paymentInfo.getProtocolCode().equals(protocol.getProtocolCode()) && paymentInfo.getProtocolType().equals(protocol.getProtocolType())){
					//插入检索出的已申请金额与剩余金额
					map.put("protocolPayed", paymentInfo.getProtocolPayed());
					map.put("protocolPurplus", paymentInfo.getProtocolSurplus());
					map.put("batch", paymentInfo.getBatch() == null ? 0 : paymentInfo.getBatch());
					flag = true;
				}
			}
			
			if("add".equals(type) && flag){//新增兑付申请时不返回已经做过申请协议
				
			}else if(!("".equals(CommonUtil.trim(protocolCode)))){//根据协议编号查询仅返回当前协议
				if(protocolCode.equals(protocol.getProtocolCode()) && protocolType == protocol.getProtocolType())protocolMapList.add(map);
			}else{				
				protocolMapList.add(map);
			}
		}

		return protocolMapList;
	}
	
	public PaymentInfoVO getPaymentInfo(String ownerNm, String protocolCode, Integer protocolType, String type, SysDept loginDept) {
		
		String protocolTyoe = "";//权限控制，施工单位展示资金代管协议，政府机构展示全部，其余展示补偿协议
		PubDictValue deptTypeDict = pubDictValueDao.findByNm(loginDept.getDeptType());

		if("施工单位".equals(deptTypeDict.getName())){//施工单位
			protocolTyoe = "1";
		}else if("政府机构".equals(deptTypeDict.getName())){//政府机构
			protocolTyoe = "0,1";
		}else{
			protocolTyoe = "0";
		}
		
		PaymentInfoVO paymentInfo = new PaymentInfoVO();
		if("".equals(CommonUtil.trim(ownerNm)))return paymentInfo;
		//获取权属人信息
		OwnerInfoVO ownerInfo = paymentInfoDao.ownerInfo(ownerNm);
		//获取权属人银行卡信息
		List<BankCardVO> bankCard = paymentInfoDao.queryBankCardByOwnerNm(ownerNm);
		
		List<Map<String, Object>> signedProtocol = querySignedProtocol(ownerNm,protocolCode,protocolType,type,protocolTyoe);
		
		paymentInfo.setOwner(ownerInfo);
		paymentInfo.setBankCard(bankCard);
		paymentInfo.setProtocol(signedProtocol);
		return paymentInfo;
	}
	
	/**
	 * 获取字典表中的支付方式列表
	 * @return 
	 */
	public List<PaymentMethodVO> getPaymentMethodDict() {
		return paymentInfoDao.getPaymentMethodDict();
	}
	
	public List<InfoListVO> infoList(InfoListPojo infoListPojo) {
		List<InfoListVO> infoList = paymentInfoDao.infoList(infoListPojo.getRegion(),infoListPojo.getOwnername(),infoListPojo.getOwnername());
		return infoList;
	}
	
}
