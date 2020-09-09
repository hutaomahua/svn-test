package com.lyht.business.tab.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.tab.common.constant.TableConst;
import com.lyht.business.tab.common.enums.TabExceptionEnums;
import com.lyht.business.tab.dao.ConfigTableDao;
import com.lyht.business.tab.dao.ConfigTableFieldDao;
import com.lyht.business.tab.entity.ConfigSearchEntity;
import com.lyht.business.tab.entity.ConfigTableEntity;
import com.lyht.business.tab.entity.ConfigTableFieldEntity;
import com.lyht.util.DateUtil;
import com.lyht.util.Randomizer;
import com.lyht.util.SystemUtil;

@Service
public class ConfigTableOperateService {
	
	@Autowired
	private ConfigTableDao configTableDao;
	@Autowired
	private ConfigTableFieldDao configTableFieldDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 新增
	 * @param parameterMap
	 * @param configTableId
	 * @param request
	 * @return
	 */
	public Map<String, Object> insert(Map<String, Object> parameterMap, HttpServletRequest request) {
		if (parameterMap == null || parameterMap.isEmpty()) {
			throw new LyhtRuntimeException(TabExceptionEnums.PARAM_IS_NOT_NULL);
		}
		Integer configTableId = null;
		Object object = parameterMap.get("configTableId");
		if (object == null) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}
		configTableId = Integer.parseInt(String.valueOf(object));
		Optional<ConfigTableEntity> findById = configTableDao.findById(configTableId);
		if (!findById.isPresent()) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}
		ConfigTableEntity configTableEntity = findById.get();//表
		String configTableName = configTableEntity.getConfigTableName();
		
		//insert语句
		StringBuffer sqlBuffer = new StringBuffer("INSERT INTO " + configTableName + " SET ");
		//内码
		sqlBuffer.append("nm = '");
		sqlBuffer.append(Randomizer.generCode(10));
		sqlBuffer.append("',");
		//项目编码
		Object projectNm = parameterMap.get("project_nm");
		if (projectNm != null) {
			sqlBuffer.append("project_nm = '");
			sqlBuffer.append(projectNm);
			sqlBuffer.append("',");
		}
		//行政区域
		Object region = parameterMap.get("region");
		if (region != null) {
			sqlBuffer.append("region = '");
			sqlBuffer.append(region);
			sqlBuffer.append("',");
		}
		//指标类型
//		Object zblx = parameterMap.get("zblx");
//		if (zblx != null) {
//			sqlBuffer.append("zblx = '");
//			sqlBuffer.append(zblx);
//			sqlBuffer.append("',");
//		}
		//创建时间
		sqlBuffer.append("create_time = ");
		String dateTime = DateUtil.getDateTime();
		sqlBuffer.append("STR_TO_DATE('");
		sqlBuffer.append(dateTime);
		sqlBuffer.append("','%Y-%m-%d %H:%i:%s'),");
		//创建人
		String loginStaffNm = SystemUtil.getLoginStaffNm(request);
		if (StringUtils.isNotBlank(loginStaffNm)) {
			sqlBuffer.append("create_user = '");
			sqlBuffer.append(loginStaffNm);
			sqlBuffer.append("',");
		}
		//阶段
		Object stage = parameterMap.get("stage");
		if (stage != null) {
			sqlBuffer.append("stage = '");
			sqlBuffer.append(stage);
			sqlBuffer.append("',");
		}
		//备注
		Object remark = parameterMap.get("remark");
		if (remark != null) {
			sqlBuffer.append("remark = '");
			sqlBuffer.append(remark);
			sqlBuffer.append("',");
		}
		
		List<ConfigTableFieldEntity> findByConfigTableId = configTableFieldDao.findByConfigTableId(configTableId);//字段
		if (findByConfigTableId != null && !findByConfigTableId.isEmpty()) {
			for(ConfigTableFieldEntity configTableFieldEntity :findByConfigTableId) {
				String configFieldName = configTableFieldEntity.getConfigFieldName();//字段名称
				String configFieldDisplayName = configTableFieldEntity.getConfigFieldDisplayName();//字段显示名称
				Integer configFieldLength = configTableFieldEntity.getConfigFieldLength();//字段总长度
				Integer configFieldDouble = configTableFieldEntity.getConfigFieldDouble();//小数位数
				String configFieldType = configTableFieldEntity.getConfigFieldType();//字段数据库类型
				Integer configFieldIsempty = configTableFieldEntity.getConfigFieldIsempty();//是否为空
				Integer configFieldEdit = configTableFieldEntity.getConfigFieldEdit();//是否可编辑
				Object value = parameterMap.get(configFieldName);
				if (configFieldIsempty != null && configFieldIsempty == 0 && value == null) {//字段非空
					throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", "字段不允许为空"));
				}
				if (value == null || configFieldEdit == null || configFieldEdit == 0) {//如果该字段参数为空，则不处理
					continue;
				}
				String string = String.valueOf(value);
				if (StringUtils.isBlank(string)) {
					continue;
				}
				if (StringUtils.equals(configFieldName, "stage") && stage != null) {//如果是阶段字段，则不再处理
					continue;
				}
				if (configFieldLength != null && string.length() > configFieldLength) {//字段值超长
					throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，长度不能超过" + configFieldLength));
				}
				if (StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_VARCHAR)) {
					sqlBuffer.append(configFieldName);
					sqlBuffer.append(" = '");
					sqlBuffer.append(string);
					sqlBuffer.append("',");
				}else if(StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_INT)) {
					if (string.length() > 11) {
						throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，整型长度不可超过11位"));
					}
					sqlBuffer.append(configFieldName);
					sqlBuffer.append(" = ");
					sqlBuffer.append(string);
					sqlBuffer.append(",");
				} else if(StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_DECIMAL)) {
					String[] split = string.split("\\.");
					int left = configFieldLength;
					int right = 0;
					if (configFieldDouble != null) {
						left = left - configFieldDouble;;
						right = configFieldDouble;
					}
					if(split[0].length() > left) {
						throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，整数位数不能超过" + left + ",小数位数不能超过" + configFieldDouble));
					}
					if (split.length > 1 && split[1].length() > right) {
						throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，整数位数不能超过" + left + ",小数位数不能超过" + configFieldDouble));
					}
					sqlBuffer.append(configFieldName);
					sqlBuffer.append(" = ");
					sqlBuffer.append(string);
					sqlBuffer.append(",");
				} else if(StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_DATE)) {
					Date strToDate = DateUtil.strToDate(string, "yyyy-MM-dd");
					if (strToDate == null) {
						throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，日期格式不正确(yyyy-MM-dd)"));
					}
					if (string.length() > 10) {
						string = string.substring(0, 10);
					}
					sqlBuffer.append(configFieldName);
					sqlBuffer.append(" = ");
					sqlBuffer.append("STR_TO_DATE('");
					sqlBuffer.append(string);
					sqlBuffer.append("','%Y-%m-%d'),");
				} else if(StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_DATETIME)) {
					Date strToDate = DateUtil.strToDate(string, "yyyy-MM-dd HH:mm:ss");
					if (strToDate == null) {
						throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，日期格式不正确(yyyy-MM-dd HH:mm:ss)"));
					}
					sqlBuffer.append(configFieldName);
					sqlBuffer.append(" = ");
					sqlBuffer.append("STR_TO_DATE('");
					sqlBuffer.append(string);
					sqlBuffer.append("','%Y-%m-%d %H:%i:%s'),");
				} else {
					continue;
				}
			}
		}
		sqlBuffer.delete(sqlBuffer.lastIndexOf(","), sqlBuffer.length());
		sqlBuffer.append(";");
		
		try {
			int insert = jdbcTemplate.update(sqlBuffer.toString());
			if (insert < 1) {
				throw new LyhtRuntimeException(TabExceptionEnums.INSERT_FAILURE);
			}
		} catch (Exception e) {
			throw new LyhtRuntimeException(TabExceptionEnums.INSERT_FAILURE);
		}
		
		return parameterMap;
	}
	
	/**
	 * 修改
	 * @param parameterMap
	 * @param request
	 * @return
	 */
	public Map<String, Object> update(Map<String, Object> parameterMap, HttpServletRequest request) {
		if (parameterMap == null || parameterMap.isEmpty()) {
			throw new LyhtRuntimeException(TabExceptionEnums.PARAM_IS_NOT_NULL);
		}
		Object tableIdObj = parameterMap.get("configTableId");
		if (tableIdObj == null) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}
		Integer configTableId = Integer.parseInt(String.valueOf(tableIdObj));
		Object idObj = parameterMap.get("id");
		if (idObj == null) {
			throw new LyhtRuntimeException(TabExceptionEnums.ID_IS_NOT_NULL);
		}
		Integer id = Integer.parseInt(String.valueOf(idObj));
		Optional<ConfigTableEntity> findById = configTableDao.findById(configTableId);
		if (!findById.isPresent()) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}
		ConfigTableEntity configTableEntity = findById.get();//表
		String configTableName = configTableEntity.getConfigTableName();
		
		//update语句
		StringBuffer sqlBuffer = new StringBuffer("UPDATE " + configTableName + " SET ");
		//项目编码
		Object projectNm = parameterMap.get("project_nm");
		if (projectNm != null) {
			sqlBuffer.append("project_nm = '");
			sqlBuffer.append(projectNm);
			sqlBuffer.append("',");
		}
		//行政区域
		Object region = parameterMap.get("region");
		if (region != null) {
			sqlBuffer.append("region = '");
			sqlBuffer.append(region);
			sqlBuffer.append("',");
		}
		//指标类型
