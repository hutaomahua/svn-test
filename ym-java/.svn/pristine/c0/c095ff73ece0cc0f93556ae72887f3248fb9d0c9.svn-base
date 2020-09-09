package com.lyht.business.tab.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lyht.business.tab.entity.ConfigTableEntity;

public interface ConfigTableDao extends JpaRepository<ConfigTableEntity, Integer> {

	@Query(value = "SELECT"
			+ " tt.*"
			+ " FROM sys_config_table AS tt"
			+ " WHERE 1=1"
			+ " AND IF (:configTableName is not null, tt.config_table_name LIKE CONCAT('%',:configTableName,'%'), 1=1)"
			+ " AND IF (:configTableDisplayName is not null, tt.config_table_display_name LIKE CONCAT('%',:configTableDisplayName,'%'), 1=1)"
			+ " ORDER BY tt.id ASC", 
			countQuery = "SELECT"
					+ " COUNT(1)"
					+ " FROM sys_config_table AS tt"
					+ " WHERE 1=1"
					+ " AND IF (:configTableName is not null, tt.config_table_name LIKE CONCAT('%',:configTableName,'%'), 1=1)"
					+ " AND IF (:configTableDisplayName is not null, tt.config_table_display_name LIKE CONCAT('%',:configTableDisplayName,'%'), 1=1)",
			nativeQuery = true)
	public Page<ConfigTableEntity> pageByName(String configTableName, String configTableDisplayName, Pageable pageable);

	@Query(value = "SELECT" + " tt.*" + " FROM sys_config_table AS tt" + " WHERE 1=1 "
			+ " AND IF (:configTableName is not null, tt.config_table_name LIKE CONCAT('%',:configTableName,'%'), 1=1)"
			+ " AND IF (:configTableDisplayName is not null, tt.config_table_display_name LIKE CONCAT('%',:configTableDisplayName,'%'), 1=1)"
			+ " ORDER BY tt.id ASC", nativeQuery = true)
	public List<ConfigTableEntity> listByName(String configTableName, String configTableDisplayName);

	@Query(value = "SELECT" + " count(1)" + " FROM sys_config_table AS tt" + " WHERE 1=1 "
			+ " AND IF (:configTableName is not null, tt.config_table_name = :configTableName, 1=1)", nativeQuery = true)
	public int checkUniqueByName(String configTableName);

}
