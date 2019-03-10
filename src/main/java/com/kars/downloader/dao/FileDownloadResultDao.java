package com.kars.downloader.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kars.downloader.entities.FileDownloadResultEntity;

/**
 * 
 * FileDownloadResult DAO
 * 
 * @author karthik.subbaramaiah
 *
 */
@Repository
public interface FileDownloadResultDao extends JpaRepository<FileDownloadResultEntity, Long> {

	/**
	 * 
	 * Fetch all the entities by URL list
	 * 
	 * @param urlList
	 * @return List of FileDownloadResultEntity
	 */
	List<FileDownloadResultEntity> findByUrlIn(List<String> urlList);

}