//		Object zblx = parameterMap.get("zblx");
//		if (zblx != null) {
//			sqlBuffer.append("zblx = '");
//			sqlBuffer.append(zblx);
//			sqlBuffer.append("',");
//		}
		//修改时间
		sqlBuffer.append("modify_time = ");
		String dateTime = DateUtil.getDateTime();
		sqlBuffer.append("STR_TO_DATE('");
		sqlBuffer.append(dateTime);
		sqlBuffer.append("','%Y-%m-%d %H:%i:%s'),");
		//修改人
		String loginStaffNm = SystemUtil.getLoginStaffNm(request);
		if (StringUtils.isNotBlank(loginStaffNm)) {
			sqlBuffer.append("modify_user = '");
			sqlBuffer.append(loginStaffNm);
			sqlBuffer.append("',");
		}
		//阶段
		Object stage = parameterMap.get("stage");
		if (stage != null) {
			sqlBuffer.append("stage = '");
			sqlBuffer.append(stage);
			sqlBuffer.append("',");
		}
		//备注
		Object remark = parameterMap.get("remark");
		if (remark != null) {
			sqlBuffer.append("remark = '");
			sqlBuffer.append(remark);
			sqlBuffer.append("',");
		}
		
		List<ConfigTableFieldEntity> findByConfigTableId = configTableFieldDao.findByConfigTableId(configTableId);//字段
		if (findByConfigTableId != null && !findByConfigTableId.isEmpty()) {
			for(ConfigTableFieldEntity configTableFieldEntity :findByConfigTableId) {
				String configFieldName = configTableFieldEntity.getConfigFieldName();//字段名称
				String configFieldDisplayName = configTableFieldEntity.getConfigFieldDisplayName();//字段显示名称
				Integer configFieldLength = configTableFieldEntity.getConfigFieldLength();//字段总长度
				Integer configFieldDouble = configTableFieldEntity.getConfigFieldDouble();//小数位数
				String configFieldType = configTableFieldEntity.getConfigFieldType();//字段数据库类型
				Integer configFieldIsempty = configTableFieldEntity.getConfigFieldIsempty();//是否为空
				Integer configFieldEdit = configTableFieldEntity.getConfigFieldEdit();//是否可编辑
				Object value = parameterMap.get(configFieldName);
				if (configFieldIsempty != null && configFieldIsempty == 0 && value == null) {//字段非空
					throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", "字段不允许为空"));
				}
				if (value == null || configFieldEdit == null || configFieldEdit == 0) {//如果该字段参数为空，则不处理
					continue;
				}
				if (StringUtils.equals(configFieldName, "stage") && stage != null) {//如果是阶段字段，则不再处理
					continue;
				}
				String string = String.valueOf(value);
				if (configFieldLength != null && string.length() > configFieldLength) {//字段值超长
					throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，长度不能超过" + configFieldLength));
				}
				if (StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_VARCHAR)) {
					if (StringUtils.isNotBlank(string)) {
						sqlBuffer.append(configFieldName);
						sqlBuffer.append(" = '");
						sqlBuffer.append(string);
						sqlBuffer.append("',");
					} else {
						sqlBuffer.append(configFieldName);
						sqlBuffer.append(" = null,");
					}
				}else if(StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_INT)) {
					if (string.length() > 11) {
						throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，整型长度不可超过11位"));
					}
					if (StringUtils.isNotBlank(string)) {
						sqlBuffer.append(configFieldName);
						sqlBuffer.append(" = ");
						sqlBuffer.append(string);
						sqlBuffer.append(",");
					} else {
						sqlBuffer.append(configFieldName);
						sqlBuffer.append(" = null,");
					}
				} else if(StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_DECIMAL)) {
					String[] split = string.split("\\.");
					int left = configFieldLength;
					int right = 0;
					if (configFieldDouble != null) {
						left = left - configFieldDouble;;
						right = configFieldDouble;
					}
					if(split[0].length() > left) {
						throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，整数位数不能超过" + left + ",小数位数不能超过" + configFieldDouble));
					}
					if (split.length > 1 && split[1].length() > right) {
						throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，整数位数不能超过" + left + ",小数位数不能超过" + configFieldDouble));
					}
					if (StringUtils.isNotBlank(string)) {
						sqlBuffer.append(configFieldName);
						sqlBuffer.append(" = ");
						sqlBuffer.append(string);
						sqlBuffer.append(",");
					} else {
						sqlBuffer.append(configFieldName);
						sqlBuffer.append(" = null,");
					}
				} else if(StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_DATE)) {
					Date strToDate = DateUtil.strToDate(string, "yyyy-MM-dd");
					if (strToDate == null) {
						throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，日期格式不正确(yyyy-MM-dd)"));
					}
					if (StringUtils.isNotBlank(string)) {
						if (string.length() > 10) {
							string = string.substring(0, 10);
						}
						sqlBuffer.append(configFieldName);
						sqlBuffer.append(" = ");
						sqlBuffer.append("STR_TO_DATE('");
						sqlBuffer.append(string);
						sqlBuffer.append("','%Y-%m-%d'),");
					} else {
						sqlBuffer.append(configFieldName);
						sqlBuffer.append(" = null,");
					}
				} else if(StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_DATETIME)) {
					Date strToDate = DateUtil.strToDate(string, "yyyy-MM-dd HH:mm:ss");
					if (strToDate == null) {
						throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，日期格式不正确(yyyy-MM-dd HH:mm:ss)"));
					}
					if (StringUtils.isNotBlank(string)) {
						sqlBuffer.append(configFieldName);
						sqlBuffer.append(" = ");
						sqlBuffer.append("STR_TO_DATE('");
						sqlBuffer.append(string);
						sqlBuffer.append("','%Y-%m-%d %H:%i:%s'),");
					} else {
						sqlBuffer.append(configFieldName);
						sqlBuffer.append(" = null,");
					}
				} else {
					continue;
				}
			}
		}
		sqlBuffer.delete(sqlBuffer.lastIndexOf(","), sqlBuffer.length());
		//ID
		sqlBuffer.append(" WHERE ");
		sqlBuffer.append("id = ");
		sqlBuffer.append(id);
		sqlBuffer.append(";");
		
		try {
			int update = jdbcTemplate.update(sqlBuffer.toString());
			if (update < 1) {
				throw new LyhtRuntimeException(TabExceptionEnums.UPDATE_FAILURE);
			}
		} catch (Exception e) {
			throw new LyhtRuntimeException(TabExceptionEnums.UPDATE_FAILURE);
		}
		
		return parameterMap;
	}
	
	/**
	 * 删除
	 * @param parameterMap
	 * @param request
	 * @return
	 */
	public Map<String, Object> delete(Map<String, Object> parameterMap) {
		if (parameterMap == null || parameterMap.isEmpty()) {
			throw new LyhtRuntimeException(TabExceptionEnums.PARAM_IS_NOT_NULL);
		}
		Object tableIdObj = parameterMap.get("configTableId");
		if (tableIdObj == null) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}
		Integer configTableId = Integer.parseInt(String.valueOf(tableIdObj));
		Object idObj = parameterMap.get("id");
		if (idObj == null) {
			throw new LyhtRuntimeException(TabExceptionEnums.ID_IS_NOT_NULL);
		}
		Integer id = Integer.parseInt(String.valueOf(idObj));
		Optional<ConfigTableEntity> findById = configTableDao.findById(configTableId);
		if (!findById.isPresent()) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}
		ConfigTableEntity configTableEntity = findById.get();//表
		String configTableName = configTableEntity.getConfigTableName();
		
		//delete语句
		StringBuffer sqlBuffer = new StringBuffer("DELETE FROM ");
		sqlBuffer.append(configTableName);
		sqlBuffer.append(" WHERE ");
		sqlBuffer.append("id = ");
		sqlBuffer.append(id);
		sqlBuffer.append(";");
		
		int delete = jdbcTemplate.update(sqlBuffer.toString());
		if (delete < 1) {
			throw new LyhtRuntimeException(TabExceptionEnums.DELETE_FAILURE);
		}
		
		return parameterMap;
	}
	
	/**
	 * 查询
	 * @param parameterMap
	 * @return
	 */
	public List<Map<String, Object>> list(Map<String, Object> parameterMap) {
		if (parameterMap == null || parameterMap.isEmpty()) {
			throw new LyhtRuntimeException(TabExceptionEnums.PARAM_IS_NOT_NULL);
		}
		Object tableIdObj = parameterMap.get("configTableId");
		if (tableIdObj == null) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}
		Integer configTableId = Integer.parseInt(String.valueOf(tableIdObj));
		Optional<ConfigTableEntity> findById = configTableDao.findById(configTableId);
		if (!findById.isPresent()) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}
		
		ConfigTableEntity configTableEntity = findById.get();//表
		String configTableName = configTableEntity.getConfigTableName();//表名
		
		//查询字段sql
		StringBuffer fieldBuffer = new StringBuffer();
		fieldBuffer.append(" tt.id AS id,tt.nm AS nm,tt.remark AS remark,");
		fieldBuffer.append("tt.project_nm AS project_nm,tt.region AS region,");
		fieldBuffer.append("tpp.name AS project_name,");
		fieldBuffer.append("REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS region_name,");
		fieldBuffer.append("tt.create_user AS create_user,tt.modify_user AS modify_user,");
		fieldBuffer.append("tss.staff_name AS create_user_name,tss1.staff_name AS modify_user_name,");
		fieldBuffer.append("IF(tt.create_time is not null,DATE_FORMAT(tt.create_time,'%Y-%m-%d %H:%i:%s'),null) AS create_time,");
		fieldBuffer.append("IF(tt.modify_time is not null,DATE_FORMAT(tt.modify_time,'%Y-%m-%d %H:%i:%s'),null) AS modify_time,");
		//关联表sql
		StringBuffer fkBuffer = new StringBuffer();
		fkBuffer.append(" LEFT JOIN pub_project AS tpp ON tt.project_nm = tpp.nm");//项目
		fkBuffer.append(" LEFT JOIN pub_region AS tpr ON tt.region = tpr.city_code");//行政区
