package com.kars.downloader.service.impl;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kars.downloader.exception.DownloadFailedException;
import com.kars.downloader.helper.FTPHelper;
import com.kars.downloader.helper.FileDownloadHelper;
import com.kars.downloader.service.FileDownloader;
import com.kars.downloader.vo.FileDownloadRequestVO.URLDetailsVO;

/**
 * 
 * FTP FileDownloader implementation
 * @author karthik.subbaramaiah
 *
 */
@Service
public class FTPFileDownloader implements FileDownloader {

	private Logger logger = LoggerFactory.getLogger(FTPFileDownloader.class);

	@Autowired
	private FileDownloadHelper fileDownloadHelper;

	@Value("${download.output.temp.directory}")
	private String tempDirectory;

	@Override
	public void download(URLDetailsVO urlDetails) throws DownloadFailedException {

		String downloadUrl = urlDetails.getUrl();
		String[] urlSplits = downloadUrl.split(PROTOCOL_DELIMITER);
		String saveAsFileName = urlSplits[1];

		int index = saveAsFileName.indexOf('/');
		String serverName = saveAsFileName.substring(0, index);
		String sourcePath = saveAsFileName.substring(index + 1, saveAsFileName.length());
		File tempFile = new File(tempDirectory, saveAsFileName);

		FTPHelper ftpHelper = new FTPHelper(serverName, urlDetails.getUsername(), urlDetails.getPassword());
		try {

			ftpHelper.open();
			ftpHelper.downloadFile(sourcePath, tempFile);

			fileDownloadHelper.moveFromTempToOutputDir(saveAsFileName);

		} catch (IOException e) {
			logger.error("File to download file", e);
			throw new DownloadFailedException("Failed to download file", e);
		} finally {
			try {
				ftpHelper.close();
			} catch (IOException e) {
				logger.error("Failed to close the FTP connection");
			}
		}

	}

}
