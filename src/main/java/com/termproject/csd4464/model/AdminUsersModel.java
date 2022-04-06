/**
 * 
 */
package com.termproject.csd4464.model;

import java.security.MessageDigest;

/**
 * @author abhinavmittal
 *
 */
public class AdminUsersModel {

	private Long adminId;

	private String username;

	private String password;

	/**
	 * @return the adminId
	 */
	public Long getAdminId() {
		return adminId;
	}

	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getMd5Password() {
		try {
			byte[] bytesOfMessage = this.password.getBytes("UTF-8");

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] theMD5digest = md.digest(bytesOfMessage);
			return theMD5digest.toString();
		} catch (Exception e) {
			System.out.println("Exception while converting admin's password to MD5 Hash");
			return null;
		}
	}

}
