package com.kars.downloader.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kars.downloader.exception.DownloadFailedException;
import com.kars.downloader.helper.FileDownloadHelper;
import com.kars.downloader.service.FileDownloader;
import com.kars.downloader.vo.FileDownloadRequestVO.URLDetailsVO;

/**
 * 
 * Http FileDownloader implementation
 * 
 * @author karthik.subbaramaiah
 *
 */
@Service
public class HttpFileDownloader implements FileDownloader {

	private Logger logger = LoggerFactory.getLogger(HttpFileDownloader.class);

	@Autowired
	private FileDownloadHelper fileDownloadHelper;

	@Value("${download.output.temp.directory}")
	private String tempDirectory;

	@Override
	public void download(URLDetailsVO urlDetails) throws DownloadFailedException {

		try {
			String downloadUrl = urlDetails.getUrl();
			String[] urlSplits = downloadUrl.split(PROTOCOL_DELIMITER);
			String saveAsFileName = urlSplits[1];

			File tempFile = new File(tempDirectory, saveAsFileName);

			if (this.resumeSupported(downloadUrl)) {
				this.downloadFileWithResume(downloadUrl, tempFile);
			} else {
				this.downloadFile(downloadUrl, tempFile);
			}

			fileDownloadHelper.moveFromTempToOutputDir(saveAsFileName);

		} catch (MalformedURLException | URISyntaxException e) {
			logger.error("Incorrect URL", e);
			throw new DownloadFailedException("Incorrect URL", e);
		} catch (IOException e) {
			logger.error("File to download file", e);
			throw new DownloadFailedException("Failed to download file", e);
		}

	}

	private boolean resumeSupported(String downloadUrl) throws IOException, URISyntaxException {
		HttpURLConnection downloadFileConnection = (HttpURLConnection) new URI(downloadUrl).toURL().openConnection();
		downloadFileConnection.setRequestProperty("Range", "bytes=0-10");
		downloadFileConnection.setRequestMethod("HEAD");
		int status = downloadFileConnection.getResponseCode();
		downloadFileConnection.disconnect();
		// Supports resumable links
		return status == 206;
	}

	private long downloadFile(String downloadUrl, File tempFile) throws IOException, URISyntaxException {
		URLConnection downloadFileConnection = new URI(downloadUrl).toURL().openConnection();
		return transferDataAndGetBytesDownloaded(downloadFileConnection, tempFile);
	}

	public long downloadFileWithResume(String downloadUrl, File tempFile) throws IOException, URISyntaxException {

		URLConnection downloadFileConnection = new URI(downloadUrl).toURL().openConnection();

		if (tempFile.exists() && downloadFileConnection instanceof HttpURLConnection) {
			HttpURLConnection httpFileConnection = (HttpURLConnection) downloadFileConnection;

			HttpURLConnection tmpFileConn = (HttpURLConnection) new URI(downloadUrl).toURL().openConnection();
			tmpFileConn.setRequestMethod("HEAD");
			long fileLength = tmpFileConn.getContentLengthLong();
			long existingFileSize = tempFile.length();

			if (existingFileSize < fileLength) {
				httpFileConnection.setRequestProperty("Range", "bytes=" + existingFileSize + "-" + fileLength);
			} else {
				throw new IOException("File Download already completed.");
			}
		}
		return transferDataAndGetBytesDownloaded(downloadFileConnection, tempFile);
	}

	private long transferDataAndGetBytesDownloaded(URLConnection downloadFileConnection, File tempFile)
			throws IOException {

		tempFile.getParentFile().mkdirs();

		long bytesDownloaded = 0;
		try (InputStream is = downloadFileConnection.getInputStream();
				OutputStream os = new FileOutputStream(tempFile, true)) {

			byte[] buffer = new byte[1024];

			int bytesCount;
			while ((bytesCount = is.read(buffer)) > 0) {
				os.write(buffer, 0, bytesCount);
				bytesDownloaded += bytesCount;
			}
		}
		return bytesDownloaded;
	}

}