//		fkBuffer.append(" LEFT JOIN pub_dict_value AS tpdv ON tt.zblx = tpdv.nm");//指标类型
//		fkBuffer.append(" LEFT JOIN pub_dict_value AS tpdv1 ON tt.stage = tpdv1.nm");//阶段
		fkBuffer.append(" LEFT JOIN sys_staff AS tss ON tt.create_user = tss.nm");//创建人
		fkBuffer.append(" LEFT JOIN sys_staff AS tss1 ON tt.modify_user = tss1.nm");//修改人
		//查询条件sql
		StringBuffer whereBuffer = new StringBuffer();
		whereBuffer.append(" WHERE 1=1 ");
		//ID
		Object id = parameterMap.get("id");
		if (id != null) {
			whereBuffer.append(" AND tt.id = '" + id + "' ");
		}
		//NM
		Object nm = parameterMap.get("nm");
		if (nm != null) {
			whereBuffer.append(" AND tt.nm = '" + nm + "' ");
		}
		//项目编码
		Object projectNm = parameterMap.get("project_nm");
		if (projectNm != null) {
			whereBuffer.append(" AND tt.project_nm = '" + projectNm + "' ");
		}
		//项目长编码
		Object projectFcode = parameterMap.get("projectFcode");
		if (projectFcode != null) {
			whereBuffer.append(" AND tpp.f_code LIKE CONCAT('" + projectFcode + "','%') ");
		}
		//行政区域
		Object region = parameterMap.get("region");
		if (region != null) {
			whereBuffer.append(" AND tt.region = '" + region + "' ");
		}
		//行政区域全称
		Object regionName = parameterMap.get("regionName");
		if (regionName != null) {
			whereBuffer.append(" AND REPLACE(tpr.merger_name,',','') LIKE CONCAT('%','" + regionName + "','%') ");
		}
		
		//字段关联关系与查询配置
		List<ConfigTableFieldEntity> findByConfigTableId = configTableFieldDao.findByConfigTableId(configTableId);//字段
		if (findByConfigTableId != null && !findByConfigTableId.isEmpty()) {
			for(ConfigTableFieldEntity configTableFieldEntity :findByConfigTableId) {
				String configFieldName = configTableFieldEntity.getConfigFieldName();//字段名称
				String configFieldDisplayName = configTableFieldEntity.getConfigFieldDisplayName();//字段显示名称
				String configFieldType = configTableFieldEntity.getConfigFieldType();//字段数据库类型
//				Integer configFieldShow = configTableFieldEntity.getConfigFieldShow();//是否显示
				Integer configFieldSearch = configTableFieldEntity.getConfigFieldSearch();//是否可查询
				Integer fieldIsFk = configTableFieldEntity.getFieldIsFk();//是否是外键
				String fieldFkTable = configTableFieldEntity.getFieldFkTable();//外键表名
				String fieldFkName = configTableFieldEntity.getFieldFkName();//外键关联字段名
				String fieldFkShowName = configTableFieldEntity.getFieldFkShowName();//外键显示字段名
				//不显示
//				if (configFieldShow == null || configFieldShow == 0) {
//					continue;
//				}
				//查询字段sql拼接
				if(StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_DATETIME)) {
					fieldBuffer.append("IF(tt." + configFieldName + " is not null,DATE_FORMAT(tt." + configFieldName + ",'%Y-%m-%d %H:%i:%s'),null) AS " + configFieldName + ",");
				} else {
					fieldBuffer.append("tt." + configFieldName + " AS " + configFieldName + ",");
				}
				//外键
				String pkTableName = null;
				if (fieldIsFk != null && fieldIsFk == 1 && StringUtils.isNotBlank(fieldFkTable) && StringUtils.isNotBlank(fieldFkName)) {
					pkTableName = fieldFkTable + Randomizer.generCode(4);//关联表别名（表名+唯一四位系统编码）
					fkBuffer.append(" LEFT JOIN " + fieldFkTable + " AS " + pkTableName + " ON tt." + configFieldName + " = " + pkTableName + "." + fieldFkName);
					fieldBuffer.append(pkTableName + "." + fieldFkShowName + " AS " + configFieldName + "_value,");//关联表显示字段别名（主表外键字段名+_value）
				}
				//阶段字段，查询条件特殊处理
				if (StringUtils.equalsIgnoreCase(configFieldName, "stage")) {
					Object stage = parameterMap.get(configFieldName);
					if (stage != null) {
						whereBuffer.append(" AND tt.stage = '" + stage + "' ");
					}
					continue;
				}
				//不可做查询条件
				if (configFieldSearch == null || configFieldSearch == 0) {
					continue;
				}
				//参数为空，查询条件不处理
				Object value = parameterMap.get(configFieldName);
				if (value == null) {
					continue;
				}
				String string = String.valueOf(value);
				if(StringUtils.isBlank(string)) {
					continue;
				}
				//查询条件sql拼接
				if (StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_VARCHAR)) {
					ConfigSearchEntity configSearchEntity = configTableFieldEntity.getConfigSearchEntity();
					if (configSearchEntity == null) {//查询条件配置为空，不参与查询
						continue;
					}
					String configInputType = configSearchEntity.getConfigInputType();
					if (fieldIsFk != null && fieldIsFk == 1 && StringUtils.isNotBlank(fieldFkTable) && StringUtils.isNotBlank(fieldFkName)) {
						if (StringUtils.equalsIgnoreCase(configInputType, "textAcc")) {//精确查询
							whereBuffer.append(" AND " + pkTableName + "." + fieldFkShowName + " = '" + string + "' ");
						} else {//模糊查询
							whereBuffer.append(" AND " + pkTableName + "." + fieldFkShowName + " LIKE '%" + string + "%' ");
						}
					} else {
						if (StringUtils.equalsIgnoreCase(configInputType, "textAcc")) {//精确查询
							whereBuffer.append(" AND tt." + configFieldName + " = '" + string + "' ");
						} else {//模糊查询
							whereBuffer.append(" AND tt." + configFieldName + " LIKE '%" + string + "%' ");
						}
					}
				} else if(StringUtils.equalsAnyIgnoreCase(configFieldType, TableConst.DATA_TYPE_INT, TableConst.DATA_TYPE_DECIMAL)) {
					whereBuffer.append(" AND tt." + configFieldName + " = " + string + " ");
				} else if(StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_DATE)) {//日期用“，”分割，可精确与范围查询
					String[] split = string.split(",");
					Date strToStartDate = DateUtil.strToDate(split[0], "yyyy-MM-dd");
					if (strToStartDate == null) {
						throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，日期格式不正确(yyyy-MM-dd)"));
					}
					if (split.length > 1) {
						Date strToEndDate = DateUtil.strToDate(split[1], "yyyy-MM-dd");
						if (strToEndDate == null) {
							throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，日期格式不正确(yyyy-MM-dd)"));
						}
						whereBuffer.append(" AND tt.");
						whereBuffer.append(configFieldName);
						whereBuffer.append(" >= ");
						whereBuffer.append("STR_TO_DATE('");
						whereBuffer.append(split[0]);
						whereBuffer.append("','%Y-%m-%d')");
						whereBuffer.append(" AND tt.");
						whereBuffer.append(configFieldName);
						whereBuffer.append(" <= ");
						whereBuffer.append("STR_TO_DATE('");
						whereBuffer.append(split[1]);
						whereBuffer.append("','%Y-%m-%d')");
					} else {
						whereBuffer.append(" AND tt.");
						whereBuffer.append(configFieldName);
						whereBuffer.append(" = ");
						whereBuffer.append("STR_TO_DATE('");
						whereBuffer.append(split[0]);
						whereBuffer.append("','%Y-%m-%d')");
					}
				} else if(StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_DATETIME)) {//日期用“，”分割，可精确与范围查询
					String[] split = string.split(",");
					Date strToStartDate = DateUtil.strToDate(split[0], "yyyy-MM-dd HH:mm:ss");
					if (strToStartDate == null) {
						throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，日期格式不正确(yyyy-MM-dd HH:mm:ss)"));
					}
					if (split.length > 1) {
						Date strToEndDate = DateUtil.strToDate(split[1], "yyyy-MM-dd HH:mm:ss");
						if (strToEndDate == null) {
							throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，日期格式不正确(yyyy-MM-dd HH:mm:ss)"));
						}
						whereBuffer.append(" AND tt.");
						whereBuffer.append(configFieldName);
						whereBuffer.append(" >= ");
						whereBuffer.append("STR_TO_DATE('");
						whereBuffer.append(split[0]);
						whereBuffer.append("','%Y-%m-%d %H:%i:%s')");
						whereBuffer.append(" AND tt.");
						whereBuffer.append(configFieldName);
						whereBuffer.append(" <= ");
						whereBuffer.append("STR_TO_DATE('");
						whereBuffer.append(split[1]);
						whereBuffer.append("','%Y-%m-%d %H:%i:%s')");
					} else {
						whereBuffer.append(" AND tt.");
						whereBuffer.append(configFieldName);
						whereBuffer.append(" = ");
						whereBuffer.append("STR_TO_DATE('");
						whereBuffer.append(split[0]);
						whereBuffer.append("','%Y-%m-%d %H:%i:%s')");
					}
				} else {
					continue;
				}
			}
		}
		//删除查询字段sql的最后一个“，”
		fieldBuffer.delete(fieldBuffer.lastIndexOf(","), fieldBuffer.length());
		
		//select语句
		StringBuffer sqlBuffer = new StringBuffer("SELECT ");
		sqlBuffer.append(fieldBuffer);//字段
		sqlBuffer.append(" FROM ");
		sqlBuffer.append(configTableName);//主表
		sqlBuffer.append(" AS tt ");
		sqlBuffer.append(fkBuffer);//关联表
		sqlBuffer.append(whereBuffer);//条件
		sqlBuffer.append(" ORDER BY tt.modify_time DESC,tt.create_time DESC,tt.id ASC");//排序
		
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sqlBuffer.toString());
		
		return queryForList;
	}
	
	/**
	 * 分页
	 * @param parameterMap
	 * @return
	 */
	public LyhtResultBody<List<Map<String, Object>>> page(Map<String, Object> parameterMap) {
		if (parameterMap == null || parameterMap.isEmpty()) {
			throw new LyhtRuntimeException(TabExceptionEnums.PARAM_IS_NOT_NULL);
		}
		Object tableIdObj = parameterMap.get("configTableId");
		if (tableIdObj == null) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}
		Integer configTableId = Integer.parseInt(String.valueOf(tableIdObj));
		Optional<ConfigTableEntity> findById = configTableDao.findById(configTableId);
		if (!findById.isPresent()) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}
		
		ConfigTableEntity configTableEntity = findById.get();//表
		String configTableName = configTableEntity.getConfigTableName();//表名
		
		//查询字段sql
		StringBuffer fieldBuffer = new StringBuffer();
		fieldBuffer.append(" tt.id AS id,tt.nm AS nm,tt.remark AS remark,");
		fieldBuffer.append("tt.project_nm AS project_nm,tt.region AS region,");
		fieldBuffer.append("tpp.name AS project_name,");
		fieldBuffer.append("REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS region_name,");//行政区域，前三级不显示
		fieldBuffer.append("tt.create_user AS create_user,tt.modify_user AS modify_user,");
		fieldBuffer.append("tss.staff_name AS create_user_name,tss1.staff_name AS modify_user_name,");
		fieldBuffer.append("IF(tt.create_time is not null,DATE_FORMAT(tt.create_time,'%Y-%m-%d %H:%i:%s'),null) AS create_time,");
		fieldBuffer.append("IF(tt.modify_time is not null,DATE_FORMAT(tt.modify_time,'%Y-%m-%d %H:%i:%s'),null) AS modify_time,");
		//关联表sql
		StringBuffer fkBuffer = new StringBuffer();
		fkBuffer.append(" LEFT JOIN pub_project AS tpp ON tt.project_nm = tpp.id");//项目
		fkBuffer.append(" LEFT JOIN pub_region AS tpr ON tt.region = tpr.city_code");//行政区
