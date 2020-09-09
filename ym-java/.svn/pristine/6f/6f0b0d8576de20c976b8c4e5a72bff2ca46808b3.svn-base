package com.lyht.business.abm.plan.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyht.Constants;
import com.lyht.business.abm.plan.dao.PublicityDao;
import com.lyht.business.abm.plan.dao.PublicityDetailDao;
import com.lyht.business.abm.plan.entity.PublicityDetailEntity;
import com.lyht.business.abm.plan.entity.PublicityEntity;
import com.lyht.business.abm.removal.dao.AbmFamilyDao;
import com.lyht.util.DateUtil;

@Service
public class TimerService {

	@Autowired
	PublicityDao publicityDao;

	@Autowired
	PublicityDetailDao dao;

	@Autowired
	AbmFamilyDao abmFamilyDao;

	@Transactional
	public void updateState() {
		List<PublicityEntity> list = publicityDao.findByState("1");// 找到 所有公示中 的公示批次
		String date = DateUtil.getDate();// 当前时间
		if (list.size() > 0) {
			for (PublicityEntity publicityEntity : list) {
				if (publicityEntity.getZbType() == "1" || publicityEntity.getZbType().equals("1")) {//实物指标公示  以权属人为单位
					List<PublicityDetailEntity> details = dao.findByNm(publicityEntity.getNm());// 找到该公式下 绑定的所有权属人
					if (StringUtils.isNotBlank(publicityEntity.getEndTime())
							&& publicityEntity.getEndTime() != "end_time") {// 判断 结束时间不为空 不为end_time
						if (date.equals(DateUtil.getDate())) {// 如果时间等于当前时间
							publicityDao.updateState("2", publicityEntity.getNm());// 更新t_abm_publicity表
																					// 根据nm修改当前公式状态为完成状态
							for (PublicityDetailEntity publicityDetailEntity : details) {// 循环修改 所有权属人状态
								abmFamilyDao.updateAbmFamilys(2, publicityDetailEntity.getOwnerNm());
							}
						}
					}
				}else {//移民人口公式 以家庭成员为单位
					List<PublicityDetailEntity> details = dao.findByNm(publicityEntity.getNm());// 找到该公式下 绑定的所有家庭成员
					if (StringUtils.isNotBlank(publicityEntity.getEndTime())
							&& publicityEntity.getEndTime() != "end_time") {// 判断 结束时间不为空 不为end_time
						if (date.equals(DateUtil.getDate())) {// 如果时间等于当前时间
							publicityDao.updateState("2", publicityEntity.getNm());// 更新t_abm_publicity表
																					// 根据nm修改当前公式状态为完成状态
							for (PublicityDetailEntity publicityDetailEntity : details) {// 循环修改 所有家庭成员的公示状态
								abmFamilyDao.updateGsState(2, publicityDetailEntity.getOwnerNm());
							}
						}
					}
				}
			}
		}
	}

}
