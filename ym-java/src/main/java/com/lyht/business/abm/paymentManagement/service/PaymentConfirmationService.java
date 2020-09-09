package com.lyht.business.abm.paymentManagement.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.paymentManagement.dao.PaymentConfirmationDao;
import com.lyht.business.abm.paymentManagement.dao.PaymentDetailDao;
import com.lyht.business.abm.paymentManagement.dao.PaymentInfoDao;
import com.lyht.business.abm.paymentManagement.dao.VPaymentConfirmationDao;
import com.lyht.business.abm.paymentManagement.entity.PaymentConfirmationBatch;
import com.lyht.business.abm.paymentManagement.entity.PaymentDetailEntity;
import com.lyht.business.abm.paymentManagement.entity.PaymentInfoEntity;
import com.lyht.business.abm.paymentManagement.pojo.PaymentConfirmPojo;
import com.lyht.business.abm.paymentManagement.pojo.PaymentConfirmationBatchPojo;
import com.lyht.business.abm.paymentManagement.to.Msg;
import com.lyht.business.abm.paymentManagement.vo.ApplyAuditVO;
import com.lyht.business.abm.paymentManagement.vo.ApplyBatchRecordVO;
import com.lyht.business.abm.paymentManagement.vo.BatchInfoVO;
import com.lyht.business.abm.paymentManagement.vo.PaymentConfirmationBatchVO;
import com.lyht.business.abm.paymentManagement.vo.PaymentConfirmationVO;
import com.lyht.business.abm.paymentManagement.vo.PubFilesVO;
import com.lyht.business.pub.dao.PubFilesMergeDao;
import com.lyht.business.pub.entity.PubFilesEntity;
import com.lyht.business.pub.entity.PubFilesMergeEntity;
import com.lyht.business.pub.service.PubFilesService;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;
import com.lyht.util.SystemUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class PaymentConfirmationService {
	
	@Autowired
	private PaymentConfirmationDao dao;

	@Autowired
	private VPaymentConfirmationDao paymentConfirmationDao;

	@Autowired
	private PaymentAuditService paymentAuditService;

	@Autowired
	private PaymentInfoDao paymentInfoDao;
	
	@Autowired
	private PaymentDetailDao paymentDetailDao;

	@Autowired
	private PubFilesService pubFilesService;

	@Autowired
	private PubFilesMergeDao pubFilesMergeDao;
	
	public List<PaymentConfirmationVO> confirmationList(String region){
		return paymentConfirmationDao.paymentConfirmationList(region, null,true);
	}
	
	/**
	 * 形成兑付确认批次记录
	 * @param nmList
	 * @param request 
	 * @return
	 */
	public Msg<String> confirm(PaymentConfirmPojo pojo, HttpServletRequest request) {
		List<String> nmList = pojo.getData();
		//没有记录传入不做处理
		if(nmList == null || nmList.size() == 0)return new Msg<>(false,"请指定需要进行兑付确认的记录");
		/*校验数据*/
		List<String> errorNms = new ArrayList<String>();
		List<PaymentDetailEntity> detailList = paymentDetailDao.findByNms(nmList);
		for (PaymentDetailEntity entity : detailList) {
			if(nmList.indexOf(entity.getNm()) > -1){
				nmList.remove(entity.getNm());
				
				if(!"".equals(CommonUtil.trim(entity.getConfirmationBatch()))){
					errorNms.add(entity.getNm());
				}
			}
		}
		if(nmList.size() > 0)return new Msg<>(false,"查寻找不到这些记录信息："+StringUtils.join(nmList.toArray(), ","));
		if(errorNms.size() > 0)return new Msg<>(false,"以下记录已发起过兑付确认："+StringUtils.join(errorNms.toArray(), ","));
		/*校验数据*/
		
		/*处理数据*/
		//批次记录内码
		String NM = Randomizer.generCode(10);
		for (PaymentDetailEntity entity : detailList) {
			entity.setConfirmationBatch(NM);
		}
		PaymentConfirmationBatch batch = new PaymentConfirmationBatch();
		batch.setNm(NM);
		batch.setBatchNum("");
		batch.setRegion(pojo.getRegion());
		batch.setProposer("".equals(pojo.getPorposer()) ? SystemUtil.getLoginStaffName(request) : pojo.getPorposer());
		batch.setConfirmTime("".equals(pojo.getConfirmTime()) ? DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss") : pojo.getConfirmTime());
		batch.setRemark(pojo.getRemark());
		/*处理数据*/ 
		
		/*存储数据*/
		dao.save(batch);
		paymentDetailDao.saveAll(detailList);
		/*存储数据*/
		return new Msg<>(true,"成功");
	}
	
	/**
	 * 兑付确认批次列表分页 带条件查询
	 * @param pojo
	 * @param lyhtPageVO 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public Page<PaymentConfirmationBatchVO> infoPage(PaymentConfirmationBatchPojo pojo, LyhtPageVO lyhtPageVO) {
		Pageable pageable = lyhtPageVO.getPageable(lyhtPageVO);
		Page<PaymentConfirmationBatchVO> page = dao.infoPage(pojo.getRegion(),pojo.getBatchnum(),pojo.getOwnername(),pojo.getIdcard(),pojo.getProtocolname(),
				pojo.getSt(),pojo.getEt(),pageable);
		return page;
	}
	
	/**
	 * 兑付确认批次详情
	 * @param nm 
	 * @return
	 */
	public BatchInfoVO batchInfo(String nm) {
		BatchInfoVO infoVO = new BatchInfoVO();
		PaymentConfirmationBatch confirmationBatch = dao.getByNm(nm);
		List<PaymentConfirmationVO> list = paymentConfirmationDao.paymentConfirmationList(null, nm,false);
		infoVO.setInfo(confirmationBatch);
		infoVO.setList(list);
		return infoVO;
	}
	
	/**
	 * 根据流程实例ID获取兑付申请信息
	 * @param processId
	 * @return
	 */
	public BatchInfoVO getByProcessId(String processId) {
		BatchInfoVO infoVO = new BatchInfoVO();
		PaymentConfirmationBatch confirmationBatch = dao.getByProcessId(processId);
		List<PaymentConfirmationVO> list = paymentConfirmationDao.paymentConfirmationList(null, confirmationBatch.getNm(),false);
		infoVO.setInfo(confirmationBatch);
		infoVO.setList(list);
		return infoVO;
	}
	
	/**
	 * 兑付支付清单
	 * @param nms
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> paymentDetailList(String nms) {
		List<String> nmlist = Arrays.asList(nms.split(","));
		List<Map> list = paymentConfirmationDao.paymentDetailList(nmlist);
		return list;
	}

	public LyhtResultBody<List<PubFilesEntity>> uploads(HttpServletRequest request, MultipartFile[] files, List<String> ownerNm) {
		List<PubFilesEntity> list = new ArrayList<>();
		for (MultipartFile file : files) {
			PubFilesEntity pubFileEntity = new PubFilesEntity();
			String code = Randomizer.generCode(10);
			List<PubFilesMergeEntity> entityList = new ArrayList<>();
			for (String nm : ownerNm) {
				PubFilesMergeEntity entity = new PubFilesMergeEntity();
				entity.setFileNm(code);
				entity.setTableName("t_abm_payment_detail");
				entity.setTablePkColumn(nm);
				entityList.add(entity);
			}
			pubFilesMergeDao.saveAll(entityList);
			pubFileEntity.setTableName("pub_files_merge");
			pubFileEntity.setTablePkColumn(code);
			list.add(pubFilesService.upload(request, file, pubFileEntity));
		}
		return new LyhtResultBody<>(list);
	}
	
	/**
	 * 根据兑付批次内码查询与之关联的兑付申请记录附件
	 * @param data
	 * @return
	 */
	public List<PubFilesVO> findFileDetailsByConfirmationBatchNms(List<String> data) {
		if(data == null || data.size() == 0)return new ArrayList<>();
		List<PubFilesVO> fileList = pubFilesMergeDao.findFileDetailsByConfirmationBatchNms(data);
		return fileList;
	}

	/**
	 * 根据兑付申请记录内码查询所关联附件
	 * @param data
	 * @return
	 */
	public List<PubFilesVO> findFileDetailsByApplyNms(List<String> data) {
		if(data == null || data.size() == 0)return new ArrayList<>();
		List<PubFilesVO> fileList = pubFilesMergeDao.findFileDetailsByApplyNms(data);
		return fileList;
	}
	
	/**
	 * 根据附件内码查询所关联的兑付申请记录
	 * @param data
	 * @return 
	 * @return
	 */
	public List<ApplyBatchRecordVO> findApplyDetailsByFileNms(List<String> data) {
		if(data == null || data.size() == 0)return new ArrayList<>();
		List<ApplyBatchRecordVO> applyDetailList = pubFilesMergeDao.findApplyDetailsByFileNms(data);
		return applyDetailList;
	}

	/**
	 * 根据附件关联码删除附件并解除关联
	 * @param request
	 * @param fileNm
	 * @return
	 */
	public Msg<String> delFile(String fileTablePkColumn) {
		if("".equals(CommonUtil.trim(fileTablePkColumn)))return new Msg<>(false,"附件内码为空！");
		Integer deleteFileNum = pubFilesService.deleteBytablePkColumn(fileTablePkColumn);
		Integer deleteMergeNum = pubFilesMergeDao.deleteByFileNm(fileTablePkColumn);
		return new Msg<>("成功删除附件："+deleteFileNum+";解除关联："+deleteMergeNum);
	}

	/**
	 * 根据附件关联码与业务关联码解除附件关联，若附件的关联全部解除，则删除该附件
	 * @param fileTablePkColumn
	 * @param mergeTablePkColumn
	 * @param request 
	 * @return
	 */
	public Msg<String> delMerge(String fileTablePkColumn, String mergeTablePkColumn, HttpServletRequest request) {
		if("".equals(CommonUtil.trim(fileTablePkColumn)) || "".equals(CommonUtil.trim(mergeTablePkColumn)))return new Msg<>(false,"附件关联码或者业务关联码为空！");
		pubFilesMergeDao.delMerge(fileTablePkColumn, mergeTablePkColumn);
		List<PubFilesMergeEntity> pubFilesMergeList = pubFilesMergeDao.findByTableNameAndTablePkColumn("t_abm_payment_detail",fileTablePkColumn);
		if(pubFilesMergeList.size() == 0)pubFilesService.deleteBytablePkColumn(request, fileTablePkColumn);
		return new Msg<>("成功");
	}

	/**
	 * 资金兑付确认审批
	 * @param nm
	 * @param request
	 * @return
	 */
	public Msg<String> audit(String nm,HttpServletRequest request) {

		//中南院流程系统BUG 先暂缓流程操作 
		PaymentConfirmationBatch entity = dao.getByNm(nm);
		 
		if("".equals(CommonUtil.trim(entity)))return new Msg<>(false,"不明确的确认批次！");
		
		String processId = paymentAuditService.paymentConfirmation(entity, request);
		String date = DateFormatUtils.format(Calendar.getInstance(), "yyyyMMdd");
		String batchNum = "df"+date+processId;
		entity.setProcessId(processId);
		entity.setBatchNum(batchNum);
		dao.save(entity);
		
		return new Msg<>(true,"成功");
	}


	@SuppressWarnings("rawtypes")
	public Msg paymentFinish(String processId, String state) {
		if("".equals(CommonUtil.trim(processId)))return new Msg(false,"流程ID为空");
		if(!("Approved".equals(state)))return new Msg(false,"未知的审核状态");
		
		PaymentConfirmationBatch confirmationBatch = dao.getByProcessId(processId);
		if(confirmationBatch == null)return new Msg(false,"查询不到该记录");
		
		if(!"Approved".equals(state)){//审核不通过
		}else if("Approved".equals(state)){//审核通过
			paymentFinish(confirmationBatch.getNm());
		}
		return new Msg(true,"操作成功");
	}
	/**
	 * 兑付确认数据同步
	 * @param confirmationBatch
	 * @return
	 */
	public Msg<String> paymentFinish(String confirmationBatch){
		List<PaymentDetailEntity> list = paymentDetailDao.findByConfirmationBatch(confirmationBatch);
		
		StringBuilder errMsg = new StringBuilder("");
		Boolean flag = true;
		for (PaymentDetailEntity entity : list) {
			PaymentInfoEntity paymentInfo = paymentInfoDao.findByCodeAndType(entity.getProtocolCode(), entity.getProtocolType());
			
			BigDecimal payed 		= paymentInfo.getProtocolRealPayed()== null ? new BigDecimal(0) : paymentInfo.getProtocolRealPayed();	//已兑付金额
			BigDecimal apply 		= entity.getApplyAmount() 			== null ? new BigDecimal(0) : entity.getApplyAmount();				//单记录申请金额
			BigDecimal applyAmount 	= paymentInfo.getProtocolPayed() 	== null ? new BigDecimal(0) : paymentInfo.getProtocolPayed();		//协议申请金额
			
			if(payed.add(apply).compareTo(applyAmount) < 1){//单记录申请金额 + 已兑付金额 <= 协议申请金额
				paymentInfo.setProtocolRealPayed(payed.add(apply));
				paymentInfoDao.save(paymentInfo);
			}else{
				flag = false;
				errMsg.append(paymentInfo.getProtocolCode()+"  第"+entity.getApplyBatch()+"次兑付申请  "+"申请兑付金额错误！;");
			}
		}
		String msg = errMsg.length() == 0 ? "成功！" : errMsg.toString();
		return new Msg<>(flag,msg);
	}

	public void delete(String nm) {
		//删除附件
		List<String> fileMergeTablePKColumnList = pubFilesMergeDao.findFileMergeTablePKColumnByConfirmationBatch(nm);
		for (String fileMergeTablePKColumn : fileMergeTablePKColumnList) {
			delFile(fileMergeTablePKColumn);
		}
		//删除兑付批次记录
		dao.deleteByNm(nm);
		//清除兑付批次关联
		paymentDetailDao.updateConfirmationBatchAsNull(nm);
	}
	
}
