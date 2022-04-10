/**
 * 
 */
package com.termproject.csd4464.model;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * @author abhinavmittal
 * 
 * Model class for table "admin_users" with adminId, username, password.
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
			byte[] bytesOfPassword = this.password.getBytes("UTF-8");

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(bytesOfPassword);
			byte[] digest = md.digest();
			return Base64.getEncoder().encodeToString(digest);
		} catch (Exception e) {
			System.out.println("Exception while converting admin's password to MD5 Hash");
			return null;
		}
	}

	@Override
	public String toString() {
		return "AdminUsersModel [adminId=" + adminId + ", username=" + username + ", password=" + password + "]";
	}
	
}
