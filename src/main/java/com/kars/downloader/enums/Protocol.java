package com.kars.downloader.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Supported protocol
 * @author karthik.subbaramaiah
 *
 */
public enum Protocol {

	FTP("ftp"), SFTP("sftp"), HTTP("http"), HTTPS("https");

	private String name;

	private Protocol(String name) {
		this.name = name;
	}

	private static Map<String, Protocol> protocolMap = null;

	public static Protocol getProtocolByUrl(String name) {
		if(protocolMap == null) {
			initializeMap();
		}
		return protocolMap.get(name);
	}

	private static void initializeMap() {
		protocolMap = new HashMap<>();
		for (Protocol p : Protocol.values()) {
			protocolMap.put(p.name, p);
		}
	}
}
