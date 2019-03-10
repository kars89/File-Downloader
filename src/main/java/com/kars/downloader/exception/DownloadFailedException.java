package com.kars.downloader.exception;

/**
 * DownloadFailedException
 * 
 * @author karthik.subbaramaiah
 *
 */
public class DownloadFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DownloadFailedException() {
		super();
	}

	public DownloadFailedException(String msg) {
		super(msg);
	}
	
	public DownloadFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
