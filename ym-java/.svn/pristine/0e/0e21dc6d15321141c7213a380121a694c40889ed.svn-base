package com.lyht.business.tab.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.tab.common.enums.TabExceptionEnums;
import com.lyht.business.tab.dao.ConfigTableDao;
import com.lyht.business.tab.entity.ConfigTableEntity;
import com.lyht.business.tab.vo.DatebaseTableVO;
import com.lyht.util.Randomizer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConfigTableService {
	@Autowired
	private ConfigTableDao configTableDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Value("${lyht.database.name}")
	private String databaseName;

	/**
	 * 分页查询（表名）
	 * 
	 * @param lyhtPageVO
	 * @param configTableEntity
	 * @return
	 */
	public LyhtResultBody<List<ConfigTableEntity>> page(LyhtPageVO lyhtPageVO, ConfigTableEntity configTableEntity) {
		String configTableName = null;
		String configTableDisplayName = null;
		if (configTableEntity != null) {
			configTableName = configTableEntity.getConfigTableName();
			configTableDisplayName = configTableEntity.getConfigTableDisplayName();
		}
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<ConfigTableEntity> page = configTableDao.pageByName(configTableName, configTableDisplayName, pageable);
		List<ConfigTableEntity> content = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(content, pageVO);
	}

	/**
	 * 列表查询（表名）
	 * 
	 * @param configTableEntity
	 * @return
	 */
	public List<ConfigTableEntity> list(ConfigTableEntity configTableEntity) {
		String configTableName = null;
		String configTableDisplayName = null;
		if (configTableEntity != null) {
			configTableName = configTableEntity.getConfigTableName();
			configTableDisplayName = configTableEntity.getConfigTableDisplayName();
		}
		List<ConfigTableEntity> list = configTableDao.listByName(configTableName, configTableDisplayName);
		return list;
	}

	/**
	 * 详情查询（ID）
	 * 
	 * @param tableId
	 * @return
	 */
	public ConfigTableEntity findById(Integer tableId) {
		Optional<ConfigTableEntity> findById = configTableDao.findById(tableId);
		if (findById.isPresent()) {
			return findById.get();
		}
		return null;
	}

	/**
	 * 列表查询（表名）
	 * 
	 * @param configTableEntity
	 * @return
	 */
	public int checkUniqueByName(ConfigTableEntity configTableEntity) {
		String configTableName = null;
		if (configTableEntity != null) {
			configTableName = configTableEntity.getConfigTableName();
		}
		int count = configTableDao.checkUniqueByName(configTableName);
		return count;
	}

	/**
	 * 建表（create，有默认字段）
	 * 
	 * @param configTableEntity
	 * @return
	 */
	@Transactional
	public ConfigTableEntity createTable(ConfigTableEntity configTableEntity) {
		// 参数校验
		if (configTableEntity == null) {
			throw new LyhtRuntimeException(TabExceptionEnums.PARAM_IS_NOT_NULL);
		}
		// 表名称
		String configTableName = configTableEntity.getConfigTableName();
		if (StringUtils.isBlank(configTableName)) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NAME_IS_NOT_NULL);
		}
		// 表中文名
		String configTableDisplayName = configTableEntity.getConfigTableDisplayName();
		if (StringUtils.isBlank(configTableDisplayName)) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NAME_IS_NOT_NULL);
		}
		// 表说明
		String configTableRemark = configTableEntity.getConfigTableRemark();
		// 校验表名是否重复
		int count = checkUniqueByName(configTableEntity);// 表记录校验
		int countByTableName = countByTableName(configTableName);// 数据库校验
		if (count > 0 || countByTableName > 0) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_IS_UNIQUE);
		}

		ConfigTableEntity result = null;

		try {
			// 建表，包含指标默认字段
			jdbcTemplate.execute("drop table if exists " + configTableName + ";");
			jdbcTemplate.execute("create table " + configTableName + "("
					+ "id                   int not null auto_increment comment '主键',"
					+ "nm                   varchar(10) not null comment '内码',"
					+ "project_nm           varchar(10) comment '项目编码'," // 树状下拉框，与指标类型联动
					+ "region               varchar(10) comment '行政区'," // 树状下拉框，
					+ "status               varchar(10) comment '数据状态',"
					+ "create_time          datetime comment '创建时间',"
					+ "create_user          varchar(10) comment '创建用户',"
					+ "modify_time          datetime comment '修改时间',"
					+ "modify_user          varchar(10) comment '修改用户',"
					+ "remark               varchar(500) comment '备注',"
//					+"zblx                 varchar(10) comment '指标类型',"  // 农村、集镇
//					+ "stage                varchar(10) comment '阶段',"  // 可行性研究阶段、实施阶段
					+ "primary key (id)" + ");");
			if (StringUtils.isNotBlank(configTableRemark)) {
				jdbcTemplate.execute("alter table " + configTableName + " comment '" + configTableRemark + "';");
			}

			// 保存建表数据
			configTableEntity.setNm(Randomizer.generCode(10));
			result = configTableDao.save(configTableEntity);
		} catch (Exception e) {
			log.error("=====ConfigTableService=====Method：createTable=====Param：" + configTableEntity + "=====", e);
			throw new LyhtRuntimeException(TabExceptionEnums.CREATE_TABLE_FAILURE);
		}

		return result;
	}

	/**
	 * 修改表相关信息（表名、表显示名、是否有附件、说明）
	 * 
	 * @param configTableEntity
	 * @return
	 */
	@Transactional
	public ConfigTableEntity updateTable(ConfigTableEntity configTableEntity) {
		// 参数校验
		if (configTableEntity == null) {
			throw new LyhtRuntimeException(TabExceptionEnums.PARAM_IS_NOT_NULL);
		}
		// 表名称
		String configTableName = configTableEntity.getConfigTableName();
		if (StringUtils.isBlank(configTableName)) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NAME_IS_NOT_NULL);
		}
		// 表中文名
		String configTableDisplayName = configTableEntity.getConfigTableDisplayName();
		if (StringUtils.isBlank(configTableDisplayName)) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NAME_IS_NOT_NULL);
		}
		// ID
		Integer id = configTableEntity.getId();
		if (id == null) {
			throw new LyhtRuntimeException(TabExceptionEnums.ID_IS_NOT_NULL);
		}
		// 内码
		String nm = configTableEntity.getNm();
		if (StringUtils.isBlank(nm)) {
			throw new LyhtRuntimeException(TabExceptionEnums.NM_IS_NOT_NULL);
		}
		// 表说明
		String configTableRemark = configTableEntity.getConfigTableRemark();
		// 校验修改的表是否存在
		Optional<ConfigTableEntity> findById = configTableDao.findById(id);
		if (!findById.isPresent()) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}
		ConfigTableEntity oldTable = findById.get();
		String oldTableName = oldTable.getConfigTableName();
		// 原始表名数据库校验
		int countByOldTableName = countByTableName(oldTableName);//
		if (countByOldTableName <= 0) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}

		// 校验表名是否重复
		if (!StringUtils.equals(configTableName, oldTableName)) {
			int count = checkUniqueByName(configTableEntity);// 表记录校验
			int countByTableName = countByTableName(configTableName);// 数据库校验
			if (count > 0 || countByTableName > 0) {
				throw new LyhtRuntimeException(TabExceptionEnums.TABLE_IS_UNIQUE);
			}
		}

		ConfigTableEntity result = null;

		try {
			// 修改表名
			jdbcTemplate.execute("alter table " + oldTableName + " rename to " + configTableName + ";");
			// 修改表说明
			if (StringUtils.isNotBlank(configTableRemark)) {
				jdbcTemplate.execute("alter table " + configTableName + " comment '" + configTableRemark + "';");
			}

			// 保存建表数据
			result = configTableDao.save(configTableEntity);
		} catch (Exception e) {
			log.error("=====ConfigTableService=====Method：updateTable=====Param：" + configTableEntity + "=====", e);
			throw new LyhtRuntimeException(TabExceptionEnums.UPDATE_TABLE_FAILURE);
		}

		return result;
	}

	/**
	 * 删除表（drop）
	 * 
	 * @param configTableEntity
	 */
	@Transactional
	public void deleteTable(Integer id) {
		// ID
		if (id == null) {
			throw new LyhtRuntimeException(TabExceptionEnums.ID_IS_NOT_NULL);
		}

		// 校验修改的表是否存在
		Optional<ConfigTableEntity> findById = configTableDao.findById(id);
		if (!findById.isPresent()) {
			throw new LyhtRuntimeException(TabExceptionEnums.TABLE_NON_EXISTENT);
		}
		ConfigTableEntity oldTable = findById.get();

		String configTableName = oldTable.getConfigTableName();

		try {
			// 删除表
			jdbcTemplate.execute("drop table if exists " + configTableName + ";");
			configTableDao.deleteById(id);
		} catch (Exception e) {
			log.error("=====ConfigTableService=====Method：deleteTable=====Param：" + id + "=====", e);
			throw new LyhtRuntimeException(TabExceptionEnums.DELETE_TABLE_FAILURE);
		}
	}

	/**
	 * 查询数据库对应表是否存在
	 * 
	 * @param tableName
	 * @return
	 */
	public int countByTableName(String tableName) {
		StringBuffer sqlBuffer = new StringBuffer("SELECT count(1) FROM information_schema.tables WHERE TABLE_NAME='");
		sqlBuffer.append(tableName);
		sqlBuffer.append("' AND TABLE_SCHEMA='");
		sqlBuffer.append(databaseName);
		sqlBuffer.append("';");
		Integer count = jdbcTemplate.queryForObject(sqlBuffer.toString(), Integer.class);
		if (count == null) {
			return 0;
		}
		return count;
	}

	/**
	 * 查询数据库所有表
	 * 
	 * @return
	 */
	public List<DatebaseTableVO> listByTableName(String tableName) {
		StringBuffer sqlBuffer = new StringBuffer(
				"SELECT table_name AS tableName FROM information_schema.tables WHERE TABLE_SCHEMA='");
		sqlBuffer.append(databaseName);
		sqlBuffer.append("'");
		if (StringUtils.isNotBlank(tableName)) {
			sqlBuffer.append(" AND table_name LIKE '");
			sqlBuffer.append(tableName);
			sqlBuffer.append("%'");
		}
		List<DatebaseTableVO> queryForList = jdbcTemplate.query(sqlBuffer.toString(),
				new BeanPropertyRowMapper<DatebaseTableVO>(DatebaseTableVO.class));
		return queryForList;
	}

}
