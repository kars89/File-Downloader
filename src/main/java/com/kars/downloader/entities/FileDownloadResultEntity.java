package com.kars.downloader.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kars.downloader.enums.DownloadStatus;

/**
 * 
 * Entity to store the download status of the URLs
 * @author karthik.subbaramaiah
 *
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class FileDownloadResultEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "url")
	private String url;

	@Enumerated
	@Column(name = "download_status")
	private DownloadStatus downloadStatus;

	@Column(name = "retry_attempt")
	private int retryAttempt = 0;

	@Column(name = "remarks")
	private String remarks;
	
	private long serverSpeed;

	@Column(name = "created_date", nullable = false, updatable = false)
	@CreatedDate
	private long createdDate;

	@Column(name = "modified_date")
	@LastModifiedDate
	private long modifiedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}

	public long getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(long modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public long getServerSpeed() {
		return serverSpeed;
	}

	public void setServerSpeed(long serverSpeed) {
		this.serverSpeed = serverSpeed;
	}

	@Override
	public String toString() {
		return "FileDownloadResultEntity [id=" + id + ", url=" + url + ", downloadStatus=" + downloadStatus
				+ ", retryAttempt=" + retryAttempt + ", remarks=" + remarks + "]";
	}

}
