package com.lyht.business.pub.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lyht.business.pub.entity.PubFilesEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PubFilesDao extends JpaRepository<PubFilesEntity, Integer> {
	
	/**
	 * 根据关联内码删除
	 * @param nm
	 */
	@Modifying
	@Query(value = "delete from pub_files where table_pk_column = ?1",nativeQuery = true)
	Integer deleteBytablePkColumn(String nm);
	
	/**
	 * 多条件查询
	 * @param spc
	 * @param pageable
	 * @return
	 */
	List<PubFilesEntity> findAll(Specification<PubFilesEntity> spc);
	
	/**
	 * 多条件查询
	 * @param spc
	 * @param pageable
	 * @return
	 */
	Page<PubFilesEntity> findAll(Specification<PubFilesEntity> spc, Pageable pageable);
	
	/**
	 * 查询文件个数
	 * @param tablePkColumn
	 * @return
	 */
	int countByTablePkColumn(String tablePkColumn);

	List<PubFilesEntity> findByTablePkColumn(String tab);
	
	/**
	 * 根据内码查询
	 * @param nm
	 */
	Optional<PubFilesEntity> findByNm(String nm);
}
