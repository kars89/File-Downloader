package com.kars.downloader.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.util.StringUtils;

/**
 * FTPHelper
 * 
 * @author karthik.subbaramaiah
 *
 */
public class FTPHelper {

	private final String server;
	private final int port;
	private final String user;
	private final String password;
	private FTPClient ftp;

	public FTPHelper(String server, String user, String password) {
		this.server = server;
		this.port = 21;
		if (StringUtils.isEmpty(user)) {
			user = "anonymous";
		}
		this.user = user;
		this.password = password;
	}

	/**
	 * opens the connection to ftp
	 * 
	 * @throws IOException - if couldn't connect to the ftp
	 */
	public void open() throws IOException {
		ftp = new FTPClient();

		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

		ftp.connect(server, port);
		int reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			throw new IOException("Exception in connecting to FTP Server");
		}

		if (!ftp.login(user, password)) {
			ftp.logout();
			throw new IOException("Exception in login to FTP Server");
		}
	}

	/**
	 * closes the connection to ftp
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		ftp.disconnect();
	}

	Collection<String> listFiles(String path) throws IOException {
		FTPFile[] files = ftp.listFiles(path);

		return Arrays.stream(files).map(FTPFile::getName).collect(Collectors.toList());
	}

	public void putFileToPath(File file, String path) throws IOException {
		ftp.storeFile(path, new FileInputStream(file));
	}

	/**
	 * download file from ftp to the local
	 * 
	 * @param source   - source file
	 * @param tempFile - target file
	 * @throws IOException e
	 */
	public void downloadFile(String source, File tempFile) throws IOException {
		tempFile.getParentFile().mkdirs();
		try (FileOutputStream out = new FileOutputStream(tempFile)) {
			if (!ftp.retrieveFile(source, out)) {
				throw new IOException("Couldn't transfer the file from the server");
			}
		}
	}
}
