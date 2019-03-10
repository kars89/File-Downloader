package com.kars.downloader.exception;

/**
 * 
 * InvalidDataException
 * @author karthik.subbaramaiah
 *
 */
public class InvalidDataException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidDataException() {
		super();
	}

	public InvalidDataException(String msg) {
		super(msg);
	}
	
	public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
