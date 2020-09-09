package com.lyht.business.info.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyht.business.info.dao.InfoStatisticsDao;
import com.lyht.business.info.entity.InfoStatistics;
import com.lyht.business.info.vo.InfoStatisticsDetailVO;
//建设征地实物指标汇总表 定时器 更新
@Service
public class InfoStatisticsService {

	@Autowired
	private InfoStatisticsDao dao;

	@Transactional
	//@Scheduled(fixedDelay = 10 * 60 * 1000)
	public void statistics() {
		List<InfoStatisticsDetailVO> statisticsList = new ArrayList<InfoStatisticsDetailVO>();
		statisticsList.add(dao.line01());//县
		statisticsList.add(dao.line02());//乡
		statisticsList.add(dao.line03());//村
		statisticsList.add(dao.line04());//组
		for (InfoStatisticsDetailVO infoStatisticsDetailVO : dao.land01()) {//土地一级
			statisticsList.add(infoStatisticsDetailVO);
		}
		for (InfoStatisticsDetailVO infoStatisticsDetailVO : dao.land02()) {//土地二级
			statisticsList.add(infoStatisticsDetailVO);
		}
		statisticsList.add(dao.houseArea());//房屋面积总
		for (InfoStatisticsDetailVO infoStatisticsDetailVO : dao.houseAreaProject()) {
			statisticsList.add(infoStatisticsDetailVO);
		}
		statisticsList.add(dao.population01());//人口与户数
		statisticsList.add(dao.population02());//移民户
		statisticsList.add(dao.population03());//移民人口
		statisticsList.add(dao.population04());//农业人口
		statisticsList.add(dao.population05());//非农业人口
		statisticsList.add(dao.building00());//附属建筑物
		for (InfoStatisticsDetailVO infoStatisticsDetailVO : dao.building01()) {//附属建筑物 分项
			statisticsList.add(infoStatisticsDetailVO);
		}
		statisticsList.add(dao.trees00());//零星树木总数 
		for (InfoStatisticsDetailVO infoStatisticsDetailVO : dao.trees01()) {//零星树木 分项
			statisticsList.add(infoStatisticsDetailVO);
		}
		for (InfoStatisticsDetailVO infoStatisticsDetailVO : dao.transport()) {//交通运输工程
			statisticsList.add(infoStatisticsDetailVO);
		}
		statisticsList.add(dao.enter());//企业和个体工商户
		statisticsList.add(dao.enterprise());//企业
		statisticsList.add(dao.individual());//个体工商户总数
		statisticsList.add(dao.individual01());// 生产型个体工商户
		statisticsList.add(dao.individual02());// 经营型个体工商户
		List<InfoStatistics> list = new ArrayList<InfoStatistics>();
		for (InfoStatisticsDetailVO infoStatistics : statisticsList) {
			InfoStatistics info = new InfoStatistics();
			info.setA(infoStatistics.getA());
			info.setB(infoStatistics.getB());
			info.setC(infoStatistics.getC());
			info.setD(infoStatistics.getD()); 
			info.setE(infoStatistics.getE());
			info.setF(infoStatistics.getF());
			info.setG(infoStatistics.getG());
			info.setH(infoStatistics.getH());
			info.setI(infoStatistics.getI());
			info.setJ(infoStatistics.getJ());
			info.setK(infoStatistics.getK());
			list.add(info);
		}
		dao.deleteStatistics();
		dao.saveAll(list);
	}

}
