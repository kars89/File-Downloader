package com.kars.downloader.service.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kars.downloader.dao.FileDownloadResultDao;
import com.kars.downloader.entities.FileDownloadResultEntity;
import com.kars.downloader.enums.Protocol;
import com.kars.downloader.exception.DownloadFailedException;
import com.kars.downloader.exception.ValidationException;
import com.kars.downloader.factory.FileDownloaderFactory;
import com.kars.downloader.helper.FileDownloadHelper;
import com.kars.downloader.service.FileDownloadService;
import com.kars.downloader.service.FileDownloader;
import com.kars.downloader.service.adapter.FileDownloadResultAdapter;
import com.kars.downloader.vo.FileDownloadResultResponseVO;
import com.kars.downloader.vo.FileDownloadRequestVO;
import com.kars.downloader.vo.FileDownloadRequestVO.URLDetailsVO;

/**
 * 
 * Implementation of FileDownloadService
 * 
 * @author karthik.subbaramaiah
 *
 */
@Service
public class FileDownloadServiceImpl implements FileDownloadService {

	private Logger logger = LoggerFactory.getLogger(FileDownloadServiceImpl.class);

	@Autowired
	private StatusUpdaterService statusUpdaterService;

	@Autowired
	private FileDownloaderFactory fileDownloaderFactory;

	@Autowired
	private FileDownloadHelper fileDownloadHelper;

	@Autowired
	private FileDownloadResultDao fileDownloadResultDao;

	@Autowired
	private FileDownloadResultAdapter fileDownloadResultAdapter;

	@Autowired
	private RetryTemplate retryTemplate;

	@Autowired
	private TaskExecutor taskExecutor;

	@Override
	public void downloadFiles(FileDownloadRequestVO request) {

		for (URLDetailsVO urlDetails : request.getUrlDetails()) {
			this.downloadFileAsync(urlDetails);
		}
	}

	/**
	 * Asynchronously downloads the file
	 * 
	 * @param urlDetails - details of the netwrok URL
	 * @return CompletableFuture
	 */
	@Async("file_download_task_executor")
	public CompletableFuture<Void> downloadFileAsync(URLDetailsVO urlDetails) {
		return CompletableFuture.runAsync(() -> {

			String url = urlDetails.getUrl();

			final Long resultId = statusUpdaterService.initialize(url);

			try {
				Protocol protocol = fileDownloadHelper.getProtocol(url);
				FileDownloader fileDownloader = fileDownloaderFactory.getFileDownloader(protocol);

				retryTemplate.execute(context -> {
					statusUpdaterService.inProgess(resultId, context.getRetryCount());
					if (context.getRetryCount() > 0) {
						logger.info("Retry {} - trying to download again", context.getRetryCount());
					}
					fileDownloader.download(urlDetails);
					return null;
				});

				statusUpdaterService.success(resultId);

			} catch (ValidationException e) {
				logger.error(e.getErrors().toString(), e);
				statusUpdaterService.failed(resultId, e.getErrors().toString());
			} catch (DownloadFailedException e) {
				logger.error("Failed to download the file", e);
				statusUpdaterService.failed(resultId, e.getMessage());
			} catch (Exception e) {
				logger.error("Exception Occurred", e);
				statusUpdaterService.failed(resultId, e.getMessage());
			}
		}, taskExecutor);

	}

	@Override
	public List<FileDownloadResultResponseVO> getDownloadStatus(List<String> urlList) {
		List<FileDownloadResultEntity> fileDownloadResultList = null;
		if (CollectionUtils.isEmpty(urlList)) {
			fileDownloadResultList = fileDownloadResultDao.findAll();
		} else {
			fileDownloadResultList = fileDownloadResultDao.findByUrlIn(urlList);
		}
		return fileDownloadResultList.stream().map(e -> fileDownloadResultAdapter.adaptFrom(e))
				.collect(Collectors.toList());
	}

}
