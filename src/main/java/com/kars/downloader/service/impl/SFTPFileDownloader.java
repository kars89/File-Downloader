package com.kars.downloader.service.impl;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.kars.downloader.exception.DownloadFailedException;
import com.kars.downloader.helper.FileDownloadHelper;
import com.kars.downloader.service.FileDownloader;
import com.kars.downloader.vo.FileDownloadRequestVO.URLDetailsVO;

/**
 * 
 * SFTP FileDownloader implementation
 * 
 * @author karthik.subbaramaiah
 *
 */
@Service
public class SFTPFileDownloader implements FileDownloader {

	private Logger logger = LoggerFactory.getLogger(SFTPFileDownloader.class);

	@Autowired
	private FileDownloadHelper fileDownloadHelper;

	@Value("${download.output.temp.directory}")
	private String tempDirectory;

	@Override
	public void download(URLDetailsVO urlDetails) throws DownloadFailedException {

		String downloadUrl = urlDetails.getUrl();
		String[] urlSplits = downloadUrl.split("://");
		String saveAsFileName = urlSplits[1];

		int index = saveAsFileName.indexOf('/');
		String hostName = saveAsFileName.substring(0, index);
		String sourcePath = saveAsFileName.substring(index, saveAsFileName.length());
		String targetPath = tempDirectory + "//" + saveAsFileName;

		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(urlDetails.getUsername(), hostName, 22);
			// Not handling the authentication for TEST servers
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(urlDetails.getPassword());
			session.connect();

			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;

			new File(targetPath).getParentFile().mkdirs();

			sftpChannel.get(sourcePath, targetPath);
			sftpChannel.exit();
			session.disconnect();

			fileDownloadHelper.moveFromTempToOutputDir(saveAsFileName);

		} catch (JSchException | SftpException e) {
			logger.error("Failed to download file", e);
			throw new DownloadFailedException("Failed to download file", e);
		} catch (IOException e) {
			logger.error("Failed to move file from temp to output", e);
			throw new DownloadFailedException("File to move file from temp to output", e);
		}

	}

}
