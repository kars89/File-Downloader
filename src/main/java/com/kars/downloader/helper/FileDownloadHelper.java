package com.kars.downloader.helper;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kars.downloader.enums.Errors;
import com.kars.downloader.enums.Protocol;
import com.kars.downloader.exception.ValidationException;

/**
 * 
 * 
 * @author karthik.subbaramaiah
 *
 */
@Service
public class FileDownloadHelper {

	private Logger logger = LoggerFactory.getLogger(FileDownloadHelper.class);

	@Value("${download.output.directory}")
	private String outputDirectory;

	@Value("${download.output.temp.directory}")
	private String tempDirectory;

	/**
	 * 
	 * Moves the file from temp directory to output directory
	 * 
	 * @param saveAsFileName file to moved
	 * @throws IOException e
	 */
	public void moveFromTempToOutputDir(String saveAsFileName) throws IOException {
		File tempFile = new File(tempDirectory, saveAsFileName);
		File outputFile = new File(outputDirectory, saveAsFileName);
		outputFile.getParentFile().mkdirs();

		if (!tempFile.renameTo(outputFile)) {
			outputFile.delete();
			logger.error("Couldn't move the file from temp to output");
			throw new IOException("Couldn't move to the output directory");
		} else {
			tempFile.delete();
			logger.debug("temp file deleted");
		}
	}

	/**
	 * 
	 * Returns the protocol by url
	 * 
	 * @param url - url
	 * @return protocol
	 * @throws ValidationException if it is unsupported or invalid URL
	 */
	public Protocol getProtocol(String url) throws ValidationException {
		if (StringUtils.isEmpty(url)) {
			throw new ValidationException(Errors.INVALID_URL);
		}
		String[] protocol = url.split("://");
		if (protocol.length == 0) {
			throw new ValidationException(Errors.INVALID_URL);
		}
		Protocol proto = Protocol.getProtocolByUrl(protocol[0]);
		if (proto == null) {
			throw new ValidationException(Errors.UNSUPPORTED_PROTOCOL);
		}
		return proto;
	}
}
