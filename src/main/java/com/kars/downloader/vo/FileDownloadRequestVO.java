package com.kars.downloader.vo;

import java.io.Serializable;
import java.util.List;

public class FileDownloadRequestVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<URLDetailsVO> urlDetails;

	public List<URLDetailsVO> getUrlDetails() {
		return urlDetails;
	}

	public void setUrlDetails(List<URLDetailsVO> urlDetails) {
		this.urlDetails = urlDetails;
	}

	public static class URLDetailsVO implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String url;
		private String username;
		private String password;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		@Override
		public String toString() {
			return "URLDetailsVO [url=" + url + ", username=" + username + "]";
		}
	}

	@Override
	public String toString() {
		return "FileDownloadRequestVO [urlDetails=" + urlDetails + "]";
	}

}
