package com.kars.downloader.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kars.downloader.enums.Errors;
import com.kars.downloader.enums.Protocol;
import com.kars.downloader.exception.InvalidDataException;
import com.kars.downloader.service.FileDownloader;
import com.kars.downloader.service.impl.FTPFileDownloader;
import com.kars.downloader.service.impl.HttpFileDownloader;
import com.kars.downloader.service.impl.SFTPFileDownloader;

/**
 * 
 * FileDownloaderFactory contains different implementations of protocol
 * downloader
 * 
 * @author karthik.subbaramaiah
 *
 */
@Component
public class FileDownloaderFactory {

	@Autowired
	private FTPFileDownloader ftpFileDownloader;

	@Autowired
	private SFTPFileDownloader sftpFileDownloader;

	@Autowired
	private HttpFileDownloader httpFileDownloader;

	/**
	 * returns the fileDownloader based on the protocol
	 * 
	 * @param protocol
	 * @return FileDownloader
	 */
	public FileDownloader getFileDownloader(Protocol protocol) {
		switch (protocol) {
		case FTP:
			return ftpFileDownloader;
		case SFTP:
			return sftpFileDownloader;
		case HTTP:
		case HTTPS:
			return httpFileDownloader;
		default:
			// Already validated, so this should never happen
			throw new InvalidDataException(Errors.UNSUPPORTED_PROTOCOL.getMsg());
		}
	}
}
