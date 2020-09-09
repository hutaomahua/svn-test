package com.lyht.business.pub.service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.pub.dao.PubFilesDao;
import com.lyht.business.pub.entity.PubFilesEntity;
import com.lyht.business.pub.enums.PubExceptionEnums;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;
import com.lyht.util.SystemUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * 附件
 * 
 * @author hxl
 *
 */
@Service
public class PubFilesService {
	private Logger log = LoggerFactory.getLogger(PubFilesService.class);

	@Autowired
	private PubFilesDao pubFilesDao;

	@Value("${lyht.file.upload.path}")
	private String filePath;

	/**
	 * 文件上传(单个)
	 * 
	 * @param request
	 * @param files
	 * @param pubFileEntity
	 */
	public PubFilesEntity upload(HttpServletRequest request, MultipartFile multipartFile,
			PubFilesEntity pubFileEntity) {
		try {
			String originalFilename = multipartFile.getOriginalFilename();// 文件全名
			long size = multipartFile.getSize();// 文件大小
			String nm = Randomizer.generCode(10);// 内码
			String fileName = originalFilename.substring(0, originalFilename.lastIndexOf(".")) + "_" + nm;// 文件名
			String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);// 文件类型
			String dateString = DateFormatUtils.format(Calendar.getInstance(), "yyyyMM");// 当前年月字符串
			StringBuffer fileUrl = new StringBuffer();// 文件路径
			fileUrl.append("/upload/");
			fileUrl.append(fileType);
			fileUrl.append("/");
			fileUrl.append(dateString);

			// 创建文件夹以及写入文件
			File fileDir = new File(filePath + fileUrl.toString());
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}

			// 保存文件
			fileUrl.append("/");
			fileUrl.append(fileName);
			fileUrl.append(".");
			fileUrl.append(fileType);
			File file = new File(filePath + fileUrl.toString());
			multipartFile.transferTo(file);

