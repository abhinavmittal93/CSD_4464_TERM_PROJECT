/**
 * 
 */
package com.termproject.csd4464.dao;

import java.security.MessageDigest;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.termproject.csd4464.model.AdminUsersModel;

/**
 * @author abhinavmittal
 * 
 * This is a DAO class which handles the Database queries related to "admin_users" table 
 * and transform the retrieved data and return to AdminController.
 *
 */
@Service
public class AdminDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 
	 * It retrieves the details of an admin user by username.
	 * 
	 * @param username
	 * @return AdminUsersModel
	 */
	public AdminUsersModel getAdminUserDetail(String username) {
		String sql = "select * from admin_users where username=?";
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<AdminUsersModel>(AdminUsersModel.class),
				new Object[] { username });
	}

	/**
	 * 
	 * It validates the admin user by comparing the password in the database and the password entered by the user.
	 * 
	 * @param username
	 * @param password
	 * @return True/False
	 */
	public boolean isValidAdminUser(String username, String password) {
		try {
			AdminUsersModel adminUsersModel = getAdminUserDetail(username);
			if (adminUsersModel != null) {
				byte[] bytesOfPassword = password.getBytes();

				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(bytesOfPassword);
			    byte[] digest = md.digest();
				return adminUsersModel.getPassword().equals(Base64.getEncoder().encodeToString(digest));
			}
		} catch (Exception e) {
			System.out.println("Exception occurred while validating admin user: " + e.getMessage() + e);
		}
		return false;
	}

}
