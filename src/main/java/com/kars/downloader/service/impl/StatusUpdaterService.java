package com.kars.downloader.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kars.downloader.dao.FileDownloadResultDao;
import com.kars.downloader.entities.FileDownloadResultEntity;
import com.kars.downloader.enums.DownloadStatus;

/**
 * Service to update the status of the file download
 * 
 * @author karthik.subbaramaiah
 *
 */
@Service
public class StatusUpdaterService {

	@Autowired
	private FileDownloadResultDao fileDownloadResultDao;

	/**
	 * download is queued
	 * 
	 * @param url - url
	 * @return resultId
	 */
	public Long initialize(String url) {
		FileDownloadResultEntity fileDownloadResultEntity = new FileDownloadResultEntity();
		fileDownloadResultEntity.setUrl(url);
		fileDownloadResultEntity.setDownloadStatus(DownloadStatus.STARTED);
		return fileDownloadResultDao.save(fileDownloadResultEntity).getId();
	}

	/**
	 * download is in process
	 * 
	 * @param resultId   - resultId
	 * @param retryCount - count of retry
	 */
	public void inProgess(Long resultId, int retryCount) {
		Optional<FileDownloadResultEntity> fileDownloadResultEntityOpt = fileDownloadResultDao.findById(resultId);
		if (fileDownloadResultEntityOpt.isPresent()) {
			FileDownloadResultEntity fileDownloadResultEntity = fileDownloadResultEntityOpt.get();
			fileDownloadResultEntity.setRetryAttempt(retryCount);
			fileDownloadResultEntity.setDownloadStatus(DownloadStatus.IN_PROGRESS);
			fileDownloadResultDao.save(fileDownloadResultEntity);
		}
	}

	/**
	 * download successful
	 * 
	 * @param resultId - entityId
	 */
	public void success(Long resultId) {
		Optional<FileDownloadResultEntity> fileDownloadResultEntityOpt = fileDownloadResultDao.findById(resultId);
		if (fileDownloadResultEntityOpt.isPresent()) {
			FileDownloadResultEntity fileDownloadResultEntity = fileDownloadResultEntityOpt.get();
			fileDownloadResultEntity.setDownloadStatus(DownloadStatus.COMPLETED);
			fileDownloadResultDao.save(fileDownloadResultEntity);
		}
	}

	/**
	 * download failed
	 * 
	 * @param resultId - entityId
	 * @param remarks  - reason for failure
	 */
	public void failed(Long resultId, String remarks) {
		Optional<FileDownloadResultEntity> fileDownloadResultEntityOpt = fileDownloadResultDao.findById(resultId);
		if (fileDownloadResultEntityOpt.isPresent()) {
			FileDownloadResultEntity fileDownloadResultEntity = fileDownloadResultEntityOpt.get();
			fileDownloadResultEntity.setDownloadStatus(DownloadStatus.FAILED);
			if (remarks != null && remarks.length() > 255) {
				remarks = remarks.substring(0, 255);
				remarks.intern();
			}
			fileDownloadResultEntity.setRemarks(remarks);
			fileDownloadResultDao.save(fileDownloadResultEntity);
		}

	}

}
