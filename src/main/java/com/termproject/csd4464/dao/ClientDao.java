/**
 * 
 */
package com.termproject.csd4464.dao;

import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.termproject.csd4464.model.ClientsModel;

/**
 * @author abhinavmittal
 *
 */
@Service
public class ClientDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public ClientsModel getClientsDetailByUsername(String username) {
		String sql = "select * from clients where username=?";
		try {
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<ClientsModel>(ClientsModel.class),
					new Object[] { username });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public boolean isValidClient(String username, String password) {
		try {
			ClientsModel clientsModel = getClientsDetailByUsername(username);
			if (clientsModel != null) {
				byte[] bytesOfMessage = password.getBytes("UTF-8");

				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] theMD5digest = md.digest(bytesOfMessage);
				return clientsModel.getPassword().equals(theMD5digest.toString());
			}
		} catch (Exception e) {
			System.out.println("Exception occurred while validating admin user: " + e.getMessage() + e);
		}
		return false;
	}
	
	public int addClient(ClientsModel clientsModel) {
		String sql = "INSERT INTO clients (first_name, last_name, gender, email, phone, username, password) "
				+ "VALUES "
				+ "('" + clientsModel.getFirstName() + "', '" + clientsModel.getLastName() 
				+ "', '"+ clientsModel.getGender() +"','"+ clientsModel.getEmail() + "', '" + clientsModel.getPhone()
				+"', '"+ clientsModel.getUsername() +"', '"+ clientsModel.getMd5Password() +"')";

	    return jdbcTemplate.update(sql);  
	}
	
	public int updateClient(ClientsModel clientsModel) {
		String sql = "UPDATE clients SET first_name = '" + clientsModel.getFirstName() + "', last_name = '" + clientsModel.getLastName() 
				+ "', gender = '"+ clientsModel.getGender() +"', email = '"+ clientsModel.getEmail() +"', phone = '"+ clientsModel.getPhone() +"', password = '"+ clientsModel.getMd5Password() +"'";

	    return jdbcTemplate.update(sql);  
	}

}
