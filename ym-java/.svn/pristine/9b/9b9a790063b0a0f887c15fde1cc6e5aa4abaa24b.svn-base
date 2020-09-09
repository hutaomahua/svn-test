package com.lyht.business.abm.plan.controlle;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lyht.business.abm.plan.service.PublicityService;
import com.lyht.business.abm.plan.service.TimerService;

@Component
public class TimerController {

	@Autowired
	TimerService timerService;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	// 每天凌晨1点执行一次
	@Scheduled(cron = "0 0 1 * * ?")
	public void updateState() {
		System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
		timerService.updateState();

	}
}
