package com.kars.downloader.service.adapter;

import org.springframework.stereotype.Service;

import com.kars.downloader.entities.FileDownloadResultEntity;
import com.kars.downloader.service.Adapter;
import com.kars.downloader.vo.FileDownloadResultResponseVO;

/**
 * FileDownloadResultAdapter class to convert from entity to response
 * 
 * @author karthik.subbaramaiah
 *
 */
@Service
public class FileDownloadResultAdapter implements Adapter<FileDownloadResultEntity, FileDownloadResultResponseVO> {

	@Override
	public FileDownloadResultResponseVO adaptFrom(FileDownloadResultEntity fileDownloadResultEntity) {
		FileDownloadResultResponseVO fileDownloadResultResponseVO = new FileDownloadResultResponseVO();
		fileDownloadResultResponseVO.setUrl(fileDownloadResultEntity.getUrl());
		fileDownloadResultResponseVO.setDownloadStatus(fileDownloadResultEntity.getDownloadStatus());
		fileDownloadResultResponseVO.setRetryAttempt(fileDownloadResultEntity.getRetryAttempt());
		fileDownloadResultResponseVO.setRemarks(fileDownloadResultEntity.getRemarks());
		return fileDownloadResultResponseVO;
	}

	@Override
	public FileDownloadResultEntity adaptTo(FileDownloadResultResponseVO obj) {
		// TODO: add later if required
		return null;
	}

}
