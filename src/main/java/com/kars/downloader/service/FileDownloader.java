package com.kars.downloader.service;

import com.kars.downloader.exception.DownloadFailedException;
import com.kars.downloader.vo.FileDownloadRequestVO.URLDetailsVO;

/**
 * File Downloader interface to support multiple protocols
 * 
 * @author karthik.subbaramaiah
 *
 */
public interface FileDownloader {

	String PROTOCOL_DELIMITER = "://";

	/**
	 * 
	 * download the file from network to configured local directory
	 * 
	 * @param urlDetails urlDetails
	 * @throws DownloadFailedException - if couldn't download the file
	 */
	public void download(URLDetailsVO urlDetails) throws DownloadFailedException;

}
