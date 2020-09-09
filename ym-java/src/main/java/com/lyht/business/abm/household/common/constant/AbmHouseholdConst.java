package com.lyht.business.abm.household.common.constant;

import com.lyht.base.common.constant.LyhtResultConst;

public class AbmHouseholdConst {
	public static final String HOUSEHOLD_KEY = LyhtResultConst.APP_NAME + "_abm_household_";// 分户redis key
	public static final Long HOUSEHOLD_KEY_EXPIRE_TIME = 30000000000000L;// 分户redis key 过期时间

	public static final String MASTER_RELATIONSHIP_OWNER = "E2A29C1823";// 与户主关系字典NM（户主）

	/*--------------------------------分户指标数据类型-------------------------------------*/
	public static final String SPLIT_HOUSEHOLD_DATA_TYPE_FAMILY = "family";// 家庭成员
	public static final String SPLIT_HOUSEHOLD_DATA_TYPE_LAND = "land";// 土地
	public static final String SPLIT_HOUSEHOLD_DATA_TYPE_HOUSE = "house";// 房屋
	public static final String SPLIT_HOUSEHOLD_DATA_TYPE_TREES = "trees";// 零星树木
	public static final String SPLIT_HOUSEHOLD_DATA_TYPE_BUILDING = "building";// 附属建筑物
	public static final String SPLIT_HOUSEHOLD_DATA_TYPE_DECORATION = "decoration";// 房屋装修

	// 分户状态
	public static final String SPLIT_STATE_NORMAL = "0";// 正常，可分户
	public static final String SPLIT_STATE_APPLY_DOING = "1";// 分户申请流程处理中
	public static final String SPLIT_STATE_APPLY_APPROVED = "2";// 分户申请流程通过
	public static final String SPLIT_STATE_DOING = "3";// ：分户流程处理中
	public static final String SPLIT_STATE_STORAGE = "4";// 分户流程暂存
	public static final String SPLIT_STATE_HAVE_LEDGER = "5";// 已分户，可提交分户申请

	// 分户关键字（前缀）
	public static final String SPLIT_PREFIX = "SPLIT_";

	// 分户表名
	public static final String SPLIT_TABLE_NAME = "t_abm_split_household";

}
