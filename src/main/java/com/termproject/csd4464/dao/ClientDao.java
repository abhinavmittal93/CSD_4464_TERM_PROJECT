/**
 * 
 */
package com.termproject.csd4464.dao;

import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.termproject.csd4464.model.ClientsModel;

/**
 * @author abhinavmittal
 * 
 * This is a DAO class which handles the Database queries related to "clients" table 
 * and transform the retrieved data and return to ClientController.
 *
 */
@Service
public class ClientDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 
	 * It retrieves all the records from clients table 
	 * and set it in the Model 
	 * and return the List. 
	 * 
	 * @return List<ClientsModel>
	 */
	public List<ClientsModel> getAllClients() {
		return jdbcTemplate.query("select * from clients", new RowMapper<ClientsModel>() {
			public ClientsModel mapRow(ResultSet rs, int row) throws SQLException {
				ClientsModel clientsModel = new ClientsModel();
				clientsModel.setClientId(rs.getLong(1));
				clientsModel.setFirstName(rs.getString(2));
				clientsModel.setLastName(rs.getString(3));
				clientsModel.setGender(rs.getString(4));
				clientsModel.setEmail(rs.getString(5));
				clientsModel.setPhone(rs.getString(6));
				clientsModel.setUsername(rs.getString(7));
				return clientsModel;
			}
		});
	}

	/**
	 * 
	 * It retrieves the details of an client user by username.
	 * 
	 * @param username
	 * @return ClientsModel
	 */
	public ClientsModel getClientsDetailByUsername(String username) {
		String sql = "select * from clients where username=?";
		try {
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<ClientsModel>(ClientsModel.class),
					new Object[] { username });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * It retrieves the details of an client user by clientId.
	 * 
	 * @param clientId
	 * @return ClientsModel
	 */
	public ClientsModel getClientsDetailById(Long clientId) {
		String sql = "select * from clients where client_id=?";
		try {
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<ClientsModel>(ClientsModel.class),
					new Object[] { clientId });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * 
	 * It validates the client user by comparing the password in the database and the password entered by the user.
	 * 
	 * @param username
	 * @param password
	 * @return True/False
	 */
	public boolean isValidClient(String username, String password) {
		try {
			ClientsModel clientsModel = getClientsDetailByUsername(username);
			if (clientsModel != null) {
				byte[] bytesOfPassword = password.getBytes();

				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(bytesOfPassword);
			    byte[] digest = md.digest();
				return clientsModel.getPassword().equals(Base64.getEncoder().encodeToString(digest));
			}
		} catch (Exception e) {
			System.out.println("Exception occurred while validating admin user: " + e.getMessage() + e);
		}
		return false;
	}

	/**
	 * 
	 * It adds a new record in clients table with the details provided by the admin user.
	 * 
	 * @param clientsModel
	 * @return the number of rows affected
	 */
	public int addClient(ClientsModel clientsModel) {
		String sql = "INSERT INTO clients (first_name, last_name, gender, email, phone, username, password) "
				+ "VALUES " + "('" + clientsModel.getFirstName() + "', '" + clientsModel.getLastName() + "', '"
				+ clientsModel.getGender() + "','" + clientsModel.getEmail() + "', '" + clientsModel.getPhone() + "', '"
				+ clientsModel.getUsername() + "', '" + clientsModel.getMd5Password() + "')";

		return jdbcTemplate.update(sql);
	}

	/**
	 * It updates a record in clients table with the details provided by the admin user.
	 * 
	 * @param clientsModel
	 * @return the number of rows affected
	 */
	public int updateClient(ClientsModel clientsModel) {
		String sql = "UPDATE clients SET first_name = '" + clientsModel.getFirstName() + "', last_name = '"
				+ clientsModel.getLastName() + "', gender = '" + clientsModel.getGender() + "', email = '"
				+ clientsModel.getEmail() + "', phone = '" + clientsModel.getPhone() + "', password = '"
				+ clientsModel.getMd5Password() + "'";

		return jdbcTemplate.update(sql);
	}

}
