package com.lyht.business.tab.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.tab.common.constant.TableConst;
import com.lyht.business.tab.common.enums.TabExceptionEnums;
import com.lyht.business.tab.dao.ConfigTableDao;
import com.lyht.business.tab.dao.ConfigTableFieldDao;
import com.lyht.business.tab.entity.ConfigFormEntity;
import com.lyht.business.tab.entity.ConfigSearchEntity;
import com.lyht.business.tab.entity.ConfigTableEntity;
import com.lyht.business.tab.entity.ConfigTableFieldEntity;
import com.lyht.business.tab.vo.ConfigTableFieldVO;
import com.lyht.business.tab.vo.DatebaseFiledListVO;
import com.lyht.business.tab.vo.DatebaseTableColumnVO;
import com.lyht.util.Randomizer;

@Service
public class ConfigTableFieldService {
	@Value("${lyht.root.url}")
	private String lyhtUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ConfigTableService configTableService;

	@Autowired
	private ConfigTableFieldDao configTableFieldDao;
	@Autowired
	private ConfigTableDao configTableDao;

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Value("${lyht.database.name}")
	private String databaseName;

	/**
	 * 表字段创建
	 * 
	 * @param configTableFieldEntity
	 * @return
	 */
	@Transactional
	public ConfigTableFieldEntity addTableField(ConfigTableFieldEntity configTableFieldEntity) {
		// 参数校验
		if (configTableFieldEntity == null) {
			throw new LyhtRuntimeException(TabExceptionEnums.PARAM_IS_NOT_NULL);
		}
		configTableFieldEntity.setId(null);
		configTableFieldEntity.setNm(null);
		Integer configTableId = configTableFieldEntity.getConfigTableId();// 配置表ID
		if (configTableId == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-7001", "配置表ID不能为空"));
		}
		String configFieldName = configTableFieldEntity.getConfigFieldName();// 字段名称
		if (StringUtils.isBlank(configFieldName)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-7002", "字段名称不能为空"));
		}
		String configFieldDisplayName = configTableFieldEntity.getConfigFieldDisplayName();// 字段显示名称
		if (StringUtils.isBlank(configFieldDisplayName)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-7003", "显示名称不能为空"));
		}
		String configFieldType = configTableFieldEntity.getConfigFieldType();// 字段数据类型
		if (StringUtils.isBlank(configFieldType)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-7004", "字段数据类型不能为空"));
		}
		// 表是否存在校验
		Optional<ConfigTableEntity> tableOp = configTableDao.findById(configTableId);
		if (!tableOp.isPresent()) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}
		ConfigTableEntity configTableEntity = tableOp.get();
		String configTableName = configTableEntity.getConfigTableName();// 表名
		int countByTableName = configTableService.countByTableName(configTableName);
		if (countByTableName <= 0) {// 如果表不存在于数据库，则删除对应表相关数据
			configTableService.deleteTable(configTableId);
			List<ConfigTableFieldEntity> listByTableId = listByTableId(configTableId);
			configTableFieldDao.deleteInBatch(listByTableId);
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}

