package com.lyht.business.pub.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.abm.paymentManagement.vo.ApplyBatchRecordVO;
import com.lyht.business.abm.paymentManagement.vo.PubFilesVO;
import com.lyht.business.pub.entity.PubFilesMergeEntity;

@Repository
public interface PubFilesMergeDao extends JpaRepository<PubFilesMergeEntity, Integer> {
	
	/**
	 * 查询多个兑付申请记录关联的附件信息
	 * @param applyNms
	 * @return
	 */
	@Query(value = "SELECT "
			+ "id 					id,"
			+ "nm					nm,"
			+ "table_name 			tableName,"
			+ "table_pk_column		tablePkColumn,"
			+ "file_name			fileName,"
			+ "file_type			fileType,"
			+ "file_size			fileSize,"
			+ "upload_staff_name	uploadStaffName,"
			+ "upload_time			uploadTime,"
			+ "file_url				fileUrl "
			+ "from pub_files "
			+ "where table_name = 'pub_files_merge' "
				+ "and table_pk_column in ("
					+ "SELECT "
					+ "DISTINCT merge.file_nm file_nm "
					+ "FROM pub_files_merge merge "
					+ "where merge.table_name = 't_abm_payment_detail' "
					+ "and merge.table_pk_column in :applyNms "
				+ ");",nativeQuery = true)
	List<PubFilesVO> findFileDetailsByApplyNms(@Param("applyNms")List<String> applyNms);

	/**
	 * 查询多个附件关联的
	 * @param fileNms
	 */
	@Query(value = "SELECT "
			+ "detail.nm				nm,"
			+ "owner.nm					ownernm,"
			+ "owner.name				ownername,"
			+ "protocol.protocolCode	protocolcode,"
			+ "protocol.protocolName	protocolname,"
			+ "protocol.protocolType	protocoltype,"
			+ "detail.apply_batch 		applybatch "
			+ "FROM t_info_owner_impl owner "
			+ "LEFT JOIN t_abm_payment_detail detail on owner.nm = detail.owner_nm "
			+ "LEFT JOIN v_protocol_finish protocol on detail.protocol_code = protocol.protocolCode and detail.protocol_type = protocol.protocolType "
			+ "WHERE "
				+ "detail.nm in ("
					+ "SELECT "
					+ "table_pk_column as nm "
					+ "from pub_files_merge "
					+ "where "
					+ "table_name = 't_abm_payment_detail' "
					+ "AND file_nm in :fileNms"
				+ ")"
	,nativeQuery = true)
	List<ApplyBatchRecordVO> findApplyDetailsByFileNms(@Param("fileNms")List<String> fileNms);
	
	/**
	 * 根据兑付批次内码查询附件
	 * @param confirmationBatchNms
	 * @return
	 */
	@Query(value = "SELECT "
			+ "id 					id,"
			+ "nm					nm,"
			+ "table_name 			tableName,"
			+ "table_pk_column		tablePkColumn,"
			+ "file_name			fileName,"
			+ "file_type			fileType,"
			+ "file_size			fileSize,"
			+ "upload_staff_name	uploadStaffName,"
			+ "upload_time			uploadTime,"
			+ "file_url				fileUrl "
			+ "from pub_files "
			+ "where table_name = 'pub_files_merge' "
				+ "and table_pk_column in ("
					+ "SELECT "
					+ "DISTINCT merge.file_nm file_nm "
					+ "FROM pub_files_merge merge "
					+ "where merge.table_name = 't_abm_payment_detail' "
					+ "and merge.table_pk_column in ("
						+ "SELECT "
							+ "nm "
						+ "from t_abm_payment_detail detail "
						+ "where detail.confirmation_batch in :confirmationBatchNms"
					+ ") "
				+ ");",nativeQuery = true)
	List<PubFilesVO> findFileDetailsByConfirmationBatchNms(@Param("confirmationBatchNms")List<String> confirmationBatchNms);

	/**
	 * 根据附件内码删除
	 * @param fileNm
	 */
	@Modifying
	@Query(value = "delete from pub_files_merge where file_nm = :fileNm",nativeQuery = true)
	Integer deleteByFileNm(@Param("fileNm")String fileNm);
	
	/**
	 * 根据附件关联码与业务关联码解除附件关联
	 * @param fileTablePkColumn
	 * @param mergeTablePkColumn
	 * @return
	 */
	@Modifying
	@Query(value = "DELETE from pub_files_merge WHERE table_name = 't_abm_payment_detail' and file_nm = :fileTablePkColumn and table_pk_column = :mergeTablePkColumn",nativeQuery = true)
	Integer delMerge(@Param("fileTablePkColumn")String fileTablePkColumn, @Param("mergeTablePkColumn")String mergeTablePkColumn);

	@Query(value = "FROM PubFilesMergeEntity WHERE tableName = :tableName and fileNm = :fileTablePkColumn")
	List<PubFilesMergeEntity> findByTableNameAndTablePkColumn(@Param("tableName")String tableName, @Param("fileTablePkColumn")String fileTablePkColumn);

	@Query(value = "SELECT "
			+ "DISTINCT table_pk_column "
			+ "FROM pub_files_merge where file_nm in "
				+ "(SELECT nm from t_abm_payment_detail where confirmation_batch = :confirmationBatch );",nativeQuery = true)
	List<String> findFileMergeTablePKColumnByConfirmationBatch(String confirmationBatch);

}