//		fkBuffer.append(" LEFT JOIN pub_dict_value AS tpdv ON tt.zblx = tpdv.nm");//指标类型
//		fkBuffer.append(" LEFT JOIN pub_dict_value AS tpdv1 ON tt.stage = tpdv1.nm");//阶段
		fkBuffer.append(" LEFT JOIN sys_staff AS tss ON tt.create_user = tss.nm");//创建人
		fkBuffer.append(" LEFT JOIN sys_staff AS tss1 ON tt.modify_user = tss1.nm");//修改人
		//查询条件sql
		StringBuffer whereBuffer = new StringBuffer();
		whereBuffer.append(" WHERE 1=1 ");
		//ID
		Object id = parameterMap.get("id");
		if (id != null) {
			whereBuffer.append(" AND tt.id = '" + id + "' ");
		}
		//NM
		Object nm = parameterMap.get("nm");
		if (nm != null) {
			whereBuffer.append(" AND tt.nm = '" + nm + "' ");
		}
		//项目编码
		Object projectNm = parameterMap.get("project_nm");
		if (projectNm != null) {
			whereBuffer.append(" AND tt.project_nm = '" + projectNm + "' ");
		}
		//项目长编码
		Object projectFcode = parameterMap.get("projectFcode");
		if (projectFcode != null) {
			whereBuffer.append(" AND tpp.fcode LIKE CONCAT('" + projectFcode + "','%') ");
		}
		//行政区域
		Object region = parameterMap.get("region");
		if (region != null) {
			whereBuffer.append(" AND tt.region = '" + region + "' ");
		}
		//行政区域全称
		Object regionName = parameterMap.get("regionName");
		if (regionName != null) {
			whereBuffer.append(" AND REPLACE(tpr.merger_name,',','') Like CONCAT('%','" + regionName + "','%')");
		}
		
		//字段关联关系与查询配置
		List<ConfigTableFieldEntity> findByConfigTableId = configTableFieldDao.findByConfigTableId(configTableId);//字段
		if (findByConfigTableId != null && !findByConfigTableId.isEmpty()) {
			for(ConfigTableFieldEntity configTableFieldEntity :findByConfigTableId) {
				String configFieldName = configTableFieldEntity.getConfigFieldName();//字段名称
				String configFieldDisplayName = configTableFieldEntity.getConfigFieldDisplayName();//字段显示名称
				String configFieldType = configTableFieldEntity.getConfigFieldType();//字段数据库类型
//				Integer configFieldShow = configTableFieldEntity.getConfigFieldShow();//是否显示
				Integer configFieldSearch = configTableFieldEntity.getConfigFieldSearch();//是否可查询
				Integer fieldIsFk = configTableFieldEntity.getFieldIsFk();//是否是外键
				String fieldFkTable = configTableFieldEntity.getFieldFkTable();//外键表名
				String fieldFkName = configTableFieldEntity.getFieldFkName();//外键关联字段名
				String fieldFkShowName = configTableFieldEntity.getFieldFkShowName();//外键显示字段名
				//不显示
//				if (configFieldShow == null || configFieldShow == 0) {
//					continue;
//				}
				//查询字段sql拼接
				if(StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_DATETIME)) {
					fieldBuffer.append("IF(tt." + configFieldName + " is not null,DATE_FORMAT(tt." + configFieldName + ",'%Y-%m-%d %H:%i:%s'),null) AS " + configFieldName + ",");
				} else {
					fieldBuffer.append("tt." + configFieldName + " AS " + configFieldName + ",");
				}
				//外键
				String pkTableName = null;
				if (fieldIsFk != null && fieldIsFk == 1 && StringUtils.isNotBlank(fieldFkTable) && StringUtils.isNotBlank(fieldFkName)) {
					pkTableName = fieldFkTable + Randomizer.generCode(4);//关联表别名（表名+唯一四位系统编码）
					fkBuffer.append(" LEFT JOIN " + fieldFkTable + " AS " + pkTableName + " ON tt." + configFieldName + " = " + pkTableName + "." + fieldFkName);
					fieldBuffer.append(pkTableName + "." + fieldFkShowName + " AS " + configFieldName + "_value,");//关联表显示字段别名（主表外键字段名+_value）
				}
				//阶段字段，查询条件特殊处理
				if (StringUtils.equalsIgnoreCase(configFieldName, "stage")) {
					Object stage = parameterMap.get(configFieldName);
					if (stage != null) {
						whereBuffer.append(" AND tt.stage = '" + stage + "' ");
					}
					continue;
				}
				//不可做查询条件
				if (configFieldSearch == null || configFieldSearch == 0) {
					continue;
				}
				//参数为空，查询条件不处理
				Object value = parameterMap.get(configFieldName);
				if (value == null) {
					continue;
				}
				String string = String.valueOf(value);
				if(StringUtils.isBlank(string)) {
					continue;
				}
				//查询条件sql拼接
				if (StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_VARCHAR)) {
					ConfigSearchEntity configSearchEntity = configTableFieldEntity.getConfigSearchEntity();
					if (configSearchEntity == null) {//查询条件配置为空，不参与查询
						continue;
					}
					String configInputType = configSearchEntity.getConfigInputType();
					if (fieldIsFk != null && fieldIsFk == 1 && StringUtils.isNotBlank(fieldFkTable) && StringUtils.isNotBlank(fieldFkName)) {
						if (StringUtils.equalsIgnoreCase(configInputType, "text")) {//模糊查询
							whereBuffer.append(" AND " + pkTableName + "." + fieldFkShowName + " LIKE '%" + string + "%' ");
						} else {//精确查询
							whereBuffer.append(" AND tt." + configFieldName + " = '" + string + "' ");
						}
					} else {
						if (StringUtils.equalsIgnoreCase(configInputType, "text")) {//模糊查询
							whereBuffer.append(" AND tt." + configFieldName + " LIKE '%" + string + "%' ");
						} else {//精确查询
							whereBuffer.append(" AND tt." + configFieldName + " = '" + string + "' ");
						}
					}
				} else if(StringUtils.equalsAnyIgnoreCase(configFieldType, TableConst.DATA_TYPE_INT, TableConst.DATA_TYPE_DECIMAL)) {
					whereBuffer.append(" AND tt." + configFieldName + " = " + string + " ");
				} else if(StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_DATE)) {//日期用“，”分割，可精确与范围查询
					String[] split = string.split(",");
					Date strToStartDate = DateUtil.strToDate(split[0], "yyyy-MM-dd");
					if (strToStartDate == null) {
						throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，日期格式不正确(yyyy-MM-dd)"));
					}
					if (split.length > 1) {
						Date strToEndDate = DateUtil.strToDate(split[1], "yyyy-MM-dd");
						if (strToEndDate == null) {
							throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，日期格式不正确(yyyy-MM-dd)"));
						}
						whereBuffer.append(" AND tt.");
						whereBuffer.append(configFieldName);
						whereBuffer.append(" >= ");
						whereBuffer.append("STR_TO_DATE('");
						whereBuffer.append(split[0]);
						whereBuffer.append("','%Y-%m-%d')");
						whereBuffer.append(" AND tt.");
						whereBuffer.append(configFieldName);
						whereBuffer.append(" <= ");
						whereBuffer.append("STR_TO_DATE('");
						whereBuffer.append(split[1]);
						whereBuffer.append("','%Y-%m-%d')");
					} else {
						whereBuffer.append(" AND tt.");
						whereBuffer.append(configFieldName);
						whereBuffer.append(" = ");
						whereBuffer.append("STR_TO_DATE('");
						whereBuffer.append(split[0]);
						whereBuffer.append("','%Y-%m-%d')");
					}
				} else if(StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_DATETIME)) {//日期用“，”分割，可精确与范围查询
					String[] split = string.split(",");
					Date strToStartDate = DateUtil.strToDate(split[0], "yyyy-MM-dd HH:mm:ss");
					if (strToStartDate == null) {
						throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，日期格式不正确(yyyy-MM-dd HH:mm:ss)"));
					}
					if (split.length > 1) {
						Date strToEndDate = DateUtil.strToDate(split[1], "yyyy-MM-dd HH:mm:ss");
						if (strToEndDate == null) {
							throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，日期格式不正确(yyyy-MM-dd HH:mm:ss)"));
						}
						whereBuffer.append(" AND tt.");
						whereBuffer.append(configFieldName);
						whereBuffer.append(" >= ");
						whereBuffer.append("STR_TO_DATE('");
						whereBuffer.append(split[0]);
						whereBuffer.append("','%Y-%m-%d %H:%i:%s')");
						whereBuffer.append(" AND tt.");
						whereBuffer.append(configFieldName);
						whereBuffer.append(" <= ");
						whereBuffer.append("STR_TO_DATE('");
						whereBuffer.append(split[1]);
						whereBuffer.append("','%Y-%m-%d %H:%i:%s')");
					} else {
						whereBuffer.append(" AND tt.");
						whereBuffer.append(configFieldName);
						whereBuffer.append(" = ");
						whereBuffer.append("STR_TO_DATE('");
						whereBuffer.append(split[0]);
						whereBuffer.append("','%Y-%m-%d %H:%i:%s')");
					}
				} else {
					continue;
				}
			}
		}
		//删除查询字段sql的最后一个“，”
		fieldBuffer.delete(fieldBuffer.lastIndexOf(","), fieldBuffer.length());
		
		//select语句
		StringBuffer sqlBuffer = new StringBuffer("SELECT ");
		sqlBuffer.append(fieldBuffer);//字段
		sqlBuffer.append(" FROM ");
		sqlBuffer.append(configTableName);//主表
		sqlBuffer.append(" AS tt ");
		sqlBuffer.append(fkBuffer);//关联表
		sqlBuffer.append(whereBuffer);//条件
		sqlBuffer.append(" ORDER BY tt.modify_time DESC,tt.create_time DESC,tt.id ASC");//排序
		
		Object currentObj = parameterMap.get("current");//页码
		Object pageSizeObj = parameterMap.get("pageSize");//每页显示条数
		Integer current = 1;
		Integer pageSize = 10;
		if (currentObj != null && pageSizeObj != null) {
			current = Integer.valueOf(String.valueOf(currentObj));
			pageSize = Integer.valueOf(String.valueOf(pageSizeObj));
		}
		sqlBuffer.append(" limit ");
		sqlBuffer.append((current - 1) * pageSize);
		sqlBuffer.append(",");
		sqlBuffer.append(pageSize);
		
		StringBuffer countSqlBuffer = new StringBuffer("SELECT COUNT(tt.id) AS count");
		countSqlBuffer.append(" FROM ");
		countSqlBuffer.append(configTableName);//主表
		countSqlBuffer.append(" AS tt ");
		countSqlBuffer.append(fkBuffer);//关联表
		countSqlBuffer.append(whereBuffer);//条件
		
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sqlBuffer.toString());
		Long count = jdbcTemplate.queryForObject(countSqlBuffer.toString(), Long.class);
		LyhtPageVO pageVO = new LyhtPageVO(current - 1, null, pageSize,count,null);
		return new LyhtResultBody<>(queryForList, pageVO);
	}

	/**
	 * 查询条件不控制，查询所有字段
	 * @param parameterMap
	 * @return
	 */
	public LyhtResultBody<List<Map<String, Object>>> detailPage(Map<String, Object> parameterMap) {
		if (parameterMap == null || parameterMap.isEmpty()) {
			throw new LyhtRuntimeException(TabExceptionEnums.PARAM_IS_NOT_NULL);
		}
		Object tableIdObj = parameterMap.get("configTableId");
		if (tableIdObj == null) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}
		Integer configTableId = Integer.parseInt(String.valueOf(tableIdObj));
		Optional<ConfigTableEntity> findById = configTableDao.findById(configTableId);
		if (!findById.isPresent()) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}
		
		ConfigTableEntity configTableEntity = findById.get();//表
		String configTableName = configTableEntity.getConfigTableName();//表名
		
		//查询字段sql
		StringBuffer fieldBuffer = new StringBuffer();
		fieldBuffer.append(" tt.id AS id,tt.nm AS nm,tt.remark AS remark,");
		fieldBuffer.append("tt.project_nm AS project_nm,tt.region AS region,");
		fieldBuffer.append("tpp.name AS project_name,");
		fieldBuffer.append("REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS region_name,");//行政区域，前三级不显示
		fieldBuffer.append("tt.create_user AS create_user,tt.modify_user AS modify_user,");
		fieldBuffer.append("tss.staff_name AS create_user_name,tss1.staff_name AS modify_user_name,");
		fieldBuffer.append("IF(tt.create_time is not null,DATE_FORMAT(tt.create_time,'%Y-%m-%d %H:%i:%s'),null) AS create_time,");
		fieldBuffer.append("IF(tt.modify_time is not null,DATE_FORMAT(tt.modify_time,'%Y-%m-%d %H:%i:%s'),null) AS modify_time,");
		//关联表sql
		StringBuffer fkBuffer = new StringBuffer();
		fkBuffer.append(" LEFT JOIN pub_project AS tpp ON tt.project_nm = tpp.id");//项目
		fkBuffer.append(" LEFT JOIN pub_region AS tpr ON tt.region = tpr.city_code");//行政区
