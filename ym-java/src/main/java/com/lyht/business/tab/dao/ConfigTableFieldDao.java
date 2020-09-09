package com.lyht.business.tab.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.tab.entity.ConfigTableFieldEntity;

public interface ConfigTableFieldDao extends JpaRepository<ConfigTableFieldEntity, Integer> {
	
	@Query(value = "SELECT * FROM sys_config_table_field "
			+ " WHERE config_table_id = :configTableId"
			+ " ORDER BY sorted IS NULL, sorted ASC", nativeQuery = true)
	public List<ConfigTableFieldEntity> findByConfigTableId(@Param(value = "configTableId") Integer configTableId);
	
	public Page<ConfigTableFieldEntity> findByConfigTableIdOrderBySortedAsc(Integer configTableId, Pageable pageable);
	
	public int countByConfigFieldNameAndConfigTableId(String configFieldName, Integer configTableId);

	@Query(value = "SELECT * FROM sys_config_table_field "
			+ " WHERE config_table_id = :configTableId"
			+ " AND config_field_search = :configFieldSearch"
			+ " ORDER BY sorted IS NULL, sorted ASC", nativeQuery = true)
	public List<ConfigTableFieldEntity> findBySearch(@Param(value = "configTableId") Integer configTableId, @Param(value = "configFieldSearch") Integer configFieldSearch);
	
	@Query(value = "SELECT * FROM sys_config_table_field "
			+ " WHERE config_table_id = :configTableId"
			+ " AND config_field_show = :configFieldShow"
			+ " ORDER BY sorted IS NULL, sorted ASC", nativeQuery = true)
	public List<ConfigTableFieldEntity> findByShow(@Param(value = "configTableId") Integer configTableId, @Param(value = "configFieldShow") Integer configFieldShow);
	
	@Query(value = "SELECT * FROM sys_config_table_field "
			+ " WHERE config_table_id = :configTableId"
			+ " AND config_field_edit = :configFieldEdit"
			+ " ORDER BY sorted IS NULL, sorted ASC", nativeQuery = true)
	public List<ConfigTableFieldEntity> findByEdit(@Param(value = "configTableId") Integer configTableId, @Param(value = "configFieldEdit") Integer configFieldEdit);
	
}