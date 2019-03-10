package com.kars.downloader.service;

import java.util.List;

import com.kars.downloader.vo.FileDownloadResultResponseVO;
import com.kars.downloader.vo.FileDownloadRequestVO;

/**
 * 
 * File Download Service
 * 
 * @author karthik.subbaramaiah
 *
 */
public interface FileDownloadService {

	/**
	 * Downloads File from network to local
	 * 
	 * @param request request containing url details
	 */
	public void downloadFiles(FileDownloadRequestVO request);

	/**
	 * Returns the status of the download
	 * 
	 * @param urlList list of URLs for which status is required; if empty everything
	 *                is returned
	 * @return list of FileDownloadResultResponseVO
	 */
	public List<FileDownloadResultResponseVO> getDownloadStatus(List<String> urlList);

}
