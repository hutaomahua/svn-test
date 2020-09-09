package com.lyht.business.process.common.constant;

/**
 * 
 * @author hxl
 *
 */
public class ProcessConstant {
	//步骤状态[Standby:处理中,Approved:通过,Rejected:拒绝,PickBack:退回,Ignore:忽略,Deleted:删除] 
	
	public static final String PROCESS_APPROVED = "Approved";//审批(同意)
	public static final String PROCESS_REJECTED = "Rejected";//审批(拒绝)
	public static final String PROCESS_PICKBACK = "PickBack";//审批(退回)
	
	public static final String PROCESS_STANDBY = "Standby";//流程处理中
	public static final String PROCESS_IGNORE = "Ignore";//流程已忽略
	public static final String PROCESS_DELETED = "Deleted";//流程已删除
	public static final String PROCESS_DELETED_CN = "已取消";//流程已删除

}
