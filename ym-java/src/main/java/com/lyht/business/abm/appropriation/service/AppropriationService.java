package com.lyht.business.abm.appropriation.service;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.appropriation.dao.AppropriationDao;
import com.lyht.business.abm.appropriation.entity.Appropriation;
import com.lyht.business.abm.appropriation.vo.AppropriationVO;
import com.lyht.business.abm.protocol.dao.BankCradInfoDao;
import com.lyht.business.abm.protocol.entity.BankCradInfo;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

@Service
public class AppropriationService {

	@Autowired
	private AppropriationDao dao;

	@Autowired
	private BankCradInfoDao cardDao;

	public LyhtResultBody<List<AppropriationVO>> page(AppropriationVO appropriationVO, LyhtPageVO lyhtPageVO) {
		Pageable pageable = PageRequest.of(lyhtPageVO.getCurrent() - 1, lyhtPageVO.getPageSize());
		Page<AppropriationVO> page = dao.page(appropriationVO.getProtocolNo(), appropriationVO.getStartTime(),
				appropriationVO.getEndTime(), appropriationVO.getProtocolName(), pageable);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(page.getContent(), pageVO);
	}

	/**
	 * 单个删除
	 *
	 * @param id
	 * @return
	 */
	public LyhtResultBody<Integer> delete(Integer id) {
		// 参数校验
		if (id == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		dao.deleteById(id);
		return new LyhtResultBody<>(id);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	public LyhtResultBody<String> batchDel(String ids) {
		if (StringUtils.isBlank(ids)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		List<Integer> idList = null;
		try {
			idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		List<Appropriation> deleteFundCost = dao.findAllById(idList);
		dao.deleteInBatch(deleteFundCost);
		return new LyhtResultBody<>(ids);
	}

	/**
	 * 查询单个
	 */
	public Appropriation findOneById(Integer id) {
		return dao.getOne(id);
	}

	/**
	 * 添加 修改
	 * 
	 * @param t_fund_cost
	 * @return
	 */
	public LyhtResultBody<Appropriation> save(Appropriation appropriation, BankCradInfo bankCradInfo) {
		// 参数校验
		if (appropriation == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}

		if (StringUtils.isBlank(appropriation.getBankCard())) {
			bankCradInfo.setNm(null);
			bankCradInfo.setId(null);
			String cardNm = cardDao.findByBankNumberAndBank(bankCradInfo.getBankNumber(), bankCradInfo.getBankNm());
			if (StringUtils.isNotBlank(cardNm)) {// 查询该卡号是否存在银行卡信息
				appropriation.setBankCard(cardNm);//
			} else {
				BankCradInfo cardInfo = cardDao.save(bankCradInfo);
				appropriation.setBankCard(cardInfo.getNm());
			}
		}
		// 内码赋值
		String nm = appropriation.getNm();
		if (StringUtils.isBlank(nm)) {// 新增 兑付编号 生成(规则：X_YYYYMM_0000) 生成规则 X_当前年当前月_生成四位顺序编号
			appropriation.setNm(Randomizer.generCode(10));
			Calendar date = Calendar.getInstance();
			String year = String.valueOf(date.get(Calendar.YEAR));// 获取当前年
			String month = String.valueOf(date.get(Calendar.MONTH));// 获取当前月
			String appNo = dao.getLargeAppNo();// 拿到当前最大编号
			String appliacationNo = "";
			if (StringUtils.isNotBlank(appNo)) {// 数据库能查到值时
				// 取到当前数据中最大顺序号 +1为当前所需顺序号
				appliacationNo = String.format("%04d",
						Integer.parseInt(appNo.substring(appNo.length() - 4, appNo.length())) + 1);
			} else {// 数据库查不到值时 后四位 默认为0001
				appliacationNo = String.format("%04d", 1);
			}
			appropriation.setApplicationNo("X_" + year + month + "_" + appliacationNo);
		}
		Appropriation result = dao.save(appropriation);
		return new LyhtResultBody<>(result);
	}

}
