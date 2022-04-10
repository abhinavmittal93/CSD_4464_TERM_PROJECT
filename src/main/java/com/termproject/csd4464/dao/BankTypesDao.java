/**
 * 
 */
package com.termproject.csd4464.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.termproject.csd4464.model.BankTypesModel;

/**
 * @author abhinavmittal
 * 
 * This is a DAO class which handles the Database queries related to "bank_types" table 
 * and transform the retrieved data and return to Controller.
 *
 */
@Service
public class BankTypesDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 
	 * It retrieves all the records from bank_types table 
	 * and set it in the Model 
	 * and return the List. 
	 * 
	 * @return List<BankTypesModel>
	 */
	public List<BankTypesModel> getBankTypes() {
		System.out.println("getBankTypes(): begins:");
		return jdbcTemplate.query("select * from bank_types", new RowMapper<BankTypesModel>() {
			public BankTypesModel mapRow(ResultSet rs, int row) throws SQLException {
				BankTypesModel bankTypesModel = new BankTypesModel();
				bankTypesModel.setBankTypeId(rs.getLong(1));
				bankTypesModel.setBankType(rs.getString(2));
				return bankTypesModel;
			}
		});
	}
	
	/**
	 * 
	 * It retrieves the details of an bankType user by bankTypeId.
	 * 
	 * @param bankTypeId
	 * @return BankTypesModel
	 */
	public BankTypesModel getBankTypeById(Long bankTypeId) {
		System.out.println("getBankTypeById(): begins:, bankTypeId: " + bankTypeId);
		String sql = "select * from bank_types where bank_type_id=?";
		try {
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<BankTypesModel>(BankTypesModel.class),
					new Object[] { bankTypeId });
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Exception in getBankTypeById(): " + e.getMessage() + e);
			return null;
		}
	}

}
