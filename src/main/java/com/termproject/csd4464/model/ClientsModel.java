/**
 * 
 */
package com.termproject.csd4464.model;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * @author abhinavmittal
 * 
 * Model class for table "clients" with clientId, firstName, lastName, gender,
 *  email, phone, username, password.
 *
 */
public class ClientsModel {

	private Long clientId;

	private String firstName;

	private String lastName;

	private String gender;

	private String email;

	private String phone;

	private String username;

	private String password;

	/**
	 * @return the clientId
	 */
	public Long getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
			System.out.println("Exception while converting client's password to MD5 Hash");
			return null;
		}
	}

	@Override
	public String toString() {
		return "ClientsModel [clientId=" + clientId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", gender=" + gender + ", email=" + email + ", phone=" + phone + ", username=" + username
				+ ", password=" + password + "]";
	}
	
	

}