//		fkBuffer.append(" LEFT JOIN pub_dict_value AS tpdv ON tt.zblx = tpdv.nm");//指标类型
//		fkBuffer.append(" LEFT JOIN pub_dict_value AS tpdv1 ON tt.stage = tpdv1.nm");//阶段
		fkBuffer.append(" LEFT JOIN sys_staff AS tss ON tt.create_user = tss.nm");//创建人
		fkBuffer.append(" LEFT JOIN sys_staff AS tss1 ON tt.modify_user = tss1.nm");//修改人
		//查询条件sql
		StringBuffer whereBuffer = new StringBuffer();
		whereBuffer.append(" WHERE 1=1 ");
		//ID
		Object id = parameterMap.get("id");
		if (id != null) {
			whereBuffer.append(" AND tt.id = '" + id + "' ");
		}
		//NM
		Object nm = parameterMap.get("nm");
		if (nm != null) {
			whereBuffer.append(" AND tt.nm = '" + nm + "' ");
		}
		//项目编码
		Object projectNm = parameterMap.get("project_nm");
		if (projectNm != null) {
			whereBuffer.append(" AND tt.project_nm = '" + projectNm + "' ");
		}
		//项目长编码
		Object projectFcode = parameterMap.get("projectFcode");
		if (projectFcode != null) {
			whereBuffer.append(" AND tpp.fcode LIKE CONCAT('" + projectFcode + "','%') ");
		}
		//行政区域
		Object region = parameterMap.get("region");
		if (region != null) {
			whereBuffer.append(" AND tt.region = '" + region + "' ");
		}
		//行政区域全称
		Object regionName = parameterMap.get("regionName");
		if (regionName != null) {
			whereBuffer.append(" AND REPLACE(tpr.merger_name,',','') Like CONCAT('%','" + regionName + "','%')");
		}
		
		//字段关联关系与查询配置
		List<ConfigTableFieldEntity> findByConfigTableId = configTableFieldDao.findByConfigTableId(configTableId);//字段
		if (findByConfigTableId != null && !findByConfigTableId.isEmpty()) {
			for(ConfigTableFieldEntity configTableFieldEntity :findByConfigTableId) {
				String configFieldName = configTableFieldEntity.getConfigFieldName();//字段名称
				String configFieldDisplayName = configTableFieldEntity.getConfigFieldDisplayName();//字段显示名称
				String configFieldType = configTableFieldEntity.getConfigFieldType();//字段数据库类型
//				Integer configFieldShow = configTableFieldEntity.getConfigFieldShow();//是否显示
//				Integer configFieldSearch = configTableFieldEntity.getConfigFieldSearch();//是否可查询
				Integer fieldIsFk = configTableFieldEntity.getFieldIsFk();//是否是外键
				String fieldFkTable = configTableFieldEntity.getFieldFkTable();//外键表名
				String fieldFkName = configTableFieldEntity.getFieldFkName();//外键关联字段名
				String fieldFkShowName = configTableFieldEntity.getFieldFkShowName();//外键显示字段名
				//不显示
//				if (configFieldShow == null || configFieldShow == 0) {
//					continue;
//				}
				//查询字段sql拼接
				if(StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_DATETIME)) {
					fieldBuffer.append("IF(tt." + configFieldName + " is not null,DATE_FORMAT(tt." + configFieldName + ",'%Y-%m-%d %H:%i:%s'),null) AS " + configFieldName + ",");
				} else {
					fieldBuffer.append("tt." + configFieldName + " AS " + configFieldName + ",");
				}
				//外键
				String pkTableName = null;
				if (fieldIsFk != null && fieldIsFk == 1 && StringUtils.isNotBlank(fieldFkTable) && StringUtils.isNotBlank(fieldFkName)) {
					pkTableName = fieldFkTable + Randomizer.generCode(4);//关联表别名（表名+唯一四位系统编码）
					fkBuffer.append(" LEFT JOIN " + fieldFkTable + " AS " + pkTableName + " ON tt." + configFieldName + " = " + pkTableName + "." + fieldFkName);
					fieldBuffer.append(pkTableName + "." + fieldFkShowName + " AS " + configFieldName + "_value,");//关联表显示字段别名（主表外键字段名+_value）
				}
				//阶段字段，查询条件特殊处理
				if (StringUtils.equalsIgnoreCase(configFieldName, "stage")) {
					Object stage = parameterMap.get(configFieldName);
					if (stage != null) {
						whereBuffer.append(" AND tt.stage = '" + stage + "' ");
					}
					continue;
				}
				//不可做查询条件
//				if (configFieldSearch == null || configFieldSearch == 0) {
//					continue;
//				}
				//参数为空，查询条件不处理
				Object value = parameterMap.get(configFieldName);
				if (value == null) {
					continue;
				}
				String string = String.valueOf(value);
				if(StringUtils.isBlank(string)) {
					continue;
				}
				//查询条件sql拼接
				if (StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_VARCHAR)) {
					ConfigSearchEntity configSearchEntity = configTableFieldEntity.getConfigSearchEntity();
					String configInputType = null;
					if (configSearchEntity != null) {
						configInputType = configSearchEntity.getConfigInputType();
					}
					if (fieldIsFk != null && fieldIsFk == 1 && StringUtils.isNotBlank(fieldFkTable) && StringUtils.isNotBlank(fieldFkName)) {
//						if (StringUtils.equalsIgnoreCase(configInputType, "text")) {//模糊查询
//							whereBuffer.append(" AND " + pkTableName + "." + fieldFkShowName + " LIKE '%" + string + "%' ");
//						} else {//精确查询
							whereBuffer.append(" AND tt." + configFieldName + " = '" + string + "' ");
//						}
					} else {
						if (StringUtils.equalsIgnoreCase(configInputType, "textAcc")) {//精确查询
							whereBuffer.append(" AND tt." + configFieldName + " = '" + string + "' ");
						} else {//精确查询
							whereBuffer.append(" AND tt." + configFieldName + " LIKE '%" + string + "%' ");
						}
					}
				} else if(StringUtils.equalsAnyIgnoreCase(configFieldType, TableConst.DATA_TYPE_INT, TableConst.DATA_TYPE_DECIMAL)) {
					whereBuffer.append(" AND tt." + configFieldName + " = " + string + " ");
				} else if(StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_DATE)) {//日期用“，”分割，可精确与范围查询
					String[] split = string.split(",");
					Date strToStartDate = DateUtil.strToDate(split[0], "yyyy-MM-dd");
					if (strToStartDate == null) {
						throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，日期格式不正确(yyyy-MM-dd)"));
					}
					if (split.length > 1) {
						Date strToEndDate = DateUtil.strToDate(split[1], "yyyy-MM-dd");
						if (strToEndDate == null) {
							throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，日期格式不正确(yyyy-MM-dd)"));
						}
						whereBuffer.append(" AND tt.");
						whereBuffer.append(configFieldName);
						whereBuffer.append(" >= ");
						whereBuffer.append("STR_TO_DATE('");
						whereBuffer.append(split[0]);
						whereBuffer.append("','%Y-%m-%d')");
						whereBuffer.append(" AND tt.");
						whereBuffer.append(configFieldName);
						whereBuffer.append(" <= ");
						whereBuffer.append("STR_TO_DATE('");
						whereBuffer.append(split[1]);
						whereBuffer.append("','%Y-%m-%d')");
					} else {
						whereBuffer.append(" AND tt.");
						whereBuffer.append(configFieldName);
						whereBuffer.append(" = ");
						whereBuffer.append("STR_TO_DATE('");
						whereBuffer.append(split[0]);
						whereBuffer.append("','%Y-%m-%d')");
					}
				} else if(StringUtils.equalsIgnoreCase(configFieldType, TableConst.DATA_TYPE_DATETIME)) {//日期用“，”分割，可精确与范围查询
					String[] split = string.split(",");
					Date strToStartDate = DateUtil.strToDate(split[0], "yyyy-MM-dd HH:mm:ss");
					if (strToStartDate == null) {
						throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，日期格式不正确(yyyy-MM-dd HH:mm:ss)"));
					}
					if (split.length > 1) {
						Date strToEndDate = DateUtil.strToDate(split[1], "yyyy-MM-dd HH:mm:ss");
						if (strToEndDate == null) {
							throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-33", configFieldDisplayName + "字段，日期格式不正确(yyyy-MM-dd HH:mm:ss)"));
						}
						whereBuffer.append(" AND tt.");
						whereBuffer.append(configFieldName);
						whereBuffer.append(" >= ");
						whereBuffer.append("STR_TO_DATE('");
						whereBuffer.append(split[0]);
						whereBuffer.append("','%Y-%m-%d %H:%i:%s')");
						whereBuffer.append(" AND tt.");
						whereBuffer.append(configFieldName);
						whereBuffer.append(" <= ");
						whereBuffer.append("STR_TO_DATE('");
						whereBuffer.append(split[1]);
						whereBuffer.append("','%Y-%m-%d %H:%i:%s')");
					} else {
						whereBuffer.append(" AND tt.");
						whereBuffer.append(configFieldName);
						whereBuffer.append(" = ");
						whereBuffer.append("STR_TO_DATE('");
						whereBuffer.append(split[0]);
						whereBuffer.append("','%Y-%m-%d %H:%i:%s')");
					}
				} else {
					continue;
				}
			}
		}
		//删除查询字段sql的最后一个“，”
		fieldBuffer.delete(fieldBuffer.lastIndexOf(","), fieldBuffer.length());
		
		//select语句
		StringBuffer sqlBuffer = new StringBuffer("SELECT ");
		sqlBuffer.append(fieldBuffer);//字段
		sqlBuffer.append(" FROM ");
		sqlBuffer.append(configTableName);//主表
		sqlBuffer.append(" AS tt ");
		sqlBuffer.append(fkBuffer);//关联表
		sqlBuffer.append(whereBuffer);//条件
		sqlBuffer.append(" ORDER BY tt.modify_time DESC,tt.create_time DESC,tt.id ASC");//排序
		
		Object currentObj = parameterMap.get("current");//页码
		Object pageSizeObj = parameterMap.get("pageSize");//每页显示条数
		Integer current = 1;
		Integer pageSize = 10;
		if (currentObj != null && pageSizeObj != null) {
			current = Integer.valueOf(String.valueOf(currentObj));
			pageSize = Integer.valueOf(String.valueOf(pageSizeObj));
		}
		sqlBuffer.append(" limit ");
		sqlBuffer.append((current - 1) * pageSize);
		sqlBuffer.append(",");
		sqlBuffer.append(pageSize);
		
		StringBuffer countSqlBuffer = new StringBuffer("SELECT COUNT(tt.id) AS count");
		countSqlBuffer.append(" FROM ");
		countSqlBuffer.append(configTableName);//主表
		countSqlBuffer.append(" AS tt ");
		countSqlBuffer.append(fkBuffer);//关联表
		countSqlBuffer.append(whereBuffer);//条件
		
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sqlBuffer.toString());
		Long count = jdbcTemplate.queryForObject(countSqlBuffer.toString(), Long.class);
		LyhtPageVO pageVO = new LyhtPageVO(current - 1, null, pageSize,count,null);
		return new LyhtResultBody<>(queryForList, pageVO);
	}
	
}
