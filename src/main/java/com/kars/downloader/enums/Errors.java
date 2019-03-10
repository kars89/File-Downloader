package com.kars.downloader.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Errors Details
 * @author karthik.subbaramaiah
 *
 */
public enum Errors {

	GENERIC_ERROR("ERR-01", "Generic Error"),
	INVALID_URL("ERR-02", "Invalid Url"),
	UNSUPPORTED_PROTOCOL("ERR-02", "Unsupported Protocol. Currently supported ones are http, ftp and sftp");
	

	private String code;
	private String msg;

	private static Map<String, Errors> errorsCodeMap = null;

	private Errors(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public static Errors getErrorsByCode(String code) {
		if (errorsCodeMap == null) {
			initializeMap();
		}
		return errorsCodeMap.get(code);
	}

	private static void initializeMap() {
		errorsCodeMap = new HashMap<>();
		for (Errors e : Errors.values()) {
			errorsCodeMap.put(e.code, e);
		}
	}
}