			// 附件详情
			PubFilesEntity fileEntity = new PubFilesEntity();
			fileEntity.setFileName(originalFilename.substring(0, originalFilename.lastIndexOf(".")));
			fileEntity.setFileSize(String.valueOf(size));
			fileEntity.setFileType(fileType);
			fileEntity.setFileUrl(fileUrl.toString());
			fileEntity.setNm(nm);
			fileEntity.setTableName(pubFileEntity.getTableName());
			fileEntity.setTablePkColumn(pubFileEntity.getTablePkColumn());
			fileEntity.setUploadStaffName(SystemUtil.getLoginStaffName(request));
			fileEntity.setUploadTime(DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));
			fileEntity.setSubject(pubFileEntity.getSubject());
			PubFilesEntity save = pubFilesDao.save(fileEntity);
			return save;
		} catch (Exception e) {
			log.error("=====PubFilesService=====Method=uploads=====Params:" + pubFileEntity + "=====", e);
			throw new LyhtRuntimeException(PubExceptionEnums.FILE_UPLOAD_FAILURE);
		}
	}

	/**
	 * 文件上传(多个)
	 * 
	 * @param request
	 * @param files
	 * @param pubFileEntity
	 */
	public LyhtResultBody<List<PubFilesEntity>> uploads(HttpServletRequest request, MultipartFile[] files,
			PubFilesEntity pubFileEntity) {
		try {
			List<PubFilesEntity> pubList = new ArrayList<>();
			for (MultipartFile multipartFile : files) {
				String originalFilename = multipartFile.getOriginalFilename();// 文件全名
				long size = multipartFile.getSize();// 文件大小
				String nm = Randomizer.generCode(10);// 内码
				String fileName = originalFilename.substring(0, originalFilename.lastIndexOf(".")) + "_" + nm;// 文件名
				String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);// 文件类型
				String dateString = DateFormatUtils.format(Calendar.getInstance(), "yyyyMM");// 当前年月字符串
				StringBuffer fileUrl = new StringBuffer();// 文件路径
				fileUrl.append("/upload/");
				fileUrl.append(fileType);
				fileUrl.append("/");
				fileUrl.append(dateString);

				// 创建文件夹以及写入文件
				File fileDir = new File(filePath + fileUrl.toString());
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}

				// 保存文件
				fileUrl.append("/");
				fileUrl.append(fileName);
				fileUrl.append(".");
				fileUrl.append(fileType);
				File file = new File(filePath + fileUrl.toString());
				multipartFile.transferTo(file);

				// 附件详情
				PubFilesEntity fileEntity = new PubFilesEntity();
				fileEntity.setFileName(originalFilename.substring(0, originalFilename.lastIndexOf(".")));
				fileEntity.setFileSize(String.valueOf(size));
				fileEntity.setFileType(fileType);
				fileEntity.setFileUrl(fileUrl.toString());
				fileEntity.setNm(nm);
				fileEntity.setTableName(pubFileEntity.getTableName());
				fileEntity.setTablePkColumn(pubFileEntity.getTablePkColumn());
				fileEntity.setUploadStaffName(SystemUtil.getLoginStaffName(request));
				fileEntity.setUploadTime(DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));
				fileEntity.setSubject(pubFileEntity.getSubject());
				pubList.add(fileEntity);
			}
			List<PubFilesEntity> save = pubFilesDao.saveAll(pubList);
			return new LyhtResultBody<List<PubFilesEntity>>(save);
		} catch (Exception e) {
			log.error("=====PubFilesService=====Method=uploads=====Params:" + pubFileEntity + "=====", e);
			throw new LyhtRuntimeException(PubExceptionEnums.FILE_UPLOAD_FAILURE);
		}
	}

	/**
	 * 单个删除
	 * 
	 * @param id
	 */
	public LyhtResultBody<Integer> delete(HttpServletRequest request, Integer id) {
		try {
			Optional<PubFilesEntity> findById = pubFilesDao.findById(id);
			PubFilesEntity pubFileEntity = findById.get();
			pubFilesDao.deleteById(id);
			File file = new File(filePath + pubFileEntity.getFileUrl());
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			log.error("=====PubFilesService=====Method=delete=====Params:" + id + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<Integer>(id);
	}

	/**
	 * 批量删除
	 * 
	 * @param request
	 * @param ids
	 * @return
	 */
	public LyhtResultBody<String> batchDel(HttpServletRequest request, String ids) {
		try {
			// 删除记录
			List<Integer> idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
			List<PubFilesEntity> pubFileEntityList = pubFilesDao.findAllById(idList);
			pubFilesDao.deleteInBatch(pubFileEntityList);
			// 删除文件
			for (PubFilesEntity pubFileEntity : pubFileEntityList) {
				File file = new File(filePath + pubFileEntity.getFileUrl());
				if (file.exists()) {
					file.delete();
				}
			}
		} catch (Exception e) {
			log.error("=====PubFilesService=====Method=batchDel=====Params:" + ids + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(ids);
	}

	/**
	 * 单个附件详情
	 * 
	 * @param id
	 * @return
	 */
	public LyhtResultBody<PubFilesEntity> findById(Integer id) {
		Optional<PubFilesEntity> findById = pubFilesDao.findById(id);
		if (findById.isPresent()) {
			return new LyhtResultBody<>(findById.get());
		}
		return new LyhtResultBody<>();
	}

	/**
	 * 查询多个id对应的附件详情
	 * 
	 * @param ids
	 * @return
	 */
	public LyhtResultBody<List<PubFilesEntity>> findByIds(String ids) {
		List<PubFilesEntity> list = null;
		try {
			List<Integer> idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
			list = pubFilesDao.findAllById(idList);
		} catch (Exception e) {
			log.error("=====PubFilesService=====Method=findByIds=====Params:" + ids + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(list);
	}

	/**
	 * 列表
	 * 
	 * @return
	 */
	public LyhtResultBody<List<PubFilesEntity>> list(PubFilesEntity pubFileEntity) {
		// 分页条件查询
		List<PubFilesEntity> list = pubFilesDao.findAll(new Specification<PubFilesEntity>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<PubFilesEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (pubFileEntity != null) {
					// 关联表名
					String tableName = pubFileEntity.getTableName();
					if (StringUtils.isNotBlank(tableName)) {
						predicates.add(cb.equal(root.get("tableName").as(String.class), tableName));
					}
					// 关联表属性
					String tablePkColumn = pubFileEntity.getTablePkColumn();
					if (StringUtils.isNotBlank(tablePkColumn)) {
						predicates.add(cb.equal(root.get("tablePkColumn").as(String.class), tablePkColumn));
					}
					// 附件分类
					String subject = pubFileEntity.getSubject();
					if (StringUtils.isNotBlank(subject)) {
						predicates.add(cb.equal(root.get("subject").as(String.class), subject));
					}
				}
				Predicate[] pres = new Predicate[predicates.size()];
				query.where(predicates.toArray(pres));
				return cb.and(predicates.toArray(pres));
			}
		});
		// 结构集
		return new LyhtResultBody<>(list);
	}

	/**
	 * 分页查询
	 * 
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<PubFilesEntity>> page(LyhtPageVO lyhtPageVO, PubFilesEntity pubFileEntity) {
		// 分页条件查询
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<PubFilesEntity> page = pubFilesDao.findAll(new Specification<PubFilesEntity>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<PubFilesEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (pubFileEntity != null) {
					// 关联表名
					String tableName = pubFileEntity.getTableName();
					if (StringUtils.isNotBlank(tableName)) {
						predicates.add(cb.equal(root.get("tableName").as(String.class), tableName));
					}
					// 关联表属性
					String tablePkColumn = pubFileEntity.getTablePkColumn();
					if (StringUtils.isNotBlank(tablePkColumn)) {
						predicates.add(cb.equal(root.get("tablePkColumn").as(String.class), tablePkColumn));
					}
					// 附件分类
					String subject = pubFileEntity.getSubject();
					if (StringUtils.isNotBlank(subject)) {
						predicates.add(cb.equal(root.get("subject").as(String.class), subject));
					}
				}
				Predicate[] pres = new Predicate[predicates.size()];
				query.where(predicates.toArray(pres));
				return cb.and(predicates.toArray(pres));
			}
		}, pageable);
		// 结构集
		List<PubFilesEntity> content = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(content, pageVO);
	}

	/**
	 * 查询文件个数
	 * 
	 * @param pubFileEntity
	 * @return
	 */
	public LyhtResultBody<Integer> count(PubFilesEntity pubFileEntity) {
		String tablePkColumn = pubFileEntity.getTablePkColumn();
		if (StringUtils.isBlank(tablePkColumn)) {
			return new LyhtResultBody<>(0);
		}
		Integer countByTablePkColumn = pubFilesDao.countByTablePkColumn(tablePkColumn);
		return new LyhtResultBody<>(countByTablePkColumn);
	}

	@Transactional
	public Integer deleteBytablePkColumn(HttpServletRequest request, String nm) {
		List<PubFilesEntity> fileList = pubFilesDao.findByTablePkColumn(nm);
		Integer num = pubFilesDao.deleteBytablePkColumn(nm);
		// 删除文件
		for (PubFilesEntity pubFilesEntity : fileList) {
			File file = new File(filePath + pubFilesEntity.getFileUrl());
			if (file.exists()) {
				file.delete();
			}
		}
		return num;
	}

	@Transactional
	public Integer deleteBytablePkColumn(String nm) {
		List<PubFilesEntity> fileList = pubFilesDao.findByTablePkColumn(nm);
		Integer num = pubFilesDao.deleteBytablePkColumn(nm);
		// 删除文件
		for (PubFilesEntity pubFilesEntity : fileList) {
			File file = new File(filePath + pubFilesEntity.getFileUrl());
			if (file.exists()) {
				file.delete();
			}
		}
		return num;
	}

}
