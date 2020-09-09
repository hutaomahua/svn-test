package com.lyht.business.process.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lyht.business.process.service.ProcessRoleSyncService;
import com.lyht.business.process.service.ProcessRoleUserSyncService;
import com.lyht.business.process.service.ProcessUserSyncService;

/**
 * 
 * @author hxl（xg）
 *
 */
@Component
public class ProcessScheduledConfig {
	private Logger log = LoggerFactory.getLogger(ProcessScheduledConfig.class);
	
	@Autowired
	private ProcessRoleSyncService processRoleSyncService;
	@Autowired
	private ProcessUserSyncService processUserSyncService;
	@Autowired
	private ProcessRoleUserSyncService processRoleUserSyncService;
	
	/**
	 * 用户同步
	 */
	@Scheduled(cron = "0 0 3 * * ? ")
	public void userSync() {
		boolean userSync = false;
		try {
			userSync = processUserSyncService.userSync();
			log.info("=====ProcessScheduledConfig=====Method:userSync=====定时任务=====状态：" + userSync + "=====");
		} catch (Exception e) {
			log.error("=====ProcessScheduledConfig=====Method:userSync=====定时任务=====状态：" + userSync + "=====", e);
		}
	}
	
	/**
	 * 岗位同步
	 */
	@Scheduled(cron = "25 0 3 * * ? ")
	public void roleSync() {
		boolean roleSync = false;
		try {   
			roleSync = processRoleSyncService.roleSync();
			log.info("=====ProcessScheduledConfig=====Method:roleSync=====定时任务=====状态：" + roleSync + "=====");
		} catch (Exception e) {
			log.error("=====ProcessScheduledConfig=====Method:roleSync=====定时任务=====状态：" + roleSync + "=====", e);
		}
	}
	
	/**
	 * 岗位与用户关联关系同步
	 */
	@Scheduled(cron = "50 0 3 * * ? ")
	public void roleUserSync() {
		boolean roleUserSync = false;
		try {
			roleUserSync = processRoleUserSyncService.roleUserSync();
			log.info("=====ProcessScheduledConfig=====Method:roleUserSync=====定时任务=====状态：" + roleUserSync + "=====");
		} catch (Exception e) {
			log.error("=====ProcessScheduledConfig=====Method:roleUserSync=====定时任务=====状态：" + roleUserSync + "=====", e);
		}
	}

}
