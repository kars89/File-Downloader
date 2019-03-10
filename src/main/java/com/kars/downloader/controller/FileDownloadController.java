package com.kars.downloader.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kars.downloader.service.FileDownloadService;
import com.kars.downloader.vo.FileDownloadResultResponseVO;
import com.kars.downloader.vo.FileDownloadRequestVO;

/**
 * File Download Resource
 * 
 * @author karthik.subbaramaiah
 *
 */
@RestController
@RequestMapping("/files")
public class FileDownloadController {

	@Autowired
	public FileDownloadService fileDownloadService;

	@PostMapping("/download")
	public String downloadFiles(@RequestBody FileDownloadRequestVO request) {
		fileDownloadService.downloadFiles(request);
		return "Request Received: Use /fileDownloader/files/status url to check the status of all the requests";
	}

	@GetMapping("/status")
	public List<FileDownloadResultResponseVO> getDownloadStatus(@RequestParam(required = false) List<String> urlList) {
		return fileDownloadService.getDownloadStatus(urlList);
	}

}
