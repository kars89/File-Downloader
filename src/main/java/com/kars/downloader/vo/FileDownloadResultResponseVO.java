package com.kars.downloader.vo;

import java.io.Serializable;

import com.kars.downloader.enums.DownloadStatus;

/**
 * 
 * FileDownloadResultResponseVO
 * 
 * @author karthik.subbaramaiah
 *
 */
public class FileDownloadResultResponseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private DownloadStatus downloadStatus;
	private int retryAttempt;
	private String remarks;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public DownloadStatus getDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(DownloadStatus downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	public int getRetryAttempt() {
		return retryAttempt;
	}

	public void setRetryAttempt(int retryAttempt) {
		this.retryAttempt = retryAttempt;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "FileDownloadResultResponseVO [url=" + url + ", downloadStatus=" + downloadStatus + ", retryAttempt="
				+ retryAttempt + ", remarks=" + remarks + "]";
	}

}
