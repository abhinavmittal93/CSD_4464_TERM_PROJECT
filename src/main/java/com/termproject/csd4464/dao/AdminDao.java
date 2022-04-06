/**
 * 
 */
package com.termproject.csd4464.dao;

import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.termproject.csd4464.model.AdminUsersModel;

/**
 * @author abhinavmittal
 *
 */
@Service
public class AdminDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private AdminUsersModel getAdminUserDetail(String username) {
		String sql = "select * from admin_users where username=?";
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<AdminUsersModel>(AdminUsersModel.class),
				new Object[] { username });
	}

	public boolean isValidAdminUser(String username, String password) {
		try {
			AdminUsersModel adminUsersModel = getAdminUserDetail(username);
			if (adminUsersModel != null) {
				byte[] bytesOfMessage = password.getBytes("UTF-8");

				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] theMD5digest = md.digest(bytesOfMessage);
				return adminUsersModel.getPassword().equals(theMD5digest.toString());
			}
		} catch (Exception e) {
			System.out.println("Exception occurred while validating admin user: " + e.getMessage() + e);
		}
		return false;
	}

}