		// 字段重复校验(待处理)
		int countByConfigFieldName = configTableFieldDao.countByConfigFieldNameAndConfigTableId(configFieldName,
				configTableId);// 表记录校验
		DatebaseTableColumnVO columnByName = columnByName(configFieldName, configTableName);// 数据库校验
		if (countByConfigFieldName > 0 || columnByName != null) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_FIELD_IS_UNIQUE);
		}

		Integer configFieldLength = configTableFieldEntity.getConfigFieldLength();// 字段总长度
		Integer configFieldDouble = configTableFieldEntity.getConfigFieldDouble();// 字段小数位数长度
		String configFieldDefault = configTableFieldEntity.getConfigFieldDefault();// 字段默认值
		Integer configFieldIsempty = configTableFieldEntity.getConfigFieldIsempty();// 是否为空
		String configFieldDisplayNameFujia = configTableFieldEntity.getConfigFieldDisplayNameFujia();// 字段显示名称附加信息
		Integer configFieldUnique = configTableFieldEntity.getConfigFieldUnique();// 是否唯一

		// 字段关联校验与处理
		if (StringUtils.equalsAny(configFieldType, TableConst.DATA_TYPE_VARCHAR)) {
			configFieldDouble = null;
			if (StringUtils.isNotBlank(configFieldDefault) && configFieldLength != null) {
				if (configFieldDefault.length() > configFieldLength) {
					throw new LyhtRuntimeException(TabExceptionEnums.DEFAULT_VALUE_LENGTH_TOO_LONG);
				}
			}
		} else if (StringUtils.equalsAny(configFieldType, TableConst.DATA_TYPE_INT)) {
			configFieldDouble = null;
			if (StringUtils.isNotBlank(configFieldDefault) && configFieldLength != null) {
				try {
					Integer.valueOf(configFieldDefault);
				} catch (Exception e) {
					throw new LyhtRuntimeException(TabExceptionEnums.DEFAULT_VALUE_INVALID);
				}
				if (configFieldDefault.length() > configFieldLength) {
					throw new LyhtRuntimeException(TabExceptionEnums.DEFAULT_VALUE_LENGTH_TOO_LONG);
				}
			}
		} else if (StringUtils.equalsAny(configFieldType, TableConst.DATA_TYPE_DATE, TableConst.DATA_TYPE_DATETIME)) {
			configFieldLength = null;
			configFieldDouble = null;
			configFieldDefault = null;
		} else if (StringUtils.equals(configFieldType, TableConst.DATA_TYPE_DECIMAL)) {
			if (StringUtils.isNotBlank(configFieldDefault)) {
				try {
					configFieldDefault = String.valueOf(Double.valueOf(configFieldDefault));
				} catch (Exception e) {
					throw new LyhtRuntimeException(TabExceptionEnums.DEFAULT_VALUE_INVALID);
				}
			}
			if (configFieldLength == null) {
				configFieldDouble = null;
			} else {
				if (configFieldDouble == null) {
					if (StringUtils.isNotBlank(configFieldDefault)) {
						String[] split = configFieldDefault.split("\\.");
						if (split[0].length() > configFieldLength) {
							throw new LyhtRuntimeException(TabExceptionEnums.DEFAULT_VALUE_LENGTH_TOO_LONG);
						}
						if (Integer.parseInt(split[1]) > 0) {
							throw new LyhtRuntimeException(TabExceptionEnums.DEFAULT_VALUE_LENGTH_TOO_LONG);
						}
					}
				} else {
					if (configFieldDouble >= configFieldLength) {
						throw new LyhtRuntimeException(TabExceptionEnums.DECIMAL_TOO_LONG);
					}
					if (StringUtils.isNotBlank(configFieldDefault)) {
						String[] split = configFieldDefault.split("\\.");
						if (split[0].length() > configFieldLength) {
							throw new LyhtRuntimeException(TabExceptionEnums.DEFAULT_VALUE_LENGTH_TOO_LONG);
						}
						if (split[1].length() > configFieldDouble) {
							throw new LyhtRuntimeException(TabExceptionEnums.DEFAULT_VALUE_LENGTH_TOO_LONG);
						}
					}
				}
			}
		}

		// 新增字段语句
		StringBuffer sqlBuffer = new StringBuffer("alter table ");
		sqlBuffer.append(configTableName);// 表名
		sqlBuffer.append(" add ");
		sqlBuffer.append(configFieldName);// 字段名称
		sqlBuffer.append(" ");
		sqlBuffer.append(configFieldType);// 字段类型
		// 字段长度
		if (configFieldLength != null && configFieldDouble != null && configFieldLength > 0 && configFieldDouble > 0) {
			sqlBuffer.append("(");
			sqlBuffer.append(configFieldLength);
			sqlBuffer.append(",");
			sqlBuffer.append(configFieldDouble);
			sqlBuffer.append(")");
		} else if (configFieldLength != null && configFieldLength > 0) {
			sqlBuffer.append("(");
			sqlBuffer.append(configFieldLength);
			sqlBuffer.append(")");
		}
		sqlBuffer.append(" ");
		// 字段默认值
		if (StringUtils.isNotBlank(configFieldDefault)) {
			sqlBuffer.append(" default '");
			sqlBuffer.append(configFieldDefault);
			sqlBuffer.append("' ");
		}
		// 是否为空
		if (configFieldIsempty != null && configFieldIsempty == 0) {
			sqlBuffer.append(" not null ");
		}
		// 字段描述
		sqlBuffer.append(" comment '");
		sqlBuffer.append(configFieldDisplayName);
		sqlBuffer.append(" ");
		if (StringUtils.isNotBlank(configFieldDisplayNameFujia)) {
			sqlBuffer.append(configFieldDisplayNameFujia);
		}
		sqlBuffer.append("'");

		try {
			jdbcTemplate.execute(sqlBuffer.toString());
		} catch (Exception e) {
			String message = e.getMessage();
			throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("500", message));
		}

		// 是否唯一
		if (configFieldUnique != null && configFieldUnique == 1) {
			jdbcTemplate.execute("alter table " + configTableName + " add unique(" + configFieldName + ")");
		} else {
			try {
				jdbcTemplate.execute("alter table " + configTableName + " drop index " + configFieldName + "");
			} catch (Exception e) {
			}
		}

		// 是否可查询
		Integer configFieldSearch = configTableFieldEntity.getConfigFieldSearch();
		if (configFieldSearch == null || configFieldSearch == 0) {
			configTableFieldEntity.setConfigSearchEntity(null);
		}
		// 是否可编辑
		Integer configFieldEdit = configTableFieldEntity.getConfigFieldEdit();
		if (configFieldEdit == null || configFieldEdit == 0) {
			configTableFieldEntity.setConfigFormEntitys(null);
		}

		configTableFieldEntity.setNm(Randomizer.generCode(10));
		ConfigFormEntity configFormEntitys = configTableFieldEntity.getConfigFormEntitys();
		if (configFormEntitys != null) {
			configFormEntitys.setNm(Randomizer.generCode(10));
		}
		ConfigSearchEntity configSearchEntity = configTableFieldEntity.getConfigSearchEntity();
		if (configSearchEntity != null) {
			configSearchEntity.setNm(Randomizer.generCode(10));
		}
		ConfigTableFieldEntity result = configTableFieldDao.save(configTableFieldEntity);
		entityManager.refresh(result);

		return result;
	}

	/**
	 * 表字段修改
	 * 
	 * @param configTableFieldEntity
	 * @return
	 */
	@Transactional
	public ConfigTableFieldEntity updateTableField(ConfigTableFieldEntity configTableFieldEntity) {
		// 参数校验
		if (configTableFieldEntity == null) {
			throw new LyhtRuntimeException(TabExceptionEnums.PARAM_IS_NOT_NULL);
		}
		Integer id = configTableFieldEntity.getId();// ID
		if (id == null) {
			throw new LyhtRuntimeException(TabExceptionEnums.ID_IS_NOT_NULL);
		}
		String nm = configTableFieldEntity.getNm();// 内码
		if (StringUtils.isBlank(nm)) {
			throw new LyhtRuntimeException(TabExceptionEnums.NM_IS_NOT_NULL);
		}
		Integer configTableId = configTableFieldEntity.getConfigTableId();// 配置表ID
		if (configTableId == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-7001", "配置表ID不能为空"));
		}
		String configFieldName = configTableFieldEntity.getConfigFieldName();// 字段名称
		if (StringUtils.isBlank(configFieldName)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-7002", "字段名称不能为空"));
		}
		String configFieldDisplayName = configTableFieldEntity.getConfigFieldDisplayName();// 字段显示名称
		if (StringUtils.isBlank(configFieldDisplayName)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-7003", "显示名称不能为空"));
		}
		String configFieldType = configTableFieldEntity.getConfigFieldType();// 字段数据类型
		if (StringUtils.isBlank(configFieldType)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("-7004", "字段数据类型不能为空"));
		}
		Optional<ConfigTableEntity> tableOp = configTableDao.findById(configTableId);
		if (!tableOp.isPresent()) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}
		// 表是否存在校验
		Optional<ConfigTableFieldEntity> fieldOp = configTableFieldDao.findById(id);
		if (!fieldOp.isPresent()) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_FIELD_NON_EXISTENT);
		}
		ConfigTableEntity configTableEntity = tableOp.get();
		ConfigTableFieldEntity oldTableField = fieldOp.get();
		String configTableName = configTableEntity.getConfigTableName();// 表名
		int countByTableName = configTableService.countByTableName(configTableName);
		if (countByTableName <= 0) {// 如果表不存在于数据库，则删除对应表相关数据
			configTableService.deleteTable(configTableId);
			List<ConfigTableFieldEntity> listByTableId = listByTableId(configTableId);
			configTableFieldDao.deleteInBatch(listByTableId);
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}
		// 数据库字段是否存在校验
		String oldFieldName = oldTableField.getConfigFieldName();// 原始字段
		DatebaseTableColumnVO columnByOldName = columnByName(oldFieldName, configTableName);
		if (columnByOldName == null) {// 如果表字段不存在，则删除对应字段数据
			configTableFieldDao.delete(oldTableField);
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_FIELD_NON_EXISTENT);
		}

		// 字段重复校验
		if (!StringUtils.equalsIgnoreCase(oldFieldName, configFieldName)) {
			int countByConfigFieldName = configTableFieldDao.countByConfigFieldNameAndConfigTableId(configFieldName,
					configTableId);// 表记录校验
			DatebaseTableColumnVO columnByName = columnByName(configFieldName, configTableName);// 数据库校验
			if (countByConfigFieldName > 0 || columnByName != null) {
				throw new LyhtRuntimeException(TabExceptionEnums.TABLE_FIELD_IS_UNIQUE);
			}
		}

		Integer configFieldLength = configTableFieldEntity.getConfigFieldLength();// 字段总长度
		Integer configFieldDouble = configTableFieldEntity.getConfigFieldDouble();// 字段小数位数长度
		String configFieldDefault = configTableFieldEntity.getConfigFieldDefault();// 字段默认值
		Integer configFieldIsempty = configTableFieldEntity.getConfigFieldIsempty();// 是否为空
		String configFieldDisplayNameFujia = configTableFieldEntity.getConfigFieldDisplayNameFujia();// 字段显示名称附加信息
		Integer configFieldUnique = configTableFieldEntity.getConfigFieldUnique();// 是否唯一

		// 字段关联校验与处理
		if (StringUtils.equalsAny(configFieldType, TableConst.DATA_TYPE_VARCHAR)) {
			configFieldDouble = null;
			if (StringUtils.isNotBlank(configFieldDefault) && configFieldLength != null) {
				if (configFieldDefault.length() > configFieldLength) {
					throw new LyhtRuntimeException(TabExceptionEnums.DEFAULT_VALUE_LENGTH_TOO_LONG);
				}
			}
		} else if (StringUtils.equalsAny(configFieldType, TableConst.DATA_TYPE_INT)) {
			configFieldDouble = null;
			if (StringUtils.isNotBlank(configFieldDefault) && configFieldLength != null) {
				try {
					Integer.valueOf(configFieldDefault);
				} catch (Exception e) {
					throw new LyhtRuntimeException(TabExceptionEnums.DEFAULT_VALUE_INVALID);
				}
				if (configFieldDefault.length() > configFieldLength) {
					throw new LyhtRuntimeException(TabExceptionEnums.DEFAULT_VALUE_LENGTH_TOO_LONG);
				}
			}
		} else if (StringUtils.equalsAny(configFieldType, TableConst.DATA_TYPE_DATE, TableConst.DATA_TYPE_DATETIME)) {
			configFieldLength = null;
			configFieldDouble = null;
			configFieldDefault = null;
		} else if (StringUtils.equals(configFieldType, TableConst.DATA_TYPE_DECIMAL)) {
			if (StringUtils.isNotBlank(configFieldDefault)) {
				try {
					configFieldDefault = String.valueOf(Double.valueOf(configFieldDefault));
				} catch (Exception e) {
					throw new LyhtRuntimeException(TabExceptionEnums.DEFAULT_VALUE_INVALID);
				}
			}
			if (configFieldLength == null) {
				configFieldDouble = null;
			} else {
				if (configFieldDouble == null) {
					if (StringUtils.isNotBlank(configFieldDefault)) {
						String[] split = configFieldDefault.split("\\.");
						if (split[0].length() > configFieldLength) {
							throw new LyhtRuntimeException(TabExceptionEnums.DEFAULT_VALUE_LENGTH_TOO_LONG);
						}
						if (Integer.parseInt(split[1]) > 0) {
							throw new LyhtRuntimeException(TabExceptionEnums.DEFAULT_VALUE_LENGTH_TOO_LONG);
						}
					}
				} else {
					if (configFieldDouble >= configFieldLength) {
						throw new LyhtRuntimeException(TabExceptionEnums.DECIMAL_TOO_LONG);
					}
					if (StringUtils.isNotBlank(configFieldDefault)) {
						String[] split = configFieldDefault.split("\\.");
						if (split[0].length() > configFieldLength) {
							throw new LyhtRuntimeException(TabExceptionEnums.DEFAULT_VALUE_LENGTH_TOO_LONG);
						}
						if (split[1].length() > configFieldDouble) {
							throw new LyhtRuntimeException(TabExceptionEnums.DEFAULT_VALUE_LENGTH_TOO_LONG);
						}
					}
				}
			}
		}

		// 修改字段语句
		StringBuffer sqlBuffer = new StringBuffer("alter table ");
		sqlBuffer.append(configTableName);// 表名
		sqlBuffer.append(" change ");
		sqlBuffer.append(oldFieldName);
		sqlBuffer.append(" ");
		sqlBuffer.append(configFieldName);// 字段名称
		sqlBuffer.append(" ");
		sqlBuffer.append(configFieldType);// 字段类型
		// 字段长度
		if (configFieldLength != null && configFieldDouble != null && configFieldLength > 0 && configFieldDouble > 0) {
			sqlBuffer.append("(");
			sqlBuffer.append(configFieldLength);
			sqlBuffer.append(",");
			sqlBuffer.append(configFieldDouble);
			sqlBuffer.append(")");
		} else if (configFieldLength != null && configFieldLength > 0) {
			sqlBuffer.append("(");
			sqlBuffer.append(configFieldLength);
			sqlBuffer.append(")");
		}
		sqlBuffer.append(" ");
		// 字段默认值
		if (StringUtils.isNotBlank(configFieldDefault)) {
			sqlBuffer.append(" default '");
			sqlBuffer.append(configFieldDefault);
			sqlBuffer.append("' ");
		}
		// 是否为空
		if (configFieldIsempty != null && configFieldIsempty == 0) {
			sqlBuffer.append(" not null ");
		}
		// 字段描述
		sqlBuffer.append(" comment '");
		sqlBuffer.append(configFieldDisplayName);
		sqlBuffer.append(" ");
		if (StringUtils.isNotBlank(configFieldDisplayNameFujia)) {
			sqlBuffer.append(configFieldDisplayNameFujia);
		}
		sqlBuffer.append("'");
		try {
			jdbcTemplate.execute(sqlBuffer.toString());
		} catch (Exception e) {
			String message = e.getMessage();
			throw new LyhtRuntimeException(LyhtExceptionEnums.getDynamicException("500", message));
		}

		// 是否唯一
		if (configFieldUnique != null && configFieldUnique == 1) {
			jdbcTemplate.execute("alter table " + configTableName + " add unique(" + configFieldName + ")");
		} else {
			try {
				jdbcTemplate.execute("alter table " + configTableName + " drop index " + configFieldName + "");
			} catch (Exception e) {
			}
		}

		// 是否可查询
		Integer configFieldSearch = configTableFieldEntity.getConfigFieldSearch();
		if (configFieldSearch == null || configFieldSearch == 0) {
			configTableFieldEntity.setConfigSearchEntity(null);
		}
		// 是否可编辑
		Integer configFieldEdit = configTableFieldEntity.getConfigFieldEdit();
		if (configFieldEdit == null || configFieldEdit == 0) {
			configTableFieldEntity.setConfigFormEntitys(null);
		}

		ConfigFormEntity configFormEntitys = configTableFieldEntity.getConfigFormEntitys();
		if (configFormEntitys != null) {
			if (StringUtils.isBlank(configFormEntitys.getNm())) {
				configFormEntitys.setNm(Randomizer.generCode(10));
			}
		}
		ConfigSearchEntity configSearchEntity = configTableFieldEntity.getConfigSearchEntity();
		if (configSearchEntity != null) {
			if (StringUtils.isBlank(configSearchEntity.getNm())) {
				configSearchEntity.setNm(Randomizer.generCode(10));
			}
		}

		ConfigTableFieldEntity result = configTableFieldDao.saveAndFlush(configTableFieldEntity);
		entityManager.refresh(result);

		return result;
	}

	/**
	 * 表字段删除
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public Integer deleteTableField(Integer id) {
		if (id == null) {
			throw new LyhtRuntimeException(TabExceptionEnums.ID_IS_NOT_NULL);
		}
		Optional<ConfigTableFieldEntity> fieldOp = configTableFieldDao.findById(id);
		if (!fieldOp.isPresent()) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_FIELD_NON_EXISTENT);
		}
		ConfigTableFieldEntity configTableFieldEntity = fieldOp.get();
		Integer configTableId = configTableFieldEntity.getConfigTableId();

		Optional<ConfigTableEntity> talbeOp = configTableDao.findById(configTableId);
		if (!talbeOp.isPresent()) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}

		ConfigTableEntity configTableEntity = talbeOp.get();
		String configTableName = configTableEntity.getConfigTableName();
		String configFieldName = configTableFieldEntity.getConfigFieldName();

		DatebaseTableColumnVO columnByName = columnByName(configTableFieldEntity.getConfigFieldName(), configTableName);
		if (columnByName != null) {
			jdbcTemplate.execute("alter table " + configTableName + " drop column " + configFieldName + ";");
		}

		configTableFieldDao.delete(configTableFieldEntity);
		return id;
	}

	/**
	 * 表字段查询（配置表NM）
	 * 
	 * @param configTableId
	 * @return
	 */
	public List<ConfigTableFieldEntity> listByTableId(Integer configTableId) {
		List<ConfigTableFieldEntity> resultList = configTableFieldDao.findByConfigTableId(configTableId);
		return resultList;
	}

	/**
	 * 字段分类查询（配置表NM）
	 * 
	 * @param configTableId
	 * @param stage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DatebaseFiledListVO getFiledTypeList(Integer configTableId, String stage) {

		DatebaseFiledListVO datebaseFiledListVO = new DatebaseFiledListVO();
		// 查询字段
		List<ConfigTableFieldEntity> searchList = configTableFieldDao.findBySearch(configTableId, 1);
		if (searchList != null) {
			List<ConfigTableFieldVO> parseArray = JSON.parseArray(JSON.toJSONString(searchList),
					ConfigTableFieldVO.class);
			for (ConfigTableFieldVO configTableFieldVO : parseArray) {
				ConfigSearchEntity configSearchEntity = configTableFieldVO.getConfigSearchEntity();
				if (configSearchEntity != null) {
					configSearchEntity.getConfigLoadType();
					String configSelectData = configSearchEntity.getConfigSelectData();
					if (StringUtils.isNotBlank(configSelectData)) {
						try {
							List<Map<String, Object>> selectDataList = (List<Map<String, Object>>) JSON
									.parse(configSelectData);
							configTableFieldVO.setSelectDataList(selectDataList);
						} catch (Exception e) {
						}
					} else {
						String configApiPath = configSearchEntity.getConfigApiPath();
						String configApiMethod = configSearchEntity.getConfigApiMethod();
						String configApiParam = configSearchEntity.getConfigApiParam();
						List<Map<String, Object>> formDataRequest = formDataRequest(configApiPath, configApiMethod,
								configApiParam, stage);
						configTableFieldVO.setSelectDataList(formDataRequest);
					}
				}
			}
			datebaseFiledListVO.setSearchList(parseArray);
		}
		// 列表显示字段
		List<ConfigTableFieldEntity> showList = configTableFieldDao.findByShow(configTableId, 1);
		List<ConfigTableFieldVO> showTableList = JSON.parseArray(JSON.toJSONString(showList), ConfigTableFieldVO.class);
		datebaseFiledListVO.setShowList(showTableList);
		// 编辑字段
		List<ConfigTableFieldEntity> formList = configTableFieldDao.findByEdit(configTableId, 1);
		if (formList != null) {
			List<ConfigTableFieldVO> parseArray = JSON.parseArray(JSON.toJSONString(formList),
					ConfigTableFieldVO.class);
			for (ConfigTableFieldVO configTableFieldVO : parseArray) {
				ConfigFormEntity configFormEntitys = configTableFieldVO.getConfigFormEntitys();
				if (configFormEntitys != null) {
					String configSelectData = configFormEntitys.getConfigSelectData();
					if (StringUtils.isNotBlank(configSelectData)) {
						try {
							List<Map<String, Object>> selectDataList = (List<Map<String, Object>>) JSON
									.parse(configSelectData);
							configTableFieldVO.setSelectDataList(selectDataList);
						} catch (Exception e) {
						}
					} else {
						String configApiPath = configFormEntitys.getConfigApiPath();
						String configApiMethod = configFormEntitys.getConfigApiMethod();
						String configApiParam = configFormEntitys.getConfigApiParam();
						List<Map<String, Object>> formDataRequest = formDataRequest(configApiPath, configApiMethod,
								configApiParam, stage);
						configTableFieldVO.setSelectDataList(formDataRequest);
					}
				}
			}
			datebaseFiledListVO.setFormList(parseArray);
		}
		return datebaseFiledListVO;
	}

	/**
	 * form-data请求
	 * 
	 * @param url
	 * @param method
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> formDataRequest(String url, String method, String param, String stage) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		try {
			if (!StringUtils.startsWith(url, "http")) {
				url = lyhtUrl + url;
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.set("sign", "server_request");
			Map<String, Object> map = new HashMap<>();
			if (StringUtils.isNotBlank(param)) {
				Map<String, Object> parse = (Map<String, Object>) JSON.parse(param);
				map = parse;
			}
			if (StringUtils.isNotBlank(stage)) {
				map.put("stage", stage);
			}

			MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
			Set<String> keySet = map.keySet();
			Iterator<String> iterator = keySet.iterator();
			while (iterator.hasNext()) {
				String string = iterator.next();
				postParameters.add(string, map.get(string));
			}

			HttpEntity<MultiValueMap<String, Object>> requestBody = new HttpEntity<>(postParameters, headers);
			if (StringUtils.equalsIgnoreCase(method, "post")) {
				ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, requestBody, String.class);
				String body = postForEntity.getBody();
				Map<String, Object> parse = (Map<String, Object>) JSON.parse(body);
				String object = parse.get("list").toString();
				List<Map<String, Object>> list = (List<Map<String, Object>>) JSON.parse(object);
				return list;
			} else if (StringUtils.equalsIgnoreCase(method, "get")) {
				ResponseEntity<String> getEntity = restTemplate.getForEntity(url, String.class, map);
				String body = getEntity.getBody();
				Map<String, Object> parse = (Map<String, Object>) JSON.parse(body);
				String object = parse.get("list").toString();
				List<Map<String, Object>> list = (List<Map<String, Object>>) JSON.parse(object);
				return list;
			}
		} catch (Exception e) {
			// 参数转换异常
		}
		return null;
	}

//	public static void main(String[] args) {
//		RestTemplate restTemplate = new RestTemplate();
//		String url = "http://localhost:8080/lyht/pubDictName/findPubDiveValue";
//		String param = "{\"code\":\"dict_ limits\"}";
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//		Map<String, Object> map = new HashMap<>();
//		if (StringUtils.isNotBlank(param)) {
//			Map<String, Object> parse = (Map<String, Object>) JSON.parse(param);
//			map = parse;
//		}
//		MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
//		Set<String> keySet = map.keySet();
//		Iterator<String> iterator = keySet.iterator();
//		while (iterator.hasNext()) {
//			String string = iterator.next();
//			postParameters.add(string, map.get(string));
//		}
//		
//		HttpEntity<MultiValueMap<String, Object>> requestBody = new HttpEntity<>(postParameters, headers);
//		ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, requestBody, String.class);
//		String body = postForEntity.getBody();
//		System.out.println(body);
//	}

	/**
	 * 表字段分页查询（配置表NM）
	 * 
	 * @param configTableId
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<ConfigTableFieldEntity>> pageByTableId(Integer configTableId, LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<ConfigTableFieldEntity> page = configTableFieldDao.findByConfigTableIdOrderBySortedAsc(configTableId,
				pageable);
		List<ConfigTableFieldEntity> content = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(content, pageVO);
	}

	/**
	 * 查询数据库字段信息
	 * 
	 * @param columnName
	 * @return
	 */
	public DatebaseTableColumnVO columnByName(String columnName, String tableName) {
		StringBuffer sqlBuffer = new StringBuffer(
				"SELECT table_schema AS databaseName,table_name AS tableName,column_name AS columnName,");
		sqlBuffer.append("column_default AS columnDefault,is_nullable AS isNullable,");
		sqlBuffer.append("data_type AS dataType,character_maximum_length AS stringlength,");
		sqlBuffer.append("numeric_precision AS numericPrecision,numeric_scale AS numericScale,");
		sqlBuffer.append("column_key AS columnKey,column_comment AS columnComment ");
		sqlBuffer.append("FROM information_schema.columns WHERE column_name = '");
		sqlBuffer.append(columnName);
		sqlBuffer.append("' AND table_name = '");
		sqlBuffer.append(tableName);
		sqlBuffer.append("' AND table_schema = '");
		sqlBuffer.append(databaseName);
		sqlBuffer.append("';");

		List<DatebaseTableColumnVO> queryForList = jdbcTemplate.query(sqlBuffer.toString(),
				new BeanPropertyRowMapper<DatebaseTableColumnVO>(DatebaseTableColumnVO.class));
		if (queryForList != null && !queryForList.isEmpty()) {
			return queryForList.get(0);
		}
		return null;
	}

	/**
	 * 按表名查询当前数据库所有字段信息
	 * 
	 * @param tableName
	 * @return
	 */
	public List<DatebaseTableColumnVO> listByTableName(String tableName, String fieldName) {
		StringBuffer sqlBuffer = new StringBuffer(
				"SELECT table_schema AS databaseName,table_name AS tableName,column_name AS columnName,");
		sqlBuffer.append("column_default AS columnDefault,is_nullable AS isNullable,");
		sqlBuffer.append("data_type AS dataType,character_maximum_length AS stringlength,");
		sqlBuffer.append("numeric_precision AS numericPrecision,numeric_scale AS numericScale,");
		sqlBuffer.append("column_key AS columnKey,column_comment AS columnComment ");
		sqlBuffer.append("FROM information_schema.columns WHERE table_name = '");
		sqlBuffer.append(tableName);
		sqlBuffer.append("' AND table_schema = '");
		sqlBuffer.append(databaseName);
		sqlBuffer.append("'");
		if (StringUtils.isNotBlank(fieldName)) {
			sqlBuffer.append(" AND column_name LIKE '");
			sqlBuffer.append(fieldName);
			sqlBuffer.append("%'");
		}
		List<DatebaseTableColumnVO> queryForList = jdbcTemplate.query(sqlBuffer.toString(),
				new BeanPropertyRowMapper<DatebaseTableColumnVO>(DatebaseTableColumnVO.class));
		return queryForList;
	}

}
