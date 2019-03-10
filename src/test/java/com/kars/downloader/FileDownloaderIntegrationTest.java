package com.kars.downloader;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.kars.downloader.dao.FileDownloadResultDao;
import com.kars.downloader.entities.FileDownloadResultEntity;
import com.kars.downloader.enums.DownloadStatus;
import com.kars.downloader.service.impl.FileDownloadServiceImpl;
import com.kars.downloader.vo.FileDownloadRequestVO;
import com.kars.downloader.vo.FileDownloadRequestVO.URLDetailsVO;
import com.kars.downloader.vo.FileDownloadResultResponseVO;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@SpringBootTest
public class FileDownloaderIntegrationTest {

	private Logger logger = LoggerFactory.getLogger(FileDownloaderIntegrationTest.class);

	@Autowired
	private FileDownloadServiceImpl fileDownloadService;

	@Autowired
	private FileDownloadResultDao fileDownloadResultDao;

	@Test
	public void test_unsupportedProtocol() throws Exception {
		URLDetailsVO invalidUrl = new URLDetailsVO("abcd://www.imks.lp/lk/io");
		CompletableFuture<Long> future = fileDownloadService.downloadFileAsync(invalidUrl);
		Long resultId = future.get();
		Optional<FileDownloadResultEntity> result = fileDownloadResultDao.findById(resultId);
		assertTrue(result.isPresent() && result.get().getDownloadStatus() == DownloadStatus.FAILED);
	}

	@Test
	public void test_downloadHttpFileAsync() throws Exception {
		URLDetailsVO httpUrlDetails = new URLDetailsVO(
				"https://upload.wikimedia.org/wikipedia/commons/f/ff/Pizigani_1367_Chart_10MB.jpg");
		CompletableFuture<Long> future = fileDownloadService.downloadFileAsync(httpUrlDetails);
		Long resultId = future.get();
		Optional<FileDownloadResultEntity> result = fileDownloadResultDao.findById(resultId);
		assertTrue(result.isPresent() && result.get().getDownloadStatus() == DownloadStatus.COMPLETED);
	}

	@Test
	public void test_downloadFtpFileAsync() throws Exception {
		URLDetailsVO ftpUrlDetails = new URLDetailsVO("ftp://speedtest.tele2.net/3MB.zip");
		CompletableFuture<Long> future = fileDownloadService.downloadFileAsync(ftpUrlDetails);
		Long resultId = future.get();
		Optional<FileDownloadResultEntity> result = fileDownloadResultDao.findById(resultId);
		assertTrue(result.isPresent() && result.get().getDownloadStatus() == DownloadStatus.COMPLETED);
	}

	@Test
	public void test_downloadSftpFileAsync() throws Exception {
		URLDetailsVO sftpUrlDetails = new URLDetailsVO("sftp://test.rebex.net/pub/example/WinFormClient.png", "demo",
				"password");
		CompletableFuture<Long> future = fileDownloadService.downloadFileAsync(sftpUrlDetails);
		Long resultId = future.get();
		Optional<FileDownloadResultEntity> result = fileDownloadResultDao.findById(resultId);
		assertTrue(result.isPresent() && result.get().getDownloadStatus() == DownloadStatus.COMPLETED);
	}

	@Test
	@Ignore
	public void test_downloadAllFiles() throws Exception {

		FileDownloadRequestVO fileDownloadRequestVO = new FileDownloadRequestVO();
		List<URLDetailsVO> uRLDetailsVOList = new ArrayList<>();

		URLDetailsVO ftpUrlDetails = new URLDetailsVO("ftp://speedtest.tele2.net/3MB.zip");
		URLDetailsVO httpUrlDetails = new URLDetailsVO(
				"https://upload.wikimedia.org/wikipedia/commons/f/ff/Pizigani_1367_Chart_10MB.jpg");
		URLDetailsVO sftpUrlDetails = new URLDetailsVO("sftp://test.rebex.net/pub/example/WinFormClient.png", "demo",
				"password");

		uRLDetailsVOList.add(ftpUrlDetails);
		uRLDetailsVOList.add(httpUrlDetails);
		uRLDetailsVOList.add(sftpUrlDetails);

		fileDownloadRequestVO.setUrlDetails(uRLDetailsVOList);

		fileDownloadService.downloadFiles(fileDownloadRequestVO);

		Set<DownloadStatus> finalStates = new HashSet<>(Arrays.asList(DownloadStatus.COMPLETED, DownloadStatus.FAILED));
		while (true) {
			Thread.sleep(20000);
			List<FileDownloadResultResponseVO> objList = fileDownloadService.getDownloadStatus(null);
			logger.info("{}", objList);
			List<FileDownloadResultResponseVO> uncompletedDownloads = objList.stream()
					.filter(f -> !finalStates.contains(f.getDownloadStatus())).collect(Collectors.toList());
			if (CollectionUtils.isEmpty(uncompletedDownloads)) {
				break;
			}
		}

	}

}
